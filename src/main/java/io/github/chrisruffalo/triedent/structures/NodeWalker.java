package io.github.chrisruffalo.triedent.structures;

import io.github.chrisruffalo.triedent.nodes.Node;

/**
 * A walker visits _directed_ nodes.
 */
public interface NodeWalker<TYPE> {

    Direction walk(Node<TYPE> current, int depth);

}
