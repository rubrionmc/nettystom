// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Keyed;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Registry<T> extends Keyed permits StaticRegistry, DynamicRegistry {

    // Annotation pour l'élément suivant
    @Nullable T get(int id);
    // Annotation pour l'élément suivant
    @Nullable T get(Key key);
    // Début d'une méthode/d'un bloc
    default @Nullable T get(RegistryKey<T> key) {
        // Renvoie une valeur à l'appelant
        return get(key.key());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable RegistryKey<T> getKey(int id);
    // Annotation pour l'élément suivant
    @Nullable RegistryKey<T> getKey(T value);
    // Annotation pour l'élément suivant
    @Nullable RegistryKey<T> getKey(Key key);

    /**
     * Returns the protocol ID associated with the given {@link RegistryKey}, or -1 if none is registered.
     */
    // Appelle une méthode
    int getId(RegistryKey<T> key);

    // Annotation pour l'élément suivant
    @Nullable DataPack getPack(int id);
    // Début d'une méthode/d'un bloc
    default @Nullable DataPack getPack(RegistryKey<T> key) {
        // Appelle une méthode
        final int id = getId(key);
        // Renvoie une valeur à l'appelant
        return id == -1 ? null : getPack(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the number of entries present in this registry.
     */
    // Appelle une méthode
    int size();

    /**
     * <p>Returns the keys in this registry as an immutable list.</p>
     *
     * <p>Note: The list order is not guaranteed, and the contents are not guaranteed to update with the registry,
     * it should be fetched again for updated values.</p>
     *
     * @return An immutable collection of the keys in this registry.
     */
    // Appelle une méthode
    Collection<RegistryKey<T>> keys();

    /**
     * <p>Returns the entries in this registry as an immutable list.</p>
     *
     * <p>Note: The list order is not guaranteed, and the contents are not guaranteed to update with the registry,
     * it should be fetched again for updated values.</p>
     *
     * @return An immutable list of the entries in this registry.
     */
    // Appelle une méthode
    Collection<T> values();

    // Tags

    /**
     * Get a tag by its key.
     *
     * @param key The key of the tag
     * @return The tag, or null if not found
     */
    // Annotation pour l'élément suivant
    @Nullable RegistryTag<T> getTag(TagKey<T> key);
    // Début d'une méthode/d'un bloc
    default @Nullable RegistryTag<T> getTag(Key key) {
        // Renvoie une valeur à l'appelant
        return getTag(new TagKeyImpl<>(key));
    // Fin d'un bloc/d'une expression
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
    // Appelle une méthode
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
    // Appelle une méthode
    boolean removeTag(TagKey<T> key);

    /**
     * <p>Returns the available tags in this registry.</p>
     *
     * <p>Note: The returned list is not guaranteed to update with the registry,
     * it should be fetched again for updated values.</p>
     *
     * @return An immutable collection of the tags in this registry.
     */
    // Appelle une méthode
    Collection<RegistryTag<T>> tags();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    TagsPacket.Registry tagRegistry();

// Fin d'un bloc/d'une expression
}
