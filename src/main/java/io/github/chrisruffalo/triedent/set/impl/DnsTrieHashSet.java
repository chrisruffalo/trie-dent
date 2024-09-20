package io.github.chrisruffalo.triedent.set.impl;

import io.github.chrisruffalo.triedent.map.TrieMap;
import io.github.chrisruffalo.triedent.structures.impl.dns.DnsHashIndexerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class DnsTrieHashSet implements Set<String> {

    final TrieMap<String, Number, String> internal;

    public DnsTrieHashSet() {
        internal = new TrieMap<>(new DnsHashIndexerFactory());
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof String)) {
            return false;
        }
        return internal.containsKey(o);
    }

    @Override
    public Iterator<String> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(String s) {
        String added = internal.put(s, s);
        return Objects.equals(added, s);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean added = true;
        for (String s : c) {
            added = internal.put(s, s) != null && added;
        }
        return added;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        this.internal.clear();
    }
}
