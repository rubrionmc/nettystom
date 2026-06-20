// Package declaration for this file
package net.minestom.server.network.plugin;

// Import of a required class
import net.minestom.server.network.packet.server.login.LoginPluginRequestPacket;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public class LoginPluginMessageProcessor {
    // Calls a method
    private static final AtomicInteger REQUEST_ID = new AtomicInteger(0);

    // Calls a method
    private final Map<Integer, LoginPlugin.Request> requestByMsgId = new ConcurrentHashMap<>();
    // Code statement
    private final PlayerConnection connection;

    // Start of a method/block
    public LoginPluginMessageProcessor(PlayerConnection connection) {
        // Access to the current/parent object
        this.connection = connection;
    // End of a block/expression
    }

    // Start of a method/block
    public CompletableFuture<LoginPlugin.Response> request(String channel, byte [] requestPayload) {
        // Calls a method
        LoginPlugin.Request request = new LoginPlugin.Request(channel, requestPayload);

        // Calls a method
        final int messageId = nextMessageId();
        // Calls a method
        requestByMsgId.put(messageId, request);
        // Calls a method
        connection.sendPacket(new LoginPluginRequestPacket(messageId, request.channel(), request.payload()));

        // Returns a value to the caller
        return request.responseFuture();
    // End of a block/expression
    }

    // Start of a method/block
    public void handleResponse(int messageId, byte[] responseData) throws Exception {
        // Calls a method
        LoginPlugin.Request request = requestByMsgId.remove(messageId);
        // Branch: checks a condition
        if (request == null) {
            // Throws an exception
            throw new Exception("Received unexpected Login Plugin Response id " + messageId + " of " + responseData.length + " bytes");
        // End of a block/expression
        }

        // Exception handling
        try {
            // Calls a method
            LoginPlugin.Response response = new LoginPlugin.Response(request.channel(), responseData);
            // Calls a method
            request.responseFuture().complete(response);
        // Start of a method/block
        } catch (Throwable t) {
            // Throws an exception
            throw new Exception("Error handling Login Plugin Response on channel '" + request.channel() + "'", t);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void awaitReplies(long timeout, TimeUnit timeUnit) throws Exception {
        // Branch: checks a condition
        if (requestByMsgId.isEmpty()) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Assigns a value
        CompletableFuture[] futures = requestByMsgId.values().stream()
                // Code statement
                .map(LoginPlugin.Request::responseFuture)
                // Calls a method
                .toArray(CompletableFuture[]::new);
        // Calls a method
        CompletableFuture.allOf(futures).get(timeout, timeUnit);
    // End of a block/expression
    }

    // Start of a method/block
    private static int nextMessageId() {
        // Returns a value to the caller
        return REQUEST_ID.getAndIncrement();
    // End of a block/expression
    }
// End of a block/expression
}
