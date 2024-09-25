package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalCenterNode<TYPE> extends CenterNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalCenterNode(TYPE initial) {
        super(initial);
    }

}
