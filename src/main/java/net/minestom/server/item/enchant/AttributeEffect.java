// Package declaration for this file
package net.minestom.server.item.enchant;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeOperation;

// Type declaration (class/interface/enum/record)
public record AttributeEffect(
        // Code statement
        Key id,
        // Code statement
        Attribute attribute,
        // Code statement
        LevelBasedValue amount,
        // Code statement
        AttributeOperation operation
// Start of a method/block
) implements Enchantment.Effect, LocationEffect {

    // Assigns a value
    public static final StructCodec<AttributeEffect> CODEC = StructCodec.struct(
            // Code statement
            "id", Codec.KEY, AttributeEffect::id,
            // Code statement
            "attribute", Attribute.CODEC, AttributeEffect::attribute,
            // Code statement
            "amount", LevelBasedValue.CODEC, AttributeEffect::amount,
            // Code statement
            "operation", AttributeOperation.CODEC, AttributeEffect::operation,
            // Code statement
            AttributeEffect::new);

    // Annotation for the following element
    @Override
    // Start of a method/block
    public StructCodec<AttributeEffect> codec() {
        // Returns a value to the caller
        return CODEC;
    // End of a block/expression
    }
// End of a block/expression
}
