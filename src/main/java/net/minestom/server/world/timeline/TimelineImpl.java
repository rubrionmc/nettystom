// Déclaration du paquet de ce fichier
package net.minestom.server.world.timeline;

// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.ClockTimeMarker;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record TimelineImpl(
        // Instruction de code
        RegistryKey<WorldClock> clock,
        // Annotation pour l'élément suivant
        @Nullable Integer periodTicks,
        // Instruction de code
        Map<EnvironmentAttribute<?>, Track<?, ?>> tracks,
        // Instruction de code
        Map<RegistryKey<ClockTimeMarker>, TimeMarkerInfo> timeMarkers
// Début d'une méthode/d'un bloc
) implements Timeline {

    // Début d'une méthode/d'un bloc
    public TimelineImpl {
        // Appelle une méthode
        Objects.requireNonNull(clock, "clock");
        // Appelle une méthode
        tracks = Map.copyOf(tracks);
        // Appelle une méthode
        timeMarkers = Map.copyOf(timeMarkers);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
