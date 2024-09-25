package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.nodes.Node;
import io.github.chrisruffalo.triedent.structures.NodeVisitor;

public class Counter<PART> implements NodeVisitor<PART> {

    private long terminalCount = 0;

    @Override
    public boolean visit(Node<PART> current, int depth) {
        if (current.isTerminal()) {
            terminalCount++;
        }
        return true;
    }

    public void reset() {
        terminalCount = 0;
    }

    public long getCount() {
        return this.terminalCount;
    }
}
