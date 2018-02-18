package pizza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static pizza.FunctionsAndConstants.M;
import static pizza.FunctionsAndConstants.T;

abstract class FileUtils {
    static String[] readFile(String filename) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return contentBuilder.toString().split("\n");
    }
}

abstract class PizzaParser {
    static Pizza parseFromArray(String[] lines) {

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

        for (int r = 1; r <= R; r++) {
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
    }

    static List<SliceBase> toppingBases(Pizza pizza) {
        List<SliceBase> toppingBases = new ArrayList<>(pizza.C * pizza.R);

        int S = 0;
        int half = (pizza.T % 2 == 0) ? pizza.T / 2 : (pizza.T / 2) + 1;

        for (int r = 0; r < pizza.R; r++)
            for (int c = 0; c < pizza.C; c++) S += pizza.toppings[r][c];

        int min = (pizza.R * pizza.C - S > half) ? M : T;

        for (int r = 0; r < pizza.R; r++)
            for (int c = 0; c < pizza.C; c++)
                if (pizza.toppings[r][c] == min)
                    toppingBases.add(SliceBase.create(r, c));
        return toppingBases;
    }
}