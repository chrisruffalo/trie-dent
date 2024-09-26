package io.github.chrisruffalo.triedent.structures.nodes;

public class LowerHigherNode<TYPE> extends ValueNode<TYPE> {

    Node<TYPE> lower;

    Node<TYPE> higher;

    public LowerHigherNode(TYPE initial) {
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
    public Node<TYPE> getHigher() {
        return higher;
    }

    @Override
    public void setHigher(Node<TYPE> higher) {
        this.higher = higher;
    }

    @Override
    public boolean hasLower() {
        return this.lower != null;
    }

    @Override
    public boolean hasHigher() {
        return this.higher != null;
    }

}
