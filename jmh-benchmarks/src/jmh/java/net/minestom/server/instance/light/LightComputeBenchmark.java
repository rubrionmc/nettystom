// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.shorts.ShortArrayFIFOQueue;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.palette.Palette;
// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(3)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Déclaration de type (classe/interface/enum/record)
public class LightComputeBenchmark {

    // Instruction de code
    private Palette airPalette;
    // Instruction de code
    private Palette stonePalette;
    // Instruction de code
    private Palette glowstonePalette;
    // Instruction de code
    private Palette mixedGlowstonePalette;
    // Instruction de code
    private Palette mixedStonePalette;

    // Instruction de code
    private byte[] content1;
    // Instruction de code
    private byte[] content2;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        airPalette = Palette.blocks();
        // Appelle une méthode
        airPalette.fill(Block.AIR.stateId());

        // Appelle une méthode
        stonePalette = Palette.blocks();
        // Appelle une méthode
        stonePalette.fill(Block.STONE.stateId());

        // Appelle une méthode
        glowstonePalette = Palette.blocks();
        // Appelle une méthode
        glowstonePalette.fill(Block.GLOWSTONE.stateId());

        // Appelle une méthode
        mixedStonePalette = Palette.blocks();
        // Appelle une méthode
        mixedStonePalette.fill(Block.STONE.stateId());
        // Boucle : répète un bloc
        for (int x = 0; x < 16; x += 2) {
            // Boucle : répète un bloc
            for (int y = 0; y < 16; y += 2) {
                // Boucle : répète un bloc
                for (int z = 0; z < 16; z += 2) {
                    // Appelle une méthode
                    mixedStonePalette.set(x, y, z, Block.AIR.stateId());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        mixedGlowstonePalette = Palette.blocks();
        // Appelle une méthode
        mixedGlowstonePalette.fill(Block.GLOWSTONE.stateId());
        // Boucle : répète un bloc
        for (int x = 0; x < 16; x += 2) {
            // Boucle : répète un bloc
            for (int y = 0; y < 16; y += 2) {
                // Boucle : répète un bloc
                for (int z = 0; z < 16; z += 2) {
                    // Appelle une méthode
                    mixedGlowstonePalette.set(x, y, z, Block.AIR.stateId());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Content arrays for bake_differentArrays benchmark
        // Appelle une méthode
        var queue1 = new ShortArrayFIFOQueue();
        // Appelle une méthode
        queue1.enqueue((short) ((8 | (8 << 4) | (8 << 8)) | (15 << 12)));
        // Appelle une méthode
        var queue2 = new ShortArrayFIFOQueue();
        // Boucle : répète un bloc
        for (int i = 0; i < 16; i += 4) {
            // Appelle une méthode
            queue2.enqueue((short) ((i | (8 << 4) | (8 << 8)) | (15 << 12)));
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        content1 = LightCompute.compute(airPalette, queue1);
        // Appelle une méthode
        content2 = LightCompute.compute(airPalette, queue2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void buildInternalQueue_air(Blackhole blackhole) {
        // Appelle une méthode
        var queue = BlockLight.buildInternalQueue(airPalette);
        // Appelle une méthode
        blackhole.consume(queue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void buildInternalQueue_stone(Blackhole blackhole) {
        // Appelle une méthode
        var queue = BlockLight.buildInternalQueue(stonePalette);
        // Appelle une méthode
        blackhole.consume(queue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void buildInternalQueue_glowstone(Blackhole blackhole) {
        // Appelle une méthode
        var queue = BlockLight.buildInternalQueue(glowstonePalette);
        // Appelle une méthode
        blackhole.consume(queue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void buildInternalQueue_mixedStone(Blackhole blackhole) {
        // Appelle une méthode
        var queue = BlockLight.buildInternalQueue(mixedStonePalette);
        // Appelle une méthode
        blackhole.consume(queue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void buildInternalQueue_mixedGlowStone(Blackhole blackhole) {
        // Appelle une méthode
        var queue = BlockLight.buildInternalQueue(mixedGlowstonePalette);
        // Appelle une méthode
        blackhole.consume(queue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void bake_emptyContent(Blackhole blackhole) {
        // Appelle une méthode
        byte[] result = LightCompute.bake(LightCompute.EMPTY_CONTENT, LightCompute.EMPTY_CONTENT);
        // Appelle une méthode
        blackhole.consume(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void bake_fullyLit(Blackhole blackhole) {
        // Appelle une méthode
        byte[] result = LightCompute.bake(LightCompute.CONTENT_FULLY_LIT, LightCompute.EMPTY_CONTENT);
        // Appelle une méthode
        blackhole.consume(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void bake_sameReference(Blackhole blackhole) {
        // Affecte une valeur
        byte[] content = new byte[LightCompute.LIGHT_LENGTH];
        // Appelle une méthode
        byte[] result = LightCompute.bake(content, content);
        // Appelle une méthode
        blackhole.consume(result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void bake_differentArrays(Blackhole blackhole) {
        // Appelle une méthode
        byte[] result = LightCompute.bake(content1, content2);
        // Appelle une méthode
        blackhole.consume(result);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

