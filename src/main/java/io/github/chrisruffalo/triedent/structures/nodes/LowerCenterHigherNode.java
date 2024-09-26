package io.github.chrisruffalo.triedent.structures.nodes;

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

}
