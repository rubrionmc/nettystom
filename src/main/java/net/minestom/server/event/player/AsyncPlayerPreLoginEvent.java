// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.event.trait.AsyncEvent;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.network.plugin.LoginPlugin;
// Import of a required class
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;

// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.concurrent.CompletableFuture;

/**
 * Called before the player initialization, it can be used to kick the player before any connection
 * or to change his final username/uuid.
 */
// Type declaration (class/interface/enum/record)
public class AsyncPlayerPreLoginEvent implements AsyncEvent {

    // Code statement
    private final PlayerConnection connection;
    // Code statement
    private GameProfile gameProfile;
    // Code statement
    private final LoginPluginMessageProcessor pluginMessageProcessor;

    // Code statement
    public AsyncPlayerPreLoginEvent(PlayerConnection connection,
                                    // Code statement
                                    GameProfile gameProfile,
                                    // Start of a method/block
                                    LoginPluginMessageProcessor pluginMessageProcessor) {
        // Access to the current/parent object
        this.connection = connection;
        // Access to the current/parent object
        this.gameProfile = gameProfile;
        // Access to the current/parent object
        this.pluginMessageProcessor = pluginMessageProcessor;
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerConnection getConnection() {
        // Returns a value to the caller
        return connection;
    // End of a block/expression
    }

    // Start of a method/block
    public GameProfile getGameProfile() {
        // Returns a value to the caller
        return gameProfile;
    // End of a block/expression
    }

    // Start of a method/block
    public void setGameProfile(GameProfile gameProfile) {
        // Access to the current/parent object
        this.gameProfile = gameProfile;
    // End of a block/expression
    }

    /**
     * Sends a login plugin message request. Can be useful to negotiate with modded clients or
     * proxies before moving on to the Configuration state.
     *
     * @param channel        the plugin message channel
     * @param requestPayload the contents of the plugin message, can be null for empty
     * @return a CompletableFuture for the response. The thread on which it completes is asynchronous.
     */
    // Start of a method/block
    public CompletableFuture<LoginPlugin.Response> sendPluginRequest(String channel, byte[] requestPayload) {
        // Returns a value to the caller
        return pluginMessageProcessor.request(channel, requestPayload);
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public String getUsername() {
        // Returns a value to the caller
        return gameProfile.name();
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setUsername(String username) {
        // Access to the current/parent object
        this.gameProfile = new GameProfile(gameProfile.uuid(), username, gameProfile.properties());
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public UUID getPlayerUuid() {
        // Returns a value to the caller
        return gameProfile.uuid();
    // End of a block/expression
    }
// End of a block/expression
}
