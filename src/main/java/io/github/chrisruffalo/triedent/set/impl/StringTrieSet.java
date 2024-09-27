package io.github.chrisruffalo.triedent.set.impl;

import io.github.chrisruffalo.triedent.structures.impl.PoolingIndexerFactory;
import io.github.chrisruffalo.triedent.structures.nodes.DefaultNodeFactory;
import io.github.chrisruffalo.triedent.set.TrieSet;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterCollectorFactory;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;

public class StringTrieSet extends TrieSet<String, Character> {

    public StringTrieSet() {
        super(
            new StringCharacterCollectorFactory(),
            new PoolingIndexerFactory<>(new StringCharacterIndexerFactory()),
            new DefaultNodeFactory<>()
        );
    }

}
