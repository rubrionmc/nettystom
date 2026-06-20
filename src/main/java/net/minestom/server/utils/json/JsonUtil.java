// Déclaration du paquet de ce fichier
package net.minestom.server.utils.json;

// Import d'une classe nécessaire
import com.google.gson.Gson;
// Import d'une classe nécessaire
import com.google.gson.GsonBuilder;
// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.Strictness;

// Import d'une classe nécessaire
import java.io.Reader;

// Déclaration de type (classe/interface/enum/record)
public final class JsonUtil {
    // Appelle une méthode
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().setStrictness(Strictness.STRICT).create();

    // Début d'une méthode/d'un bloc
    public static JsonElement fromJson(String json) {
        // Renvoie une valeur à l'appelant
        return GSON.fromJson(json, JsonElement.class);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static JsonElement fromJson(Reader reader) {
        // Renvoie une valeur à l'appelant
        return GSON.fromJson(reader, JsonElement.class);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String toJson(JsonElement element) {
        // Renvoie une valeur à l'appelant
        return GSON.toJson(element);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private JsonUtil() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
