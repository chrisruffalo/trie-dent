package io.github.chrisruffalo.triedent.structures.nodes;

public class RootNode<TYPE> extends LowerCenterHigherNode<TYPE> {

    boolean terminal = false;

    public RootNode(TYPE initial) {
        super(initial);
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

}
