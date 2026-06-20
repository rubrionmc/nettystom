// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.ToIntFunction;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class ArrayUtils {

    // Début d'une méthode/d'un bloc
    private ArrayUtils() {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isEmpty(@Nullable Object [] array) {
        // Boucle : répète un bloc
        for (Object object : array) {
            // Embranchement : vérifie une condition
            if (object != null) return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <T> int[] mapToIntArray(Collection<T> collection, ToIntFunction<T> function) {
        // Appelle une méthode
        final int size = collection.size();
        // Embranchement : vérifie une condition
        if (size == 0)
            // Renvoie une valeur à l'appelant
            return new int[0];
        // Affecte une valeur
        int[] result = new int[size];
        // Affecte une valeur
        int i = 0;
        // Boucle : répète un bloc
        for (T object : collection) {
            // Appelle une méthode
            result[i++] = function.applyAsInt(object);
        // Fin d'un bloc/d'une expression
        }
        // Instruction de code
        assert i == size;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <K, V> @Unmodifiable Map<K, V> toMap(K[] keys, V[] values, int length) {
        // Instruction de code
        assert keys.length >= length && keys.length == values.length;
        // Renvoie une valeur à l'appelant
        return switch (length) {
            // Embranchement multiple (switch/case)
            case 0 -> Map.of();
            // Embranchement multiple (switch/case)
            case 1 -> Map.of(keys[0], values[0]);
            // Embranchement multiple (switch/case)
            case 2 -> Map.of(keys[0], values[0], keys[1], values[1]);
            // Embranchement multiple (switch/case)
            case 3 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2]);
            // Embranchement multiple (switch/case)
            case 4 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3]);
            // Embranchement multiple (switch/case)
            case 5 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3], keys[4], values[4]);
            // Embranchement multiple (switch/case)
            case 6 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3], keys[4], values[4], keys[5], values[5]);
            // Embranchement multiple (switch/case)
            case 7 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6]);
            // Embranchement multiple (switch/case)
            case 8 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6],
                    // Instruction de code
                    keys[7], values[7]);
            // Embranchement multiple (switch/case)
            case 9 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6],
                    // Instruction de code
                    keys[7], values[7], keys[8], values[8]);
            // Embranchement multiple (switch/case)
            case 10 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Instruction de code
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6],
                    // Instruction de code
                    keys[7], values[7], keys[8], values[8], keys[9], values[9]);
            // Embranchement multiple (switch/case)
            default -> Map.copyOf(new Object2ObjectArrayMap<>(keys, values, length));
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
