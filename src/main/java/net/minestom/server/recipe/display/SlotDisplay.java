// Déclaration du paquet de ce fichier
package net.minestom.server.recipe.display;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.ComponentHolder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStackTemplate;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.TagKey;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Déclaration de type (classe/interface/enum/record)
public sealed interface SlotDisplay extends ComponentHolder<SlotDisplay> {

    // Affecte une valeur
    NetworkBuffer.Type<SlotDisplay> NETWORK_TYPE = SlotDisplayType.NETWORK_TYPE
            // Appelle une méthode
            .unionType(SlotDisplay::dataSerializer, SlotDisplay::slotDisplayToType);

    // Déclaration de type (classe/interface/enum/record)
    final class Empty implements SlotDisplay {
        // Appelle une méthode
        public static final Empty INSTANCE = new Empty();

        // Appelle une méthode
        public static final NetworkBuffer.Type<Empty> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);

        // Début d'une méthode/d'un bloc
        private Empty() {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class AnyFuel implements SlotDisplay {
        // Appelle une méthode
        public static final AnyFuel INSTANCE = new AnyFuel();

        // Appelle une méthode
        public static final NetworkBuffer.Type<AnyFuel> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);

        // Début d'une méthode/d'un bloc
        private AnyFuel() {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record WithAnyPotion(SlotDisplay display) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<WithAnyPotion> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, WithAnyPotion::display,
                // Instruction de code
                WithAnyPotion::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OnlyWithComponent(SlotDisplay source, DataComponent<?> component) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<OnlyWithComponent> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, OnlyWithComponent::source,
                // Instruction de code
                DataComponent.NETWORK_TYPE, OnlyWithComponent::component,
                // Instruction de code
                OnlyWithComponent::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Item(Material material) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Item> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                Material.NETWORK_TYPE, Item::material,
                // Instruction de code
                Item::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ItemStack(net.minestom.server.item.ItemStack itemStack) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<ItemStack> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                ItemStackTemplate.NETWORK_TYPE, ItemStack::itemStack,
                // Instruction de code
                ItemStack::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Renvoie une valeur à l'appelant
            return net.minestom.server.item.ItemStack.textComponents(itemStack);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new ItemStack(net.minestom.server.item.ItemStack.copyWithOperator(itemStack, operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Tag(TagKey<Material> tag) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Tag> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                TagKey.networkType(_ -> Material.staticRegistry()), Tag::tag,
                // Instruction de code
                Tag::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Dyed(SlotDisplay dye, SlotDisplay target) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Dyed> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Dyed::dye,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, Dyed::target,
                // Instruction de code
                Dyed::new);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SmithingTrim(
            // Instruction de code
            SlotDisplay base,
            // Instruction de code
            SlotDisplay trimMaterial,
            // Instruction de code
            SlotDisplay trimPattern
    // Début d'une méthode/d'un bloc
    ) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<SmithingTrim> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, SmithingTrim::base,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, SmithingTrim::trimMaterial,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, SmithingTrim::trimPattern,
                // Instruction de code
                SmithingTrim::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<>(base.components());
            // Appelle une méthode
            components.addAll(trimMaterial.components());
            // Appelle une méthode
            components.addAll(trimPattern.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new SmithingTrim(base.copyWithOperator(operator),
                    // Instruction de code
                    trimMaterial.copyWithOperator(operator),
                    // Appelle une méthode
                    trimPattern.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record WithRemainder(SlotDisplay input, SlotDisplay remainder) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<WithRemainder> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, WithRemainder::input,
                // Instruction de code
                SlotDisplay.NETWORK_TYPE, WithRemainder::remainder,
                // Instruction de code
                WithRemainder::new);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<>(input.components());
            // Appelle une méthode
            components.addAll(remainder.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Renvoie une valeur à l'appelant
            return new WithRemainder(input.copyWithOperator(operator), remainder.copyWithOperator(operator));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Composite(List<SlotDisplay> contents) implements SlotDisplay {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Composite> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                SlotDisplay.NETWORK_TYPE.list(), Composite::contents,
                // Instruction de code
                Composite::new);

        // Début d'une méthode/d'un bloc
        public Composite {
            // Appelle une méthode
            contents = List.copyOf(contents);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Collection<Component> components() {
            // Appelle une méthode
            final var components = new ArrayList<Component>();
            // Boucle : répète un bloc
            for (var display : contents)
                // Appelle une méthode
                components.addAll(display.components());
            // Renvoie une valeur à l'appelant
            return List.copyOf(components);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Appelle une méthode
            final var newContents = new ArrayList<SlotDisplay>();
            // Boucle : répète un bloc
            for (var display : contents)
                // Appelle une méthode
                newContents.add(display.copyWithOperator(operator));
            // Renvoie une valeur à l'appelant
            return new Composite(newContents);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Collection<Component> components() {
        // Renvoie une valeur à l'appelant
        return List.of();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static NetworkBuffer.Type<? extends SlotDisplay> dataSerializer(SlotDisplayType type) {
        // Renvoie une valeur à l'appelant
        return switch (type) {
            // Embranchement multiple (switch/case)
            case EMPTY -> Empty.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case ANY_FUEL -> AnyFuel.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case WITH_ANY_POTION -> WithAnyPotion.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case ONLY_WITH_COMPONENT -> OnlyWithComponent.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case ITEM -> Item.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case ITEM_STACK -> ItemStack.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case TAG -> Tag.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case DYED -> Dyed.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case SMITHING_TRIM -> SmithingTrim.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case WITH_REMAINDER -> WithRemainder.NETWORK_TYPE;
            // Embranchement multiple (switch/case)
            case COMPOSITE -> Composite.NETWORK_TYPE;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static SlotDisplayType slotDisplayToType(SlotDisplay slotDisplay) {
        // Renvoie une valeur à l'appelant
        return switch (slotDisplay) {
            // Embranchement multiple (switch/case)
            case Empty _ -> SlotDisplayType.EMPTY;
            // Embranchement multiple (switch/case)
            case AnyFuel _ -> SlotDisplayType.ANY_FUEL;
            // Embranchement multiple (switch/case)
            case Item _ -> SlotDisplayType.ITEM;
            // Embranchement multiple (switch/case)
            case ItemStack _ -> SlotDisplayType.ITEM_STACK;
            // Embranchement multiple (switch/case)
            case Tag _ -> SlotDisplayType.TAG;
            // Embranchement multiple (switch/case)
            case SmithingTrim _ -> SlotDisplayType.SMITHING_TRIM;
            // Embranchement multiple (switch/case)
            case WithRemainder _ -> SlotDisplayType.WITH_REMAINDER;
            // Embranchement multiple (switch/case)
            case Composite _ -> SlotDisplayType.COMPOSITE;
            // Embranchement multiple (switch/case)
            case Dyed _ -> SlotDisplayType.DYED;
            // Embranchement multiple (switch/case)
            case OnlyWithComponent _ -> SlotDisplayType.ONLY_WITH_COMPONENT;
            // Embranchement multiple (switch/case)
            case WithAnyPotion _ -> SlotDisplayType.WITH_ANY_POTION;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
