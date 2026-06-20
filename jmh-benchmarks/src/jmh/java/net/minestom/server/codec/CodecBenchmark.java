// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
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
public class CodecBenchmark {

    // Déclaration de type (classe/interface/enum/record)
    public enum TranscoderKind {
        // Instruction de code
        NBT(Transcoder.NBT),
        // Instruction de code
        JSON(Transcoder.JSON),
        // Appelle une méthode
        JAVA(Transcoder.JAVA);

        // Instruction de code
        final Transcoder<?> transcoder;

        // Début d'une méthode/d'un bloc
        TranscoderKind(Transcoder<?> transcoder) {
            // Accès à l'objet courant/parent
            this.transcoder = transcoder;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum CodecKind {
        // Instruction de code
        INT(Codec.INT, 42),
        // Instruction de code
        STRING(Codec.STRING, "Hello, World!"),
        // Instruction de code
        OPTIONAL_PRESENT(Codec.STRING.optional(), "value"),
        // Instruction de code
        OPTIONAL_ABSENT(Codec.STRING.optional(), null),
        // Instruction de code
        ENUM(Codec.Enum(SampleEnum.class), SampleEnum.BAR),
        // Instruction de code
        LIST_INT(Codec.INT.list(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
        // Instruction de code
        MAP_STRING_INT(Codec.STRING.mapValue(Codec.INT), Map.of("a", 1, "b", 2, "c", 3, "d", 4)),
        // Instruction de code
        STRUCT_SMALL(SmallStruct.CODEC, new SmallStruct(7, "name")),
        // Instruction de code
        STRUCT_LARGE(LargeStruct.CODEC, new LargeStruct(1, 2L, 3.0f, 4.0, "five", true, List.of(6, 7), "eight", 9)),
        // Appelle une méthode
        STRUCT_NESTED(NestedStruct.CODEC, new NestedStruct("outer", new SmallStruct(1, "inner")));

        // Instruction de code
        final Codec<Object> codec;
        // Instruction de code
        final Object value;

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Début d'une méthode/d'un bloc
        <T> CodecKind(Codec<T> codec, T value) {
            // Accès à l'objet courant/parent
            this.codec = (Codec<Object>) codec;
            // Accès à l'objet courant/parent
            this.value = value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Param
    // Instruction de code
    public CodecKind codec;

    // Annotation pour l'élément suivant
    @Param
    // Instruction de code
    public TranscoderKind transcoder;

    // Instruction de code
    private Transcoder<Object> activeTranscoder;
    // Instruction de code
    private Object encoded;

    // Annotation pour l'élément suivant
    @Setup
    // Annotation pour l'élément suivant
    @SuppressWarnings({"rawtypes", "unchecked"})
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Accès à l'objet courant/parent
        this.activeTranscoder = (Transcoder) transcoder.transcoder;
        // Accès à l'objet courant/parent
        this.encoded = codec.codec.encode(activeTranscoder, codec.value).orElseThrow();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void encode(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(codec.codec.encode(activeTranscoder, codec.value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void decode(Blackhole blackhole) {
        // Appelle une méthode
        blackhole.consume(codec.codec.decode(activeTranscoder, encoded));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum SampleEnum {FOO, BAR, BAZ}

    // Déclaration de type (classe/interface/enum/record)
    public record SmallStruct(int id, String name) {
        // Affecte une valeur
        public static final StructCodec<SmallStruct> CODEC = StructCodec.struct(
                // Instruction de code
                "id", Codec.INT, SmallStruct::id,
                // Instruction de code
                "name", Codec.STRING, SmallStruct::name,
                // Instruction de code
                SmallStruct::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record LargeStruct(int a, long b, float c, double d, String e, boolean f, List<Integer> g, String h, int i) {
        // Affecte une valeur
        public static final StructCodec<LargeStruct> CODEC = StructCodec.struct(
                // Instruction de code
                "a", Codec.INT, LargeStruct::a,
                // Instruction de code
                "b", Codec.LONG, LargeStruct::b,
                // Instruction de code
                "c", Codec.FLOAT, LargeStruct::c,
                // Instruction de code
                "d", Codec.DOUBLE, LargeStruct::d,
                // Instruction de code
                "e", Codec.STRING, LargeStruct::e,
                // Instruction de code
                "f", Codec.BOOLEAN, LargeStruct::f,
                // Instruction de code
                "g", Codec.INT.list(), LargeStruct::g,
                // Instruction de code
                "h", Codec.STRING, LargeStruct::h,
                // Instruction de code
                "i", Codec.INT, LargeStruct::i,
                // Instruction de code
                LargeStruct::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record NestedStruct(String label, SmallStruct inner) {
        // Affecte une valeur
        public static final StructCodec<NestedStruct> CODEC = StructCodec.struct(
                // Instruction de code
                "label", Codec.STRING, NestedStruct::label,
                // Instruction de code
                "inner", SmallStruct.CODEC, NestedStruct::inner,
                // Instruction de code
                NestedStruct::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
