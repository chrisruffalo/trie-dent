package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;

import java.util.Iterator;

public class StringCharacterIndexer implements Indexer<String, Character> {

    private String value;

    private StringCharacterIndexer() {
        this(null);
    }

    public StringCharacterIndexer(String value) {
        this.value = value;
    }

    @Override
    public void refresh(String input) {
        this.value = input;
    }

    @Override
    public Character atIndex(int index) {
        if (index < 0) {
            return null;
        }
        if (value == null) {
            return null;
        }
        if (index > value.length() - 1) {
            return null;
        }
        return value.charAt(index);
    }

    @Override
    public boolean atOrBeyondEnd(int index) {
        return index >= length() - 1;
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < length();
            }

            @Override
            public Character next() {
                return atIndex(currentIndex++);
            }
        };
    }

    @Override
    public int length() {
        return this.value == null ? 0 : this.value.length();
    }


    @Override
    public Direction compare(int index, Character compare) throws IllegalStateException {
        return compare(this.atIndex(index), compare);
    }

    @Override
    public Direction compare(Character base, Character compare) {
        if (compare == null) {
            throw new IllegalStateException("the comparison value passed to an indexer cannot be null.");
        }
        if (base == null) {
            return Direction.NONE;
        }
        int comp = Character.compare(base, compare);
        if (comp < 0) {
            return Direction.HIGHER;
        } if (comp > 0) {
            return Direction.LOWER;
        }
        return Direction.CENTER;
    }
}
