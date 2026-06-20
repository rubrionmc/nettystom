// Package declaration for this file
package net.minestom.server.utils.json;

// Import of a required class
import com.google.gson.Gson;
// Import of a required class
import com.google.gson.GsonBuilder;
// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.Strictness;

// Import of a required class
import java.io.Reader;

// Type declaration (class/interface/enum/record)
public final class JsonUtil {
    // Calls a method
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().setStrictness(Strictness.STRICT).create();

    // Start of a method/block
    public static JsonElement fromJson(String json) {
        // Returns a value to the caller
        return GSON.fromJson(json, JsonElement.class);
    // End of a block/expression
    }

    // Start of a method/block
    public static JsonElement fromJson(Reader reader) {
        // Returns a value to the caller
        return GSON.fromJson(reader, JsonElement.class);
    // End of a block/expression
    }

    // Start of a method/block
    public static String toJson(JsonElement element) {
        // Returns a value to the caller
        return GSON.toJson(element);
    // End of a block/expression
    }

    // Start of a method/block
    private JsonUtil() {
    // End of a block/expression
    }
// End of a block/expression
}
