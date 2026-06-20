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
public record ConditionalEffect<E extends Enchantment.Effect>(
        // Code statement
        E effect,
        // Annotation for the following element
        @Nullable DataPredicate requirements
// Start of a method/block
) implements Enchantment.Effect {

    // Start of a method/block
    public static <E extends Enchantment.Effect> Codec<ConditionalEffect<E>> codec(Codec<E> effectType) {
        // Returns a value to the caller
        return StructCodec.struct(
                // Code statement
                "effect", effectType, ConditionalEffect::effect,
                // Code statement
                "requirements", DataPredicate.NBT_TYPE.optional(), ConditionalEffect::requirements,
                // Code statement
                ConditionalEffect::new
        // End of a block/expression
        );
    // End of a block/expression
    }

// End of a block/expression
}
