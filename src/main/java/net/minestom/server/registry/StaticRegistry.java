// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

/**
 * A registry for holding static vanilla registry data. Not generally user modifiable, always immutable.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class StaticRegistry<T extends StaticProtocolObject<T>> implements Registry<T> {
    // Code statement
    private final Key key;
    // Code statement
    private final Map<Key, T> keyToValue;
    // Code statement
    private final Map<T, RegistryKey<T>> valueToKey;
    // Code statement
    private final List<T> idToValue;

    // Code statement
    private final Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags;

    // Code statement
    StaticRegistry(
            // Code statement
            Key key,
            // Code statement
            Map<Key, T> namespaces,
            // Code statement
            ObjectArray<T> ids,
            // Code statement
            Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags
    // Start of a method/block
    ) {
        // Access to the current/parent object
        this.key = key;
        // Access to the current/parent object
        this.keyToValue = Map.copyOf(namespaces);
        // Calls a method
        var valueToKey = new HashMap<T, RegistryKey<T>>(namespaces.size());
        // Loop: repeats a block
        for (var entry : namespaces.entrySet())
            // Calls a method
            valueToKey.put(entry.getValue(), new RegistryKeyImpl<>(entry.getKey()));
        // Access to the current/parent object
        this.valueToKey = Map.copyOf(valueToKey);
        // Access to the current/parent object
        this.idToValue = ids.toList();
        // Access to the current/parent object
        this.tags = new ConcurrentHashMap<>(tags);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key key() {
        // Returns a value to the caller
        return this.key;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable T get(int id) {
        // Returns a value to the caller
        return this.idToValue.get(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable T get(Key key) {
        // Returns a value to the caller
        return this.keyToValue.get(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryKey<T> getKey(int id) {
        // Calls a method
        final T value = this.idToValue.get(id);
        // Returns a value to the caller
        return value == null ? null : new RegistryKeyImpl<>(value.key());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryKey<T> getKey(T value) {
        // Returns a value to the caller
        return this.valueToKey.get(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryKey<T> getKey(Key key) {
        // Returns a value to the caller
        return this.keyToValue.containsKey(key) ? new RegistryKeyImpl<>(key) : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getId(RegistryKey<T> key) {
        // Calls a method
        final T value = this.keyToValue.get(key.key());
        // Branch: checks a condition
        if (value == null) return -1; // Not found
        // Returns a value to the caller
        return this.valueToKey.get(value) != null ? value.id() : -1;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable DataPack getPack(int id) {
        // Static registries are always in the core data pack
        // Returns a value to the caller
        return this.idToValue.get(id) != null ? DataPack.MINECRAFT_CORE : null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int size() {
        // Returns a value to the caller
        return this.keyToValue.size();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<RegistryKey<T>> keys() {
        // Returns a value to the caller
        return this.valueToKey.values();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<T> values() {
        // Returns a value to the caller
        return this.valueToKey.keySet();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryTag<T> getTag(TagKey<T> key) {
        // Returns a value to the caller
        return this.tags.get(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RegistryTag<T> getOrCreateTag(TagKey<T> key) {
        // Returns a value to the caller
        return this.tags.computeIfAbsent(key, RegistryTagImpl.Backed::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeTag(TagKey<T> key) {
        // Returns a value to the caller
        return this.tags.remove(key) != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<RegistryTag<T>> tags() {
        // Returns a value to the caller
        return Collections.unmodifiableCollection(this.tags.values());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TagsPacket.Registry tagRegistry() {
        // Calls a method
        final List<TagsPacket.Tag> tagList = new ArrayList<>(tags.size());
        // Loop: repeats a block
        for (final RegistryTagImpl.Backed<T> tag : tags.values()) {
            // Calls a method
            final int[] entries = new int[tag.size()];
            // Assigns a value
            int i = 0;
            // Loop: repeats a block
            for (var staticEntry : tag) {
                // Assigns a value
                entries[i++] = staticEntry instanceof StaticProtocolObject<T> po
                        // Calls a method
                        ? po.id() : getId(staticEntry);
            // End of a block/expression
            }
            // Calls a method
            tagList.add(new TagsPacket.Tag(tag.key().key().asString(), entries));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new TagsPacket.Registry(key().asString(), tagList);
    // End of a block/expression
    }

// End of a block/expression
}
