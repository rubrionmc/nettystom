// Déclaration du paquet de ce fichier
package net.minestom.server.utils.time;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.ChronoUnit;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;

// Déclaration de type (classe/interface/enum/record)
public final class Cooldown {
    // Instruction de code
    private final Duration duration;
    // Instruction de code
    private final TemporalUnit temporalUnit;
    // if this cooldown object as a lastUpdate set
    // Instruction de code
    private boolean hasLastUpdate;
    // Instruction de code
    private long lastUpdate;

    /**
     * Creates a cooldown with a measurement unit of {@link ChronoUnit#MILLIS}
     */
    // Début d'une méthode/d'un bloc
    public Cooldown(Duration duration) {
        // Appelle une méthode
        this(duration, ChronoUnit.MILLIS);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a cooldown with a given unit of measurement.
     * <p>
     * All calls to {@link #refreshLastUpdate(long)} and {@link #isReady(long)} must pass values in the given unit.
     *
     * @param duration     the duration of the cooldown
     * @param temporalUnit the unit of measurement
     */
    // Début d'une méthode/d'un bloc
    public Cooldown(Duration duration, TemporalUnit temporalUnit) {
        // Accès à l'objet courant/parent
        this.duration = duration;
        // Accès à l'objet courant/parent
        this.temporalUnit = temporalUnit;
        // Accès à l'objet courant/parent
        this.hasLastUpdate = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return the unit of measurement
     */
    // Début d'une méthode/d'un bloc
    public TemporalUnit getTemporalUnit() {
        // Renvoie une valeur à l'appelant
        return temporalUnit;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Duration getDuration() {
        // Renvoie une valeur à l'appelant
        return this.duration;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param lastUpdate the time of the last update, in nanos
     */
    // Début d'une méthode/d'un bloc
    public void refreshLastUpdate(long lastUpdate) {
        // Accès à l'objet courant/parent
        this.hasLastUpdate = true;
        // Accès à l'objet courant/parent
        this.lastUpdate = lastUpdate;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the cooldown is ready again
     *
     * @param time the time, in nanos
     */
    // Début d'une méthode/d'un bloc
    public boolean isReady(long time) {
        // Embranchement : vérifie une condition
        if (!hasLastUpdate) return true;
        // Renvoie une valeur à l'appelant
        return !hasCooldown(temporalUnit, time, lastUpdate, duration);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param currentTime  the current time in milliseconds
     * @param lastUpdate   the last update in milliseconds
     * @param cooldownUnit the time unit of the cooldown
     * @param cooldown     the value of the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean hasCooldown(long currentTime, long lastUpdate, TemporalUnit cooldownUnit, long cooldown) {
        // Renvoie une valeur à l'appelant
        return hasCooldown(currentTime, lastUpdate, Duration.of(cooldown, cooldownUnit));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param currentTime the current time in milliseconds
     * @param lastUpdate  the last update in milliseconds
     * @param duration    the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean hasCooldown(long currentTime, long lastUpdate, Duration duration) {
        // Renvoie une valeur à l'appelant
        return hasCooldown(ChronoUnit.MILLIS, currentTime, lastUpdate, duration);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param temporalUnit the {@link TemporalUnit} of {@code currentTime} and {@code lastUpdate}
     * @param currentTime  the current time in milliseconds
     * @param lastUpdate   the last update in milliseconds
     * @param cooldownUnit the time unit of the cooldown
     * @param cooldown     the value of the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean hasCooldown(TemporalUnit temporalUnit, long currentTime, long lastUpdate, TemporalUnit cooldownUnit, long cooldown) {
        // Renvoie une valeur à l'appelant
        return hasCooldown(temporalUnit, currentTime, lastUpdate, Duration.of(cooldown, cooldownUnit));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if something is in cooldown based on a {@code currentTime}.
     *
     * @param temporalUnit the {@link TemporalUnit} of {@code currentTime} and {@code lastUpdate}
     * @param currentTime  the current time in the given {@code temporalUnit}
     * @param lastUpdate   the last update in the given {@code temporalUnit}
     * @param duration     the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean hasCooldown(TemporalUnit temporalUnit, long currentTime, long lastUpdate, Duration duration) {
        // Renvoie une valeur à l'appelant
        return Duration.of(currentTime - lastUpdate, temporalUnit).compareTo(duration) < 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if something is in cooldown based on the current time ({@link System#nanoTime()}).
     *
     * @param lastUpdate   the last update in {@link System#nanoTime()}
     * @param temporalUnit the time unit of the cooldown
     * @param cooldown     the value of the cooldown
     * @return true if the cooldown is in progress, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean hasCooldown(long lastUpdate, TemporalUnit temporalUnit, int cooldown) {
        // Renvoie une valeur à l'appelant
        return hasCooldown(ChronoUnit.NANOS, System.nanoTime(), lastUpdate, temporalUnit, cooldown);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
