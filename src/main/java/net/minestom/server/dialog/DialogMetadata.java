// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record DialogMetadata(
        // Instruction de code
        Component title,
        // Annotation pour l'élément suivant
        @Nullable Component externalTitle,
        // Instruction de code
        boolean canCloseWithEscape,
        // Instruction de code
        boolean pause,
        // Instruction de code
        DialogAfterAction afterAction,
        // Instruction de code
        List<DialogBody> body,
        // Instruction de code
        List<DialogInput> inputs
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final StructCodec<DialogMetadata> CODEC = StructCodec.struct(
            // Instruction de code
            "title", Codec.COMPONENT, DialogMetadata::title,
            // Instruction de code
            "external_title", Codec.COMPONENT.optional(), DialogMetadata::externalTitle,
            // Instruction de code
            "can_close_with_escape", StructCodec.BOOLEAN.optional(true), DialogMetadata::canCloseWithEscape,
            // Instruction de code
            "pause", StructCodec.BOOLEAN.optional(true), DialogMetadata::pause,
            // Instruction de code
            "after_action", DialogAfterAction.CODEC.optional(DialogAfterAction.CLOSE), DialogMetadata::afterAction,
            // Instruction de code
            "body", DialogBody.CODEC.listOrSingle().optional(List.of()), DialogMetadata::body,
            // Instruction de code
            "inputs", DialogInput.CODEC.list().optional(List.of()), DialogMetadata::inputs,
            // Instruction de code
            DialogMetadata::new);

    // Début d'une méthode/d'un bloc
    public DialogMetadata {
        // Instruction de code
        Check.argCondition(pause && afterAction == DialogAfterAction.NONE,
                // Affecte une valeur
                "Dialog may not have pause=true and afterAction=NONE");
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
