// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlayerAbilitiesPacket(byte flags) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlayerAbilitiesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE, ClientPlayerAbilitiesPacket::flags,
            // Instruction de code
            ClientPlayerAbilitiesPacket::new);
// Fin d'un bloc/d'une expression
}
