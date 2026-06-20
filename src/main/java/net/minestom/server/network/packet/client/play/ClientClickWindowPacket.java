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

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientClickWindowPacket(int windowId, int stateId,
                                      // Instruction de code
                                      short slot, byte button, ClickType clickType,
                                      // Instruction de code
                                      Map<Short, ItemStack.Hash> changedSlots,
                                      // Début d'une méthode/d'un bloc
                                      ItemStack.Hash clickedItem) implements ClientPacket.Play {
    // Affecte une valeur
    public static final int MAX_CHANGED_SLOTS = 128;

    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientClickWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientClickWindowPacket::windowId,
            // Instruction de code
            VAR_INT, ClientClickWindowPacket::stateId,
            // Instruction de code
            SHORT, ClientClickWindowPacket::slot,
            // Instruction de code
            BYTE, ClientClickWindowPacket::button,
            // Instruction de code
            Enum(ClickType.class), ClientClickWindowPacket::clickType,
            // Instruction de code
            SHORT.mapValue(ItemStack.Hash.NETWORK_TYPE, MAX_CHANGED_SLOTS), ClientClickWindowPacket::changedSlots,
            // Instruction de code
            ItemStack.Hash.NETWORK_TYPE, ClientClickWindowPacket::clickedItem,
            // Instruction de code
            ClientClickWindowPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientClickWindowPacket {
        // Appelle une méthode
        changedSlots = Map.copyOf(changedSlots);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum ClickType {
        // Instruction de code
        PICKUP,
        // Instruction de code
        QUICK_MOVE,
        // Instruction de code
        SWAP,
        // Instruction de code
        CLONE,
        // Instruction de code
        THROW,
        // Instruction de code
        QUICK_CRAFT,
        // Instruction de code
        PICKUP_ALL
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
