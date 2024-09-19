package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

public class DnsHashIndexerFactory implements IndexerFactory<String, Number> {

    @Override
    public Indexer<String, Number> get(String s) {
        return new DnsHashIndexer(s);
    }

}
