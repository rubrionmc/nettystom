// Package declaration for this file
package net.minestom.server.message;

// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.kyori.adventure.translation.Translatable;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.ComponentCodecs;
// Import of a required class
import net.minestom.server.codec.StructCodec;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record ChatTypeDecoration(
        // Code statement
        String translationKey,
        // Code statement
        List<Parameter> parameters,
        // Code statement
        Style style
// Start of a method/block
) implements Translatable {
    // Assigns a value
    public static final Codec<ChatTypeDecoration> CODEC = StructCodec.struct(
            // Code statement
            "translation_key", Codec.STRING, ChatTypeDecoration::translationKey,
            // Code statement
            "parameters", Parameter.CODEC.list().optional(List.of()), ChatTypeDecoration::parameters,
            // Code statement
            "style", ComponentCodecs.STYLE.optional(Style.empty()), ChatTypeDecoration::style,
            // Code statement
            ChatTypeDecoration::new);

    // Type declaration (class/interface/enum/record)
    public enum Parameter {
        // Code statement
        SENDER,
        // Code statement
        TARGET,
        // Code statement
        CONTENT;

        // Calls a method
        public static final Codec<Parameter> CODEC = Codec.Enum(Parameter.class);
    // End of a block/expression
    }

// End of a block/expression
}
