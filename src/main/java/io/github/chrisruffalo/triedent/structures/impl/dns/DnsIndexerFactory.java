package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.IndexerFactory;

public class DnsIndexerFactory implements IndexerFactory<String, CharSequence> {

    @Override
    public DnsIndexer get(String s) {
        return new DnsIndexer(s);
    }

}
