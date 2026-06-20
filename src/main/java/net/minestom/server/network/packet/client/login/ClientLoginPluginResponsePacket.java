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

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.RAW_BYTES;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientLoginPluginResponsePacket(int messageId, byte @Nullable [] data) implements ClientPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientLoginPluginResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientLoginPluginResponsePacket::messageId,
            // Instruction de code
            RAW_BYTES.optional(), ClientLoginPluginResponsePacket::data,
            // Instruction de code
            ClientLoginPluginResponsePacket::new);

    // Début d'une méthode/d'un bloc
    public ClientLoginPluginResponsePacket {
        // Appelle une méthode
        data = data != null ? data.clone() : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof ClientLoginPluginResponsePacket(int id, byte[] data1))) return false;
        // Renvoie une valeur à l'appelant
        return messageId() == id && Arrays.equals(data(), data1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = messageId();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(data());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
