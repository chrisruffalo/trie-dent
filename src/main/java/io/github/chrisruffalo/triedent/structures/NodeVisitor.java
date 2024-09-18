package io.github.chrisruffalo.triedent.structures;

import io.github.chrisruffalo.triedent.nodes.Node;

/**
 * A visitor visits _all_ nodes in the order left, center, right. Basically
 * from the lowest values, to the center values, to the right values.
 *
 * @param <PART>
 */
public interface NodeVisitor<PART> {

    /**
     * A visitor will visit EVERY node, in the visiting order, until
     * the visit is ended by having the visitor return "false" from
     * this method.
     *
     * @param current
     * @param depth
     * @return true when the visit should continue, false otherwise
     */
    boolean visit(Node<PART> current, int depth);

}
