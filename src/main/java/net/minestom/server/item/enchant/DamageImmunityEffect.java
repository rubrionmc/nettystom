// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;

// Type declaration (class/interface/enum/record)
public final class DamageImmunityEffect implements Enchantment.Effect {
    // Calls a method
    public static final DamageImmunityEffect INSTANCE = new DamageImmunityEffect();

    // Calls a method
    public static final Codec<DamageImmunityEffect> CODEC = StructCodec.struct(INSTANCE);

    // Start of a method/block
    private DamageImmunityEffect() {
    // End of a block/expression
    }
// End of a block/expression
}
