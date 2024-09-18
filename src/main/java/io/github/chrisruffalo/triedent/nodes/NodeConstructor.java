package io.github.chrisruffalo.triedent.nodes;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.IndexerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * Think of this as the little spider that scuttles up
 * the "tree" and then makes changes to the node
 * structure as it goes.
 */
public class NodeConstructor<WHOLE, PART> {

    private final IndexerFactory<WHOLE, PART> indexerFactory;
    
    private final NodeFactory<PART> nodeFactory;

    public NodeConstructor(IndexerFactory<WHOLE, PART> indexerFactory) {
        this(new DefaultNodeFactory<>(), indexerFactory);
    }

    public NodeConstructor(NodeFactory<PART> nodeFactory, IndexerFactory<WHOLE, PART> indexerFactory) {
        this.nodeFactory = nodeFactory;
        this.indexerFactory = indexerFactory;
    }

    public RootNode<PART> build() {
        return new RootNode<>(null);
    }

    public boolean insert(RootNode<PART> to, final WHOLE input) {
        final Indexer<WHOLE, PART> indexer = indexerFactory.get(input);
        final AtomicBoolean constructedNew = new AtomicBoolean(false);
        this.insert(to, indexer, 0, constructedNew);
        indexerFactory.release(indexer); // release indexer when done, allows pooling
        return constructedNew.get();
    }

    private Node<PART> insert(Node<PART> to, final Indexer<WHOLE, PART> indexer, int index, AtomicBoolean constructedNew) {
        final PART currentValue = indexer.atIndex(index);

        final Class<?> initialClass = to != null ? to.getClass() : null;
        boolean terminal = indexer.atOrBeyondEnd(index) && currentValue != null;

        // break early if no current value is available, that means we are done walking the tree
        if (currentValue == null) {
            return to;
        }

        if (to == null) {
            nodeFactory.release(to);
            to = nodeFactory.createValueNode(currentValue, terminal);
        }

        if (to.getValue() == null) {
            if (to.isRoot()) {
                final RootNode<PART> root = (RootNode<PART>) to;
                root.setValue(currentValue);
                constructedNew.set(true);
            }
        }

        Node<PART> lower = to.getLower();
        Node<PART> center = to.getCenter();
        Node<PART> higher = to.getHigher();

        // compare current value to decide which way to go
        Direction toGo = indexer.compare(to.getValue(), currentValue);
        if (Direction.CENTER.equals(toGo)) {
            int newIndex = index + 1;
            center = insert(center, indexer, newIndex, constructedNew);
        } else if (Direction.HIGHER.equals(toGo)) {
            higher = insert(higher, indexer, index, constructedNew);
            if (higher != null) {
                terminal = false;
            }
        } else if (Direction.LOWER.equals(toGo)) {
            lower = insert(lower, indexer, index, constructedNew);
            if (lower != null) {
                terminal = false;
            }
        }

        to = transform(to, lower, center, higher, terminal);

        if (terminal && !to.getClass().equals(initialClass)) {
            constructedNew.compareAndSet(false, true);
        }

        return to;
    }

    Node<PART> transform(Node<PART> current, Node<PART> lower, Node<PART> center, Node<PART> higher, boolean shouldBeTerminal) {
        if (current.isRoot() && current instanceof final RootNode<PART> root) {
            if (shouldBeTerminal) {
                root.setTerminal(true);
            }
            root.setLower(lower);
            root.setCenter(center);
            root.setHigher(higher);
            return current;
        }

        final PART value = current.getValue();
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

        if (needsCenter) {
            if (needsHigher && needsLower) {
                current = guard(current, nodeFactory.lowerCenterHigherNodeType(terminal), () -> nodeFactory.createLowerCenterHigherNode(value, terminal));
            } else if (needsLower) {
                current = guard(current, nodeFactory.lowerCenterNodeType(terminal), () -> nodeFactory.createLowerCenterNode(value, terminal));
            } else if (needsHigher) {
                current = guard(current, nodeFactory.centerHigherNodeType(terminal), () -> nodeFactory.createCenterHigherNode(value, terminal));
            } else {
                current = guard(current, nodeFactory.centerNodeType(terminal), () -> nodeFactory.createCenterNode(value, terminal));
            }
        } else {
            if (needsHigher && needsLower) {
                current = guard(current, nodeFactory.lowerHigherNodeType(terminal), () -> nodeFactory.createLowerHigherNode(value, terminal));
            } else if (needsLower) {
                current = guard(current, nodeFactory.lowerNodeType(terminal), () -> nodeFactory.createLowerNode(value, terminal));
            } else if (needsHigher) {
                current = guard(current, nodeFactory.higherNodeType(terminal), () -> nodeFactory.createHigherNode(value, terminal));
            } else {
                current = guard(current, nodeFactory.valueNodeType(terminal), () -> nodeFactory.createValueNode(value, terminal));
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
            nodeFactory.release(instance);
            return supplier.get();
        }
        return instance;
    }
}
