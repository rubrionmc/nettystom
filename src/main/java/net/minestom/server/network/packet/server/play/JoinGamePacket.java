// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record JoinGamePacket(
        // Instruction de code
        int entityId, boolean isHardcore, List<String> worlds, int maxPlayers,
        // Instruction de code
        int viewDistance, int simulationDistance, boolean reducedDebugInfo, boolean enableRespawnScreen,
        // Instruction de code
        boolean doLimitedCrafting, int dimensionType,
        // Instruction de code
        String world, long hashedSeed, GameMode gameMode, GameMode previousGameMode,
        // Instruction de code
        boolean isDebug, boolean isFlat, @Nullable WorldPos deathLocation, int portalCooldown,
        // Instruction de code
        int seaLevel, boolean enforcesSecureChat
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_WORLDS = Short.MAX_VALUE;

    // Début d'une méthode/d'un bloc
    public JoinGamePacket {
        // Appelle une méthode
        worlds = List.copyOf(worlds);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<JoinGamePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, JoinGamePacket::entityId,
            // Instruction de code
            BOOLEAN, JoinGamePacket::isHardcore,
            // Instruction de code
            STRING.list(MAX_WORLDS), JoinGamePacket::worlds,
            // Instruction de code
            VAR_INT, JoinGamePacket::maxPlayers,
            // Instruction de code
            VAR_INT, JoinGamePacket::viewDistance,
            // Instruction de code
            VAR_INT, JoinGamePacket::simulationDistance,
            // Instruction de code
            BOOLEAN, JoinGamePacket::reducedDebugInfo,
            // Instruction de code
            BOOLEAN, JoinGamePacket::enableRespawnScreen,
            // Instruction de code
            BOOLEAN, JoinGamePacket::doLimitedCrafting,
            // Instruction de code
            VAR_INT, JoinGamePacket::dimensionType,
            // Instruction de code
            STRING, JoinGamePacket::world,
            // Instruction de code
            LONG, JoinGamePacket::hashedSeed,
            // Instruction de code
            GameMode.NETWORK_TYPE, JoinGamePacket::gameMode,
            // Instruction de code
            GameMode.OPT_NETWORK_TYPE, JoinGamePacket::previousGameMode,
            // Instruction de code
            BOOLEAN, JoinGamePacket::isDebug,
            // Instruction de code
            BOOLEAN, JoinGamePacket::isFlat,
            // Instruction de code
            WorldPos.NETWORK_TYPE.optional(), JoinGamePacket::deathLocation,
            // Instruction de code
            VAR_INT, JoinGamePacket::portalCooldown,
            // Instruction de code
            VAR_INT, JoinGamePacket::seaLevel,
            // Instruction de code
            BOOLEAN, JoinGamePacket::enforcesSecureChat,
            // Instruction de code
            JoinGamePacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
