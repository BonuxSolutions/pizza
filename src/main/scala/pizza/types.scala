package pizza

final case class Size(
  rows: Int,
  cols: Int,
)

final case class PizzaConfig(
  size: Size,
  minIngredientPerSlice: Int,
  maxCellsPerSlice: Int,
)

sealed trait Topping {
  def value: Char
}

object Topping {
  case object Tomato extends Topping {
    override def value: Char = 'T'
  }
  case object Mushroom extends Topping {
    override def value: Char = 'M'
  }

  private val toppings: Seq[Topping] = Seq(Tomato, Mushroom)

  def apply(v: Char): Topping =
    toppings
      .find(_.value == v)
      .getOrElse(sys.error(s"topping $v not recognized"))
}

final case class Key(value: String) extends AnyVal

final case class Coords(
  x: Int,
  y: Int,
) {
  def key: Key = Key(s"$x-$y")
}

final case class Cell(
  coords: Coords,
  topping: Topping,
  inSlice: Boolean = false,
) {
  override def toString: String = topping.value.toString
}

final case class Pizza(cells: Seq[(Key, Cell)])

final case class Slice(
  upperLeft: Coords,
  lowerRight: Coords,
) {
  def area: Int = (lowerRight.x - upperLeft.x + 1) * (lowerRight.y - upperLeft.y + 1)
}

final case class CutPizza(slices: Seq[Slice]) {
  def score: Int = slices.map(_.area).sum
}

final case class Shape(
  a: Int,
  b: Int,
)
