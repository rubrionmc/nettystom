// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.RegistryDataPacket;
// Import of a required class
import net.minestom.server.utils.json.JsonUtil;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.io.InputStreamReader;
// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class DynamicRegistryImpl<T> implements DynamicRegistry<T> {
    // Assigns a value
    private static final String UNSAFE_REMOVE_MESSAGE = "Unsafe remove is disabled. Enable by setting the system property 'minestom.registry.unsafe-ops' to 'true'";
    // Could also just use `this`, but this is a good candidate for identityless classes.
    // Also, what use case requires you to mutate registries faster than one monitor?
    // Calls a method
    private static final Object REGISTRY_LOCK = new Object();

    // Assigns a value
    private volatile Registries registries = null;
    // Calls a method
    private final CachedPacket vanillaRegistryDataPacket = new CachedPacket(() -> createRegistryDataPacket(registries, true));

    // Code statement
    private final List<T> idToValue;
    // Code statement
    private final List<RegistryKey<T>> idToKey;
    // Code statement
    private final Map<RegistryKey<T>, Integer> keyToId;
    // Code statement
    private final Map<Key, T> keyToValue;
    // Code statement
    private final Map<T, RegistryKey<T>> valueToKey;
    // Code statement
    private final List<DataPack> packById;

    // Code statement
    private final Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags;

    // Code statement
    private final Key key;
    // Code statement
    private final Codec<T> codec;

    // Start of a method/block
    DynamicRegistryImpl(Key key, @Nullable Codec<T> codec) {
        // Access to the current/parent object
        this.key = key;
        // Access to the current/parent object
        this.codec = codec;
        // Expect stale data possibilities with unsafe ops.
        // Access to the current/parent object
        this.idToValue = new ArrayList<>();
        // Access to the current/parent object
        this.idToKey = new ArrayList<>();
        // Access to the current/parent object
        this.keyToId = new HashMap<>();
        // Access to the current/parent object
        this.keyToValue = new HashMap<>();
        // Access to the current/parent object
        this.valueToKey = new HashMap<>();
        // Access to the current/parent object
        this.packById = new ArrayList<>();
        // Tags are always mutable across the lock.
        // Access to the current/parent object
        this.tags = new ConcurrentHashMap<>();
    // End of a block/expression
    }

    // Used to create compressed registries
    // Code statement
    DynamicRegistryImpl(Key key, @Nullable Codec<T> codec, List<T> idToValue,
                        // Code statement
                        Map<RegistryKey<T>, Integer> keyToId, List<RegistryKey<T>> idToKey,
                        // Code statement
                        Map<Key, T> keyToValue, Map<T, RegistryKey<T>> valueToKey,
                        // Start of a method/block
                        List<DataPack> packById, Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags) {
        // Access to the current/parent object
        this.key = key;
        // Access to the current/parent object
        this.codec = codec;
        // Access to the current/parent object
        this.idToValue = idToValue;
        // Access to the current/parent object
        this.idToKey = idToKey;
        // Access to the current/parent object
        this.keyToId = keyToId;
        // Access to the current/parent object
        this.keyToValue = keyToValue;
        // Access to the current/parent object
        this.valueToKey = valueToKey;
        // Access to the current/parent object
        this.packById = packById;
        // Access to the current/parent object
        this.tags = tags;
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

    // Start of a method/block
    public @UnknownNullability Codec<T> codec() {
        // Returns a value to the caller
        return codec;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable T get(int id) {
        // Branch: checks a condition
        if (id < 0 || id >= idToValue.size())
            // Returns a value to the caller
            return null;
        // Returns a value to the caller
        return idToValue.get(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable T get(Key key) {
        // Returns a value to the caller
        return keyToValue.get(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryKey<T> getKey(int id) {
        // Branch: checks a condition
        if (id < 0 || id >= idToKey.size())
            // Returns a value to the caller
            return null;
        // Returns a value to the caller
        return idToKey.get(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryKey<T> getKey(T value) {
        // Returns a value to the caller
        return valueToKey.get(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable RegistryKey<T> getKey(Key key) {
        // Branch: checks a condition
        if (!keyToValue.containsKey(key))
            // Returns a value to the caller
            return null;
        // Returns a value to the caller
        return new RegistryKeyImpl<>(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getId(RegistryKey<T> key) {
        // Returns a value to the caller
        return keyToId.getOrDefault(key, -1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RegistryKey<T> register(Key key, T object, DataPack pack) {
        // Branch: checks a condition
        if (isFrozen()) throw new UnsupportedOperationException(UNSAFE_REMOVE_MESSAGE);
        // Calls a method
        Objects.requireNonNull(key, "Key cannot be null");
        // Calls a method
        Objects.requireNonNull(object, "Object cannot be null");
        // Calls a method
        Objects.requireNonNull(pack, "Pack cannot be null");

        // Calls a method
        final RegistryKey<T> registryKey = new RegistryKeyImpl<>(key);
        // Start of a method/block
        synchronized (REGISTRY_LOCK) {
            // Assigns a value
            Integer id = keyToId.get(registryKey); // Array set at home
            // Calls a method
            keyToValue.put(key, object);
            // Calls a method
            valueToKey.put(object, registryKey);
            // Branch: checks a condition
            if (id == null) {
                // Calls a method
                idToValue.add(object);
                // Calls a method
                idToKey.add(registryKey);
                // Calls a method
                keyToId.put(registryKey, idToValue.size() - 1);
                // Calls a method
                packById.add(pack);
            // Alternative branch of the condition
            } else {
                // Calls a method
                idToValue.set(id, object);
                // Calls a method
                idToKey.set(id, registryKey);
                // Calls a method
                keyToId.put(registryKey, id);
                // Calls a method
                packById.set(id, pack);
            // End of a block/expression
            }

            // Calls a method
            vanillaRegistryDataPacket.invalidate();
            // Returns a value to the caller
            return registryKey;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean remove(Key key) throws UnsupportedOperationException {
        // Branch: checks a condition
        if (isFrozen()) throw new UnsupportedOperationException(UNSAFE_REMOVE_MESSAGE);
        // Calls a method
        Objects.requireNonNull(key, "Key cannot be null");

        // Calls a method
        final RegistryKey<T> registryKey = new RegistryKeyImpl<>(key);
        // Start of a method/block
        synchronized (REGISTRY_LOCK) {
            // Calls a method
            Integer idObject = keyToId.get(registryKey);
            // Branch: checks a condition
            if (idObject == null) return false;
            // Assigns a value
            int id = idObject;

            // Remove value from all mappings (shifting down indices)
            // Calls a method
            idToValue.remove(id);
            // Calls a method
            idToKey.remove(registryKey);
            // Calls a method
            keyToId.remove(registryKey);
            // Calls a method
            var value = keyToValue.remove(key);
            // Calls a method
            valueToKey.remove(value);
            // Calls a method
            packById.remove(id);

            // Remove all references from tags
            // Loop: repeats a block
            for (final var tag : tags.values()) {
                // Calls a method
                tag.remove(registryKey);
            // End of a block/expression
            }

            // Calls a method
            vanillaRegistryDataPacket.invalidate();
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable DataPack getPack(int id) {
        // Branch: checks a condition
        if (id < 0 || id >= packById.size())
            // Returns a value to the caller
            return null;
        // Returns a value to the caller
        return packById.get(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int size() {
        // Returns a value to the caller
        return idToValue.size();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<RegistryKey<T>> keys() {
        // Returns a value to the caller
        return Collections.unmodifiableCollection(idToKey);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<T> values() {
        // Returns a value to the caller
        return Collections.unmodifiableCollection(idToValue);
    // End of a block/expression
    }

    // Tags

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
    @Override // This method is called by a virtual thread in the configuration phase
    // Start of a method/block
    public SendablePacket registryDataPacket(Registries registries, boolean excludeVanilla) {
        // We cache the vanilla packet because that is by far the most common case. If some client claims not to have
        // the vanilla datapack we can compute the entire thing.
        // Branch: checks a condition
        if (excludeVanilla) {
            // Branch: checks a condition
            if (this.registries != registries) {
                // Code statement
                synchronized (REGISTRY_LOCK) { // Bootleg off the static lock for this mutation
                    // Branch: checks a condition
                    if (this.registries != registries) {
                        // Access to the current/parent object
                        this.registries = registries;
                        // Calls a method
                        vanillaRegistryDataPacket.invalidate();
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return vanillaRegistryDataPacket;
        // End of a block/expression
        }

        // Returns a value to the caller
        return createRegistryDataPacket(registries, false);
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
            for (var registryKey : tag)
                // Calls a method
                entries[i++] = keyToId.get(registryKey);
            // Calls a method
            tagList.add(new TagsPacket.Tag(tag.key().key().asString(), entries));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new TagsPacket.Registry(key().asString(), tagList);
    // End of a block/expression
    }

    // Start of a method/block
    private RegistryDataPacket createRegistryDataPacket(Registries registries, boolean excludeVanilla) {
        // Calls a method
        Objects.requireNonNull(codec, "Cannot create registry data packet for server-only registry");
        // Calls a method
        Transcoder<BinaryTag> transcoder = new RegistryTranscoder<>(Transcoder.NBT, registries);
        // Copy to avoid concurrent modification issues while iterating, as we are not synchronized on the registry
        // Code statement
        final List<T> idToValue;
        // Code statement
        final List<DataPack> packById;
        // Branch: checks a condition
        if (!canFreeze()) {
            // Start of a method/block
            synchronized (REGISTRY_LOCK) {
                // Calls a method
                idToValue = List.copyOf(this.idToValue);
                // Calls a method
                packById = List.copyOf(this.packById);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Assigns a value
            idToValue = this.idToValue;
            // Assigns a value
            packById = this.packById;
        // End of a block/expression
        }
        // Calls a method
        List<RegistryDataPacket.Entry> entries = new ArrayList<>(idToValue.size());
        // Loop: repeats a block
        for (int i = 0; i < idToValue.size(); i++) {
            // Assigns a value
            CompoundBinaryTag data = null;
            // sorta todo, sorta just a note:
            // Right now we very much only support the minecraft:core (vanilla) 'pack'. Any entry which was not loaded
            // from static data will be treated as non vanilla and always sent completely. However, we really should
            // support arbitrary packs and associate all registry data with a datapack. Additionally, we should generate
            // all data for the experimental datapacks built in to vanilla such as the next update experimental (1.21 at
            // the time of writing). Datagen currently behaves kind of badly in that the registry inspecting generators
            // like material, block, etc generate entries which are behind feature flags, whereas the ones which inspect
            // static assets (the traditionally dynamic registries), do not generate those assets.
            // Calls a method
            T entry = idToValue.get(i);
            // Calls a method
            DataPack pack = packById.get(i);
            // Branch: checks a condition
            if (!excludeVanilla || pack != DataPack.MINECRAFT_CORE) {
                // Calls a method
                final Result<BinaryTag> entryResult = codec.encode(transcoder, entry);
                // Branch: checks a condition
                if (entryResult instanceof Result.Ok(BinaryTag tag)) {
                    // Calls a method
                    data = (CompoundBinaryTag) tag;
                // Alternative branch of the condition
                } else {
                    // Throws an exception
                    throw new IllegalStateException("Failed to encode registry entry " + i + " (" + getKey(i) + ") for registry " + key);
                // End of a block/expression
                }
            // End of a block/expression
            }
            //noinspection DataFlowIssue
            // Calls a method
            entries.add(new RegistryDataPacket.Entry(getKey(i).key().asString(), data));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new RegistryDataPacket(key.asString(), entries);
    // End of a block/expression
    }

    /**
     * Attempts to create a copy with compressed data structures.
     *
     * @return A safe copy of this registry
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    DynamicRegistryImpl<T> compact() {
        // Create new instances so they are trimmed to size without downcasting.
        // Returns a value to the caller
        return new DynamicRegistryImpl<>(key, codec,
                // Creates a new object
                new ArrayList<>(idToValue),
                // Creates a new object
                new HashMap<>(keyToId),
                // Creates a new object
                new ArrayList<>(idToKey),
                // Creates a new object
                new HashMap<>(keyToValue),
                // Creates a new object
                new HashMap<>(valueToKey),
                // Creates a new object
                new ArrayList<>(packById),
                // Creates a new object
                new ConcurrentHashMap<>(tags)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    static boolean isFrozen() {
        // Returns a value to the caller
        return canFreeze() && MinecraftServer.process() != null && MinecraftServer.isStarted();
    // End of a block/expression
    }

    // Start of a method/block
    static boolean canFreeze() {
        // Returns a value to the caller
        return !ServerFlag.REGISTRY_UNSAFE_OPS && !ServerFlag.INSIDE_TEST;
    // End of a block/expression
    }

    // Start of a method/block
    static <T> void loadStaticJsonRegistry(@Nullable Registries registries, DynamicRegistryImpl<T> registry, RegistryData.Resource resource, @Nullable Comparator<String> idComparator, Codec<T> codec) {
        // Calls a method
        Check.argCondition(!resource.fileName().endsWith(".json"), "Resource must be a JSON file: {0}", resource.fileName());
        // Exception handling
        try (InputStream resourceStream = RegistryData.loadRegistryFile(String.format("%s.json", registry.key().value()))) {
            // Calls a method
            Check.notNull(resourceStream, "Resource {0} does not exist!", resource);
            // Calls a method
            final JsonElement json = JsonUtil.fromJson(new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
            // Branch: checks a condition
            if (!(json instanceof JsonObject root))
                // Throws an exception
                throw new IllegalStateException("Failed to load registry " + registry.key() + ": expected a JSON object, got " + json);

            // Calls a method
            final Transcoder<JsonElement> transcoder = registries != null ? new RegistryTranscoder<>(Transcoder.JSON, registries, false, true) : Transcoder.JSON;
            // Calls a method
            List<Map.Entry<String, JsonElement>> entries = new ArrayList<>(root.entrySet());
            // Branch: checks a condition
            if (idComparator != null) entries.sort(Map.Entry.comparingByKey(idComparator));
            // Loop: repeats a block
            for (Map.Entry<String, JsonElement> entry : entries) {
                // Calls a method
                final String namespace = entry.getKey();
                // Calls a method
                final Result<T> valueResult = codec.decode(transcoder, entry.getValue());
                // Branch: checks a condition
                if (valueResult instanceof Result.Ok(T value)) {
                    // Calls a method
                    registry.register(namespace, value, DataPack.MINECRAFT_CORE);
                // Alternative branch of the condition
                } else {
                    // Throws an exception
                    throw new IllegalStateException("Failed to decode registry entry " + namespace + " for registry " + registry.key() + ": " + valueResult);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Load tags if present
            // Calls a method
            Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags = RegistryData.loadTags(registry.key());
            // Calls a method
            registry.tags.putAll(tags);
        // Start of a method/block
        } catch (Exception e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
