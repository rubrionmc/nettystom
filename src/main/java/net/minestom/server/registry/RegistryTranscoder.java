// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.codec.TranscoderProxy;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
public record RegistryTranscoder<D>(
        // Instruction de code
        Transcoder<D> transcoder,
        // Instruction de code
        Registries registries,
        // Instruction de code
        boolean forClient,
        // Instruction de code
        boolean init // True for initial load
// Début d'une méthode/d'un bloc
) implements TranscoderProxy<D> {

    // Début d'une méthode/d'un bloc
    public RegistryTranscoder(Transcoder<D> transcoder, Registries registries) {
        // Appelle une méthode
        this(Objects.requireNonNull(transcoder), registries, false, false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Transcoder<D> delegate() {
        // Renvoie une valeur à l'appelant
        return transcoder;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
