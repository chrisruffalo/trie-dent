package io.github.chrisruffalo.triedent.nodes;

public class TerminalCenterHigherNode<TYPE> extends CenterHigherNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalCenterHigherNode(TYPE initial) {
        super(initial);
    }

}
