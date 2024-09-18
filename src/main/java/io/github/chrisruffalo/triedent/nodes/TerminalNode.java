package io.github.chrisruffalo.triedent.nodes;

public interface TerminalNode<TYPE> extends Node<TYPE> {

    @Override
    default boolean isTerminal() {
        return true;
    }

}
