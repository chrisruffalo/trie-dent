package io.github.chrisruffalo.triedent.structures.impl.dns;

import com.github.eprst.murmur3.MurmurHash3;
import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;

import java.util.Objects;

public class DnsHashIndexer implements Indexer<String, Number> {

    public static final int DEFAULT_SEED = 104729;

    private Number[] hashes;

    public DnsHashIndexer(final String name) {
        update(name);
    }

    @Override
    public void update(String input) {
        // count . in source
        int count = 0;
        for(int idx = 0; idx < input.length(); idx++) {
            if (input.charAt(idx) == '.') {
                count++;
            }
        };
        int inputLength = input.length();
        if (input.charAt(0) == '.') {
            count--;
        }
        if (input.charAt(inputLength - 1) == '.') {
            count--;
        }
        if (count == 0) {
            hashes = new Number[]{hash(input)};
            return;
        }
        if (hashes != null) {
            if (count + 1 != hashes.length) {
                hashes = new Number[count + 1];
            }
        } else {
            hashes = new Number[count + 1];
        }
        int lag = 0;
        for (int idx = 0; idx < inputLength; idx++) {
            if (input.charAt(idx) == '.') {
                hashes[count--] = hash(input, lag, idx);
                lag = idx + 1;
            }
        }
        if (lag <= inputLength - 1) {
            hashes[0] = hash(input, lag, input.length());
        }
    }

    static Number hash(CharSequence input, int offset, int end) {
        return (long) MurmurHash3.murmurhash3_x86_32(input, offset, end - offset, DEFAULT_SEED);
    }

    static Number hash(CharSequence input) {
        return hash(input, 0, input.length());
    }

    @Override
    public Number atIndex(int index) {
        if (index < 0 || index >= hashes.length) {
            return null;
        }
        return hashes[index];
    }

    @Override
    public boolean atOrBeyondEnd(int index) {
        return index >= hashes.length - 1;
    }

    @Override
    public int length() {
        return hashes.length;
    }

    @Override
    public Direction compare(Number base, Number compare) {
        if (compare == null) {
            throw new IllegalStateException("the comparison value passed to an indexer cannot be null.");
        }
        if (base == null) {
            return Direction.NONE;
        }
        if (Objects.equals(base, compare)) {
            return Direction.CENTER;
        }
        // using long.compare() seems slower
        final long ref = base.longValue() - compare.longValue();
        if (ref < 0) {
            return Direction.HIGHER;
        }
        return Direction.LOWER;
    }
}
