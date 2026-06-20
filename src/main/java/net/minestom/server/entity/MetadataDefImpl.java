// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
final class MetadataDefImpl {
    // Appelle une méthode
    static final Map<String, Integer> MAX_INDEX = new HashMap<>();

    // Début d'une méthode/d'un bloc
    static <T extends @UnknownNullability Object> MetadataDef.Entry.Index<T> index(int index, Function<T, Metadata.Entry<T>> function, T defaultValue) {
        // Appelle une méthode
        final String caller = caller();
        // Appelle une méthode
        storeMaxIndex(caller, index);
        // Appelle une méthode
        final int superIndex = findSuperIndex(caller);
        // Renvoie une valeur à l'appelant
        return new MetadataDef.Entry.Index<>(superIndex + index, function, defaultValue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static MetadataDef.Entry.BitMask bitMask(int index, byte bitMask, boolean defaultValue) {
        // Appelle une méthode
        final String caller = caller();
        // Appelle une méthode
        storeMaxIndex(caller, index);
        // Appelle une méthode
        final int superIndex = findSuperIndex(caller);
        // Renvoie une valeur à l'appelant
        return new MetadataDef.Entry.BitMask(superIndex + index, bitMask, defaultValue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static MetadataDef.Entry.ByteMask byteMask(int index, byte byteMask, int offset, byte defaultValue) {
        // Appelle une méthode
        final String caller = caller();
        // Appelle une méthode
        storeMaxIndex(caller, index);
        // Appelle une méthode
        final int superIndex = findSuperIndex(caller);
        // Renvoie une valeur à l'appelant
        return new MetadataDef.Entry.ByteMask(superIndex + index, byteMask, offset, defaultValue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T extends MetadataDef> int count(Class<T> clazz) {
        // Appelle une méthode
        final String name = clazz.getName();
        // Gestion des exceptions
        try {
            // Force load the class to ensure entries are registered
            // Appelle une méthode
            Class.forName(name);
        // Début d'une méthode/d'un bloc
        } catch (ClassNotFoundException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final int classIndex = MAX_INDEX.get(name);
        // Appelle une méthode
        final int superIndex = findSuperIndex(name);
        // Renvoie une valeur à l'appelant
        return classIndex + superIndex + 1;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static String caller() {
        // Renvoie une valeur à l'appelant
        return Thread.currentThread().getStackTrace()[3].getClassName();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void storeMaxIndex(String caller, int index) {
        // Appelle une méthode
        final int currentMax = MAX_INDEX.getOrDefault(caller, 0);
        // Appelle une méthode
        MAX_INDEX.put(caller, Math.max(currentMax, index));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static int findSuperIndex(String caller) {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final Class<?> subclass = Class.forName(caller);
            // Appelle une méthode
            Class<?> superclass = subclass.getSuperclass();
            // Embranchement : vérifie une condition
            if (superclass == Object.class) return 0;

            // Affecte une valeur
            int index = 0;
            // Boucle : répète un bloc
            do {
                // Appelle une méthode
                index += MAX_INDEX.get(superclass.getName()) + 1;
                // Appelle une méthode
                superclass = superclass.getSuperclass();
            // Appelle une méthode
            } while (superclass != Object.class);

            // Renvoie une valeur à l'appelant
            return index;
        // Début d'une méthode/d'un bloc
        } catch (ClassNotFoundException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
