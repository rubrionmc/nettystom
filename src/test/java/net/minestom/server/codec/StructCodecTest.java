// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import com.google.gson.JsonParser;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertError;
// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class StructCodecTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void emptyObject() {
        // Type declaration (class/interface/enum/record)
        record Empty() {
        // End of a block/expression
        }

        // Calls a method
        var codec = StructCodec.struct(Empty::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Calls a method
        assertEquals(new Empty(), assertOk(result));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleField() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING, TheObject::name,
                // Code statement
                TheObject::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{name: \"test\"}"));
        // Calls a method
        assertEquals(new TheObject("test"), assertOk(result));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleFieldMissing() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING, TheObject::name,
                // Code statement
                TheObject::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Calls a method
        assertError("name: No such key: name", result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleFieldOptionalMissing() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING.optional(), TheObject::name,
                // Code statement
                TheObject::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Calls a method
        assertEquals(new TheObject(null), assertOk(result));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleFieldOptionalMissingDefault() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING.optional("defaultValue"), TheObject::name,
                // Code statement
                TheObject::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Calls a method
        assertEquals(new TheObject("defaultValue"), assertOk(result));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleFieldOptionalIncorrectTypeButNotMissing() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING.optional(), TheObject::name,
                // Code statement
                TheObject::new
        // End of a block/expression
        );
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{\"name\": 2}"));
        // Calls a method
        assertError("name: Not a string: IntBinaryTagImpl[value=2]", result);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleFieldOptionalExplicitJsonNull() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING.optional(), TheObject::name,
                // Code statement
                TheObject::new);
        // Calls a method
        var json = JsonParser.parseString("{\"name\": null}");
        // Calls a method
        assertEquals(new TheObject(null), assertOk(codec.decode(Transcoder.JSON, json)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void singleFieldOptionalExplicitJsonNullWithDefault() {
        // Type declaration (class/interface/enum/record)
        record TheObject(String name) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING.optional("defaultValue"), TheObject::name,
                // Code statement
                TheObject::new);
        // Calls a method
        var json = JsonParser.parseString("{\"name\": null}");
        // Calls a method
        assertEquals(new TheObject("defaultValue"), assertOk(codec.decode(Transcoder.JSON, json)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void inlineField() {
        // Type declaration (class/interface/enum/record)
        record InnerObject(String value) {
        // End of a block/expression
        }
        // Type declaration (class/interface/enum/record)
        record TheObject(String name, InnerObject inner) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING, TheObject::name,
                // Code statement
                StructCodec.INLINE, StructCodec.struct(
                        // Code statement
                        "value", Codec.STRING, InnerObject::value,
                        // Code statement
                        InnerObject::new
                // Code statement
                ), TheObject::inner,
                // Code statement
                TheObject::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{name: \"test\", value: \"innerValue\"}"));
        // Calls a method
        assertEquals(new TheObject("test", new InnerObject("innerValue")), assertOk(result));

        // Calls a method
        var encodeResult = codec.encode(TranscoderNbtImpl.INSTANCE, new TheObject("test", new InnerObject("innerValue")));
        // Calls a method
        assertEquals(snbt("{name: \"test\", value: \"innerValue\"}"), assertOk(encodeResult));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void inlineFieldEmpty() {
        // Type declaration (class/interface/enum/record)
        record InnerObject(String value) {
        // End of a block/expression
        }
        // Type declaration (class/interface/enum/record)
        record TheObject(String name, InnerObject inner) {
        // End of a block/expression
        }

        // Assigns a value
        var codec = StructCodec.struct(
                // Code statement
                "name", Codec.STRING, TheObject::name,
                // Code statement
                StructCodec.INLINE, StructCodec.struct(
                        // Code statement
                        "value", Codec.STRING, InnerObject::value,
                        // Code statement
                        InnerObject::new
                // Code statement
                ), TheObject::inner,
                // Code statement
                TheObject::new);
        // Calls a method
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{name: \"test\", value: \"innerValue\"}"));
        // Calls a method
        assertEquals(new TheObject("test", new InnerObject("innerValue")), assertOk(result));

        // Calls a method
        var encodeResult = codec.encode(TranscoderNbtImpl.INSTANCE, new TheObject("test", new InnerObject("innerValue")));
        // Calls a method
        assertEquals(snbt("{name: \"test\", value: \"innerValue\"}"), assertOk(encodeResult));
    // End of a block/expression
    }

    // Start of a method/block
    private BinaryTag snbt(String snbt) {
        // Returns a value to the caller
        return assertDoesNotThrow(() -> MinestomAdventure.tagStringIO().asTag(snbt));
    // End of a block/expression
    }


// End of a block/expression
}
