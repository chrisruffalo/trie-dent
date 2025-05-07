package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.structures.nodes.Node;
import io.github.chrisruffalo.triedent.structures.Direction;
import io.github.chrisruffalo.triedent.structures.Indexer;
import io.github.chrisruffalo.triedent.structures.NodeWalker;

import java.util.LinkedList;
import java.util.List;

public class Finder<WHOLE, PART> implements NodeWalker<PART> {

    Indexer<WHOLE, PART> indexer;

    final List<Node<PART>> path = new LinkedList<>();

    final List<Node<PART>> visited = new LinkedList<>();

    boolean tracking = false;

    int index = 0;

    boolean matched = false;

    public Finder(Indexer<WHOLE, PART> indexer) {
        this.indexer = indexer;
    }

    public void setIndexer(Indexer<WHOLE, PART> indexer) {
        this.indexer = indexer;
    }

    public void clear() {
        indexer = null;
        path.clear();
        visited.clear();
        tracking = false;
        index = 0;
        matched = false;
    }

    @Override
    public Direction walk(Node<PART> current, int depth) {
        // this means you have run out of tree
        if (current == null || index >= this.indexer.length()) {
            return Direction.NONE;
        }

        PART valueAt = this.indexer.atIndex(index);
        PART currentNodeValue = current.getValue();
        visited.add(current);
        boolean terminal = indexer.atOrBeyondEnd(index);

        // find the direction in the tree
        final Direction toGo = indexer.compare(currentNodeValue, valueAt);

        // CENTER path: matching characters
        if (Direction.CENTER.equals(toGo)) {
            tracking = true;  // on the right track
            path.add(current); // add current node as part of the followed path

            // check if we've reached the terminal node of the input and if it's also terminal in the tree
            if (tracking && terminal && current.isTerminal()) {
                matched = true; // mark as matched when both conditions are met
                return Direction.NONE; // no need to go further
            } else if (tracking && terminal) {
                return Direction.NONE;
            }

            // move to the next part of the whole
            index++;
            return Direction.CENTER; // continue tracking down the center
        }

        // if not the center path, follow the direction
        return toGo;
    }

    public boolean matched() {
        return this.matched;
    }

    /**
     * Get the path of nodes that led to the match. Should
     * allow the original value (or parts) to be reconstructed
     * by following it back.
     *
     *
     * @return the list of nodes forming the path
     */
    public List<Node<PART>> getPath() {
        return this.path;
    }

    /**
     * This is the path of all nodes visited during the
     * walk regardless of if they are part of the output
     * terminal value.
     *
     * @return all visited nodes
     */
    public List<Node<PART>> getVisited() { return this.visited; }

    public static <WHOLE, PART> Finder<WHOLE, PART> find(Node<PART> partNode, Indexer<WHOLE, PART> indexer) {
        final Finder<WHOLE, PART> finder = new Finder<>(indexer);
        partNode.walk(finder);
        return finder;
    }
}
