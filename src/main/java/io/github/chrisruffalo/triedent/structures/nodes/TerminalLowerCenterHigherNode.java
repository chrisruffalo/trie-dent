package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalLowerCenterHigherNode<TYPE> extends LowerCenterHigherNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalLowerCenterHigherNode(TYPE initial) {
        super(initial);
    }

}
