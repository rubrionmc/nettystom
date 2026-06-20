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

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record RespawnPacket(
        // Code statement
        int dimensionType, String worldName,
        // Code statement
        long hashedSeed, GameMode gameMode, @Nullable GameMode previousGameMode,
        // Code statement
        boolean isDebug, boolean isFlat, @Nullable WorldPos deathLocation,
        // Code statement
        int portalCooldown, int seaLevel, byte copyData
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final int COPY_NONE = 0x0;
    // Assigns a value
    public static final int COPY_ATTRIBUTES = 0x1;
    // Assigns a value
    public static final int COPY_METADATA = 0x2;
    // Assigns a value
    public static final int COPY_ALL = COPY_ATTRIBUTES | COPY_METADATA;

    // Assigns a value
    public static final NetworkBuffer.Type<RespawnPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, RespawnPacket::dimensionType,
            // Code statement
            STRING, RespawnPacket::worldName,
            // Code statement
            LONG, RespawnPacket::hashedSeed,
            // Code statement
            GameMode.NETWORK_TYPE, RespawnPacket::gameMode,
            // Code statement
            GameMode.OPT_NETWORK_TYPE, RespawnPacket::previousGameMode,
            // Code statement
            BOOLEAN, RespawnPacket::isDebug,
            // Code statement
            BOOLEAN, RespawnPacket::isFlat,
            // Code statement
            WorldPos.NETWORK_TYPE.optional(), RespawnPacket::deathLocation,
            // Code statement
            VAR_INT, RespawnPacket::portalCooldown,
            // Code statement
            VAR_INT, RespawnPacket::seaLevel,
            // Code statement
            BYTE, RespawnPacket::copyData,
            // Code statement
            RespawnPacket::new);

// End of a block/expression
}
