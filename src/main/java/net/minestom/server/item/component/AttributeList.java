// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlotGroup;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeModifier;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record AttributeList(List<Modifier> modifiers) {
    // Appelle une méthode
    public static final AttributeList EMPTY = new AttributeList(List.of());

    // Affecte une valeur
    public static final NetworkBuffer.Type<AttributeList> NETWORK_TYPE = Modifier.NETWORK_TYPE.list(Short.MAX_VALUE)
            // Appelle une méthode
            .transform(AttributeList::new, AttributeList::modifiers);
    // Affecte une valeur
    public static final Codec<AttributeList> CODEC = Modifier.CODEC.list(Short.MAX_VALUE)
            // Appelle une méthode
            .transform(AttributeList::new, AttributeList::modifiers);

    // Déclaration de type (classe/interface/enum/record)
    public record Modifier(
            // Instruction de code
            Attribute attribute,
            // Instruction de code
            AttributeModifier modifier,
            // Instruction de code
            EquipmentSlotGroup slot,
            // Instruction de code
            Display display
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Modifier> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                Attribute.NETWORK_TYPE, Modifier::attribute,
                // Instruction de code
                AttributeModifier.NETWORK_TYPE, Modifier::modifier,
                // Instruction de code
                NetworkBuffer.Enum(EquipmentSlotGroup.class), Modifier::slot,
                // Instruction de code
                Display.NETWORK_TYPE, Modifier::display,
                // Instruction de code
                Modifier::new);
        // Affecte une valeur
        public static final Codec<Modifier> CODEC = StructCodec.struct(
                // Instruction de code
                "type", Attribute.CODEC, Modifier::attribute,
                // Instruction de code
                StructCodec.INLINE, AttributeModifier.CODEC, Modifier::modifier,
                // Instruction de code
                "slot", EquipmentSlotGroup.CODEC.optional(EquipmentSlotGroup.ANY), Modifier::slot,
                // Instruction de code
                "display", Display.CODEC.optional(Display.Default.INSTANCE), Modifier::display,
                // Instruction de code
                Modifier::new);

        // Instruction de code
        public Modifier(
                // Instruction de code
                Attribute attribute,
                // Instruction de code
                AttributeModifier modifier,
                // Instruction de code
                EquipmentSlotGroup slot
        // Début d'une méthode/d'un bloc
        ) {
            // Appelle une méthode
            this(attribute, modifier, slot, Display.Default.INSTANCE);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AttributeList {
        // Appelle une méthode
        modifiers = List.copyOf(modifiers);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AttributeList(Modifier modifier) {
        // Appelle une méthode
        this(List.of(modifier));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AttributeList with(Modifier modifier) {
        // Affecte une valeur
        List<Modifier> newModifiers = new ArrayList<>(modifiers);
        // Appelle une méthode
        newModifiers.add(modifier);
        // Renvoie une valeur à l'appelant
        return new AttributeList(newModifiers);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AttributeList remove(Modifier modifier) {
        // Affecte une valeur
        List<Modifier> newModifiers = new ArrayList<>(modifiers);
        // Appelle une méthode
        newModifiers.remove(modifier);
        // Renvoie une valeur à l'appelant
        return new AttributeList(newModifiers);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public sealed interface Display {
        // Affecte une valeur
        NetworkBuffer.Type<Display> NETWORK_TYPE = Type.NETWORK_TYPE
                // Appelle une méthode
                .unionType(Display::dataSerializer, Display::targetToType);
        // Appelle une méthode
        Codec<Display> CODEC = Type.CODEC.unionType(Display::codec, Display::targetToType);

        // Déclaration de type (classe/interface/enum/record)
        record Default() implements Display {
            // Appelle une méthode
            public static final Default INSTANCE = new Default();

            // Appelle une méthode
            public static final NetworkBuffer.Type<Default> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);
            // Appelle une méthode
            public static final StructCodec<Default> CODEC = StructCodec.struct(INSTANCE);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record Hidden() implements Display {
            // Appelle une méthode
            public static final Hidden INSTANCE = new Hidden();

            // Appelle une méthode
            public static final NetworkBuffer.Type<Hidden> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);
            // Appelle une méthode
            public static final StructCodec<Hidden> CODEC = StructCodec.struct(INSTANCE);
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        record Override(Component component) implements Display {
            // Affecte une valeur
            public static final NetworkBuffer.Type<Override> NETWORK_TYPE = NetworkBufferTemplate.template(
                    // Instruction de code
                    NetworkBuffer.COMPONENT, Override::component,
                    // Instruction de code
                    Override::new);
            // Affecte une valeur
            public static final StructCodec<Override> CODEC = StructCodec.struct(
                    // Instruction de code
                    "value", Codec.COMPONENT, Override::component,
                    // Instruction de code
                    Override::new);
        // Fin d'un bloc/d'une expression
        }


        // Déclaration de type (classe/interface/enum/record)
        enum Type {
            // Instruction de code
            DEFAULT, HIDDEN, OVERRIDE;

            // Appelle une méthode
            public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
            // Appelle une méthode
            public static final Codec<Type> CODEC = Codec.Enum(Type.class);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static NetworkBuffer.Type<? extends Display> dataSerializer(Type type) {
            // Renvoie une valeur à l'appelant
            return switch (type) {
                // Embranchement multiple (switch/case)
                case DEFAULT -> Default.NETWORK_TYPE;
                // Embranchement multiple (switch/case)
                case HIDDEN -> Hidden.NETWORK_TYPE;
                // Embranchement multiple (switch/case)
                case OVERRIDE -> Override.NETWORK_TYPE;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static StructCodec<? extends Display> codec(Type type) {
            // Renvoie une valeur à l'appelant
            return switch (type) {
                // Embranchement multiple (switch/case)
                case DEFAULT -> Default.CODEC;
                // Embranchement multiple (switch/case)
                case HIDDEN -> Hidden.CODEC;
                // Embranchement multiple (switch/case)
                case OVERRIDE -> Override.CODEC;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static Type targetToType(Display display) {
            // Renvoie une valeur à l'appelant
            return switch (display) {
                // Embranchement multiple (switch/case)
                case Default ignored -> Type.DEFAULT;
                // Embranchement multiple (switch/case)
                case Hidden ignored -> Type.HIDDEN;
                // Embranchement multiple (switch/case)
                case Override ignored -> Type.OVERRIDE;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
