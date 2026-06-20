// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
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
public class CodecBenchmark {

    // Type declaration (class/interface/enum/record)
    public enum TranscoderKind {
        // Code statement
        NBT(Transcoder.NBT),
        // Code statement
        JSON(Transcoder.JSON),
        // Calls a method
        JAVA(Transcoder.JAVA);

        // Code statement
        final Transcoder<?> transcoder;

        // Start of a method/block
        TranscoderKind(Transcoder<?> transcoder) {
            // Access to the current/parent object
            this.transcoder = transcoder;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum CodecKind {
        // Code statement
        INT(Codec.INT, 42),
        // Code statement
        STRING(Codec.STRING, "Hello, World!"),
        // Code statement
        OPTIONAL_PRESENT(Codec.STRING.optional(), "value"),
        // Code statement
        OPTIONAL_ABSENT(Codec.STRING.optional(), null),
        // Code statement
        ENUM(Codec.Enum(SampleEnum.class), SampleEnum.BAR),
        // Code statement
        LIST_INT(Codec.INT.list(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
        // Code statement
        MAP_STRING_INT(Codec.STRING.mapValue(Codec.INT), Map.of("a", 1, "b", 2, "c", 3, "d", 4)),
        // Code statement
        STRUCT_SMALL(SmallStruct.CODEC, new SmallStruct(7, "name")),
        // Code statement
        STRUCT_LARGE(LargeStruct.CODEC, new LargeStruct(1, 2L, 3.0f, 4.0, "five", true, List.of(6, 7), "eight", 9)),
        // Calls a method
        STRUCT_NESTED(NestedStruct.CODEC, new NestedStruct("outer", new SmallStruct(1, "inner")));

        // Code statement
        final Codec<Object> codec;
        // Code statement
        final Object value;

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Start of a method/block
        <T> CodecKind(Codec<T> codec, T value) {
            // Access to the current/parent object
            this.codec = (Codec<Object>) codec;
            // Access to the current/parent object
            this.value = value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Param
    // Code statement
    public CodecKind codec;

    // Annotation for the following element
    @Param
    // Code statement
    public TranscoderKind transcoder;

    // Code statement
    private Transcoder<Object> activeTranscoder;
    // Code statement
    private Object encoded;

    // Annotation for the following element
    @Setup
    // Annotation for the following element
    @SuppressWarnings({"rawtypes", "unchecked"})
    // Start of a method/block
    public void setup() {
        // Access to the current/parent object
        this.activeTranscoder = (Transcoder) transcoder.transcoder;
        // Access to the current/parent object
        this.encoded = codec.codec.encode(activeTranscoder, codec.value).orElseThrow();
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void encode(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(codec.codec.encode(activeTranscoder, codec.value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void decode(Blackhole blackhole) {
        // Calls a method
        blackhole.consume(codec.codec.decode(activeTranscoder, encoded));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum SampleEnum {FOO, BAR, BAZ}

    // Type declaration (class/interface/enum/record)
    public record SmallStruct(int id, String name) {
        // Assigns a value
        public static final StructCodec<SmallStruct> CODEC = StructCodec.struct(
                // Code statement
                "id", Codec.INT, SmallStruct::id,
                // Code statement
                "name", Codec.STRING, SmallStruct::name,
                // Code statement
                SmallStruct::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record LargeStruct(int a, long b, float c, double d, String e, boolean f, List<Integer> g, String h, int i) {
        // Assigns a value
        public static final StructCodec<LargeStruct> CODEC = StructCodec.struct(
                // Code statement
                "a", Codec.INT, LargeStruct::a,
                // Code statement
                "b", Codec.LONG, LargeStruct::b,
                // Code statement
                "c", Codec.FLOAT, LargeStruct::c,
                // Code statement
                "d", Codec.DOUBLE, LargeStruct::d,
                // Code statement
                "e", Codec.STRING, LargeStruct::e,
                // Code statement
                "f", Codec.BOOLEAN, LargeStruct::f,
                // Code statement
                "g", Codec.INT.list(), LargeStruct::g,
                // Code statement
                "h", Codec.STRING, LargeStruct::h,
                // Code statement
                "i", Codec.INT, LargeStruct::i,
                // Code statement
                LargeStruct::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record NestedStruct(String label, SmallStruct inner) {
        // Assigns a value
        public static final StructCodec<NestedStruct> CODEC = StructCodec.struct(
                // Code statement
                "label", Codec.STRING, NestedStruct::label,
                // Code statement
                "inner", SmallStruct.CODEC, NestedStruct::inner,
                // Code statement
                NestedStruct::new);
    // End of a block/expression
    }
// End of a block/expression
}
