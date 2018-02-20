package pizza;

import static java.lang.System.out;

public class App {

    public static void main(String[] args) {
        PizzaParser.possibleSlices(6)
                .forEach(out::println);
    }
}
