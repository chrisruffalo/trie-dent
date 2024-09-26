package io.github.chrisruffalo.triedent.map;

import io.github.chrisruffalo.triedent.map.impl.StringTrieMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class StringTrieMapTest {

    @Test
    void basic() {
        Map<String, String> map = new StringTrieMap<>();
        map.put("c", "value-c");
        map.put("cat", "value-cat");
        Assertions.assertEquals(2, map.size());

        Assertions.assertTrue(map.containsKey("c"));
        Assertions.assertTrue(map.containsKey("cat"));

        Assertions.assertEquals("value-c", map.get("c"));
        Assertions.assertEquals("value-cat", map.get("cat"));

        Assertions.assertTrue(map.containsValue("value-c"));
        Assertions.assertTrue(map.containsValue("value-cat"));
        map.putAll(null);
        map.putAll(Map.of("cargo", "value-cargo", "dork", "value-dork"));
        Assertions.assertEquals(4, map.size());
        Assertions.assertTrue(map.containsKey("cargo"));
        Assertions.assertTrue(map.containsValue("value-dork"));

        Assertions.assertEquals("value-c", map.put("c", "value-update"));
        Assertions.assertFalse(map.containsValue("value-c"));
    }

    /**
     * Test non-ascii/non-english characters
     */
    @Test
    void greek() {
        final StringTrieMap<String> map = new StringTrieMap<>();
        map.put("γαμμα", "αλπηα");
        Assertions.assertEquals("αλπηα", map.put("γαμμα", "βετα"));
        Assertions.assertEquals("βετα", map.get("γαμμα"));
        Assertions.assertNull(map.get("gamma"));
        Assertions.assertNull(map.put("Α", "greek")); // greek capital A (alpha)
        Assertions.assertNull(map.put("A", "english")); // english capital A
        Assertions.assertEquals("english", map.get("A")); // english capital A
        Assertions.assertEquals("greek", map.get("Α")); // greek capital A (alpha)
    }

    @Test
    void symbols() {
        final StringTrieMap<String> map = new StringTrieMap<>();
        map.put(".", "one");
        map.put(".?", "two");
        map.put("\\", "three");
        map.put("..", "four");
        map.put(".\\.\\?.", "five");

        Assertions.assertEquals("one", map.get("."));
        Assertions.assertEquals("two", map.get(".?"));
        Assertions.assertEquals("three", map.get("\\"));
        Assertions.assertEquals("four", map.get(".."));
        Assertions.assertEquals("five", map.get(".\\.\\?."));

        Assertions.assertTrue(map.containsKey("."));
        Assertions.assertTrue(map.containsKey(".?"));
        Assertions.assertTrue(map.containsKey("\\"));
        Assertions.assertTrue(map.containsKey(".."));
        Assertions.assertTrue(map.containsKey(".\\.\\?."));
        Assertions.assertFalse(map.containsKey("?"));
        Assertions.assertFalse(map.containsKey("?."));
    }

}
