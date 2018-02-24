package pizza;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static pizza.FunctionsAndConstants.M;
import static pizza.FunctionsAndConstants.T;

abstract class FileUtils {
    static Consumer<String> writeFile(Result result) {
        return fileName -> {
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(result.toString().getBytes());
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };
    }

    static String[] readFile(String filename) {
        StringBuilder contentBuilder = new StringBuilder();

        // /C:/paths/file.txt <-- cutting first / of for Windows
        if (System.getProperty("os.name").contains("Windows")) filename = filename.substring(1);

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
    static class SliceOffset {
        final int r, c;

        private SliceOffset(int r, int c) {
            this.r = r;
            this.c = c;
        }

        static SliceOffset create(int r, int c) {
            return new SliceOffset(r, c);
        }

        @Override
        public String toString() {
            return "(" + r + "," + c + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SliceOffset that = (SliceOffset) o;

            if (r != that.r) return false;
            return c == that.c;
        }

        @Override
        public int hashCode() {
            int result = r;
            result = 31 * result + c;
            return result;
        }
    }

    static Set<SliceOffset> possibleSlices(int H) {
        Set<SliceOffset> result = new HashSet<>(H);

        for (Integer i : FunctionsAndConstants.divisors(H)) {
            for (int r = 0; r < H / i; r++) {
                for (int c = 0; c < H / i; c++) {
                    if ((r != 0 || c != 0) && ((r + 1) * (c + 1) <= H)) {
                        SliceOffset sliceOffset = SliceOffset.create(r, c);
                        result.add(sliceOffset);
                    }
                }
            }
        }

        return result;
    }

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