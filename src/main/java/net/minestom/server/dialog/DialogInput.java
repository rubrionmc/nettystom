// Package declaration for this file
package net.minestom.server.dialog;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.text.MessageFormat;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public sealed interface DialogInput {
    // Assigns a value
    int DEFAULT_WIDTH = 200;

    // Assigns a value
    Registry<StructCodec<? extends DialogInput>> REGISTRY = DynamicRegistry.fromMap(
            // Code statement
            Key.key("input_control_type"),
            // Code statement
            Map.entry(Key.key("boolean"), Boolean.CODEC),
            // Code statement
            Map.entry(Key.key("number_range"), NumberRange.CODEC),
            // Code statement
            Map.entry(Key.key("single_option"), SingleOption.CODEC),
            // Calls a method
            Map.entry(Key.key("text"), Text.CODEC));
    // Calls a method
    StructCodec<DialogInput> CODEC = Codec.RegistryTaggedUnion(REGISTRY, DialogInput::codec);

    // Start of a method/block
    static void validateKey(String key) {
        // Loop: repeats a block
        for (var c : key.toCharArray())
            // Branch: checks a condition
            if (!Character.isLetterOrDigit(c) && c != '_')
                // Throws an exception
                throw new IllegalArgumentException(MessageFormat.format("Invalid input key: {0}. Must match [a-zA-Z0-9_]+", key));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Boolean(
            // Code statement
            String key,
            // Code statement
            Component label,
            // Code statement
            boolean initial,
            // Code statement
            String onTrue,
            // Code statement
            String onFalse
    // Start of a method/block
    ) implements DialogInput {

        // Assigns a value
        public static final StructCodec<Boolean> CODEC = StructCodec.struct(
                // Code statement
                "key", Codec.STRING, Boolean::key,
                // Code statement
                "label", Codec.COMPONENT, Boolean::label,
                // Code statement
                "initial", StructCodec.BOOLEAN.optional(false), Boolean::initial,
                // Code statement
                "on_true", StructCodec.STRING.optional("true"), Boolean::onTrue,
                // Code statement
                "on_false", StructCodec.STRING.optional("false"), Boolean::onFalse,
                // Code statement
                Boolean::new);

        // Start of a method/block
        public Boolean {
            // Calls a method
            validateKey(key);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Boolean> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record NumberRange(
            // Code statement
            String key, int width,
            // Code statement
            Component label,
            // Code statement
            String labelFormat,
            // Code statement
            float start, float end,
            // Annotation for the following element
            @Nullable Float initial,
            // Annotation for the following element
            @Nullable Float step
    // Start of a method/block
    ) implements DialogInput {

        // Assigns a value
        public static final StructCodec<NumberRange> CODEC = StructCodec.struct(
                // Code statement
                "key", Codec.STRING, NumberRange::key,
                // Code statement
                "width", Codec.INT.optional(DEFAULT_WIDTH), NumberRange::width,
                // Code statement
                "label", Codec.COMPONENT, NumberRange::label,
                // Code statement
                "label_format", Codec.STRING.optional("options.generic_value"), NumberRange::labelFormat,
                // Code statement
                "start", Codec.FLOAT, NumberRange::start,
                // Code statement
                "end", Codec.FLOAT, NumberRange::end,
                // Code statement
                "initial", Codec.FLOAT.optional(), NumberRange::initial,
                // Code statement
                "step", Codec.FLOAT.optional(), NumberRange::step,
                // Code statement
                NumberRange::new);

        // Start of a method/block
        public NumberRange {
            // Calls a method
            validateKey(key);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<NumberRange> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SingleOption(
            // Code statement
            String key, int width,
            // Code statement
            List<Option> options,
            // Code statement
            Component label,
            // Code statement
            boolean labelVisible
    // Start of a method/block
    ) implements DialogInput {
        // Assigns a value
        public static final StructCodec<SingleOption> CODEC = StructCodec.struct(
                // Code statement
                "key", Codec.STRING, SingleOption::key,
                // Code statement
                "width", Codec.INT.optional(DEFAULT_WIDTH), SingleOption::width,
                // Code statement
                "options", Option.CODEC.list(), SingleOption::options,
                // Code statement
                "label", Codec.COMPONENT, SingleOption::label,
                // Code statement
                "label_visible", Codec.BOOLEAN.optional(true), SingleOption::labelVisible,
                // Code statement
                SingleOption::new);

        // Start of a method/block
        public SingleOption {
            // Calls a method
            validateKey(key);
            // Assigns a value
            boolean found = false;
            // Loop: repeats a block
            for (var option : options) {
                // Branch: checks a condition
                if (!option.initial) continue;
                // Calls a method
                Check.argCondition(found, "Multiple initial options found in SingleOption input");
                // Assigns a value
                found = true;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<SingleOption> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        public record Option(String id, @Nullable Component display, boolean initial) {
            // Assigns a value
            public static final StructCodec<Option> CODEC = StructCodec.struct(
                    // Code statement
                    "id", Codec.STRING, Option::id,
                    // Code statement
                    "display", Codec.COMPONENT.optional(), Option::display,
                    // Code statement
                    "initial", Codec.BOOLEAN.optional(false), Option::initial,
                    // Code statement
                    Option::new);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Text(
            // Code statement
            String key, int width,
            // Code statement
            Component label,
            // Code statement
            boolean labelVisible,
            // Code statement
            String initial,
            // Code statement
            int maxLength,
            // Annotation for the following element
            @Nullable Multiline multiline
    // Start of a method/block
    ) implements DialogInput {
        // Assigns a value
        public static final StructCodec<Text> CODEC = StructCodec.struct(
                // Code statement
                "key", Codec.STRING, Text::key,
                // Code statement
                "width", Codec.INT.optional(DEFAULT_WIDTH), Text::width,
                // Code statement
                "label", Codec.COMPONENT, Text::label,
                // Code statement
                "label_visible", Codec.BOOLEAN.optional(true), Text::labelVisible,
                // Code statement
                "initial", Codec.STRING.optional(""), Text::initial,
                // Code statement
                "max_length", Codec.INT.optional(32), Text::maxLength,
                // Code statement
                "multiline", Multiline.CODEC.optional(), Text::multiline,
                // Code statement
                Text::new);

        // Start of a method/block
        public Text {
            // Calls a method
            validateKey(key);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Text> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        public record Multiline(@Nullable Integer maxLines, @Nullable Integer height) {
            // Assigns a value
            public static final StructCodec<Multiline> CODEC = StructCodec.struct(
                    // Code statement
                    "max_lines", Codec.INT.optional(), Multiline::maxLines,
                    // Code statement
                    "height", Codec.INT.optional(), Multiline::height,
                    // Code statement
                    Multiline::new);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.OverrideOnly
    // Calls a method
    StructCodec<? extends DialogInput> codec();

// End of a block/expression
}
