package pizza
import scala.io.Source

object PizzaParser {
  private case class PizzaPlainConfig(
      R: Int,
      C: Int,
      L: Int,
      H: Int,
      cells: Seq[String]
  )
  private def readConfig(file: String): PizzaPlainConfig = {
    val src = Source.fromInputStream(
      getClass().getClassLoader().getResourceAsStream(file)
    )
    val lines = src.getLines().toList
    println(lines.mkString("\n"))
    println()
    src.close()
    val Array(r, c, l, h) = lines.head.split(" ").map(_.toInt)
    PizzaPlainConfig(r, c, l, h, lines.tail)
  }
  def createPizza(
      inputSet: String
  ): (Pizza, PizzaConfig) = {
    val pizzaConfig: PizzaPlainConfig = readConfig(s"$inputSet.in")
    (
      Pizza(
        cells = pizzaConfig.cells.zipWithIndex.flatMap {
          case (line, row) =>
            if (row > pizzaConfig.R)
              sys.error(s"Row too big: $row. Max expected ${pizzaConfig.R}")
            line.zipWithIndex.map {
              case (topping, col) =>
                if (col > pizzaConfig.C)
                  sys.error(s"Col too big: $col. Max expected ${pizzaConfig.C}")
                val coords = Coords(
                  x = col,
                  y = row
                )
                coords.key -> Cell(
                  coords = coords,
                  topping = Topping(topping)
                )
            }
        }
      ),
      PizzaConfig(
        size = Size(
          rows = pizzaConfig.R,
          cols = pizzaConfig.C
        ),
        minIngredientPerSlice = pizzaConfig.L,
        maxCellsPerSlice = pizzaConfig.H
      )
    )
  }
  def pizzaToString(
      pizza: Pizza
  )(
      pizzaConfig: PizzaConfig
  ) =
    pizza.cells.map(_._2).map { cell =>
      cell + (if ((cell.coords.x + 1) % pizzaConfig.size.cols == 0) "\n"
              else "")
    }.mkString
}

object PizzaParserApp extends App {
  import PizzaParser._

  val (p, pc) = createPizza("example")

  val pizza = pizzaToString(p)(pc)

  println(pizza)
}
