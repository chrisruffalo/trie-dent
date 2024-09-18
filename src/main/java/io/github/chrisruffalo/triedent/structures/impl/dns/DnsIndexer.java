package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DnsIndexer implements Indexer<String, CharSequence> {

    private final List<CharSequence> labels;

    public DnsIndexer(final String name) {
        labels = new ArrayList<>(0);
        refresh(name);
    }

    @Override
    public void refresh(String name) {
        labels.clear();
        if(!name.contains(".")) {
            labels.add(name);
            return;
        }
        int lag = 0;
        int index = 0;
        while (index < name.length()) {
            if (name.charAt(index) == '.') {
                labels.addFirst(name.substring(lag, index).intern());
                lag = index + 1;
            }
            index++;
        }
        labels.addFirst(name.substring(lag).intern());
    }

    @Override
    public List<CharSequence> parts() {
        return Indexer.super.parts();
    }

    @Override
    public CharSequence atIndex(int index) {
        if (index < 0 || index >= labels.size()) {
            return null;
        }
        return labels.get(index) == null ? null : labels.get(index);
    }

    @Override
    public boolean atOrBeyondEnd(int index) {
        return index >= labels.size() - 1;
    }

    @Override
    public Iterator<CharSequence> iterator() {
        return labels.iterator();
    }

    @Override
    public int length() {
        return labels.size();
    }

    @Override
    public Direction compare(int index, CharSequence compare) throws IllegalStateException {
        return compare(this.atIndex(index), compare);
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
