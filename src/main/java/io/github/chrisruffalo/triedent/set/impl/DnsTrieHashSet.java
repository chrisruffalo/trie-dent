package io.github.chrisruffalo.triedent.set.impl;

import io.github.chrisruffalo.triedent.map.impl.DnsHashMap;

import java.util.*;

public class DnsTrieHashSet implements Set<String> {

    final DnsHashMap internal;

    public DnsTrieHashSet() {
        internal = new DnsHashMap();
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
        return !Objects.equals(added, s);
    }

    @Override
    public boolean remove(Object o) {
        return this.internal.remove(o) != null;
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
            added = added && this.add(s);
        }
        return added;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        final List<String> found = c.stream()
                .map(x -> {
                    try {
                        return (String)x;
                    } catch (Exception ex) {
                        return null;
                    }})
                .filter(Objects::nonNull)
                .filter(this::contains).toList();
        boolean all = found.size() == c.size();
        this.clear();
        this.addAll(found);
        return all;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = true;
        for (Object o : c) {
            removed = removed && this.remove(o);
        }
        return removed;
    }

    @Override
    public void clear() {
        this.internal.clear();
    }
}
