// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.Keyed;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface Registry<T> extends Keyed permits StaticRegistry, DynamicRegistry {

    // Annotation for the following element
    @Nullable T get(int id);
    // Annotation for the following element
    @Nullable T get(Key key);
    // Start of a method/block
    default @Nullable T get(RegistryKey<T> key) {
        // Returns a value to the caller
        return get(key.key());
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable RegistryKey<T> getKey(int id);
    // Annotation for the following element
    @Nullable RegistryKey<T> getKey(T value);
    // Annotation for the following element
    @Nullable RegistryKey<T> getKey(Key key);

    /**
     * Returns the protocol ID associated with the given {@link RegistryKey}, or -1 if none is registered.
     */
    // Calls a method
    int getId(RegistryKey<T> key);

    // Annotation for the following element
    @Nullable DataPack getPack(int id);
    // Start of a method/block
    default @Nullable DataPack getPack(RegistryKey<T> key) {
        // Calls a method
        final int id = getId(key);
        // Returns a value to the caller
        return id == -1 ? null : getPack(id);
    // End of a block/expression
    }

    /**
     * Returns the number of entries present in this registry.
     */
    // Calls a method
    int size();

    /**
     * <p>Returns the keys in this registry as an immutable list.</p>
     *
     * <p>Note: The list order is not guaranteed, and the contents are not guaranteed to update with the registry,
     * it should be fetched again for updated values.</p>
     *
     * @return An immutable collection of the keys in this registry.
     */
    // Calls a method
    Collection<RegistryKey<T>> keys();

    /**
     * <p>Returns the entries in this registry as an immutable list.</p>
     *
     * <p>Note: The list order is not guaranteed, and the contents are not guaranteed to update with the registry,
     * it should be fetched again for updated values.</p>
     *
     * @return An immutable list of the entries in this registry.
     */
    // Calls a method
    Collection<T> values();

    // Tags

    /**
     * Get a tag by its key.
     *
     * @param key The key of the tag
     * @return The tag, or null if not found
     */
    // Annotation for the following element
    @Nullable RegistryTag<T> getTag(TagKey<T> key);
    // Start of a method/block
    default @Nullable RegistryTag<T> getTag(Key key) {
        // Returns a value to the caller
        return getTag(new TagKeyImpl<>(key));
    // End of a block/expression
    }
    /**
     * Get a tag by its key, or create it if it does not exist.
     *
     * <p><b>Note that if a tag is created by this operation, it will not be added to clients who previously received tags.
     * You must resend updated registry tags manually for this to take effect. Referencing a tag for which the client
     * is not aware will result in an immediate clientside disconnect.</b></p>
     *
     * @param key The key of the tag
     * @return The tag, never null
     */
    // Calls a method
    RegistryTag<T> getOrCreateTag(TagKey<T> key);

    /**
     * Removes the given tag from this registry if it exists.
     *
     * <p>Note that this does _not_ remove the tag from clients who have previously received tags.
     * You must resend updated registry tags manually for this to take effect.</p>
     *
     * @param key The registry tag to remove.
     * @return True if the tag was removed, false if it did not exist in this registry.
     */
    // Calls a method
    boolean removeTag(TagKey<T> key);

    /**
     * <p>Returns the available tags in this registry.</p>
     *
     * <p>Note: The returned list is not guaranteed to update with the registry,
     * it should be fetched again for updated values.</p>
     *
     * @return An immutable collection of the tags in this registry.
     */
    // Calls a method
    Collection<RegistryTag<T>> tags();

    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    TagsPacket.Registry tagRegistry();

// End of a block/expression
}
