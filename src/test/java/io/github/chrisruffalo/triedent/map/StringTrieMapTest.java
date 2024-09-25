package io.github.chrisruffalo.triedent.map;

import io.github.chrisruffalo.triedent.map.impl.StringTrieMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StringTrieMapTest {

    @Test
    public void basic() {
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

}
