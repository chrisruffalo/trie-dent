package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

public class StringIndexerFactory implements IndexerFactory<String, CharSequence> {

    @Override
    public Indexer<String, CharSequence> get(String whole) {
        return new StringIndexer(whole);
    }
}
