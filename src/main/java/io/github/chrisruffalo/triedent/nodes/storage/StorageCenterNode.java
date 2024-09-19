package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.TerminalCenterNode;

public class StorageCenterNode<TYPE, STORED> extends TerminalCenterNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageCenterNode(TYPE initial) {
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
