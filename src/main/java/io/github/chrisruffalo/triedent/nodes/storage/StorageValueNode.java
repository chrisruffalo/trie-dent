package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.TerminalValueNode;

public class StorageValueNode<TYPE, STORED> extends TerminalValueNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageValueNode(TYPE initial) {
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
