package pizza;

import java.util.Set;

import static pizza.PizzaParser.possibleSlices;
import static pizza.PizzaParser.toppingBases;

public class App {

    public static void main(String[] args) {
        String fileName = args[0];

        Pizza pizza = PizzaParser.parseFromArray(FileUtils.readFile(fileName));

        PizzaSlicer ps = PizzaSlicer.create(pizza);
        Set<Slice> slices = ps.slicePizza(toppingBases(pizza), possibleSlices(pizza.H));

        Set<Slice> slicesMaxArea = ps.withMaxArea(slices);

        Result.Builder builder = new Result.Builder(slicesMaxArea.size());
        slicesMaxArea.forEach(s -> builder.withSlice(s));
        Result result = builder.build();
        FileUtils.writeFile(result).accept(fileName + ".out");
    }
}
