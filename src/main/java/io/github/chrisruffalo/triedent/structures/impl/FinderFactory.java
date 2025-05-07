package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.nodes.Node;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FinderFactory<WHOLE, PART> {

    private ConcurrentLinkedQueue<Finder<WHOLE, PART>> pool = new ConcurrentLinkedQueue<>();

    public Finder<WHOLE, PART> find(Node<PART> node, Indexer<WHOLE, PART> indexer) {
        Finder<WHOLE, PART> finder = pool.poll();
        if (finder == null) {
            finder = new Finder<>(indexer);
        } else {
            finder.clear(); // aggressive
            finder.setIndexer(indexer);
        }
        node.walk(finder);
        return finder;
    }

    public void release(Finder<WHOLE, PART> finder) {
        if (finder == null) {
            return;
        }
        finder.clear();
        pool.add(finder);
    }

}
