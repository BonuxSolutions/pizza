package pizza

import SlicerStrategy._
import scala.annotation.tailrec

class PizzaSlicer(
  pizzaConfig: PizzaConfig,
  slicerStrategy: SlicerStrategy,
) {

  private val cutter = SliceProvider(
    pizzaConfig = pizzaConfig,
    slicerStrategy = slicerStrategy,
  )

  private val iterKeys: List[Key] = slicerStrategy.directionPreference match {
    case Horizontal =>
      (0 to pizzaConfig.size.cols).toList.flatMap { c =>
        (0 to pizzaConfig.size.rows).toList.map(Key.toKey(c, _))
      }
    case Vertical =>
      (0 to pizzaConfig.size.rows).toList.flatMap { r =>
        (0 to pizzaConfig.size.cols).toList.map(Key.toKey(_, r))
      }
  }

  @tailrec private def slice(
    keys: List[Key],
    pizza: Pizza,
    acc: CutPizza,
  ): CutPizza =
    keys match {
      case Nil => acc
      case key :: rest =>
        val upperLeft = pizza.cells.get(key).map(_.coords).flatMap(cutter.nextSlice(pizza))
        val cutPizza = upperLeft.fold(acc) { slice =>
          acc.copy(slices = slice :: acc.slices)
        }
        val pizzaNew = upperLeft.fold(pizza) { slice =>
          val keysToDelete = (slice.upperLeft.x to slice.lowerRight.x).flatMap { x =>
            (slice.upperLeft.y to slice.lowerRight.y).map { y =>
              Key.toKey(x, y)
            }
          }
          Pizza(pizza.cells.removedAll(keysToDelete))
        }

        slice(
          keys = rest,
          pizza = pizzaNew,
          acc = cutPizza,
        )
    }

  def slice(pizza: Pizza): CutPizza = {
    slice(iterKeys, pizza, CutPizza(Nil))
  }
}
