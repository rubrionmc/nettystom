// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Arrays;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record LoginPluginRequestPacket(int messageId, String channel,
                                       // Début d'une méthode/d'un bloc
                                       byte[] data) implements ServerPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<LoginPluginRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, LoginPluginRequestPacket::messageId,
            // Instruction de code
            STRING, LoginPluginRequestPacket::channel,
            // Instruction de code
            RAW_BYTES, LoginPluginRequestPacket::data,
            // Instruction de code
            LoginPluginRequestPacket::new);

    // Début d'une méthode/d'un bloc
    public LoginPluginRequestPacket {
        // Appelle une méthode
        data = data.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object object) {
        // Embranchement : vérifie une condition
        if (!(object instanceof LoginPluginRequestPacket(int messageId1, String channel1, byte[] data1))) return false;
        // Renvoie une valeur à l'appelant
        return messageId() == messageId1 && Arrays.equals(data(), data1) && channel().equals(channel1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = messageId();
        // Appelle une méthode
        result = 31 * result + channel().hashCode();
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(data());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
