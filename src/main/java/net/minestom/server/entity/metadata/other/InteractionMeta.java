// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class InteractionMeta extends EntityMeta {
    // Start of a method/block
    public InteractionMeta(@Nullable Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public float getWidth() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Interaction.WIDTH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setWidth(float value) {
        // Calls a method
        metadata.set(MetadataDef.Interaction.WIDTH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getHeight() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Interaction.HEIGHT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHeight(float value) {
        // Calls a method
        metadata.set(MetadataDef.Interaction.HEIGHT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean getResponse() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Interaction.RESPONSIVE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setResponse(boolean response) {
        // Calls a method
        metadata.set(MetadataDef.Interaction.RESPONSIVE, response);
    // End of a block/expression
    }
// End of a block/expression
}
