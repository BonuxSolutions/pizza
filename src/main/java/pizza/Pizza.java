package pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pizza.Constants.T;

class Pizza {
  private class SlicesPerIteration {
    int[][] slices = new int[R][C];
  }

  final int R, C, L, H;

  final int[][] toppings;

  final List<SlicesPerIteration> allSlices;

  private Pizza(int R, int C, int L, int H, int[][] toppings) {
    this.R = R;
    this.C = C;
    this.L = L;
    this.H = H;
    this.toppings = toppings;

    this.allSlices = new ArrayList<SlicesPerIteration>(R * C);
    this.allSlices.add(new SlicesPerIteration());
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

  void nextSlice() {

  }

  private boolean isValid(int r1, int c1, int r2, int c2, Pizza pizza) {
    int l = r1 - r2 + 1;
    int h = c1 - c2 + 1;
    int lh = l * h;
    int s = 0;

    for (int r = r1; r < r2; r++) {
      for (int c = c1; c < c2; c++) {
        for (SlicesPerIteration slicesPerIteration : allSlices) {
          if (slicesPerIteration.slices[r][c] > 0) { return false; }
        }
        s += pizza.toppings[r][c];
      }
    }

    return lh <= pizza.H && lh - s >= pizza.L && s >= pizza.L;
  }

  @Override
  public String toString() {
    StringBuilder toppingsToString = new StringBuilder();

    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        toppingsToString.append((toppings[r][c] == T) ? "T" : "M");
      }
      if (r + 1 < R) { toppingsToString.append("\n"); }
    }

    return R + " " +
        C + " " +
        L + " " +
        H + "\n" +
        toppingsToString.toString();
  }
}