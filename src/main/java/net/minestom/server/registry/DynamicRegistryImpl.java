// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.CachedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.TagsPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.RegistryDataPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.json.JsonUtil;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.io.InputStreamReader;
// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class DynamicRegistryImpl<T> implements DynamicRegistry<T> {
    // Affecte une valeur
    private static final String UNSAFE_REMOVE_MESSAGE = "Unsafe remove is disabled. Enable by setting the system property 'minestom.registry.unsafe-ops' to 'true'";
    // Could also just use `this`, but this is a good candidate for identityless classes.
    // Also, what use case requires you to mutate registries faster than one monitor?
    // Appelle une méthode
    private static final Object REGISTRY_LOCK = new Object();

    // Affecte une valeur
    private volatile Registries registries = null;
    // Appelle une méthode
    private final CachedPacket vanillaRegistryDataPacket = new CachedPacket(() -> createRegistryDataPacket(registries, true));

    // Instruction de code
    private final List<T> idToValue;
    // Instruction de code
    private final List<RegistryKey<T>> idToKey;
    // Instruction de code
    private final Map<RegistryKey<T>, Integer> keyToId;
    // Instruction de code
    private final Map<Key, T> keyToValue;
    // Instruction de code
    private final Map<T, RegistryKey<T>> valueToKey;
    // Instruction de code
    private final List<DataPack> packById;

    // Instruction de code
    private final Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags;

    // Instruction de code
    private final Key key;
    // Instruction de code
    private final Codec<T> codec;

    // Début d'une méthode/d'un bloc
    DynamicRegistryImpl(Key key, @Nullable Codec<T> codec) {
        // Accès à l'objet courant/parent
        this.key = key;
        // Accès à l'objet courant/parent
        this.codec = codec;
        // Expect stale data possibilities with unsafe ops.
        // Accès à l'objet courant/parent
        this.idToValue = new ArrayList<>();
        // Accès à l'objet courant/parent
        this.idToKey = new ArrayList<>();
        // Accès à l'objet courant/parent
        this.keyToId = new HashMap<>();
        // Accès à l'objet courant/parent
        this.keyToValue = new HashMap<>();
        // Accès à l'objet courant/parent
        this.valueToKey = new HashMap<>();
        // Accès à l'objet courant/parent
        this.packById = new ArrayList<>();
        // Tags are always mutable across the lock.
        // Accès à l'objet courant/parent
        this.tags = new ConcurrentHashMap<>();
    // Fin d'un bloc/d'une expression
    }

    // Used to create compressed registries
    // Instruction de code
    DynamicRegistryImpl(Key key, @Nullable Codec<T> codec, List<T> idToValue,
                        // Instruction de code
                        Map<RegistryKey<T>, Integer> keyToId, List<RegistryKey<T>> idToKey,
                        // Instruction de code
                        Map<Key, T> keyToValue, Map<T, RegistryKey<T>> valueToKey,
                        // Début d'une méthode/d'un bloc
                        List<DataPack> packById, Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags) {
        // Accès à l'objet courant/parent
        this.key = key;
        // Accès à l'objet courant/parent
        this.codec = codec;
        // Accès à l'objet courant/parent
        this.idToValue = idToValue;
        // Accès à l'objet courant/parent
        this.idToKey = idToKey;
        // Accès à l'objet courant/parent
        this.keyToId = keyToId;
        // Accès à l'objet courant/parent
        this.keyToValue = keyToValue;
        // Accès à l'objet courant/parent
        this.valueToKey = valueToKey;
        // Accès à l'objet courant/parent
        this.packById = packById;
        // Accès à l'objet courant/parent
        this.tags = tags;
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

    // Début d'une méthode/d'un bloc
    public @UnknownNullability Codec<T> codec() {
        // Renvoie une valeur à l'appelant
        return codec;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable T get(int id) {
        // Embranchement : vérifie une condition
        if (id < 0 || id >= idToValue.size())
            // Renvoie une valeur à l'appelant
            return null;
        // Renvoie une valeur à l'appelant
        return idToValue.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable T get(Key key) {
        // Renvoie une valeur à l'appelant
        return keyToValue.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<T> getKey(int id) {
        // Embranchement : vérifie une condition
        if (id < 0 || id >= idToKey.size())
            // Renvoie une valeur à l'appelant
            return null;
        // Renvoie une valeur à l'appelant
        return idToKey.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<T> getKey(T value) {
        // Renvoie une valeur à l'appelant
        return valueToKey.get(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable RegistryKey<T> getKey(Key key) {
        // Embranchement : vérifie une condition
        if (!keyToValue.containsKey(key))
            // Renvoie une valeur à l'appelant
            return null;
        // Renvoie une valeur à l'appelant
        return new RegistryKeyImpl<>(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getId(RegistryKey<T> key) {
        // Renvoie une valeur à l'appelant
        return keyToId.getOrDefault(key, -1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public RegistryKey<T> register(Key key, T object, DataPack pack) {
        // Embranchement : vérifie une condition
        if (isFrozen()) throw new UnsupportedOperationException(UNSAFE_REMOVE_MESSAGE);
        // Appelle une méthode
        Check.notNull(key, "Key cannot be null");
        // Appelle une méthode
        Check.notNull(object, "Object cannot be null");
        // Appelle une méthode
        Check.notNull(pack, "Pack cannot be null");

        // Affecte une valeur
        final RegistryKey<T> registryKey = new RegistryKeyImpl<>(key);
        // Début d'une méthode/d'un bloc
        synchronized (REGISTRY_LOCK) {
            // Affecte une valeur
            Integer id = keyToId.get(registryKey); // Array set at home
            // Appelle une méthode
            keyToValue.put(key, object);
            // Appelle une méthode
            valueToKey.put(object, registryKey);
            // Embranchement : vérifie une condition
            if (id == null) {
                // Appelle une méthode
                idToValue.add(object);
                // Appelle une méthode
                idToKey.add(registryKey);
                // Appelle une méthode
                keyToId.put(registryKey, idToValue.size() - 1);
                // Appelle une méthode
                packById.add(pack);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                idToValue.set(id, object);
                // Appelle une méthode
                idToKey.set(id, registryKey);
                // Appelle une méthode
                keyToId.put(registryKey, id);
                // Appelle une méthode
                packById.set(id, pack);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            vanillaRegistryDataPacket.invalidate();
            // Renvoie une valeur à l'appelant
            return registryKey;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean remove(Key key) throws UnsupportedOperationException {
        // Embranchement : vérifie une condition
        if (isFrozen()) throw new UnsupportedOperationException(UNSAFE_REMOVE_MESSAGE);
        // Appelle une méthode
        Check.notNull(key, "Key cannot be null");

        // Affecte une valeur
        final RegistryKey<T> registryKey = new RegistryKeyImpl<>(key);
        // Début d'une méthode/d'un bloc
        synchronized (REGISTRY_LOCK) {
            // Appelle une méthode
            Integer idObject = keyToId.get(registryKey);
            // Embranchement : vérifie une condition
            if (idObject == null) return false;
            // Affecte une valeur
            int id = idObject;

            // Remove value from all mappings (shifting down indices)
            // Appelle une méthode
            idToValue.remove(id);
            // Appelle une méthode
            idToKey.remove(registryKey);
            // Appelle une méthode
            keyToId.remove(registryKey);
            // Appelle une méthode
            var value = keyToValue.remove(key);
            // Appelle une méthode
            valueToKey.remove(value);
            // Appelle une méthode
            packById.remove(id);

            // Remove all references from tags
            // Boucle : répète un bloc
            for (final var tag : tags.values()) {
                // Appelle une méthode
                tag.remove(registryKey);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            vanillaRegistryDataPacket.invalidate();
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable DataPack getPack(int id) {
        // Embranchement : vérifie une condition
        if (id < 0 || id >= packById.size())
            // Renvoie une valeur à l'appelant
            return null;
        // Renvoie une valeur à l'appelant
        return packById.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int size() {
        // Renvoie une valeur à l'appelant
        return idToValue.size();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<RegistryKey<T>> keys() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableCollection(idToKey);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<T> values() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableCollection(idToValue);
    // Fin d'un bloc/d'une expression
    }

    // Tags

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
    @Override // This method is called by a virtual thread in the configuration phase
    // Début d'une méthode/d'un bloc
    public SendablePacket registryDataPacket(Registries registries, boolean excludeVanilla) {
        // We cache the vanilla packet because that is by far the most common case. If some client claims not to have
        // the vanilla datapack we can compute the entire thing.
        // Embranchement : vérifie une condition
        if (excludeVanilla) {
            // Embranchement : vérifie une condition
            if (this.registries != registries) {
                // Instruction de code
                synchronized (REGISTRY_LOCK) { // Bootleg off the static lock for this mutation
                    // Embranchement : vérifie une condition
                    if (this.registries != registries) {
                        // Accès à l'objet courant/parent
                        this.registries = registries;
                        // Appelle une méthode
                        vanillaRegistryDataPacket.invalidate();
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return vanillaRegistryDataPacket;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return createRegistryDataPacket(registries, false);
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
            for (var registryKey : tag)
                // Appelle une méthode
                entries[i++] = keyToId.get(registryKey);
            // Appelle une méthode
            tagList.add(new TagsPacket.Tag(tag.key().key().asString(), entries));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new TagsPacket.Registry(key().asString(), tagList);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private RegistryDataPacket createRegistryDataPacket(Registries registries, boolean excludeVanilla) {
        // Appelle une méthode
        Check.notNull(codec, "Cannot create registry data packet for server-only registry");
        // Affecte une valeur
        Transcoder<BinaryTag> transcoder = new RegistryTranscoder<>(Transcoder.NBT, registries);
        // Copy to avoid concurrent modification issues while iterating, as we are not synchronized on the registry
        // Instruction de code
        final List<T> idToValue;
        // Instruction de code
        final List<DataPack> packById;
        // Embranchement : vérifie une condition
        if (!canFreeze()) {
            // Début d'une méthode/d'un bloc
            synchronized (REGISTRY_LOCK) {
                // Appelle une méthode
                idToValue = List.copyOf(this.idToValue);
                // Appelle une méthode
                packById = List.copyOf(this.packById);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            idToValue = this.idToValue;
            // Affecte une valeur
            packById = this.packById;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        List<RegistryDataPacket.Entry> entries = new ArrayList<>(idToValue.size());
        // Boucle : répète un bloc
        for (int i = 0; i < idToValue.size(); i++) {
            // Affecte une valeur
            CompoundBinaryTag data = null;
            // sorta todo, sorta just a note:
            // Right now we very much only support the minecraft:core (vanilla) 'pack'. Any entry which was not loaded
            // from static data will be treated as non vanilla and always sent completely. However, we really should
            // support arbitrary packs and associate all registry data with a datapack. Additionally, we should generate
            // all data for the experimental datapacks built in to vanilla such as the next update experimental (1.21 at
            // the time of writing). Datagen currently behaves kind of badly in that the registry inspecting generators
            // like material, block, etc generate entries which are behind feature flags, whereas the ones which inspect
            // static assets (the traditionally dynamic registries), do not generate those assets.
            // Appelle une méthode
            T entry = idToValue.get(i);
            // Appelle une méthode
            DataPack pack = packById.get(i);
            // Embranchement : vérifie une condition
            if (!excludeVanilla || pack != DataPack.MINECRAFT_CORE) {
                // Appelle une méthode
                final Result<BinaryTag> entryResult = codec.encode(transcoder, entry);
                // Embranchement : vérifie une condition
                if (entryResult instanceof Result.Ok(BinaryTag tag)) {
                    // Affecte une valeur
                    data = (CompoundBinaryTag) tag;
                // Branche alternative de la condition
                } else {
                    // Lève une exception
                    throw new IllegalStateException("Failed to encode registry entry " + i + " (" + getKey(i) + ") for registry " + key);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            //noinspection DataFlowIssue
            // Appelle une méthode
            entries.add(new RegistryDataPacket.Entry(getKey(i).key().asString(), data));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new RegistryDataPacket(key.asString(), entries);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to create a copy with compressed data structures.
     *
     * @return A safe copy of this registry
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    DynamicRegistryImpl<T> compact() {
        // Create new instances so they are trimmed to size without downcasting.
        // Renvoie une valeur à l'appelant
        return new DynamicRegistryImpl<>(key, codec,
                // Crée un nouvel objet
                new ArrayList<>(idToValue),
                // Crée un nouvel objet
                new HashMap<>(keyToId),
                // Crée un nouvel objet
                new ArrayList<>(idToKey),
                // Crée un nouvel objet
                new HashMap<>(keyToValue),
                // Crée un nouvel objet
                new HashMap<>(valueToKey),
                // Crée un nouvel objet
                new ArrayList<>(packById),
                // Crée un nouvel objet
                new ConcurrentHashMap<>(tags)
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean isFrozen() {
        // Renvoie une valeur à l'appelant
        return canFreeze() && MinecraftServer.process() != null && MinecraftServer.isStarted();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean canFreeze() {
        // Renvoie une valeur à l'appelant
        return !ServerFlag.REGISTRY_UNSAFE_OPS && !ServerFlag.INSIDE_TEST;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> void loadStaticJsonRegistry(@Nullable Registries registries, DynamicRegistryImpl<T> registry, RegistryData.Resource resource, @Nullable Comparator<String> idComparator, Codec<T> codec) {
        // Appelle une méthode
        Check.argCondition(!resource.fileName().endsWith(".json"), "Resource must be a JSON file: {0}", resource.fileName());
        // Gestion des exceptions
        try (InputStream resourceStream = RegistryData.loadRegistryFile(String.format("%s.json", registry.key().value()))) {
            // Appelle une méthode
            Check.notNull(resourceStream, "Resource {0} does not exist!", resource);
            // Appelle une méthode
            final JsonElement json = JsonUtil.fromJson(new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
            // Embranchement : vérifie une condition
            if (!(json instanceof JsonObject root))
                // Lève une exception
                throw new IllegalStateException("Failed to load registry " + registry.key() + ": expected a JSON object, got " + json);

            // Instruction de code
            final Transcoder<JsonElement> transcoder = registries != null ? new RegistryTranscoder<>(Transcoder.JSON, registries, false, true) : Transcoder.JSON;
            // Appelle une méthode
            List<Map.Entry<String, JsonElement>> entries = new ArrayList<>(root.entrySet());
            // Embranchement : vérifie une condition
            if (idComparator != null) entries.sort(Map.Entry.comparingByKey(idComparator));
            // Boucle : répète un bloc
            for (Map.Entry<String, JsonElement> entry : entries) {
                // Appelle une méthode
                final String namespace = entry.getKey();
                // Appelle une méthode
                final Result<T> valueResult = codec.decode(transcoder, entry.getValue());
                // Embranchement : vérifie une condition
                if (valueResult instanceof Result.Ok(T value)) {
                    // Appelle une méthode
                    registry.register(namespace, value, DataPack.MINECRAFT_CORE);
                // Branche alternative de la condition
                } else {
                    // Lève une exception
                    throw new IllegalStateException("Failed to decode registry entry " + namespace + " for registry " + registry.key() + ": " + valueResult);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Load tags if present
            // Appelle une méthode
            Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags = RegistryData.loadTags(registry.key());
            // Appelle une méthode
            registry.tags.putAll(tags);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
