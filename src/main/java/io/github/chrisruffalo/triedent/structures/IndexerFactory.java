package io.github.chrisruffalo.triedent.structures;

/**
 * Creates indexers for the given WHOLE that turns it into PARTS.
 *
 * @param <WHOLE> to break down
 * @param <PART> it will be broken down into (for node storage)
 */
public interface IndexerFactory<WHOLE, PART> {

    Indexer<WHOLE, PART> get(WHOLE whole);

    default void release(Indexer<WHOLE, PART> indexer) {
        // no-op
    };

}
