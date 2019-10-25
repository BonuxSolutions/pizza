package pizza
import SlicerStrategy._

object PizzaApp extends App {
  import PizzaParser._

  def pizzaToString(pizza: Pizza)(pizzaConfig: PizzaConfig) =
    pizza.cells
      .map(_._2)
      .map { cell =>
        cell.toString + (if ((cell.coords.x + 1) % pizzaConfig.size.cols == 0) "\n"
                else "")
      }
      .mkString

  val (p, pc) = createPizza("example")

  val pizza = pizzaToString(p)(pc)

  val strategy = SlicerStrategy(RandomSlice, Horizontal)

  val pizzaSlicer = new PizzaSlicer(
    pizzaConfig = pc,
    slicerStrategy = strategy,
  )

  val cpr = pizzaSlicer.slice(p)
  println(s"score=${cpr.score}")
}
