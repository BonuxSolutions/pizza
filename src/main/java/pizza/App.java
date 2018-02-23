package pizza;

import java.util.Set;

import static pizza.PizzaParser.possibleSlices;
import static pizza.PizzaParser.toppingBases;

public class App {

    public static void main(String[] args) {
        String fileName = args[1];

        Pizza pizza = PizzaParser.parseFromArray(FileUtils.readFile(fileName));

        PizzaSlicer ps = PizzaSlicer.create(pizza);
        Set<Slice> slices = ps.slicePizza(toppingBases(pizza), possibleSlices(pizza.H));
    }
}
