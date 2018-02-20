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

  static List<Slice> possibleSlices(int H) {
    List<Slice> result = new ArrayList<>(H);

    for (Integer i : divisors(H)) {
      for (int r = 0; r < H / i; r++) {
        for (int c = 0; c < H / i; c++) {
          if ((r == 0 && c == 0) || ((r + 1) * (c + 1) > H)) continue;
          Slice slice = new Slice.Builder()
              .withUpperLeft(0, 0)
              .withLowerRight(r, c)
              .build();
          result.add(slice);
        }
      }
    }

    return result;
  }
}