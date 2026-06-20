// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record PluginMessagePacket(String channel,
                                  // Début d'une méthode/d'un bloc
                                  byte[] data) implements ServerPacket.Configuration, ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PluginMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, PluginMessagePacket::channel,
            // Instruction de code
            RAW_BYTES, PluginMessagePacket::data,
            // Instruction de code
            PluginMessagePacket::new);

    /**
     * Gets the current server brand name packet.
     * <p>
     * Sent to all players when the name changes.
     *
     * @return the current brand name packet
     */
    // Début d'une méthode/d'un bloc
    public static PluginMessagePacket brandPacket(String brandName) {
        // Appelle une méthode
        final byte[] data = NetworkBuffer.makeArray(STRING, brandName);
        // Renvoie une valeur à l'appelant
        return new PluginMessagePacket("minecraft:brand", data);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
