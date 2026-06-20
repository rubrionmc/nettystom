// Déclaration du paquet de ce fichier
package net.minestom.server.world.clock;

// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record ClockTimeMarkerImpl(RegistryKey<WorldClock> clock, int ticks, @Nullable Integer periodTicks,
                                  // Début d'une méthode/d'un bloc
                                  boolean showInCommands) implements ClockTimeMarker {
    // Début d'une méthode/d'un bloc
    public ClockTimeMarkerImpl {
        // Appelle une méthode
        Objects.requireNonNull(clock, "clock");
        // Appelle une méthode
        Check.argCondition(ticks < 0, "ticks must be positive");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
