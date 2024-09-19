package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(Direction.CENTER, factory.get("a").compare('a', 'a'));
        Assertions.assertEquals(Direction.HIGHER, factory.get("a").compare('a', 'b'));
        Assertions.assertEquals(Direction.LOWER, factory.get("b").compare('b', 'a'));
    }
}