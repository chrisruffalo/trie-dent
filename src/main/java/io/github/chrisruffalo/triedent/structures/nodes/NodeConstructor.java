package io.github.chrisruffalo.triedent.structures.nodes;

import io.github.chrisruffalo.triedent.structures.nodes.base.BaseConstructor;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

/**
 * Think of this as the little spider that scuttles up
 * the "tree" and then makes changes to the node
 * structure as it goes.
 */
public class NodeConstructor<WHOLE, PART> extends BaseConstructor<WHOLE, PART> {

    private final IndexerFactory<WHOLE, PART> indexerFactory;
    
    private final NodeFactory<PART> nodeFactory;

    public NodeConstructor(IndexerFactory<WHOLE, PART> indexerFactory) {
        this(new DefaultNodeFactory<>(), indexerFactory);
    }

    public NodeConstructor(NodeFactory<PART> nodeFactory, IndexerFactory<WHOLE, PART> indexerFactory) {
        this.nodeFactory = nodeFactory;
        this.indexerFactory = indexerFactory;
    }

    public RootNode<PART> build() {
        return new RootNode<>(null);
    }

    public boolean insert(RootNode<PART> to, final WHOLE input) {
        return this.insert(to, input, null);
    }


    @Override
    public NodeFactory<PART> getNodeFactory() {
        return this.nodeFactory;
    }

    @Override
    public IndexerFactory<WHOLE, PART> getIndexerFactory() {
        return this.indexerFactory;
    }
}
