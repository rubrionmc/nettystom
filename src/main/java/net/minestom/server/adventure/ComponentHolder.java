// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Represents an object that holds some amount of components.
 *
 * @param <T> the holding class
 */
// Déclaration de type (classe/interface/enum/record)
public interface ComponentHolder<T> {

    /**
     * Gets the components held by this object.
     *
     * @return the components
     */
    // Appelle une méthode
    Collection<Component> components();

    /**
     * Returns a copy of this object. For each component this object holds, the operator
     * is applied to the copy before returning.
     *
     * @param operator the operator
     * @return the copy
     */
    // Appelle une méthode
    T copyWithOperator(UnaryOperator<Component> operator);

    /**
     * Visits each component held by this object.
     *
     * @param visitor the visitor
     */
    // Début d'une méthode/d'un bloc
    default void visitComponents(Consumer<Component> visitor) {
        // Boucle : répète un bloc
        for (Component component : this.components()) {
            // Appelle une méthode
            visitor.accept(component);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
