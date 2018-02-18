package pizza;

import java.util.function.Function;

abstract class FunctionsAndConstants {

    static int T = 0;
    static int M = 1;

    private static double s2 = Math.sqrt(2.);
    static Function<Integer, Integer> sqrt2 = (mult) -> (int) Math.round(mult * s2);

}