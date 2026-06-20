// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record JoinGamePacket(
        // Code statement
        int entityId, boolean isHardcore, List<String> worlds, int maxPlayers,
        // Code statement
        int viewDistance, int simulationDistance, boolean reducedDebugInfo, boolean enableRespawnScreen,
        // Code statement
        boolean doLimitedCrafting, int dimensionType,
        // Code statement
        String world, long hashedSeed, GameMode gameMode, @Nullable GameMode previousGameMode,
        // Code statement
        boolean isDebug, boolean isFlat, @Nullable WorldPos deathLocation, int portalCooldown,
        // Code statement
        int seaLevel, boolean enforcesSecureChat
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_WORLDS = Short.MAX_VALUE;

    // Start of a method/block
    public JoinGamePacket {
        // Calls a method
        worlds = List.copyOf(worlds);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<JoinGamePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, JoinGamePacket::entityId,
            // Code statement
            BOOLEAN, JoinGamePacket::isHardcore,
            // Code statement
            STRING.list(MAX_WORLDS), JoinGamePacket::worlds,
            // Code statement
            VAR_INT, JoinGamePacket::maxPlayers,
            // Code statement
            VAR_INT, JoinGamePacket::viewDistance,
            // Code statement
            VAR_INT, JoinGamePacket::simulationDistance,
            // Code statement
            BOOLEAN, JoinGamePacket::reducedDebugInfo,
            // Code statement
            BOOLEAN, JoinGamePacket::enableRespawnScreen,
            // Code statement
            BOOLEAN, JoinGamePacket::doLimitedCrafting,
            // Code statement
            VAR_INT, JoinGamePacket::dimensionType,
            // Code statement
            STRING, JoinGamePacket::world,
            // Code statement
            LONG, JoinGamePacket::hashedSeed,
            // Code statement
            GameMode.NETWORK_TYPE, JoinGamePacket::gameMode,
            // Code statement
            GameMode.OPT_NETWORK_TYPE, JoinGamePacket::previousGameMode,
            // Code statement
            BOOLEAN, JoinGamePacket::isDebug,
            // Code statement
            BOOLEAN, JoinGamePacket::isFlat,
            // Code statement
            WorldPos.NETWORK_TYPE.optional(), JoinGamePacket::deathLocation,
            // Code statement
            VAR_INT, JoinGamePacket::portalCooldown,
            // Code statement
            VAR_INT, JoinGamePacket::seaLevel,
            // Code statement
            BOOLEAN, JoinGamePacket::enforcesSecureChat,
            // Code statement
            JoinGamePacket::new
    // End of a block/expression
    );
// End of a block/expression
}
