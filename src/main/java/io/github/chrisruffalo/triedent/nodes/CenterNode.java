package io.github.chrisruffalo.triedent.nodes;

public class CenterNode<TYPE> extends ValueNode<TYPE> {

    Node<TYPE> center;

    public CenterNode(TYPE initial) {
        super(initial);
    }

    @Override
    public Node<TYPE> getCenter() {
        return center;
    }

    @Override
    public void setCenter(Node<TYPE> lower) {
        this.center = lower;
    }

    @Override
    public boolean hasCenter() {
        return center != null;
    }

    @Override
    public void reset() {
        super.reset();
        this.center = null;
    }
}
