// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import it.unimi.dsi.fastutil.shorts.ShortArrayFIFOQueue;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.palette.Palette;
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
public class LightComputeBenchmark {

    // Code statement
    private Palette airPalette;
    // Code statement
    private Palette stonePalette;
    // Code statement
    private Palette glowstonePalette;
    // Code statement
    private Palette mixedGlowstonePalette;
    // Code statement
    private Palette mixedStonePalette;

    // Code statement
    private byte[] content1;
    // Code statement
    private byte[] content2;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Calls a method
        airPalette = Palette.blocks();
        // Calls a method
        airPalette.fill(Block.AIR.stateId());

        // Calls a method
        stonePalette = Palette.blocks();
        // Calls a method
        stonePalette.fill(Block.STONE.stateId());

        // Calls a method
        glowstonePalette = Palette.blocks();
        // Calls a method
        glowstonePalette.fill(Block.GLOWSTONE.stateId());

        // Calls a method
        mixedStonePalette = Palette.blocks();
        // Calls a method
        mixedStonePalette.fill(Block.STONE.stateId());
        // Loop: repeats a block
        for (int x = 0; x < 16; x += 2) {
            // Loop: repeats a block
            for (int y = 0; y < 16; y += 2) {
                // Loop: repeats a block
                for (int z = 0; z < 16; z += 2) {
                    // Calls a method
                    mixedStonePalette.set(x, y, z, Block.AIR.stateId());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        mixedGlowstonePalette = Palette.blocks();
        // Calls a method
        mixedGlowstonePalette.fill(Block.GLOWSTONE.stateId());
        // Loop: repeats a block
        for (int x = 0; x < 16; x += 2) {
            // Loop: repeats a block
            for (int y = 0; y < 16; y += 2) {
                // Loop: repeats a block
                for (int z = 0; z < 16; z += 2) {
                    // Calls a method
                    mixedGlowstonePalette.set(x, y, z, Block.AIR.stateId());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Content arrays for bake_differentArrays benchmark
        // Calls a method
        var queue1 = new ShortArrayFIFOQueue();
        // Calls a method
        queue1.enqueue((short) ((8 | (8 << 4) | (8 << 8)) | (15 << 12)));
        // Calls a method
        var queue2 = new ShortArrayFIFOQueue();
        // Loop: repeats a block
        for (int i = 0; i < 16; i += 4) {
            // Calls a method
            queue2.enqueue((short) ((i | (8 << 4) | (8 << 8)) | (15 << 12)));
        // End of a block/expression
        }
        // Calls a method
        content1 = LightCompute.compute(airPalette, queue1);
        // Calls a method
        content2 = LightCompute.compute(airPalette, queue2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void buildInternalQueue_air(Blackhole blackhole) {
        // Calls a method
        var queue = BlockLight.buildInternalQueue(airPalette);
        // Calls a method
        blackhole.consume(queue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void buildInternalQueue_stone(Blackhole blackhole) {
        // Calls a method
        var queue = BlockLight.buildInternalQueue(stonePalette);
        // Calls a method
        blackhole.consume(queue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void buildInternalQueue_glowstone(Blackhole blackhole) {
        // Calls a method
        var queue = BlockLight.buildInternalQueue(glowstonePalette);
        // Calls a method
        blackhole.consume(queue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void buildInternalQueue_mixedStone(Blackhole blackhole) {
        // Calls a method
        var queue = BlockLight.buildInternalQueue(mixedStonePalette);
        // Calls a method
        blackhole.consume(queue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void buildInternalQueue_mixedGlowStone(Blackhole blackhole) {
        // Calls a method
        var queue = BlockLight.buildInternalQueue(mixedGlowstonePalette);
        // Calls a method
        blackhole.consume(queue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void bake_emptyContent(Blackhole blackhole) {
        // Calls a method
        byte[] result = LightCompute.bake(LightCompute.EMPTY_CONTENT, LightCompute.EMPTY_CONTENT);
        // Calls a method
        blackhole.consume(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void bake_fullyLit(Blackhole blackhole) {
        // Calls a method
        byte[] result = LightCompute.bake(LightCompute.CONTENT_FULLY_LIT, LightCompute.EMPTY_CONTENT);
        // Calls a method
        blackhole.consume(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void bake_sameReference(Blackhole blackhole) {
        // Assigns a value
        byte[] content = new byte[LightCompute.LIGHT_LENGTH];
        // Calls a method
        byte[] result = LightCompute.bake(content, content);
        // Calls a method
        blackhole.consume(result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void bake_differentArrays(Blackhole blackhole) {
        // Calls a method
        byte[] result = LightCompute.bake(content1, content2);
        // Calls a method
        blackhole.consume(result);
    // End of a block/expression
    }
// End of a block/expression
}

