package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Collector;

import java.util.LinkedList;
import java.util.List;

public class StringCollector implements Collector<String, CharSequence> {

    @Override
    public List<String> collect(List<List<CharSequence>> from) {
        final List<String> wholes = new LinkedList<>();
        from.forEach(list -> {
            final StringBuilder builder = new StringBuilder();
            list.forEach(builder::append);
            wholes.add(builder.toString());
        });

        return wholes;
    }

}
