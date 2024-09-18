package io.github.chrisruffalo.triedent.nodes;

/**
 * In this type all references are replaced
 * with constant values. This is the base
 * of what prevents each node from having to
 * carry around references that are empty.
 *
 * @param <TYPE>
 */
public class EmptyNode<TYPE> implements Node<TYPE> {

    @Override
    public Node<TYPE> getLower() {
        return null;
    }

    @Override
    public Node<TYPE> getCenter() {
        return null;
    }

    @Override
    public Node<TYPE> getHigher() {
        return null;
    }

    @Override
    public TYPE getValue() {
        return null;
    }

    @Override
    public void reset() {
        // nothing to do here
        this.setValue(null);
        this.setCenter(null);
        this.setHigher(null);
        this.setLower(null);
    }

    @Override
    public boolean hasLower() {
        return false;
    }

    @Override
    public boolean hasCenter() {
        return false;
    }

    @Override
    public boolean hasHigher() {
        return false;
    }
}
