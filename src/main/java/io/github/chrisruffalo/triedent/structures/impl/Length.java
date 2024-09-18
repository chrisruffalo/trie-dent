package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.nodes.Node;
import io.github.chrisruffalo.triedent.structures.NodeVisitor;

public class Length<PART> implements NodeVisitor<PART> {

    int length = 0;

    @Override
    public boolean visit(Node<PART> current, int depth) {
        if (current.getValue() != null) {
            length = Math.max(length, depth + 1);
        }
        return true;
    }

    public int getLength() {
        return length;
    }
}
