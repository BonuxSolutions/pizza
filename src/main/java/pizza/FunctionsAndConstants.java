package pizza;

abstract class FunctionsAndConstants {

  private FunctionsAndConstants() {}

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