// Déclaration du paquet de ce fichier
package net.minestom.server.network;

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
@State(Scope.Thread)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Déclaration de type (classe/interface/enum/record)
public class NetworkSerializerLargeTemplateBenchmark {

    // Déclaration de type (classe/interface/enum/record)
    record Packet(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9, boolean var10, boolean var11, boolean var12, boolean var13, boolean var14, boolean var15, boolean var16, boolean var17, boolean var18, boolean var19, boolean var20) {
    // Fin d'un bloc/d'une expression
    }
    
    // Instruction de code
    private NetworkBuffer.Type<Packet> serializer;
    // Instruction de code
    private Packet packet;
    // Instruction de code
    private NetworkBuffer readBuffer;
    // Instruction de code
    private NetworkBuffer writeBuffer;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Affecte une valeur
        serializer = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var1,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var2,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var3,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var4,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var5,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var6,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var7,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var8,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var9,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var10,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var11,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var12,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var13,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var14,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var15,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var16,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var17,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var18,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var19,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Packet::var20,
                // Instruction de code
                Packet::new
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        packet = new Packet(true, false, true, true, false, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false);
        // Appelle une méthode
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Appelle une méthode
        readBuffer.write(serializer, packet);
        // Appelle une méthode
        writeBuffer = NetworkBuffer.staticBuffer(256);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writePacket(Blackhole blackhole) {
        // Affecte une valeur
        var writeBuffer = this.writeBuffer;
        // Appelle une méthode
        writeBuffer.writeAt(0, serializer, packet);
        // Appelle une méthode
        blackhole.consume(writeBuffer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readPacket(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(readBuffer.readAt(0, serializer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(serializer);
        // Appelle une méthode
        blackhole.consume(packet);
        // Appelle une méthode
        blackhole.consume(readBuffer);
        // Appelle une méthode
        blackhole.consume(writeBuffer);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
