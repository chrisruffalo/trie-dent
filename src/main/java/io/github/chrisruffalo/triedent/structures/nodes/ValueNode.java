package io.github.chrisruffalo.triedent.structures.nodes;

/**
 * Some nodes are not capable of holding a value.
 *
 * @param <TYPE> to contain
 */
public class ValueNode<TYPE> extends EmptyNode<TYPE> {

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
    public void reset() {
        this.value = null;
    }
}
