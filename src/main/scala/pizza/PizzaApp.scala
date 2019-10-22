package pizza
import SlicerStrategy._

object PizzaApp extends App {
  import PizzaParser._

  def pizzaToString(pizza: Pizza)(pizzaConfig: PizzaConfig) =
    pizza.cells
      .map(_._2)
      .map { cell =>
        cell + (if ((cell.coords.x + 1) % pizzaConfig.size.cols == 0) "\n"
                else "")
      }
      .mkString

  val (p, pc) = createPizza("example")

  val pizza = pizzaToString(p)(pc)

  println(pizza)
  println(pc)

  val cutPizza = CutPizza(
    slices = Vector(
      Slice(
        upperLeft = Coords(0, 0),
        lowerRight = Coords(2, 1),
      ),
      Slice(
        upperLeft = Coords(0, 2),
        lowerRight = Coords(2, 2),
      ),
      Slice(
        upperLeft = Coords(0, 3),
        lowerRight = Coords(2, 4),
      ),
    ),
  )

  println(outputCutPizza(cutPizza).mkString("\n"))

<<<<<<< HEAD
  val gen = new SliceCutter(pc, UpperLeft(RandomSlice))
=======
  val gen = SliceProvider(pc)
>>>>>>> 00c14255a186ccbbcc8205823dcc5e4fc7658fd5

  println(gen.allSlices(p)(Coords(0, 0)).mkString("\n"))
  println()
  println(gen.nextSlice(p)(Coords(0, 0)).mkString("\n"))

  println(cutPizza.slices.map(_.area).sum)
}
