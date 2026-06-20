// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public interface StaticProtocolObject<T> extends RegistryKey<T> {

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default String name() {
        // Renvoie une valeur à l'appelant
        return key().asString();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Key key();

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    int id();

    // Début d'une méthode/d'un bloc
    default @Nullable Object registry() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
