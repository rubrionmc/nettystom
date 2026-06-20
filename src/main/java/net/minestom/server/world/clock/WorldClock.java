// Package declaration for this file
package net.minestom.server.world.clock;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public sealed interface WorldClock extends WorldClocks permits WorldClockImpl {
    // Calls a method
    NetworkBuffer.Type<RegistryKey<WorldClock>> NETWORK_TYPE = RegistryKey.networkType(Registries::worldClock);
    // Calls a method
    Codec<RegistryKey<WorldClock>> CODEC = RegistryKey.codec(Registries::worldClock);

    // Calls a method
    Codec<WorldClock> REGISTRY_CODEC = StructCodec.struct(WorldClock::create);

    // Start of a method/block
    static WorldClock create() {
        // Returns a value to the caller
        return new WorldClockImpl();
    // End of a block/expression
    }

    /**
     * Creates a new instance of the "minecraft:world_clock" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<WorldClock> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("world_clock"), REGISTRY_CODEC, RegistryData.Resource.WORLD_CLOCKS);
    // End of a block/expression
    }
// End of a block/expression
}
