// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.HashSet;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Set;

//TODO(1.21.5) hashes of components should be cached. Vanilla does it on a per player basis, could also do it globally perhaps.
// Type declaration (class/interface/enum/record)
final class ItemStackHashImpl {

    // Start of a method/block
    public static ItemStack.Hash of(Transcoder<Integer> hashCoder, ItemStack itemStack) {
        // Branch: checks a condition
        if (itemStack.isAir()) return net.minestom.server.item.ItemStack.Hash.AIR;

        // Calls a method
        final Map<DataComponent<?>, Integer> addedComponents = new HashMap<>();
        // Calls a method
        final Set<DataComponent<?>> removedComponents = new HashSet<>();
        // Loop: repeats a block
        for (var entry : itemStack.componentPatch().entrySet()) {
            // Branch: checks a condition
            if (entry.value() != null) {
                // Calls a method
                addedComponents.put(entry.component(), ((DataComponent<Object>) entry.component()).encode(hashCoder, entry.value()).orElseThrow());
            // Alternative branch of the condition
            } else {
                // Calls a method
                removedComponents.add(entry.component());
            // End of a block/expression
            }

        // End of a block/expression
        }
        // Returns a value to the caller
        return new ItemStackHashImpl.Item(
                // Code statement
                itemStack.material(),
                // Code statement
                itemStack.amount(),
                // Code statement
                addedComponents,
                // Code statement
                removedComponents
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<ItemStack.Hash> NETWORK_TYPE = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, ItemStack.Hash value) {
            // Branch: checks a condition
            if (!(value instanceof Item item)) {
                // Calls a method
                buffer.write(NetworkBuffer.BOOLEAN, false);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }

            // Calls a method
            buffer.write(NetworkBuffer.BOOLEAN, true);
            // Calls a method
            buffer.write(Item.NETWORK_TYPE, item);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ItemStack.Hash read(NetworkBuffer buffer) {
            // Branch: checks a condition
            if (!buffer.read(NetworkBuffer.BOOLEAN))
                // Returns a value to the caller
                return ItemStack.Hash.AIR;
            // Returns a value to the caller
            return buffer.read(Item.NETWORK_TYPE);
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Type declaration (class/interface/enum/record)
    record Air() implements ItemStack.Hash {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Item(
            // Code statement
            Material material,
            // Code statement
            int amount,
            // Code statement
            Map<DataComponent<?>, Integer> addedComponents,
            // Code statement
            Set<DataComponent<?>> removedComponents
    // Start of a method/block
    ) implements ItemStack.Hash {
        // Assigns a value
        private static final int MAX_COMPONENTS = 256;
        // Assigns a value
        public static final NetworkBuffer.Type<Item> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                Material.NETWORK_TYPE, Item::material,
                // Code statement
                NetworkBuffer.VAR_INT, Item::amount,
                // Code statement
                DataComponent.NETWORK_TYPE.mapValue(NetworkBuffer.INT, MAX_COMPONENTS), Item::addedComponents,
                // Code statement
                DataComponent.NETWORK_TYPE.set(MAX_COMPONENTS), Item::removedComponents,
                // Code statement
                Item::new);

        // Start of a method/block
        public Item {
            // Calls a method
            addedComponents = Map.copyOf(addedComponents);
            // Calls a method
            removedComponents = Set.copyOf(removedComponents);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
