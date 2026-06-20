// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.kyori.adventure.nbt.ListBinaryTag;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public final class CodecTest {

    // Start of a method/block
    private static List<Arguments> optionalResults() {
        // Returns a value to the caller
        return nonDestructiveTranscoders().stream().flatMap(transcoder -> Stream.of(
                // Code statement
                Arguments.of(transcoder, Codec.BOOLEAN, Boolean.FALSE),
                // Code statement
                Arguments.of(transcoder, Codec.INT, 5125),
                // Code statement
                Arguments.of(transcoder, Codec.LONG, 5125123L),
                // Code statement
                Arguments.of(transcoder, Codec.FLOAT, 0.62143f),
                // Code statement
                Arguments.of(transcoder, Codec.DOUBLE, 15.2d),
                // Code statement
                Arguments.of(transcoder, Codec.BYTE, (byte) 7),
                // Code statement
                Arguments.of(transcoder, Codec.SHORT, (short) 0),
                // Code statement
                Arguments.of(transcoder, Codec.STRING, "scary")
        // Calls a method
        )).toList();
    // End of a block/expression
    }

    // Start of a method/block
    private static List<Transcoder<?>> nonDestructiveTranscoders() {
        // Returns a value to the caller
        return List.of(Transcoder.NBT, Transcoder.JSON, Transcoder.JAVA);
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("optionalResults")
    // Start of a method/block
    public <D, T> void testOptionalNullDecode(Transcoder<D> transcoder, Codec<T> codec, T expected) {
        // Calls a method
        var optionalCodec = codec.optional(expected);
        // Calls a method
        var result = optionalCodec.decode(transcoder, transcoder.createNull());
        // Calls a method
        CodecAssertions.assertOk(result);
        // Calls a method
        assertEquals(expected, result.orElseThrow());
    // End of a block/expression
    }

    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("optionalResults")
    // Start of a method/block
    public <D, T> void testOptionalEncodeDecodeValue(Transcoder<D> transcoder, Codec<T> codec, T expected) {
        // Calls a method
        var optionalCodec = codec.optional(expected);
        // Calls a method
        var encodeResult = optionalCodec.encode(transcoder, null);
        // Calls a method
        var result = optionalCodec.decode(transcoder, encodeResult.orElseThrow());
        // Calls a method
        CodecAssertions.assertOk(result);
        // Calls a method
        assertEquals(expected, result.orElseThrow());
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("DataFlowIssue")
    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("nonDestructiveTranscoders")
    // Start of a method/block
    <D> void testListUnmodifiable(Transcoder<D> transcoder) {
        // Calls a method
        List<String> testList = Arrays.asList("Hey", "How", "Are", "You");
        // Calls a method
        var codec = Codec.STRING.list();
        // Calls a method
        var encoded = codec.encode(transcoder, testList);
        // Calls a method
        CodecAssertions.assertOk(encoded);
        // Calls a method
        var decoded = codec.decode(transcoder, encoded.orElseThrow());
        // Calls a method
        CodecAssertions.assertOk(decoded);
        // Calls a method
        var decodedObject = decoded.orElseThrow();
        // Calls a method
        assertEquals(testList, decodedObject);
        // Calls a method
        Assertions.assertDoesNotThrow(() -> testList.set(0, "Test"));
        // Calls a method
        Assertions.assertNotEquals(testList, decodedObject);
        // Calls a method
        Assertions.assertThrows(UnsupportedOperationException.class, () -> decodedObject.set(0, "Test"));
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("DataFlowIssue")
    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("nonDestructiveTranscoders")
    // Start of a method/block
    <D> void testSetUnmodifiable(Transcoder<D> transcoder) {
        // Calls a method
        var testSet = new HashSet<>(Set.of("Hey", "How", "Are", "You"));
        // Calls a method
        var codec = Codec.STRING.set();
        // Calls a method
        var encoded = codec.encode(transcoder, testSet);
        // Calls a method
        CodecAssertions.assertOk(encoded);
        // Calls a method
        var decoded = codec.decode(transcoder, encoded.orElseThrow());
        // Calls a method
        CodecAssertions.assertOk(decoded);
        // Calls a method
        var decodedObject = decoded.orElseThrow();
        // Calls a method
        assertEquals(testSet, decodedObject);
        // Calls a method
        Assertions.assertDoesNotThrow(() -> testSet.remove("Hey"));
        // Calls a method
        Assertions.assertNotEquals(testSet, decodedObject);
        // Calls a method
        Assertions.assertThrows(UnsupportedOperationException.class, () -> decodedObject.remove("Hey"));
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("DataFlowIssue")
    // Annotation for the following element
    @ParameterizedTest
    // Annotation for the following element
    @MethodSource("nonDestructiveTranscoders")
    // Start of a method/block
    <D> void testMapUnmodifiable(Transcoder<D> transcoder) {
        // Calls a method
        var testSet = new HashMap<>(Map.of("Hey", "How", "Are", "You"));
        // Calls a method
        var codec = Codec.STRING.mapValue(Codec.STRING);
        // Calls a method
        var encoded = codec.encode(transcoder, testSet);
        // Calls a method
        CodecAssertions.assertOk(encoded);
        // Calls a method
        var decoded = codec.decode(transcoder, encoded.orElseThrow());
        // Calls a method
        CodecAssertions.assertOk(decoded);
        // Calls a method
        var decodedObject = decoded.orElseThrow();
        // Calls a method
        assertEquals(testSet, decodedObject);
        // Calls a method
        Assertions.assertDoesNotThrow(() -> testSet.remove("Hey"));
        // Calls a method
        Assertions.assertNotEquals(testSet, decodedObject);
        // Calls a method
        Assertions.assertThrows(UnsupportedOperationException.class, () -> decodedObject.remove("Hey"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void listOrSingleSingleValueAsSingle() {
        // Calls a method
        var codec = Codec.INT.listOrSingle();
        // Calls a method
        var value = codec.encode(Transcoder.NBT, List.of(42)).orElseThrow();
        // Calls a method
        assertEquals(intBinaryTag(42), value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void listOrSingleMultiValueAsList() {
        // Calls a method
        var codec = Codec.INT.listOrSingle();
        // Calls a method
        var value = codec.encode(Transcoder.NBT, List.of(42, 24)).orElseThrow();
        // Code statement
        assertEquals(ListBinaryTag.builder()
                             // Code statement
                             .add(intBinaryTag(42))
                             // Code statement
                             .add(intBinaryTag(24))
                             // Calls a method
                             .build(), value);
    // End of a block/expression
    }
// End of a block/expression
}
