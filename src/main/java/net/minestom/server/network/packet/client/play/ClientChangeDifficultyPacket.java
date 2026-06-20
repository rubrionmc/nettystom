// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.world.Difficulty;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.Enum;

// Déclaration de type (classe/interface/enum/record)
public record ClientChangeDifficultyPacket(Difficulty difficulty, boolean locked) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientChangeDifficultyPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Enum(Difficulty.class), ClientChangeDifficultyPacket::difficulty,
            // Instruction de code
            BOOLEAN, ClientChangeDifficultyPacket::locked,
            // Instruction de code
            ClientChangeDifficultyPacket::new);
// Fin d'un bloc/d'une expression
}
