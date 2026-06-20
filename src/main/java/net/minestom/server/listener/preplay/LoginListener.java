// Déclaration du paquet de ce fichier
package net.minestom.server.listener.preplay;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.Auth;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.extras.mojangAuth.MojangCrypt;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientConfigurationAckPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.EncryptionRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerSocketConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.plugin.LoginPlugin;
// Import d'une classe nécessaire
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;
// Import d'une classe nécessaire
import net.minestom.server.utils.mojang.MojangUtils;

// Import d'une classe nécessaire
import javax.crypto.SecretKey;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.math.BigInteger;
// Import d'une classe nécessaire
import java.net.InetAddress;
// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.net.UnknownHostException;
// Import d'une classe nécessaire
import java.security.KeyPair;
// Import d'une classe nécessaire
import java.security.SecureRandom;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public final class LoginListener {
    // Appelle une méthode
    private static final SecureRandom NONCE_RANDOM = new SecureRandom();

    // Appelle une méthode
    private static final Component ALREADY_CONNECTED = Component.text("You are already on this server", NamedTextColor.RED);
    // Appelle une méthode
    private static final Component ERROR_DURING_LOGIN = Component.text("Error during login!", NamedTextColor.RED);
    // Appelle une méthode
    private static final Component ERROR_MALFORMED_USERNAME = Component.text("Error malformed username", NamedTextColor.RED);
    // Appelle une méthode
    private static final Component ENCRYPTION_FAILED = Component.text("Encryption failed!", NamedTextColor.RED);
    // Appelle une méthode
    private static final Component ERROR_MOJANG_RESPONSE = Component.text("Failed to contact Mojang's Session Servers (Are they down?)", NamedTextColor.RED);

    // Appelle une méthode
    public static final Component INVALID_PROXY_RESPONSE = Component.text("Invalid proxy response!", NamedTextColor.RED);

    // Début d'une méthode/d'un bloc
    public static void loginStartListener(ClientLoginStartPacket packet, PlayerConnection connection) {
        // Appelle une méthode
        final Auth auth = MinecraftServer.process().auth();
        // Affecte une valeur
        final boolean isSocketConnection = connection instanceof PlayerSocketConnection;
        // Proxy support (only for socket clients) and cache the login username
        // Embranchement : vérifie une condition
        if (isSocketConnection) {
            // Appelle une méthode
            PlayerSocketConnection socketConnection = (PlayerSocketConnection) connection;
            // Appelle une méthode
            socketConnection.UNSAFE_setLoginUsername(packet.username());
            // Velocity support
            // Embranchement : vérifie une condition
            if (auth instanceof Auth.Velocity) {
                // Instruction de code
                connection.loginPluginMessageProcessor().request(Auth.Velocity.PLAYER_INFO_CHANNEL, new byte[0])
                        // Appelle une méthode
                        .thenAccept(response -> handleVelocityProxyResponse(socketConnection, response));
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (auth instanceof Auth.Online(KeyPair keyPair) && isSocketConnection) {
            // Mojang auth
            // Embranchement : vérifie une condition
            if (MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(packet.username()) != null) {
                // Appelle une méthode
                connection.kick(ALREADY_CONNECTED);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            final PlayerSocketConnection socketConnection = (PlayerSocketConnection) connection;

            // Appelle une méthode
            final byte[] publicKey = keyPair.getPublic().getEncoded();
            // Affecte une valeur
            byte[] nonce = new byte[4];
            // Appelle une méthode
            NONCE_RANDOM.nextBytes(nonce);
            // Appelle une méthode
            socketConnection.setNonce(nonce);
            // Appelle une méthode
            socketConnection.sendPacket(new EncryptionRequestPacket("", publicKey, nonce, true));
        // Branche alternative de la condition
        } else {
            // Offline
            // Instruction de code
            final GameProfile gameProfile;
            // Embranchement : vérifie une condition
            if (auth instanceof Auth.Bungee) {
                // LEGACY FORWARDING
                // Use game profile set during handshake
                // Instruction de code
                assert connection instanceof PlayerSocketConnection;
                // Appelle une méthode
                final GameProfile bungeeProfile = ((PlayerSocketConnection) connection).gameProfile();
                // Instruction de code
                assert bungeeProfile != null;
                // Appelle une méthode
                gameProfile = new GameProfile(bungeeProfile.uuid(), packet.username(), bungeeProfile.properties());
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                gameProfile = new GameProfile(packet.profileId(), packet.username());
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            enterConfig(connection, gameProfile);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void loginEncryptionResponseListener(ClientEncryptionResponsePacket packet, PlayerConnection connection) {
        // Embranchement : vérifie une condition
        if (!(MinecraftServer.process().auth() instanceof Auth.Online(KeyPair keyPair))) {
            // Appelle une méthode
            connection.kick(Component.text("Encryption is not supported in offline mode", NamedTextColor.RED));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Encryption is only support for socket connection¬
        // Embranchement : vérifie une condition
        if (!(connection instanceof PlayerSocketConnection socketConnection)) return;
        // Appelle une méthode
        final String loginUsername = socketConnection.getLoginUsername();
        // Embranchement : vérifie une condition
        if (loginUsername == null || loginUsername.isEmpty()) {
            // Shouldn't happen, but in case
            // Appelle une méthode
            connection.kick(ERROR_MALFORMED_USERNAME);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final boolean hasPublicKey = connection.playerPublicKey() != null;
        // Affecte une valeur
        final boolean verificationFailed = hasPublicKey || !Arrays.equals(socketConnection.getNonce(),
                // Appelle une méthode
                MojangCrypt.decryptUsingKey(keyPair.getPrivate(), packet.encryptedVerifyToken()));

        // Embranchement : vérifie une condition
        if (verificationFailed) {
            // Appelle une méthode
            MinecraftServer.LOGGER.error("Encryption failed for {}", loginUsername);
            // Appelle une méthode
            connection.kick(ENCRYPTION_FAILED);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final SecretKey secretKey = MojangCrypt.decryptByteToSecretKey(keyPair.getPrivate(), packet.sharedSecret());
        // Appelle une méthode
        final byte[] digestedData = MojangCrypt.digestData("", keyPair.getPublic(), secretKey);
        // Embranchement : vérifie une condition
        if (digestedData == null) {
            // Incorrect key, probably because of the client
            // Appelle une méthode
            MinecraftServer.LOGGER.error("Connection {} failed initializing encryption.", socketConnection.getRemoteAddress());
            // Appelle une méthode
            connection.kick(ENCRYPTION_FAILED);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Query Mojang's session server.
        // Appelle une méthode
        final String serverId = new BigInteger(digestedData).toString(16);

        // Gestion des exceptions
        try {
            // Appelle une méthode
            final JsonObject gameProfileJson = MojangUtils.authenticateSession(loginUsername, serverId, socketConnection.getRemoteAddress());

            // We have verified the session, parse response.
            // Appelle une méthode
            socketConnection.setEncryptionKey(secretKey);
            // Affecte une valeur
            final UUID profileUUID = UUID.fromString(gameProfileJson.get("id").getAsString()
                    // Appelle une méthode
                    .replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            // Appelle une méthode
            final String profileName = gameProfileJson.get("name").getAsString();

            // Appelle une méthode
            MinecraftServer.LOGGER.info("UUID of player {} is {}", profileName, profileUUID);
            // Appelle une méthode
            List<GameProfile.Property> propertyList = new ArrayList<>();
            // Boucle : répète un bloc
            for (JsonElement element : gameProfileJson.get("properties").getAsJsonArray()) {
                // Appelle une méthode
                JsonObject object = element.getAsJsonObject();
                // Appelle une méthode
                propertyList.add(new GameProfile.Property(object.get("name").getAsString(), object.get("value").getAsString(), object.get("signature").getAsString()));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            enterConfig(connection, new GameProfile(profileUUID, profileName, propertyList));
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            socketConnection.kick(ERROR_MOJANG_RESPONSE);
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Appelle une méthode
            socketConnection.kick(ERROR_DURING_LOGIN);
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void handleVelocityProxyResponse(PlayerSocketConnection socketConnection, LoginPlugin.Response response) {
        // Embranchement : vérifie une condition
        if (!(MinecraftServer.process().auth() instanceof Auth.Velocity velocity)) {
            // Appelle une méthode
            socketConnection.kick(Component.text("Login plugin response is not supported in this auth mode", NamedTextColor.RED));
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final byte[] data = response.payload();
        // Affecte une valeur
        SocketAddress socketAddress = null;
        // Affecte une valeur
        GameProfile gameProfile = null;
        // Affecte une valeur
        boolean success = false;
        // Embranchement : vérifie une condition
        if (data != null && data.length > 0) {
            // Appelle une méthode
            NetworkBuffer buffer = NetworkBuffer.wrap(data, 0, data.length);
            // Appelle une méthode
            success = velocity.checkIntegrity(buffer);
            // Embranchement : vérifie une condition
            if (success) {
                // Get the real connection address
                // Instruction de code
                final InetAddress address;
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    address = InetAddress.getByName(buffer.read(STRING));
                // Début d'une méthode/d'un bloc
                } catch (UnknownHostException e) {
                    // Appelle une méthode
                    socketConnection.kick(INVALID_PROXY_RESPONSE);
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                final int port = ((java.net.InetSocketAddress) socketConnection.getRemoteAddress()).getPort();
                // Appelle une méthode
                socketAddress = new InetSocketAddress(address, port);
                // Appelle une méthode
                gameProfile = GameProfile.SERIALIZER.read(buffer);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (!success) {
            // Appelle une méthode
            socketConnection.kick(INVALID_PROXY_RESPONSE);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        socketConnection.setRemoteAddress(socketAddress);
        // Appelle une méthode
        enterConfig(socketConnection, gameProfile);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void loginPluginResponseListener(ClientLoginPluginResponsePacket packet, PlayerConnection connection) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            LoginPluginMessageProcessor messageProcessor = connection.loginPluginMessageProcessor();
            // Appelle une méthode
            messageProcessor.handleResponse(packet.messageId(), packet.data());
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Appelle une méthode
            connection.kick(ERROR_DURING_LOGIN);
            // Appelle une méthode
            MinecraftServer.LOGGER.error("Error handling Login Plugin Response", t);
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(t);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void loginAckListener(ClientLoginAcknowledgedPacket ignored, PlayerConnection connection) {
        // Embranchement : vérifie une condition
        if (!(connection instanceof PlayerSocketConnection socketConnection))
            // Lève une exception
            throw new UnsupportedOperationException("Only socket");
        // Appelle une méthode
        final GameProfile gameProfile = socketConnection.gameProfile();
        // Instruction de code
        assert gameProfile != null;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final Player player = MinecraftServer.getConnectionManager().createPlayer(connection, gameProfile);
            // Appelle une méthode
            executeConfig(player, true);
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(t);
            // Appelle une méthode
            connection.kick(ERROR_DURING_LOGIN);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void configAckListener(ClientConfigurationAckPacket packet, Player player) {
        // Appelle une méthode
        executeConfig(player, false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void selectKnownPacks(ClientSelectKnownPacksPacket packet, Player player) {
        // Appelle une méthode
        player.getPlayerConnection().receiveKnownPacksResponse(packet.entries());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void finishConfigListener(ClientFinishConfigurationPacket packet, Player player) {
        // Appelle une méthode
        MinecraftServer.getConnectionManager().transitionConfigToPlay(player);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void enterConfig(PlayerConnection connection, GameProfile gameProfile) {
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                var newGameProfile = MinecraftServer.getConnectionManager().transitionLoginToConfig(connection, gameProfile);
                // Embranchement : vérifie une condition
                if (connection instanceof PlayerSocketConnection socketConnection) {
                    // Appelle une méthode
                    socketConnection.UNSAFE_setProfile(newGameProfile);
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } catch (Throwable t) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(t);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void executeConfig(Player player, boolean isFirstConfig) {
        // We have to create another thread (even though we should already be in a virtual thread)
        // because configuration handling involves waiting for the client to send a known packs packet.
        // Which mean that we have to free up the current thread to continue reading the socket.
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                MinecraftServer.getConnectionManager().doConfiguration(player, isFirstConfig);
            // Début d'une méthode/d'un bloc
            } catch (Throwable t) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(t);
                // Appelle une méthode
                player.kick(ERROR_DURING_LOGIN);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
