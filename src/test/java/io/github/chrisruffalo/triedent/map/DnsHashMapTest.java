package io.github.chrisruffalo.triedent.map;

import io.github.chrisruffalo.triedent.map.impl.DnsHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DnsHashMapTest {

    @Test
    void basic() {
        final DnsHashMap map = new DnsHashMap();
        Assertions.assertNull(map.put("google.com", "alpha"));
        Assertions.assertEquals("alpha", map.get("google.com"));
        Assertions.assertEquals("alpha", map.put("google.com", "bravo"));
        Assertions.assertEquals("bravo", map.put("google.com", "charlie"));
        Assertions.assertNull(map.get("com"));
        Assertions.assertEquals("charlie", map.get("google.com"));
        Assertions.assertEquals(1, map.size());
    }

    @Test
    void domain() {
        final DnsHashMap map = new DnsHashMap();
        map.put("google.com", "google.com");
        map.put("yahoo.com", "yahoo.com");
        Assertions.assertEquals("google.com", map.get("google.com"));
        Assertions.assertEquals("yahoo.com", map.get("yahoo.com"));
    }

    @Test
    void list() {
        final DnsHashMap map = new DnsHashMap();
        final List<String> list = List.of(
            "google.com",
            "bing.com",
            "yahoo.com"
        );
        list.forEach(p -> Assertions.assertNull(map.put(p, p)));
        list.forEach(p -> Assertions.assertEquals(p, map.get(p)));
        list.forEach(p -> Assertions.assertEquals(p, map.put(p, p)));
    }


}
