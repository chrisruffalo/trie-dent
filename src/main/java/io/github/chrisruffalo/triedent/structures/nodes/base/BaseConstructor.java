package io.github.chrisruffalo.triedent.structures.nodes.base;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;
import io.github.chrisruffalo.triedent.structures.nodes.Node;
import io.github.chrisruffalo.triedent.structures.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.structures.nodes.RootNode;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseConstructor<WHOLE, PART> {

    public abstract NodeFactory<PART> getNodeFactory();

    public abstract IndexerFactory<WHOLE, PART> getIndexerFactory();

    protected boolean insert(RootNode<PART> to, final WHOLE input, Consumer<Node<PART>> afterTerminal) {
        final Indexer<WHOLE, PART> indexer = getIndexerFactory().get(input);
        final AtomicBoolean constructedNew = new AtomicBoolean(false);

        // handle root node value/consideration here and remove it
        // from the path of each insert recursion
        if (to.getValue() == null) {
            to.setValue(indexer.atIndex(0));
            if (indexer.atOrBeyondEnd(0)) {
                to.setTerminal(true);
            }
        }

        this.insert(true, to, indexer, 0, constructedNew, afterTerminal);
        getIndexerFactory().release(indexer); // release indexer when done, allows pooling
        return constructedNew.get();
    }

    protected Node<PART> insert(boolean root, Node<PART> to, final Indexer<WHOLE, PART> indexer, int index, AtomicBoolean constructedNew, Consumer<Node<PART>> afterTerminal) {

        final PART currentValue = indexer.atIndex(index);
        // break early if no current value is available, that means we are done walking the tree
        if (currentValue == null) {
            return to;
        }

        boolean terminal = indexer.atOrBeyondEnd(index);

        Node<PART> lower;
        Node<PART> center;
        Node<PART> higher;

        final Direction toGo;
        if (to == null) {
            to = getNodeFactory().createValueNode(currentValue, terminal);
            if (terminal) {
                constructedNew.compareAndSet(false, true);
            }
            toGo = Direction.CENTER;
            lower = null;
            center = null;
            higher = null;
        } else {
            toGo = indexer.compare(to.getValue(), currentValue);
            lower = to.getLower();
            center = to.getCenter();
            higher = to.getHigher();
        }

        // based on the path between the current value in the indexer and the current
        // value of the "to" node decide which way to navigate the tree
        if (Direction.CENTER.equals(toGo)) {
            int newIndex = index + 1;
            center = insert(false, center, indexer, newIndex, constructedNew, afterTerminal);
        } else if (Direction.HIGHER.equals(toGo)) {
            higher = insert(false, higher, indexer, index, constructedNew, afterTerminal);
            terminal = false; // in higher or lower this means we took a separate
                              // path and even though the _index_ may be the
                              // terminal index it is not this node that should
                              // be considered terminal
        } else if (Direction.LOWER.equals(toGo)) {
            lower = insert(false, lower, indexer, index, constructedNew, afterTerminal);
            terminal = false; // see above comment
        }

        Class<?> initialClass = null;
        if (terminal) {
            initialClass = to.getClass();
        }

        if (!root) {
            to = transform(to, lower, center, higher, terminal);
        }

        // after transform ensure that the created node has the same contents
        // as it should before the transform
        to.setLower(lower);
        to.setCenter(center);
        to.setHigher(higher);

        if (terminal && !to.getClass().equals(initialClass)) {
            constructedNew.compareAndSet(false, true);
        }

        // if the current node and current string are co-terminal then
        // fire the after-insert update action
        if (terminal && afterTerminal != null && to.isTerminal()) {
            afterTerminal.accept(to);
        }

        return to;
    }

    protected Node<PART> transform(Node<PART> current, Node<PART> lower, Node<PART> center, Node<PART> higher, boolean newTerminalState) {

        final boolean needsLower = lower != null;
        final boolean needsCenter = center != null;
        final boolean needsHigher = higher != null;
        final boolean terminal = current.shouldBeTerminal(newTerminalState);

        final PART value = current.getValue();
        if (needsCenter) {
            if (needsHigher && needsLower) {
                current = guard(current, getNodeFactory().lowerCenterHigherNodeType(terminal), () -> getNodeFactory().createLowerCenterHigherNode(value, terminal));
            } else if (needsLower) {
                current = guard(current, getNodeFactory().lowerCenterNodeType(terminal), () -> getNodeFactory().createLowerCenterNode(value, terminal));
            } else if (needsHigher) {
                current = guard(current, getNodeFactory().centerHigherNodeType(terminal), () -> getNodeFactory().createCenterHigherNode(value, terminal));
            } else {
                current = guard(current, getNodeFactory().centerNodeType(terminal), () -> getNodeFactory().createCenterNode(value, terminal));
            }
        } else {
            if (needsHigher && needsLower) {
                current = guard(current, getNodeFactory().lowerHigherNodeType(terminal), () -> getNodeFactory().createLowerHigherNode(value, terminal));
            } else if (needsLower) {
                current = guard(current, getNodeFactory().lowerNodeType(terminal), () -> getNodeFactory().createLowerNode(value, terminal));
            } else if (needsHigher) {
                current = guard(current, getNodeFactory().higherNodeType(terminal), () -> getNodeFactory().createHigherNode(value, terminal));
            } else {
                current = guard(current, getNodeFactory().valueNodeType(terminal), () -> getNodeFactory().createValueNode(value, terminal));
            }
        }

        return current;
    }

    private Node<PART> guard(Node<PART> instance, Class<?> desired, Supplier<Node<PART>> supplier) {
        if (instance == null || !instance.getClass().equals(desired)) {
            return supplier.get();
        }
        return instance;
    }

}
