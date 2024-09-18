package io.github.chrisruffalo.triedent.set;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

class HashSetTest extends StringSetTest {

    @Test
    void million() throws IOException {
        million(new HashSet<>());
    }

}
