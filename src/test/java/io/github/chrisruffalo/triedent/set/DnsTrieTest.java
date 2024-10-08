package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.set.impl.DnsTrieSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DnsTrieTest extends StringSetTest {

    @Test
    void basic() {
        final Set<String> dnsTrie = new DnsTrieSet();
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
    }

    @Test
    void domains() {
        final Set<String> dnsTrie = new DnsTrieSet();
        domains(dnsTrie);
    }

    @Test
    void torture() throws IOException {
        final Set<String> dnsTrie = new DnsTrieSet();
        torture(dnsTrie);
    }

    @Test
    void million() throws IOException {
        final Set<String> dnsTrie = new DnsTrieSet();
        million(dnsTrie);
    }

    @Test
    void millionCheck() throws IOException {
        final Set<String> dnsTrie = new DnsTrieSet();
        millionCheck(dnsTrie);
    }

}
