package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

public class StringCharacterIndexerFactory implements IndexerFactory<String, Character> {

    @Override
    public Indexer<String, Character> get(String s) {
        return new StringCharacterIndexer(s);
    }
}
