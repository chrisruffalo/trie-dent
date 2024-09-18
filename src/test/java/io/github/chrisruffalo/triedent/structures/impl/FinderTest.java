package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.nodes.Node;
import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.string.StringIndexer;
import io.github.chrisruffalo.triedent.structures.impl.string.StringIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FinderTest {

    @Test
    void straight() {
        final String testString = "aaaaa";

        final StringIndexerFactory stringIndexerFactory = new StringIndexerFactory();
        final NodeConstructor<String, CharSequence> constructor = new NodeConstructor<>(stringIndexerFactory);
        RootNode<CharSequence> root = constructor.build();
        constructor.insert(root, testString);

        Finder<String, CharSequence> finder = new Finder<>(stringIndexerFactory.get(testString));

        // do walk
        root.walk(finder);

        // check finder
        Assertions.assertTrue(finder.matched());

        finder = new Finder<>(stringIndexerFactory.get("aaaa"));
        root.walk(finder);
        Assertions.assertFalse(finder.matched());
    }

    @Test
    void confusingPath() {
        final StringIndexerFactory stringIndexerFactory = new StringIndexerFactory();
        final NodeConstructor<String, CharSequence> constructor = new NodeConstructor<>(stringIndexerFactory);
        RootNode<CharSequence> root = constructor.build();
        constructor.insert(root, "dogs");
        constructor.insert(root, "dog");
        constructor.insert(root, "doggo");
        constructor.insert(root, "ream");
        constructor.insert(root, "dream");
        constructor.insert(root, "crab");
        constructor.insert(root, "crabs");
        constructor.insert(root, "crabby");
        constructor.insert(root, "crablike");

        Finder<String, CharSequence> finder = Finder.find(root, new StringIndexer("dog"));
        Assertions.assertTrue(finder.matched());

        finder = Finder.find(root, new StringIndexer("dogs"));
        Assertions.assertTrue(finder.matched());

        finder = Finder.find(root, new StringIndexer("doggo"));
        Assertions.assertTrue(finder.matched());

        finder = Finder.find(root, new StringIndexer("dogsgo"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringIndexer("dogo"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringIndexer("d"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringIndexer("dogss"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringIndexer("crabby"));
        List<Node<CharSequence>> path = finder.getPath();
        Assertions.assertEquals("c", path.getFirst().getValue().toString());
        Assertions.assertEquals("y", path.getLast().getValue().toString());
    }

}