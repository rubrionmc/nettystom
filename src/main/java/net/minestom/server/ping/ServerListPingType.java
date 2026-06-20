// Déclaration du paquet de ce fichier
package net.minestom.server.ping;

// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerListPingEvent;
// Import d'une classe nécessaire
import net.minestom.server.extras.lan.OpenToLAN;

// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * An enum containing the different types of server list ping responses.
 *
 * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Server_List_Ping">the Minecraft Wiki</a>
 * @see ServerListPingEvent
 */
// Déclaration de type (classe/interface/enum/record)
public enum ServerListPingType {
    /**
     * The client is on version 1.16 or higher and supports full RGB with JSON text formatting.
     */
    // Instruction de code
    MODERN_FULL_RGB(data -> getModernPingResponse(data, true).toString()),

    /**
     * The client is on version 1.7 or higher and doesn't support full RGB but does support JSON text formatting.
     */
    // Instruction de code
    MODERN_NAMED_COLORS(data -> getModernPingResponse(data, false).toString()),

    /**
     * The client is on version 1.4 or higher and supports a description, the player count and the version information.
     */
    // Instruction de code
    LEGACY_VERSIONED(data -> getLegacyPingResponse(data, true)),

    /**
     * The client is on version 1.3.2 or lower and supports a description and the player count.
     */
    // Instruction de code
    LEGACY_UNVERSIONED(data -> getLegacyPingResponse(data, false)),

    /**
     * The ping that is sent when {@link OpenToLAN} is enabled and sending packets.
     * Only the description formatted as a legacy string is sent.
     * Ping events with this ping version are <b>not</b> cancellable.
     */
    // Appelle une méthode
    OPEN_TO_LAN(ServerListPingType::getOpenToLANPing);

    // Instruction de code
    private final Function<Status, String> pingResponseCreator;

    // Début d'une méthode/d'un bloc
    ServerListPingType(Function<Status, String> pingResponseCreator) {
        // Accès à l'objet courant/parent
        this.pingResponseCreator = pingResponseCreator;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the ping response for this version.
     *
     * @param status the response data
     * @return the response
     */
    // Début d'une méthode/d'un bloc
    public String getPingResponse(Status status) {
        // Renvoie une valeur à l'appelant
        return this.pingResponseCreator.apply(status);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    private static final String LAN_PING_FORMAT = "[MOTD]%s[/MOTD][AD]%s[/AD]";
    // Affecte une valeur
    private static final GsonComponentSerializer FULL_RGB = GsonComponentSerializer.gson(),
            // Appelle une méthode
            NAMED_RGB = GsonComponentSerializer.colorDownsamplingGson();
    // Appelle une méthode
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.legacySection();

    /**
     * Creates a ping sent when the server is sending {@link OpenToLAN} packets.
     *
     * @param status the response data
     * @return the ping
     * @see OpenToLAN
     */
    // Début d'une méthode/d'un bloc
    public static String getOpenToLANPing(Status status) {
        // Renvoie une valeur à l'appelant
        return String.format(LAN_PING_FORMAT, SECTION.serialize(status.description()), MinecraftServer.getServer().getPort());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a legacy ping response for client versions below the Netty rewrite (1.6-).
     *
     * @param status           the response data
     * @param supportsVersions if the client supports recieving the versions of the server
     * @return the response
     */
    // Début d'une méthode/d'un bloc
    public static String getLegacyPingResponse(Status status, boolean supportsVersions) {
        // Appelle une méthode
        final String motd = SECTION.serialize(status.description());
        // Appelle une méthode
        Status.PlayerInfo playerInfo = status.playerInfo();
        // Appelle une méthode
        int onlinePlayers = playerInfo == null ? 0 : playerInfo.onlinePlayers();
        // Appelle une méthode
        int maxPlayers = playerInfo == null ? 1 : playerInfo.maxPlayers();

        // Embranchement : vérifie une condition
        if (supportsVersions) {
            // Renvoie une valeur à l'appelant
            return String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                    // Instruction de code
                    status.versionInfo().protocolVersion(),
                    // Instruction de code
                    status.versionInfo().name(),
                    // Instruction de code
                    motd,
                    // Instruction de code
                    onlinePlayers,
                    // Instruction de code
                    maxPlayers);
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return String.format("%s§%d§%d", motd, onlinePlayers, maxPlayers);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a modern ping response for client versions above the Netty rewrite (1.7+).
     *
     * @param status          the response data
     * @param supportsFullRgb if the client supports full RGB colors in text components
     * @return the response
     */
    // Début d'une méthode/d'un bloc
    public static JsonObject getModernPingResponse(Status status, boolean supportsFullRgb) {
        // Appelle une méthode
        JsonObject element = (JsonObject) Status.CODEC.encode(Transcoder.JSON, status).orElseThrow();

        // reset description element with downscaled colors if this version does not support RGB
        // Embranchement : vérifie une condition
        if (!supportsFullRgb) {
            // Appelle une méthode
            GsonComponentSerializer serializer = GsonComponentSerializer.colorDownsamplingGson();
            // Appelle une méthode
            element.add("description", serializer.serializeToTree(status.description()));
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return element;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the server list ping version from the protocol version.
     * This only works for modern ping responses since the Netty rewrite.
     *
     * @param version the protocol version
     * @return the corresponding server list ping version
     */
    // Début d'une méthode/d'un bloc
    public static ServerListPingType fromModernProtocolVersion(int version) {
        // Renvoie une valeur à l'appelant
        return version >= 713 ? MODERN_FULL_RGB : MODERN_NAMED_COLORS;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
