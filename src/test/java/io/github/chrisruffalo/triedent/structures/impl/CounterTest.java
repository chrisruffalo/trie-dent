package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.structures.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CounterTest {

    @Test
    void getCount() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();
        constructor.insert(root, "dogs");
        constructor.insert(root, "dog");
        constructor.insert(root, "doggo");
        constructor.insert(root, "doggo");
        constructor.insert(root, "doggo");
        constructor.insert(root, "ralph");

        final Counter<Character> counter = new Counter<>();
        root.visit(counter);

        Assertions.assertEquals(4, counter.getCount());
        counter.reset();
        Assertions.assertEquals(0, counter.getCount());
    }
}