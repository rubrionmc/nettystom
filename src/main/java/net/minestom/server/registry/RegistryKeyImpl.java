// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;

// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
record RegistryKeyImpl<T>(Key key) implements RegistryKey<T> {

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof RegistryKey<?> that)) return false;
        // Renvoie une valeur à l'appelant
        return Objects.equals(key, that.key());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hashCode(key);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
