// Déclaration du paquet de ce fichier
package net.minestom.server.utils.async;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class AsyncUtils {
    // Appelle une méthode
    public static final CompletableFuture<Void> VOID_FUTURE = CompletableFuture.completedFuture(null);

    // Début d'une méthode/d'un bloc
    public static <T> CompletableFuture<T> empty() {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return (CompletableFuture<T>) VOID_FUTURE;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        // Renvoie une valeur à l'appelant
        return CompletableFuture.runAsync(() -> {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                runnable.run();
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
