package pizza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static pizza.FunctionsAndConstants.M;
import static pizza.FunctionsAndConstants.T;

abstract class FileUtils {
    static Pizza readInput(String filename) {

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {

            String[] lines = contentBuilder.toString().split("\n");
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
