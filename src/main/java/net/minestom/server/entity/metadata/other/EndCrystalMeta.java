// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.coordinate.Point;
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
public class EndCrystalMeta extends EntityMeta {
    // Start of a method/block
    public EndCrystalMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Point getBeamTarget() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.EndCrystal.BEAM_TARGET);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBeamTarget(@Nullable Point value) {
        // Calls a method
        metadata.set(MetadataDef.EndCrystal.BEAM_TARGET, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isShowingBottom() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.EndCrystal.SHOW_BOTTOM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShowingBottom(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.EndCrystal.SHOW_BOTTOM, value);
    // End of a block/expression
    }

// End of a block/expression
}
