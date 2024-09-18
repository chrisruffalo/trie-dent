package io.github.chrisruffalo.triedent.nodes;

public interface NodeFactory<TYPE> {

    Class<?> valueNodeType(boolean terminal);

    Node<TYPE> createValueNode(TYPE value, boolean terminal);

    Class<?> centerNodeType(boolean terminal);

    Node<TYPE> createCenterNode(TYPE value, boolean terminal);

    Class<?> lowerNodeType(boolean terminal);

    Node<TYPE> createLowerNode(TYPE value, boolean terminal);

    Class<?> higherNodeType(boolean terminal);

    Node<TYPE> createHigherNode(TYPE value, boolean terminal);

    Class<?> lowerCenterNodeType(boolean terminal);

    Node<TYPE> createLowerCenterNode(TYPE value, boolean terminal);

    Class<?> centerHigherNodeType(boolean terminal);

    Node<TYPE> createCenterHigherNode(TYPE value, boolean terminal);

    Class<?> lowerHigherNodeType(boolean terminal);

    Node<TYPE> createLowerHigherNode(TYPE value, boolean terminal);

    Class<?> lowerCenterHigherNodeType(boolean terminal);

    Node<TYPE> createLowerCenterHigherNode(TYPE value, boolean terminal);

    void release(Node<TYPE> node);
}
