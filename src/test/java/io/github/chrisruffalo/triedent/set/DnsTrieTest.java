package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.Trie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

class DnsTrieTest extends StringSetTest {

    @Test
    void basic() {
        final Set<String> dnsTrie = Trie.dnsTrieSet();
        dnsTrie.addAll(List.of(
            "google.com",
            "netflix.com",
            "disney.com",
            "mail.google.com",
            "github.com",
            "github.io"
        ));

        final Set<String> names = new HashSet<>(dnsTrie);
        names.forEach(x -> Assertions.assertTrue(dnsTrie.contains(x)));

        Assertions.assertFalse(dnsTrie.contains("github.co"));
        Assertions.assertFalse(dnsTrie.contains("mall.google.com"));

        if (dnsTrie instanceof final TrieSet<?,?> trieSet) {
            Assertions.assertEquals(8, trieSet.root.nodeCount());
        }
    }

    @Test
    void domains() {
        final Set<String> dnsTrie = Trie.dnsTrieSet();
        domains(dnsTrie);
    }

    @Test
    void torture() throws IOException {
        final Set<String> dnsTrie = Trie.dnsTrieSet();
        torture(dnsTrie);
    }

    @Test
    void million() throws IOException {
        final Set<String> dnsTrie = Trie.dnsTrieSet();
        million(dnsTrie);
    }

    @Test
    void millionCheck() throws IOException {
        final Set<String> dnsTrie = Trie.dnsTrieSet();
        millionCheck(dnsTrie);
    }

}
