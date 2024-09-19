package io.github.chrisruffalo.triedent.map.impl;

import io.github.chrisruffalo.triedent.map.TrieMap;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;

public class StringTrieMap<V> extends TrieMap<String, Character, V> {

    public StringTrieMap() {
        super(
            new StringCharacterIndexerFactory()
        );
    }
}
