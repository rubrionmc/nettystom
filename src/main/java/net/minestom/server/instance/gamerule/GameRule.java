// Package declaration for this file
package net.minestom.server.instance.gamerule;

// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/// Bindings for [Game rule](https://minecraft.wiki/w/Game_rule)
// Type declaration (class/interface/enum/record)
public sealed interface GameRule<T> extends GameRules, StaticProtocolObject<GameRule<?>> permits GameRuleImpl {
    // Start of a method/block
    static Registry<GameRule<?>> staticRegistry() {
        // Returns a value to the caller
        return GameRuleImpl.REGISTRY;
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Annotation for the following element
    @Override
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    default Object registry() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Calls a method
    T defaultValue();
// End of a block/expression
}
