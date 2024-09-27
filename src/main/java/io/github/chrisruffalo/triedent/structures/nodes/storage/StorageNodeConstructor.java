package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.IndexerFactory;
import io.github.chrisruffalo.triedent.structures.nodes.Node;
import io.github.chrisruffalo.triedent.structures.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.structures.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.structures.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.nodes.base.BaseConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class StorageNodeConstructor<STORAGE, WHOLE, PART> extends BaseConstructor<WHOLE, PART> {

    final NodeConstructor<WHOLE, PART> internal;

    final NodeFactory<PART> factory;

    final IndexerFactory<WHOLE, PART> indexerFactory;

    final List<AtomicReference<STORAGE>> referencePool = new ArrayList<>(1);

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
        final AtomicReference<STORAGE> valueRef = referencePool.isEmpty() ? new AtomicReference<>() : referencePool.removeFirst();
        this.insert(to, input, node -> {
            if(node == null) {
                return;
            }
            if (node instanceof final StorageNode storageNode) {
                boolean store = true;
                if (preStoreCheck != null && storageNode.getStored() != null) {
                    store = preStoreCheck.apply((StorageNode<PART, STORAGE>) storageNode, (STORAGE)storageNode.getStored());
                }
                valueRef.set((STORAGE)storageNode.getStored());
                if (store) {
                    storageNode.setStored(toStore);
                }
            }
        });
        final STORAGE value = valueRef.get();
        if (referencePool.size() < 10) {
            valueRef.set(null);
            referencePool.addLast(valueRef);
        }
        return value;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Node<PART> transform(Node<PART> current, Node<PART> lower, Node<PART> center, Node<PART> higher, boolean newTerminalState) {
        // grab the stored value if it exists
        STORAGE stored = null;
        if (current instanceof StorageNode storedNode) {
            stored = (STORAGE)storedNode.getStored();
        }
        // perform the normal transform
        final Node<PART> transformed = super.transform(current, lower, center, higher, newTerminalState);
        // copy the stored value to the constructed/transformed node
        if (transformed instanceof StorageNode storageNode) {
            storageNode.setStored(stored);
        }
        // return the transformed node
        return transformed;
    }
}
