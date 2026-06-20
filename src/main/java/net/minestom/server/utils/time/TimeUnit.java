// Déclaration du paquet de ce fichier
package net.minestom.server.utils.time;

// Import d'une classe nécessaire
import java.time.temporal.ChronoUnit;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;

// Déclaration de type (classe/interface/enum/record)
public final class TimeUnit {
    // Affecte une valeur
    public static final TemporalUnit DAY = ChronoUnit.DAYS;
    // Affecte une valeur
    public static final TemporalUnit HOUR = ChronoUnit.HOURS;
    // Affecte une valeur
    public static final TemporalUnit MINUTE = ChronoUnit.MINUTES;
    // Affecte une valeur
    public static final TemporalUnit SECOND = ChronoUnit.SECONDS;
    // Affecte une valeur
    public static final TemporalUnit MILLISECOND = ChronoUnit.MILLIS;
    // Affecte une valeur
    public static final TemporalUnit SERVER_TICK = Tick.SERVER_TICKS;
    // Affecte une valeur
    public static final TemporalUnit CLIENT_TICK = Tick.CLIENT_TICKS;

    // Début d'une méthode/d'un bloc
    private TimeUnit() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
