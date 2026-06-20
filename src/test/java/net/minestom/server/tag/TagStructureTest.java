// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerSkin;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TagStructureTest {

    // Affecte une valeur
    private static final Tag<Entry> STRUCTURE_TAG = Tag.Structure("entry", new TagSerializer<>() {
        // Appelle une méthode
        private static final Tag<String> VALUE_TAG = Tag.String("value");

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Entry read(TagReadable reader) {
            // Appelle une méthode
            final String value = reader.getTag(VALUE_TAG);
            // Renvoie une valeur à l'appelant
            return value != null ? new Entry(value) : null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(TagWritable writer, Entry value) {
            // Appelle une méthode
            writer.setTag(VALUE_TAG, value.value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    });

    // Affecte une valeur
    private static final Tag<Entry> STRUCTURE_TAG2 = Tag.Structure("entry", new TagSerializer<>() {
        // Appelle une méthode
        private static final Tag<String> VALUE_TAG = Tag.String("value2");

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Entry read(TagReadable reader) {
            // Appelle une méthode
            final String value = reader.getTag(VALUE_TAG);
            // Renvoie une valeur à l'appelant
            return value != null ? new Entry(value) : null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(TagWritable writer, Entry value) {
            // Appelle une méthode
            writer.setTag(VALUE_TAG, value.value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    });

    // Déclaration de type (classe/interface/enum/record)
    private record Entry(String value) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        assertNull(handler.getTag(STRUCTURE_TAG));
        // Appelle une méthode
        assertFalse(handler.hasTag(STRUCTURE_TAG));

        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(STRUCTURE_TAG, entry);
        // Appelle une méthode
        assertTrue(handler.hasTag(STRUCTURE_TAG));
        // Appelle une méthode
        assertEquals(entry, handler.getTag(STRUCTURE_TAG));

        // Appelle une méthode
        handler.removeTag(STRUCTURE_TAG);
        // Appelle une méthode
        assertFalse(handler.hasTag(STRUCTURE_TAG));
        // Appelle une méthode
        assertNull(handler.getTag(STRUCTURE_TAG));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void snbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var entry = new Entry("hello");
        // Appelle une méthode
        handler.setTag(STRUCTURE_TAG, entry);
        // Instruction de code
        assertEqualsSNBT("""
                {
                  "entry": {
                    "value":"hello"
                  }
                }
                """, handler.asCompound());

        // Appelle une méthode
        handler.removeTag(STRUCTURE_TAG);
        // Appelle une méthode
        assertEqualsSNBT("{}", handler.asCompound());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void overrideBasic() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        assertNull(handler.getTag(STRUCTURE_TAG));
        // Appelle une méthode
        assertFalse(handler.hasTag(STRUCTURE_TAG));

        // Appelle une méthode
        var entry1 = new Entry("hello");
        // Appelle une méthode
        var entry2 = new Entry("hello2");

        // Add first entry
        // Début d'un bloc
        {
            // Appelle une méthode
            handler.setTag(STRUCTURE_TAG, entry1);
            // Appelle une méthode
            assertTrue(handler.hasTag(STRUCTURE_TAG));
            // Appelle une méthode
            assertEquals(entry1, handler.getTag(STRUCTURE_TAG));
        // Fin d'un bloc/d'une expression
        }
        // Add second entry
        // Début d'un bloc
        {
            // Appelle une méthode
            handler.setTag(STRUCTURE_TAG2, entry2);
            // Appelle une méthode
            assertTrue(handler.hasTag(STRUCTURE_TAG2));
            // Appelle une méthode
            assertEquals(entry2, handler.getTag(STRUCTURE_TAG2));
            // Assert first
            // Appelle une méthode
            assertFalse(handler.hasTag(STRUCTURE_TAG));
            // Appelle une méthode
            assertNull(handler.getTag(STRUCTURE_TAG));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void overrideNbt() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        var entry1 = new Entry("hello");
        // Appelle une méthode
        var entry2 = new Entry("hello2");
        // Add first entry
        // Début d'un bloc
        {
            // Appelle une méthode
            handler.setTag(STRUCTURE_TAG, entry1);
            // Instruction de code
            assertEqualsSNBT("""
                    {
                      "entry": {
                        "value":"hello"
                      }
                    }
                    """, handler.asCompound());
        // Fin d'un bloc/d'une expression
        }
        // Add second entry
        // Début d'un bloc
        {
            // Appelle une méthode
            handler.setTag(STRUCTURE_TAG2, entry2);
            // Instruction de code
            assertEqualsSNBT("""
                    {
                      "entry": {
                        "value2": "hello2"
                      }
                    }
                    """, handler.asCompound());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void pathOverride() {
        // Appelle une méthode
        var handler = TagHandler.newHandler();
        // Appelle une méthode
        Tag<UUID> uuidTag = Tag.UUID("Id").path("SkullOwner");
        // Affecte une valeur
        Tag<PlayerSkin> skinTag = Tag.Structure("Properties", new TagSerializer<PlayerSkin>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable PlayerSkin read(TagReadable reader) {
                // Appelle une méthode
                final String value = reader.getTag(Tag.String("Value"));
                // Appelle une méthode
                final String signature = reader.getTag(Tag.String("Signature"));
                // Embranchement : vérifie une condition
                if (value == null || signature == null) return null;
                // Renvoie une valeur à l'appelant
                return new PlayerSkin(value, signature);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(TagWritable writer, PlayerSkin value) {
                // Appelle une méthode
                writer.setTag(Tag.String("Value"), value.textures());
                // Appelle une méthode
                writer.setTag(Tag.String("Signature"), value.signature());
            // Fin d'un bloc/d'une expression
            }
        // Appelle une méthode
        }).path("SkullOwner");
        // Appelle une méthode
        var uuid = UUID.fromString("a4a9f3e7-f8b5-4b8e-8b3d-b8b9f8b9f8b9");
        // Appelle une méthode
        var skin = new PlayerSkin("textures", "signature");
        // Appelle une méthode
        handler.setTag(uuidTag, uuid);
        // Appelle une méthode
        handler.setTag(skinTag, skin);

        // Appelle une méthode
        assertEquals(uuid, handler.getTag(uuidTag));
        // Appelle une méthode
        assertEquals(skin, handler.getTag(skinTag));
        // Instruction de code
        assertEqualsSNBT("""
                {
                   "SkullOwner":{
                      "Id":[I;-1532365849,-122336370,-1958889287,-122029895],
                      "Properties":{"Signature":"signature","Value":"textures"}
                   }
                }
                """, handler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
