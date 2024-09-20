package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.nodes.base.BaseConstructor;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class StorageNodeConstructor<STORAGE, WHOLE, PART> extends BaseConstructor<WHOLE, PART> {

    final NodeConstructor<WHOLE, PART> internal;

    final NodeFactory<PART> factory;

    final IndexerFactory<WHOLE, PART> indexerFactory;

    public StorageNodeConstructor(IndexerFactory<WHOLE, PART> indexerFactory) {
        this.factory =  new StorageNodeFactory<>();
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

    public STORAGE insert(RootNode<PART> to, final WHOLE input, final STORAGE toStore) {
        return this.insert(to, input, toStore, null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public STORAGE insert(RootNode<PART> to, final WHOLE input, final STORAGE toStore, final BiFunction<StorageNode<PART, STORAGE>, STORAGE, Boolean> preStoreCheck) {
        final AtomicReference<STORAGE> valueRef = new AtomicReference<>();
        this.insert(to, input, node -> {
            if(node == null) {
                return;
            }
            if (node instanceof final StorageNode storageNode) {
                boolean store = true;
                if (preStoreCheck != null && storageNode.getStored() != null) {
                    store = preStoreCheck.apply((StorageNode<PART, STORAGE>) storageNode, (STORAGE)storageNode.getStored());
                }
                if (store) {
                    storageNode.setStored(toStore);
                    valueRef.set((STORAGE)storageNode.getStored());
                }
            }
        });
        if (valueRef.get() != null) {
            return valueRef.get();
        }
        return null;
    }


}
