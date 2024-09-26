# Trie-Dent

## Purpose
To create memory-efficient TSTs for use in Java.

## Abstract
For several years, and through working on various java projects, I have been somewhat obsessed with finding a memory-compact
way to store a set of domain names that would be useful for working with block/allow lists or similar problems where there
is so much repeated string data.

This project takes a novel (read: crazy) approach to managing the branching of a TST ([ternary search trie](https://en.wikipedia.org/wiki/Ternary_search_tree))
through polymorphism. It also allows for customization of splitting so that something like a DNS splitter can be
written to use DNS labels as nodes in the tree instead of single `char` values.

The underlying structure is a tree that is created with only the branches and objects it needs to represent each node. It 
makes as little use as possible of mutable values and tries to make do with polymorphism for things like determining which 
branches are active or if the node is terminal or not.

The result is a set of implementations that _mostly_ map to the expected map/set interfaces with some caveats. This
project is mainly intended as the backbone for a DNS storage structure to be used by [pintle](https://github.com/chrisruffalo/pintle) 
for allow and block lists that may run into the millions of entries.

## Use
The use is very much like a basic set or map:

```java
import io.github.chrisruffalo.triedent.set.TrieSet;
import io.github.chrisruffalo.triedent.set.impl.StringTrieSet;

// simple example method to show how this should work
public void example() {
    // works as a set for the storage of unique values
    final Set<String> set = new StringTrieSet();
    set.add("john");
    set.add("mary");
    set.add("steve");
    assert (set.contains("steve"));
    assert (3 == set.size());

    // stores strings using the trie and the value type is stored with
    // that entry.
    final Map<String, String> map = new StringTrieMap<String>();
    map.put("cats", "food");
    assert ("food".equals(map.get("cats")));
    assert (1 == map.size());
    assert (!map.isEmpty());
}
```

## Rationale
As mentioned in the Abstract this repository implements a data structure that uses a novel (crazy) approach to creating
a tree by using polymorphic elision of data fields. What the term "polymorphic elision" means in this case is that instead
of every node being of the type Node with fields for each property each node has a distinct type depending on the structure. 
While this does necessitate a more complex creation algorithm and result in more allocations initially the final result
is that nodes with less mutable information take fewer bytes. A TerminalValueNode has a representation that takes 16B where
a TerminalLowerCenterHigherNode has a representational cost of 32B. The Root node, which has a structure that would
be required for every node if they were all the same, takes 40B to represent. In small trees this would not matter and
is fairly detrimental to code maintainability and readability so this will only matter on large data sets.

There are two different measures of memory that are important to this data structure and its use. The first is the number
of allocations required to build the structure and the second is the retained size of the structure. The starting point
for this approach was to reduce the retained size to the minimum and then work towards reducing allocations.

## Performance
In testing a HashSet and a DnsHashTrieSet were both loaded with 1,000,000 domain names. The HashSet is much faster than
the DnsHashTrieSet and, as expected, also allocates less memory during creation. However, the retained size of the
DnsHashTrieSet is about 35% of the HashSet's retained size.

The `million()` method of the `HashSetTest`, `StringTrieSetTest`, and `DnsTrieHashSetTest` was used to collect data for this comparison. A `System.gc()`
call was made before taking a snapshot after loading the domain names. The time was measured without profiling.

| Implementation | Time (ms) | Allocations (MB) | Retained (MB)  |
| - |-----------|------------------|----------------|
|HashSet| 372       | 21.38            | 108.98         |
|DnsHashTrieSet| 2131      | 251.27           | 69.92          |
|StringTrieSet| 2441      | 1085             | 403.22         |

As you can see from the above case the DnsTrieHashSet only makes sense in the event that you can spare the allocation
pressure for creating it and the retained size savings is worthwhile.