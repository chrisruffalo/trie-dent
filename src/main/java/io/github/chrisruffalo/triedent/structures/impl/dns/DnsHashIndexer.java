package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;
import org.apache.commons.codec.digest.MurmurHash3;

public class DnsHashIndexer implements Indexer<String, Number> {

    private Number[] hashes;

    public DnsHashIndexer(final String name) {
        update(name);
    }

    @Override
    public void update(String input) {
        byte[] inputBytes = input.getBytes();
        if (!input.contains(".")) {
            hashes = new Number[]{hash(inputBytes, 0, inputBytes.length)};
            return;
        }
        // count . in source
        int count = 0;
        for(int idx = 0; idx < input.length(); idx++) {
            if (input.charAt(idx) == '.') {
                count++;
            }
        };
        if (input.startsWith(".")) {
            count--;
        }
        if (input.endsWith(".")) {
            count--;
        }
        hashes = new Number[count + 1];
        int lag = 0;
        for (int idx = 0; idx < input.length(); idx++) {
            if (input.charAt(idx) == '.') {
                hashes[count--] = hash(inputBytes, lag, idx);
                lag = idx + 1;
            }
        }
        if (lag <= input.length() - 1) {
            hashes[0] = hash(inputBytes, lag, input.length());
        }
    }

    static Number hash(byte[] input, int offset, int end) {
        return MurmurHash3.hash32x86(input, offset, end - offset, MurmurHash3.DEFAULT_SEED);
    }

    static Number hash(String input) {
        return hash(input.getBytes(), 0, input.length());
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
        int comp = Long.compare(compare.longValue(), base.longValue());
        if (comp < 0) {
            return Direction.HIGHER;
        } if (comp > 0) {
            return Direction.LOWER;
        }
        return Direction.CENTER;
    }
}
