package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalLowerNode<TYPE> extends LowerNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalLowerNode(TYPE initial) {
        super(initial);
    }
}
