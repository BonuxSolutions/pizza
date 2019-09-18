package pizza

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

  val gen = new SliceCutter(pc)

  println(gen.allShapes.mkString("\n"))
}
