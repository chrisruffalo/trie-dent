package io.github.chrisruffalo.triedent.structures.nodes;

import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.NodeVisitor;
import io.github.chrisruffalo.triedent.structures.NodeWalker;

import java.util.LinkedList;
import java.util.List;

public interface Node<TYPE> {

    /**
     * If present, get the lower (left) value with reference
     * to the "center" of this node.
     *
     * @return the node containing the lower value starting point
     */
    Node<TYPE> getLower();

    default void setLower(Node<TYPE> node) {};

    /**
     * Method for determining if the lower node is present.
     *
     * @return true if there is an existing lower node
     */
    boolean hasLower();

    /**
     * If present get the (center) descendent of this node.
     *
     * @return the node containing the direct descendent
     */
    Node<TYPE> getCenter();

    default void setCenter(Node<TYPE> node) {};

    /**
     * Method for determining if the center node is present.
     *
     * @return true if there is an existing center node
     */
    boolean hasCenter();

    /**
     * If present, get the higher (right) value with
     * reference to the "center" of this node.
     *
     * @return the node containing the higher value starting point
     */
    Node<TYPE> getHigher();

    default void setHigher(Node<TYPE> node) {};

    /**
     * Method for determining if the higher node is present.
     *
     * @return true if there is an existing higher node
     */
    boolean hasHigher();

    /**
     * Get the value contained by this node
     *
     * @return the value of the current node
     */
    TYPE getValue();

    default void setValue(TYPE value) {
        // only value-bearing nodes can do this
    }

    /**
     * Remove any values that are currently set on the node.
     */
    void reset();

    /**
     * Returns "true" if the current node will allow a search
     * to end on this node. This would mean that a find operation
     * through the tree can end here because an item has been found.
     *
     * For example if the string "dads" was represented by string
     * nodes then a find operation for "dad" or "da" would return false.
     * If the strings "da", "dad", and "dads" were added then it would
     * look something like <pre>d -> a -> d -> s -> null</pre> and
     * any of "da", "dad", or "dads" could be found because they
     * would end on terminal nodes.
     *
     * To reiterate: a "terminal" node is not the last node in a tree
     * or an edge node. It is a potential escape point where a search
     * can be terminated because it matches the end point of something
     * added to the tree.
     *
     * @return true if this is a "terminal" node. false otherwise.
     */
    default boolean isTerminal() {
        return false;
    };

    default void setTerminal(boolean terminal) {

    }

    /**
     * The root node is a special node that _can_ be terminal but that
     * cannot be transformed because of its location as a root node.
     *
     * @return if the current node is a root node
     */
    default boolean isRoot() {
        return false;
    }

    /**
     * Take a directed walk through the nodes. The walker
     * itself will return if it wants to visit the LOWER (left),
     * CENTER (direct), or HIGHER (right) node. The walker
     * can also end the walk by specifying a return value
     * of NONE.
     *
     * @param walker that will walk the tree
     */
    default void walk(NodeWalker<TYPE> walker) {
        Direction toGo;
        Node<TYPE> current = this;
        int depth = 0;
        do {
            toGo = walker.walk(current, depth++);
            switch (toGo) {
                case LOWER -> current = current.getLower();
                case HIGHER -> current = current.getHigher();
                case CENTER -> current = current.getCenter();
                default -> current = null;
            }
        } while (current != null);
    }

    /**
     * Visits every connected node, unlike a walker the only direction
     * is that it will continue or not continue. Otherwise: the visit order
     * is lower, center, higher, current. The visit is recursive. If the visited lower
     * node has a connected lower node then the visit will be to the connected
     * lower node and so on.
     *
     * @param visitor that will visit the nodes
     */
    default void visit(NodeVisitor<TYPE> visitor) {
        this.visit(visitor, 0);
    }

    /**
     * Internal "engine" for the visitation method that tracks depth.
     *
     * @param visitor that will be invoked on every node
     * @param depth of the current visitation (distance from the root)
     */
    private boolean visit(NodeVisitor<TYPE> visitor, int depth) {
        // Recursively visit lower node if present
        if (hasLower()) {
            boolean lowerVisit = getLower().visit(visitor, depth);
            if (!lowerVisit) {
                return false; // Stop visiting if lower branch should not continue
            }
        }

        // Visit the center node
        if (hasCenter()) {
            boolean centerVisit = getCenter().visit(visitor, depth + 1);
            if (!centerVisit) {
                return false; // Stop visiting if center branch should not continue
            }
        }

        // Recursively visit higher node if present
        if (hasHigher()) {
            boolean higherVisit = getHigher().visit(visitor, depth);
            if (!higherVisit) {
                return false; // Stop visiting if higher branch should not continue
            }
        }

        // visiting the current node
        return visitor.visit(this, depth); // Stop visitation if the visitor requests so
    }

    /**
     * Count all reachable nodes.
     *
     * @return the count of all reachable nodes
     */
    default long nodeCount() {
        return 1 + (hasLower() ? getLower().nodeCount() : 0) + (hasCenter() ? getCenter().nodeCount() : 0) + (hasHigher() ? getHigher().nodeCount() : 0);
    }

    /**
     * Count all terminal nodes
     *
     * @return the count of terminal nodes, serves as the "size" of the structure, generally
     */
    default long terminalCount() {
        return (isTerminal() ? 1 : 0) + (hasLower() ? getLower().terminalCount() : 0) + (hasCenter() ? getCenter().terminalCount() : 0) + (hasHigher() ? getHigher().terminalCount() : 0);
    }

    /**
     * Simple method to collect all possible paths through the tree that result
     * in something terminal. This allows the collection of values to be
     * reconstituted, by parts, via a collector that can collate them.
     *
     * @return the list of all paths through the tree that result in termination
     */
    default List<List<TYPE>> collect() {
        List<List<TYPE>> allPaths = new LinkedList<>();
        collectPaths(new LinkedList<>(), allPaths); // Start with an empty path
        return allPaths;
    }

    /**
     * Helper method for resolving paths
     *
     * @param currentPath that is being traversed
     * @param allPaths that have been traversed
     */
    private void collectPaths(List<TYPE> currentPath, List<List<TYPE>> allPaths) {
        // add the current node's value to the path if it's not null
        if (this.getValue() != null) {
            currentPath.add(this.getValue());
        }

        // if this is a terminal node, add the current path to the result list
        if (this.isTerminal()) {
            allPaths.add(new LinkedList<>(currentPath)); // Add a copy of the current path
        }

        // recursively collect paths from lower, center, and higher nodes
        if (this.hasLower()) {
            final List<TYPE> branch = new LinkedList<>(currentPath);
            branch.removeLast();
            this.getLower().collectPaths(branch, allPaths);
        }

        if (this.hasCenter()) {
            final List<TYPE> branch = new LinkedList<>(currentPath);
            this.getCenter().collectPaths(branch, allPaths);
        }

        if (this.hasHigher()) {
            final List<TYPE> branch = new LinkedList<>(currentPath);
            branch.removeLast();
            this.getHigher().collectPaths(branch, allPaths);
        }
    }
}
