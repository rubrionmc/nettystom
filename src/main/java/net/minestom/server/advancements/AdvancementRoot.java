// Package declaration for this file
package net.minestom.server.advancements;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Represents an {@link Advancement} which is the root of an {@link AdvancementTab}.
 * Every tab requires one since advancements needs to be linked to a parent.
 * <p>
 * The difference between this and an {@link Advancement} is that the root is responsible for the tab background.
 */
// Type declaration (class/interface/enum/record)
public class AdvancementRoot extends Advancement {
    // Code statement
    public AdvancementRoot(Component title, Component description,
                           // Code statement
                           ItemStack icon, FrameType frameType,
                           // Code statement
                           float x, float y,
                           // Annotation for the following element
                           @Nullable String background) {
        // Access to the current/parent object
        super(title, description, icon, frameType, x, y);
        // Calls a method
        setBackground(background);
    // End of a block/expression
    }

    // Code statement
    public AdvancementRoot(Component title, Component description,
                           // Code statement
                           Material icon, FrameType frameType,
                           // Code statement
                           float x, float y,
                           // Annotation for the following element
                           @Nullable String background) {
        // Access to the current/parent object
        super(title, description, icon, frameType, x, y);
        // Calls a method
        setBackground(background);
    // End of a block/expression
    }
// End of a block/expression
}
