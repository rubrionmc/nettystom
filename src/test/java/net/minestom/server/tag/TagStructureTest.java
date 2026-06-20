// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.minestom.server.entity.PlayerSkin;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.testing.TestUtils.assertEqualsSNBT;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TagStructureTest {

    // Assigns a value
    private static final Tag<Entry> STRUCTURE_TAG = Tag.Structure("entry", new TagSerializer<>() {
        // Calls a method
        private static final Tag<String> VALUE_TAG = Tag.String("value");

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Entry read(TagReadable reader) {
            // Calls a method
            final String value = reader.getTag(VALUE_TAG);
            // Returns a value to the caller
            return value != null ? new Entry(value) : null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(TagWritable writer, Entry value) {
            // Calls a method
            writer.setTag(VALUE_TAG, value.value);
        // End of a block/expression
        }
    // End of a block/expression
    });

    // Assigns a value
    private static final Tag<Entry> STRUCTURE_TAG2 = Tag.Structure("entry", new TagSerializer<>() {
        // Calls a method
        private static final Tag<String> VALUE_TAG = Tag.String("value2");

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Entry read(TagReadable reader) {
            // Calls a method
            final String value = reader.getTag(VALUE_TAG);
            // Returns a value to the caller
            return value != null ? new Entry(value) : null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(TagWritable writer, Entry value) {
            // Calls a method
            writer.setTag(VALUE_TAG, value.value);
        // End of a block/expression
        }
    // End of a block/expression
    });

    // Type declaration (class/interface/enum/record)
    private record Entry(String value) {
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        assertNull(handler.getTag(STRUCTURE_TAG));
        // Calls a method
        assertFalse(handler.hasTag(STRUCTURE_TAG));

        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(STRUCTURE_TAG, entry);
        // Calls a method
        assertTrue(handler.hasTag(STRUCTURE_TAG));
        // Calls a method
        assertEquals(entry, handler.getTag(STRUCTURE_TAG));

        // Calls a method
        handler.removeTag(STRUCTURE_TAG);
        // Calls a method
        assertFalse(handler.hasTag(STRUCTURE_TAG));
        // Calls a method
        assertNull(handler.getTag(STRUCTURE_TAG));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void snbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var entry = new Entry("hello");
        // Calls a method
        handler.setTag(STRUCTURE_TAG, entry);
        // Code statement
        assertEqualsSNBT("""
                {
                  "entry": {
                    "value":"hello"
                  }
                }
                """, handler.asCompound());

        // Calls a method
        handler.removeTag(STRUCTURE_TAG);
        // Calls a method
        assertEqualsSNBT("{}", handler.asCompound());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void overrideBasic() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        assertNull(handler.getTag(STRUCTURE_TAG));
        // Calls a method
        assertFalse(handler.hasTag(STRUCTURE_TAG));

        // Calls a method
        var entry1 = new Entry("hello");
        // Calls a method
        var entry2 = new Entry("hello2");

        // Add first entry
        // Start of a block
        {
            // Calls a method
            handler.setTag(STRUCTURE_TAG, entry1);
            // Calls a method
            assertTrue(handler.hasTag(STRUCTURE_TAG));
            // Calls a method
            assertEquals(entry1, handler.getTag(STRUCTURE_TAG));
        // End of a block/expression
        }
        // Add second entry
        // Start of a block
        {
            // Calls a method
            handler.setTag(STRUCTURE_TAG2, entry2);
            // Calls a method
            assertTrue(handler.hasTag(STRUCTURE_TAG2));
            // Calls a method
            assertEquals(entry2, handler.getTag(STRUCTURE_TAG2));
            // Assert first
            // Calls a method
            assertFalse(handler.hasTag(STRUCTURE_TAG));
            // Calls a method
            assertNull(handler.getTag(STRUCTURE_TAG));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void overrideNbt() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        var entry1 = new Entry("hello");
        // Calls a method
        var entry2 = new Entry("hello2");
        // Add first entry
        // Start of a block
        {
            // Calls a method
            handler.setTag(STRUCTURE_TAG, entry1);
            // Code statement
            assertEqualsSNBT("""
                    {
                      "entry": {
                        "value":"hello"
                      }
                    }
                    """, handler.asCompound());
        // End of a block/expression
        }
        // Add second entry
        // Start of a block
        {
            // Calls a method
            handler.setTag(STRUCTURE_TAG2, entry2);
            // Code statement
            assertEqualsSNBT("""
                    {
                      "entry": {
                        "value2": "hello2"
                      }
                    }
                    """, handler.asCompound());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void pathOverride() {
        // Calls a method
        var handler = TagHandler.newHandler();
        // Calls a method
        Tag<UUID> uuidTag = Tag.UUID("Id").path("SkullOwner");
        // Assigns a value
        Tag<PlayerSkin> skinTag = Tag.Structure("Properties", new TagSerializer<PlayerSkin>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable PlayerSkin read(TagReadable reader) {
                // Calls a method
                final String value = reader.getTag(Tag.String("Value"));
                // Calls a method
                final String signature = reader.getTag(Tag.String("Signature"));
                // Branch: checks a condition
                if (value == null || signature == null) return null;
                // Returns a value to the caller
                return new PlayerSkin(value, signature);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(TagWritable writer, PlayerSkin value) {
                // Calls a method
                writer.setTag(Tag.String("Value"), value.textures());
                // Calls a method
                writer.setTag(Tag.String("Signature"), value.signature());
            // End of a block/expression
            }
        // Calls a method
        }).path("SkullOwner");
        // Calls a method
        var uuid = UUID.fromString("a4a9f3e7-f8b5-4b8e-8b3d-b8b9f8b9f8b9");
        // Calls a method
        var skin = new PlayerSkin("textures", "signature");
        // Calls a method
        handler.setTag(uuidTag, uuid);
        // Calls a method
        handler.setTag(skinTag, skin);

        // Calls a method
        assertEquals(uuid, handler.getTag(uuidTag));
        // Calls a method
        assertEquals(skin, handler.getTag(skinTag));
        // Code statement
        assertEqualsSNBT("""
                {
                   "SkullOwner":{
                      "Id":[I;-1532365849,-122336370,-1958889287,-122029895],
                      "Properties":{"Signature":"signature","Value":"textures"}
                   }
                }
                """, handler.asCompound());
    // End of a block/expression
    }
// End of a block/expression
}
