package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Collector;

import java.util.Collections;
import java.util.List;

public class DnsHashCollector implements Collector<String, Number> {

    @Override
    public List<String> collect(List<List<Number>> from) {
        return Collections.emptyList();
    }
}
