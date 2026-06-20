// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

// Type declaration (class/interface/enum/record)
public class CommandData {

    // Calls a method
    private final Map<String, Object> dataMap = new ConcurrentHashMap<>();

    // Start of a method/block
    public CommandData set(String key, Object value) {
        // Access to the current/parent object
        this.dataMap.put(key, value);
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public <T> T get(String key) {
        // Returns a value to the caller
        return (T) dataMap.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean has(String key) {
        // Returns a value to the caller
        return dataMap.containsKey(key);
    // End of a block/expression
    }

    // Start of a method/block
    public Map<String, Object> getDataMap() {
        // Returns a value to the caller
        return dataMap;
    // End of a block/expression
    }
// End of a block/expression
}
