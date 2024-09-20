package io.github.chrisruffalo.triedent.set;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.Collector;
import io.github.chrisruffalo.triedent.structures.CollectorFactory;
import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;
import io.github.chrisruffalo.triedent.structures.impl.Counter;
import io.github.chrisruffalo.triedent.structures.impl.Finder;

import java.util.*;

public class TrieSet<WHOLE, PART> implements Set<WHOLE> {

    final IndexerFactory<WHOLE, PART> indexerFactory;

    RootNode<PART> root;

    final NodeConstructor<WHOLE, PART> constructor;

    final NodeFactory<PART> nodeFactory;

    final CollectorFactory<WHOLE, PART> collectorFactory;

    public TrieSet(CollectorFactory<WHOLE, PART> collectorFactory, IndexerFactory<WHOLE, PART> indexerFactory, NodeFactory<PART> nodeFactory) {
        this.indexerFactory = indexerFactory;
        this.collectorFactory = collectorFactory;
        this.nodeFactory = nodeFactory;
        this.constructor = new NodeConstructor<>(this.nodeFactory, this.indexerFactory);
        root = constructor.build();
    }

    @Override
    public int size() {
        return (int)this.root.terminalCount();
    }

    @Override
    public boolean isEmpty() {
        return size() < 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        try {
            WHOLE x = (WHOLE)o;
            Indexer<WHOLE, PART> indexer = indexerFactory.get(x);
            final Finder<WHOLE, PART> finder = Finder.find(root, indexer);
            indexerFactory.release(indexer);
            return finder.matched();
        } catch (ClassCastException ex) {
            return false;
        }
    }

    @Override
    public Iterator<WHOLE> iterator() {
        final Collector<WHOLE, PART> collector = collectorFactory.build();
        final List<List<PART>> parts = root.collect();
        return collector.collect(parts).iterator();
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
    public boolean add(WHOLE whole) {
        return constructor.insert(root, whole);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public void clear() {
        root = constructor.build();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends WHOLE> c) {
        boolean all = true;
        for (WHOLE item : c) {
            // this would look really cool as a one-liner
            // but it is super easy to make a short-circuiting mistake
            // so we don't do that, ask me how i know...
            final boolean added = this.add(item);
            all = added && all;
        }
        return all;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean retainAll(Collection<?> c) {
        List<WHOLE> found = c.stream()
            .map(x -> {
                try {
                    return (WHOLE)x;
                } catch (Exception ex) {
                    return null;
                }})
            .filter(Objects::nonNull)
            .filter(this::contains).toList();
        boolean all = found.size() == c.size();
        root = constructor.build();
        found.forEach(x -> constructor.insert(root, x));
        return all;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
}
