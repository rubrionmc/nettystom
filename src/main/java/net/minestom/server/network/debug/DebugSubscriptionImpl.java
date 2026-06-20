// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record DebugSubscriptionImpl<T>(
        // Instruction de code
        int id,
        // Instruction de code
        Key key,
        // Instruction de code
        NetworkBuffer.Type<T> networkType
// Début d'une méthode/d'un bloc
) implements DebugSubscription<T>, NetworkBuffer.Type<T> {
    // Affecte une valeur
    static final Map<String, DebugSubscription<?>> NAMESPACES = new HashMap<>(32);
    // Appelle une méthode
    static final ObjectArray<DebugSubscription<?>> IDS = ObjectArray.singleThread(32);

    // Début d'une méthode/d'un bloc
    static {
        // Affecte une valeur
        var ignoredForInit = DebugSubscriptions.BEES;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void write(NetworkBuffer buffer, T value) {
        // Appelle une méthode
        networkType.write(buffer, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T read(NetworkBuffer buffer) {
        // Renvoie une valeur à l'appelant
        return networkType.read(buffer);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
