// Package declaration for this file
package net.minestom.server.statistic;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;

// Type declaration (class/interface/enum/record)
record StatisticTypeImpl(Key key, int id) implements StatisticType {
    // Assigns a value
    static final Registry<StatisticType> REGISTRY = RegistryData.createStaticRegistry(Key.key("custom_statistics"),
            // Calls a method
            (namespace, properties) -> new StatisticTypeImpl(Key.key(namespace), properties.getInt("id")));

    // Start of a method/block
    static StatisticType get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return name();
    // End of a block/expression
    }
// End of a block/expression
}
