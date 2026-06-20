// Package declaration for this file
package net.minestom.server.listener.preplay;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.Auth;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientConfigurationAckPacket;
// Import of a required class
import net.minestom.server.network.packet.server.login.EncryptionRequestPacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.network.player.PlayerSocketConnection;
// Import of a required class
import net.minestom.server.network.plugin.LoginPlugin;
// Import of a required class
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;
// Import of a required class
import net.minestom.server.utils.mojang.MojangUtils;

// Import of a required class
import javax.crypto.SecretKey;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.math.BigInteger;
// Import of a required class
import java.net.InetAddress;
// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.net.UnknownHostException;
// Import of a required class
import java.security.KeyPair;
// Import of a required class
import java.security.SecureRandom;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public final class LoginListener {
    // Calls a method
    private static final SecureRandom NONCE_RANDOM = new SecureRandom();

    // Calls a method
    private static final Component ALREADY_CONNECTED = Component.text("You are already on this server", NamedTextColor.RED);
    // Calls a method
    private static final Component ERROR_DURING_LOGIN = Component.text("Error during login!", NamedTextColor.RED);
    // Calls a method
    private static final Component ERROR_MALFORMED_USERNAME = Component.text("Error malformed username", NamedTextColor.RED);
    // Calls a method
    private static final Component ENCRYPTION_FAILED = Component.text("Encryption failed!", NamedTextColor.RED);
    // Calls a method
    private static final Component ERROR_MOJANG_RESPONSE = Component.text("Failed to contact Mojang's Session Servers (Are they down?)", NamedTextColor.RED);

    // Calls a method
    public static final Component INVALID_PROXY_RESPONSE = Component.text("Invalid proxy response!", NamedTextColor.RED);

    // Start of a method/block
    public static void loginStartListener(ClientLoginStartPacket packet, PlayerConnection connection) {
        // Calls a method
        final Auth auth = MinecraftServer.process().auth();
        // Assigns a value
        final boolean isSocketConnection = connection instanceof PlayerSocketConnection;
        // Proxy support (only for socket clients) and cache the login username
        // Branch: checks a condition
        if (isSocketConnection) {
            // Calls a method
            PlayerSocketConnection socketConnection = (PlayerSocketConnection) connection;
            // Calls a method
            socketConnection.UNSAFE_setLoginUsername(packet.username());
            // Velocity support
            // Branch: checks a condition
            if (auth instanceof Auth.Velocity) {
                // Code statement
                connection.loginPluginMessageProcessor().request(Auth.Velocity.PLAYER_INFO_CHANNEL, new byte[0])
                        // Calls a method
                        .thenAccept(response -> handleVelocityProxyResponse(socketConnection, response));
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (auth instanceof Auth.Online(KeyPair keyPair) && isSocketConnection) {
            // Mojang auth
            // Branch: checks a condition
            if (MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(packet.username()) != null) {
                // Calls a method
                connection.kick(ALREADY_CONNECTED);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Calls a method
            final PlayerSocketConnection socketConnection = (PlayerSocketConnection) connection;

            // Calls a method
            final byte[] publicKey = keyPair.getPublic().getEncoded();
            // Assigns a value
            byte[] nonce = new byte[4];
            // Calls a method
            NONCE_RANDOM.nextBytes(nonce);
            // Calls a method
            socketConnection.setNonce(nonce);
            // Calls a method
            socketConnection.sendPacket(new EncryptionRequestPacket("", publicKey, nonce, true));
        // Alternative branch of the condition
        } else {
            // Offline
            // Code statement
            final GameProfile gameProfile;
            // Branch: checks a condition
            if (auth instanceof Auth.Bungee) {
                // LEGACY FORWARDING
                // Use game profile set during handshake
                // Code statement
                assert connection instanceof PlayerSocketConnection;
                // Calls a method
                final GameProfile bungeeProfile = ((PlayerSocketConnection) connection).gameProfile();
                // Code statement
                assert bungeeProfile != null;
                // Calls a method
                gameProfile = new GameProfile(bungeeProfile.uuid(), packet.username(), bungeeProfile.properties());
            // Alternative branch of the condition
            } else {
                // Calls a method
                gameProfile = new GameProfile(packet.profileId(), packet.username());
            // End of a block/expression
            }
            // Calls a method
            enterConfig(connection, gameProfile);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void loginEncryptionResponseListener(ClientEncryptionResponsePacket packet, PlayerConnection connection) {
        // Branch: checks a condition
        if (!(MinecraftServer.process().auth() instanceof Auth.Online(KeyPair keyPair))) {
            // Calls a method
            connection.kick(Component.text("Encryption is not supported in offline mode", NamedTextColor.RED));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Encryption is only support for socket connection¬
        // Branch: checks a condition
        if (!(connection instanceof PlayerSocketConnection socketConnection)) return;
        // Calls a method
        final String loginUsername = socketConnection.getLoginUsername();
        // Branch: checks a condition
        if (loginUsername == null || loginUsername.isEmpty()) {
            // Shouldn't happen, but in case
            // Calls a method
            connection.kick(ERROR_MALFORMED_USERNAME);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final boolean hasPublicKey = connection.playerPublicKey() != null;
        // Assigns a value
        final boolean verificationFailed = hasPublicKey || !Arrays.equals(socketConnection.getNonce(),
                // Calls a method
                MojangCrypt.decryptUsingKey(keyPair.getPrivate(), packet.encryptedVerifyToken()));

        // Branch: checks a condition
        if (verificationFailed) {
            // Calls a method
            MinecraftServer.LOGGER.error("Encryption failed for {}", loginUsername);
            // Calls a method
            connection.kick(ENCRYPTION_FAILED);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final SecretKey secretKey = MojangCrypt.decryptByteToSecretKey(keyPair.getPrivate(), packet.sharedSecret());
        // Calls a method
        final byte[] digestedData = MojangCrypt.digestData("", keyPair.getPublic(), secretKey);
        // Branch: checks a condition
        if (digestedData == null) {
            // Incorrect key, probably because of the client
            // Calls a method
            MinecraftServer.LOGGER.error("Connection {} failed initializing encryption.", socketConnection.getRemoteAddress());
            // Calls a method
            connection.kick(ENCRYPTION_FAILED);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Query Mojang's session server.
        // Calls a method
        final String serverId = new BigInteger(digestedData).toString(16);

        // Exception handling
        try {
            // Calls a method
            final JsonObject gameProfileJson = MojangUtils.authenticateSession(loginUsername, serverId, socketConnection.getRemoteAddress());

            // We have verified the session, parse response.
            // Calls a method
            socketConnection.setEncryptionKey(secretKey);
            // Assigns a value
            final UUID profileUUID = UUID.fromString(gameProfileJson.get("id").getAsString()
                    // Calls a method
                    .replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            // Calls a method
            final String profileName = gameProfileJson.get("name").getAsString();

            // Calls a method
            MinecraftServer.LOGGER.info("UUID of player {} is {}", profileName, profileUUID);
            // Calls a method
            List<GameProfile.Property> propertyList = new ArrayList<>();
            // Loop: repeats a block
            for (JsonElement element : gameProfileJson.get("properties").getAsJsonArray()) {
                // Calls a method
                JsonObject object = element.getAsJsonObject();
                // Calls a method
                propertyList.add(new GameProfile.Property(object.get("name").getAsString(), object.get("value").getAsString(), object.get("signature").getAsString()));
            // End of a block/expression
            }
            // Calls a method
            enterConfig(connection, new GameProfile(profileUUID, profileName, propertyList));
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            socketConnection.kick(ERROR_MOJANG_RESPONSE);
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // Start of a method/block
        } catch (Exception e) {
            // Calls a method
            socketConnection.kick(ERROR_DURING_LOGIN);
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void handleVelocityProxyResponse(PlayerSocketConnection socketConnection, LoginPlugin.Response response) {
        // Branch: checks a condition
        if (!(MinecraftServer.process().auth() instanceof Auth.Velocity velocity)) {
            // Calls a method
            socketConnection.kick(Component.text("Login plugin response is not supported in this auth mode", NamedTextColor.RED));
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final byte[] data = response.payload();
        // Assigns a value
        SocketAddress socketAddress = null;
        // Assigns a value
        GameProfile gameProfile = null;
        // Assigns a value
        boolean success = false;
        // Branch: checks a condition
        if (data != null && data.length > 0) {
            // Calls a method
            NetworkBuffer buffer = NetworkBuffer.wrap(data, 0, data.length);
            // Calls a method
            success = velocity.checkIntegrity(buffer);
            // Branch: checks a condition
            if (success) {
                // Get the real connection address
                // Code statement
                final InetAddress address;
                // Exception handling
                try {
                    // Calls a method
                    address = InetAddress.getByName(buffer.read(STRING));
                // Start of a method/block
                } catch (UnknownHostException e) {
                    // Calls a method
                    socketConnection.kick(INVALID_PROXY_RESPONSE);
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }
                // Calls a method
                final int port = ((java.net.InetSocketAddress) socketConnection.getRemoteAddress()).getPort();
                // Calls a method
                socketAddress = new InetSocketAddress(address, port);
                // Calls a method
                gameProfile = GameProfile.SERIALIZER.read(buffer);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (!success) {
            // Calls a method
            socketConnection.kick(INVALID_PROXY_RESPONSE);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        socketConnection.setRemoteAddress(socketAddress);
        // Calls a method
        enterConfig(socketConnection, gameProfile);
    // End of a block/expression
    }

    // Start of a method/block
    public static void loginPluginResponseListener(ClientLoginPluginResponsePacket packet, PlayerConnection connection) {
        // Exception handling
        try {
            // Calls a method
            LoginPluginMessageProcessor messageProcessor = connection.loginPluginMessageProcessor();
            // Calls a method
            messageProcessor.handleResponse(packet.messageId(), packet.data());
        // Start of a method/block
        } catch (Throwable t) {
            // Calls a method
            connection.kick(ERROR_DURING_LOGIN);
            // Calls a method
            MinecraftServer.LOGGER.error("Error handling Login Plugin Response", t);
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(t);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void loginAckListener(ClientLoginAcknowledgedPacket ignored, PlayerConnection connection) {
        // Branch: checks a condition
        if (!(connection instanceof PlayerSocketConnection socketConnection))
            // Throws an exception
            throw new UnsupportedOperationException("Only socket");
        // Calls a method
        final GameProfile gameProfile = socketConnection.gameProfile();
        // Code statement
        assert gameProfile != null;
        // Exception handling
        try {
            // Calls a method
            final Player player = MinecraftServer.getConnectionManager().createPlayer(connection, gameProfile);
            // Calls a method
            executeConfig(player, true);
        // Start of a method/block
        } catch (Throwable t) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(t);
            // Calls a method
            connection.kick(ERROR_DURING_LOGIN);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void configAckListener(ClientConfigurationAckPacket packet, Player player) {
        // Calls a method
        executeConfig(player, false);
    // End of a block/expression
    }

    // Start of a method/block
    public static void selectKnownPacks(ClientSelectKnownPacksPacket packet, Player player) {
        // Calls a method
        player.getPlayerConnection().receiveKnownPacksResponse(packet.entries());
    // End of a block/expression
    }

    // Start of a method/block
    public static void finishConfigListener(ClientFinishConfigurationPacket packet, Player player) {
        // Calls a method
        MinecraftServer.getConnectionManager().transitionConfigToPlay(player);
    // End of a block/expression
    }

    // Start of a method/block
    private static void enterConfig(PlayerConnection connection, GameProfile gameProfile) {
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // Exception handling
            try {
                // Calls a method
                var newGameProfile = MinecraftServer.getConnectionManager().transitionLoginToConfig(connection, gameProfile);
                // Branch: checks a condition
                if (connection instanceof PlayerSocketConnection socketConnection) {
                    // Calls a method
                    socketConnection.UNSAFE_setProfile(newGameProfile);
                // End of a block/expression
                }
            // Start of a method/block
            } catch (Throwable t) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(t);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    private static void executeConfig(Player player, boolean isFirstConfig) {
        // We have to create another thread (even though we should already be in a virtual thread)
        // because configuration handling involves waiting for the client to send a known packs packet.
        // Which mean that we have to free up the current thread to continue reading the socket.
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // Exception handling
            try {
                // Calls a method
                MinecraftServer.getConnectionManager().doConfiguration(player, isFirstConfig);
            // Start of a method/block
            } catch (Throwable t) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(t);
                // Calls a method
                player.kick(ERROR_DURING_LOGIN);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
