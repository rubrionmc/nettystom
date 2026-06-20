// Déclaration du paquet de ce fichier
package net.minestom.server.dialog;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.text.MessageFormat;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface DialogInput {
    // Affecte une valeur
    int DEFAULT_WIDTH = 200;

    // Affecte une valeur
    Registry<StructCodec<? extends DialogInput>> REGISTRY = DynamicRegistry.fromMap(
            // Instruction de code
            Key.key("input_control_type"),
            // Instruction de code
            Map.entry(Key.key("boolean"), Boolean.CODEC),
            // Instruction de code
            Map.entry(Key.key("number_range"), NumberRange.CODEC),
            // Instruction de code
            Map.entry(Key.key("single_option"), SingleOption.CODEC),
            // Appelle une méthode
            Map.entry(Key.key("text"), Text.CODEC));
    // Appelle une méthode
    StructCodec<DialogInput> CODEC = Codec.RegistryTaggedUnion(REGISTRY, DialogInput::codec);

    // Début d'une méthode/d'un bloc
    static void validateKey(String key) {
        // Boucle : répète un bloc
        for (var c : key.toCharArray())
            // Embranchement : vérifie une condition
            if (!Character.isLetterOrDigit(c) && c != '_')
                // Lève une exception
                throw new IllegalArgumentException(MessageFormat.format("Invalid input key: {0}. Must match [a-zA-Z0-9_]+", key));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Boolean(
            // Instruction de code
            String key,
            // Instruction de code
            Component label,
            // Instruction de code
            boolean initial,
            // Instruction de code
            String onTrue,
            // Instruction de code
            String onFalse
    // Début d'une méthode/d'un bloc
    ) implements DialogInput {

        // Affecte une valeur
        public static final StructCodec<Boolean> CODEC = StructCodec.struct(
                // Instruction de code
                "key", Codec.STRING, Boolean::key,
                // Instruction de code
                "label", Codec.COMPONENT, Boolean::label,
                // Instruction de code
                "initial", StructCodec.BOOLEAN.optional(false), Boolean::initial,
                // Instruction de code
                "on_true", StructCodec.STRING.optional("true"), Boolean::onTrue,
                // Instruction de code
                "on_false", StructCodec.STRING.optional("false"), Boolean::onFalse,
                // Instruction de code
                Boolean::new);

        // Début d'une méthode/d'un bloc
        public Boolean {
            // Appelle une méthode
            validateKey(key);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends DialogInput> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record NumberRange(
            // Instruction de code
            String key, int width,
            // Instruction de code
            Component label,
            // Instruction de code
            String labelFormat,
            // Instruction de code
            float start, float end,
            // Annotation pour l'élément suivant
            @Nullable Float initial,
            // Annotation pour l'élément suivant
            @Nullable Float step
    // Début d'une méthode/d'un bloc
    ) implements DialogInput {

        // Affecte une valeur
        public static final StructCodec<NumberRange> CODEC = StructCodec.struct(
                // Instruction de code
                "key", Codec.STRING, NumberRange::key,
                // Instruction de code
                "width", Codec.INT.optional(DEFAULT_WIDTH), NumberRange::width,
                // Instruction de code
                "label", Codec.COMPONENT, NumberRange::label,
                // Instruction de code
                "label_format", Codec.STRING.optional("options.generic_value"), NumberRange::labelFormat,
                // Instruction de code
                "start", Codec.FLOAT, NumberRange::start,
                // Instruction de code
                "end", Codec.FLOAT, NumberRange::end,
                // Instruction de code
                "initial", Codec.FLOAT.optional(), NumberRange::initial,
                // Instruction de code
                "step", Codec.FLOAT.optional(), NumberRange::step,
                // Instruction de code
                NumberRange::new);

        // Début d'une méthode/d'un bloc
        public NumberRange {
            // Appelle une méthode
            validateKey(key);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends DialogInput> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SingleOption(
            // Instruction de code
            String key, int width,
            // Instruction de code
            List<Option> options,
            // Instruction de code
            Component label,
            // Instruction de code
            boolean labelVisible
    // Début d'une méthode/d'un bloc
    ) implements DialogInput {
        // Affecte une valeur
        public static final StructCodec<SingleOption> CODEC = StructCodec.struct(
                // Instruction de code
                "key", Codec.STRING, SingleOption::key,
                // Instruction de code
                "width", Codec.INT.optional(DEFAULT_WIDTH), SingleOption::width,
                // Instruction de code
                "options", Option.CODEC.list(), SingleOption::options,
                // Instruction de code
                "label", Codec.COMPONENT, SingleOption::label,
                // Instruction de code
                "label_visible", Codec.BOOLEAN.optional(true), SingleOption::labelVisible,
                // Instruction de code
                SingleOption::new);

        // Début d'une méthode/d'un bloc
        public SingleOption {
            // Appelle une méthode
            validateKey(key);
            // Affecte une valeur
            boolean found = false;
            // Boucle : répète un bloc
            for (var option : options) {
                // Embranchement : vérifie une condition
                if (!option.initial) continue;
                // Appelle une méthode
                Check.argCondition(found, "Multiple initial options found in SingleOption input");
                // Affecte une valeur
                found = true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends DialogInput> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        public record Option(String id, @Nullable Component display, boolean initial) {
            // Affecte une valeur
            public static final StructCodec<Option> CODEC = StructCodec.struct(
                    // Instruction de code
                    "id", Codec.STRING, Option::id,
                    // Instruction de code
                    "display", Codec.COMPONENT.optional(), Option::display,
                    // Instruction de code
                    "initial", Codec.BOOLEAN.optional(false), Option::initial,
                    // Instruction de code
                    Option::new);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Text(
            // Instruction de code
            String key, int width,
            // Instruction de code
            Component label,
            // Instruction de code
            boolean labelVisible,
            // Instruction de code
            String initial,
            // Instruction de code
            int maxLength,
            // Annotation pour l'élément suivant
            @Nullable Multiline multiline
    // Début d'une méthode/d'un bloc
    ) implements DialogInput {
        // Affecte une valeur
        public static final StructCodec<Text> CODEC = StructCodec.struct(
                // Instruction de code
                "key", Codec.STRING, Text::key,
                // Instruction de code
                "width", Codec.INT.optional(DEFAULT_WIDTH), Text::width,
                // Instruction de code
                "label", Codec.COMPONENT, Text::label,
                // Instruction de code
                "label_visible", Codec.BOOLEAN.optional(true), Text::labelVisible,
                // Instruction de code
                "initial", Codec.STRING.optional(""), Text::initial,
                // Instruction de code
                "max_length", Codec.INT.optional(32), Text::maxLength,
                // Instruction de code
                "multiline", Multiline.CODEC.optional(), Text::multiline,
                // Instruction de code
                Text::new);

        // Début d'une méthode/d'un bloc
        public Text {
            // Appelle une méthode
            validateKey(key);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<? extends DialogInput> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        public record Multiline(@Nullable Integer maxLines, @Nullable Integer height) {
            // Affecte une valeur
            public static final StructCodec<Multiline> CODEC = StructCodec.struct(
                    // Instruction de code
                    "max_lines", Codec.INT.optional(), Multiline::maxLines,
                    // Instruction de code
                    "height", Codec.INT.optional(), Multiline::height,
                    // Instruction de code
                    Multiline::new);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    StructCodec<? extends DialogInput> codec();

// Fin d'un bloc/d'une expression
}
