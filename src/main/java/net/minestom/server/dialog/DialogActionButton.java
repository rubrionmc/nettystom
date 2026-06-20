// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record DialogActionButton(
        // Code statement
        Component label,
        // Annotation for the following element
        @Nullable Component tooltip,
        // Code statement
        int width,
        // Annotation for the following element
        @Nullable DialogAction action
// Start of a method/block
) {
    // Assigns a value
    public static final int DEFAULT_WIDTH = 150;
    // Assigns a value
    public static final StructCodec<DialogActionButton> CODEC = StructCodec.struct(
            // Code statement
            "label", Codec.COMPONENT, DialogActionButton::label,
            // Code statement
            "tooltip", Codec.COMPONENT.optional(), DialogActionButton::tooltip,
            // Code statement
            "width", Codec.INT.optional(DEFAULT_WIDTH), DialogActionButton::width,
            // Code statement
            "action", DialogAction.CODEC.optional(), DialogActionButton::action,
            // Code statement
            DialogActionButton::new);
// End of a block/expression
}
