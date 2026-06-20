// Package declaration for this file
package net.minestom.server.statistic;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface StatisticType extends StaticProtocolObject<StatisticType>, StatisticTypes permits StatisticTypeImpl {

    // Start of a method/block
    static Collection<StatisticType> values() {
        // Returns a value to the caller
        return StatisticTypeImpl.REGISTRY.values();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable StatisticType fromKey(@KeyPattern String key) {
        // Returns a value to the caller
        return fromKey(Key.key(key));
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable StatisticType fromKey(Key key) {
        // Returns a value to the caller
        return StatisticTypeImpl.REGISTRY.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable StatisticType fromId(int id) {
        // Returns a value to the caller
        return StatisticTypeImpl.REGISTRY.get(id);
    // End of a block/expression
    }

// End of a block/expression
}
