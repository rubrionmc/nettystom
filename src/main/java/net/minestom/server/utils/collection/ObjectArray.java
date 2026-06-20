// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.jetbrains.annotations.*;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents an array which will be resized to the highest required index.
 *
 * @param <T> the type of the array
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public sealed interface ObjectArray<T>
        // Début d'une méthode/d'un bloc
        permits ObjectArrayImpl.SingleThread, ObjectArrayImpl.Concurrent {
    // Début d'une méthode/d'un bloc
    static <T> ObjectArray<T> singleThread(int initialSize) {
        // Renvoie une valeur à l'appelant
        return new ObjectArrayImpl.SingleThread<>(initialSize);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> ObjectArray<T> singleThread() {
        // Renvoie une valeur à l'appelant
        return singleThread(0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> ObjectArray<T> concurrent(int initialSize) {
        // Renvoie une valeur à l'appelant
        return new ObjectArrayImpl.Concurrent<>(initialSize);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> ObjectArray<T> concurrent() {
        // Renvoie une valeur à l'appelant
        return concurrent(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @UnknownNullability T get(int index);

    // Appelle une méthode
    void set(int index, @Nullable T object);

    // Début d'une méthode/d'un bloc
    default void remove(int index) {
        // Appelle une méthode
        set(index, null);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    void trim();

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @UnknownNullability T [] arrayCopy(Class<T> type);

    /**
     * Copies the array into a list.
     * Requires all elements to be present and indexed from 0.
     *
     * @return List of the array elements
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @Unmodifiable List<T> toList();
// Fin d'un bloc/d'une expression
}
