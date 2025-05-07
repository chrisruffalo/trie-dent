package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

public class ThreadLocalIndexerFactory<WHOLE, PART> implements IndexerFactory<WHOLE, PART> {

    private final IndexerFactory<WHOLE, PART> delegate;

    private final ThreadLocal<Indexer<WHOLE, PART>> threadLocal = new ThreadLocal<>();

    public ThreadLocalIndexerFactory(IndexerFactory<WHOLE, PART> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Indexer<WHOLE, PART> get(WHOLE whole) {
        Indexer<WHOLE, PART> threadInstance = threadLocal.get();
        if (threadInstance == null) {
            threadInstance = delegate.get(whole);
            threadLocal.set(threadInstance);
        }
        threadInstance.update(whole);
        return threadInstance;
    }

    @Override
    public void release(Indexer<WHOLE, PART> indexer) {
        delegate.release(indexer);
    }
}
