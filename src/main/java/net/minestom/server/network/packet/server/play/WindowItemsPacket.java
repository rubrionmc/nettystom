// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.UnaryOperator;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record WindowItemsPacket(int windowId, int stateId, List<ItemStack> items,
                                // Start of a method/block
                                ItemStack carriedItem) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    // Assigns a value
    public static final int MAX_ENTRIES = 128;

    // Assigns a value
    public static final NetworkBuffer.Type<WindowItemsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, WindowItemsPacket::windowId,
            // Code statement
            VAR_INT, WindowItemsPacket::stateId,
            // Code statement
            ItemStack.NETWORK_TYPE.list(MAX_ENTRIES), WindowItemsPacket::items,
            // Code statement
            ItemStack.NETWORK_TYPE, WindowItemsPacket::carriedItem,
            // Code statement
            WindowItemsPacket::new);

    // Start of a method/block
    public WindowItemsPacket {
        // Calls a method
        items = List.copyOf(items);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Component> components() {
        // Calls a method
        final var list = new ArrayList<>(this.items);
        // Calls a method
        list.add(this.carriedItem);

        // Calls a method
        final var components = new ArrayList<Component>();

        // Start of a method/block
        list.forEach(itemStack -> {
            // Calls a method
            components.addAll(itemStack.get(DataComponents.LORE, List.of()));

            // Calls a method
            final var customName = itemStack.get(DataComponents.CUSTOM_NAME);
            // Branch: checks a condition
            if (customName != null) {
                // Calls a method
                components.add(customName);
            // End of a block/expression
            }

            // Calls a method
            final var itemName = itemStack.get(DataComponents.ITEM_NAME);
            // Branch: checks a condition
            if (itemName != null) {
                // Calls a method
                components.add(itemName);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Returns a value to the caller
        return components;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerPacket copyWithOperator(UnaryOperator<Component> operator) {
        // Assigns a value
        UnaryOperator<List<Component>> loreOperator = lines -> {
            // Calls a method
            final var translatedComponents = new ArrayList<Component>();
            // Calls a method
            lines.forEach(component -> translatedComponents.add(operator.apply(component)));
            // Returns a value to the caller
            return translatedComponents;
        // End of a block/expression
        };
        // Returns a value to the caller
        return new WindowItemsPacket(
                // Access to the current/parent object
                this.windowId,
                // Access to the current/parent object
                this.stateId,
                // Access to the current/parent object
                this.items.stream().map(stack -> stack
                                // Code statement
                                .with(DataComponents.ITEM_NAME, operator)
                                // Code statement
                                .with(DataComponents.CUSTOM_NAME, operator)
                                // Code statement
                                .with(DataComponents.LORE, loreOperator))
                        // Code statement
                        .toList(),
                // Access to the current/parent object
                this.carriedItem
                        // Code statement
                        .with(DataComponents.ITEM_NAME, operator)
                        // Code statement
                        .with(DataComponents.CUSTOM_NAME, operator)
                        // Code statement
                        .with(DataComponents.LORE, loreOperator)
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
