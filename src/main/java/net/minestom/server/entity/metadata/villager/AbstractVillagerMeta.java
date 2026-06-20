// Package declaration for this file
package net.minestom.server.entity.metadata.villager;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.AgeableMobMeta;

// Type declaration (class/interface/enum/record)
public class AbstractVillagerMeta extends AgeableMobMeta {
    // Start of a method/block
    protected AbstractVillagerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getHeadShakeTimer() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractVillager.HEAD_SHAKE_TIMER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHeadShakeTimer(int value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractVillager.HEAD_SHAKE_TIMER, value);
    // End of a block/expression
    }
// End of a block/expression
}
