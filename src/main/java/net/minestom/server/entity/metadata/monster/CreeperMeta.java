// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class CreeperMeta extends MonsterMeta {
    // Start of a method/block
    public CreeperMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public State getState() {
        // Calls a method
        int id = metadata.get(MetadataDef.Creeper.STATE);
        // Returns a value to the caller
        return id == -1 ? State.IDLE : State.FUSE;
    // End of a block/expression
    }

    // Start of a method/block
    public void setState(State value) {
        // Calls a method
        metadata.set(MetadataDef.Creeper.STATE, value == State.IDLE ? -1 : 1);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isCharged() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Creeper.IS_CHARGED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCharged(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Creeper.IS_CHARGED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isIgnited() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Creeper.IS_IGNITED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setIgnited(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Creeper.IS_IGNITED, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum State {
        // Code statement
        IDLE,
        // Code statement
        FUSE
    // End of a block/expression
    }

// End of a block/expression
}
