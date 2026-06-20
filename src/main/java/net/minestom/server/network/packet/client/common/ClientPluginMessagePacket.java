// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientPluginMessagePacket(String channel, byte[] data) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPluginMessagePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientPluginMessagePacket::channel,
            // Instruction de code
            RAW_BYTES, ClientPluginMessagePacket::data,
            // Instruction de code
            ClientPluginMessagePacket::new);

    // Début d'une méthode/d'un bloc
    public ClientPluginMessagePacket {
        // Embranchement : vérifie une condition
        if (channel.length() > 256)
            // Lève une exception
            throw new IllegalArgumentException("Channel cannot be more than 256 characters long");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (o == null || getClass() != o.getClass()) return false;
        // Affecte une valeur
        ClientPluginMessagePacket that = (ClientPluginMessagePacket) o;
        // Renvoie une valeur à l'appelant
        return Objects.deepEquals(data, that.data) && Objects.equals(channel, that.channel);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(channel, Arrays.hashCode(data));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
