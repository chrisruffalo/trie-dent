package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.nodes.TerminalCenterHigherNode;

public class StorageCenterHigherNode<TYPE, STORED> extends TerminalCenterHigherNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageCenterHigherNode(TYPE initial) {
        super(initial);
    }

    @Override
    public STORED getStored() {
        return stored;
    }

    @Override
    public void setStored(STORED stored) {
        this.stored = stored;
    }
}
