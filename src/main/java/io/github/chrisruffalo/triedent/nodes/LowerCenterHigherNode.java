package io.github.chrisruffalo.triedent.nodes;

public class LowerCenterHigherNode<TYPE> extends LowerCenterNode<TYPE> {

    Node<TYPE> higher;

    public LowerCenterHigherNode(TYPE initial) {
        super(initial);
    }

    @Override
    public Node<TYPE> getHigher() {
        return higher;
    }

    @Override
    public void setHigher(Node<TYPE> higher) {
        this.higher = higher;
    }

    @Override
    public boolean hasHigher() {
        return higher != null;
    }

    @Override
    public void reset() {
        super.reset();
        this.higher = null;
    }
}
