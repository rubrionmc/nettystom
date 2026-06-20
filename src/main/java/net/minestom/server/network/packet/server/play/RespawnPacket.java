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

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record RespawnPacket(
        // Instruction de code
        int dimensionType, String worldName,
        // Instruction de code
        long hashedSeed, GameMode gameMode, @Nullable GameMode previousGameMode,
        // Instruction de code
        boolean isDebug, boolean isFlat, @Nullable WorldPos deathLocation,
        // Instruction de code
        int portalCooldown, int seaLevel, byte copyData
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int COPY_NONE = 0x0;
    // Affecte une valeur
    public static final int COPY_ATTRIBUTES = 0x1;
    // Affecte une valeur
    public static final int COPY_METADATA = 0x2;
    // Affecte une valeur
    public static final int COPY_ALL = COPY_ATTRIBUTES | COPY_METADATA;

    // Affecte une valeur
    public static final NetworkBuffer.Type<RespawnPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, RespawnPacket::dimensionType,
            // Instruction de code
            STRING, RespawnPacket::worldName,
            // Instruction de code
            LONG, RespawnPacket::hashedSeed,
            // Instruction de code
            GameMode.NETWORK_TYPE, RespawnPacket::gameMode,
            // Instruction de code
            GameMode.OPT_NETWORK_TYPE, RespawnPacket::previousGameMode,
            // Instruction de code
            BOOLEAN, RespawnPacket::isDebug,
            // Instruction de code
            BOOLEAN, RespawnPacket::isFlat,
            // Instruction de code
            WorldPos.NETWORK_TYPE.optional(), RespawnPacket::deathLocation,
            // Instruction de code
            VAR_INT, RespawnPacket::portalCooldown,
            // Instruction de code
            VAR_INT, RespawnPacket::seaLevel,
            // Instruction de code
            BYTE, RespawnPacket::copyData,
            // Instruction de code
            RespawnPacket::new);

// Fin d'un bloc/d'une expression
}
