package io.github.chrisruffalo.triedent.structures.nodes;

public class TerminalLowerCenterNode<TYPE> extends LowerCenterNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalLowerCenterNode(TYPE initial) {
        super(initial);
    }
    
}
