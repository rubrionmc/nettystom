// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record DialogActionButton(
        // Instruction de code
        Component label,
        // Annotation pour l'élément suivant
        @Nullable Component tooltip,
        // Instruction de code
        int width,
        // Annotation pour l'élément suivant
        @Nullable DialogAction action
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final int DEFAULT_WIDTH = 150;
    // Affecte une valeur
    public static final StructCodec<DialogActionButton> CODEC = StructCodec.struct(
            // Instruction de code
            "label", Codec.COMPONENT, DialogActionButton::label,
            // Instruction de code
            "tooltip", Codec.COMPONENT.optional(), DialogActionButton::tooltip,
            // Instruction de code
            "width", Codec.INT.optional(DEFAULT_WIDTH), DialogActionButton::width,
            // Instruction de code
            "action", DialogAction.CODEC.optional(), DialogActionButton::action,
            // Instruction de code
            DialogActionButton::new);
// Fin d'un bloc/d'une expression
}
