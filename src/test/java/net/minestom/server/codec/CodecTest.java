// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.ListBinaryTag;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.Arguments;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.MethodSource;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Import statique d'un membre
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public final class CodecTest {

    // Début d'une méthode/d'un bloc
    private static List<Arguments> optionalResults() {
        // Renvoie une valeur à l'appelant
        return nonDestructiveTranscoders().stream().flatMap(transcoder -> Stream.of(
                // Instruction de code
                Arguments.of(transcoder, Codec.BOOLEAN, Boolean.FALSE),
                // Instruction de code
                Arguments.of(transcoder, Codec.INT, 5125),
                // Instruction de code
                Arguments.of(transcoder, Codec.LONG, 5125123L),
                // Instruction de code
                Arguments.of(transcoder, Codec.FLOAT, 0.62143f),
                // Instruction de code
                Arguments.of(transcoder, Codec.DOUBLE, 15.2d),
                // Instruction de code
                Arguments.of(transcoder, Codec.BYTE, (byte) 7),
                // Instruction de code
                Arguments.of(transcoder, Codec.SHORT, (short) 0),
                // Instruction de code
                Arguments.of(transcoder, Codec.STRING, "scary")
        // Appelle une méthode
        )).toList();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static List<Transcoder<?>> nonDestructiveTranscoders() {
        // Renvoie une valeur à l'appelant
        return List.of(Transcoder.NBT, Transcoder.JSON, Transcoder.JAVA);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("optionalResults")
    // Début d'une méthode/d'un bloc
    public <D, T> void testOptionalNullDecode(Transcoder<D> transcoder, Codec<T> codec, T expected) {
        // Appelle une méthode
        var optionalCodec = codec.optional(expected);
        // Appelle une méthode
        var result = optionalCodec.decode(transcoder, transcoder.createNull());
        // Appelle une méthode
        CodecAssertions.assertOk(result);
        // Appelle une méthode
        assertEquals(expected, result.orElseThrow());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("optionalResults")
    // Début d'une méthode/d'un bloc
    public <D, T> void testOptionalEncodeDecodeValue(Transcoder<D> transcoder, Codec<T> codec, T expected) {
        // Appelle une méthode
        var optionalCodec = codec.optional(expected);
        // Appelle une méthode
        var encodeResult = optionalCodec.encode(transcoder, null);
        // Appelle une méthode
        var result = optionalCodec.decode(transcoder, encodeResult.orElseThrow());
        // Appelle une méthode
        CodecAssertions.assertOk(result);
        // Appelle une méthode
        assertEquals(expected, result.orElseThrow());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("DataFlowIssue")
    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("nonDestructiveTranscoders")
    // Début d'une méthode/d'un bloc
    <D> void testListUnmodifiable(Transcoder<D> transcoder) {
        // Appelle une méthode
        List<String> testList = Arrays.asList("Hey", "How", "Are", "You");
        // Appelle une méthode
        var codec = Codec.STRING.list();
        // Appelle une méthode
        var encoded = codec.encode(transcoder, testList);
        // Appelle une méthode
        CodecAssertions.assertOk(encoded);
        // Appelle une méthode
        var decoded = codec.decode(transcoder, encoded.orElseThrow());
        // Appelle une méthode
        CodecAssertions.assertOk(decoded);
        // Appelle une méthode
        var decodedObject = decoded.orElseThrow();
        // Appelle une méthode
        assertEquals(testList, decodedObject);
        // Appelle une méthode
        Assertions.assertDoesNotThrow(() -> testList.set(0, "Test"));
        // Appelle une méthode
        Assertions.assertNotEquals(testList, decodedObject);
        // Appelle une méthode
        Assertions.assertThrows(UnsupportedOperationException.class, () -> decodedObject.set(0, "Test"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("DataFlowIssue")
    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("nonDestructiveTranscoders")
    // Début d'une méthode/d'un bloc
    <D> void testSetUnmodifiable(Transcoder<D> transcoder) {
        // Appelle une méthode
        var testSet = new HashSet<>(Set.of("Hey", "How", "Are", "You"));
        // Appelle une méthode
        var codec = Codec.STRING.set();
        // Appelle une méthode
        var encoded = codec.encode(transcoder, testSet);
        // Appelle une méthode
        CodecAssertions.assertOk(encoded);
        // Appelle une méthode
        var decoded = codec.decode(transcoder, encoded.orElseThrow());
        // Appelle une méthode
        CodecAssertions.assertOk(decoded);
        // Appelle une méthode
        var decodedObject = decoded.orElseThrow();
        // Appelle une méthode
        assertEquals(testSet, decodedObject);
        // Appelle une méthode
        Assertions.assertDoesNotThrow(() -> testSet.remove("Hey"));
        // Appelle une méthode
        Assertions.assertNotEquals(testSet, decodedObject);
        // Appelle une méthode
        Assertions.assertThrows(UnsupportedOperationException.class, () -> decodedObject.remove("Hey"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("DataFlowIssue")
    // Annotation pour l'élément suivant
    @ParameterizedTest
    // Annotation pour l'élément suivant
    @MethodSource("nonDestructiveTranscoders")
    // Début d'une méthode/d'un bloc
    <D> void testMapUnmodifiable(Transcoder<D> transcoder) {
        // Appelle une méthode
        var testSet = new HashMap<>(Map.of("Hey", "How", "Are", "You"));
        // Appelle une méthode
        var codec = Codec.STRING.mapValue(Codec.STRING);
        // Appelle une méthode
        var encoded = codec.encode(transcoder, testSet);
        // Appelle une méthode
        CodecAssertions.assertOk(encoded);
        // Appelle une méthode
        var decoded = codec.decode(transcoder, encoded.orElseThrow());
        // Appelle une méthode
        CodecAssertions.assertOk(decoded);
        // Appelle une méthode
        var decodedObject = decoded.orElseThrow();
        // Appelle une méthode
        assertEquals(testSet, decodedObject);
        // Appelle une méthode
        Assertions.assertDoesNotThrow(() -> testSet.remove("Hey"));
        // Appelle une méthode
        Assertions.assertNotEquals(testSet, decodedObject);
        // Appelle une méthode
        Assertions.assertThrows(UnsupportedOperationException.class, () -> decodedObject.remove("Hey"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void listOrSingleSingleValueAsSingle() {
        // Appelle une méthode
        var codec = Codec.INT.listOrSingle();
        // Appelle une méthode
        var value = codec.encode(Transcoder.NBT, List.of(42)).orElseThrow();
        // Appelle une méthode
        assertEquals(intBinaryTag(42), value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void listOrSingleMultiValueAsList() {
        // Appelle une méthode
        var codec = Codec.INT.listOrSingle();
        // Appelle une méthode
        var value = codec.encode(Transcoder.NBT, List.of(42, 24)).orElseThrow();
        // Instruction de code
        assertEquals(ListBinaryTag.builder()
                             // Instruction de code
                             .add(intBinaryTag(42))
                             // Instruction de code
                             .add(intBinaryTag(24))
                             // Appelle une méthode
                             .build(), value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
