package io.github.chrisruffalo.triedent.set;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;

class HashSetTest extends StringSetTest {

    @Test
    void million() throws Exception {
        final HashSet<String> set = new HashSet<>();
        million(set);
        // enable the following to get time for a heap dump
        //System.gc();
        //System.out.println("done");
        //Thread.sleep(30000);
    }

    @Test
    void millionCheck() throws IOException {
        final HashSet<String> set = new HashSet<>();
        millionCheck(set);
    }

}
