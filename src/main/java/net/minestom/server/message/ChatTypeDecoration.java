// Déclaration du paquet de ce fichier
package net.minestom.server.message;

// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.Translatable;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.ComponentCodecs;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record ChatTypeDecoration(
        // Instruction de code
        String translationKey,
        // Instruction de code
        List<Parameter> parameters,
        // Instruction de code
        Style style
// Début d'une méthode/d'un bloc
) implements Translatable {
    // Affecte une valeur
    public static final Codec<ChatTypeDecoration> CODEC = StructCodec.struct(
            // Instruction de code
            "translation_key", Codec.STRING, ChatTypeDecoration::translationKey,
            // Instruction de code
            "parameters", Parameter.CODEC.list().optional(List.of()), ChatTypeDecoration::parameters,
            // Instruction de code
            "style", ComponentCodecs.STYLE.optional(Style.empty()), ChatTypeDecoration::style,
            // Instruction de code
            ChatTypeDecoration::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Parameter {
        // Instruction de code
        SENDER,
        // Instruction de code
        TARGET,
        // Instruction de code
        CONTENT;

        // Appelle une méthode
        public static final Codec<Parameter> CODEC = Codec.Enum(Parameter.class);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
