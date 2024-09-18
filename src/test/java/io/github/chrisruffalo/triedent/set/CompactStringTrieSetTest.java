package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.Trie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

class CompactStringTrieSetTest extends StringSetTest {

    @Test
    void basic() {
        final Set<String> trie = Trie.compactStringTrieSet();
        this.basic(trie);
    }

    @Test
    void domains() {
        final Set<String> trie = Trie.compactStringTrieSet();
        this.domains(trie);
    }

    @Test
    void torture() throws IOException {
        final Set<String> trie = Trie.compactStringTrieSet();
        this.torture(trie);
    }

    @Test
    void million() throws IOException {
        final Set<String> trie = Trie.compactStringTrieSet();
        this.millionCheck(trie);
    }
}

