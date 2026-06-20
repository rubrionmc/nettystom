// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

// Déclaration de type (classe/interface/enum/record)
public class CommandData {

    // Appelle une méthode
    private final Map<String, Object> dataMap = new ConcurrentHashMap<>();

    // Début d'une méthode/d'un bloc
    public CommandData set(String key, Object value) {
        // Accès à l'objet courant/parent
        this.dataMap.put(key, value);
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public <T> T get(String key) {
        // Renvoie une valeur à l'appelant
        return (T) dataMap.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean has(String key) {
        // Renvoie une valeur à l'appelant
        return dataMap.containsKey(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Map<String, Object> getDataMap() {
        // Renvoie une valeur à l'appelant
        return dataMap;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
