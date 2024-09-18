package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;

import java.nio.CharBuffer;
import java.util.Iterator;

public class StringIndexer implements Indexer<String, CharSequence> {

    private String value;

    private StringIndexer() {
        this(null);
    }

    public StringIndexer(String value) {
        this.value = value;
    }

    @Override
    public void refresh(String input) {
        this.value = input;
    }

    @Override
    public CharSequence atIndex(int index) {
        if (index < 0) {
            return  null;
        }
        if (value == null) {
            return null;
        }
        if (index > value.length() - 1) {
            return null;
        }
        return String.valueOf(value.charAt(index)).intern();
    }

    @Override
    public boolean atOrBeyondEnd(int index) {
        return index >= length() - 1;
    }

    @Override
    public Iterator<CharSequence> iterator() {
        return new Iterator<>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < length();
            }

            @Override
            public CharSequence next() {
                return atIndex(currentIndex++);
            }
        };
    }

    @Override
    public int length() {
        return this.value == null ? 0 : this.value.length();
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
