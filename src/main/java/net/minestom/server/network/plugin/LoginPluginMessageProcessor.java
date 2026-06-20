// Déclaration du paquet de ce fichier
package net.minestom.server.network.plugin;

// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.LoginPluginRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public class LoginPluginMessageProcessor {
    // Appelle une méthode
    private static final AtomicInteger REQUEST_ID = new AtomicInteger(0);

    // Affecte une valeur
    private final Map<Integer, LoginPlugin.Request> requestByMsgId = new ConcurrentHashMap<>();
    // Instruction de code
    private final PlayerConnection connection;

    // Début d'une méthode/d'un bloc
    public LoginPluginMessageProcessor(PlayerConnection connection) {
        // Accès à l'objet courant/parent
        this.connection = connection;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CompletableFuture<LoginPlugin.Response> request(String channel, byte [] requestPayload) {
        // Appelle une méthode
        LoginPlugin.Request request = new LoginPlugin.Request(channel, requestPayload);

        // Appelle une méthode
        final int messageId = nextMessageId();
        // Appelle une méthode
        requestByMsgId.put(messageId, request);
        // Appelle une méthode
        connection.sendPacket(new LoginPluginRequestPacket(messageId, request.channel(), request.payload()));

        // Renvoie une valeur à l'appelant
        return request.responseFuture();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void handleResponse(int messageId, byte[] responseData) throws Exception {
        // Appelle une méthode
        LoginPlugin.Request request = requestByMsgId.remove(messageId);
        // Embranchement : vérifie une condition
        if (request == null) {
            // Lève une exception
            throw new Exception("Received unexpected Login Plugin Response id " + messageId + " of " + responseData.length + " bytes");
        // Fin d'un bloc/d'une expression
        }

        // Gestion des exceptions
        try {
            // Appelle une méthode
            LoginPlugin.Response response = new LoginPlugin.Response(request.channel(), responseData);
            // Appelle une méthode
            request.responseFuture().complete(response);
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Lève une exception
            throw new Exception("Error handling Login Plugin Response on channel '" + request.channel() + "'", t);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void awaitReplies(long timeout, TimeUnit timeUnit) throws Exception {
        // Embranchement : vérifie une condition
        if (requestByMsgId.isEmpty()) {
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        CompletableFuture[] futures = requestByMsgId.values().stream()
                // Instruction de code
                .map(LoginPlugin.Request::responseFuture)
                // Appelle une méthode
                .toArray(CompletableFuture[]::new);
        // Appelle une méthode
        CompletableFuture.allOf(futures).get(timeout, timeUnit);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static int nextMessageId() {
        // Renvoie une valeur à l'appelant
        return REQUEST_ID.getAndIncrement();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
