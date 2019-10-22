package pizza

class PizzaSlicer(
  pizzaConfig: PizzaConfig,
  slicerStrategy: SlicerStrategy,
) {
  val cutter = SliceProvider(
    pizzaConfig = pizzaConfig,
    slicerStrategy = slicerStrategy,
  )

  def slice(pizza: Pizza): CutPizza = {
    ???
  }
}
