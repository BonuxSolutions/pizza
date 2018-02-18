package pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pizza.FunctionsAndConstants.M;

final class Pizza {

    final int R, C, L, H, T;

    final int[][] toppings;

    private Pizza(int R, int C, int L, int H, int[][] toppings) {
        this.R = R;
        this.C = C;
        this.L = L;
        this.H = H;
        this.toppings = toppings;
        this.T = R * C;
    }

    public static class Builder {
        private int R, C, L, H;

        private int[][] toppings;

        public Builder withRows(int R) {
            this.R = R;
            return this;
        }

        public Builder withCols(int C) {
            this.C = C;
            return this;
        }

        public Builder withMinIngredient(int L) {
            this.L = L;
            return this;
        }

        public Builder withTotalCells(int H) {
            this.H = H;
            return this;
        }

        public Builder withToppings(int[][] toppings) {
            this.toppings = new int[R][C];
            for (int r = 0; r < R; r++) {
                this.toppings[r] = Arrays.copyOf(toppings[r], C);
            }

            return this;
        }

        public Pizza build() {
            return new Pizza(R, C, L, H, toppings);
        }
    }

    @Override
    public String toString() {
        StringBuilder toppingsToString = new StringBuilder();

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                toppingsToString.append((toppings[r][c] == M) ? "M" : "T");
            }
            if (r + 1 < R) {
                toppingsToString.append("\n");
            }
        }

        return R + " " +
                C + " " +
                L + " " +
                H + "\n" +
                toppingsToString.toString();
    }
}

final class PizzaSlicer {
    final private Pizza pizza;

    List<SlicesPerBase> currentState;

    private PizzaSlicer(Pizza pizza) {
        this.pizza = pizza;
        currentState = new ArrayList<>(pizza.C * pizza.R);
    }

    static PizzaSlicer create(Pizza pizza) {
        return new PizzaSlicer(pizza);
    }

    void slicePizza(final List<SliceBase> sliceBases) {
        for (SliceBase sliceBase : sliceBases) {
            final SlicesPerBase slicesPerBase = SlicesPerBase.create(pizza.R, pizza.C);
            slicesPerBase.addSlices(forVertical(sliceBase));
            slicesPerBase.addSlices(forHorizontal(sliceBase));

            currentState.add(slicesPerBase);
        }
    }

    private List<Slice> forVertical(final SliceBase sliceBase) {
        final List<Slice> slices = new ArrayList<>();
        for (int r = sliceBase.r - pizza.H; r < sliceBase.r; r++) {
            if (isValid((r < 0) ? 0 : r, sliceBase.c, sliceBase.r, sliceBase.c)) {
                slices.add(markValid((r < 0) ? 0 : r, sliceBase.c, sliceBase.r, sliceBase.c));
            }
        }
        for (int r = sliceBase.r; r < sliceBase.r + pizza.H; r++) {
            if (isValid(sliceBase.r, sliceBase.c, (r >= pizza.R) ? pizza.R - 1 : r, sliceBase.c)) {
                slices.add(markValid(sliceBase.r, sliceBase.c, (r >= pizza.R) ? pizza.R - 1 : r, sliceBase.c));
            }
        }
        return slices;
    }

    private List<Slice> forHorizontal(final SliceBase sliceBase) {
        final List<Slice> slices = new ArrayList<>();
        for (int c = sliceBase.c - pizza.H; c < sliceBase.c; c++) {
            if (isValid(sliceBase.r, (c < 0) ? 0 : c, sliceBase.r, sliceBase.c)) {
                slices.add(markValid(sliceBase.r, (c < 0) ? 0 : c, sliceBase.r, sliceBase.c));
            }
        }
        for (int c = sliceBase.c; c < sliceBase.c + pizza.H; c++) {
            if (isValid(sliceBase.r, sliceBase.c, sliceBase.r, (c >= pizza.C) ? pizza.C - 1 : c)) {
                slices.add(markValid(sliceBase.r, sliceBase.c, sliceBase.r, (c >= pizza.C) ? pizza.C - 1 : c));
            }
        }
        return slices;
    }

    private Slice markValid(int r1, int c1, int r2, int c2) {
        return new Slice.Builder()
                .withUpperLeft(r1, c1)
                .withLowerRight(r2, c2)
                .build();
    }

    boolean isValid(int r1, int c1, int r2, int c2) {
        int l = r2 - r1 + 1;
        int h = c2 - c1 + 1;
        int lh = l * h;
        int s = 0;

        for (int r = r1; r <= r2; r++) {
            for (int c = c1; c <= c2; c++) {
                s += pizza.toppings[r][c];
            }
        }

        return lh <= pizza.H && lh - s >= pizza.L && s >= pizza.L;
    }
}
