package pizza
import SlicerStrategy._

object PizzaApp extends App {
  import PizzaParser._

  def pizzaToString(pizza: Pizza)(pizzaConfig: PizzaConfig) =
    pizza.cells.toSeq
      .sortBy(_._1.value)
      .map(_._2)
      .map { cell =>
        cell.toString + (if ((cell.coords.x + 1) % pizzaConfig.size.cols == 0) "\n"
                         else "")
      }
      .mkString

  val (p, pc) = createPizza("example")

  val strategy = SlicerStrategy(RandomSlice, Horizontal)

  val pizzaSlicer = new PizzaSlicer(
    pizzaConfig = pc,
    slicerStrategy = strategy,
  )

  val cpr = LazyList.from(1).map(_ => pizzaSlicer.slice(p)).dropWhile(_.score < 15).head

  println(outputCutPizza(cpr))
}
