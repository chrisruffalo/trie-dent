package io.github.chrisruffalo.triedent.structures.nodes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * This is _probably_ a bad idea because we end up with a lot more allocations
 * and memory hanging around when it isn't necessary. Leaving this here
 * as an example and scaffold for future work if it needs to be done.
 *
 * @param <TYPE> that will be stored in the constructed nodes
 */
public class PoolingNodeFactory<TYPE> implements NodeFactory<TYPE> {

    NodeFactory<TYPE> delegate;

    private final HashMap<Class<?>, List<Node<TYPE>> >pool = new HashMap<>();

    public PoolingNodeFactory(NodeFactory<TYPE> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Class<?> valueNodeType(boolean terminal) {
        return delegate.valueNodeType(terminal);
    }

    @Override
    public Node<TYPE> createValueNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.valueNodeType(terminal), value, () -> delegate.createValueNode(value, terminal));
    }

    @Override
    public Class<?> centerNodeType(boolean terminal) {
        return delegate.centerNodeType(terminal);
    }

    @Override
    public Node<TYPE> createCenterNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.centerNodeType(terminal), value, () -> delegate.createCenterNode(value, terminal));
    }

    @Override
    public Class<?> lowerNodeType(boolean terminal) {
        return delegate.lowerNodeType(terminal);
    }

    @Override
    public Node<TYPE> createLowerNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.lowerNodeType(terminal), value, () -> delegate.createLowerNode(value, terminal));
    }

    @Override
    public Class<?> higherNodeType(boolean terminal) {
        return delegate.higherNodeType(terminal);
    }

    @Override
    public Node<TYPE> createHigherNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.higherNodeType(terminal), value, () -> delegate.createHigherNode(value, terminal));
    }

    @Override
    public Class<?> lowerCenterNodeType(boolean terminal) {
        return delegate.lowerCenterNodeType(terminal);
    }

    @Override
    public Node<TYPE> createLowerCenterNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.lowerCenterNodeType(terminal), value, () -> delegate.createLowerCenterNode(value, terminal));
    }

    @Override
    public Class<?> centerHigherNodeType(boolean terminal) {
        return delegate.centerHigherNodeType(terminal);
    }

    @Override
    public Node<TYPE> createCenterHigherNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.centerHigherNodeType(terminal), value, () -> delegate.createCenterHigherNode(value, terminal));
    }

    @Override
    public Class<?> lowerHigherNodeType(boolean terminal) {
        return delegate.lowerHigherNodeType(terminal);
    }

    @Override
    public Node<TYPE> createLowerHigherNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.lowerHigherNodeType(terminal), value, () -> delegate.createLowerHigherNode(value, terminal));
    }

    @Override
    public Class<?> lowerCenterHigherNodeType(boolean terminal) {
        return delegate.lowerCenterHigherNodeType(terminal);
    }

    @Override
    public Node<TYPE> createLowerCenterHigherNode(TYPE value, boolean terminal) {
        return getOrConstruct(delegate.lowerCenterHigherNodeType(terminal), value, () -> delegate.createLowerCenterHigherNode(value, terminal));
    }

    @Override
    public void release(Node<TYPE> node) {
        // do nothing with null nodes
        if (node == null) {
            return;
        }

        // reset the node
        node.reset();

        // return to the pool
        final List<Node<TYPE>> options = pool.computeIfAbsent(node.getClass(), k -> new LinkedList<>());
        options.add(node);
    }

    private Node<TYPE> getOrConstruct(Class<?> nodeClass, TYPE value, Supplier<Node<TYPE>> supplier) {
        final List<Node<TYPE>> options = pool.get(nodeClass);
        Node<TYPE> pooled = null;
        if (options == null || options.isEmpty()) {
            pooled = supplier.get();
        } else {
            do {
                pooled = options.removeFirst();
                if (pooled != null) {
                    pooled.setValue(value);
                    break;
                }
            } while (!options.isEmpty());
            if (pooled == null) {
                pooled = supplier.get();
            }
        }
        return pooled;
    }
}
