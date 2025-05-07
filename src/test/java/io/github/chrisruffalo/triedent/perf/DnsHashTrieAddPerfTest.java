package io.github.chrisruffalo.triedent.perf;


import io.github.chrisruffalo.triedent.set.impl.DnsHashTrie;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(1)
public class DnsHashTrieAddPerfTest {


    DnsHashTrie trie = new DnsHashTrie();

    List<String> cache = new ArrayList<>();

    int index = 0;

    @Setup(Level.Invocation)
    public void setupInvokation() throws Exception {
        // executed before each invocation of the benchmark
    }

    @Setup(Level.Iteration)
    public void setupIteration() throws Exception {
       index = 0;
    }

    @Setup(Level.Trial)
    public void setupTrial() throws Exception {
        Path millionCsvPath = Paths.get("src", "test", "resources", "top-1m.csv");
        cache.clear();
        try(final BufferedReader reader = Files.newBufferedReader(millionCsvPath)) {
            reader.lines().forEach(x -> {
                final String entry = CharBuffer.wrap(x, x.indexOf(",") + 1, x.length()).toString().trim();
                cache.add(entry);
            });
        }
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    @Fork(warmups = 1, value = 1)
    @Warmup(batchSize = -1, iterations = 5, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(batchSize = -1, iterations = 100, time = 50, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void insert(Blackhole bh) throws Exception {
        bh.consume(trie.add(cache.get(index++)));
    }

    @Test
    public void benchmark() throws Exception {
        String[] argv = {
            "DnsHashTrieAddPerfTest.insert",
            "-prof", "gc"
        };
        org.openjdk.jmh.Main.main(argv);
    }

}
