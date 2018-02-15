package pizza;

import java.util.ArrayList;
import java.util.List;

class Slice {
  final Point p1, p2;

  private Slice(Point p1, Point p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  static Slice create(Point p1, Point p2) {
    return new Slice(p1, p2);
  }

  @Override
  public String toString() {
    return p1 + " " + p2;
  }
}

class Point {
  final int r, c;

  private Point(int r, int c) {
    this.r = r;
    this.c = c;
  }

  static Point create(int r, int c) {
    return new Point(r, c);
  }

  @Override
  public String toString() {
    return r + " " + c;
  }
}

class Result {
  final List<Slice> slices;

  private Result(List<Slice> slices) {
    this.slices = new ArrayList<>(slices.size());
    this.slices.addAll(slices);
  }

  static class Builder {
    private List<Slice> slices;

    public Builder() {this.slices = new ArrayList<>();}

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
