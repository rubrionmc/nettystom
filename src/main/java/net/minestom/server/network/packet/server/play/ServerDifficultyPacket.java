// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.world.Difficulty;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.Enum;

// Déclaration de type (classe/interface/enum/record)
public record ServerDifficultyPacket(Difficulty difficulty, boolean locked) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ServerDifficultyPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Enum(Difficulty.class), ServerDifficultyPacket::difficulty,
            // Instruction de code
            BOOLEAN, ServerDifficultyPacket::locked,
            // Instruction de code
            ServerDifficultyPacket::new);
// Fin d'un bloc/d'une expression
}
