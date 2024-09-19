package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;

import java.util.ArrayList;
import java.util.List;

public class DnsIndexer implements Indexer<String, CharSequence> {

    private CharSequence name;

    private final List<Range> labels;

    private record Range(int start, int end) {
    }

    public DnsIndexer(final String name) {
        labels = new ArrayList<>(0);
        update(name);
    }

    @Override
    public void update(String name) {
        this.name = name;
        if (!labels.isEmpty()) {
            this.labels.clear();
        }
        if (!name.contains(".")) {
            this.labels.add(new Range(0, name.length()));
            return;
        }
        int lag = 0;
        for (int idx = 0; idx < name.length(); idx++) {
            if (name.charAt(idx) == '.') {
                this.labels.addFirst(new Range(lag, idx));
                lag = idx + 1;
            }
        }
        this.labels.addFirst(new Range(lag, name.length()));
    }

    @Override
    public CharSequence atIndex(int index) {
        if (index < 0 || index >= labels.size()) {
            return null;
        }
        Range range = labels.get(index);
        if (range == null) {
            return null;
        }
        return this.name.subSequence(range.start, range.end);
    }

    @Override
    public boolean atOrBeyondEnd(int index) {
        return index >= labels.size() - 1;
    }

    @Override
    public int length() {
        return labels.size();
    }

    @Override
    public Direction compare(CharSequence base, CharSequence compare) {
        if (compare == null) {
            throw new IllegalStateException("the comparison value passed to an indexer cannot be null.");
        }
        if (base == null) {
            return Direction.NONE;
        }
        int comp = CharSequence.compare(base, compare);
        if (comp < 0) {
            return Direction.HIGHER;
        } if (comp > 0) {
            return Direction.LOWER;
        }
        return Direction.CENTER;
    }
}
