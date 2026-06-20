// Déclaration du paquet de ce fichier
package net.minestom.server.collision;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.doubles.DoubleUnaryOperator;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public record Aerodynamics(double gravity, double horizontalAirResistance, double verticalAirResistance) {
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withGravity(double gravity) {
        // Renvoie une valeur à l'appelant
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withGravity(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withGravity(operator.apply(gravity));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withHorizontalAirResistance(double horizontalAirResistance) {
        // Renvoie une valeur à l'appelant
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withHorizontalAirResistance(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withHorizontalAirResistance(operator.apply(horizontalAirResistance));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withVerticalAirResistance(double verticalAirResistance) {
        // Renvoie une valeur à l'appelant
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withVerticalAirResistance(DoubleUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withVerticalAirResistance(operator.apply(verticalAirResistance));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Aerodynamics withAirResistance(double horizontalAirResistance, double verticalAirResistance) {
        // Renvoie une valeur à l'appelant
        return new Aerodynamics(gravity, horizontalAirResistance, verticalAirResistance);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
