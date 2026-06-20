// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.common;

// Import d'une classe nécessaire
import net.kyori.adventure.resource.ResourcePackStatus;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.UUID;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record ClientResourcePackStatusPacket(
        // Instruction de code
        UUID id,
        // Instruction de code
        ResourcePackStatus status
// Début d'une méthode/d'un bloc
) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientResourcePackStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            UUID, ClientResourcePackStatusPacket::id,
            // Instruction de code
            VAR_INT.transform(ClientResourcePackStatusPacket::readStatus, ClientResourcePackStatusPacket::statusId), ClientResourcePackStatusPacket::status,
            // Instruction de code
            ClientResourcePackStatusPacket::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    private static ResourcePackStatus readStatus(int id) {
        // Renvoie une valeur à l'appelant
        return switch (id) {
            // Embranchement multiple (switch/case)
            case 0 -> ResourcePackStatus.SUCCESSFULLY_LOADED;
            // Embranchement multiple (switch/case)
            case 1 -> ResourcePackStatus.DECLINED;
            // Embranchement multiple (switch/case)
            case 2 -> ResourcePackStatus.FAILED_DOWNLOAD;
            // Embranchement multiple (switch/case)
            case 3 -> ResourcePackStatus.ACCEPTED;
            // Embranchement multiple (switch/case)
            case 4 -> ResourcePackStatus.DOWNLOADED;
            // Embranchement multiple (switch/case)
            case 5 -> ResourcePackStatus.INVALID_URL;
            // Embranchement multiple (switch/case)
            case 6 -> ResourcePackStatus.FAILED_RELOAD;
            // Embranchement multiple (switch/case)
            case 7 -> ResourcePackStatus.DISCARDED;
            // Appelle une méthode
            default -> throw new IllegalStateException("Unexpected resource pack status: " + id);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int statusId(ResourcePackStatus status) {
        // Renvoie une valeur à l'appelant
        return switch (status) {
            // Embranchement multiple (switch/case)
            case SUCCESSFULLY_LOADED -> 0;
            // Embranchement multiple (switch/case)
            case DECLINED -> 1;
            // Embranchement multiple (switch/case)
            case FAILED_DOWNLOAD -> 2;
            // Embranchement multiple (switch/case)
            case ACCEPTED -> 3;
            // Embranchement multiple (switch/case)
            case DOWNLOADED -> 4;
            // Embranchement multiple (switch/case)
            case INVALID_URL -> 5;
            // Embranchement multiple (switch/case)
            case FAILED_RELOAD -> 6;
            // Embranchement multiple (switch/case)
            case DISCARDED -> 7;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
