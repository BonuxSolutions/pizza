package pizza
import pizza.Topping.Tomato
import pizza.Topping.Mushroom
import pizza.SlicerStrategy._

class SliceProvider(
  pizzaConfig: PizzaConfig,
  slicerStrategy: SlicerStrategy,
) {

  /**
    *  code taken and adjusted from [[https://kostyukov.net/posts/combinatorial-algorithms-in-scala/]]
    */
  private def xvariations[A](l: List[A])(n: Int): List[List[A]] = {
    def mixmany(
      x: A,
      ll: List[List[A]],
    ): List[List[A]] = ll match {
      case hd :: tl => foldone(x, hd) ::: mixmany(x, tl)
      case _        => Nil
    }

    def foldone(
      x: A,
      ll: List[A],
    ): List[List[A]] =
      (1 to ll.length).foldLeft(List(x :: ll))((a, i) => (mixone(i, x, ll)) :: a)

    def mixone(
      i: Int,
      x: A,
      ll: List[A],
    ): List[A] =
      ll.slice(0, i) ::: (x :: ll.slice(i, ll.length))

    if (n > l.size) Nil
    else
      l match {
        case _ :: _ if n == 1 => l.map(List(_))
        case hd :: tl         => mixmany(hd, xvariations(tl)(n - 1)) ::: xvariations(tl)(n)
        case _                => Nil
      }
  }

  private val allShapes: Seq[Shape] = {
    val minElements = pizzaConfig.minIngredientPerSlice * 2
    val maxElements = pizzaConfig.maxCellsPerSlice
    val l = (0 until maxElements).toList
    val variants = xvariations(l)(2)
    val samePairs = l.zip(l).map { case (a, b) => List(a, b) }

    (variants ::: samePairs).map { l =>
      val List(a: Int, b: Int) = l
      Shape(a, b)
    }.filter { s =>
      val r = (s.a + 1) * (s.b + 1)
      r >= minElements && r <= maxElements
    }
  }

  private def allSlices(pizza: Pizza)(upperLeft: Coords): Seq[Slice] =
    allShapes.map { shape =>
      Slice(
        upperLeft = upperLeft,
        lowerRight = upperLeft.copy(
          x = upperLeft.x + shape.a,
          y = upperLeft.y + shape.b,
        ),
      )
    }.filter { slice =>
      slice.upperLeft.x >= 0 &&
      slice.lowerRight.x <= pizzaConfig.size.cols &&
      slice.upperLeft.y >= 0 &&
      slice.lowerRight.y <= pizzaConfig.size.rows
    }.filter { slice =>
      val cells = pizza.cells
        .map(_._2)
        .filter { cell =>
          cell.coords.x <= slice.lowerRight.x &&
          cell.coords.x >= slice.upperLeft.x &&
          cell.coords.y <= slice.lowerRight.y &&
          cell.coords.y >= slice.upperLeft.y
        }

      cells.count(_.topping == Tomato) >= pizzaConfig.minIngredientPerSlice &&
      cells.count(_.topping == Mushroom) >= pizzaConfig.minIngredientPerSlice &&
      cells.forall { cell =>
        !cell.inSlice
      }
    }

  def nextRandomSlice(pizza: Pizza)(upperLeft: Coords): Option[Slice] = {
    import scala.util.Random

    val slices = allSlices(pizza)(upperLeft)
    val n = Random.nextInt(slices.size)
    slices.zipWithIndex.find(_._2 == n).map(_._1)
  }

  def nextSlice(pizza: Pizza)(upperLeft: Coords): Option[Slice] = slicerStrategy.sliceSize match {
    case MaxSlice => allSlices(pizza)(upperLeft).maxByOption(_.area)
    case MinSlice => allSlices(pizza)(upperLeft).minByOption(_.area)
    case RandomSlice => nextRandomSlice(pizza)(upperLeft)
  }
}

object SliceProvider {
  def apply(
    pizzaConfig: PizzaConfig,
    slicerStrategy: SlicerStrategy,
  ): SliceProvider = new SliceProvider(
    pizzaConfig = pizzaConfig,
    slicerStrategy = slicerStrategy,
  )
}
