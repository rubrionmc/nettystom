// Déclaration du paquet de ce fichier
package net.minestom.server.world.clock;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public sealed interface ClockTimeMarker extends ClockTimeMarkers permits ClockTimeMarkerImpl {
    // The default keys for ClockTimeMarker aren't a registry currently, just keys for a map.
    // Appelle une méthode
    Codec<RegistryKey<ClockTimeMarker>> CODEC = RegistryKey.uncheckedCodec();

    // Instruction de code
    static ClockTimeMarker create(RegistryKey<WorldClock> clock, int ticks, @Nullable Integer periodTicks,
                                  // Début d'une méthode/d'un bloc
                                  boolean showInCommands) {
        // Renvoie une valeur à l'appelant
        return new ClockTimeMarkerImpl(clock, ticks, periodTicks, showInCommands);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static RegistryKey<ClockTimeMarker> key(Key key) {
        // Renvoie une valeur à l'appelant
        return RegistryKey.unsafeOf(key);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    RegistryKey<WorldClock> clock();

    // Appelle une méthode
    int ticks();

    // Annotation pour l'élément suivant
    @Nullable Integer periodTicks();

    // Appelle une méthode
    boolean showInCommands();
// Fin d'un bloc/d'une expression
}
