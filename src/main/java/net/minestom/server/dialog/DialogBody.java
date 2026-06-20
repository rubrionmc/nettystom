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
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registry;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface DialogBody {
    // Affecte une valeur
    Registry<StructCodec<? extends DialogBody>> REGISTRY = DynamicRegistry.fromMap(
            // Instruction de code
            Key.key("dialog_body_type"),
            // Instruction de code
            Map.entry(Key.key("item"), Item.CODEC),
            // Appelle une méthode
            Map.entry(Key.key("plain_message"), PlainMessage.CODEC));
    // Appelle une méthode
    StructCodec<DialogBody> CODEC = Codec.RegistryTaggedUnion(REGISTRY, DialogBody::codec);

    // Déclaration de type (classe/interface/enum/record)
    record Item(
            // Instruction de code
            ItemStack itemStack,
            // Annotation pour l'élément suivant
            @Nullable PlainMessage description,
            // Instruction de code
            boolean showDecoration,
            // Instruction de code
            boolean showTooltip,
            // Instruction de code
            int width, int height
    // Début d'une méthode/d'un bloc
    ) implements DialogBody {
        // Affecte une valeur
        public static final StructCodec<Item> CODEC = StructCodec.struct(
                // Instruction de code
                "item", ItemStack.CODEC, Item::itemStack,
                // Instruction de code
                "description", PlainMessage.CODEC.optional(), Item::description,
                // Instruction de code
                "show_decoration", Codec.BOOLEAN.optional(true), Item::showDecoration,
                // Instruction de code
                "show_tooltip", Codec.BOOLEAN.optional(true), Item::showTooltip,
                // Instruction de code
                "width", Codec.INT.optional(16), Item::width,
                // Instruction de code
                "height", Codec.INT.optional(16), Item::height,
                // Instruction de code
                Item::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<Item> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PlainMessage(Component contents, int width) implements DialogBody {
        // Affecte une valeur
        public static final int DEFAULT_WIDTH = 200;

        // Affecte une valeur
        private static final StructCodec<PlainMessage> COMPONENT_CODEC = StructCodec.struct(
                // Instruction de code
                StructCodec.INLINE, Codec.COMPONENT, PlainMessage::contents,
                // Appelle une méthode
                (component) -> new PlainMessage(component, DEFAULT_WIDTH));
        // Affecte une valeur
        public static final StructCodec<PlainMessage> CODEC = StructCodec.struct(
                // Instruction de code
                "contents", Codec.COMPONENT, PlainMessage::contents,
                // Instruction de code
                "width", Codec.INT.optional(200), PlainMessage::width,
                // Appelle une méthode
                PlainMessage::new).orElseStruct(COMPONENT_CODEC);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StructCodec<PlainMessage> codec() {
            // Renvoie une valeur à l'appelant
            return CODEC;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.OverrideOnly
    // Appelle une méthode
    StructCodec<? extends DialogBody> codec();

// Fin d'un bloc/d'une expression
}
