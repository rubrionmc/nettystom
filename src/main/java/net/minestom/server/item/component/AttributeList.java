// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.EquipmentSlotGroup;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeModifier;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record AttributeList(List<Modifier> modifiers) {
    // Calls a method
    public static final AttributeList EMPTY = new AttributeList(List.of());

    // Assigns a value
    public static final NetworkBuffer.Type<AttributeList> NETWORK_TYPE = Modifier.NETWORK_TYPE.list(Short.MAX_VALUE)
            // Calls a method
            .transform(AttributeList::new, AttributeList::modifiers);
    // Assigns a value
    public static final Codec<AttributeList> CODEC = Modifier.CODEC.list(Short.MAX_VALUE)
            // Calls a method
            .transform(AttributeList::new, AttributeList::modifiers);

    // Type declaration (class/interface/enum/record)
    public record Modifier(
            // Code statement
            Attribute attribute,
            // Code statement
            AttributeModifier modifier,
            // Code statement
            EquipmentSlotGroup slot,
            // Code statement
            Display display
    // Start of a method/block
    ) {
        // Assigns a value
        public static final NetworkBuffer.Type<Modifier> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                Attribute.NETWORK_TYPE, Modifier::attribute,
                // Code statement
                AttributeModifier.NETWORK_TYPE, Modifier::modifier,
                // Code statement
                NetworkBuffer.Enum(EquipmentSlotGroup.class), Modifier::slot,
                // Code statement
                Display.NETWORK_TYPE, Modifier::display,
                // Code statement
                Modifier::new);
        // Assigns a value
        public static final Codec<Modifier> CODEC = StructCodec.struct(
                // Code statement
                "type", Attribute.CODEC, Modifier::attribute,
                // Code statement
                StructCodec.INLINE, AttributeModifier.CODEC, Modifier::modifier,
                // Code statement
                "slot", EquipmentSlotGroup.CODEC.optional(EquipmentSlotGroup.ANY), Modifier::slot,
                // Code statement
                "display", Display.CODEC.optional(Display.Default.INSTANCE), Modifier::display,
                // Code statement
                Modifier::new);

        // Code statement
        public Modifier(
                // Code statement
                Attribute attribute,
                // Code statement
                AttributeModifier modifier,
                // Code statement
                EquipmentSlotGroup slot
        // Start of a method/block
        ) {
            // Calls a method
            this(attribute, modifier, slot, Display.Default.INSTANCE);
        // End of a block/expression
        }

    // End of a block/expression
    }

    // Start of a method/block
    public AttributeList {
        // Calls a method
        modifiers = List.copyOf(modifiers);
    // End of a block/expression
    }

    // Start of a method/block
    public AttributeList(Modifier modifier) {
        // Calls a method
        this(List.of(modifier));
    // End of a block/expression
    }

    // Start of a method/block
    public AttributeList with(Modifier modifier) {
        // Calls a method
        List<Modifier> newModifiers = new ArrayList<>(modifiers);
        // Calls a method
        newModifiers.add(modifier);
        // Returns a value to the caller
        return new AttributeList(newModifiers);
    // End of a block/expression
    }

    // Start of a method/block
    public AttributeList remove(Modifier modifier) {
        // Calls a method
        List<Modifier> newModifiers = new ArrayList<>(modifiers);
        // Calls a method
        newModifiers.remove(modifier);
        // Returns a value to the caller
        return new AttributeList(newModifiers);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public sealed interface Display {
        // Assigns a value
        NetworkBuffer.Type<Display> NETWORK_TYPE = Type.NETWORK_TYPE
                // Calls a method
                .unionType(Display::dataSerializer, Display::targetToType);
        // Calls a method
        Codec<Display> CODEC = Type.CODEC.unionType(Display::codec, Display::targetToType);

        // Type declaration (class/interface/enum/record)
        record Default() implements Display {
            // Calls a method
            public static final Default INSTANCE = new Default();

            // Calls a method
            public static final NetworkBuffer.Type<Default> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);
            // Calls a method
            public static final StructCodec<Default> CODEC = StructCodec.struct(INSTANCE);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record Hidden() implements Display {
            // Calls a method
            public static final Hidden INSTANCE = new Hidden();

            // Calls a method
            public static final NetworkBuffer.Type<Hidden> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);
            // Calls a method
            public static final StructCodec<Hidden> CODEC = StructCodec.struct(INSTANCE);
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        record Override(Component component) implements Display {
            // Assigns a value
            public static final NetworkBuffer.Type<Override> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Code statement
                    NetworkBuffer.COMPONENT, Override::component,
                    // Code statement
                    Override::new);
            // Assigns a value
            public static final StructCodec<Override> CODEC = StructCodec.struct(
                    // Code statement
                    "value", Codec.COMPONENT, Override::component,
                    // Code statement
                    Override::new);
        // End of a block/expression
        }


        // Type declaration (class/interface/enum/record)
        enum Type {
            // Code statement
            DEFAULT, HIDDEN, OVERRIDE;

            // Calls a method
            public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
            // Calls a method
            public static final Codec<Type> CODEC = Codec.Enum(Type.class);
        // End of a block/expression
        }

        // Start of a method/block
        private static NetworkBuffer.Type<? extends Display> dataSerializer(Type type) {
            // Returns a value to the caller
            return switch (type) {
                // Multiple branching (switch/case)
                case DEFAULT -> Default.NETWORK_TYPE;
                // Multiple branching (switch/case)
                case HIDDEN -> Hidden.NETWORK_TYPE;
                // Multiple branching (switch/case)
                case OVERRIDE -> Override.NETWORK_TYPE;
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        private static StructCodec<? extends Display> codec(Type type) {
            // Returns a value to the caller
            return switch (type) {
                // Multiple branching (switch/case)
                case DEFAULT -> Default.CODEC;
                // Multiple branching (switch/case)
                case HIDDEN -> Hidden.CODEC;
                // Multiple branching (switch/case)
                case OVERRIDE -> Override.CODEC;
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        private static Type targetToType(Display display) {
            // Returns a value to the caller
            return switch (display) {
                // Multiple branching (switch/case)
                case Default ignored -> Type.DEFAULT;
                // Multiple branching (switch/case)
                case Hidden ignored -> Type.HIDDEN;
                // Multiple branching (switch/case)
                case Override ignored -> Type.OVERRIDE;
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
