package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.set.impl.StringTrieSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

class StringTrieSetTest extends StringSetTest {

    @Test
    void basic() {
        final Set<String> trie = new StringTrieSet();
        this.basic(trie);
    }

    @Test
    void domains() {
        final Set<String> trie = new StringTrieSet();
        this.domains(trie);
    }

    @Test
    void torture() throws IOException {
        final Set<String> trie = new StringTrieSet();
        this.torture(trie);
    }

}

