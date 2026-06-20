// Déclaration du paquet de ce fichier
package net.minestom.server.listener.preplay;

// Import d'une classe nécessaire
import com.google.gson.JsonArray;
// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import com.google.gson.JsonParser;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.Auth;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerSocketConnection;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public final class HandshakeListener {

    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(HandshakeListener.class);

    /**
     * Text sent if a player tries to connect with an invalid version of the client
     */
    // Appelle une méthode
    private static final Component INVALID_VERSION_TEXT = Component.text("Invalid Version, please use " + MinecraftServer.VERSION_NAME, NamedTextColor.RED);

    /**
     * Indicates that a BungeeGuard authentication was invalid due to missing, multiple, or invalid tokens.
     */
    // Appelle une méthode
    private static final Component INVALID_BUNGEE_FORWARDING = Component.text("Invalid connection, please connect through the BungeeCord proxy. If you believe this is an error, contact a server administrator.", NamedTextColor.RED);

    /**
     * Text sent if a player was transferred to this server but the {@link ServerFlag#ACCEPT_TRANSFERS} server flag is not enabled.
     */
    // Appelle une méthode
    private static final Component TRANSFERS_DISABLED_TEXT = Component.translatable("multiplayer.disconnect.transfers_disabled");

    // Début d'une méthode/d'un bloc
    public static void listener(ClientHandshakePacket packet, PlayerConnection connection) {
        // Appelle une méthode
        String address = packet.serverAddress();
        // Embranchement multiple (switch/case)
        switch (packet.intent()) {
            // Embranchement multiple (switch/case)
            case TRANSFER:
                // Appelle une méthode
                connection.markTransferred(true);

                // Embranchement : vérifie une condition
                if (!ServerFlag.ACCEPT_TRANSFERS) {
                    // Appelle une méthode
                    connection.kick(TRANSFERS_DISABLED_TEXT);
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }
            // Embranchement multiple (switch/case)
            case LOGIN:
                // Embranchement : vérifie une condition
                if (packet.protocolVersion() != MinecraftServer.PROTOCOL_VERSION) {
                    // Incorrect client version
                    // Appelle une méthode
                    connection.kick(INVALID_VERSION_TEXT);
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                final Auth auth = MinecraftServer.process().auth();

                // Bungee support (IP forwarding)
                // Embranchement : vérifie une condition
                if (auth instanceof Auth.Bungee bungee && connection instanceof PlayerSocketConnection socketConnection) {
                    // Appelle une méthode
                    address = handleBungeeForwarding(address, socketConnection, bungee);
                // Fin d'un bloc/d'une expression
                }
            // Embranchement multiple (switch/case)
            default:
                // Interrompt la boucle/le bloc
                break;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (connection instanceof PlayerSocketConnection socketConnection) {
            // Appelle une méthode
            socketConnection.refreshServerInformation(address, packet.serverPort(), packet.protocolVersion());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static String handleBungeeForwarding(String address,
                                                 // Instruction de code
                                                 PlayerSocketConnection socketConnection,
                                                 // Début d'une méthode/d'un bloc
                                                 Auth.Bungee bungee) {
        // Appelle une méthode
        final String[] split = address.split("\00");

        // Embranchement : vérifie une condition
        if (split.length == 3 || split.length == 4) {
            // Instruction de code
            final boolean hasProperties = split.length == 4;
            // Embranchement : vérifie une condition
            if (bungee.guard() && !hasProperties) {
                // Appelle une méthode
                bungeeDisconnect(socketConnection);
                // Renvoie une valeur à l'appelant
                return address;
            // Fin d'un bloc/d'une expression
            }

            // Affecte une valeur
            final String forwardedAddress = split[0];

            // Affecte une valeur
            final SocketAddress socketAddress = new InetSocketAddress(split[1],
                    // Appelle une méthode
                    ((InetSocketAddress) socketConnection.getRemoteAddress()).getPort());
            // Appelle une méthode
            socketConnection.setRemoteAddress(socketAddress);

            // Affecte une valeur
            final UUID playerUuid = UUID.fromString(
                    // Instruction de code
                    split[2]
                            // Instruction de code
                            .replaceFirst(
                                    // Instruction de code
                                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                            // Fin d'un bloc/d'une expression
                            )
            // Fin d'un bloc/d'une expression
            );

            // Affecte une valeur
            List<GameProfile.Property> properties = new ArrayList<>();
            // Embranchement : vérifie une condition
            if (hasProperties) {
                // Affecte une valeur
                boolean foundBungeeGuardToken = false;
                // Affecte une valeur
                final String rawPropertyJson = split[3];
                // Appelle une méthode
                final JsonArray propertyJson = JsonParser.parseString(rawPropertyJson).getAsJsonArray();
                // Boucle : répète un bloc
                for (JsonElement element : propertyJson) {
                    // Appelle une méthode
                    final JsonObject jsonObject = element.getAsJsonObject();
                    // Appelle une méthode
                    final JsonElement name = jsonObject.get("name");
                    // Appelle une méthode
                    final JsonElement value = jsonObject.get("value");
                    // Appelle une méthode
                    final JsonElement signature = jsonObject.get("signature");
                    // Embranchement : vérifie une condition
                    if (name == null || value == null) continue;

                    // Appelle une méthode
                    final String nameString = name.getAsString();
                    // Appelle une méthode
                    final String valueString = value.getAsString();
                    // Appelle une méthode
                    final String signatureString = signature == null ? null : signature.getAsString();

                    // Embranchement : vérifie une condition
                    if (bungee.guard() && nameString.equals("bungeeguard-token")) {
                        // Embranchement : vérifie une condition
                        if (foundBungeeGuardToken || !bungee.validToken(valueString)) {
                            // Appelle une méthode
                            bungeeDisconnect(socketConnection);
                            // Renvoie une valeur à l'appelant
                            return address;
                        // Fin d'un bloc/d'une expression
                        }

                        // Affecte une valeur
                        foundBungeeGuardToken = true;
                    // Fin d'un bloc/d'une expression
                    }

                    // Appelle une méthode
                    properties.add(new GameProfile.Property(nameString, valueString, signatureString));
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (bungee.guard() && !foundBungeeGuardToken) {
                    // Appelle une méthode
                    bungeeDisconnect(socketConnection);
                    // Renvoie une valeur à l'appelant
                    return address;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final GameProfile gameProfile = new GameProfile(playerUuid, "test", properties);
            // Appelle une méthode
            socketConnection.UNSAFE_setProfile(gameProfile);
            // Renvoie une valeur à l'appelant
            return forwardedAddress;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        bungeeDisconnect(socketConnection);
        // Renvoie une valeur à l'appelant
        return address;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void bungeeDisconnect(PlayerConnection connection) {
        // Appelle une méthode
        LOGGER.warn("{} tried to log in without valid BungeeGuard forwarding information.", connection.getIdentifier());
        // Appelle une méthode
        connection.kick(INVALID_BUNGEE_FORWARDING);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
