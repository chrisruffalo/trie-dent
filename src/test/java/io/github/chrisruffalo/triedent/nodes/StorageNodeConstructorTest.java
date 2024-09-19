package io.github.chrisruffalo.triedent.nodes;

import io.github.chrisruffalo.triedent.nodes.storage.StorageNode;
import io.github.chrisruffalo.triedent.nodes.storage.StorageNodeConstructor;
import io.github.chrisruffalo.triedent.nodes.storage.StorageRootNode;
import io.github.chrisruffalo.triedent.structures.impl.Finder;
import io.github.chrisruffalo.triedent.structures.impl.string.StringCharacterIndexerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StorageNodeConstructorTest {

    @Test
    public void build() {
        final StringCharacterIndexerFactory stringCharacterIndexerFactory = new StringCharacterIndexerFactory();
        final StorageNodeConstructor<String, String, Character> constructor = new StorageNodeConstructor<>(stringCharacterIndexerFactory);
        RootNode<Character> root = constructor.build();
        Assertions.assertInstanceOf(StorageRootNode.class, root);

        constructor.insert(root, "alpha", "alpha-value");
        constructor.insert(root, "alert", "alert-value");
        constructor.insert(root, "abacus", "abacus-value");
        constructor.insert(root, "alpaca", "alpaca-value");
        Finder<String, Character> finder = Finder.find(root, stringCharacterIndexerFactory.get("alpha"));

        Node<Character> node = finder.getPath().getLast();
        Assertions.assertInstanceOf(StorageNode.class, node);
        StorageNode<Character, String> storageNode = (StorageNode<Character, String>) node;
        Assertions.assertEquals("alpha-value", storageNode.getStored());
    }

}
