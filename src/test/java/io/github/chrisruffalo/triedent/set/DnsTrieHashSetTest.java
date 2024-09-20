package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.set.impl.DnsTrieHashSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

class DnsTrieHashSetTest extends StringSetTest {

    @Test
    void domains() {
        final Set<String> dnsTrie = new DnsTrieHashSet();
        domains(dnsTrie);
    }

    @Test
    void torture() throws IOException {
        final Set<String> dnsTrie = new DnsTrieHashSet();
        torture(dnsTrie);
    }

    @Test
    void million() throws Exception {
        final Set<String> dnsTrie = new DnsTrieHashSet();
        million(dnsTrie);
        // enable the following to get time for a heap dump
        //System.gc();
        //System.out.println("done");
        //Thread.sleep(30000);
    }

    @Test
    void millionCheck() throws IOException {
        final Set<String> dnsTrie = new DnsTrieHashSet();
        millionCheck(dnsTrie);
    }

}