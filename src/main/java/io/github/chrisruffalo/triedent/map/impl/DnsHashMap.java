package io.github.chrisruffalo.triedent.map.impl;

import io.github.chrisruffalo.triedent.map.TrieMap;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsHashIndexerFactory;

public class DnsHashMap extends TrieMap<String, Number, String> {

    public DnsHashMap() {
        super(new DnsHashIndexerFactory());
    }
}
