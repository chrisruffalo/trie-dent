package io.github.chrisruffalo.triedent.structures.nodes.base;

import io.github.chrisruffalo.triedent.structures.nodes.Node;
import io.github.chrisruffalo.triedent.structures.nodes.NodeFactory;
import io.github.chrisruffalo.triedent.structures.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseConstructor<WHOLE, PART> {

    public abstract NodeFactory<PART> getNodeFactory();

    public abstract IndexerFactory<WHOLE, PART> getIndexerFactory();

    protected boolean insert(RootNode<PART> to, final WHOLE input, Consumer<Node<PART>> afterTerminal) {
        final Indexer<WHOLE, PART> indexer = getIndexerFactory().get(input);
        final AtomicBoolean constructedNew = new AtomicBoolean(false);
        this.insert(to, indexer, 0, constructedNew, afterTerminal);
        getIndexerFactory().release(indexer); // release indexer when done, allows pooling
        return constructedNew.get();
    }

    protected Node<PART> insert(Node<PART> to, final Indexer<WHOLE, PART> indexer, int index, AtomicBoolean constructedNew, Consumer<Node<PART>> afterTerminal) {
        final PART currentValue = indexer.atIndex(index);
        // break early if no current value is available, that means we are done walking the tree
        if (currentValue == null) {
            return to;
        }

        boolean terminal = indexer.atOrBeyondEnd(index);

        if (to == null) {
            getNodeFactory().release(to);
            to = getNodeFactory().createValueNode(currentValue, terminal);
            constructedNew.compareAndSet(false, true);
        }

        if (to instanceof RootNode<PART> root && to.getValue() == null) {
            root.setValue(currentValue);
            if (terminal) {
                constructedNew.compareAndSet(false, true);
                return root;
            }
        }

        Node<PART> lower = to.getLower();
        Node<PART> center = to.getCenter();
        Node<PART> higher = to.getHigher();

        // compare current value to decide which way to go
        final Direction toGo = indexer.compare(to.getValue(), currentValue);
        if (Direction.CENTER.equals(toGo)) {
            int newIndex = index + 1;
            center = insert(center, indexer, newIndex, constructedNew, afterTerminal);
        } else if (Direction.HIGHER.equals(toGo)) {
            higher = insert(higher, indexer, index, constructedNew, afterTerminal);
            if (higher != null) {
                terminal = false;
            }
        } else if (Direction.LOWER.equals(toGo)) {
            lower = insert(lower, indexer, index, constructedNew, afterTerminal);
            if (lower != null) {
                terminal = false;
            }
        }

        Class<?> initialClass = null;
        if (terminal) {
            initialClass = to.getClass();
        }

        to = transform(to, lower, center, higher, terminal);

        if (terminal && !to.getClass().equals(initialClass)) {
            constructedNew.compareAndSet(false, true);
        }

        // if the current node and current string are co-terminal then
        // fire the after-insert update action
        if (terminal && to.isTerminal() && afterTerminal != null) {
            afterTerminal.accept(to);
        }

        return to;
    }

    protected Node<PART> transform(Node<PART> current, Node<PART> lower, Node<PART> center, Node<PART> higher, boolean shouldBeTerminal) {

        final boolean needsLower = lower != null;
        final boolean needsCenter = center != null;
        final boolean needsHigher = higher != null;
        final boolean terminal = current.isTerminal() || shouldBeTerminal;

        // Update the node only if the transformation is required
        if (shouldBeTerminal == current.isTerminal() && current.hasLower() == needsLower && current.hasCenter() == needsCenter && current.hasHigher() == needsHigher) {
            current.setLower(lower);
            current.setCenter(center);
            current.setHigher(higher);
            return current;
        }

        if (current.isRoot()) {
            if (shouldBeTerminal) {
                current.setTerminal(true);
            }
            current.setLower(lower);
            current.setCenter(center);
            current.setHigher(higher);
            return current;
        }

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

        // Set children to the node
        current.setLower(lower);
        current.setCenter(center);
        current.setHigher(higher);
        return current;
    }

    private Node<PART> guard(Node<PART> instance, Class<?> desired, Supplier<Node<PART>> supplier) {
        if (instance == null || !instance.getClass().equals(desired)) {
            getNodeFactory().release(instance);
            return supplier.get();
        }
        return instance;
    }

}
