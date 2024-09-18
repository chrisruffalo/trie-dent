package io.github.chrisruffalo.triedent;

import io.github.chrisruffalo.triedent.nodes.DefaultNodeFactory;
import io.github.chrisruffalo.triedent.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.set.TrieSet;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;
import io.github.chrisruffalo.triedent.structures.impl.PoolingIndexerFactory;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsCollectorFactory;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsIndexerFactory;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterCollectorFactory;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCollectorFactory;
import io.github.chrisruffalo.triedent.structures.impl.string.StringIndexerFactory;

import java.util.Set;

public class Trie {

    public static Set<String> stringTrieSet() {
        final StringCollectorFactory collectorFactory = new StringCollectorFactory();
        final IndexerFactory<String, CharSequence> indexerFactory = new PoolingIndexerFactory<>(new StringIndexerFactory());

        return new TrieSet<>(collectorFactory, indexerFactory, new DefaultNodeFactory<>());
    }

    public static Set<String> compactStringTrieSet() {
        final StringCharacterCollectorFactory collectorFactory = new StringCharacterCollectorFactory();
        final IndexerFactory<String,Character> indexerFactory = new PoolingIndexerFactory<>(new StringCharacterIndexerFactory());

        return new TrieSet<>(collectorFactory, indexerFactory, new DefaultNodeFactory<>());
    }

    public static Set<String> dnsTrieSet() {
        final DnsCollectorFactory collectorFactory = new DnsCollectorFactory();
        final IndexerFactory<String, CharSequence> indexerFactory = new PoolingIndexerFactory<>(new DnsIndexerFactory());
        NodeFactory<CharSequence> nodeFactory = new DefaultNodeFactory<>();
        return new TrieSet<>(collectorFactory, indexerFactory, nodeFactory);
    }

}
