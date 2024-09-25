package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalHigherNode<TYPE> extends HigherNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalHigherNode(TYPE initial) {
        super(initial);
    }

}
