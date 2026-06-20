// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.UUID;

// Déclaration de type (classe/interface/enum/record)
public record ClientLoginStartPacket(String username,
                                     // Début d'une méthode/d'un bloc
                                     UUID profileId) implements ClientPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientLoginStartPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            STRING, ClientLoginStartPacket::username,
            // Instruction de code
            UUID, ClientLoginStartPacket::profileId,
            // Instruction de code
            ClientLoginStartPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientLoginStartPacket {
        // Embranchement : vérifie une condition
        if (username.length() > 16)
            // Lève une exception
            throw new IllegalArgumentException("Username is not allowed to be longer than 16 characters");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
