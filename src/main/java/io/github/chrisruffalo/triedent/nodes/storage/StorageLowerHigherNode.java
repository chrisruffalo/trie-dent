package io.github.chrisruffalo.triedent.nodes.storage;

import io.github.chrisruffalo.triedent.nodes.TerminalLowerHigherNode;

public class StorageLowerHigherNode<TYPE, STORED> extends TerminalLowerHigherNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageLowerHigherNode(TYPE initial) {
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
