package io.github.chrisruffalo.triedent.nodes;

public class HigherNode<TYPE> extends ValueNode<TYPE> {

    Node<TYPE> higher;

    public HigherNode(TYPE initial) {
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
        return this.higher != null;
    }

    @Override
    public void reset() {
        super.reset();
        this.higher = null;
    }
}
