import java.util.Arrays;

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
}

abstract class FunctionsAndConstants {

  public static int T = 0;
  public static int M = 1;

  public static boolean testValid(int r1, int c1, int r2, int c2, Pizza pizza) {
    int l = Math.max(r1, r2) - Math.min(r1, r2);
    int h = Math.max(c1, c2) - Math.min(c1, c2);
    int lh = l * h;
    int s = 0;

    for (int r = r1; r < r2; r++) {
      for (int c = c1; c < c2; c++) {
        s += pizza.toppings[r][c];
      }
    }

    return lh <= pizza.H && lh - s >= pizza.L && s >= pizza.L;
  }
}

public class App {
  public String getGreeting() {
    return "Hello world.";
  }

  public static void main(String[] args) {
    System.out.println(new App().getGreeting());
  }
}
