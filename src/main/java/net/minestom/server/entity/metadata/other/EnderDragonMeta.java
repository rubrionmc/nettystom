// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.MobMeta;

// Type declaration (class/interface/enum/record)
public class EnderDragonMeta extends MobMeta {
    // Start of a method/block
    public EnderDragonMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Phase getPhase() {
        // Returns a value to the caller
        return Phase.VALUES[metadata.get(MetadataDef.EnderDragon.DRAGON_PHASE)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setPhase(Phase value) {
        // Calls a method
        metadata.set(MetadataDef.EnderDragon.DRAGON_PHASE, value.ordinal());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Phase {
        // Code statement
        CIRCLING,
        // Code statement
        STRAFING,
        // Code statement
        FLYING_TO_THE_PORTAL,
        // Code statement
        LANDING_ON_THE_PORTAL,
        // Code statement
        TAKING_OFF_FROM_THE_PORTAL,
        // Code statement
        BREATH_ATTACK,
        // Code statement
        LOOKING_FOR_BREATH_ATTACK_PLAYER,
        // Code statement
        ROAR,
        // Code statement
        CHARGING_PLAYER,
        // Code statement
        FLYING_TO_THE_PORTAL_TO_DIE,
        // Code statement
        HOVERING_WITHOUT_AI;

        // Calls a method
        private final static Phase[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
