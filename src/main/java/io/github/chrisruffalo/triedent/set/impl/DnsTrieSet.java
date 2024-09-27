package io.github.chrisruffalo.triedent.set.impl;

import io.github.chrisruffalo.triedent.structures.impl.PoolingIndexerFactory;
import io.github.chrisruffalo.triedent.structures.nodes.DefaultNodeFactory;
import io.github.chrisruffalo.triedent.set.TrieSet;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsCollectorFactory;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsIndexerFactory;

public class DnsTrieSet extends TrieSet<String, CharSequence> {

    public DnsTrieSet() {
        super(
            new DnsCollectorFactory(),
            new PoolingIndexerFactory<>(new DnsIndexerFactory()),
            new DefaultNodeFactory<>()
        );
    }
}
