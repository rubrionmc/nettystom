// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.EndBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.StringBinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;
// Import d'une classe nécessaire
import org.junit.jupiter.params.ParameterizedTest;
// Import d'une classe nécessaire
import org.junit.jupiter.params.provider.ValueSource;

// Déclaration de type (classe/interface/enum/record)
public final class CodecDocumentationTest {


    // Annotation pour l'élément suivant
    @ParameterizedTest(name = "package-info.java example: null={0}")
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {true, false})
    // Début d'une méthode/d'un bloc
    public void testPackageInfoExample(boolean nullName) {
        // Déclaration de type (classe/interface/enum/record)
        record MyType(int id, @Nullable String name) {
            // Affecte une valeur
            static final StructCodec<MyType> CODEC = StructCodec.struct(
                    // Instruction de code
                    "id", Codec.INT, MyType::id,
                    // Instruction de code
                    "name", Codec.STRING.optional(), MyType::name,
                    // Instruction de code
                    MyType::new
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        MyType value = new MyType(42, nullName ? null : "Example"); // Or use a null name for no name.
        // Encoding to JSON
        // Appelle une méthode
        JsonElement encoded = MyType.CODEC.encode(Transcoder.JSON, value).orElseThrow();
        // Decoding from JSON
        // Appelle une méthode
        MyType decoded = MyType.CODEC.decode(Transcoder.JSON, encoded).orElseThrow();

        // Appelle une méthode
        Assertions.assertEquals(value, decoded);
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @ParameterizedTest(name = "StructCodec example: null={0}")
    // Annotation pour l'élément suivant
    @ValueSource(booleans = {true, false})
    // Début d'une méthode/d'un bloc
    public void testStructCodecExample(boolean nullName) {
        // Déclaration de type (classe/interface/enum/record)
        record MyObject(double coolnessFactor, @Nullable String of) {
            // Affecte une valeur
            static final StructCodec<MyObject> CODEC = StructCodec.struct(
                    // Instruction de code
                    "id", Codec.DOUBLE, MyObject::coolnessFactor,
                    // Instruction de code
                    "name", Codec.STRING.optional(), MyObject::of,
                    // Instruction de code
                    MyObject::new
            // Fin d'un bloc/d'une expression
            );

            // Début d'une méthode/d'un bloc
            public MyObject {
                // Affecte une valeur
                coolnessFactor = Math.clamp(coolnessFactor, 0.0, 2.0); // Too powerful
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        MyObject value = new MyObject(7.8d, nullName ? null : "me"); // Or use a null name for no name.
        // Encoding to JSON
        // Appelle une méthode
        JsonElement encoded = MyObject.CODEC.encode(Transcoder.JSON, value).orElseThrow();
        // Decoding from JSON
        // Appelle une méthode
        MyObject decoded = MyObject.CODEC.decode(Transcoder.JSON, encoded).orElseThrow();

        // Appelle une méthode
        Assertions.assertEquals(value, decoded);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testEncoderExample() {
        // Déclaration de type (classe/interface/enum/record)
        record Name(String imTheBoss) { }
        // Affecte une valeur
        Encoder<Name> encoder = new Encoder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable Name value) {
                // Embranchement : vérifie une condition
                if (value == null) return new Result.Error<>("null");
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(coder.createString(value.imTheBoss()));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        Result<BinaryTag> result = encoder.encode(Transcoder.NBT, new Name("me"));
        // Appelle une méthode
        Result<BinaryTag> errorResult = encoder.encode(Transcoder.NBT, null);
        // Appelle une méthode
        Assertions.assertEquals(StringBinaryTag.stringBinaryTag("me"), result.orElseThrow());
        // Appelle une méthode
        CodecAssertions.assertError("null", errorResult);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDecodingExample() {
        // Déclaration de type (classe/interface/enum/record)
        record Name(String imTheBoss) { }
        // Affecte une valeur
        Decoder<Name> decoder = new Decoder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<Name> decode(Transcoder<D> coder, D value) {
                // Renvoie une valeur à l'appelant
                return coder.getString(value).mapResult(Name::new);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Appelle une méthode
        Result<Name> result = decoder.decode(Transcoder.NBT, StringBinaryTag.stringBinaryTag("me"));
        // Appelle une méthode
        Result<Name> errorResult = decoder.decode(Transcoder.NBT, EndBinaryTag.endBinaryTag());
        // Appelle une méthode
        Assertions.assertEquals(new Name("me"), result.orElseThrow());
        // Appelle une méthode
        Assertions.assertInstanceOf(Result.Error.class, errorResult);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
