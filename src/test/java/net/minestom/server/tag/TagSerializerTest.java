// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.item.component.FireworkExplosion;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;

// Type declaration (class/interface/enum/record)
public class TagSerializerTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void fromCompound(){
        // Assigns a value
        var serializer = TagSerializer.fromCompound(
                // Code statement
                c -> assertOk(FireworkExplosion.CODEC.decode(Transcoder.NBT, c)),
                // Calls a method
                explosion -> (CompoundBinaryTag) assertOk(FireworkExplosion.CODEC.encode(Transcoder.NBT, explosion)));
        // Calls a method
        var effect = new FireworkExplosion(FireworkExplosion.Shape.BURST, List.of(), List.of(), false, false);
        // Calls a method
        TagHandler handler = TagHandler.newHandler();
        // Calls a method
        serializer.write(handler, effect);
        // Calls a method
        Assertions.assertEquals(effect, serializer.read(handler));
    // End of a block/expression
    }
// End of a block/expression
}
