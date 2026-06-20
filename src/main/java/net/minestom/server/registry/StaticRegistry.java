// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

/**
 * A registry for holding static vanilla registry data. Not generally user modifiable, always immutable.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class StaticRegistry<T extends StaticProtocolObject<T>> implements Registry<T> {
    // Instruction de code
    private final Key key;
    // Instruction de code
    private final Map<Key, T> keyToValue;
    // Instruction de code
    private final Map<T, RegistryKey<T>> valueToKey;
    // Instruction de code
    private final List<T> idToValue;

    // Instruction de code
    private final Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags;

    // Instruction de code
    StaticRegistry(
            // Instruction de code
            Key key,
            // Instruction de code
            Map<Key, T> namespaces,
            // Instruction de code
            ObjectArray<T> ids,
            // Instruction de code
            Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags
    // Début d'une méthode/d'un bloc
    ) {
        // Accès à l'objet courant/parent
        this.key = key;
        // Accès à l'objet courant/parent
        this.keyToValue = Map.copyOf(namespaces);
        // Appelle une méthode
        var valueToKey = new HashMap<T, RegistryKey<T>>(namespaces.size());
        // Boucle : répète un bloc
        for (var entry : namespaces.entrySet())
            // Appelle une méthode
            valueToKey.put(entry.getValue(), new RegistryKeyImpl<>(entry.getKey()));
        // Accès à l'objet courant/parent
        this.valueToKey = Map.copyOf(valueToKey);
        // Accès à l'objet courant/parent
        this.idToValue = ids.toList();
        // Accès à l'objet courant/parent
        this.tags = new ConcurrentHashMap<>(tags);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key key() {
        // Renvoie une valeur à l'appelant
        return this.key;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable T get(int id) {
        // Renvoie une valeur à l'appelant
        return this.idToValue.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable T get(Key key) {
        // Renvoie une valeur à l'appelant
        return this.keyToValue.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<T> getKey(int id) {
        // Appelle une méthode
        final T value = this.idToValue.get(id);
        // Renvoie une valeur à l'appelant
        return value == null ? null : new RegistryKeyImpl<>(value.key());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<T> getKey(T value) {
        // Renvoie une valeur à l'appelant
        return this.valueToKey.get(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<T> getKey(Key key) {
        // Renvoie une valeur à l'appelant
        return this.keyToValue.containsKey(key) ? new RegistryKeyImpl<>(key) : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getId(RegistryKey<T> key) {
        // Appelle une méthode
        final T value = this.keyToValue.get(key.key());
        // Embranchement : vérifie une condition
        if (value == null) return -1; // Not found
        // Renvoie une valeur à l'appelant
        return this.valueToKey.get(value) != null ? value.id() : -1;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable DataPack getPack(int id) {
        // Static registries are always in the core data pack
        // Renvoie une valeur à l'appelant
        return this.idToValue.get(id) != null ? DataPack.MINECRAFT_CORE : null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int size() {
        // Renvoie une valeur à l'appelant
        return this.keyToValue.size();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<RegistryKey<T>> keys() {
        // Renvoie une valeur à l'appelant
        return this.valueToKey.values();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<T> values() {
        // Renvoie une valeur à l'appelant
        return this.valueToKey.keySet();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryTag<T> getTag(TagKey<T> key) {
        // Renvoie une valeur à l'appelant
        return this.tags.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RegistryTag<T> getOrCreateTag(TagKey<T> key) {
        // Renvoie une valeur à l'appelant
        return this.tags.computeIfAbsent(key, RegistryTagImpl.Backed::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeTag(TagKey<T> key) {
        // Renvoie une valeur à l'appelant
        return this.tags.remove(key) != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<RegistryTag<T>> tags() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableCollection(this.tags.values());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TagsPacket.Registry tagRegistry() {
        // Appelle une méthode
        final List<TagsPacket.Tag> tagList = new ArrayList<>(tags.size());
        // Boucle : répète un bloc
        for (final RegistryTagImpl.Backed<T> tag : tags.values()) {
            // Appelle une méthode
            final int[] entries = new int[tag.size()];
            // Affecte une valeur
            int i = 0;
            // Boucle : répète un bloc
            for (var staticEntry : tag) {
                // Affecte une valeur
                entries[i++] = staticEntry instanceof StaticProtocolObject<T> po
                        // Appelle une méthode
                        ? po.id() : getId(staticEntry);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            tagList.add(new TagsPacket.Tag(tag.key().key().asString(), entries));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new TagsPacket.Registry(key().asString(), tagList);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
