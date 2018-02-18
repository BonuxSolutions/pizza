package pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

abstract class FunctionsAndConstants {

    static int T = 0;
    static int M = 1;

    private static double s2 = Math.sqrt(2.);
    static Function<Integer, Integer> sqrt2 = (mult) -> (int) Math.round(mult * s2);

    static List<SliceBase> minToppings(Pizza pizza) {
        List<SliceBase> minToppings = new ArrayList<>(pizza.C * pizza.R);

        int S = 0;
        int T = (pizza.T % 2 == 0) ? pizza.T / 2 : (pizza.T / 2) + 1;

        for (int r = 0; r < pizza.R; r++)
            for (int c = 0; c < pizza.C; c++) S += pizza.toppings[r][c];

        for (int r = 0; r < pizza.R; r++)
            for (int c = 0; c < pizza.C; c++)
                if (T > S) {
                    if (pizza.toppings[r][c] == M)
                        minToppings.add(SliceBase.create(r, c));
                } else {
                    minToppings.add(SliceBase.create(r, c));
                }
        return minToppings;
    }
}