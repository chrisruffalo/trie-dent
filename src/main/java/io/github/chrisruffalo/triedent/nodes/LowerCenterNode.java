package io.github.chrisruffalo.triedent.nodes;

public class LowerCenterNode<TYPE> extends CenterNode<TYPE> {

    Node<TYPE> lower;

    public LowerCenterNode(TYPE initial) {
        super(initial);
    }

    @Override
    public Node<TYPE> getLower() {
        return lower;
    }

    @Override
    public void setLower(Node<TYPE> lower) {
        this.lower = lower;
    }

    @Override
    public boolean hasLower() {
        return lower != null;
    }

    @Override
    public void reset() {
        super.reset();
        this.lower = null;
    }
}
