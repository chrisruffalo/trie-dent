package io.github.chrisruffalo.triedent.structures.impl;

import io.github.chrisruffalo.triedent.nodes.NodeConstructor;
import io.github.chrisruffalo.triedent.nodes.RootNode;
import io.github.chrisruffalo.triedent.structures.impl.string.StringIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LengthTest {


    @Test
    void getLength() {
        final StringIndexerFactory stringIndexerFactory = new StringIndexerFactory();
        final NodeConstructor<String, CharSequence> constructor = new NodeConstructor<>(stringIndexerFactory);
        RootNode<CharSequence> root = constructor.build();

        Length<CharSequence> lengther = new Length<>();
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