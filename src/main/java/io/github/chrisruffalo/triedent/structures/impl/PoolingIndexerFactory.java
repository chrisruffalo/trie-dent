package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

import java.util.ArrayList;
import java.util.List;

public class PoolingIndexerFactory<WHOLE, PART> implements IndexerFactory<WHOLE, PART> {

    private final IndexerFactory<WHOLE, PART> delegate;

    private final List<Indexer<WHOLE,PART>> pool = new ArrayList<>(1);

    public PoolingIndexerFactory(IndexerFactory<WHOLE, PART> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Indexer<WHOLE, PART> get(WHOLE whole) {
        if (!pool.isEmpty()) {
            Indexer<WHOLE, PART> first = pool.removeFirst();
            first.update(whole);
            return first;
        }
        return delegate.get(whole);
    }

    @Override
    public void release(Indexer<WHOLE, PART> indexer) {
        pool.addLast(indexer);
    }
}
