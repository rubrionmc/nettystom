// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

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
public class NetworkSerializerPollutedTemplateBenchmark {
    // Instruction de code
    private NetworkBuffer.Type<Packet> packetSerializer;
    // Instruction de code
    private Packet packet;
    // Instruction de code
    private NetworkBuffer readBuffer;
    // Instruction de code
    private NetworkBuffer writeBuffer;
    // Instruction de code
    private Polluter<?>[] polluters;

    // Début d'une méthode/d'un bloc
    private static <T> Polluter<T> polluter(NetworkBuffer.Type<T> type, T value) {
        // Renvoie une valeur à l'appelant
        return new Polluter<>(type, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Setup(Level.Trial)
    // Début d'une méthode/d'un bloc
    public void setupTrial() {
        // Affecte une valeur
        polluters = new Polluter[]{
                // Instruction de code
                polluter(NetworkBufferTemplate.template(BOOLEAN, BooleanPacket::value, BooleanPacket::new), new BooleanPacket(true)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(BYTE, BytePacket::value, BytePacket::new), new BytePacket((byte) 1)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(SHORT, ShortPacket::value, ShortPacket::new), new ShortPacket((short) 2)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(INT, IntPacket::value, IntPacket::new), new IntPacket(3)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(FLOAT, FloatPacket::value, FloatPacket::new), new FloatPacket(4.0f)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(DOUBLE, DoublePacket::value, DoublePacket::new), new DoublePacket(5.0d)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(STRING, StringPacket::value, StringPacket::new), new StringPacket("polluted")),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(VAR_INT, VarIntPacket::value, VarIntPacket::new), new VarIntPacket(6)),
                // Instruction de code
                polluter(NetworkBufferTemplate.template(VAR_LONG, VarLongPacket::value, VarLongPacket::new), new VarLongPacket(7L)),
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        packetSerializer = NetworkBufferTemplate.template(NetworkBuffer.LONG, Packet::id, Packet::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Setup(Level.Iteration)
    // Début d'une méthode/d'un bloc
    public void setupIteration() {
        // Appelle une méthode
        packet = new Packet(12451235L);
        // Appelle une méthode
        readBuffer = NetworkBuffer.staticBuffer(256);
        // Appelle une méthode
        readBuffer.write(packetSerializer, packet);
        // Appelle une méthode
        writeBuffer = NetworkBuffer.staticBuffer(256);

        // Boucle : répète un bloc
        for (int i = 0; i < 20_000; i++) {
            // Affecte une valeur
            Polluter<?> polluter = polluters[i % polluters.length];
            // Appelle une méthode
            polluter.write();
            // Appelle une méthode
            polluter.read();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writePacket(Blackhole blackhole) {
        // Affecte une valeur
        var writeBuffer = this.writeBuffer;
        // Appelle une méthode
        writeBuffer.writeIndex(0);
        // Appelle une méthode
        packetSerializer.write(writeBuffer, packet);
        // Appelle une méthode
        blackhole.consume(writeBuffer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readPacket(Blackhole blackhole) {
        // Affecte une valeur
        var readBuffer = this.readBuffer;
        // Appelle une méthode
        readBuffer.readIndex(0);
        // Appelle une méthode
        blackhole.consume(packetSerializer.read(readBuffer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @TearDown
    // Début d'une méthode/d'un bloc
    public void teardown(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(packet);
        // Appelle une méthode
        blackhole.consume(readBuffer);
        // Appelle une méthode
        blackhole.consume(writeBuffer);
        // Appelle une méthode
        blackhole.consume(polluters);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Packet(long id) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class Polluter<T> {
        // Instruction de code
        private final NetworkBuffer.Type<T> type;
        // Instruction de code
        private final T value;
        // Instruction de code
        private final NetworkBuffer buffer;

        // Début d'une méthode/d'un bloc
        private Polluter(NetworkBuffer.Type<T> type, T value) {
            // Accès à l'objet courant/parent
            this.type = type;
            // Accès à l'objet courant/parent
            this.value = value;
            // Accès à l'objet courant/parent
            this.buffer = NetworkBuffer.staticBuffer(256);
            // Accès à l'objet courant/parent
            super();
            // Appelle une méthode
            write();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void write() {
            // Appelle une méthode
            buffer.writeIndex(0);
            // Appelle une méthode
            type.write(buffer, value);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private T read() {
            // Appelle une méthode
            buffer.readIndex(0);
            // Renvoie une valeur à l'appelant
            return type.read(buffer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record BooleanPacket(boolean value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record BytePacket(byte value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record ShortPacket(short value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record IntPacket(int value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record FloatPacket(float value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record DoublePacket(double value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record StringPacket(String value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record VarIntPacket(int value) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record VarLongPacket(long value) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
