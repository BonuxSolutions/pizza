package pizza;

import java.util.*;

import static pizza.FunctionsAndConstants.M;
import static pizza.PizzaParser.SliceOffset;

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

    private PizzaSlicer(Pizza pizza) {
        this.pizza = pizza;
    }

    static PizzaSlicer create(Pizza pizza) {
        return new PizzaSlicer(pizza);
    }

    Set<Slice> slicePizza(final Collection<SliceBase> sliceBases, final Collection<SliceOffset> sliceOffsets) {
        Set<Slice> currentState = new HashSet<>(pizza.C * pizza.R);
        for (SliceBase sliceBase : sliceBases) {
            for (SliceOffset sliceOffset : sliceOffsets) {
                for (int r = sliceBase.r - sliceOffset.r; r <= sliceBase.r; r++) {
                    for (int c = sliceBase.c - sliceOffset.c; c <= sliceBase.c; c++) {
                        if (isValid(r, c, r + sliceOffset.r, c + sliceOffset.c)) {
                            currentState.add(markValid(r, c, r + sliceOffset.r, c + sliceOffset.c));
                        }
                    }
                }
            }
        }
        return currentState;
    }

    Collection<Slice> withMaxArea(Set<Slice> slices) {
        List<Slice> oslices = new ArrayList<>(slices);
        oslices.sort(Slice.comparator());

        List<Slice> slicesWithMaxAreaCovered = new LinkedList<>();

        for (Slice s1 : oslices) {
            List<Slice> newSliceSet = new LinkedList<>();
            newSliceSet.add(s1);
            for (Slice s2 : oslices) {
                if (s1 == s2) continue;
                boolean add = true;
                for (Slice s3 : new ArrayList<>(newSliceSet)) {
                    if (s2.intersects(s3)) {
                        add = false;
                    }
                }
                if (add) newSliceSet.add(s2);
            }
            int currentAreaCovered = slicesWithMaxAreaCovered.stream().map(k -> k.area).reduce(0, Integer::sum);
            int newAreaCovered = newSliceSet.stream().map(k -> k.area).reduce(0, Integer::sum);
            if (newAreaCovered > currentAreaCovered) {
                slicesWithMaxAreaCovered = newSliceSet;
            }
            if (newAreaCovered == pizza.R * pizza.C) {
                return slicesWithMaxAreaCovered;
            }
        }

        return slicesWithMaxAreaCovered;
    }

    private Slice markValid(int r1, int c1, int r2, int c2) {
        return new Slice.Builder()
                .withUpperLeft(r1, c1)
                .withLowerRight(r2, c2)
                .build();
    }

    boolean isValid(int r1, int c1, int r2, int c2) {
        if (r1 < 0 || c1 < 0 || r2 >= pizza.R || c2 >= pizza.C) return false;
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
