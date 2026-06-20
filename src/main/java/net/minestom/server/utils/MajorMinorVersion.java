// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import org.jetbrains.annotations.NotNull;

// Déclaration de type (classe/interface/enum/record)
public record MajorMinorVersion(int major, int minor) {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @NotNull String toString() {
        // Renvoie une valeur à l'appelant
        return major + "." + minor;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
