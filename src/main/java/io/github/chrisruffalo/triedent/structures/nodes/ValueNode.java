package io.github.chrisruffalo.triedent.structures.nodes;

/**
 * This should be considered the base type for any nodes
 * used to build the tree. The value they contain determines
 * which way any tree followers/walkers should go to continue
 * finding the rest of the value
 *
 * @param <TYPE> to contain
 */
public class ValueNode<TYPE> implements Node<TYPE> {

    TYPE value;

    public ValueNode(TYPE initial) {
        this.value = initial;
    }

    @Override
    public TYPE getValue() {
        return value;
    }

    @Override
    public void setValue(TYPE value) {
        this.value = value;
    }

    @Override
    public void setHigher(Node<TYPE> node) {

    }

    @Override
    public void setLower(Node<TYPE> node) {

    }

    @Override
    public void setCenter(Node<TYPE> node) {

    }
}
