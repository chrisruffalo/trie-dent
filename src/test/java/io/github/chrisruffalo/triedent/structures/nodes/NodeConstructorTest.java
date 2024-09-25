package io.github.chrisruffalo.triedent.structures.nodes;

import io.github.chrisruffalo.triedent.structures.Collector;
import io.github.chrisruffalo.triedent.structures.impl.Counter;
import io.github.chrisruffalo.triedent.structures.impl.Finder;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterCollector;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class NodeConstructorTest {

    @Test
    void build() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();
        Assertions.assertTrue(constructor.insert(root, "dogs"));
        Assertions.assertTrue(constructor.insert(root, "crabs"));
        constructor.insert(root, "dog");
        Assertions.assertFalse(constructor.insert(root, "dog"));
        constructor.insert(root, "doggo");
        constructor.insert(root, "appender");
        constructor.insert(root, "append");
        constructor.insert(root, "apple");
        constructor.insert(root, "april");
        constructor.insert(root, "zebra");
        constructor.insert(root, "zzzzz");
        Assertions.assertFalse(constructor.insert(root, ""));
        constructor.insert(root, "welsh");
        Assertions.assertFalse(constructor.insert(root, "welsh"));
        constructor.insert(root, "wander");
        constructor.insert(root, "welter");
        Assertions.assertEquals("d", root.getValue().toString());
        Assertions.assertEquals(46, root.nodeCount());
    }

    @Test
    void triPath() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();
        constructor.insert(root, "f");
        Assertions.assertTrue(constructor.insert(root, "a"));
        constructor.insert(root, "z");
        Assertions.assertEquals("a", root.getLower().getValue().toString());
        Assertions.assertEquals("f", root.getValue().toString());
        Assertions.assertEquals("z", root.getHigher().getValue().toString());
        Assertions.assertEquals(3, root.nodeCount());
    }

    @Test
    void domains() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();

        Assertions.assertTrue(constructor.insert(root, "google.com"));
        Assertions.assertTrue(constructor.insert(root, "google.c"));
        Assertions.assertTrue(constructor.insert(root, "google.c.b"));
        Assertions.assertTrue(constructor.insert(root, "google.com.br"));
        Assertions.assertTrue(constructor.insert(root,"koino.io"));
        Assertions.assertTrue(constructor.insert(root, "google.co"));

        Finder<String, Character> finder = Finder.find(root, stringCharacterIndexerFactory.get("google.co"));
        root.walk(finder);
        Assertions.assertTrue(finder.matched());

        Assertions.assertEquals("google.co".length(), finder.getPath().size());
    }

    /**
     * This is an attempt to get one of every node type constructed
     */
    @Test
    void paths() {
        test(List.of(
                "f",
                "faces",
                "farthing",
                "facile",
                "fulcrum",
                "faltering",
                "fiver",
                "c",
                "capital",
                "crepes",
                "car",
                "cam",
                "cab",
                "cat",
                "caw",
                "n",
                "north",
                "nape",
                "nunez",
                "a",
                "z",
                "d",
                "dog",
                "dot",
                "doll",
                "dorm",
                "dork",
                "doro",
                "tomes",
                "tomb",
                "tomato",
                "alpha",
                "bravo",
                "charlie",
                "delta",
                "echo",
                "mike",
                "lima"
        ));
    }

    /**
     * For some reason, when testing the basic operations of the trie,
     * this series of operations ended up messing with the tree
     * building and finding, so we attempt to debug it here
     * the same way as above
     */
    @Test
    void repeats() {
        test(List.of(
            "dog",
            "cat",
            "ralph",
            "ralph",
            "cats",
            "alpha",
            "bravo",
            "charlie",
            "mike",
            "charlie",
            "lima"
        ));
    }

    void test(List<String> list) {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);

        Set<String> unique = new HashSet<>(list);

        final Collector<String, Character> collector = new StringCharacterCollector();
        RootNode<Character> root = constructor.build();
        list.forEach(x -> {
            constructor.insert(root, x);
        });

        Set<String> values = new HashSet<>(collector.collect(root.collect()));
        Assertions.assertTrue(values.containsAll(list));
        Assertions.assertEquals(unique.size(), values.size());
        list.forEach(values::remove);
        Assertions.assertTrue(values.isEmpty());

        list.forEach(x -> {
            Assertions.assertFalse(constructor.insert(root, x), String.format("was able to insert the word '%s' a second time", x));
        });

        values = new HashSet<>(collector.collect(root.collect()));
        Assertions.assertEquals(unique.size(), values.size());
        Assertions.assertTrue(values.containsAll(list));
        list.forEach(values::remove);
        Assertions.assertTrue(values.isEmpty());

        final Counter<Character> counter = new Counter<>();
        root.visit(counter);
        Assertions.assertEquals(unique.size(), counter.getCount());

        // not going to calculate this but it should always be > 0
        Assertions.assertTrue(0 < root.nodeCount());
    }
}