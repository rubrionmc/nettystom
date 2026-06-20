// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertError;
// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class StructCodecTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void emptyObject() {
        // Déclaration de type (classe/interface/enum/record)
        record Empty() {
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var codec = StructCodec.struct(Empty::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Appelle une méthode
        assertEquals(new Empty(), assertOk(result));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void singleField() {
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING, TheObject::name,
                // Instruction de code
                TheObject::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{name: \"test\"}"));
        // Appelle une méthode
        assertEquals(new TheObject("test"), assertOk(result));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void singleFieldMissing() {
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING, TheObject::name,
                // Instruction de code
                TheObject::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Appelle une méthode
        assertError("name: No such key: name", result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void singleFieldOptionalMissing() {
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING.optional(), TheObject::name,
                // Instruction de code
                TheObject::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Appelle une méthode
        assertEquals(new TheObject(null), assertOk(result));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void singleFieldOptionalMissingDefault() {
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING.optional("defaultValue"), TheObject::name,
                // Instruction de code
                TheObject::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{}"));
        // Appelle une méthode
        assertEquals(new TheObject("defaultValue"), assertOk(result));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void singleFieldOptionalIncorrectTypeButNotMissing() {
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING.optional(), TheObject::name,
                // Instruction de code
                TheObject::new
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{\"name\": 2}"));
        // Appelle une méthode
        assertError("name: Not a string: BinaryTagType[IntBinaryTag 3 (numeric)]{value=2}", result);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void inlineField() {
        // Déclaration de type (classe/interface/enum/record)
        record InnerObject(String value) {
        // Fin d'un bloc/d'une expression
        }
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name, InnerObject inner) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING, TheObject::name,
                // Instruction de code
                StructCodec.INLINE, StructCodec.struct(
                        // Instruction de code
                        "value", Codec.STRING, InnerObject::value,
                        // Instruction de code
                        InnerObject::new
                // Instruction de code
                ), TheObject::inner,
                // Instruction de code
                TheObject::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{name: \"test\", value: \"innerValue\"}"));
        // Appelle une méthode
        assertEquals(new TheObject("test", new InnerObject("innerValue")), assertOk(result));

        // Appelle une méthode
        var encodeResult = codec.encode(TranscoderNbtImpl.INSTANCE, new TheObject("test", new InnerObject("innerValue")));
        // Appelle une méthode
        assertEquals(snbt("{name: \"test\", value: \"innerValue\"}"), assertOk(encodeResult));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void inlineFieldEmpty() {
        // Déclaration de type (classe/interface/enum/record)
        record InnerObject(String value) {
        // Fin d'un bloc/d'une expression
        }
        // Déclaration de type (classe/interface/enum/record)
        record TheObject(String name, InnerObject inner) {
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        var codec = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING, TheObject::name,
                // Instruction de code
                StructCodec.INLINE, StructCodec.struct(
                        // Instruction de code
                        "value", Codec.STRING, InnerObject::value,
                        // Instruction de code
                        InnerObject::new
                // Instruction de code
                ), TheObject::inner,
                // Instruction de code
                TheObject::new);
        // Appelle une méthode
        var result = codec.decode(TranscoderNbtImpl.INSTANCE, snbt("{name: \"test\", value: \"innerValue\"}"));
        // Appelle une méthode
        assertEquals(new TheObject("test", new InnerObject("innerValue")), assertOk(result));

        // Appelle une méthode
        var encodeResult = codec.encode(TranscoderNbtImpl.INSTANCE, new TheObject("test", new InnerObject("innerValue")));
        // Appelle une méthode
        assertEquals(snbt("{name: \"test\", value: \"innerValue\"}"), assertOk(encodeResult));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private BinaryTag snbt(String snbt) {
        // Renvoie une valeur à l'appelant
        return assertDoesNotThrow(() -> MinestomAdventure.tagStringIO().asTag(snbt));
    // Fin d'un bloc/d'une expression
    }


// Fin d'un bloc/d'une expression
}
