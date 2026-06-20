// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public record PlayerInfoRemovePacket(List<UUID> uuids) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 1024;

    // Affecte une valeur
    public static final NetworkBuffer.Type<PlayerInfoRemovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.UUID.list(MAX_ENTRIES), PlayerInfoRemovePacket::uuids,
            // Instruction de code
            PlayerInfoRemovePacket::new);

    // Début d'une méthode/d'un bloc
    public PlayerInfoRemovePacket(UUID uuid) {
        // Appelle une méthode
        this(List.of(uuid));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PlayerInfoRemovePacket {
        // Appelle une méthode
        uuids = List.copyOf(uuids);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
