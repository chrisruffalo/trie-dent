package io.github.chrisruffalo.triedent.structures;

import java.util.List;

public interface Collector<WHOLE, PART> {

    List<WHOLE> collect(List<List<PART>> from);

}
