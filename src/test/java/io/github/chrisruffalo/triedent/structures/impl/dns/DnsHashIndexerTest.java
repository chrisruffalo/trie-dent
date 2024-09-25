package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Indexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DnsHashIndexerTest {

    @Test
    public void basic() {
        final DnsHashIndexer hashIndexer = new DnsHashIndexer("example.com");
        Assertions.assertEquals(2, hashIndexer.length());
        Assertions.assertEquals(DnsHashIndexer.hash("com").intValue(), hashIndexer.atIndex(0).intValue());
        Assertions.assertEquals(DnsHashIndexer.hash("example"), hashIndexer.atIndex(1), String.format("hash at index 1 '%s'='%d' does not equal '%d'", "example", DnsHashIndexer.hash("example").intValue(), hashIndexer.atIndex(1).intValue()));
        Assertions.assertFalse(hashIndexer.atOrBeyondEnd(0));
        Assertions.assertTrue(hashIndexer.atOrBeyondEnd(1));
    }

    @Test
    public void regression() {
        final DnsHashIndexerFactory hashIndexerFactory = new DnsHashIndexerFactory();
        Indexer<String, Number> indexer = hashIndexerFactory.get("youm7.com");
        Assertions.assertEquals(2, indexer.length());
        Assertions.assertEquals(DnsHashIndexer.hash("com"), indexer.atIndex(0));
        Assertions.assertEquals(DnsHashIndexer.hash("youm7"), indexer.atIndex(1));

        indexer = hashIndexerFactory.get("mp.pl");
        Assertions.assertEquals(2, indexer.length());
        Assertions.assertEquals(DnsHashIndexer.hash("pl"), indexer.atIndex(0));
        Assertions.assertEquals(DnsHashIndexer.hash("mp"), indexer.atIndex(1));

        indexer = hashIndexerFactory.get("a.b");
        Assertions.assertEquals(2, indexer.length());
        Assertions.assertEquals(DnsHashIndexer.hash("b"), indexer.atIndex(0));
        Assertions.assertEquals(DnsHashIndexer.hash("a"), indexer.atIndex(1));
    }

}
