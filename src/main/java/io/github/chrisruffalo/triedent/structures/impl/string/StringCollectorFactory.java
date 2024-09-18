package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Collector;
import io.github.chrisruffalo.triedent.structures.CollectorFactory;

public class StringCollectorFactory implements CollectorFactory<String, CharSequence> {

    @Override
    public Collector<String, CharSequence> build() {
        return new StringCollector();
    }

}
