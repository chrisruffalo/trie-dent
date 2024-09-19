package io.github.chrisruffalo.triedent.map;

import io.github.chrisruffalo.triedent.nodes.Node;
import io.github.chrisruffalo.triedent.nodes.storage.StorageNode;
import io.github.chrisruffalo.triedent.nodes.storage.StorageNodeConstructor;
import io.github.chrisruffalo.triedent.nodes.storage.StorageRootNode;
import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;
import io.github.chrisruffalo.triedent.structures.impl.Finder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrieMap<WHOLE, PART, STORAGE> implements Map<WHOLE, STORAGE> {

    final IndexerFactory<WHOLE, PART> indexerFactory;

    final StorageNodeConstructor<STORAGE, WHOLE, PART> constructor;

    StorageRootNode<PART, STORAGE> root;

    public TrieMap(IndexerFactory<WHOLE, PART> indexerFactory) {
        this.indexerFactory = indexerFactory;
        this.constructor = new StorageNodeConstructor<>(this.indexerFactory);
        this.root = constructor.build();
    }

    @Override
    public int size() {
        return this.root.collect().size();
    }

    @Override
    public boolean isEmpty() {
        return this.root.collect().isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object key) {
        try {
            WHOLE x = (WHOLE)key;
            Indexer<WHOLE, PART> indexer = indexerFactory.get(x);
            final Finder<WHOLE, PART> finder = Finder.find(root, indexer);
            indexerFactory.release(indexer);
            return finder.matched();
        } catch (ClassCastException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public STORAGE get(Object key) {
        try {
            WHOLE x = (WHOLE)key;
            Indexer<WHOLE, PART> indexer = indexerFactory.get(x);
            final Finder<WHOLE, PART> finder = Finder.find(root, indexer);
            indexerFactory.release(indexer);
            if (finder.matched()) {
                final Node<PART> part = finder.getPath().getLast();
                if (part instanceof StorageNode storageNode) {
                    return (STORAGE)storageNode.getStored();
                }
            }
        } catch (ClassCastException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public STORAGE put(WHOLE key, STORAGE value) {
        if(constructor.insert(root, key, value)) {
            return value;
        }
        return null;
    }

    @Override
    public STORAGE remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends WHOLE, ? extends STORAGE> m) {

    }

    @Override
    public void clear() {
        this.root = constructor.build();
    }

    @Override
    public Set<WHOLE> keySet() {
        return Set.of();
    }

    @Override
    public Collection<STORAGE> values() {
        return List.of();
    }

    @Override
    public Set<Entry<WHOLE, STORAGE>> entrySet() {
        return Set.of();
    }
}
