package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.nodes.base.BaseConstructor;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

public class StorageNodeConstructor<STORAGE, WHOLE, PART> extends BaseConstructor {

    final NodeConstructor<WHOLE, PART> internal;

    final StorageNodeFactory<PART, STORAGE> factory;

    final IndexerFactory<WHOLE, PART> indexerFactory;

    public StorageNodeConstructor(IndexerFactory<WHOLE, PART> indexerFactory) {
        this.factory = new StorageNodeFactory<>();
        this.indexerFactory = indexerFactory;
        this.internal = new NodeConstructor<>(factory, indexerFactory);
    }

    @Override
    public NodeFactory<PART> getNodeFactory() {
        return this.factory;
    }

    @Override
    public IndexerFactory<WHOLE, PART> getIndexerFactory() {
        return this.indexerFactory;
    }

    public StorageRootNode<PART, STORAGE> build() {
        return new StorageRootNode<>(null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean insert(RootNode<PART> to, final WHOLE input, final STORAGE toStore) {
        return this.insert(to, input, node -> {
            if(node == null) {
                return;
            }
            if (node instanceof final StorageNode storageNode) {
                storageNode.setStored(toStore);
            }
        });
    }

}
