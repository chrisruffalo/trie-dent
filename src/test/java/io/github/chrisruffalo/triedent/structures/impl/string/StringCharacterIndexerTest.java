package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class StringCharacterIndexerTest {

    @Test
    void atIndex() {
        final StringCharacterIndexerFactory factory = new StringCharacterIndexerFactory();
        Assertions.assertEquals('b', factory.get("ball").atIndex(0));
        Assertions.assertEquals('a', factory.get("ball").atIndex(1));
        Assertions.assertEquals('l', factory.get("ball").atIndex(2));
        Assertions.assertEquals('l', factory.get("ball").atIndex(3));
        Assertions.assertNull(factory.get("ball").atIndex(4));
        Assertions.assertNull(factory.get("ball").atIndex(-1));
    }

    @Test
    void atOrBeyondEnd() {
        final StringCharacterIndexerFactory factory = new StringCharacterIndexerFactory();
        Assertions.assertTrue(factory.get("").atOrBeyondEnd(0));
        Assertions.assertTrue(factory.get("").atOrBeyondEnd(-1));
        Assertions.assertTrue(factory.get("").atOrBeyondEnd(22));
        Assertions.assertFalse(factory.get("grub").atOrBeyondEnd(1));
        Assertions.assertFalse(factory.get("grub").atOrBeyondEnd(2));
        Assertions.assertFalse(factory.get("grub").atOrBeyondEnd(0));
    }

    @Test
    void compare() {
        final StringCharacterIndexerFactory factory = new StringCharacterIndexerFactory();
        Assertions.assertEquals(Direction.CENTER, factory.get("a").compare(0, 'a'));
        Assertions.assertEquals(Direction.HIGHER, factory.get("a").compare(0, 'b'));
        Assertions.assertEquals(Direction.LOWER, factory.get("b").compare(0, 'a'));
    }

    @Test
    void iterator() {
        final String test = "dog dog dog";
        final StringCharacterIndexer indexer = new StringCharacterIndexer(test);
        final Iterator<Character> iterator = indexer.iterator();

        final StringBuilder builder = new StringBuilder();
        iterator.forEachRemaining(builder::append);

        Assertions.assertEquals(test, builder.toString());
    }

}