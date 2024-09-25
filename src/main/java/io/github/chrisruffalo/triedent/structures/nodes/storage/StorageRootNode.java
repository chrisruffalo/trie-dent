package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.nodes.RootNode;

public class StorageRootNode<TYPE, STORAGE> extends RootNode<TYPE> implements StorageNode<TYPE, STORAGE> {

    STORAGE stored;

    public StorageRootNode(TYPE initial) {
        super(initial);
    }

    @Override
    public STORAGE getStored() {
        return this.stored;
    }

    @Override
    public void setStored(STORAGE storage) {
        this.stored = storage;
    }
}
