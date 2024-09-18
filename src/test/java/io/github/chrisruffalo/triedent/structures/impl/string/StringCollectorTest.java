package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class StringCollectorTest {

    @Test
    void collected() {
        final StringIndexerFactory stringIndexerFactory = new StringIndexerFactory();
        final NodeConstructor<String, CharSequence> constructor = new NodeConstructor<>(stringIndexerFactory);
        RootNode<CharSequence> root = constructor.build();
        constructor.insert(root, "dogs");
        constructor.insert(root, "dog");
        constructor.insert(root, "doggo");

        StringCollector collector = new StringCollector();

        Set<String> set = new HashSet<>(collector.collect(root.collect()));
        Assertions.assertTrue(set.contains("dogs"));
        Assertions.assertTrue(set.contains("doggo"));
        Assertions.assertTrue(set.contains("dog"));
    }
}