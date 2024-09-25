package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.nodes.TerminalLowerNode;

public class StorageLowerNode<TYPE, STORED> extends TerminalLowerNode<TYPE> implements StorageNode<TYPE, STORED> {

    STORED stored;

    public StorageLowerNode(TYPE initial) {
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
