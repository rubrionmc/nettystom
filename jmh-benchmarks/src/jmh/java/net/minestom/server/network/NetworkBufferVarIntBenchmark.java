// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.Random;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@Warmup(iterations = 8, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(3)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Thread)
// Annotation pour l'élément suivant
@Threads(4)
// Déclaration de type (classe/interface/enum/record)
public class NetworkBufferVarIntBenchmark {

    // Instruction de code
    private NetworkBuffer writeBuffer;
    // Instruction de code
    private NetworkBuffer readBuffer;

    // Affecte une valeur
    private static final int DATA_SIZE = 4096;
    // Affecte une valeur
    private static final int MASK = DATA_SIZE - 1;

    // Instruction de code
    private int[] mixedData;
    // Instruction de code
    private int[] readPositions; // Offsets for reading different sized VarInts
    // Instruction de code
    private int index;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Appelle une méthode
        writeBuffer = NetworkBuffer.staticBuffer(256);
        // Appelle une méthode
        readBuffer = NetworkBuffer.staticBuffer(DATA_SIZE * 5);

        // Appelle une méthode
        Random random = new Random(67);
        // Affecte une valeur
        mixedData = new int[DATA_SIZE];
        // Affecte une valeur
        readPositions = new int[DATA_SIZE];

        // Boucle : répète un bloc
        for (int i = 0; i < DATA_SIZE; i++) {
            // Appelle une méthode
            double r = random.nextDouble();
            // Instruction de code
            int val;
            // Embranchement : vérifie une condition
            if (r < 0.5) val = random.nextInt(0, 128);
            // Embranchement : vérifie une condition
            else if (r < 0.8) val = random.nextInt(128, 16384);
            // Branche alternative de la condition
            else val = random.nextInt();

            // Affecte une valeur
            mixedData[i] = val;

            // Appelle une méthode
            readPositions[i] = (int) readBuffer.writeIndex();
            // Appelle une méthode
            readBuffer.write(NetworkBuffer.VAR_INT, val);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeVarint() {
        // Affecte une valeur
        int val = mixedData[index++ & MASK];
        // Appelle une méthode
        writeBuffer.writeAt(0, NetworkBuffer.VAR_INT, val);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readVarint(Blackhole bh) {
        // Affecte une valeur
        int pos = readPositions[index++ & MASK];
        // Appelle une méthode
        bh.consume(readBuffer.readAt(pos, NetworkBuffer.VAR_INT));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(writeBuffer);
        // Appelle une méthode
        blackhole.consume(readBuffer);
        // Appelle une méthode
        blackhole.consume(mixedData);
        // Appelle une méthode
        blackhole.consume(readPositions);
        // Appelle une méthode
        blackhole.consume(index);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
