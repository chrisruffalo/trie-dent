package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.TerminalLowerCenterNode;

public class StorageLowerCenterNode<TYPE, STORED> extends TerminalLowerCenterNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageLowerCenterNode(TYPE initial) {
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
