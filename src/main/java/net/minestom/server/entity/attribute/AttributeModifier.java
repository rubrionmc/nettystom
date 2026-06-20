// Package declaration for this file
package net.minestom.server.entity.attribute;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

/**
 * Represent an attribute modifier.
 */
// Type declaration (class/interface/enum/record)
public record AttributeModifier(Key id, double amount, AttributeOperation operation) {
    // Assigns a value
    public static final NetworkBuffer.Type<AttributeModifier> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.KEY, AttributeModifier::id,
            // Code statement
            NetworkBuffer.DOUBLE, AttributeModifier::amount,
            // Code statement
            AttributeOperation.NETWORK_TYPE, AttributeModifier::operation,
            // Code statement
            AttributeModifier::new);
    // Assigns a value
    public static final Codec<AttributeModifier> CODEC = StructCodec.struct(
            // Code statement
            "id", Codec.KEY, AttributeModifier::id,
            // Code statement
            "amount", Codec.DOUBLE, AttributeModifier::amount,
            // Code statement
            "operation", AttributeOperation.CODEC, AttributeModifier::operation,
            // Code statement
            AttributeModifier::new);

    /**
     * Creates a new modifier with a random id.
     *
     * @param id        the (namespace) id of this modifier
     * @param amount    the value of this modifier
     * @param operation the operation to apply this modifier with
     */
    // Start of a method/block
    public AttributeModifier(@KeyPattern String id, double amount, AttributeOperation operation) {
        // Calls a method
        this(Key.key(id), amount, operation);
    // End of a block/expression
    }

// End of a block/expression
}
