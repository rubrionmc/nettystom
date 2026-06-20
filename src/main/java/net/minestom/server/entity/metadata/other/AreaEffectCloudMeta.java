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
import net.minestom.server.particle.Particle;

// Type declaration (class/interface/enum/record)
public class AreaEffectCloudMeta extends EntityMeta {
    // Start of a method/block
    public AreaEffectCloudMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public float getRadius() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AreaEffectCloud.RADIUS);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRadius(float value) {
        // Calls a method
        metadata.set(MetadataDef.AreaEffectCloud.RADIUS, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isWaiting() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AreaEffectCloud.WAITING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setWaiting(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AreaEffectCloud.WAITING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Particle getParticle() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AreaEffectCloud.PARTICLE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setParticle(Particle value) {
        // Calls a method
        metadata.set(MetadataDef.AreaEffectCloud.PARTICLE, value);
    // End of a block/expression
    }

// End of a block/expression
}
