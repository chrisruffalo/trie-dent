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

        Assertions.assertTrue(map.containsKey("c"));
        Assertions.assertTrue(map.containsKey("cat"));

        Assertions.assertEquals("value-c", map.get("c"));
        Assertions.assertEquals("value-cat", map.get("cat"));
    }

}
