package io.github.chrisruffalo.triedent.structures.impl.string;

import io.github.chrisruffalo.triedent.structures.Collector;

import java.util.LinkedList;
import java.util.List;

public class StringCharacterCollector implements Collector<String, Character> {

    @Override
    public List<String> collect(List<List<Character>> from) {
        final List<String> collected = new LinkedList<>();
        from.forEach(list -> {
            final StringBuilder builder = new StringBuilder();
            list.forEach(builder::append);
            collected.add(builder.toString());
        });
        return collected;
    }

}
