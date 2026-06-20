// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.registry.Registry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Type declaration (class/interface/enum/record)
record BlockSoundImpl(RegistryData.BlockSoundTypeEntry registry) implements BlockSoundType {
    // Assigns a value
    static final Registry<BlockSoundType> REGISTRY = RegistryData.createStaticRegistry(Key.key("block_sound_type"),
            // Calls a method
            (namespace, properties) -> new BlockSoundImpl(RegistryData.blockSoundTypeEntry(namespace, properties)));

    // Start of a method/block
    static @UnknownNullability BlockSoundType get(String key) {
        // Returns a value to the caller
        return REGISTRY.get(Key.key(key));
    // End of a block/expression
    }
// End of a block/expression
}
