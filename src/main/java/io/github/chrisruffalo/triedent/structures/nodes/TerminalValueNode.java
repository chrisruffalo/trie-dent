package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalValueNode<TYPE> extends ValueNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalValueNode(TYPE initial) {
        super(initial);
    }

}
