// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientLoginPluginResponsePacket(int messageId, byte @Nullable [] data) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientLoginPluginResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientLoginPluginResponsePacket::messageId,
            // Instruction de code
            RAW_BYTES.optional(), ClientLoginPluginResponsePacket::data,
            // Instruction de code
            ClientLoginPluginResponsePacket::new);
// Fin d'un bloc/d'une expression
}
