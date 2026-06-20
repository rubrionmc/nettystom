// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record DialogMetadata(
        // Code statement
        Component title,
        // Annotation for the following element
        @Nullable Component externalTitle,
        // Code statement
        boolean canCloseWithEscape,
        // Code statement
        boolean pause,
        // Code statement
        DialogAfterAction afterAction,
        // Code statement
        List<DialogBody> body,
        // Code statement
        List<DialogInput> inputs
// Start of a method/block
) {
    // Assigns a value
    public static final StructCodec<DialogMetadata> CODEC = StructCodec.struct(
            // Code statement
            "title", Codec.COMPONENT, DialogMetadata::title,
            // Code statement
            "external_title", Codec.COMPONENT.optional(), DialogMetadata::externalTitle,
            // Code statement
            "can_close_with_escape", StructCodec.BOOLEAN.optional(true), DialogMetadata::canCloseWithEscape,
            // Code statement
            "pause", StructCodec.BOOLEAN.optional(true), DialogMetadata::pause,
            // Code statement
            "after_action", DialogAfterAction.CODEC.optional(DialogAfterAction.CLOSE), DialogMetadata::afterAction,
            // Code statement
            "body", DialogBody.CODEC.listOrSingle().optional(List.of()), DialogMetadata::body,
            // Code statement
            "inputs", DialogInput.CODEC.list().optional(List.of()), DialogMetadata::inputs,
            // Code statement
            DialogMetadata::new);

    // Start of a method/block
    public DialogMetadata {
        // Code statement
        Check.argCondition(pause && afterAction == DialogAfterAction.NONE,
                // Code statement
                "Dialog may not have pause=true and afterAction=NONE");
    // End of a block/expression
    }

// End of a block/expression
}
