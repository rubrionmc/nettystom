// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Fork(3)
// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Benchmark)
// Type declaration (class/interface/enum/record)
public class DynamicRegistryGetIdBenchmark {
    // Code statement
    private DynamicRegistry<Biome> registry;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        registry = Biome.createDefaultRegistry();
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void getId(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(registry.getId(RegistryKey.unsafeOf("pale_garden")));
    // End of a block/expression
    }
// End of a block/expression
}
