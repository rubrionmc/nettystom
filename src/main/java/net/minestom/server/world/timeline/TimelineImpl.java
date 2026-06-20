// Déclaration du paquet de ce fichier
package net.minestom.server.world.timeline;

// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record TimelineImpl(
        // Annotation pour l'élément suivant
        @Nullable Integer periodTicks,
        // Instruction de code
        Map<EnvironmentAttribute<?>, Track<?, ?>> tracks
// Début d'une méthode/d'un bloc
) implements Timeline {

    // Début d'une méthode/d'un bloc
    public TimelineImpl {
        // Appelle une méthode
        tracks = Map.copyOf(tracks);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
