package pizza;

import java.util.ArrayList;
import java.util.List;


class Result {
  static class Slice {
    private final Coord p1, p2;

    private Slice(Coord p1, Coord p2) {
      this.p1 = p1;
      this.p2 = p2;
    }

    static Slice create(Coord p1, Coord p2) {
      return new Slice(p1, p2);
    }

    @Override
    public String toString() {
      return p1 + " " + p2;
    }
  }

  static class Coord {
    private final int r, c;

    private Coord(int r, int c) {
      this.r = r;
      this.c = c;
    }

    static Coord create(int r, int c) {
      return new Coord(r, c);
    }

    @Override
    public String toString() {
      return r + " " + c;
    }
  }

  private final List<Slice> slices;

  private Result(List<Slice> slices) {
    this.slices = new ArrayList<>(slices.size());
    this.slices.addAll(slices);
  }

  static class Builder {
    private List<Slice> slices;

    Builder() {this.slices = new ArrayList<>();}

    public Builder withSlice(Slice slice) {
      slices.add(slice);
      return this;
    }

    public Result build() {
      return new Result(slices);
    }
  }

  @Override
  public String toString() {
    StringBuilder slicesAsString = new StringBuilder();
    for (int i = 0; i < slices.size(); i++) {
      slicesAsString.append(slices.get(i));
      if (i + 1 < slices.size()) slicesAsString.append("\n");
    }
    return slices.size() + "\n" + slicesAsString.toString();
  }
}
