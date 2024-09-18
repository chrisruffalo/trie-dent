package io.github.chrisruffalo.triedent.structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This interface represents the logic that is used to break a WHOLE into
 * smaller PARTS for storage as nodes in a tree.
 *
 * @param <WHOLE> that the indexer will index (think: string)
 * @param <PART> that the indexer will return as a single part (think: character)
 */
public interface Indexer<WHOLE, PART> {

    /**
     * Allow reuse of this indexer by refreshing the value in the
     * indexer, necessary for pooling.
     *
     * @param input to reset to
     */
    void refresh(WHOLE input);

    PART atIndex(int index);

    boolean atOrBeyondEnd(int index);

    Iterator<PART> iterator();

    default List<PART> parts() {
        final List<PART> parts = new LinkedList<>();
        iterator().forEachRemaining(parts::add);
        return parts;
    }

    int length();

    Direction compare(int index, PART compare) throws IllegalStateException;

    /**
     * Returns the relative direction between two points of comparison. The output is given
     * in reference to the "direction" or position (as on a number line or tree) of the compare
     * value with relationship to the base value.
     *
     * If a null value is passed as the `base` then the output direction is NONE which means that
     * there is no comparison to be made. This is really only useful for the root of a tree which
     * has no value or other special/control conditions.
     *
     * If a null value is passed as the `compare` value then an {@link IllegalStateException} should
     * be thrown unless the indexer knows what to do with that value.
     *
     * @param base for the comparison (the center or 0 point)
     * @param compare target for comparison that we want information about, this value cannot be null
     * @return the direction that the "compare" value is compared to the "base" value
     * @throws IllegalStateException if the compare value is null
     */
    Direction compare(PART base, PART compare);

}
