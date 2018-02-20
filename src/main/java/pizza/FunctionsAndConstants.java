package pizza;

import java.util.ArrayList;
import java.util.List;

abstract class FunctionsAndConstants {

    static int T = 0;
    static int M = 1;

    static List<Integer> divisors(int N) {
        List<Integer> result = new ArrayList<>(N / 2);
        result.add(1);

        for (int i = 2; i < N; i++) {
            if (N % i == 0) {
                result.add(i);
            }
        }
        result.add(N);

        return result;
    }
}