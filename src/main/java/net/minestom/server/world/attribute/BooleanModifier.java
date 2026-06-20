// Package declaration for this file
package net.minestom.server.world.attribute;

// Import of a required class
import net.minestom.server.codec.Codec;

// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
non-sealed interface BooleanModifier extends EnvironmentAttribute.Modifier<Boolean, Boolean> {
    // Calls a method
    BooleanModifier AND = (a, b) -> a && b;
    // Calls a method
    BooleanModifier NAND = (a, b) -> !a || !b;
    // Calls a method
    BooleanModifier OR = (a, b) -> a || b;
    // Calls a method
    BooleanModifier NOR = (a, b) -> !a && !b;
    // Calls a method
    BooleanModifier XOR = (a, b) -> a ^ b;
    // Calls a method
    BooleanModifier XNOR = (a, b) -> a == b;

    // Annotation for the following element
    @java.lang.Override
    // Start of a method/block
    default Codec<java.lang.Boolean> argumentCodec() {
        // Returns a value to the caller
        return Codec.BOOLEAN;
    // End of a block/expression
    }
// End of a block/expression
}
