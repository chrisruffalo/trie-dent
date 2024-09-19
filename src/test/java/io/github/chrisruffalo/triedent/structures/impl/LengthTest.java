package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LengthTest {


    @Test
    void getLength() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final NodeConstructor<String, Character> constructor = new NodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();

        Length<Character> lengther = new Length<>();
        root.visit(lengther);
        Assertions.assertEquals(0, lengther.getLength());

        constructor.insert(root, "alpha");
        lengther = new Length<>();
        root.visit(lengther);
        Assertions.assertEquals(5, lengther.getLength());

        constructor.insert(root, "constructs");
        lengther = new Length<>();
        root.visit(lengther);
        Assertions.assertEquals(10, lengther.getLength());

        constructor.insert(root, "constructive");
        lengther = new Length<>();
        root.visit(lengther);
        Assertions.assertEquals(12, lengther.getLength());

        constructor.insert(root, "deconstructive");
        lengther = new Length<>();
        root.visit(lengther);
        Assertions.assertEquals(14, lengther.getLength());
    }


}