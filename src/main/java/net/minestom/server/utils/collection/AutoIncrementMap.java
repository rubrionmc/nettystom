// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class AutoIncrementMap<K> {
    // Affecte une valeur
    private final Object2IntOpenHashMap<K> write = new Object2IntOpenHashMap<>();
    // Instruction de code
    private Object2IntOpenHashMap<K> read;
    // Instruction de code
    private int lastIndex;

    // Début d'une méthode/d'un bloc
    public AutoIncrementMap() {
        // Accès à l'objet courant/parent
        this.write.defaultReturnValue(-1);
        // Accès à l'objet courant/parent
        this.read = write.clone();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public int get(K key) {
        // Appelle une méthode
        int index = read.getInt(key);
        // Embranchement : vérifie une condition
        if (index == -1) {
            // Début d'une méthode/d'un bloc
            synchronized (write) {
                // Affecte une valeur
                var write = this.write;
                // Appelle une méthode
                index = write.getInt(key);
                // Embranchement : vérifie une condition
                if (index == -1) {
                    // Appelle une méthode
                    write.put(key, (index = lastIndex++));
                    // Appelle une méthode
                    read = write.clone();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return index;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
