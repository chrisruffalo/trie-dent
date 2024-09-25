package io.github.chrisruffalo.triedent.structures.nodes;

public class DefaultNodeFactory<TYPE> implements NodeFactory<TYPE>{

    @Override
    public Class<?> lowerNodeType(boolean terminal) {
        return terminal ? TerminalLowerNode.class : LowerNode.class;
    }

    @Override
    public Node<TYPE> createLowerNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalLowerNode<>(value);
        }
        return new LowerNode<>(value);
    }

    @Override
    public Class<?> higherNodeType(boolean terminal) {
        return terminal ? TerminalHigherNode.class : HigherNode.class;
    }

    @Override
    public Node<TYPE> createHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalHigherNode<>(value);
        }
        return new HigherNode<>(value);
    }

    @Override
    public Class<?> lowerHigherNodeType(boolean terminal) {
        return terminal ? TerminalLowerHigherNode.class : LowerHigherNode.class;
    }

    @Override
    public Node<TYPE> createLowerHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalLowerHigherNode<>(value);
        }
        return new LowerHigherNode<>(value);
    }

    @Override
    public Class<?> valueNodeType(boolean terminal) {
        return terminal ? TerminalValueNode.class : ValueNode.class;
    }

    @Override
    public Node<TYPE> createValueNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalValueNode<>(value);
        }
        return new ValueNode<>(value);
    }

    @Override
    public Class<?> centerNodeType(boolean terminal) {
        return terminal ? TerminalCenterNode.class : CenterNode.class;
    }

    @Override
    public Node<TYPE> createCenterNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalCenterNode<>(value);
        }
        return new CenterNode<>(value);
    }

    @Override
    public Class<?> lowerCenterNodeType(boolean terminal) {
        return terminal ? TerminalLowerCenterNode.class : LowerCenterNode.class;
    }

    @Override
    public Node<TYPE> createLowerCenterNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalLowerCenterNode<>(value);
        }
        return new LowerCenterNode<>(value);
    }

    @Override
    public Class<?> centerHigherNodeType(boolean terminal) {
        return terminal ? TerminalCenterHigherNode.class : CenterHigherNode.class;
    }

    @Override
    public Node<TYPE> createCenterHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalCenterHigherNode<>(value);
        }
        return new CenterHigherNode<>(value);
    }

    @Override
    public Class<?> lowerCenterHigherNodeType(boolean terminal) {
        return terminal ? TerminalLowerCenterHigherNode.class : LowerCenterHigherNode.class;
    }

    @Override
    public Node<TYPE> createLowerCenterHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new TerminalLowerCenterHigherNode<>(value);
        }
        return new LowerCenterHigherNode<>(value);
    }

    @Override
    public void release(Node<TYPE> node) {

    }
}
