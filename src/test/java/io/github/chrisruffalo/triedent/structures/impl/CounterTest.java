package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.string.StringIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CounterTest {

    @Test
    void getCount() {
        final StringIndexerFactory stringIndexerFactory = new StringIndexerFactory();
        final NodeConstructor<String, CharSequence> constructor = new NodeConstructor<>(stringIndexerFactory);
        RootNode<CharSequence> root = constructor.build();
        constructor.insert(root, "dogs");
        constructor.insert(root, "dog");
        constructor.insert(root, "doggo");
        constructor.insert(root, "doggo");
        constructor.insert(root, "doggo");
        constructor.insert(root, "ralph");

        final Counter<CharSequence> counter = new Counter<>();
        root.visit(counter);

        Assertions.assertEquals(4, counter.getCount());
    }
}