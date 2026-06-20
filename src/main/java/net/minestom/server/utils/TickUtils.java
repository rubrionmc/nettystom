// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.time.Duration;

/**
 * Tick related utilities.
 */
// Déclaration de type (classe/interface/enum/record)
public final class TickUtils {
    /**
     * Number of ticks per second for the default Java-edition client.
     */
    // Affecte une valeur
    public static final int CLIENT_TPS = 20;

    /**
     * Length of time per tick for the default Java-edition client.
     */
    // Affecte une valeur
    public static final int CLIENT_TICK_MS = 50;

    /**
     * Creates a number of ticks from a given duration, based on {@link MinecraftServer#TICK_MS}.
     *
     * @param duration the duration
     * @return the number of ticks
     * @throws IllegalArgumentException if duration is negative
     */
    // Début d'une méthode/d'un bloc
    public static int fromDuration(Duration duration) {
        // Renvoie une valeur à l'appelant
        return TickUtils.fromDuration(duration, MinecraftServer.TICK_MS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a number of ticks from a given duration.
     *
     * @param duration  the duration
     * @param msPerTick the number of milliseconds per tick
     * @return the number of ticks
     * @throws IllegalArgumentException if duration is negative
     */
    // Début d'une méthode/d'un bloc
    public static int fromDuration(Duration duration, int msPerTick) {
        // Appelle une méthode
        Check.argCondition(duration.isNegative(), "Duration cannot be negative");
        // Renvoie une valeur à l'appelant
        return (int) (duration.toMillis() / msPerTick);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
