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
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public sealed interface DialogBody {
    // Assigns a value
    Registry<StructCodec<? extends DialogBody>> REGISTRY = DynamicRegistry.fromMap(
            // Code statement
            Key.key("dialog_body_type"),
            // Code statement
            Map.entry(Key.key("item"), Item.CODEC),
            // Calls a method
            Map.entry(Key.key("plain_message"), PlainMessage.CODEC));
    // Calls a method
    StructCodec<DialogBody> CODEC = Codec.RegistryTaggedUnion(REGISTRY, DialogBody::codec);

    // Type declaration (class/interface/enum/record)
    record Item(
            // Code statement
            ItemStack itemStack,
            // Annotation for the following element
            @Nullable PlainMessage description,
            // Code statement
            boolean showDecoration,
            // Code statement
            boolean showTooltip,
            // Code statement
            int width, int height
    // Start of a method/block
    ) implements DialogBody {
        // Assigns a value
        public static final StructCodec<Item> CODEC = StructCodec.struct(
                // Code statement
                "item", ItemStack.CODEC, Item::itemStack,
                // Code statement
                "description", PlainMessage.CODEC.optional(), Item::description,
                // Code statement
                "show_decoration", Codec.BOOLEAN.optional(true), Item::showDecoration,
                // Code statement
                "show_tooltip", Codec.BOOLEAN.optional(true), Item::showTooltip,
                // Code statement
                "width", Codec.INT.optional(16), Item::width,
                // Code statement
                "height", Codec.INT.optional(16), Item::height,
                // Code statement
                Item::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<Item> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PlainMessage(Component contents, int width) implements DialogBody {
        // Assigns a value
        public static final int DEFAULT_WIDTH = 200;

        // Assigns a value
        private static final StructCodec<PlainMessage> COMPONENT_CODEC = StructCodec.struct(
                // Code statement
                StructCodec.INLINE, Codec.COMPONENT, PlainMessage::contents,
                // Calls a method
                (component) -> new PlainMessage(component, DEFAULT_WIDTH));
        // Assigns a value
        public static final StructCodec<PlainMessage> CODEC = StructCodec.struct(
                // Code statement
                "contents", Codec.COMPONENT, PlainMessage::contents,
                // Code statement
                "width", Codec.INT.optional(200), PlainMessage::width,
                // Calls a method
                PlainMessage::new).orElseStruct(COMPONENT_CODEC);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StructCodec<PlainMessage> codec() {
            // Returns a value to the caller
            return CODEC;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.OverrideOnly
    // Calls a method
    StructCodec<? extends DialogBody> codec();

// End of a block/expression
}
