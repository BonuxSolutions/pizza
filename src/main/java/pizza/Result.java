package pizza;

import java.util.ArrayList;
import java.util.Collection;


class Result {
    private final Collection<Slice> slices;

    private Result(Collection<Slice> slices) {
        this.slices = new ArrayList<>(slices.size());
        this.slices.addAll(slices);
    }

    static class Builder {
        private Collection<Slice> slices;

        Builder(int size) {
            this.slices = new ArrayList<>(size);
        }

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
        int i = 0;
        for (Slice slice : slices) {
            slicesAsString.append(slice);
            i += 1;
            if (i + 1 <= slices.size()) slicesAsString.append("\n");
        }
        return slices.stream().map(s -> s.area).reduce(0, Integer::sum) + "\n" + slicesAsString.toString();
    }
}
