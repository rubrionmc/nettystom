// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import com.google.gson.Gson;
// Import d'une classe nécessaire
import com.google.gson.GsonBuilder;
// Import d'une classe nécessaire
import com.google.gson.ToNumberPolicy;
// Import d'une classe nécessaire
import com.google.gson.stream.JsonReader;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.collision.CollisionUtils;
// Import d'une classe nécessaire
import net.minestom.server.collision.Shape;
// Import d'une classe nécessaire
import net.minestom.server.collision.ShapeImpl;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponentMap;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockEntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockSoundType;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Equippable;
// Import d'une classe nécessaire
import net.minestom.server.item.component.TypedCustomData;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ObjectArray;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.InputStream;
// Import d'une classe nécessaire
import java.io.InputStreamReader;
// Import d'une classe nécessaire
import java.nio.file.Files;
// Import d'une classe nécessaire
import java.nio.file.Path;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Handles registry data, used by {@link StaticProtocolObject} implementations and is strictly internal.
 * Use at your own risk.
 */
// Déclaration de type (classe/interface/enum/record)
public final class RegistryData {
    // Appelle une méthode
    static final Gson GSON = new GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().create();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static BlockEntry block(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new BlockEntry(namespace, main, new HashMap<>(), null, null);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static BlockEntry block(String namespace, Properties main, HashMap<Object, Object> internCache, @Nullable BlockEntry parent, @Nullable Properties parentProperties) {
        // Renvoie une valeur à l'appelant
        return new BlockEntry(namespace, main, internCache, parent, parentProperties);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static MaterialEntry material(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new MaterialEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static EntityEntry entity(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new EntityEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static VillagerProfessionEntry villagerProfession(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new VillagerProfessionEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static FeatureFlagEntry featureFlag(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new FeatureFlagEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static FluidEntry fluid(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new FluidEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static PotionEffectEntry potionEffect(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new PotionEffectEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static AttributeEntry attribute(String namespace, Properties main) {
        // Renvoie une valeur à l'appelant
        return new AttributeEntry(namespace, main);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static GameEventEntry gameEventEntry(String namespace, Properties properties) {
        // Renvoie une valeur à l'appelant
        return new GameEventEntry(namespace, properties);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static BlockSoundTypeEntry blockSoundTypeEntry(String namespace, Properties properties) {
        // Renvoie une valeur à l'appelant
        return new BlockSoundTypeEntry(namespace, properties);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param path The path without a leading slash, e.g. "blocks.json"
     */
    // Début d'une méthode/d'un bloc
    public static @Nullable InputStream loadRegistryFile(String path) throws IOException {
        // 1. Try to load from jar resources
        // Appelle une méthode
        InputStream resourceStream = RegistryData.class.getClassLoader().getResourceAsStream(path);

        // 2. Try to load from working directory
        // Appelle une méthode
        final Path filesystemPath = Path.of(path);
        // Embranchement : vérifie une condition
        if (resourceStream == null && Files.exists(filesystemPath)) {
            // Appelle une méthode
            resourceStream = Files.newInputStream(filesystemPath);
        // Fin d'un bloc/d'une expression
        }

        // 3. Not found :(
        // Renvoie une valeur à l'appelant
        return resourceStream;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static Properties load(String resourcePath, boolean required) {
        // Gestion des exceptions
        try (InputStream resourceStream = loadRegistryFile(resourcePath)) {
            // Embranchement : vérifie une condition
            if (resourceStream != null) {
                // Affecte une valeur
                final Map<String, Object> map = new HashMap<>();
                // Gestion des exceptions
                try (JsonReader reader = new JsonReader(new InputStreamReader(resourceStream))) {
                    // Appelle une méthode
                    reader.beginObject();
                    // Boucle : répète un bloc
                    while (reader.hasNext()) map.put(reader.nextName(), readObject(reader));
                    // Appelle une méthode
                    reader.endObject();
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return Properties.fromMap(map);
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(e);
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (required) Check.fail("Failed to load required registry file: {0}", resourcePath);
        // Renvoie une valeur à l'appelant
        return Properties.fromMap(Map.of());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Instantiates a static registry from a resource file. The resource file is resolved using the registryKey
     * first from the classpath, then from the working directory.
     *
     * <p>The data file should be at <code>/{registryKey.path()}.json</code></p>.
     *
     * <p>Tags will be loaded from <code>/tags/{registryKey.path()}.json</code></p>
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static <T extends StaticProtocolObject<T>> Registry<T> createStaticRegistry(Key registryKey, Loader<T> loader) {
        // Create the registry (data)
        // Appelle une méthode
        var entries = RegistryData.load(String.format("%s.json", registryKey.value()), true);
        // Appelle une méthode
        Map<Key, T> namespaces = new HashMap<>(entries.size());
        // Appelle une méthode
        ObjectArray<T> ids = ObjectArray.singleThread(entries.size());
        // Boucle : répète un bloc
        for (var entry : entries.asMap().keySet()) {
            // Appelle une méthode
            final Properties properties = entries.section(entry);
            // Appelle une méthode
            final T value = loader.get(entry, properties);
            // Appelle une méthode
            ids.set(value.id(), value);
            // Appelle une méthode
            namespaces.put(value.key(), value);
        // Fin d'un bloc/d'une expression
        }
        // Load tags if they exist
        // Appelle une méthode
        Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags = loadTags(registryKey);
        // Renvoie une valeur à l'appelant
        return new StaticRegistry<>(registryKey, namespaces, ids, tags);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> @Unmodifiable Map<TagKey<T>, RegistryTagImpl.Backed<T>> loadTags(Key registryKey) {
        // Appelle une méthode
        final var tagJson = RegistryData.load(String.format("tags/%s.json", registryKey.value()), false);
        // Appelle une méthode
        final HashMap<TagKey<T>, RegistryTagImpl.Backed<T>> tags = new HashMap<>(tagJson.size());
        // Boucle : répète un bloc
        for (String tagName : tagJson.asMap().keySet()) {
            // Appelle une méthode
            final TagKeyImpl<T> tagKey = new TagKeyImpl<>(Key.key(tagName));
            // Appelle une méthode
            final RegistryTagImpl.Backed<T> tagValue = tags.computeIfAbsent(tagKey, RegistryTagImpl.Backed::new);
            // Appelle une méthode
            getTagValues(tagValue, tagJson, tagName);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return Map.copyOf(tags);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> void getTagValues(RegistryTagImpl.Backed<T> tag, Properties main, String value) {
        // Appelle une méthode
        Properties section = main.section(value);
        // Appelle une méthode
        final List<String> tagValues = section.getList("values");
        // Début d'une méthode/d'un bloc
        tagValues.forEach(tagString -> {
            // Embranchement : vérifie une condition
            if (tagString.startsWith("#")) {
                // Appelle une méthode
                getTagValues(tag, main, tagString.substring(1));
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                tag.add(RegistryKey.unsafeOf(tagString));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public interface Loader<T extends StaticProtocolObject<T>> {
        // Appelle une méthode
        T get(String namespace, Properties properties);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Déclaration de type (classe/interface/enum/record)
    public enum Resource {
        // Dynamic Registries
        // Instruction de code
        BANNER_PATTERNS("banner_pattern.json"),
        // Instruction de code
        BIOMES("biome.json"),
        // Instruction de code
        CAT_VARIANTS("cat_variant.json"),
        // Instruction de code
        CHAT_TYPES("chat_type.json"),
        // Instruction de code
        CHICKEN_VARIANTS("chicken_variant.json"),
        // Instruction de code
        COW_VARIANTS("cow_variant.json"),
        // Instruction de code
        DAMAGE_TYPES("damage_type.json"),
        // Instruction de code
        DIALOGS("dialog.json"),
        // Instruction de code
        DIMENSION_TYPES("dimension_type.json"),
        // Instruction de code
        ENCHANTMENTS("enchantment.json"),
        // Instruction de code
        FROG_VARIANTS("frog_variant.json"),
        // Instruction de code
        JUKEBOX_SONGS("jukebox_song.json"),
        // Instruction de code
        INSTRUMENTS("instrument.json"),
        // Instruction de code
        PAINTING_VARIANTS("painting_variant.json"),
        // Instruction de code
        PIG_VARIANTS("pig_variant.json"),
        // Instruction de code
        TRIM_MATERIALS("trim_material.json"),
        // Instruction de code
        TRIM_PATTERNS("trim_pattern.json"),
        // Instruction de code
        WOLF_VARIANTS("wolf_variant.json"),
        // Instruction de code
        WOLF_SOUND_VARIANTS("wolf_sound_variant.json"),
        // Instruction de code
        ZOMBIE_NAUTILUS_VARIANTS("zombie_nautilus_variant.json"),
        // Appelle une méthode
        TIMELINES("timeline.json");

        // Instruction de code
        private final String name;

        // Début d'une méthode/d'un bloc
        Resource(String name) {
            // Accès à l'objet courant/parent
            this.name = name;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public String fileName() {
            // Renvoie une valeur à l'appelant
            return name;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record GameEventEntry(Key key, Properties main) implements Entry {
        // Début d'une méthode/d'un bloc
        public GameEventEntry(String key, Properties main) {
            // Appelle une méthode
            this(Key.key(key), main);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class BlockEntry implements Entry {
        // Affecte une valeur
        private static final byte AIR_OFFSET = 1 << 0;
        // Affecte une valeur
        private static final byte LIQUID_OFFSET = 1 << 1;
        // Affecte une valeur
        private static final byte SOLID_OFFSET = 1 << 2;
        // Affecte une valeur
        private static final byte OCCLUDES_OFFSET = 1 << 3;
        // Affecte une valeur
        private static final byte REQUIRES_TOOL_OFFSET = 1 << 4;
        // Affecte une valeur
        private static final byte REPLACEABLE_OFFSET = 1 << 5;
        // Affecte une valeur
        private static final byte REDSTONE_CONDUCTOR_OFFSET = 1 << 6;
        // Affecte une valeur
        private static final byte SIGNAL_SOURCE_OFFSET = -1 << 7; // 2's complement

        // Instruction de code
        private final Key key;
        // Instruction de code
        private final int id;
        // Instruction de code
        private final int stateId;
        // Instruction de code
        private final String translationKey;
        // Instruction de code
        private final float hardness;
        // Instruction de code
        private final float explosionResistance;
        // Instruction de code
        private final float friction;
        // Instruction de code
        private final float speedFactor;
        // Instruction de code
        private final float jumpFactor;
        // Instruction de code
        private final byte packedFlags;
        // Instruction de code
        private final byte lightEmission;
        // Instruction de code
        private final byte lightBlocked;
        // Instruction de code
        private final @Nullable BlockEntityType blockEntityType;
        // Instruction de code
        private final @Nullable Material material;
        // Instruction de code
        private final @Nullable BlockSoundType blockSoundType;
        // Instruction de code
        private final Shape collisionShape;
        // Instruction de code
        private final Shape occlusionShape;

        // Début d'une méthode/d'un bloc
        private BlockEntry(String namespace, Properties main, Map<Object, Object> internCache, @Nullable BlockEntry parent, @Nullable Properties parentProperties) {
            // Appelle une méthode
            assert parent == null || !main.asMap().isEmpty() : "BlockEntry cannot be empty if it has a parent";
            // Accès à l'objet courant/parent
            this.key = parent != null ? parent.key : Key.key(namespace);
            // Accès à l'objet courant/parent
            this.id = fromParent(parent, BlockEntry::id, main, "id", Properties::getInt, null);
            // Accès à l'objet courant/parent
            this.stateId = fromParent(parent, BlockEntry::stateId, main, "stateId", Properties::getInt, 0); // Parent doesnt have stateId; so we default to 0
            // Accès à l'objet courant/parent
            this.translationKey = fromParent(parent, BlockEntry::translationKey, main, "translationKey", Properties::getString, null);
            // Accès à l'objet courant/parent
            this.hardness = fromParent(parent, BlockEntry::hardness, main, "hardness", Properties::getFloat, null);
            // Accès à l'objet courant/parent
            this.explosionResistance = fromParent(parent, BlockEntry::explosionResistance, main, "explosionResistance", Properties::getFloat, null);
            // Accès à l'objet courant/parent
            this.friction = fromParent(parent, BlockEntry::friction, main, "friction", Properties::getFloat, 0.6f);
            // Accès à l'objet courant/parent
            this.speedFactor = fromParent(parent, BlockEntry::speedFactor, main, "speedFactor", Properties::getFloat, 1.0f);
            // Accès à l'objet courant/parent
            this.jumpFactor = fromParent(parent, BlockEntry::jumpFactor, main, "jumpFactor", Properties::getFloat, 1.0f);
            // Appelle une méthode
            var air = fromParent(parent, BlockEntry::isAir, main, "air", Properties::getBoolean, false);
            // Appelle une méthode
            var solid = fromParent(parent, BlockEntry::isSolid, main, "solid", Properties::getBoolean, null);
            // Appelle une méthode
            var liquid = fromParent(parent, BlockEntry::isLiquid, main, "liquid", Properties::getBoolean, false);
            // Appelle une méthode
            var occludes = fromParent(parent, BlockEntry::occludes, main, "occludes", Properties::getBoolean, true);
            // Appelle une méthode
            var requiresTool = fromParent(parent, BlockEntry::requiresTool, main, "requiresTool", Properties::getBoolean, true);
            // Accès à l'objet courant/parent
            this.lightEmission = fromParent(parent, BlockEntry::lightEmission, main, "lightEmission", Properties::getInt, 0).byteValue();
            // Accès à l'objet courant/parent
            this.lightBlocked = fromParent(parent, BlockEntry::lightBlocked, main, "lightBlock", Properties::getInt, 0).byteValue();
            // Appelle une méthode
            var replaceable = fromParent(parent, BlockEntry::isReplaceable, main, "replaceable", Properties::getBoolean, false);
            // Accès à l'objet courant/parent
            this.blockSoundType = fromParent(parent, BlockEntry::getBlockSoundType, main, "soundType", (properties, string) -> {
                // Appelle une méthode
                final String soundTypeKey = properties.getString(string);
                // Renvoie une valeur à l'appelant
                return soundTypeKey != null ? BlockSoundType.fromKey(soundTypeKey) : null;
            // Instruction de code
            }, null);
            // Début d'un bloc
            {
                // Appelle une méthode
                final Properties blockEntity = main.section("blockEntity");
                // Accès à l'objet courant/parent
                this.blockEntityType = fromParent(
                        // Instruction de code
                        parent, BlockEntry::blockEntityType, blockEntity, "namespace",
                        // Instruction de code
                        (properties, string) -> BlockEntityType.fromKey(properties.getString(string)),
                        // Instruction de code
                        null);
            // Fin d'un bloc/d'une expression
            }
            // Début d'un bloc
            {
                // Accès à l'objet courant/parent
                this.material = fromParent(parent, BlockEntry::material, main, "correspondingItem", (properties, string) -> {
                    // Appelle une méthode
                    final String materialNamespace = properties.getString(string);
                    // Renvoie une valeur à l'appelant
                    return materialNamespace != null ? Material.fromKey(materialNamespace) : null;
                // Instruction de code
                }, null);
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            { // Unique special case where the shape strings can mutate but arent saved after the parse.
                // Accès à l'objet courant/parent
                this.collisionShape = fromParent(parent, BlockEntry::collisionShape, main, "collisionShape", (properties, string) -> {
                    // Appelle une méthode
                    String shape = properties.getString(string);
                    // Renvoie une valeur à l'appelant
                    return CollisionUtils.parseCollisionShape(internCache, shape);
                // Instruction de code
                }, null);
                // Affecte une valeur
                Shape occludeShape = fromParent(parent, BlockEntry::occlusionShape, main, "occlusionShape", (properties, string) -> {
                    // Appelle une méthode
                    String shape = properties.getString(string);
                    // Embranchement : vérifie une condition
                    if (parent == null || parentProperties == null) // No parent, so we can just parse the shape
                        // Renvoie une valeur à l'appelant
                        return CollisionUtils.parseOcclusionShape(internCache, shape, occludes, this.lightEmission);
                    // Embranchement : vérifie une condition
                    if (shape != null || occludes != parent.occludes()) {
                        // Embranchement : vérifie une condition
                        if (shape == null) shape = parentProperties.getString(string);
                        // Renvoie une valeur à l'appelant
                        return CollisionUtils.parseOcclusionShape(internCache, shape, occludes, this.lightEmission);
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return parent.occlusionShape();
                // Instruction de code
                }, null);
                // Apply possible lightEmission override, since that isn't specified in occlusionShape
                // Embranchement : vérifie une condition
                if (parent != null && this.lightEmission != parent.lightEmission && occludeShape instanceof ShapeImpl shapeImpl) {
                    // Appelle une méthode
                    occludeShape = shapeImpl.withLightEmission(this.lightEmission);
                // Fin d'un bloc/d'une expression
                }
                // Accès à l'objet courant/parent
                this.occlusionShape = occludeShape;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            var redstoneConductor = fromParent(parent, BlockEntry::isRedstoneConductor, main, "redstoneConductor", Properties::getBoolean, null);
            // Appelle une méthode
            var signalSource = fromParent(parent, BlockEntry::isSignalSource, main, "signalSource", Properties::getBoolean, false);
            // Accès à l'objet courant/parent
            this.packedFlags = (byte) (
                    // Instruction de code
                    (air ? AIR_OFFSET : 0) |
                    // Instruction de code
                    (liquid ? LIQUID_OFFSET : 0) |
                    // Instruction de code
                    (solid ? SOLID_OFFSET : 0) |
                    // Instruction de code
                    (occludes ? OCCLUDES_OFFSET : 0) |
                    // Instruction de code
                    (requiresTool ? REQUIRES_TOOL_OFFSET : 0) |
                    // Instruction de code
                    (replaceable ? REPLACEABLE_OFFSET : 0) |
                    // Instruction de code
                    (redstoneConductor ? REDSTONE_CONDUCTOR_OFFSET : 0) |
                    // Instruction de code
                    (signalSource ? SIGNAL_SOURCE_OFFSET : 0)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private static <R>  R fromParent(@Nullable BlockEntry parent, Function<BlockEntry, R> parentProperty,
                                // Annotation pour l'élément suivant
                                @Nullable Properties main, String name, BiFunction<Properties, String, R> function,
                                // Annotation pour l'élément suivant
                                @Nullable R defaultValue) {
            // Affecte une valeur
            R value = null;
            // Embranchement : vérifie une condition
            if (main != null && main.containsKey(name)) {  // Required to have a nullable properties method
                // Appelle une méthode
                value = function.apply(main, name);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (value == null) {
                // Embranchement : vérifie une condition
                if (parent != null) {
                    // If the value is not present in the current properties, we fallback to the parent property
                    // Appelle une méthode
                    value = parentProperty.apply(parent);
                // Branche alternative de la condition
                } else {
                    // Affecte une valeur
                    value = defaultValue;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (value != defaultValue) Check.notNull(value, "{0}->{1} cannot be null", parent, name);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Key key() {
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return id;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int stateId() {
            // Renvoie une valeur à l'appelant
            return stateId;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public String translationKey() {
            // Renvoie une valeur à l'appelant
            return translationKey;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float hardness() {
            // Renvoie une valeur à l'appelant
            return hardness;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float explosionResistance() {
            // Renvoie une valeur à l'appelant
            return explosionResistance;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float friction() {
            // Renvoie une valeur à l'appelant
            return friction;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float speedFactor() {
            // Renvoie une valeur à l'appelant
            return speedFactor;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public float jumpFactor() {
            // Renvoie une valeur à l'appelant
            return jumpFactor;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isAir() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & AIR_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isSolid() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & SOLID_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isLiquid() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & LIQUID_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean occludes() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & OCCLUDES_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean requiresTool() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & REQUIRES_TOOL_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int lightEmission() {
            // Renvoie une valeur à l'appelant
            return lightEmission;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int lightBlocked() {
            // Renvoie une valeur à l'appelant
            return lightBlocked;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isReplaceable() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & REPLACEABLE_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isBlockEntity() {
            // Renvoie une valeur à l'appelant
            return blockEntityType != null;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable BlockEntityType blockEntityType() {
            // Renvoie une valeur à l'appelant
            return blockEntityType;
        // Fin d'un bloc/d'une expression
        }

        /**
         * @deprecated Use {@link #blockEntityType}
         */
        // Annotation pour l'élément suivant
        @Deprecated
        // Début d'une méthode/d'un bloc
        public @Nullable Key blockEntity() {
            // Renvoie une valeur à l'appelant
            return blockEntityType != null ? blockEntityType.key() : null;
        // Fin d'un bloc/d'une expression
        }

        /**
         * @deprecated Use {@link #blockEntityType}
         */
        // Annotation pour l'élément suivant
        @Deprecated
        // Début d'une méthode/d'un bloc
        public int blockEntityId() {
            // Renvoie une valeur à l'appelant
            return blockEntityType != null ? blockEntityType.id() : -1;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable Material material() {
            // Renvoie une valeur à l'appelant
            return material;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isRedstoneConductor() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & REDSTONE_CONDUCTOR_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isSignalSource() {
            // Renvoie une valeur à l'appelant
            return (packedFlags & SIGNAL_SOURCE_OFFSET) != 0;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Shape collisionShape() {
            // Renvoie une valeur à l'appelant
            return collisionShape;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Shape occlusionShape() {
            // Renvoie une valeur à l'appelant
            return occlusionShape;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable BlockSoundType getBlockSoundType() {
            // Renvoie une valeur à l'appelant
            return this.blockSoundType;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class MaterialEntry implements Entry {
        // Instruction de code
        private final Key key;
        // Instruction de code
        private final int id;
        // Instruction de code
        private final String translationKey;
        // Instruction de code
        private final Supplier<Block> blockSupplier;
        // Instruction de code
        private @Nullable Either<Properties, DataComponentMap> prototype;

        // Début d'une méthode/d'un bloc
        private MaterialEntry(String namespace, Properties main) {
            // Accès à l'objet courant/parent
            this.prototype = Either.left(main.section("components"));
            // Accès à l'objet courant/parent
            this.key = Key.key(namespace);
            // Accès à l'objet courant/parent
            this.id = main.getInt("id");
            // Accès à l'objet courant/parent
            this.translationKey = main.getString("translationKey");
            // Début d'un bloc
            {
                // Appelle une méthode
                final String blockNamespace = main.getString("correspondingBlock", null);
                // Accès à l'objet courant/parent
                this.blockSupplier = blockNamespace != null ? () -> Block.fromKey(blockNamespace) : () -> null;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Key key() {
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return id;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public String translationKey() {
            // Renvoie une valeur à l'appelant
            return translationKey;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable Block block() {
            // Renvoie une valeur à l'appelant
            return blockSupplier.get();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public DataComponentMap prototype() {
            // Embranchement : vérifie une condition
            if (prototype instanceof Either.Left(var components)) {
                // Appelle une méthode
                final Transcoder<Object> coder = new RegistryTranscoder<>(Transcoder.JAVA, MinecraftServer.process());
                // Appelle une méthode
                DataComponentMap.Builder builder = DataComponentMap.builder();
                // Boucle : répète un bloc
                for (Map.Entry<String, Object> entry : components) {
                    //noinspection unchecked
                    // Appelle une méthode
                    DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(entry.getKey());
                    // Appelle une méthode
                    Check.notNull(component, "Unknown component {0} in {1}", entry.getKey(), key);

                    // Appelle une méthode
                    final Result<Object> result = component.decode(coder, entry.getValue());
                    // Embranchement multiple (switch/case)
                    switch (result) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok(Object ok) -> builder.set(component, ok);
                        // Embranchement multiple (switch/case)
                        case Result.Error(String message) ->
                                // Lève une exception
                                throw new IllegalStateException("Failed to decode component " + entry.getKey() + " in " + key + ": " + message);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                final DataComponentMap prototype = builder.build();
                // Accès à l'objet courant/parent
                this.prototype = !prototype.isEmpty() ? Either.right(prototype) : null;
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return prototype instanceof Either.Right(var dataComponentMap) ? dataComponentMap : DataComponentMap.EMPTY;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean isArmor() {
            // Appelle une méthode
            final Equippable equippableComponent = prototype().get(DataComponents.EQUIPPABLE);
            // Appelle une méthode
            final EquipmentSlot equipmentSlot = equippableComponent == null ? null : equippableComponent.slot();
            // Renvoie une valeur à l'appelant
            return equipmentSlot != null && equipmentSlot.isArmor();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable EquipmentSlot equipmentSlot() {
            // Appelle une méthode
            final Equippable equippableComponent = prototype().get(DataComponents.EQUIPPABLE);
            // Renvoie une valeur à l'appelant
            return equippableComponent == null ? null : equippableComponent.slot();
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the entity type this item can spawn. Only present for spawn eggs (e.g. wolf spawn egg, skeleton spawn egg)
         *
         * @return The entity type it can spawn, or null if it is not a spawn egg
         * @deprecated Read {@link DataComponents#ENTITY_DATA} for the spawned entity data.
         */
        // Annotation pour l'élément suivant
        @Deprecated(forRemoval = true)
        // Début d'une méthode/d'un bloc
        public @Nullable EntityType spawnEntityType() {
            // Appelle une méthode
            TypedCustomData<EntityType> entityData = prototype().get(DataComponents.ENTITY_DATA);
            // Renvoie une valeur à l'appelant
            return entityData == null ? null : entityData.type();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class EntityEntry implements Entry {
        // Instruction de code
        private final Key key;
        // Instruction de code
        private final int id;
        // Instruction de code
        private final String translationKey;
        // Instruction de code
        private final double drag;
        // Instruction de code
        private final double acceleration;
        // Instruction de code
        private final boolean isLiving;
        // Instruction de code
        private final double width;
        // Instruction de code
        private final double height;
        // Instruction de code
        private final double eyeHeight;
        // Instruction de code
        private final int clientTrackingRange;
        // Instruction de code
        private final boolean fireImmune;
        // Instruction de code
        private final Map<String, List<Double>> entityOffsets;
        // Instruction de code
        private final BoundingBox boundingBox;

        // Début d'une méthode/d'un bloc
        public EntityEntry(String namespace, Properties main) {
            // Accès à l'objet courant/parent
            this.key = Key.key(namespace);
            // Accès à l'objet courant/parent
            this.id = main.getInt("id");
            // Accès à l'objet courant/parent
            this.translationKey = main.getString("translationKey");
            // Accès à l'objet courant/parent
            this.drag = main.getDouble("drag", 0.02);
            // Accès à l'objet courant/parent
            this.acceleration = main.getDouble("acceleration", 0.08);
            // Appelle une méthode
            final String packetType = main.getString("packetType").toUpperCase(Locale.ROOT);
            // Accès à l'objet courant/parent
            this.isLiving = "LIVING".equals(packetType) || "PLAYER".equals(packetType);
            // Accès à l'objet courant/parent
            this.fireImmune = main.getBoolean("fireImmune", false);
            // Accès à l'objet courant/parent
            this.clientTrackingRange = main.getInt("clientTrackingRange");

            // Dimensions
            // Accès à l'objet courant/parent
            this.width = main.getDouble("width");
            // Accès à l'objet courant/parent
            this.height = main.getDouble("height");
            // Accès à l'objet courant/parent
            this.eyeHeight = main.getDouble("eyeHeight");
            // Accès à l'objet courant/parent
            this.boundingBox = new BoundingBox(this.width, this.height, this.width);

            // Attachments
            // Affecte une valeur
            Map<String, List<Double>> entityOffsets = new HashMap<>();
            // Appelle une méthode
            Properties attachments = main.section("attachments");
            // Embranchement : vérifie une condition
            if (attachments != null) {
                // Appelle une méthode
                var allAttachments = attachments.asMap().keySet();
                // Boucle : répète un bloc
                for (String key : allAttachments) {
                    // Appelle une méthode
                    List<List<Double>> offset = attachments.getList(key);
                    // Instruction de code
                    entityOffsets.put(key, offset.getFirst()); // It's an array of an array with a single element, as of 1.21.3 we only need to grab a single array of 3 doubles
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.entityOffsets = Map.copyOf(entityOffsets);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Key key() {
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return id;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public String translationKey() {
            // Renvoie une valeur à l'appelant
            return translationKey;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double drag() {
            // Renvoie une valeur à l'appelant
            return drag;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double acceleration() {
            // Renvoie une valeur à l'appelant
            return acceleration;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double horizontalAirResistance() {
            // Renvoie une valeur à l'appelant
            return isLiving ? 0.91 : 0.98;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double verticalAirResistance() {
            // Renvoie une valeur à l'appelant
            return 1 - drag();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean shouldSendAttributes() {
            // Renvoie une valeur à l'appelant
            return isLiving;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double width() {
            // Renvoie une valeur à l'appelant
            return width;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double height() {
            // Renvoie une valeur à l'appelant
            return height;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public double eyeHeight() {
            // Renvoie une valeur à l'appelant
            return eyeHeight;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public boolean fireImmune() {
            // Renvoie une valeur à l'appelant
            return fireImmune;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int clientTrackingRange() {
            // Renvoie une valeur à l'appelant
            return clientTrackingRange;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Gets the entity attachment by name. Typically, will be PASSENGER or VEHICLE, but some entities have custom attachments (e.g. WARDEN_CHEST, NAMETAG)
         *
         * @param attachmentName The attachment to retrieve
         * @return A list of 3 doubles if the attachment is defined for this entity, or null if it is not defined
         */
        // Début d'une méthode/d'un bloc
        public @Nullable List<Double> entityAttachment(String attachmentName) {
            // Renvoie une valeur à l'appelant
            return entityOffsets.get(attachmentName);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public BoundingBox boundingBox() {
            // Renvoie une valeur à l'appelant
            return boundingBox;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static final class VillagerProfessionEntry implements Entry {
        // Instruction de code
        private final Key key;
        // Instruction de code
        private final int id;
        // Instruction de code
        private final SoundEvent workSound;

        // Début d'une méthode/d'un bloc
        public VillagerProfessionEntry(String namespace, Properties main) {
            // Accès à l'objet courant/parent
            this.key = Key.key(namespace);
            // Accès à l'objet courant/parent
            this.id = main.getInt("id");
            // Embranchement : vérifie une condition
            if (main.containsKey("workSound")) {
                // Accès à l'objet courant/parent
                this.workSound = SoundEvent.fromKey(main.getString("workSound"));
            // Branche alternative de la condition
            } else {
                // Accès à l'objet courant/parent
                this.workSound = null;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Key key() {
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return id;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable SoundEvent workSound() {
            // Renvoie une valeur à l'appelant
            return workSound;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record FeatureFlagEntry(Key key, int id) implements Entry {
        // Début d'une méthode/d'un bloc
        public FeatureFlagEntry(String namespace, Properties main) {
            // Appelle une méthode
            this(Key.key(namespace), main.getInt("id"));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record FluidEntry(Key key, int id) implements Entry {
        // Début d'une méthode/d'un bloc
        public FluidEntry(String namespace, Properties main) {
            // Appelle une méthode
            this(Key.key(namespace), main.getInt("id"));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record PotionEffectEntry(Key key, int id,
                                    // Instruction de code
                                    String translationKey,
                                    // Instruction de code
                                    int color,
                                    // Début d'une méthode/d'un bloc
                                    boolean isInstantaneous) implements Entry {
        // Début d'une méthode/d'un bloc
        public PotionEffectEntry(String namespace, Properties main) {
            // Instruction de code
            this(Key.key(namespace),
                    // Instruction de code
                    main.getInt("id"),
                    // Instruction de code
                    main.getString("translationKey"),
                    // Instruction de code
                    main.getInt("color"),
                    // Appelle une méthode
                    main.getBoolean("instantaneous"));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record AttributeEntry(Key key, int id,
                                 // Instruction de code
                                 String translationKey, double defaultValue,
                                 // Instruction de code
                                 boolean clientSync,
                                 // Boucle : répète un bloc
                                 double maxValue, double minValue) implements Entry {
        // Début d'une méthode/d'un bloc
        public AttributeEntry(String namespace, Properties main) {
            // Instruction de code
            this(Key.key(namespace),
                    // Instruction de code
                    main.getInt("id"),
                    // Instruction de code
                    main.getString("translationKey"),
                    // Instruction de code
                    main.getDouble("defaultValue"),
                    // Instruction de code
                    main.getBoolean("clientSync"),
                    // Instruction de code
                    main.getDouble("maxValue"),
                    // Appelle une méthode
                    main.getDouble("minValue"));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record BlockSoundTypeEntry(Key key, float volume, float pitch,
                                      // Instruction de code
                                      SoundEvent breakSound, SoundEvent hitSound, SoundEvent fallSound,
                                      // Début d'une méthode/d'un bloc
                                      SoundEvent placeSound, SoundEvent stepSound) {
        // Début d'une méthode/d'un bloc
        public BlockSoundTypeEntry(String namespace, Properties main) {
            // Instruction de code
            this(Key.key(namespace), main.getFloat("volume"),
                    // Instruction de code
                    main.getFloat("pitch"), SoundEvent.fromKey(main.getString("breakSound")), SoundEvent.fromKey(main.getString("hitSound")),
                    // Appelle une méthode
                    SoundEvent.fromKey(main.getString("fallSound")), SoundEvent.fromKey(main.getString("placeSound")), SoundEvent.fromKey(main.getString("stepSound")));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public interface Entry {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Object readObject(JsonReader reader) throws IOException {
        // Renvoie une valeur à l'appelant
        return switch (reader.peek()) {
            // Embranchement multiple (switch/case)
            case BEGIN_ARRAY -> {
                // Affecte une valeur
                List<Object> list = new ArrayList<>();
                // Appelle une méthode
                reader.beginArray();
                // Boucle : répète un bloc
                while (reader.hasNext()) list.add(readObject(reader));
                // Appelle une méthode
                reader.endArray();
                // Appelle une méthode
                yield List.copyOf(list);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case BEGIN_OBJECT -> {
                // Affecte une valeur
                Map<String, Object> map = new HashMap<>();
                // Appelle une méthode
                reader.beginObject();
                // Boucle : répète un bloc
                while (reader.hasNext()) map.put(reader.nextName(), readObject(reader));
                // Appelle une méthode
                reader.endObject();
                // Appelle une méthode
                yield Map.copyOf(map);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case STRING -> reader.nextString();
            // Embranchement multiple (switch/case)
            case NUMBER -> ToNumberPolicy.LONG_OR_DOUBLE.readNumber(reader);
            // Embranchement multiple (switch/case)
            case BOOLEAN -> reader.nextBoolean();
            // Appelle une méthode
            default -> throw new IllegalStateException("Invalid peek: " + reader.peek());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PropertiesMap(Map<String, Object> map) implements Properties {
        // Début d'une méthode/d'un bloc
        PropertiesMap {
            // Appelle une méthode
            map = Map.copyOf(map);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public String getString(String name, String defaultValue) {
            // Appelle une méthode
            var element = element(name);
            // Renvoie une valeur à l'appelant
            return element != null ? (String) element : defaultValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public String getString(String name) {
            // Renvoie une valeur à l'appelant
            return element(name);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public double getDouble(String name, double defaultValue) {
            // Appelle une méthode
            var element = element(name);
            // Renvoie une valeur à l'appelant
            return element != null ? ((Number) element).doubleValue() : defaultValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public double getDouble(String name) {
            // Renvoie une valeur à l'appelant
            return ((Number) element(name)).doubleValue();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int getInt(String name, int defaultValue) {
            // Appelle une méthode
            var element = element(name);
            // Renvoie une valeur à l'appelant
            return element != null ? ((Number) element).intValue() : defaultValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int getInt(String name) {
            // Renvoie une valeur à l'appelant
            return ((Number) element(name)).intValue();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float getFloat(String name, float defaultValue) {
            // Appelle une méthode
            var element = element(name);
            // Renvoie une valeur à l'appelant
            return element != null ? ((Number) element).floatValue() : defaultValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float getFloat(String name) {
            // Renvoie une valeur à l'appelant
            return ((Number) element(name)).floatValue();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean getBoolean(String name, boolean defaultValue) {
            // Appelle une méthode
            var element = element(name);
            // Renvoie une valeur à l'appelant
            return element != null ? (boolean) element : defaultValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean getBoolean(String name) {
            // Renvoie une valeur à l'appelant
            return element(name);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <T> List<T> getList(String name, List<T> defaultValue) {
            // Appelle une méthode
            List<T> element = element(name);
            // Renvoie une valeur à l'appelant
            return element != null ? element : defaultValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Properties section(String name) {
            // Appelle une méthode
            Map<String, Object> map = element(name);
            // Embranchement : vérifie une condition
            if (map == null) return null;
            // Renvoie une valeur à l'appelant
            return new PropertiesMap(map);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean containsKey(String name) {
            // Renvoie une valeur à l'appelant
            return map.containsKey(name);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Map<String, Object> asMap() {
            // Renvoie une valeur à l'appelant
            return map;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private <T> T element(String name) {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return (T) map.get(name);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public String toString() {
            // Affecte une valeur
            AtomicReference<String> string = new AtomicReference<>("{ ");
            // Accès à l'objet courant/parent
            this.map.forEach((s, object) -> string.set(string.get() + " , " + "\"" + s + "\"" + " : " + "\"" + object.toString() + "\""));
            // Renvoie une valeur à l'appelant
            return string.updateAndGet(s -> s.replaceFirst(" , ", "") + "}");
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public interface Properties extends Iterable<Map.Entry<String, Object>> {
        // Début d'une méthode/d'un bloc
        static Properties fromMap(Map<String, Object> map) {
            // Renvoie une valeur à l'appelant
            return new PropertiesMap(map);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        String getString(String name, String defaultValue);

        // Appelle une méthode
        String getString(String name);

        // Boucle : répète un bloc
        double getDouble(String name, double defaultValue);

        // Boucle : répète un bloc
        double getDouble(String name);

        // Appelle une méthode
        int getInt(String name, int defaultValue);

        // Appelle une méthode
        int getInt(String name);

        // Appelle une méthode
        float getFloat(String name, float defaultValue);

        // Appelle une méthode
        float getFloat(String name);

        // Appelle une méthode
        boolean getBoolean(String name, boolean defaultValue);

        // Appelle une méthode
        boolean getBoolean(String name);

        // Appelle une méthode
        <T> List<T> getList(String name, List<T> defaultValue);

        // Début d'une méthode/d'un bloc
        default <T> List<T> getList(String name) {
            // Renvoie une valeur à l'appelant
            return getList(name, List.of());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Deprecated(forRemoval = true)
        // Début d'une méthode/d'un bloc
        default List<List<Double>> getNestedDoubleArray(String name) {
            // Renvoie une valeur à l'appelant
            return getList(name);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        Properties section(String name);

        // Appelle une méthode
        boolean containsKey(String name);

        // Appelle une méthode
        Map<String, Object> asMap();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        default Iterator<Map.Entry<String, Object>> iterator() {
            // Renvoie une valeur à l'appelant
            return asMap().entrySet().iterator();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default int size() {
            // Renvoie une valeur à l'appelant
            return asMap().size();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
