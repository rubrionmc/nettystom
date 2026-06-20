// Déclaration du paquet de ce fichier
package net.minestom.server.network.plugin;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

// Déclaration de type (classe/interface/enum/record)
public final class LoginPlugin {
    // Déclaration de type (classe/interface/enum/record)
    public record Request(String channel, byte [] payload, CompletableFuture<Response> responseFuture) {
        // Début d'une méthode/d'un bloc
        public Request {
            // Appelle une méthode
            Objects.requireNonNull(channel);
            // Appelle une méthode
            Objects.requireNonNull(payload);
            // Appelle une méthode
            Objects.requireNonNull(responseFuture);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Request(String channel, byte [] requestPayload) {
            // Appelle une méthode
            this(channel, requestPayload, new CompletableFuture<>());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Response(String channel, byte @Nullable [] payload) {
        // Début d'une méthode/d'un bloc
        public Response {
            // Appelle une méthode
            Objects.requireNonNull(channel);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
