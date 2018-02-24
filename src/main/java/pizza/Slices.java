package pizza;

import java.util.Comparator;
import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

final class Slice {
    private final int r1, c1, r2, c2;
    final int area;

    private Slice(int r1, int c1, int r2, int c2) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.area = (r2 - r1 + 1) * (c2 - c1 + 1);
    }

    boolean intersects(Slice other) {
        int dr1 = max(r1, other.r1);
        int dr2 = min(r2, other.r2);
        int dc1 = max(c1, other.c1);
        int dc2 = min(c2, other.c2);

        return (dr2 >= dr1) && (dc2 >= dc1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slice slice = (Slice) o;
        return r1 == slice.r1 &&
                c1 == slice.c1 &&
                r2 == slice.r2 &&
                c2 == slice.c2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r1, c1, r2, c2);
    }

    static class Builder {
        private int r1, c1, r2, c2;

        Builder withUpperLeft(int r1, int c1) {
            this.r1 = r1;
            this.c1 = c1;
            return this;
        }

        Builder withLowerRight(int r2, int c2) {
            this.r2 = r2;
            this.c2 = c2;
            return this;
        }

        Slice build() {
            return new Slice(r1, c1, r2, c2);
        }
    }

    static Comparator<Slice> comparator() {
        return (Slice o1, Slice o2) -> (o1.r1 - o2.r1 == 0) ? o1.c1 - o2.c1 : o1.r1 - o2.r1;
    }

    @Override
    public String toString() {
        return r1 + " " + c1 + " " + r2 + " " + c2;
    }
}

final class SliceBase {
    final int r, c;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SliceBase) {
            SliceBase v = (SliceBase) obj;
            return v.c == this.c && v.r == this.r;
        } else {
            return false;
        }
    }

    private SliceBase(int r, int c) {
        this.r = r;
        this.c = c;
    }

    static SliceBase create(int r, int c) {
        return new SliceBase(r, c);
    }
}
