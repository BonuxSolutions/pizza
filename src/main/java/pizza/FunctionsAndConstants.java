package pizza;

import java.util.ArrayList;
import java.util.List;

abstract class FunctionsAndConstants {

    static int T = 0;
    static int M = 1;

    static List<SliceBase> minToppings(Pizza pizza) {
        List<SliceBase> minToppings = new ArrayList<>(pizza.C * pizza.R);

        int S = 0;
        int half = (pizza.T % 2 == 0) ? pizza.T / 2 : (pizza.T / 2) + 1;

        for (int r = 0; r < pizza.R; r++)
            for (int c = 0; c < pizza.C; c++) S += pizza.toppings[r][c];

        int min = (pizza.R * pizza.C - S > half) ? M : T;

        for (int r = 0; r < pizza.R; r++)
            for (int c = 0; c < pizza.C; c++)
                if (pizza.toppings[r][c] == min)
                    minToppings.add(SliceBase.create(r, c));
        return minToppings;
    }
}