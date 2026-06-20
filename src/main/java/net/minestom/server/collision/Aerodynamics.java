// Package declaration for this file
package net.minestom.server.collision;

// Import of a required class
import it.unimi.dsi.fastutil.doubles.DoubleUnaryOperator;
// Import of a required class
import org.jetbrains.annotations.Contract;

/**
 * Represents the aerodynamic properties of an entity
 *
 * @param gravity                 the entity's downward acceleration per tick
 * @param horizontalAirResistance the horizontal drag coefficient; the entity's current horizontal
 *                                velocity is multiplied by this every tick
 * @param verticalAirResistance   the vertical drag coefficient; the entity's current vertical
 *  *                             velocity is multiplied by this every tick
 */
// Type declaration (class/interface/enum/record)
public record Aerodynamics(double gravity, double horizontalAirResistance, double verticalAirResistance) {
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withGravity(double gravity) {
        // Returns a value to the caller
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withGravity(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return withGravity(operator.apply(gravity));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withHorizontalAirResistance(double horizontalAirResistance) {
        // Returns a value to the caller
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withHorizontalAirResistance(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return withHorizontalAirResistance(operator.apply(horizontalAirResistance));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withVerticalAirResistance(double verticalAirResistance) {
        // Returns a value to the caller
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withVerticalAirResistance(DoubleUnaryOperator operator) {
        // Returns a value to the caller
        return withVerticalAirResistance(operator.apply(verticalAirResistance));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Aerodynamics withAirResistance(double horizontalAirResistance, double verticalAirResistance) {
        // Returns a value to the caller
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // End of a block/expression
    }
// End of a block/expression
}
