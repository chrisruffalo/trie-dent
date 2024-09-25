package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.structures.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.Finder;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsHashIndexer;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.ZonedDateTime;
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
        List<String> list = List.of(
  "google.com",
            "google.co",
            "google.com.br",
            "google.br",
            "youm7.com",
            "live.com",
            "twitter.com",
            "sinai.com",
            "hotmail.com",
            "google.co.jp",
            "mp.pl",
            "cc5.cn",
            "on1.com",
            "te5.com",
            "7y7.com",
            "r4.com",
            "a.com",
            "alpha",
            "66n.com",
            "ht5.com",
            "dts.com",
            "air1.com",
            "y0.com",
            "w5.akamai.net",
            "c42.qbo.intuit.com",
            "koino.io",
            "a.b"
        );
        list.forEach(item -> {
            Assertions.assertTrue(set.add(item), String.format("'%s' was not added to trie", item));
        });
        set.clear();
        Assertions.assertTrue(set.addAll(list));
        list.forEach(item -> {
            Assertions.assertFalse(set.add(item), String.format("trie does not already contain collected item: '%s'", item));
        });
        Assertions.assertFalse(set.addAll(list));
        Assertions.assertTrue(set.contains("google.com"));
        Assertions.assertTrue(set.contains("google.com.br"));
        Assertions.assertTrue(set.contains("koino.io"));
        Assertions.assertTrue(set.contains("google.br"));
        Assertions.assertTrue(set.contains("a.b"));
        Assertions.assertTrue(set.containsAll(list));
        Assertions.assertFalse(set.add("google.co")); // should not be able to add a second time
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    void million(Set<String> set) throws IOException {
        Path millionCsvPath = Paths.get("src", "test", "resources", "top-1m.csv");
        final AtomicLong entryCount = new AtomicLong(0);

        final ZonedDateTime start = ZonedDateTime.now();
        try(final BufferedReader reader = Files.newBufferedReader(millionCsvPath)) {
            reader.lines().forEach(x -> {
                final String entry = CharBuffer.wrap(x, x.indexOf(",") + 1, x.length()).toString().trim();
                boolean added = set.add(entry);
                if (added) {
                    entryCount.set(entryCount.get() + 1);
                } else {
                    if (set instanceof TrieSet trieSet) {
                        final RootNode<Number> root = trieSet.root;
                        final Finder<String, Number> finder = Finder.find(root, new DnsHashIndexer(entry));
                        if (finder.matched()) {
                            System.out.printf("domain '%s' not added due to possible collision (???)\n", entry);
                        }
                    }
                }
            });
        }
        final ZonedDateTime end = ZonedDateTime.now();
        System.out.printf("million insert took %dms\n", Duration.between(start, end).toMillis());

        // ensure that the count of loaded lines matches the count of what is in the set
        //Assertions.assertEquals(entryCount.get(), set.size());
    }

    void millionCheck(Set<String> set) throws IOException {
        // load set
        million(set);

        final ZonedDateTime start = ZonedDateTime.now();
        Path millionCsvPath = Paths.get("src", "test", "resources", "top-1m.csv");
        final AtomicLong entryCount = new AtomicLong(0);
        try(final BufferedReader reader = Files.newBufferedReader(millionCsvPath)) {
            reader.lines().forEach(x -> {
                final String entry = CharBuffer.wrap(x, x.indexOf(",") + 1, x.length()).toString().trim();
                Assertions.assertTrue(set.contains(entry), String.format("trie should contain domain: '%s'", entry));
                entryCount.set(entryCount.get() + 1);
            });
        }
        final ZonedDateTime end = ZonedDateTime.now();
        long duration = Duration.between(start, end).toMillis();
        System.out.printf("million check took %dms (%f ms per operation)\n", duration, ((double)duration)/entryCount.get());
    }

}
