// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.AsyncEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.network.plugin.LoginPlugin;
// Import d'une classe nécessaire
import net.minestom.server.network.plugin.LoginPluginMessageProcessor;

// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

/**
 * Called before the player initialization, it can be used to kick the player before any connection
 * or to change his final username/uuid.
 */
// Déclaration de type (classe/interface/enum/record)
public class AsyncPlayerPreLoginEvent implements AsyncEvent {

    // Instruction de code
    private final PlayerConnection connection;
    // Instruction de code
    private GameProfile gameProfile;
    // Instruction de code
    private final LoginPluginMessageProcessor pluginMessageProcessor;

    // Instruction de code
    public AsyncPlayerPreLoginEvent(PlayerConnection connection,
                                    // Instruction de code
                                    GameProfile gameProfile,
                                    // Début d'une méthode/d'un bloc
                                    LoginPluginMessageProcessor pluginMessageProcessor) {
        // Accès à l'objet courant/parent
        this.connection = connection;
        // Accès à l'objet courant/parent
        this.gameProfile = gameProfile;
        // Accès à l'objet courant/parent
        this.pluginMessageProcessor = pluginMessageProcessor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PlayerConnection getConnection() {
        // Renvoie une valeur à l'appelant
        return connection;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public GameProfile getGameProfile() {
        // Renvoie une valeur à l'appelant
        return gameProfile;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setGameProfile(GameProfile gameProfile) {
        // Accès à l'objet courant/parent
        this.gameProfile = gameProfile;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a login plugin message request. Can be useful to negotiate with modded clients or
     * proxies before moving on to the Configuration state.
     *
     * @param channel        the plugin message channel
     * @param requestPayload the contents of the plugin message, can be null for empty
     * @return a CompletableFuture for the response. The thread on which it completes is asynchronous.
     */
    // Début d'une méthode/d'un bloc
    public CompletableFuture<LoginPlugin.Response> sendPluginRequest(String channel, byte[] requestPayload) {
        // Renvoie une valeur à l'appelant
        return pluginMessageProcessor.request(channel, requestPayload);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public String getUsername() {
        // Renvoie une valeur à l'appelant
        return gameProfile.name();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setUsername(String username) {
        // Accès à l'objet courant/parent
        this.gameProfile = new GameProfile(gameProfile.uuid(), username, gameProfile.properties());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public UUID getPlayerUuid() {
        // Renvoie une valeur à l'appelant
        return gameProfile.uuid();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
