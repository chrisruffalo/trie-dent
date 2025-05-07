package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Collector;
import io.github.chrisruffalo.triedent.structures.CollectorFactory;

public class DnsHashCollectorFactory implements CollectorFactory<String, Number> {

    static final DnsHashCollector SHARED = new DnsHashCollector();

    @Override
    public Collector<String, Number> build() {
        return SHARED;
    }

}
