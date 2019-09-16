package pizza

final case class Size(
    rows: Int,
    cols: Int
)

sealed trait Topping {
  def value: String
}
object Topping {
  case object Tomato extends Topping {
    override def value: String = "T"
  }
  case object Mushroom extends Topping {
    override def value: String = "M"
  }

  private val toppings: Seq[Topping] = Seq(Tomato, Mushroom)

  def apply(v: String): Topping =
    toppings.find(_ == v).getOrElse(sys.error(s"topping $v not recognized"))
}

final case class Cell(
    x: Int,
    y: Int,
    topping: Topping,
    inSlice: Boolean = false
) {
  override def toString: String = topping.value
}

final case class Pizza(
    size: Size,
    cells: Seq[Cell],
    minIngredientPerSlice: Int,
    maxCellsPerSlice: Int
) {
  override def toString: String = cells.map { cell =>
    cell + (if (cell.x == size.rows - 1) "\n" else "")
  }
}
