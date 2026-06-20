// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public record TooltipDisplay(boolean hideTooltip, Set<DataComponent<?>> hiddenComponents) {
    // Appelle une méthode
    public static final TooltipDisplay EMPTY = new TooltipDisplay(false, Set.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<TooltipDisplay> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BOOLEAN, TooltipDisplay::hideTooltip,
            // Instruction de code
            DataComponent.NETWORK_TYPE.set(Short.MAX_VALUE), TooltipDisplay::hiddenComponents,
            // Instruction de code
            TooltipDisplay::new);
    // Affecte une valeur
    public static final Codec<TooltipDisplay> CODEC = StructCodec.struct(
            // Instruction de code
            "hide_tooltip", Codec.BOOLEAN.optional(false), TooltipDisplay::hideTooltip,
            // Instruction de code
            "hidden_components", DataComponent.CODEC.set(Short.MAX_VALUE).optional(Set.of()), TooltipDisplay::hiddenComponents,
            // Instruction de code
            TooltipDisplay::new);

    // Début d'une méthode/d'un bloc
    public TooltipDisplay {
        // Appelle une méthode
        hiddenComponents = Set.copyOf(hiddenComponents);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public TooltipDisplay withHideTooltip(boolean hide) {
        // Renvoie une valeur à l'appelant
        return new TooltipDisplay(hide, hiddenComponents);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public TooltipDisplay with(DataComponent<?> component) {
        // Embranchement : vérifie une condition
        if (!hiddenComponents.contains(component))
            // Renvoie une valeur à l'appelant
            return new TooltipDisplay(hideTooltip, hiddenComponents);

        // Appelle une méthode
        var newHiddenComponents = new HashSet<>(hiddenComponents);
        // Appelle une méthode
        newHiddenComponents.remove(component);
        // Renvoie une valeur à l'appelant
        return new TooltipDisplay(hideTooltip, newHiddenComponents);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public TooltipDisplay without(DataComponent<?> component) {
        // Embranchement : vérifie une condition
        if (hiddenComponents.contains(component))
            // Renvoie une valeur à l'appelant
            return new TooltipDisplay(hideTooltip, hiddenComponents);

        // Appelle une méthode
        var newHiddenComponents = new HashSet<>(hiddenComponents);
        // Appelle une méthode
        newHiddenComponents.add(component);
        // Renvoie une valeur à l'appelant
        return new TooltipDisplay(hideTooltip, newHiddenComponents);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
