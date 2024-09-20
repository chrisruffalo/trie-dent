package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.set.impl.DnsHashTrieSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

class DnsHashTrieSetTest extends StringSetTest {

    @Test
    void domains() {
        final Set<String> dnsTrie = new DnsHashTrieSet();
        domains(dnsTrie);
    }

    @Test
    void torture() throws IOException {
        final Set<String> dnsTrie = new DnsHashTrieSet();
        torture(dnsTrie);
    }

    @Test
    void million() throws IOException, InterruptedException {
        final Set<String> dnsTrie = new DnsHashTrieSet();
        million(dnsTrie);
        System.gc();
        System.out.println("done");
        Thread.sleep(30000);
    }

    @Test
    void millionCheck() throws IOException {
        final Set<String> dnsTrie = new DnsHashTrieSet();
        millionCheck(dnsTrie);
    }

}