// Package declaration for this file
package net.minestom.server.recipe.display;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.adventure.ComponentHolder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.item.ItemStackTemplate;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.TagKey;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Type declaration (class/interface/enum/record)
public sealed interface SlotDisplay extends ComponentHolder<SlotDisplay> {

    // Assigns a value
    NetworkBuffer.Type<SlotDisplay> NETWORK_TYPE = SlotDisplayType.NETWORK_TYPE
            // Calls a method
            .unionType(SlotDisplay::dataSerializer, SlotDisplay::slotDisplayToType);

    // Type declaration (class/interface/enum/record)
    final class Empty implements SlotDisplay {
        // Calls a method
        public static final Empty INSTANCE = new Empty();

        // Calls a method
        public static final NetworkBuffer.Type<Empty> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);

        // Start of a method/block
        private Empty() {
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class AnyFuel implements SlotDisplay {
        // Calls a method
        public static final AnyFuel INSTANCE = new AnyFuel();

        // Calls a method
        public static final NetworkBuffer.Type<AnyFuel> NETWORK_TYPE = NetworkBufferTemplate.template(INSTANCE);

        // Start of a method/block
        private AnyFuel() {
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record WithAnyPotion(SlotDisplay display) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<WithAnyPotion> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, WithAnyPotion::display,
                // Code statement
                WithAnyPotion::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OnlyWithComponent(SlotDisplay source, DataComponent<?> component) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<OnlyWithComponent> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, OnlyWithComponent::source,
                // Code statement
                DataComponent.NETWORK_TYPE, OnlyWithComponent::component,
                // Code statement
                OnlyWithComponent::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Item(Material material) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Item> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                Material.NETWORK_TYPE, Item::material,
                // Code statement
                Item::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ItemStack(net.minestom.server.item.ItemStack itemStack) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<ItemStack> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                ItemStackTemplate.NETWORK_TYPE, ItemStack::itemStack,
                // Code statement
                ItemStack::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Returns a value to the caller
            return net.minestom.server.item.ItemStack.textComponents(itemStack);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new ItemStack(net.minestom.server.item.ItemStack.copyWithOperator(itemStack, operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Tag(TagKey<Material> tag) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Tag> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                TagKey.networkType(_ -> Material.staticRegistry()), Tag::tag,
                // Code statement
                Tag::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Dyed(SlotDisplay dye, SlotDisplay target) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Dyed> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, Dyed::dye,
                // Code statement
                SlotDisplay.NETWORK_TYPE, Dyed::target,
                // Code statement
                Dyed::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SmithingTrim(
            // Code statement
            SlotDisplay base,
            // Code statement
            SlotDisplay trimMaterial,
            // Code statement
            SlotDisplay trimPattern
    // Start of a method/block
    ) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<SmithingTrim> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, SmithingTrim::base,
                // Code statement
                SlotDisplay.NETWORK_TYPE, SmithingTrim::trimMaterial,
                // Code statement
                SlotDisplay.NETWORK_TYPE, SmithingTrim::trimPattern,
                // Code statement
                SmithingTrim::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<>(base.components());
            // Calls a method
            components.addAll(trimMaterial.components());
            // Calls a method
            components.addAll(trimPattern.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new SmithingTrim(base.copyWithOperator(operator),
                    // Code statement
                    trimMaterial.copyWithOperator(operator),
                    // Calls a method
                    trimPattern.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record WithRemainder(SlotDisplay input, SlotDisplay remainder) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<WithRemainder> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE, WithRemainder::input,
                // Code statement
                SlotDisplay.NETWORK_TYPE, WithRemainder::remainder,
                // Code statement
                WithRemainder::new);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<>(input.components());
            // Calls a method
            components.addAll(remainder.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Returns a value to the caller
            return new WithRemainder(input.copyWithOperator(operator), remainder.copyWithOperator(operator));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Composite(List<SlotDisplay> contents) implements SlotDisplay {
        // Assigns a value
        public static final NetworkBuffer.Type<Composite> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                SlotDisplay.NETWORK_TYPE.list(), Composite::contents,
                // Code statement
                Composite::new);

        // Start of a method/block
        public Composite {
            // Calls a method
            contents = List.copyOf(contents);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Collection<Component> components() {
            // Calls a method
            final var components = new ArrayList<Component>();
            // Loop: repeats a block
            for (var display : contents)
                // Calls a method
                components.addAll(display.components());
            // Returns a value to the caller
            return List.copyOf(components);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
            // Calls a method
            final var newContents = new ArrayList<SlotDisplay>();
            // Loop: repeats a block
            for (var display : contents)
                // Calls a method
                newContents.add(display.copyWithOperator(operator));
            // Returns a value to the caller
            return new Composite(newContents);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Collection<Component> components() {
        // Returns a value to the caller
        return List.of();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default SlotDisplay copyWithOperator(UnaryOperator<Component> operator) {
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    private static NetworkBuffer.Type<? extends SlotDisplay> dataSerializer(SlotDisplayType type) {
        // Returns a value to the caller
        return switch (type) {
            // Multiple branching (switch/case)
            case EMPTY -> Empty.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case ANY_FUEL -> AnyFuel.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case WITH_ANY_POTION -> WithAnyPotion.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case ONLY_WITH_COMPONENT -> OnlyWithComponent.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case ITEM -> Item.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case ITEM_STACK -> ItemStack.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case TAG -> Tag.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case DYED -> Dyed.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case SMITHING_TRIM -> SmithingTrim.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case WITH_REMAINDER -> WithRemainder.NETWORK_TYPE;
            // Multiple branching (switch/case)
            case COMPOSITE -> Composite.NETWORK_TYPE;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static SlotDisplayType slotDisplayToType(SlotDisplay slotDisplay) {
        // Returns a value to the caller
        return switch (slotDisplay) {
            // Multiple branching (switch/case)
            case Empty _ -> SlotDisplayType.EMPTY;
            // Multiple branching (switch/case)
            case AnyFuel _ -> SlotDisplayType.ANY_FUEL;
            // Multiple branching (switch/case)
            case Item _ -> SlotDisplayType.ITEM;
            // Multiple branching (switch/case)
            case ItemStack _ -> SlotDisplayType.ITEM_STACK;
            // Multiple branching (switch/case)
            case Tag _ -> SlotDisplayType.TAG;
            // Multiple branching (switch/case)
            case SmithingTrim _ -> SlotDisplayType.SMITHING_TRIM;
            // Multiple branching (switch/case)
            case WithRemainder _ -> SlotDisplayType.WITH_REMAINDER;
            // Multiple branching (switch/case)
            case Composite _ -> SlotDisplayType.COMPOSITE;
            // Multiple branching (switch/case)
            case Dyed _ -> SlotDisplayType.DYED;
            // Multiple branching (switch/case)
            case OnlyWithComponent _ -> SlotDisplayType.ONLY_WITH_COMPONENT;
            // Multiple branching (switch/case)
            case WithAnyPotion _ -> SlotDisplayType.WITH_ANY_POTION;
        // End of a block/expression
        };
    // End of a block/expression
    }

// End of a block/expression
}
