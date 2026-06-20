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
@BenchmarkMode(Mode.Throughput)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@State(Scope.Thread)
// Déclaration de type (classe/interface/enum/record)
public class NetworkBufferIOUTFStringBenchmark {

    // Test strings of various types and lengths; Asked AI for some strings
    // Affecte une valeur
    private static final String ASCII_SHORT = "Hello, World!";
    // Appelle une méthode
    private static final String ASCII_MEDIUM = "The quick brown fox jumps over the lazy dog. ".repeat(5);
    // Appelle une méthode
    private static final String ASCII_LONG = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(20);

    // Affecte une valeur
    private static final String UNICODE_SHORT = "Hello 世界! 🌍";
    // Appelle une méthode
    private static final String UNICODE_MEDIUM = "Minestom supports: English, 中文, 日本語, 한국어, العربية, Русский! 🎮🚀".repeat(3);
    // Appelle une méthode
    private static final String UNICODE_LONG = "🎮 Gaming server with emoji 🚀 and Unicode ©®™ symbols ∑∏√∫ ".repeat(15);

    // Affecte une valeur
    private static final String MIXED_SHORT = "Player123 joined!";
    // Appelle une méthode
    private static final String MIXED_MEDIUM = "User€100 bought §aGreen§r item for ¥500 (税込み)".repeat(4);
    // Appelle une méthode
    private static final String MIXED_LONG = "Server message: Player123 (レベル50) earned achievement 🏆 'Master Builder' for constructing 1,000+ blocks!".repeat(10);

    // Writing buffers
    // Instruction de code
    private NetworkBuffer writeBufferAsciiShort;
    // Instruction de code
    private NetworkBuffer writeBufferAsciiMedium;
    // Instruction de code
    private NetworkBuffer writeBufferAsciiLong;
    // Instruction de code
    private NetworkBuffer writeBufferUnicodeShort;
    // Instruction de code
    private NetworkBuffer writeBufferUnicodeMedium;
    // Instruction de code
    private NetworkBuffer writeBufferUnicodeLong;
    // Instruction de code
    private NetworkBuffer writeBufferMixedShort;
    // Instruction de code
    private NetworkBuffer writeBufferMixedMedium;
    // Instruction de code
    private NetworkBuffer writeBufferMixedLong;

    // Reading buffers
    // Instruction de code
    private NetworkBuffer readBufferAsciiShort;
    // Instruction de code
    private NetworkBuffer readBufferAsciiMedium;
    // Instruction de code
    private NetworkBuffer readBufferAsciiLong;
    // Instruction de code
    private NetworkBuffer readBufferUnicodeShort;
    // Instruction de code
    private NetworkBuffer readBufferUnicodeMedium;
    // Instruction de code
    private NetworkBuffer readBufferUnicodeLong;
    // Instruction de code
    private NetworkBuffer readBufferMixedShort;
    // Instruction de code
    private NetworkBuffer readBufferMixedMedium;
    // Instruction de code
    private NetworkBuffer readBufferMixedLong;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Initialize write buffers
        // Appelle une méthode
        writeBufferAsciiShort = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferAsciiMedium = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferAsciiLong = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferUnicodeShort = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferUnicodeMedium = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferUnicodeLong = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferMixedShort = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferMixedMedium = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        writeBufferMixedLong = NetworkBuffer.resizableBuffer();

        // Initialize and pre-fill read buffers
        // Appelle une méthode
        readBufferAsciiShort = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferAsciiShort.write(NetworkBuffer.STRING_IO_UTF8, ASCII_SHORT);

        // Appelle une méthode
        readBufferAsciiMedium = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferAsciiMedium.write(NetworkBuffer.STRING_IO_UTF8, ASCII_MEDIUM);

        // Appelle une méthode
        readBufferAsciiLong = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferAsciiLong.write(NetworkBuffer.STRING_IO_UTF8, ASCII_LONG);

