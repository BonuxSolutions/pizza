package pizza;

import java.util.Arrays;

import static pizza.Constants.T;

final class Pizza {

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

final class PizzaSlicer {
  final private Pizza pizza;

  class SlicesPerIteration {
    int currentSliceNumber = 0;
    int coveredCells = 0;
    /**
     * this will be holder of slices in current iteration
     * each cell belonging to some slice will have slice number
     * which will be incremented whenever new slice is created
     */
    final int[][] slices = new int[pizza.R][pizza.C];
  }

  SlicesPerIteration currentState;

  private PizzaSlicer(Pizza pizza) {
    this.pizza = pizza;
    currentState = new SlicesPerIteration();
  }

  static PizzaSlicer create(Pizza pizza) {
    return new PizzaSlicer(pizza);
  }

  /**
   * create new slice - increment currentSliceNumber and fill cells with that value
   */
  void nextValidSlice() {
    class Position {
      int x, y = 0;

      private Position findFreePosition() {
        if (currentState.currentSliceNumber > 0) {
          for (int r1 = 0; r1 < pizza.R; r1++) {
            for (int c1 = 0; c1 < pizza.C; c1++) {
              if (currentState.slices[r1][c1] == 0) {
                this.x = r1;
                this.y = c1;
                return this;
              }
            }
          }
        }
        return this;
      }
    }
    Position p = new Position().findFreePosition();
    boolean anyValid = false;
    for (int r = p.x; r < pizza.R; r++) {
      for (int c = p.y; c < pizza.C; c += r + 1) {
        if (isValid(p.x, p.y, r, c)) {
          anyValid = true;
          markValid(p.x, p.y, r, c);
          return;
        }
      }
    }
    if (!anyValid) {
      currentState.slices[p.x][p.y] = (-1);
    }
  }

  private void markValid(int r1, int c1, int r2, int c2) {
    currentState.currentSliceNumber += 1;
    int s = 0;
    for (int r = r1; r < r2; r++) {
      for (int c = c1; c < c2; c++) {
        s += 1;
        currentState.slices[r][c] = currentState.currentSliceNumber;
      }
    }
    currentState.coveredCells += s;
  }

  boolean isValid(int r1, int c1, int r2, int c2) {
    int l = r2 - r1 + 1;
    int h = c2 - c1 + 1;
    int lh = l * h;
    int s = 0;

    for (int r = r1; r <= r2; r++) {
      for (int c = c1; c <= c2; c++) {
        if (currentState.slices[r][c] > 0) { return false; }

        s += pizza.toppings[r][c];
      }
    }

    return lh <= pizza.H && lh - s >= pizza.L && s >= pizza.L;
  }
}