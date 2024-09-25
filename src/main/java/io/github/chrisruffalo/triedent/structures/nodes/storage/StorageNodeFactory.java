package io.github.chrisruffalo.triedent.structures.nodes.storage;

import io.github.chrisruffalo.triedent.structures.nodes.*;

public class StorageNodeFactory<TYPE> extends DefaultNodeFactory<TYPE> {

    @Override
    public Class<?> lowerNodeType(boolean terminal) {
        return terminal ? StorageLowerNode.class : LowerNode.class;
    }

    @Override
    public Node<TYPE> createLowerNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageLowerNode<>(value);
        }
        return new LowerNode<>(value);
    }

    @Override
    public Class<?> higherNodeType(boolean terminal) {
        return terminal ? StorageHigherNode.class : HigherNode.class;
    }

    @Override
    public Node<TYPE> createHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageHigherNode<>(value);
        }
        return new HigherNode<>(value);
    }

    @Override
    public Class<?> lowerHigherNodeType(boolean terminal) {
        return terminal ? StorageLowerHigherNode.class : LowerHigherNode.class;
    }

    @Override
    public Node<TYPE> createLowerHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageLowerHigherNode<>(value);
        }
        return new LowerHigherNode<>(value);
    }

    @Override
    public Class<?> valueNodeType(boolean terminal) {
        return terminal ? StorageValueNode.class : ValueNode.class;
    }

    @Override
    public Node<TYPE> createValueNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageValueNode<>(value);
        }
        return new ValueNode<>(value);
    }

    @Override
    public Class<?> centerNodeType(boolean terminal) {
        return terminal ? StorageCenterNode.class : CenterNode.class;
    }

    @Override
    public Node<TYPE> createCenterNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageCenterNode<>(value);
        }
        return new CenterNode<>(value);
    }

    @Override
    public Class<?> lowerCenterNodeType(boolean terminal) {
        return terminal ? StorageLowerCenterNode.class : LowerCenterNode.class;
    }

    @Override
    public Node<TYPE> createLowerCenterNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageLowerCenterNode<>(value);
        }
        return new LowerCenterNode<>(value);
    }

    @Override
    public Class<?> centerHigherNodeType(boolean terminal) {
        return terminal ? StorageCenterHigherNode.class : CenterHigherNode.class;
    }

    @Override
    public Node<TYPE> createCenterHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageCenterHigherNode<>(value);
        }
        return new CenterHigherNode<>(value);
    }

    @Override
    public Class<?> lowerCenterHigherNodeType(boolean terminal) {
        return terminal ? StorageLowerCenterHigherNode.class : LowerCenterHigherNode.class;
    }

    @Override
    public Node<TYPE> createLowerCenterHigherNode(TYPE value, boolean terminal) {
        if (terminal) {
            return new StorageLowerCenterHigherNode<>(value);
        }
        return new LowerCenterHigherNode<>(value);
    }

}
