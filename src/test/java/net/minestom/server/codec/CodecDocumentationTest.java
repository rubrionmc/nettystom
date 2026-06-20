// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.EndBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.StringBinaryTag;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.ValueSource;

// Type declaration (class/interface/enum/record)
public final class CodecDocumentationTest {


    // Annotation for the following element
    @ParameterizedTest(name = "package-info.java example: null={0}")
    // Annotation for the following element
    @ValueSource(booleans = {true, false})
    // Start of a method/block
    public void testPackageInfoExample(boolean nullName) {
        // Type declaration (class/interface/enum/record)
        record MyType(int id, @Nullable String name) {
            // Assigns a value
            static final StructCodec<MyType> CODEC = StructCodec.struct(
                    // Code statement
                    "id", Codec.INT, MyType::id,
                    // Code statement
                    "name", Codec.STRING.optional(), MyType::name,
                    // Code statement
                    MyType::new
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Assigns a value
        MyType value = new MyType(42, nullName ? null : "Example"); // Or use a null name for no name.
        // Encoding to JSON
        // Calls a method
        JsonElement encoded = MyType.CODEC.encode(Transcoder.JSON, value).orElseThrow();
        // Decoding from JSON
        // Calls a method
        MyType decoded = MyType.CODEC.decode(Transcoder.JSON, encoded).orElseThrow();

        // Calls a method
        Assertions.assertEquals(value, decoded);
    // End of a block/expression
    }


    // Annotation for the following element
    @ParameterizedTest(name = "StructCodec example: null={0}")
    // Annotation for the following element
    @ValueSource(booleans = {true, false})
    // Start of a method/block
    public void testStructCodecExample(boolean nullName) {
        // Type declaration (class/interface/enum/record)
        record MyObject(double coolnessFactor, @Nullable String of) {
            // Assigns a value
            static final StructCodec<MyObject> CODEC = StructCodec.struct(
                    // Code statement
                    "id", Codec.DOUBLE, MyObject::coolnessFactor,
                    // Code statement
                    "name", Codec.STRING.optional(), MyObject::of,
                    // Code statement
                    MyObject::new
            // End of a block/expression
            );

            // Start of a method/block
            public MyObject {
                // Assigns a value
                coolnessFactor = Math.clamp(coolnessFactor, 0.0, 2.0); // Too powerful
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        MyObject value = new MyObject(7.8d, nullName ? null : "me"); // Or use a null name for no name.
        // Encoding to JSON
        // Calls a method
        JsonElement encoded = MyObject.CODEC.encode(Transcoder.JSON, value).orElseThrow();
        // Decoding from JSON
        // Calls a method
        MyObject decoded = MyObject.CODEC.decode(Transcoder.JSON, encoded).orElseThrow();

        // Calls a method
        Assertions.assertEquals(value, decoded);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEncoderExample() {
        // Type declaration (class/interface/enum/record)
        record Name(String imTheBoss) { }
        // Assigns a value
        Encoder<Name> encoder = new Encoder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable Name value) {
                // Branch: checks a condition
                if (value == null) return new Result.Error<>("null");
                // Returns a value to the caller
                return new Result.Ok<>(coder.createString(value.imTheBoss()));
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        Result<BinaryTag> result = encoder.encode(Transcoder.NBT, new Name("me"));
        // Calls a method
        Result<BinaryTag> errorResult = encoder.encode(Transcoder.NBT, null);
        // Calls a method
        Assertions.assertEquals(StringBinaryTag.stringBinaryTag("me"), result.orElseThrow());
        // Calls a method
        CodecAssertions.assertError("null", errorResult);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDecodingExample() {
        // Type declaration (class/interface/enum/record)
        record Name(String imTheBoss) { }
        // Assigns a value
        Decoder<Name> decoder = new Decoder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<Name> decode(Transcoder<D> coder, D value) {
                // Returns a value to the caller
                return coder.getString(value).mapResult(Name::new);
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Calls a method
        Result<Name> result = decoder.decode(Transcoder.NBT, StringBinaryTag.stringBinaryTag("me"));
        // Calls a method
        Result<Name> errorResult = decoder.decode(Transcoder.NBT, EndBinaryTag.endBinaryTag());
        // Calls a method
        Assertions.assertEquals(new Name("me"), result.orElseThrow());
        // Calls a method
        Assertions.assertInstanceOf(Result.Error.class, errorResult);
    // End of a block/expression
    }
// End of a block/expression
}
