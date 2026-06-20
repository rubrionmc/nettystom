// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Set;

// Type declaration (class/interface/enum/record)
public record TooltipDisplay(boolean hideTooltip, Set<DataComponent<?>> hiddenComponents) {
    // Calls a method
    public static final TooltipDisplay EMPTY = new TooltipDisplay(false, Set.of());

    // Assigns a value
    public static final NetworkBuffer.Type<TooltipDisplay> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BOOLEAN, TooltipDisplay::hideTooltip,
            // Code statement
            DataComponent.NETWORK_TYPE.set(Short.MAX_VALUE), TooltipDisplay::hiddenComponents,
            // Code statement
            TooltipDisplay::new);
    // Assigns a value
    public static final Codec<TooltipDisplay> CODEC = StructCodec.struct(
            // Code statement
            "hide_tooltip", Codec.BOOLEAN.optional(false), TooltipDisplay::hideTooltip,
            // Code statement
            "hidden_components", DataComponent.CODEC.set(Short.MAX_VALUE).optional(Set.of()), TooltipDisplay::hiddenComponents,
            // Code statement
            TooltipDisplay::new);

    // Start of a method/block
    public TooltipDisplay {
        // Calls a method
        hiddenComponents = Set.copyOf(hiddenComponents);
    // End of a block/expression
    }

    // Start of a method/block
    public TooltipDisplay withHideTooltip(boolean hide) {
        // Returns a value to the caller
        return new TooltipDisplay(hide, hiddenComponents);
    // End of a block/expression
    }

    // Start of a method/block
    public TooltipDisplay with(DataComponent<?> component) {
        // Branch: checks a condition
        if (!hiddenComponents.contains(component))
            // Returns a value to the caller
            return new TooltipDisplay(hideTooltip, hiddenComponents);

        // Calls a method
        var newHiddenComponents = new HashSet<>(hiddenComponents);
        // Calls a method
        newHiddenComponents.remove(component);
        // Returns a value to the caller
        return new TooltipDisplay(hideTooltip, newHiddenComponents);
    // End of a block/expression
    }

    // Start of a method/block
    public TooltipDisplay without(DataComponent<?> component) {
        // Branch: checks a condition
        if (hiddenComponents.contains(component))
            // Returns a value to the caller
            return new TooltipDisplay(hideTooltip, hiddenComponents);

        // Calls a method
        var newHiddenComponents = new HashSet<>(hiddenComponents);
        // Calls a method
        newHiddenComponents.add(component);
        // Returns a value to the caller
        return new TooltipDisplay(hideTooltip, newHiddenComponents);
    // End of a block/expression
    }
// End of a block/expression
}
