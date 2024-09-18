package io.github.chrisruffalo.triedent.nodes;

public class TerminalLowerCenterNode<TYPE> extends LowerCenterNode<TYPE> implements TerminalNode<TYPE> {

    public TerminalLowerCenterNode(TYPE initial) {
        super(initial);
    }
    
}
