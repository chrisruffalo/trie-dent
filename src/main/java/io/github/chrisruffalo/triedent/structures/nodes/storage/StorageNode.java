package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.nodes.TerminalNode;

public interface StorageNode<TYPE, STORED> extends TerminalNode<TYPE> {

    STORED getStored();

    default void setStored(STORED stored) {
        // this is a no op unless something is stored
    }
}
