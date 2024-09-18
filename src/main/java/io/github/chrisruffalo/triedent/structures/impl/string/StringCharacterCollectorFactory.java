package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Collector;
import io.github.chrisruffalo.triedent.structures.CollectorFactory;

public class StringCharacterCollectorFactory implements CollectorFactory<String, Character> {

    @Override
    public Collector<String, Character> build() {
        return new StringCharacterCollector();
    }

}
