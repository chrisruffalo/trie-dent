package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.nodes.TerminalLowerCenterHigherNode;

public class StorageLowerCenterHigherNode<TYPE, STORED> extends TerminalLowerCenterHigherNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageLowerCenterHigherNode(TYPE initial) {
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
