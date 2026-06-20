// Déclaration du paquet de ce fichier
package net.minestom.server.world.clock;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Déclaration de type (classe/interface/enum/record)
public sealed interface WorldClock extends WorldClocks permits WorldClockImpl {
    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<WorldClock>> NETWORK_TYPE = RegistryKey.networkType(Registries::worldClock);
    // Appelle une méthode
    Codec<RegistryKey<WorldClock>> CODEC = RegistryKey.codec(Registries::worldClock);

    // Appelle une méthode
    Codec<WorldClock> REGISTRY_CODEC = StructCodec.struct(WorldClock::create);

    // Début d'une méthode/d'un bloc
    static WorldClock create() {
        // Renvoie une valeur à l'appelant
        return new WorldClockImpl();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new instance of the "minecraft:world_clock" registry containing the vanilla contents.
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<WorldClock> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("world_clock"), REGISTRY_CODEC, RegistryData.Resource.WORLD_CLOCKS);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
