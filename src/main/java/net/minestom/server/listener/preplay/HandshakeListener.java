// Package declaration for this file
package net.minestom.server.listener.preplay;

// Import of a required class
import com.google.gson.JsonArray;
// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.google.gson.JsonParser;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.Auth;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.network.player.PlayerSocketConnection;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public final class HandshakeListener {

    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(HandshakeListener.class);

    /**
     * Text sent if a player tries to connect with an invalid version of the client
     */
    // Calls a method
    private static final Component INVALID_VERSION_TEXT = Component.text("Invalid Version, please use " + MinecraftServer.VERSION_NAME, NamedTextColor.RED);

    /**
     * Indicates that a BungeeGuard authentication was invalid due to missing, multiple, or invalid tokens.
     */
    // Calls a method
    private static final Component INVALID_BUNGEE_FORWARDING = Component.text("Invalid connection, please connect through the BungeeCord proxy. If you believe this is an error, contact a server administrator.", NamedTextColor.RED);

    /**
     * Text sent if a player was transferred to this server but the {@link ServerFlag#ACCEPT_TRANSFERS} server flag is not enabled.
     */
    // Calls a method
    private static final Component TRANSFERS_DISABLED_TEXT = Component.translatable("multiplayer.disconnect.transfers_disabled");

    // Start of a method/block
    private static int maxHandshakeLength() {
        // BungeeGuard limits handshake length to 2500 characters, while vanilla limits it to 255
        // Returns a value to the caller
        return MinecraftServer.process().auth() instanceof Auth.Bungee bungee ? (bungee.guard() ? 2500 : Short.MAX_VALUE) : 255;
    // End of a block/expression
    }

    // Start of a method/block
    public static void listener(ClientHandshakePacket packet, PlayerConnection connection) {
        // Calls a method
        String address = packet.serverAddress();
        // Branch: checks a condition
        if (address.length() > maxHandshakeLength()) {
            // Throws an exception
            throw new IllegalArgumentException("Server address too long: " + address.length());
        // End of a block/expression
        }

        // Multiple branching (switch/case)
        switch (packet.intent()) {
            // Multiple branching (switch/case)
            case TRANSFER:
                // Calls a method
                connection.markTransferred(true);

                // Branch: checks a condition
                if (!ServerFlag.ACCEPT_TRANSFERS) {
                    // Calls a method
                    connection.kick(TRANSFERS_DISABLED_TEXT);
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }
            // Multiple branching (switch/case)
            case LOGIN:
                // Branch: checks a condition
                if (packet.protocolVersion() != MinecraftServer.PROTOCOL_VERSION) {
                    // Incorrect client version
                    // Calls a method
                    connection.kick(INVALID_VERSION_TEXT);
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }

                // Calls a method
                final Auth auth = MinecraftServer.process().auth();

                // Bungee support (IP forwarding)
                // Branch: checks a condition
                if (auth instanceof Auth.Bungee bungee && connection instanceof PlayerSocketConnection socketConnection) {
                    // Calls a method
                    address = handleBungeeForwarding(address, socketConnection, bungee);
                // End of a block/expression
                }
            // Multiple branching (switch/case)
            default:
                // Breaks out of the loop/block
                break;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (connection instanceof PlayerSocketConnection socketConnection) {
            // Calls a method
            socketConnection.refreshServerInformation(address, packet.serverPort(), packet.protocolVersion());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private static String handleBungeeForwarding(String address,
                                                 // Code statement
                                                 PlayerSocketConnection socketConnection,
                                                 // Start of a method/block
                                                 Auth.Bungee bungee) {
        // Calls a method
        final String[] split = address.split("\00");

        // Branch: checks a condition
        if (split.length == 3 || split.length == 4) {
            // Assigns a value
            final boolean hasProperties = split.length == 4;
            // Branch: checks a condition
            if (bungee.guard() && !hasProperties) {
                // Calls a method
                bungeeDisconnect(socketConnection);
                // Returns a value to the caller
                return address;
            // End of a block/expression
            }

            // Assigns a value
            final String forwardedAddress = split[0];

            // Assigns a value
            final SocketAddress socketAddress = new InetSocketAddress(split[1],
                    // Calls a method
                    ((InetSocketAddress) socketConnection.getRemoteAddress()).getPort());
            // Calls a method
            socketConnection.setRemoteAddress(socketAddress);

            // Assigns a value
            final UUID playerUuid = UUID.fromString(
                    // Code statement
                    split[2]
                            // Code statement
                            .replaceFirst(
                                    // Code statement
                                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                            // End of a block/expression
                            )
            // End of a block/expression
            );

            // Calls a method
            List<GameProfile.Property> properties = new ArrayList<>();
            // Branch: checks a condition
            if (hasProperties) {
                // Assigns a value
                boolean foundBungeeGuardToken = false;
                // Assigns a value
                final String rawPropertyJson = split[3];
                // Calls a method
                final JsonArray propertyJson = JsonParser.parseString(rawPropertyJson).getAsJsonArray();
                // Loop: repeats a block
                for (JsonElement element : propertyJson) {
                    // Calls a method
                    final JsonObject jsonObject = element.getAsJsonObject();
                    // Calls a method
                    final JsonElement name = jsonObject.get("name");
                    // Calls a method
                    final JsonElement value = jsonObject.get("value");
                    // Calls a method
                    final JsonElement signature = jsonObject.get("signature");
                    // Branch: checks a condition
                    if (name == null || value == null) continue;

                    // Calls a method
                    final String nameString = name.getAsString();
                    // Calls a method
                    final String valueString = value.getAsString();
                    // Calls a method
                    final String signatureString = signature == null ? null : signature.getAsString();

                    // Branch: checks a condition
                    if (bungee.guard() && nameString.equals("bungeeguard-token")) {
                        // Branch: checks a condition
                        if (foundBungeeGuardToken || !bungee.validToken(valueString)) {
                            // Calls a method
                            bungeeDisconnect(socketConnection);
                            // Returns a value to the caller
                            return address;
                        // End of a block/expression
                        }

                        // Assigns a value
                        foundBungeeGuardToken = true;
                    // End of a block/expression
                    }

                    // Calls a method
                    properties.add(new GameProfile.Property(nameString, valueString, signatureString));
                // End of a block/expression
                }

                // Branch: checks a condition
                if (bungee.guard() && !foundBungeeGuardToken) {
                    // Calls a method
                    bungeeDisconnect(socketConnection);
                    // Returns a value to the caller
                    return address;
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Calls a method
            final GameProfile gameProfile = new GameProfile(playerUuid, "test", properties);
            // Calls a method
            socketConnection.UNSAFE_setProfile(gameProfile);
            // Returns a value to the caller
            return forwardedAddress;
        // End of a block/expression
        }

        // Calls a method
        bungeeDisconnect(socketConnection);
        // Returns a value to the caller
        return address;
    // End of a block/expression
    }

    // Start of a method/block
    private static void bungeeDisconnect(PlayerConnection connection) {
        // Calls a method
        LOGGER.warn("{} tried to log in without valid BungeeGuard forwarding information.", connection.getIdentifier());
        // Calls a method
        connection.kick(INVALID_BUNGEE_FORWARDING);
    // End of a block/expression
    }
// End of a block/expression
}
