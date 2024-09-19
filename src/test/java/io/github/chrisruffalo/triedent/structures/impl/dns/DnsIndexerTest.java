package io.github.chrisruffalo.triedent.structures.impl.dns;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DnsIndexerTest {

    @Test
    void simple() {
        final DnsIndexerFactory dnsIndexerFactory = new DnsIndexerFactory();
        final DnsIndexer indexer = dnsIndexerFactory.get("alpha.google.com.br");

        Assertions.assertEquals(4, indexer.length());
        Assertions.assertEquals("br", indexer.atIndex(0).toString());
        Assertions.assertEquals("com", indexer.atIndex(1).toString());
        Assertions.assertEquals("google", indexer.atIndex(2).toString());
        Assertions.assertEquals("alpha", indexer.atIndex(3).toString());
        Assertions.assertTrue(indexer.atOrBeyondEnd(3));
        Assertions.assertNull(indexer.atIndex(4));

    }

}
