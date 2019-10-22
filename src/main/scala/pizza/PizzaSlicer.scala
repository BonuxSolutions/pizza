package pizza

class PizzaSlicer(
  pizzaConfig: PizzaConfig,
  slicerStrategy: SlicerStrategy,
) {
  val cutter = new SliceCutter(
    pizzaConfig = pizzaConfig,
    slicerStrategy = slicerStrategy,
  )

  def slice(pizza: Pizza): CutPizza = {
    ???
  }
}
