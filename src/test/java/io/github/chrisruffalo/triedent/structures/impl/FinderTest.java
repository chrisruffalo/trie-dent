package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.nodes.Node;
import io.github.chrisruffalo.triedent.structures.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.structures.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexer;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FinderTest {

    @Test
    void straight() {
        final String testString = "aaaaa";

        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();
        constructor.insert(root, testString);

        Finder<String, Character> finder = new Finder<>(stringCharacterIndexerFactory.get(testString));

        // do walk
        root.walk(finder);

        // check finder
        Assertions.assertTrue(finder.matched());

        finder = new Finder<>(stringCharacterIndexerFactory.get("aaaa"));
        root.walk(finder);
        Assertions.assertFalse(finder.matched());
    }

    @Test
    void confusingPath() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();
        constructor.insert(root, "dogs");
        constructor.insert(root, "dog");
        constructor.insert(root, "doggo");
        constructor.insert(root, "ream");
        constructor.insert(root, "dream");
        constructor.insert(root, "crab");
        constructor.insert(root, "crabs");
        constructor.insert(root, "crabby");
        constructor.insert(root, "crablike");

        Finder<String, Character> finder = Finder.find(root, new StringCharacterIndexer("dog"));
        Assertions.assertTrue(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("dogs"));
        Assertions.assertTrue(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("doggo"));
        Assertions.assertTrue(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("dogsgo"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("dogo"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("d"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("dogss"));
        Assertions.assertFalse(finder.matched());

        finder = Finder.find(root, new StringCharacterIndexer("crabby"));
        List<Node<Character>> path = finder.getPath();
        Assertions.assertEquals("c", path.getFirst().getValue().toString());
        Assertions.assertEquals("y", path.getLast().getValue().toString());
    }

}