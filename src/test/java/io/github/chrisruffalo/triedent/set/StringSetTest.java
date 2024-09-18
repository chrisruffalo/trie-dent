package io.github.chrisruffalo.triedent.set;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public abstract class StringSetTest {

    void basic(Set<String> set) {
        Assertions.assertTrue(set.isEmpty());
        Assertions.assertTrue(set.add("dog"));
        Assertions.assertEquals(1, set.size());
        Assertions.assertFalse(set.isEmpty());
        set.add("cat");
        set.add("ralph");
        Assertions.assertFalse(set.add("ralph"));
        Assertions.assertFalse(set.isEmpty());
        Assertions.assertEquals(3, set.size());

        Assertions.assertTrue(set.contains("dog"));
        Assertions.assertFalse(set.contains("dogs"));
        Assertions.assertTrue(set.contains("cat"));
        Assertions.assertFalse(set.contains("cats"));
        Assertions.assertTrue(set.contains("ralph"));

        set.add("cats");
        Assertions.assertTrue(set.contains("cats"));

        Assertions.assertEquals(4, set.size());

        Assertions.assertTrue(set.addAll(List.of("alpha", "bravo", "charlie")));

        Assertions.assertTrue(set.contains("alpha"));
        Assertions.assertTrue(set.contains("bravo"));
        Assertions.assertTrue(set.contains("charlie"));
        Assertions.assertFalse(set.add("charlie"));

        Assertions.assertFalse(set.addAll(List.of("mike", "charlie", "lima", "delta", "foxtrot")));

        Assertions.assertTrue(set.contains("alpha"), "does not contain 'alpha'");
        Assertions.assertTrue(set.contains("bravo"), "does not contain 'bravo'");
        Assertions.assertTrue(set.contains("charlie"), "does not contain 'charlie'");
        Assertions.assertTrue(set.contains("lima"), "does not contain 'lima'");
        Assertions.assertTrue(set.contains("mike"), "does not contain 'mike'");

        Assertions.assertEquals(11, set.size());

        set.iterator().forEachRemaining(x -> {
            Assertions.assertTrue(set.contains(x), String.format("trie does not contain collected item: '%s'", x));
        });

        set.clear();
        Assertions.assertTrue(set.isEmpty());
        Assertions.assertEquals(0, set.size());

        set.iterator().forEachRemaining(x -> {
            Assertions.assertTrue(set.contains(x));
        });
    }

    void domains(Set<String> set) {
        set.add("google.com");
        set.add("google.com.br");
        set.add("coop");
        set.add("gunther");
        set.add("koino.io");
        set.add("google.br");
        set.add("google.co");
        Assertions.assertFalse(set.add("google.co")); // should not be able to add a second time
        Assertions.assertTrue(set.contains("google.com"));
        Assertions.assertTrue(set.contains("google.com.br"));
        Assertions.assertTrue(set.contains("koino.io"));
        Assertions.assertTrue(set.contains("google.br"));
    }

    void torture(Set<String> set) throws IOException {
        Path top100000 = Paths.get("src", "test", "resources", "top-100000-domains.txt");
        final AtomicLong lineCount = new AtomicLong(0);
        try(final BufferedReader reader = Files.newBufferedReader(top100000)) {
            reader.lines().forEach(x -> {
                boolean added = set.add(x);
                if (added) {
                    lineCount.set(lineCount.get() + 1);
                } else {
                    System.out.printf("domain '%s' not added (???)\n", x);
                }
            });
        }

        // ensure that the count of loaded lines matches the count of what is in the trie
        Assertions.assertEquals(lineCount.get(), set.size());

        try(final BufferedReader reader = Files.newBufferedReader(top100000)) {
            reader.lines().forEach(x -> {
                Assertions.assertTrue(set.contains(x), String.format("trie should contain domain: '%s'", x));
            });
        }
    }

    void million(Set<String> set) throws IOException {
        Path millionCsvPath = Paths.get("src", "test", "resources", "top-1m.csv");
        final AtomicLong entryCount = new AtomicLong(0);

        try(final BufferedReader reader = Files.newBufferedReader(millionCsvPath)) {
            reader.lines().forEach(x -> {
                final String entry = CharBuffer.wrap(x, x.indexOf(","), x.length()).toString().trim();
                boolean added = set.add(entry);
                if (added) {
                    entryCount.set(entryCount.get() + 1);
                } else {
                    System.out.printf("domain '%s' not added (???)\n", entry);
                }
            });
        }

        // ensure that the count of loaded lines matches the count of what is in the set
        Assertions.assertEquals(entryCount.get(), set.size());
    }

    void millionCheck(Set<String> set) throws IOException {
        Path millionCsvPath = Paths.get("src", "test", "resources", "top-1m.csv");
        try(final BufferedReader reader = Files.newBufferedReader(millionCsvPath)) {
            reader.lines().forEach(x -> {
                final String entry = CharBuffer.wrap(x, x.indexOf(","), x.length()).toString().trim();
                Assertions.assertTrue(set.contains(entry), String.format("trie should contain domain: '%s'", x));
            });
        }
    }

}
