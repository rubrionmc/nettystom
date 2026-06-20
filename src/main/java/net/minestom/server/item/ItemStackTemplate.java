// Déclaration du paquet de ce fichier
package net.minestom.server.item;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public interface ItemStackTemplate {
    // Affecte une valeur
    NetworkBuffer.Type<ItemStack> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            Material.NETWORK_TYPE, ItemStack::material,
            // Instruction de code
            NetworkBuffer.VAR_INT, ItemStack::amount,
            // Instruction de code
            DataComponent.PATCH_NETWORK_TYPE, (i) -> ((ItemStackImpl) i).components(),
            // Instruction de code
            ItemStack::of);
    // Affecte une valeur
    Codec<ItemStack> CODEC = ItemStack.CODEC
            // Appelle une méthode
            .orElse(Material.CODEC.transform(ItemStack::of, ItemStack::material));
// Fin d'un bloc/d'une expression
}
