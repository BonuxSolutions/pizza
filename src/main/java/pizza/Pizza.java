package pizza;

import java.util.Arrays;

import static pizza.FunctionsAndConstants.T;

class Pizza {
  final int R, C, L, H;

  final int[][] toppings;

  private Pizza(int R, int C, int L, int H, int[][] toppings) {
    this.R = R;
    this.C = C;
    this.L = L;
    this.H = H;
    this.toppings = toppings;
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

    public Builder withToppings(int[][] pizza) {
      this.toppings = new int[R][C];
      for (int r = 0; r < R; r++) {
        this.toppings[r] = Arrays.copyOf(pizza[r], C);
      }

      return this;
    }

    public Pizza build() {
      return new Pizza(R, C, L, H, toppings);
    }
  }

  private String toppingsToString() {
    StringBuilder str = new StringBuilder();

    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        str.append((toppings[r][c] == T) ? "T" : "M");
      }
      if (r + 1 < R) { str.append("\n"); }
    }

    return str.toString();
  }

  @Override
  public String toString() {
    return R + " " +
        C + " " +
        L + " " +
        H + "\n" +
        toppingsToString();
  }
}