// Package declaration for this file
package net.minestom.server.network.plugin;

// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.concurrent.CompletableFuture;

// Type declaration (class/interface/enum/record)
public final class LoginPlugin {
    // Type declaration (class/interface/enum/record)
    public record Request(String channel, byte [] payload, CompletableFuture<Response> responseFuture) {
        // Start of a method/block
        public Request {
            // Calls a method
            Objects.requireNonNull(channel);
            // Calls a method
            Objects.requireNonNull(payload);
            // Calls a method
            Objects.requireNonNull(responseFuture);
        // End of a block/expression
        }

        // Start of a method/block
        public Request(String channel, byte [] requestPayload) {
            // Calls a method
            this(channel, requestPayload, new CompletableFuture<>());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Response(String channel, byte @Nullable [] payload) {
        // Start of a method/block
        public Response {
            // Calls a method
            Objects.requireNonNull(channel);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
