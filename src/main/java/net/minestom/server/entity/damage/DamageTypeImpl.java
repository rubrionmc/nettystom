// Package declaration for this file
package net.minestom.server.entity.damage;

// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
record DamageTypeImpl(
        // Code statement
        String messageId,
        // Code statement
        String scaling,
        // Code statement
        float exhaustion,
        // Annotation for the following element
        @Nullable String effects,
        // Annotation for the following element
        @Nullable String deathMessageType
// Start of a method/block
) implements DamageType {

    // Annotation for the following element
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Start of a method/block
    DamageTypeImpl {
        // Calls a method
        Check.argCondition(messageId == null || messageId.isEmpty(), "missing message id");
        // Calls a method
        Check.argCondition(scaling == null || scaling.isEmpty(), "missing scaling");
    // End of a block/expression
    }
// End of a block/expression
}