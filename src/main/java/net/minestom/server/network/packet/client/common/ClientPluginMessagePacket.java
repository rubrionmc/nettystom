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

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record ClientPluginMessagePacket(String channel, byte[] data) implements ClientPacket.Configuration, ClientPacket.Play {
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
        // Appelle une méthode
        data = data.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof ClientPluginMessagePacket(String channel1, byte[] data1))) return false;
        // Renvoie une valeur à l'appelant
        return Arrays.equals(data(), data1) && channel().equals(channel1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = channel().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(data());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
