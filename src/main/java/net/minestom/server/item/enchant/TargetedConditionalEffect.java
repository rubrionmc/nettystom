// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.condition.DataPredicate;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record TargetedConditionalEffect<E extends Enchantment.Effect>(
        // Code statement
        Enchantment.Target enchanted,
        // Annotation for the following element
        @Nullable Enchantment.Target affected,
        // Code statement
        E effect,
        // Annotation for the following element
        @Nullable DataPredicate requirements
// Start of a method/block
) implements Enchantment.Effect {

    // Start of a method/block
    public static <E extends Enchantment.Effect> Codec<TargetedConditionalEffect<E>> codec(Codec<E> effectType) {
        // Returns a value to the caller
        return StructCodec.struct(
                // Code statement
                "enchanted", Enchantment.Target.CODEC, TargetedConditionalEffect::enchanted,
                // Code statement
                "affected", Enchantment.Target.CODEC.optional(), TargetedConditionalEffect::affected,
                // Code statement
                "effect", effectType, TargetedConditionalEffect::effect,
                // Code statement
                "requirements", DataPredicate.NBT_TYPE.optional(), TargetedConditionalEffect::requirements,
                // Code statement
                TargetedConditionalEffect::new
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
