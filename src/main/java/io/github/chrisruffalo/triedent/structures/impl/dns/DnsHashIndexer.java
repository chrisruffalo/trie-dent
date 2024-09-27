package io.github.chrisruffalo.triedent.structures.impl.dns;

import com.github.eprst.murmur3.MurmurHash3;
import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;

public class DnsHashIndexer implements Indexer<String, Number> {

    public static final int DEFAULT_SEED = 104729;

    private Number[] hashes;

    private int length;

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
            length = 0;
            hashes = new Number[]{hash(input)};
            return;
        } else {
            length = count + 1;
        }
        if (hashes == null || hashes.length < length) {
            hashes = new Number[length];
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
        if (index < 0 || index >= length) {
            return null;
        }
        return hashes[index];
    }

    @Override
    public boolean atOrBeyondEnd(int index) {
        return index >= length - 1;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public Direction compare(Number base, Number compare) {
        if (compare == null) {
            throw new IllegalStateException("the comparison value passed to an indexer cannot be null.");
        }
        if (base == null) {
            return Direction.NONE;
        }
        // using long.compare() seems slower
        final long ref = base.longValue() - compare.longValue();
        if (ref < 0) {
            return Direction.HIGHER;
        } else if (ref > 0) {
            return Direction.LOWER;
        }
        return Direction.CENTER;
    }
}
