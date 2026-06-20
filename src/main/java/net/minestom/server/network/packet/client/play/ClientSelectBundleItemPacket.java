// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientSelectBundleItemPacket(int slot, int selectedIndex) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSelectBundleItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientSelectBundleItemPacket::slot,
            // Instruction de code
            VAR_INT, ClientSelectBundleItemPacket::selectedIndex,
            // Instruction de code
            ClientSelectBundleItemPacket::new);
// Fin d'un bloc/d'une expression
}
