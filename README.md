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

The result is a set of implementations that _mostly_ map to the expected map/set interfaces with some caveats.

## Use
The use is very much like a basic set:

```java
import io.github.chrisruffalo.triedent.set.TrieSet;
import io.github.chrisruffalo.triedent.set.impl.StringTrieSet;

final Set<String> set = new StringTrieSet();
set.add("john");
set.add("mary");
set.add("steve");
assert(set.contains("steve"));
```