        // Appelle une méthode
        readBufferUnicodeShort = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferUnicodeShort.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_SHORT);

        // Appelle une méthode
        readBufferUnicodeMedium = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferUnicodeMedium.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_MEDIUM);

        // Appelle une méthode
        readBufferUnicodeLong = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferUnicodeLong.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_LONG);

        // Appelle une méthode
        readBufferMixedShort = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferMixedShort.write(NetworkBuffer.STRING_IO_UTF8, MIXED_SHORT);

        // Appelle une méthode
        readBufferMixedMedium = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferMixedMedium.write(NetworkBuffer.STRING_IO_UTF8, MIXED_MEDIUM);

        // Appelle une méthode
        readBufferMixedLong = NetworkBuffer.resizableBuffer();
        // Appelle une méthode
        readBufferMixedLong.write(NetworkBuffer.STRING_IO_UTF8, MIXED_LONG);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeAsciiShort() {
        // Appelle une méthode
        writeBufferAsciiShort.clear();
        // Appelle une méthode
        writeBufferAsciiShort.write(NetworkBuffer.STRING_IO_UTF8, ASCII_SHORT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeAsciiMedium() {
        // Appelle une méthode
        writeBufferAsciiMedium.clear();
        // Appelle une méthode
        writeBufferAsciiMedium.write(NetworkBuffer.STRING_IO_UTF8, ASCII_MEDIUM);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeAsciiLong() {
        // Appelle une méthode
        writeBufferAsciiLong.clear();
        // Appelle une méthode
        writeBufferAsciiLong.write(NetworkBuffer.STRING_IO_UTF8, ASCII_LONG);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeUnicodeShort() {
        // Appelle une méthode
        writeBufferUnicodeShort.clear();
        // Appelle une méthode
        writeBufferUnicodeShort.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_SHORT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeUnicodeMedium() {
        // Appelle une méthode
        writeBufferUnicodeMedium.clear();
        // Appelle une méthode
        writeBufferUnicodeMedium.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_MEDIUM);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeUnicodeLong() {
        // Appelle une méthode
        writeBufferUnicodeLong.clear();
        // Appelle une méthode
        writeBufferUnicodeLong.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_LONG);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeMixedShort() {
        // Appelle une méthode
        writeBufferMixedShort.clear();
        // Appelle une méthode
        writeBufferMixedShort.write(NetworkBuffer.STRING_IO_UTF8, MIXED_SHORT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeMixedMedium() {
        // Appelle une méthode
        writeBufferMixedMedium.clear();
        // Appelle une méthode
        writeBufferMixedMedium.write(NetworkBuffer.STRING_IO_UTF8, MIXED_MEDIUM);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void writeMixedLong() {
        // Appelle une méthode
        writeBufferMixedLong.clear();
        // Appelle une méthode
        writeBufferMixedLong.write(NetworkBuffer.STRING_IO_UTF8, MIXED_LONG);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readAsciiShort(Blackhole blackhole) {
        // Appelle une méthode
        readBufferAsciiShort.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferAsciiShort.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readAsciiMedium(Blackhole blackhole) {
        // Appelle une méthode
        readBufferAsciiMedium.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferAsciiMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readAsciiLong(Blackhole blackhole) {
        // Appelle une méthode
        readBufferAsciiLong.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferAsciiLong.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readUnicodeShort(Blackhole blackhole) {
        // Appelle une méthode
        readBufferUnicodeShort.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferUnicodeShort.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readUnicodeMedium(Blackhole blackhole) {
        // Appelle une méthode
        readBufferUnicodeMedium.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferUnicodeMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readUnicodeLong(Blackhole blackhole) {
        // Appelle une méthode
        readBufferUnicodeLong.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferUnicodeLong.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readMixedShort(Blackhole blackhole) {
        // Appelle une méthode
        readBufferMixedShort.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferMixedShort.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readMixedMedium(Blackhole blackhole) {
        // Appelle une méthode
        readBufferMixedMedium.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferMixedMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void readMixedLong(Blackhole blackhole) {
        // Appelle une méthode
        readBufferMixedLong.readIndex(0);
        // Appelle une méthode
        blackhole.consume(readBufferMixedLong.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void roundTripAsciiShort(Blackhole blackhole) {
        // Appelle une méthode
        writeBufferAsciiShort.clear();
        // Appelle une méthode
        writeBufferAsciiShort.write(NetworkBuffer.STRING_IO_UTF8, ASCII_SHORT);
        // Appelle une méthode
        blackhole.consume(writeBufferAsciiShort.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void roundTripUnicodeMedium(Blackhole blackhole) {
        // Appelle une méthode
        writeBufferUnicodeMedium.clear();
        // Appelle une méthode
        writeBufferUnicodeMedium.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_MEDIUM);
        // Appelle une méthode
        blackhole.consume(writeBufferUnicodeMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void roundTripMixedLong(Blackhole blackhole) {
        // Appelle une méthode
        writeBufferMixedLong.clear();
        // Appelle une méthode
        writeBufferMixedLong.write(NetworkBuffer.STRING_IO_UTF8, MIXED_LONG);
        // Appelle une méthode
        blackhole.consume(writeBufferMixedLong.read(NetworkBuffer.STRING_IO_UTF8));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

