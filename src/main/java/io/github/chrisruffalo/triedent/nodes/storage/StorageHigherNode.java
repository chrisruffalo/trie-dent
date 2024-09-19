package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.TerminalHigherNode;

public class StorageHigherNode<TYPE, STORED> extends TerminalHigherNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageHigherNode(TYPE initial) {
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
