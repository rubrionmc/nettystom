// Package declaration for this file
package net.minestom.demo.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.network.packet.server.play.OpenSignEditorPacket;
// Import of a required class
import net.minestom.server.tag.Tag;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class SignHandler implements BlockHandler {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key getKey() {
        // Returns a value to the caller
        return Key.key("minestom:sign");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean onInteract(Interaction interaction) {
        // Code statement
        interaction.getPlayer().sendPacket(
                // Creates a new object
                new OpenSignEditorPacket(
                        // Code statement
                        interaction.getBlockPosition(),
                        // Code statement
                        true
                // End of a block/expression
                )
        // End of a block/expression
        );

        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Tag<?>> getBlockEntityTags() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Tag.NBT("front_text"),
                // Code statement
                Tag.NBT("back_text"),
                // Code statement
                Tag.Boolean("is_waxed")
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
