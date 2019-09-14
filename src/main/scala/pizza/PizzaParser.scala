package pizza
import scala.io.Source

object PizzaParser {
  private case class PizzaConfig(
    R: Int,
    C: Int,
    L: Int,
    H: Int,
    pizza: Seq[String]
  )
  private def readConfig(file: STring): PizzaConfig ={
    val src = Source.fromInputStream(getClass().getClassLoader().getResourceAsStream(file))
    val lines = src.getLines().toList
    src.close()
    val Array(R, C, L, H) = lines.head.split(" ").toList.map(_.toInt)
    PizzaConfig(R, C, L, H, lines.tail)
  }
  def createPizza(
    inputSet: String,
  ): Seq[Cell] = {
    val pizzaConfig = readConfig(s"$inputset.in")
  }
}
