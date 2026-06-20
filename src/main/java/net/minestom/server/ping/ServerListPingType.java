// Package declaration for this file
package net.minestom.server.ping;

// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
// Import of a required class
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.event.server.ServerListPingEvent;
// Import of a required class
import net.minestom.server.extras.lan.OpenToLAN;

// Import of a required class
import java.util.function.Function;

/**
 * An enum containing the different types of server list ping responses.
 *
 * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Server_List_Ping">the Minecraft Wiki</a>
 * @see ServerListPingEvent
 */
// Type declaration (class/interface/enum/record)
public enum ServerListPingType {
    /**
     * The client is on version 1.16 or higher and supports full RGB with JSON text formatting.
     */
    // Code statement
    MODERN_FULL_RGB(data -> getModernPingResponse(data, true).toString()),

    /**
     * The client is on version 1.7 or higher and doesn't support full RGB but does support JSON text formatting.
     */
    // Code statement
    MODERN_NAMED_COLORS(data -> getModernPingResponse(data, false).toString()),

    /**
     * The client is on version 1.4 or higher and supports a description, the player count and the version information.
     */
    // Code statement
    LEGACY_VERSIONED(data -> getLegacyPingResponse(data, true)),

    /**
     * The client is on version 1.3.2 or lower and supports a description and the player count.
     */
    // Code statement
    LEGACY_UNVERSIONED(data -> getLegacyPingResponse(data, false)),

    /**
     * The ping that is sent when {@link OpenToLAN} is enabled and sending packets.
     * Only the description formatted as a legacy string is sent.
     * Ping events with this ping version are <b>not</b> cancellable.
     */
    // Calls a method
    OPEN_TO_LAN(ServerListPingType::getOpenToLANPing);

    // Code statement
    private final Function<Status, String> pingResponseCreator;

    // Start of a method/block
    ServerListPingType(Function<Status, String> pingResponseCreator) {
        // Access to the current/parent object
        this.pingResponseCreator = pingResponseCreator;
    // End of a block/expression
    }

    /**
     * Gets the ping response for this version.
     *
     * @param status the response data
     * @return the response
     */
    // Start of a method/block
    public String getPingResponse(Status status) {
        // Returns a value to the caller
        return this.pingResponseCreator.apply(status);
    // End of a block/expression
    }

    // Assigns a value
    private static final String LAN_PING_FORMAT = "[MOTD]%s[/MOTD][AD]%s[/AD]";
    // Assigns a value
    private static final GsonComponentSerializer FULL_RGB = GsonComponentSerializer.gson(),
            // Calls a method
            NAMED_RGB = GsonComponentSerializer.colorDownsamplingGson();
    // Calls a method
    private static final LegacyComponentSerializer SECTION = LegacyComponentSerializer.legacySection();

    /**
     * Creates a ping sent when the server is sending {@link OpenToLAN} packets.
     *
     * @param status the response data
     * @return the ping
     * @see OpenToLAN
     */
    // Start of a method/block
    public static String getOpenToLANPing(Status status) {
        // Returns a value to the caller
        return String.format(LAN_PING_FORMAT, SECTION.serialize(status.description()), MinecraftServer.getServer().getPort());
    // End of a block/expression
    }

    /**
     * Creates a legacy ping response for client versions below the Netty rewrite (1.6-).
     *
     * @param status           the response data
     * @param supportsVersions if the client supports recieving the versions of the server
     * @return the response
     */
    // Start of a method/block
    public static String getLegacyPingResponse(Status status, boolean supportsVersions) {
        // Calls a method
        final String motd = SECTION.serialize(status.description());
        // Calls a method
        Status.PlayerInfo playerInfo = status.playerInfo();
        // Calls a method
        int onlinePlayers = playerInfo == null ? 0 : playerInfo.onlinePlayers();
        // Calls a method
        int maxPlayers = playerInfo == null ? 1 : playerInfo.maxPlayers();

        // Branch: checks a condition
        if (supportsVersions) {
            // Returns a value to the caller
            return String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                    // Code statement
                    status.versionInfo().protocolVersion(),
                    // Code statement
                    status.versionInfo().name(),
                    // Code statement
                    motd,
                    // Code statement
                    onlinePlayers,
                    // Code statement
                    maxPlayers);
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return String.format("%s§%d§%d", motd, onlinePlayers, maxPlayers);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Creates a modern ping response for client versions above the Netty rewrite (1.7+).
     *
     * @param status          the response data
     * @param supportsFullRgb if the client supports full RGB colors in text components
     * @return the response
     */
    // Start of a method/block
    public static JsonObject getModernPingResponse(Status status, boolean supportsFullRgb) {
        // Calls a method
        JsonObject element = (JsonObject) Status.CODEC.encode(Transcoder.JSON, status).orElseThrow();

        // reset description element with downscaled colors if this version does not support RGB
        // Branch: checks a condition
        if (!supportsFullRgb) {
            // Calls a method
            GsonComponentSerializer serializer = GsonComponentSerializer.colorDownsamplingGson();
            // Calls a method
            element.add("description", serializer.serializeToTree(status.description()));
        // End of a block/expression
        }

        // Returns a value to the caller
        return element;
    // End of a block/expression
    }

    /**
     * Gets the server list ping version from the protocol version.
     * This only works for modern ping responses since the Netty rewrite.
     *
     * @param version the protocol version
     * @return the corresponding server list ping version
     */
    // Start of a method/block
    public static ServerListPingType fromModernProtocolVersion(int version) {
        // Returns a value to the caller
        return version >= 713 ? MODERN_FULL_RGB : MODERN_NAMED_COLORS;
    // End of a block/expression
    }
// End of a block/expression
}
