// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class CreakingMeta extends MonsterMeta {
    // Start of a method/block
    public CreakingMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean canMove() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Creaking.CAN_MOVE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCanMove(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Creaking.CAN_MOVE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isActive() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Creaking.IS_ACTIVE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setActive(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Creaking.IS_ACTIVE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isTearingDown() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Creaking.IS_TEARING_DOWN);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTearingDown(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Creaking.IS_TEARING_DOWN, value);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Point getHomePos() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Creaking.HOME_POS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHomePos(@Nullable Point value) {
        // Calls a method
        metadata.set(MetadataDef.Creaking.HOME_POS, value);
    // End of a block/expression
    }
// End of a block/expression
}
