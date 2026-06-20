// Package declaration for this file
package net.minestom.server.entity.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public enum AttributeOperation {
    // Code statement
    ADD_VALUE(0),
    // Code statement
    ADD_MULTIPLIED_BASE(1),
    // Calls a method
    ADD_MULTIPLIED_TOTAL(2);

    // Calls a method
    public static final NetworkBuffer.Type<AttributeOperation> NETWORK_TYPE = NetworkBuffer.Enum(AttributeOperation.class);
    // Calls a method
    public static final Codec<AttributeOperation> CODEC = Codec.Enum(AttributeOperation.class);

    // Assigns a value
    private static final AttributeOperation[] VALUES = new AttributeOperation[]{ADD_VALUE, ADD_MULTIPLIED_BASE, ADD_MULTIPLIED_TOTAL};
    // Code statement
    private final int id;

    // Start of a method/block
    AttributeOperation(int id) {
        // Access to the current/parent object
        this.id = id;
    // End of a block/expression
    }

    // Start of a method/block
    public int getId() {
        // Returns a value to the caller
        return this.id;
    // End of a block/expression
    }

    // Start of a method/block
    public static @Nullable AttributeOperation fromId(int id) {
        // Branch: checks a condition
        if (id >= 0 && id < VALUES.length) {
            // Returns a value to the caller
            return VALUES[id];
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }
// End of a block/expression
}
