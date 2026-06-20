// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.dialog.Dialog;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.item.enchant.Enchantment;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Comparator;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;

/**
 * <p>Holds registry data for any of the registries controlled by the server. Entries in registries should be referenced
 * using a {@link RegistryKey} object as opposed to the record type. For example, a biome should be stored as
 * `RegistryKey Biome`, as opposed to `Biome` directly.</p>
 *
 * <p>Builtin registries should be accessed via a {@link Registries} instance (currently implemented by
 * {@link net.minestom.server.ServerProcess}, or from {@link net.minestom.server.MinecraftServer} static methods.</p>
 *
 * @param <T> The type of the registry entries
 * @see Registries
 */
// Type declaration (class/interface/enum/record)
public sealed interface DynamicRegistry<T> extends Registry<T> permits DynamicRegistryImpl {

    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    static <T> DynamicRegistry<T> fromMap(Key key, Map.Entry<Key, T>... entries) {
        // Calls a method
        var registry = new DynamicRegistryImpl<T>(key, null);
        // Loop: repeats a block
        for (var entry : entries)
            // Calls a method
            registry.register(entry.getKey(), entry.getValue(), DataPack.MINESTOM_UNNAMED);
        // Returns a value to the caller
        return registry.compact();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> DynamicRegistry<T> create(Key key) {
        // Returns a value to the caller
        return new DynamicRegistryImpl<>(key, null);
    // End of a block/expression
    }

    /**
     * Creates a new empty registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec) {
        // Returns a value to the caller
        return new DynamicRegistryImpl<>(key, codec);
    // End of a block/expression
    }

    /**
     * Creates a new registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec, RegistryData.Resource resource) {
        // Returns a value to the caller
        return create(key, codec, null, resource, null, null);
    // End of a block/expression
    }

    /**
     * Creates a new registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec, @Nullable Registries registries, RegistryData.Resource resource) {
        // Returns a value to the caller
        return create(key, codec, registries, resource, null, null);
    // End of a block/expression
    }

    /**
     * Creates a new registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec, @Nullable Registries registries, RegistryData.Resource resource, @Nullable Comparator<String> idComparator, @Nullable Codec<T> readCodec) {
        // Calls a method
        final DynamicRegistryImpl<T> registry = new DynamicRegistryImpl<>(key, codec);
        // Calls a method
        DynamicRegistryImpl.loadStaticJsonRegistry(registries, registry, resource, idComparator, Objects.requireNonNullElse(readCodec, codec));
        // Returns a value to the caller
        return registry.compact();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    static DynamicRegistry<Enchantment> createForEnchantmentsWithSelfReferentialLoadingNightmare(
            // Code statement
            Key key, Codec<Enchantment> codec,
            // Code statement
            RegistryData.Resource resource, Registries registries
    // Start of a method/block
    ) {
        // Calls a method
        final DynamicRegistryImpl<Enchantment> registry = new DynamicRegistryImpl<>(key, codec);
        // Start of a method/block
        DynamicRegistryImpl.loadStaticJsonRegistry(new Registries.Delegating() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Registries registries() {
                // Returns a value to the caller
                return registries;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public DynamicRegistry<Enchantment> enchantment() {
                // Returns a value to the caller
                return registry;
            // End of a block/expression
            }
        // Code statement
        }, registry, resource, null, codec);
        // Returns a value to the caller
        return registry.compact();
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    static DynamicRegistry<Dialog> createForDialogWithSelfReferentialLoadingNightmare(
            // Code statement
            Key key, Codec<Dialog> codec,
            // Code statement
            RegistryData.Resource resource, Registries registries
    // Start of a method/block
    ) {
        // Calls a method
        final DynamicRegistryImpl<Dialog> registry = new DynamicRegistryImpl<>(key, codec);
        // Start of a method/block
        DynamicRegistryImpl.loadStaticJsonRegistry(new Registries.Delegating() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Registries registries() {
                // Returns a value to the caller
                return registries;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public DynamicRegistry<Dialog> dialog() {
                // Returns a value to the caller
                return registry;
            // End of a block/expression
            }
        // Code statement
        }, registry, resource, null, codec);
        // Returns a value to the caller
        return registry;
    // End of a block/expression
    }

    /**
     * <p>Register an object to this registry, overwriting the previous entry if any is present.</p>
     *
     * <p>Note: the new registry will not be sent to existing players. They must be returned to
     * the configuration phase to receive new registry data. See {@link Player#startConfigurationPhase()}.</p>
     *
     * <p><b>WARNING:</b> Updating an existing entry is an inherently unsafe operation as it may cause desync with
     * existing structures. <b>This behavior is disabled by default, and must be enabled by setting the system
     * property <code>minestom.registry.unsafe-ops</code> to <code>true</code>.</b></p>
     *
     * @param object The entry to register
     * @return The new ID of the registered object
     */
    // Start of a method/block
    default RegistryKey<T> register(String id, T object) {
        // Returns a value to the caller
        return register(Key.key(id), object, DataPack.MINESTOM_UNNAMED);
    // End of a block/expression
    }

    // Start of a method/block
    default RegistryKey<T> register(Key id, T object) {
        // Returns a value to the caller
        return register(id, object, DataPack.MINESTOM_UNNAMED);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    default RegistryKey<T> register(String id, T object, DataPack pack) {
        // Returns a value to the caller
        return register(Key.key(id), object, pack);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    RegistryKey<T> register(Key id, T object, DataPack pack);

    /**
     * <p>Removes an object from this registry.</p>
     *
     * <p><b>WARNING:</b> This operation will cause all subsequent IDs to be remapped, meaning that any loaded entry
     * with existing IDs may be incorrect. For example, loading a world with 0=plains, 1=desert, 2=badlands would store
     * those IDs in the palette. If you then deleted entry 1 (desert), any desert biomes in the loaded world would
     * become badlands, and any badlands would become invalid. <b>This behavior is disabled by default, and must be
     * enabled by setting the system property <code>minestom.registry.unsafe-ops</code> to <code>true</code>.</b></p>
     *
     * <p>Note: the new registry will not be sent to existing players. They must be returned to
     * the configuration phase to receive new registry data. See {@link Player#startConfigurationPhase()}.</p>
     *
     * @param key The id of the entry to remove
     * @return True if the object was removed, false if it was not present
     * @throws UnsupportedOperationException If the system property <code>minestom.registry.unsafe-remove</code> is not set to <code>true</code>
     */
    // Calls a method
    boolean remove(Key key) throws UnsupportedOperationException;

    /**
     * <p>Returns a {@link SendablePacket} potentially excluding vanilla entries if possible. It is never possible to
     * exclude vanilla entries if one has been overridden (e.g. via {@link #register(Key, T)}.</p>
     *
     * @param registries     Registries provider
     * @param excludeVanilla Whether to exclude vanilla entries
     * @return A {@link SendablePacket} containing the registry data
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    SendablePacket registryDataPacket(Registries registries, boolean excludeVanilla);

// End of a block/expression
}
