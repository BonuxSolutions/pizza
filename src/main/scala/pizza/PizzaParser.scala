package pizza
import scala.io.Source

object PizzaParser {
  private case class PizzaConfig(
      R: Int,
      C: Int,
      L: Int,
      H: Int,
      cells: Seq[String]
  )
  private def readConfig(file: STring): PizzaConfig = {
    val src = Source.fromInputStream(
      getClass().getClassLoader().getResourceAsStream(file)
    )
    val lines = src.getLines().toList
    src.close()
    val Array(R, C, L, H) = lines.head.split(" ").toList.map(_.toInt)
    PizzaConfig(R, C, L, H, lines.tail)
  }
  def createPizza(
      inputSet: String
  ): Pizza = {
    val pizzaConfig: PizzaConfig = readConfig(s"$inputset.in")
    Pizza(
      size = Size(
        rows = pizzaConfig.R,
        cols = pizzaConfig.C
      ),
      minIngredientPerSlice = pizzaConfig.L,
      maxCellsPerSlice = pizzaCofig.H,
      cells = pizzaConfig.cells.zipWithIndex.flatMap { (line, row) =>
        if (row > pizzaConfig.R)
          sys.error(s"Row too big: $row. Max expected ${pizzaCofig.R}")
        line.zipWithIndex { (topping, col) =>
          if (col > pizzaConfig.C)
            sys.error(s"Col too big: $col. Max expected ${pizzaConfig.C}")
          Cell(
            x = row,
            y = col,
            topping = Topping(topping)
          )
        }
      }
    )
  }
}

object PizzaParserApp extends App {
  import PizzaParser._

  val pizza = createPizza("small")

  println(pizza.toString)
}
