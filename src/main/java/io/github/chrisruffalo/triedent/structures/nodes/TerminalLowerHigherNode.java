package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalLowerHigherNode<TYPE> extends LowerHigherNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalLowerHigherNode(TYPE initial) {
        super(initial);
    }

}
