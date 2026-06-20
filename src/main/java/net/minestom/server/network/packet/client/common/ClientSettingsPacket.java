// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ClientSettings;

// Déclaration de type (classe/interface/enum/record)
public record ClientSettingsPacket(ClientSettings settings) implements ClientPacket.Configuration, ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSettingsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            ClientSettings.NETWORK_TYPE, ClientSettingsPacket::settings,
            // Instruction de code
            ClientSettingsPacket::new);
// Fin d'un bloc/d'une expression
}
