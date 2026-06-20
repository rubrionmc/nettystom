// Package declaration for this file
package net.minestom.server.network;

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
@BenchmarkMode(Mode.Throughput)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// Annotation for the following element
@State(Scope.Thread)
// Type declaration (class/interface/enum/record)
public class NetworkBufferIOUTFStringBenchmark {

    // Test strings of various types and lengths; Asked AI for some strings
    // Assigns a value
    private static final String ASCII_SHORT = "Hello, World!";
    // Calls a method
    private static final String ASCII_MEDIUM = "The quick brown fox jumps over the lazy dog. ".repeat(5);
    // Calls a method
    private static final String ASCII_LONG = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(20);

    // Assigns a value
    private static final String UNICODE_SHORT = "Hello 世界! 🌍";
    // Calls a method
    private static final String UNICODE_MEDIUM = "Minestom supports: English, 中文, 日本語, 한국어, العربية, Русский! 🎮🚀".repeat(3);
    // Calls a method
    private static final String UNICODE_LONG = "🎮 Gaming server with emoji 🚀 and Unicode ©®™ symbols ∑∏√∫ ".repeat(15);

    // Assigns a value
    private static final String MIXED_SHORT = "Player123 joined!";
    // Calls a method
    private static final String MIXED_MEDIUM = "User€100 bought §aGreen§r item for ¥500 (税込み)".repeat(4);
    // Calls a method
    private static final String MIXED_LONG = "Server message: Player123 (レベル50) earned achievement 🏆 'Master Builder' for constructing 1,000+ blocks!".repeat(10);

    // Writing buffers
    // Code statement
    private NetworkBuffer writeBufferAsciiShort;
    // Code statement
    private NetworkBuffer writeBufferAsciiMedium;
    // Code statement
    private NetworkBuffer writeBufferAsciiLong;
    // Code statement
    private NetworkBuffer writeBufferUnicodeShort;
    // Code statement
    private NetworkBuffer writeBufferUnicodeMedium;
    // Code statement
    private NetworkBuffer writeBufferUnicodeLong;
    // Code statement
    private NetworkBuffer writeBufferMixedShort;
    // Code statement
    private NetworkBuffer writeBufferMixedMedium;
    // Code statement
    private NetworkBuffer writeBufferMixedLong;

    // Reading buffers
    // Code statement
    private NetworkBuffer readBufferAsciiShort;
    // Code statement
    private NetworkBuffer readBufferAsciiMedium;
    // Code statement
    private NetworkBuffer readBufferAsciiLong;
    // Code statement
    private NetworkBuffer readBufferUnicodeShort;
    // Code statement
    private NetworkBuffer readBufferUnicodeMedium;
    // Code statement
    private NetworkBuffer readBufferUnicodeLong;
    // Code statement
    private NetworkBuffer readBufferMixedShort;
    // Code statement
    private NetworkBuffer readBufferMixedMedium;
    // Code statement
    private NetworkBuffer readBufferMixedLong;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Initialize write buffers
        // Calls a method
        writeBufferAsciiShort = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferAsciiMedium = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferAsciiLong = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferUnicodeShort = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferUnicodeMedium = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferUnicodeLong = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferMixedShort = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferMixedMedium = NetworkBuffer.resizableBuffer();
        // Calls a method
        writeBufferMixedLong = NetworkBuffer.resizableBuffer();

        // Initialize and pre-fill read buffers
        // Calls a method
        readBufferAsciiShort = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferAsciiShort.write(NetworkBuffer.STRING_IO_UTF8, ASCII_SHORT);

        // Calls a method
        readBufferAsciiMedium = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferAsciiMedium.write(NetworkBuffer.STRING_IO_UTF8, ASCII_MEDIUM);

        // Calls a method
        readBufferAsciiLong = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferAsciiLong.write(NetworkBuffer.STRING_IO_UTF8, ASCII_LONG);

