package io.github.chrisruffalo.triedent.nodes;

public class TerminalValueNode<TYPE> extends ValueNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalValueNode(TYPE initial) {
        super(initial);
    }

}
