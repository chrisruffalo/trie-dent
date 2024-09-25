package io.github.chrisruffalo.triedent.set.impl;

import io.github.chrisruffalo.triedent.structures.nodes.DefaultNodeFactory;
import io.github.chrisruffalo.triedent.set.TrieSet;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsHashCollectorFactory;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsHashIndexerFactory;

public class DnsHashTrie extends TrieSet<String, Number> {

    public DnsHashTrie() {
        super(
            new DnsHashCollectorFactory(),
            new DnsHashIndexerFactory(),
            new DefaultNodeFactory<>()
        );
    }
}
