// Package declaration for this file
package net.minestom.server.entity.metadata.flying;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class PhantomMeta extends FlyingMeta {
    // Start of a method/block
    public PhantomMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getSize() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Phantom.SIZE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSize(int value) {
        // Calls a method
        metadata.set(MetadataDef.Phantom.SIZE, value);
    // End of a block/expression
    }

// End of a block/expression
}
