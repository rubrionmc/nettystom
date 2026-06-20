// Package declaration for this file
package net.minestom.server.entity.metadata.projectile;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class ThrownTridentMeta extends AbstractArrowMeta {
    // Start of a method/block
    public ThrownTridentMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public byte getLoyaltyLevel() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ThrownTrident.LOYALTY_LEVEL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLoyaltyLevel(byte value) {
        // Calls a method
        metadata.set(MetadataDef.ThrownTrident.LOYALTY_LEVEL, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasEnchantmentGlint() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ThrownTrident.HAS_ENCHANTMENT_GLINT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasEnchantmentGlint(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.ThrownTrident.HAS_ENCHANTMENT_GLINT, value);
    // End of a block/expression
    }

// End of a block/expression
}
