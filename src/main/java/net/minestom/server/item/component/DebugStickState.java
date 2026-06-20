// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public record DebugStickState(Map<String, String> state) {
    // Appelle une méthode
    public static final DebugStickState EMPTY = new DebugStickState(Map.of());

    // Affecte une valeur
    public static final Codec<DebugStickState> CODEC = Codec.STRING.mapValue(Codec.STRING)
            // Appelle une méthode
            .transform(DebugStickState::new, DebugStickState::state);
    // Appelle une méthode
    public static final NetworkBuffer.Type<DebugStickState> NETWORK_TYPE = NetworkBuffer.TypedNBT(CODEC);

    // Début d'une méthode/d'un bloc
    public DebugStickState {
        // Appelle une méthode
        state = Map.copyOf(state);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public DebugStickState set(String key, String value) {
        // Appelle une méthode
        Map<String, String> newState = new HashMap<>(state);
        // Appelle une méthode
        newState.put(key, value);
        // Renvoie une valeur à l'appelant
        return new DebugStickState(newState);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public DebugStickState remove(String key) {
        // Appelle une méthode
        Map<String, String> newState = new HashMap<>(state);
        // Appelle une méthode
        newState.remove(key);
        // Renvoie une valeur à l'appelant
        return new DebugStickState(newState);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
