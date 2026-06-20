// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.SHORT;

// Déclaration de type (classe/interface/enum/record)
public record ClientCreativeInventoryActionPacket(short slot, ItemStack item) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientCreativeInventoryActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            SHORT, ClientCreativeInventoryActionPacket::slot,
            // Instruction de code
            ItemStack.UNTRUSTED_NETWORK_TYPE, ClientCreativeInventoryActionPacket::item,
            // Instruction de code
            ClientCreativeInventoryActionPacket::new);
// Fin d'un bloc/d'une expression
}
