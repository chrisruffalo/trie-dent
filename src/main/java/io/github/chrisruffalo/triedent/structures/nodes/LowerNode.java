package io.github.chrisruffalo.triedent.structures.nodes;

public class LowerNode<TYPE> extends ValueNode<TYPE> {

    Node<TYPE> lower;

    public LowerNode(TYPE initial) {
        super(initial);
    }

    @Override
    public void setLower(Node<TYPE> lower) {
        this.lower = lower;
    }

    @Override
    public Node<TYPE> getLower() {
        return lower;
    }

    @Override
    public boolean hasLower() {
        return this.lower != null;
    }

}
