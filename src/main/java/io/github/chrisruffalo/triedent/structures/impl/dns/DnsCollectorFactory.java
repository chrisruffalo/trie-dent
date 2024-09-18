package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Collector;
import io.github.chrisruffalo.triedent.structures.CollectorFactory;

public class DnsCollectorFactory implements CollectorFactory<String, CharSequence> {

    @Override
    public Collector<String, CharSequence> build() {
        return new DnsCollector();
    }

}
