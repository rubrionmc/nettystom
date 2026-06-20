// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Objects;

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

    // Début d'une méthode/d'un bloc
    public PluginMessagePacket {
        // Appelle une méthode
        data = data.clone();
    // Fin d'un bloc/d'une expression
    }

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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof PluginMessagePacket(String channel1, byte[] data1))) return false;
        // Renvoie une valeur à l'appelant
        return Arrays.equals(data(), data1) && Objects.equals(channel(), channel1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = Objects.hashCode(channel());
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(data());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
