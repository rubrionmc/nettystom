// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public interface ItemStackTemplate {
    // Assigns a value
    NetworkBuffer.Type<ItemStack> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            Material.NETWORK_TYPE, ItemStack::material,
            // Code statement
            NetworkBuffer.VAR_INT, ItemStack::amount,
            // Code statement
            DataComponent.PATCH_NETWORK_TYPE, (i) -> ((ItemStackImpl) i).components(),
            // Code statement
            ItemStack::of);
    // Assigns a value
    Codec<ItemStack> CODEC = ItemStack.CODEC
            // Calls a method
            .orElse(Material.CODEC.transform(ItemStack::of, ItemStack::material));
// End of a block/expression
}
