// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.dialog.Dialog;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.item.enchant.Enchantment;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Comparator;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public sealed interface DynamicRegistry<T> extends Registry<T> permits DynamicRegistryImpl {

    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    static <T> DynamicRegistry<T> fromMap(Key key, Map.Entry<Key, T>... entries) {
        // Affecte une valeur
        var registry = new DynamicRegistryImpl<T>(key, null);
        // Boucle : répète un bloc
        for (var entry : entries)
            // Appelle une méthode
            registry.register(entry.getKey(), entry.getValue(), DataPack.MINESTOM_UNNAMED);
        // Renvoie une valeur à l'appelant
        return registry.compact();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> DynamicRegistry<T> create(Key key) {
        // Renvoie une valeur à l'appelant
        return new DynamicRegistryImpl<>(key, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new empty registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec) {
        // Renvoie une valeur à l'appelant
        return new DynamicRegistryImpl<>(key, codec);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec, RegistryData.Resource resource) {
        // Renvoie une valeur à l'appelant
        return create(key, codec, null, resource, null, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec, @Nullable Registries registries, RegistryData.Resource resource) {
        // Renvoie une valeur à l'appelant
        return create(key, codec, registries, resource, null, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new registry of the given type. Should only be used internally.
     *
     * @see Registries
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> DynamicRegistry<T> create(Key key, Codec<T> codec, @Nullable Registries registries, RegistryData.Resource resource, @Nullable Comparator<String> idComparator, @Nullable Codec<T> readCodec) {
        // Affecte une valeur
        final DynamicRegistryImpl<T> registry = new DynamicRegistryImpl<>(key, codec);
        // Appelle une méthode
        DynamicRegistryImpl.loadStaticJsonRegistry(registries, registry, resource, idComparator, Objects.requireNonNullElse(readCodec, codec));
        // Renvoie une valeur à l'appelant
        return registry.compact();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    static DynamicRegistry<Enchantment> createForEnchantmentsWithSelfReferentialLoadingNightmare(
            // Instruction de code
            Key key, Codec<Enchantment> codec,
            // Instruction de code
            RegistryData.Resource resource, Registries registries
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        final DynamicRegistryImpl<Enchantment> registry = new DynamicRegistryImpl<>(key, codec);
        // Début d'une méthode/d'un bloc
        DynamicRegistryImpl.loadStaticJsonRegistry(new Registries.Delegating(registries) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public DynamicRegistry<Enchantment> enchantment() {
                // Renvoie une valeur à l'appelant
                return registry;
            // Fin d'un bloc/d'une expression
            }
        // Instruction de code
        }, registry, resource, null, codec);
        // Renvoie une valeur à l'appelant
        return registry.compact();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    static DynamicRegistry<Dialog> createForDialogWithSelfReferentialLoadingNightmare(
            // Instruction de code
            Key key, Codec<Dialog> codec,
            // Instruction de code
            RegistryData.Resource resource, Registries registries
    // Début d'une méthode/d'un bloc
    ) {
        // Affecte une valeur
        final DynamicRegistryImpl<Dialog> registry = new DynamicRegistryImpl<>(key, codec);
        // Début d'une méthode/d'un bloc
        DynamicRegistryImpl.loadStaticJsonRegistry(new Registries.Delegating(registries) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public DynamicRegistry<Dialog> dialog() {
                // Renvoie une valeur à l'appelant
                return registry;
            // Fin d'un bloc/d'une expression
            }
        // Instruction de code
        }, registry, resource, null, codec);
        // Renvoie une valeur à l'appelant
        return registry;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    default RegistryKey<T> register(String id, T object) {
        // Renvoie une valeur à l'appelant
        return register(Key.key(id), object, DataPack.MINESTOM_UNNAMED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default RegistryKey<T> register(Key id, T object) {
        // Renvoie une valeur à l'appelant
        return register(id, object, DataPack.MINESTOM_UNNAMED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    default RegistryKey<T> register(String id, T object, DataPack pack) {
        // Renvoie une valeur à l'appelant
        return register(Key.key(id), object, pack);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
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
    // Appelle une méthode
    boolean remove(Key key) throws UnsupportedOperationException;

    /**
     * <p>Returns a {@link SendablePacket} potentially excluding vanilla entries if possible. It is never possible to
     * exclude vanilla entries if one has been overridden (e.g. via {@link #register(Key, T)}.</p>
     *
     * @param registries     Registries provider
     * @param excludeVanilla Whether to exclude vanilla entries
     * @return A {@link SendablePacket} containing the registry data
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    SendablePacket registryDataPacket(Registries registries, boolean excludeVanilla);

// Fin d'un bloc/d'une expression
}
