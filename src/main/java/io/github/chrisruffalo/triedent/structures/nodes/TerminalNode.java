package io.github.chrisruffalo.triedent.structures.nodes;

public interface TerminalNode<TYPE> extends Node<TYPE> {

    @Override
    default boolean isTerminal() {
        return true;
    }

    default boolean shouldBeTerminal(final boolean newTerminalState) {
        return true;
    }

}
