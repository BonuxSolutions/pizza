package pizza;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static pizza.FunctionsAndConstants.M;
import static pizza.FunctionsAndConstants.T;

abstract class FileUtils {
  static Pizza readInput(String filename) {
    try (FileInputStream fis = new FileInputStream(new File(filename))) {
      String contents = new String(fis.readAllBytes());
      String[] lines = contents.split("\n");

      String[] firstLine = lines[0].split(" ");

      if (firstLine.length != 4) {
        throw new IllegalArgumentException(
            "not enough parameters in first line:" + Arrays.toString(firstLine));
      }
      int R = Integer.parseInt(firstLine[0]);
      int C = Integer.parseInt(firstLine[1]);

      if (lines.length - 1 != R) {
        throw new IllegalArgumentException(
            String.format("number %d of rows do not match %d", lines.length, R)
        );
      }

      int L = Integer.parseInt(firstLine[2]);
      int H = Integer.parseInt(firstLine[3]);

      int[][] toppings = new int[R][C];

      for (int r = 1; r < R; r++) {
        char[] line = lines[r].toCharArray();
        for (int c = 0; c < C; c++) {
          toppings[r - 1][c] = ('T' == line[c]) ? T : M;
        }
      }

      return new Pizza.Builder()
          .withRows(R)
          .withCols(C)
          .withMinIngredient(L)
          .withTotalCells(H)
          .withToppings(toppings)
          .build();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
