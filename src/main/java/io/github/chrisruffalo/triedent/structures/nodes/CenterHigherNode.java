package io.github.chrisruffalo.triedent.structures.nodes;

public class CenterHigherNode<TYPE> extends CenterNode<TYPE> {

    Node<TYPE> higher;

    public CenterHigherNode(TYPE initial) {
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
}
