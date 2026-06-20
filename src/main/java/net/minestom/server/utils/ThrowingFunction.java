// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface ThrowingFunction<I extends @UnknownNullability Object, O extends @UnknownNullability Object> {
    // Appelle une méthode
    O apply(I i) throws Exception;

    // Début d'une méthode/d'un bloc
    static <T> ThrowingFunction<T, T> identity() {
        // Renvoie une valeur à l'appelant
        return t -> t;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
