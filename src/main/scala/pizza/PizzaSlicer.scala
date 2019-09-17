package pizza

class PizzaSlicer(
    pizzaConfig: PizzaConfig,
    slicerStrategy: SlicerStrategy
) {
  def slice(pizza: Pizza): CutPizza = ???
}
