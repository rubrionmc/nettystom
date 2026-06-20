// Déclaration du paquet de ce fichier
package net.minestom.server.utils.time;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.Temporal;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;

/**
 * A TemporalUnit that represents one tick.
 */
// Déclaration de type (classe/interface/enum/record)
public final class Tick implements TemporalUnit {
    /**
     * A TemporalUnit representing the server tick. This is defined using
     * {@link MinecraftServer#TICK_MS}.
     */
    // Appelle une méthode
    public static Tick SERVER_TICKS = new Tick(MinecraftServer.TICK_MS);

    /**
     * A TemporalUnit representing the client tick. This is always equal to 50ms.
     */
    // Appelle une méthode
    public static Tick CLIENT_TICKS = new Tick(50);

    // Instruction de code
    private final long milliseconds;
    // Instruction de code
    private final int tps;

    /**
     * Creates a new tick.
     *
     * @param length the length of the tick in milliseconds
     */
    // Début d'une méthode/d'un bloc
    private Tick(long length) {
        // Embranchement : vérifie une condition
        if (length <= 0) {
            // Lève une exception
            throw new IllegalArgumentException("length cannot be negative");
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.milliseconds = length;
        // Accès à l'objet courant/parent
        this.tps = Math.toIntExact(Duration.ofSeconds(1).dividedBy(Duration.ofMillis(this.milliseconds)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a duration from an amount of ticks.
     *
     * @param ticks the amount of ticks
     * @return the duration
     */
    // Début d'une méthode/d'un bloc
    public static Duration server(long ticks) {
        // Renvoie une valeur à l'appelant
        return Duration.of(ticks, SERVER_TICKS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a duration from an amount of client-side ticks.
     *
     * @param ticks the amount of ticks
     * @return the duration
     */
    // Début d'une méthode/d'un bloc
    public static Duration client(long ticks) {
        // Renvoie une valeur à l'appelant
        return Duration.of(ticks, CLIENT_TICKS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the number of whole ticks that occur in the provided duration. Note that this
     * method returns an {@code int} as this is the unit that Minecraft stores ticks in.
     *
     * @param duration the duration
     * @return the number of whole ticks in this duration
     * @throws ArithmeticException if the duration is zero or an overflow occurs
     */
    // Début d'une méthode/d'un bloc
    public int fromDuration(Duration duration) {
        // Renvoie une valeur à l'appelant
        return Math.toIntExact(duration.dividedBy(this.getDuration()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the whole number of these ticks that occur in one second.
     *
     * @return the number
     */
    // Début d'une méthode/d'un bloc
    public int getTicksPerSecond() {
        // Renvoie une valeur à l'appelant
        return this.tps;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Duration getDuration() {
        // Renvoie une valeur à l'appelant
        return Duration.ofMillis(this.milliseconds);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isDurationEstimated() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isDateBased() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isTimeBased() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked") // following ChronoUnit#addTo
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <R extends Temporal> R addTo(R temporal, long amount) {
        // Renvoie une valeur à l'appelant
        return (R) temporal.plus(amount, this);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public long between(Temporal start, Temporal end) {
        // Renvoie une valeur à l'appelant
        return start.until(end, this);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}