        // Calls a method
        readBufferUnicodeShort = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferUnicodeShort.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_SHORT);

        // Calls a method
        readBufferUnicodeMedium = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferUnicodeMedium.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_MEDIUM);

        // Calls a method
        readBufferUnicodeLong = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferUnicodeLong.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_LONG);

        // Calls a method
        readBufferMixedShort = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferMixedShort.write(NetworkBuffer.STRING_IO_UTF8, MIXED_SHORT);

        // Calls a method
        readBufferMixedMedium = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferMixedMedium.write(NetworkBuffer.STRING_IO_UTF8, MIXED_MEDIUM);

        // Calls a method
        readBufferMixedLong = NetworkBuffer.resizableBuffer();
        // Calls a method
        readBufferMixedLong.write(NetworkBuffer.STRING_IO_UTF8, MIXED_LONG);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeAsciiShort() {
        // Calls a method
        writeBufferAsciiShort.clear();
        // Calls a method
        writeBufferAsciiShort.write(NetworkBuffer.STRING_IO_UTF8, ASCII_SHORT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeAsciiMedium() {
        // Calls a method
        writeBufferAsciiMedium.clear();
        // Calls a method
        writeBufferAsciiMedium.write(NetworkBuffer.STRING_IO_UTF8, ASCII_MEDIUM);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeAsciiLong() {
        // Calls a method
        writeBufferAsciiLong.clear();
        // Calls a method
        writeBufferAsciiLong.write(NetworkBuffer.STRING_IO_UTF8, ASCII_LONG);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeUnicodeShort() {
        // Calls a method
        writeBufferUnicodeShort.clear();
        // Calls a method
        writeBufferUnicodeShort.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_SHORT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeUnicodeMedium() {
        // Calls a method
        writeBufferUnicodeMedium.clear();
        // Calls a method
        writeBufferUnicodeMedium.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_MEDIUM);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeUnicodeLong() {
        // Calls a method
        writeBufferUnicodeLong.clear();
        // Calls a method
        writeBufferUnicodeLong.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_LONG);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeMixedShort() {
        // Calls a method
        writeBufferMixedShort.clear();
        // Calls a method
        writeBufferMixedShort.write(NetworkBuffer.STRING_IO_UTF8, MIXED_SHORT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeMixedMedium() {
        // Calls a method
        writeBufferMixedMedium.clear();
        // Calls a method
        writeBufferMixedMedium.write(NetworkBuffer.STRING_IO_UTF8, MIXED_MEDIUM);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void writeMixedLong() {
        // Calls a method
        writeBufferMixedLong.clear();
        // Calls a method
        writeBufferMixedLong.write(NetworkBuffer.STRING_IO_UTF8, MIXED_LONG);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readAsciiShort(Blackhole blackhole) {
        // Calls a method
        readBufferAsciiShort.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferAsciiShort.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readAsciiMedium(Blackhole blackhole) {
        // Calls a method
        readBufferAsciiMedium.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferAsciiMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readAsciiLong(Blackhole blackhole) {
        // Calls a method
        readBufferAsciiLong.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferAsciiLong.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readUnicodeShort(Blackhole blackhole) {
        // Calls a method
        readBufferUnicodeShort.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferUnicodeShort.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readUnicodeMedium(Blackhole blackhole) {
        // Calls a method
        readBufferUnicodeMedium.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferUnicodeMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readUnicodeLong(Blackhole blackhole) {
        // Calls a method
        readBufferUnicodeLong.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferUnicodeLong.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readMixedShort(Blackhole blackhole) {
        // Calls a method
        readBufferMixedShort.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferMixedShort.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readMixedMedium(Blackhole blackhole) {
        // Calls a method
        readBufferMixedMedium.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferMixedMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void readMixedLong(Blackhole blackhole) {
        // Calls a method
        readBufferMixedLong.readIndex(0);
        // Calls a method
        blackhole.consume(readBufferMixedLong.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void roundTripAsciiShort(Blackhole blackhole) {
        // Calls a method
        writeBufferAsciiShort.clear();
        // Calls a method
        writeBufferAsciiShort.write(NetworkBuffer.STRING_IO_UTF8, ASCII_SHORT);
        // Calls a method
        blackhole.consume(writeBufferAsciiShort.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void roundTripUnicodeMedium(Blackhole blackhole) {
        // Calls a method
        writeBufferUnicodeMedium.clear();
        // Calls a method
        writeBufferUnicodeMedium.write(NetworkBuffer.STRING_IO_UTF8, UNICODE_MEDIUM);
        // Calls a method
        blackhole.consume(writeBufferUnicodeMedium.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void roundTripMixedLong(Blackhole blackhole) {
        // Calls a method
        writeBufferMixedLong.clear();
        // Calls a method
        writeBufferMixedLong.write(NetworkBuffer.STRING_IO_UTF8, MIXED_LONG);
        // Calls a method
        blackhole.consume(writeBufferMixedLong.read(NetworkBuffer.STRING_IO_UTF8));
    // End of a block/expression
    }
// End of a block/expression
}

