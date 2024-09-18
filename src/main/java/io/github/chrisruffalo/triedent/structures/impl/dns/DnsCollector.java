package io.github.chrisruffalo.triedent.structures.impl.dns;

import io.github.chrisruffalo.triedent.structures.Collector;

import java.util.LinkedList;
import java.util.List;

public class DnsCollector implements Collector<String, CharSequence> {

    @Override
    public List<String> collect(List<List<CharSequence>> from) {
        List<String> names = new LinkedList<>();
        from.forEach(x -> {
            // remember, these are in _reverse_ order
            final StringBuilder builder = new StringBuilder();
            x.reversed().forEach(label -> {
                if (!builder.isEmpty()) {
                    builder.append(".");
                }
                builder.append(label);
            });
            names.add(builder.toString());
        });
        return names;
    }

}
