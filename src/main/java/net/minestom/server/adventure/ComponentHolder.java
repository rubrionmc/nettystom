// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import net.kyori.adventure.text.Component;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Represents an object that holds some amount of components.
 *
 * @param <T> the holding class
 */
// Type declaration (class/interface/enum/record)
public interface ComponentHolder<T> {

    /**
     * Gets the components held by this object.
     *
     * @return the components
     */
    // Calls a method
    Collection<? extends Component> components();

    /**
     * Returns a copy of this object. For each component this object holds, the operator
     * is applied to the copy before returning.
     *
     * @param operator the operator
     * @return the copy
     */
    // Calls a method
    T copyWithOperator(UnaryOperator<Component> operator);

    /**
     * Visits each component held by this object.
     *
     * @param visitor the visitor
     */
    // Start of a method/block
    default void visitComponents(Consumer<? super Component> visitor) {
        // Loop: repeats a block
        for (Component component : this.components()) {
            // Calls a method
            visitor.accept(component);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
