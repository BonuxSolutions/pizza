package pizza;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

final class Slice {
    final int r1, c1, r2, c2;

    private Slice(int r1, int c1, int r2, int c2) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
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

final class SlicesPerBase {
    final Set<Slice> slices;

    void addSlices(final List<Slice> slices) {
        this.slices.addAll(slices);
    }

    private SlicesPerBase(int r, int c) {
        slices = new HashSet<>(c * r);
    }

    static SlicesPerBase create(int r, int c) {
        return new SlicesPerBase(r, c);
    }
}