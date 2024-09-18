package io.github.chrisruffalo.triedent.structures;

public interface CollectorFactory<WHOLE, PART> {

    Collector<WHOLE, PART> build();

}
