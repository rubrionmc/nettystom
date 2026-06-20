// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import com.google.gson.Gson;
// Import of a required class
import com.google.gson.GsonBuilder;
// Import of a required class
import com.google.gson.ToNumberPolicy;
// Import of a required class
import com.google.gson.stream.JsonReader;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.data.MinestomData;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.collision.CollisionUtils;
// Import of a required class
import net.minestom.server.collision.Shape;
// Import of a required class
import net.minestom.server.collision.ShapeImpl;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockEntityType;
// Import of a required class
import net.minestom.server.instance.block.BlockSoundType;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.item.component.Equippable;
// Import of a required class
import net.minestom.server.item.component.TypedCustomData;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.InputStream;
// Import of a required class
import java.io.InputStreamReader;
// Import of a required class
import java.nio.file.Files;
// Import of a required class
import java.nio.file.Path;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

/**
 * Handles registry data, used by {@link StaticProtocolObject} implementations and is strictly internal.
 * Use at your own risk.
 */
// Type declaration (class/interface/enum/record)
public final class RegistryData {
    // Calls a method
    static final Gson GSON = new GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().create();

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static BlockEntry block(String namespace, Properties main) {
        // Returns a value to the caller
        return new BlockEntry(namespace, main, new HashMap<>(), null, null);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static BlockEntry block(String namespace, Properties main, HashMap<Object, Object> internCache, @Nullable BlockEntry parent, @Nullable Properties parentProperties) {
        // Returns a value to the caller
        return new BlockEntry(namespace, main, internCache, parent, parentProperties);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static MaterialEntry material(String namespace, Properties main) {
        // Returns a value to the caller
        return new MaterialEntry(namespace, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static EntityEntry entity(String namespace, Properties main) {
        // Returns a value to the caller
        return new EntityEntry(namespace, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static VillagerProfessionEntry villagerProfession(String namespace, Properties main) {
        // Returns a value to the caller
        return new VillagerProfessionEntry(namespace, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static FeatureFlagEntry featureFlag(String namespace, Properties main) {
        // Returns a value to the caller
        return new FeatureFlagEntry(namespace, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static FluidEntry fluid(String namespace, Properties main) {
        // Returns a value to the caller
        return new FluidEntry(namespace, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static PotionEffectEntry potionEffect(String namespace, Properties main) {
        // Returns a value to the caller
        return new PotionEffectEntry(namespace, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static AttributeEntry attribute(String namespace, Properties main) {
        // Returns a value to the caller
        return new AttributeEntry(namespace, main);
    // End of a block/expression
    }

    // Start of a method/block
    public static GameEventEntry gameEventEntry(String namespace, Properties properties) {
        // Returns a value to the caller
        return new GameEventEntry(namespace, properties);
    // End of a block/expression
    }

    // Start of a method/block
    public static BlockSoundTypeEntry blockSoundTypeEntry(String namespace, Properties properties) {
        // Returns a value to the caller
        return new BlockSoundTypeEntry(namespace, properties);
    // End of a block/expression
    }

    /**
     * @param path The path without a leading slash, e.g. "blocks.json"
     */
    // Start of a method/block
    public static @Nullable InputStream loadRegistryFile(String path) throws IOException {
        // 1. Try to load from data resources
        // Calls a method
        InputStream resourceStream = MinestomData.resource(path);

        // 2. Try to load from working directory
        // Calls a method
        final Path filesystemPath = Path.of(path);
        // Branch: checks a condition
        if (resourceStream == null && Files.exists(filesystemPath)) {
            // Calls a method
            resourceStream = Files.newInputStream(filesystemPath);
        // End of a block/expression
        }

        // 3. Not found :(
        // Returns a value to the caller
        return resourceStream;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static Properties load(String resourcePath, boolean required) {
        // Exception handling
        try (InputStream resourceStream = loadRegistryFile(resourcePath)) {
            // Branch: checks a condition
            if (resourceStream != null) {
                // Calls a method
                final Map<String, Object> map = new HashMap<>();
                // Exception handling
                try (JsonReader reader = new JsonReader(new InputStreamReader(resourceStream))) {
                    // Calls a method
                    reader.beginObject();
                    // Loop: repeats a block
                    while (reader.hasNext()) map.put(reader.nextName(), readObject(reader));
                    // Calls a method
                    reader.endObject();
                // End of a block/expression
                }
                // Returns a value to the caller
                return Properties.fromMap(map);
            // End of a block/expression
            }
        // Start of a method/block
        } catch (IOException e) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(e);
        // End of a block/expression
        }
        // Branch: checks a condition
        if (required) Check.fail("Failed to load required registry file: {0}", resourcePath);
        // Returns a value to the caller
        return Properties.fromMap(Map.of());
    // End of a block/expression
    }

    /**
     * Instantiates a static registry from a resource file. The resource file is resolved using the registryKey
     * first from the classpath, then from the working directory.
     *
     * <p>The data file should be at <code>/{registryKey.path()}.json</code></p>.
     *
     * <p>Tags will be loaded from <code>/tags/{registryKey.path()}.json</code></p>
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static <T extends StaticProtocolObject<T>> Registry<T> createStaticRegistry(Key registryKey, Loader<T> loader) {
        // Create the registry (data)
        // Calls a method
        var entries = RegistryData.load(String.format("%s.json", registryKey.value()), true);
        // Calls a method
        Map<Key, T> namespaces = new HashMap<>(entries.size());
        // Calls a method
        ObjectArray<T> ids = ObjectArray.singleThread(entries.size());
        // Loop: repeats a block
        for (var entry : entries.asMap().keySet()) {
            // Calls a method
            final Properties properties = entries.section(entry);
            // Calls a method
            final T value = loader.get(entry, properties);
            // Calls a method
            ids.set(value.id(), value);
            // Calls a method
            namespaces.put(value.key(), value);
        // End of a block/expression
        }
        // Load tags if they exist
        // Calls a method
        Map<TagKey<T>, RegistryTagImpl.Backed<T>> tags = loadTags(registryKey);
        // Returns a value to the caller
        return new StaticRegistry<>(registryKey, namespaces, ids, tags);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> @Unmodifiable Map<TagKey<T>, RegistryTagImpl.Backed<T>> loadTags(Key registryKey) {
        // Calls a method
        final var tagJson = RegistryData.load(String.format("tags/%s.json", registryKey.value()), false);
        // Calls a method
        final HashMap<TagKey<T>, RegistryTagImpl.Backed<T>> tags = new HashMap<>(tagJson.size());
        // Loop: repeats a block
        for (String tagName : tagJson.asMap().keySet()) {
            // Calls a method
            final TagKeyImpl<T> tagKey = new TagKeyImpl<>(Key.key(tagName));
            // Calls a method
            final RegistryTagImpl.Backed<T> tagValue = tags.computeIfAbsent(tagKey, RegistryTagImpl.Backed::new);
            // Calls a method
            getTagValues(tagValue, tagJson, tagName);
        // End of a block/expression
        }
        // Returns a value to the caller
        return Map.copyOf(tags);
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> void getTagValues(RegistryTagImpl.Backed<T> tag, Properties main, String value) {
        // Calls a method
        Properties section = main.section(value);
        // Calls a method
        final List<String> tagValues = section.getList("values");
        // Start of a method/block
        tagValues.forEach(tagString -> {
            // Branch: checks a condition
            if (tagString.startsWith("#")) {
                // Calls a method
                getTagValues(tag, main, tagString.substring(1));
            // Alternative branch of the condition
            } else {
                // Calls a method
                tag.add(RegistryKey.unsafeOf(tagString));
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public interface Loader<T extends StaticProtocolObject<T>> {
        // Calls a method
        T get(String namespace, Properties properties);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Type declaration (class/interface/enum/record)
    public enum Resource {
        // Dynamic Registries
        // Code statement
        BANNER_PATTERNS("banner_pattern.json"),
        // Code statement
        BIOMES("biome.json"),
        // Code statement
        CAT_VARIANTS("cat_variant.json"),
        // Code statement
        CAT_SOUND_VARIANTS("cat_sound_variant.json"),
        // Code statement
        CHAT_TYPES("chat_type.json"),
        // Code statement
        CHICKEN_VARIANTS("chicken_variant.json"),
        // Code statement
        CHICKEN_SOUND_VARIANTS("chicken_sound_variant.json"),
        // Code statement
        COW_VARIANTS("cow_variant.json"),
        // Code statement
        COW_SOUND_VARIANTS("cow_sound_variant.json"),
        // Code statement
        DAMAGE_TYPES("damage_type.json"),
        // Code statement
        DIALOGS("dialog.json"),
        // Code statement
        DIMENSION_TYPES("dimension_type.json"),
        // Code statement
        ENCHANTMENTS("enchantment.json"),
        // Code statement
        FROG_VARIANTS("frog_variant.json"),
        // Code statement
        JUKEBOX_SONGS("jukebox_song.json"),
        // Code statement
        INSTRUMENTS("instrument.json"),
        // Code statement
        PAINTING_VARIANTS("painting_variant.json"),
        // Code statement
        PIG_VARIANTS("pig_variant.json"),
        // Code statement
        PIG_SOUND_VARIANTS("pig_sound_variant.json"),
        // Code statement
        TRIM_MATERIALS("trim_material.json"),
        // Code statement
        TRIM_PATTERNS("trim_pattern.json"),
        // Code statement
        WOLF_VARIANTS("wolf_variant.json"),
        // Code statement
        WOLF_SOUND_VARIANTS("wolf_sound_variant.json"),
        // Code statement
        ZOMBIE_NAUTILUS_VARIANTS("zombie_nautilus_variant.json"),
        // Code statement
        TIMELINES("timeline.json"),
        // Calls a method
        WORLD_CLOCKS("world_clock.json");

        // Code statement
        private final String name;

        // Start of a method/block
        Resource(String name) {
            // Access to the current/parent object
            this.name = name;
        // End of a block/expression
        }

        // Start of a method/block
        public String fileName() {
            // Returns a value to the caller
            return name;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record GameEventEntry(Key key, Properties main) implements Entry {
        // Start of a method/block
        public GameEventEntry(String key, Properties main) {
            // Calls a method
            this(Key.key(key), main);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static final class BlockEntry implements Entry {
        // Assigns a value
        private static final byte AIR_OFFSET = 1 << 0;
        // Assigns a value
        private static final byte LIQUID_OFFSET = 1 << 1;
        // Assigns a value
        private static final byte SOLID_OFFSET = 1 << 2;
        // Assigns a value
        private static final byte OCCLUDES_OFFSET = 1 << 3;
        // Assigns a value
        private static final byte REQUIRES_TOOL_OFFSET = 1 << 4;
        // Assigns a value
        private static final byte REPLACEABLE_OFFSET = 1 << 5;
        // Assigns a value
        private static final byte REDSTONE_CONDUCTOR_OFFSET = 1 << 6;
        // Assigns a value
        private static final byte SIGNAL_SOURCE_OFFSET = -1 << 7; // 2's complement

        // Code statement
        private final Key key;
        // Code statement
        private final int id;
        // Code statement
        private final int stateId;
        // Code statement
        private final String translationKey;
        // Code statement
        private final float hardness;
        // Code statement
        private final float explosionResistance;
        // Code statement
        private final float friction;
        // Code statement
        private final float speedFactor;
        // Code statement
        private final float jumpFactor;
        // Code statement
        private final int mapColorId;
        // Code statement
        private final byte packedFlags;
        // standalone field rather than a packedFlags bit: that byte is already full (8 flags; SIGNAL_SOURCE uses bit 7).
        // fold into a wider flags field if the flag set ever grows - changed here if needed.
        // Code statement
        private final boolean blocksMotion;
        // Code statement
        private final byte lightEmission;
        // Code statement
        private final byte lightBlocked;
        // Code statement
        private final @Nullable BlockEntityType blockEntityType;
        // Code statement
        private final @Nullable Material material;
        // Code statement
        private final @Nullable BlockSoundType blockSoundType;
        // Code statement
        private final Shape collisionShape;
        // Code statement
        private final Shape occlusionShape;

        // Start of a method/block
        private BlockEntry(String namespace, Properties main, Map<Object, Object> internCache, @Nullable BlockEntry parent, @Nullable Properties parentProperties) {
            // Calls a method
            assert parent == null || !main.asMap().isEmpty() : "BlockEntry cannot be empty if it has a parent";
            // Access to the current/parent object
            this.key = parent != null ? parent.key : Key.key(namespace);
            // Access to the current/parent object
            this.id = fromParent(parent, BlockEntry::id, main, "id", Properties::getInt, null);
            // Access to the current/parent object
            this.stateId = fromParent(parent, BlockEntry::stateId, main, "stateId", Properties::getInt, 0); // Parent doesnt have stateId; so we default to 0
            // Access to the current/parent object
            this.translationKey = fromParent(parent, BlockEntry::translationKey, main, "translationKey", Properties::getString, null);
            // Access to the current/parent object
            this.hardness = fromParent(parent, BlockEntry::hardness, main, "hardness", Properties::getFloat, null);
            // Access to the current/parent object
            this.explosionResistance = fromParent(parent, BlockEntry::explosionResistance, main, "explosionResistance", Properties::getFloat, null);
            // Access to the current/parent object
            this.friction = fromParent(parent, BlockEntry::friction, main, "friction", Properties::getFloat, 0.6f);
            // Access to the current/parent object
            this.speedFactor = fromParent(parent, BlockEntry::speedFactor, main, "speedFactor", Properties::getFloat, 1.0f);
            // Access to the current/parent object
            this.jumpFactor = fromParent(parent, BlockEntry::jumpFactor, main, "jumpFactor", Properties::getFloat, 1.0f);
            // Access to the current/parent object
            this.mapColorId = fromParent(parent, BlockEntry::mapColorId, main, "mapColorId", Properties::getInt, 0);
            // Calls a method
            var air = fromParent(parent, BlockEntry::isAir, main, "air", Properties::getBoolean, false);
            // Calls a method
            var solid = fromParent(parent, BlockEntry::isSolid, main, "solid", Properties::getBoolean, null);
            // Access to the current/parent object
            this.blocksMotion = fromParent(parent, BlockEntry::blocksMotion, main, "blocksMotion", Properties::getBoolean, false);
            // Calls a method
            var liquid = fromParent(parent, BlockEntry::isLiquid, main, "liquid", Properties::getBoolean, false);
            // Calls a method
            var occludes = fromParent(parent, BlockEntry::occludes, main, "occludes", Properties::getBoolean, true);
            // Calls a method
            var requiresTool = fromParent(parent, BlockEntry::requiresTool, main, "requiresTool", Properties::getBoolean, true);
            // Access to the current/parent object
            this.lightEmission = fromParent(parent, BlockEntry::lightEmission, main, "lightEmission", Properties::getInt, 0).byteValue();
            // Access to the current/parent object
            this.lightBlocked = fromParent(parent, BlockEntry::lightBlocked, main, "lightBlock", Properties::getInt, 0).byteValue();
            // Calls a method
            var replaceable = fromParent(parent, BlockEntry::isReplaceable, main, "replaceable", Properties::getBoolean, false);
            // Access to the current/parent object
            this.blockSoundType = fromParent(parent, BlockEntry::getBlockSoundType, main, "soundType", (properties, string) -> {
                // Calls a method
                final String soundTypeKey = properties.getString(string);
                // Returns a value to the caller
                return soundTypeKey != null ? BlockSoundType.fromKey(soundTypeKey) : null;
            // Code statement
            }, null);
            // Start of a block
            {
                // Calls a method
                final Properties blockEntity = main.section("blockEntity");
                // Access to the current/parent object
                this.blockEntityType = fromParent(
                        // Code statement
                        parent, BlockEntry::blockEntityType, blockEntity, "namespace",
                        // Code statement
                        (properties, string) -> BlockEntityType.fromKey(properties.getString(string)),
                        // Code statement
                        null);
            // End of a block/expression
            }
            // Start of a block
            {
                // Access to the current/parent object
                this.material = fromParent(parent, BlockEntry::material, main, "correspondingItem", (properties, string) -> {
                    // Calls a method
                    final String materialNamespace = properties.getString(string);
                    // Returns a value to the caller
                    return materialNamespace != null ? Material.fromKey(materialNamespace) : null;
                // Code statement
                }, null);
            // End of a block/expression
            }
            // Code statement
            { // Unique special case where the shape strings can mutate but arent saved after the parse.
                // Access to the current/parent object
                this.collisionShape = fromParent(parent, BlockEntry::collisionShape, main, "collisionShape", (properties, string) -> {
                    // Calls a method
                    String shape = properties.getString(string);
                    // Returns a value to the caller
                    return CollisionUtils.parseCollisionShape(internCache, shape);
                // Code statement
                }, null);
                // Assigns a value
                Shape occludeShape = fromParent(parent, BlockEntry::occlusionShape, main, "occlusionShape", (properties, string) -> {
                    // Calls a method
                    String shape = properties.getString(string);
                    // Branch: checks a condition
                    if (parent == null || parentProperties == null) // No parent, so we can just parse the shape
                        // Returns a value to the caller
                        return CollisionUtils.parseOcclusionShape(internCache, shape, occludes, this.lightEmission);
                    // Branch: checks a condition
                    if (shape != null || occludes != parent.occludes()) {
                        // Branch: checks a condition
                        if (shape == null) shape = parentProperties.getString(string);
                        // Returns a value to the caller
                        return CollisionUtils.parseOcclusionShape(internCache, shape, occludes, this.lightEmission);
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return parent.occlusionShape();
                // Code statement
                }, null);
                // Apply possible lightEmission override, since that isn't specified in occlusionShape
                // Branch: checks a condition
                if (parent != null && this.lightEmission != parent.lightEmission && occludeShape instanceof ShapeImpl shapeImpl) {
                    // Calls a method
                    occludeShape = shapeImpl.withLightEmission(this.lightEmission);
                // End of a block/expression
                }
                // Access to the current/parent object
                this.occlusionShape = occludeShape;
            // End of a block/expression
            }
            // Calls a method
            var redstoneConductor = fromParent(parent, BlockEntry::isRedstoneConductor, main, "redstoneConductor", Properties::getBoolean, null);
            // Calls a method
            var signalSource = fromParent(parent, BlockEntry::isSignalSource, main, "signalSource", Properties::getBoolean, false);
            // Access to the current/parent object
            this.packedFlags = (byte) (
                    // Code statement
                    (air ? AIR_OFFSET : 0) |
                    // Code statement
                    (liquid ? LIQUID_OFFSET : 0) |
                    // Code statement
                    (solid ? SOLID_OFFSET : 0) |
                    // Code statement
                    (occludes ? OCCLUDES_OFFSET : 0) |
                    // Code statement
                    (requiresTool ? REQUIRES_TOOL_OFFSET : 0) |
                    // Code statement
                    (replaceable ? REPLACEABLE_OFFSET : 0) |
                    // Code statement
                    (redstoneConductor ? REDSTONE_CONDUCTOR_OFFSET : 0) |
                    // Code statement
                    (signalSource ? SIGNAL_SOURCE_OFFSET : 0)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Code statement
        private static <R>  R fromParent(@Nullable BlockEntry parent, Function<BlockEntry, R> parentProperty,
                                // Annotation for the following element
                                @Nullable Properties main, String name, BiFunction<Properties, String, R> function,
                                // Annotation for the following element
                                @Nullable R defaultValue) {
            // Assigns a value
            R value = null;
            // Branch: checks a condition
            if (main != null && main.containsKey(name)) {  // Required to have a nullable properties method
                // Calls a method
                value = function.apply(main, name);
            // End of a block/expression
            }
            // Branch: checks a condition
            if (value == null) {
                // Branch: checks a condition
                if (parent != null) {
                    // If the value is not present in the current properties, we fallback to the parent property
                    // Calls a method
                    value = parentProperty.apply(parent);
                // Alternative branch of the condition
                } else {
                    // Assigns a value
                    value = defaultValue;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Branch: checks a condition
            if (value != defaultValue) Check.notNull(value, "{0}->{1} cannot be null", parent, name);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }

        // Start of a method/block
        public Key key() {
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }

        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return id;
        // End of a block/expression
        }

        // Start of a method/block
        public int stateId() {
            // Returns a value to the caller
            return stateId;
        // End of a block/expression
        }

        // Start of a method/block
        public String translationKey() {
            // Returns a value to the caller
            return translationKey;
        // End of a block/expression
        }

        // Start of a method/block
        public float hardness() {
            // Returns a value to the caller
            return hardness;
        // End of a block/expression
        }

        // Start of a method/block
        public float explosionResistance() {
            // Returns a value to the caller
            return explosionResistance;
        // End of a block/expression
        }

        // Start of a method/block
        public float friction() {
            // Returns a value to the caller
            return friction;
        // End of a block/expression
        }

        // Start of a method/block
        public float speedFactor() {
            // Returns a value to the caller
            return speedFactor;
        // End of a block/expression
        }

        // Start of a method/block
        public float jumpFactor() {
            // Returns a value to the caller
            return jumpFactor;
        // End of a block/expression
        }

        // Start of a method/block
        public int mapColorId() {
            // Returns a value to the caller
            return mapColorId;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isAir() {
            // Returns a value to the caller
            return (packedFlags & AIR_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isSolid() {
            // Returns a value to the caller
            return (packedFlags & SOLID_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean blocksMotion() {
            // Returns a value to the caller
            return blocksMotion;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isLiquid() {
            // Returns a value to the caller
            return (packedFlags & LIQUID_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean occludes() {
            // Returns a value to the caller
            return (packedFlags & OCCLUDES_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean requiresTool() {
            // Returns a value to the caller
            return (packedFlags & REQUIRES_TOOL_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public int lightEmission() {
            // Returns a value to the caller
            return lightEmission;
        // End of a block/expression
        }

        // Start of a method/block
        public int lightBlocked() {
            // Returns a value to the caller
            return lightBlocked;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isReplaceable() {
            // Returns a value to the caller
            return (packedFlags & REPLACEABLE_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isBlockEntity() {
            // Returns a value to the caller
            return blockEntityType != null;
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable BlockEntityType blockEntityType() {
            // Returns a value to the caller
            return blockEntityType;
        // End of a block/expression
        }

        /**
         * @deprecated Use {@link #blockEntityType}
         */
        // Annotation for the following element
        @Deprecated
        // Start of a method/block
        public @Nullable Key blockEntity() {
            // Returns a value to the caller
            return blockEntityType != null ? blockEntityType.key() : null;
        // End of a block/expression
        }

        /**
         * @deprecated Use {@link #blockEntityType}
         */
        // Annotation for the following element
        @Deprecated
        // Start of a method/block
        public int blockEntityId() {
            // Returns a value to the caller
            return blockEntityType != null ? blockEntityType.id() : -1;
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable Material material() {
            // Returns a value to the caller
            return material;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isRedstoneConductor() {
            // Returns a value to the caller
            return (packedFlags & REDSTONE_CONDUCTOR_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isSignalSource() {
            // Returns a value to the caller
            return (packedFlags & SIGNAL_SOURCE_OFFSET) != 0;
        // End of a block/expression
        }

        // Start of a method/block
        public Shape collisionShape() {
            // Returns a value to the caller
            return collisionShape;
        // End of a block/expression
        }

        // Start of a method/block
        public Shape occlusionShape() {
            // Returns a value to the caller
            return occlusionShape;
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable BlockSoundType getBlockSoundType() {
            // Returns a value to the caller
            return this.blockSoundType;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static final class MaterialEntry implements Entry {
        // Code statement
        private final Key key;
        // Code statement
        private final int id;
        // Code statement
        private final String translationKey;
        // Code statement
        private final Supplier<Block> blockSupplier;
        // Code statement
        private @Nullable Either<Properties, DataComponentMap> prototype;

        // Start of a method/block
        private MaterialEntry(String namespace, Properties main) {
            // Access to the current/parent object
            this.prototype = Either.left(main.section("components"));
            // Access to the current/parent object
            this.key = Key.key(namespace);
            // Access to the current/parent object
            this.id = main.getInt("id");
            // Access to the current/parent object
            this.translationKey = main.getString("translationKey");
            // Start of a block
            {
                // Calls a method
                final String blockNamespace = main.getString("correspondingBlock", null);
                // Access to the current/parent object
                this.blockSupplier = blockNamespace != null ? () -> Block.fromKey(blockNamespace) : () -> null;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public Key key() {
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }

        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return id;
        // End of a block/expression
        }

        // Start of a method/block
        public String translationKey() {
            // Returns a value to the caller
            return translationKey;
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable Block block() {
            // Returns a value to the caller
            return blockSupplier.get();
        // End of a block/expression
        }

        // Start of a method/block
        public DataComponentMap prototype() {
            // Returns a value to the caller
            return switch (prototype) {
                // Multiple branching (switch/case)
                case Either.Left(_) -> throw new IllegalStateException("Should have been bound");
                // Multiple branching (switch/case)
                case Either.Right(var dataComponentMap) -> dataComponentMap;
                // Multiple branching (switch/case)
                case null -> DataComponentMap.EMPTY;
            // End of a block/expression
            };
        // End of a block/expression
        }

        /**
         * Attempts the bind the current prototype using the registries provided.
         *
         * @param registries the registries used during decode
         */
        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        void bindComponents(Registries registries) {
            // Branch: checks a condition
            if (!(prototype instanceof Either.Left(var components))) return;
            // Calls a method
            final Transcoder<Object> coder = new RegistryTranscoder<>(Transcoder.JAVA, registries);
            // Calls a method
            DataComponentMap.Builder builder = DataComponentMap.builder();
            // Loop: repeats a block
            for (Map.Entry<String, Object> entry : components) {
                //noinspection unchecked
                // Calls a method
                DataComponent<Object> component = (DataComponent<Object>) DataComponent.fromKey(entry.getKey());
                // Calls a method
                Check.notNull(component, "Unknown component {0} in {1}", entry.getKey(), key);

                // Calls a method
                final Result<Object> result = component.decode(coder, entry.getValue());
                // Multiple branching (switch/case)
                switch (result) {
                    // Multiple branching (switch/case)
                    case Result.Ok(Object ok) -> builder.set(component, ok);
                    // Multiple branching (switch/case)
                    case Result.Error(String message) ->
                            // Throws an exception
                            throw new IllegalStateException("Failed to decode component " + entry.getKey() + " in " + key + ": " + message);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Calls a method
            final DataComponentMap prototype = builder.build();
            // Access to the current/parent object
            this.prototype = prototype.isEmpty() ? null : Either.right(prototype); // null is essential for EMPTY
        // End of a block/expression
        }

        // Start of a method/block
        public boolean isArmor() {
            // Calls a method
            final Equippable equippableComponent = prototype().get(DataComponents.EQUIPPABLE);
            // Calls a method
            final EquipmentSlot equipmentSlot = equippableComponent == null ? null : equippableComponent.slot();
            // Returns a value to the caller
            return equipmentSlot != null && equipmentSlot.isArmor();
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable EquipmentSlot equipmentSlot() {
            // Calls a method
            final Equippable equippableComponent = prototype().get(DataComponents.EQUIPPABLE);
            // Returns a value to the caller
            return equippableComponent == null ? null : equippableComponent.slot();
        // End of a block/expression
        }

        /**
         * Gets the entity type this item can spawn. Only present for spawn eggs (e.g. wolf spawn egg, skeleton spawn egg)
         *
         * @return The entity type it can spawn, or null if it is not a spawn egg
         * @deprecated Read {@link DataComponents#ENTITY_DATA} for the spawned entity data.
         */
        // Annotation for the following element
        @Deprecated(forRemoval = true)
        // Start of a method/block
        public @Nullable EntityType spawnEntityType() {
            // Calls a method
            TypedCustomData<EntityType> entityData = prototype().get(DataComponents.ENTITY_DATA);
            // Returns a value to the caller
            return entityData == null ? null : entityData.type();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static final class EntityEntry implements Entry {
        // Code statement
        private final Key key;
        // Code statement
        private final int id;
        // Code statement
        private final String translationKey;
        // Code statement
        private final double drag;
        // Code statement
        private final double acceleration;
        // Code statement
        private final boolean isLiving;
        // Code statement
        private final double width;
        // Code statement
        private final double height;
        // Code statement
        private final double eyeHeight;
        // Code statement
        private final int clientTrackingRange;
        // Code statement
        private final boolean fireImmune;
        // Code statement
        private final Map<String, List<Double>> entityOffsets;
        // Code statement
        private final Map<Attribute, Double> defaultAttributes;
        // Code statement
        private final BoundingBox boundingBox;

        // Start of a method/block
        public EntityEntry(String namespace, Properties main) {
            // Access to the current/parent object
            this.key = Key.key(namespace);
            // Access to the current/parent object
            this.id = main.getInt("id");
            // Access to the current/parent object
            this.translationKey = main.getString("translationKey");
            // Access to the current/parent object
            this.drag = main.getDouble("drag", 0.02);
            // Access to the current/parent object
            this.acceleration = main.getDouble("acceleration", 0.08);
            // Calls a method
            final String packetType = main.getString("packetType").toUpperCase(Locale.ROOT);
            // Access to the current/parent object
            this.isLiving = "LIVING".equals(packetType) || "PLAYER".equals(packetType);
            // Access to the current/parent object
            this.fireImmune = main.getBoolean("fireImmune", false);
            // Access to the current/parent object
            this.clientTrackingRange = main.getInt("clientTrackingRange");

            // Dimensions
            // Access to the current/parent object
            this.width = main.getDouble("width");
            // Access to the current/parent object
            this.height = main.getDouble("height");
            // Access to the current/parent object
            this.eyeHeight = main.getDouble("eyeHeight");
            // Access to the current/parent object
            this.boundingBox = new BoundingBox(this.width, this.height, this.width);

            // Attachments
            // Calls a method
            Map<String, List<Double>> entityOffsets = new HashMap<>();
            // Calls a method
            Properties attachments = main.section("attachments");
            // Branch: checks a condition
            if (attachments != null) {
                // Calls a method
                var allAttachments = attachments.asMap().keySet();
                // Loop: repeats a block
                for (String key : allAttachments) {
                    // Calls a method
                    List<List<Double>> offset = attachments.getList(key);
                    // Code statement
                    entityOffsets.put(key, offset.getFirst()); // It's an array of an array with a single element, as of 1.21.3 we only need to grab a single array of 3 doubles
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Access to the current/parent object
            this.entityOffsets = Map.copyOf(entityOffsets);

            // Calls a method
            Properties defaultAttributesSection = main.section("defaultAttributes");

            // Branch: checks a condition
            if (defaultAttributesSection == null) {
                // Access to the current/parent object
                this.defaultAttributes = Map.of();
            // Alternative branch of the condition
            } else {
                // Calls a method
                Map<Attribute, Double> attributes = new HashMap<>();

                // Loop: repeats a block
                for (var entry : defaultAttributesSection) {
                    // Calls a method
                    Attribute attribute = Attribute.fromKey(entry.getKey());
                    // Calls a method
                    Check.notNull(attribute, "Failed to find attribute {0}", entry.getKey());
                    // Calls a method
                    Object value = entry.getValue();
                    // Calls a method
                    Check.stateCondition(!(value instanceof Number), "Attribute value {0} is not a number", value);
                    // Calls a method
                    attributes.put(attribute, ((Number) value).doubleValue());
                // End of a block/expression
                }

                // Access to the current/parent object
                this.defaultAttributes = Map.copyOf(attributes);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public Key key() {
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }

        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return id;
        // End of a block/expression
        }

        // Start of a method/block
        public String translationKey() {
            // Returns a value to the caller
            return translationKey;
        // End of a block/expression
        }

        // Start of a method/block
        public double drag() {
            // Returns a value to the caller
            return drag;
        // End of a block/expression
        }

        // Start of a method/block
        public double acceleration() {
            // Returns a value to the caller
            return acceleration;
        // End of a block/expression
        }

        // Start of a method/block
        public double horizontalAirResistance() {
            // Returns a value to the caller
            return isLiving ? 0.91 : 0.98;
        // End of a block/expression
        }

        // Start of a method/block
        public double verticalAirResistance() {
            // Returns a value to the caller
            return 1 - drag();
        // End of a block/expression
        }

        // Start of a method/block
        public boolean shouldSendAttributes() {
            // Returns a value to the caller
            return isLiving;
        // End of a block/expression
        }

        // Start of a method/block
        public double width() {
            // Returns a value to the caller
            return width;
        // End of a block/expression
        }

        // Start of a method/block
        public double height() {
            // Returns a value to the caller
            return height;
        // End of a block/expression
        }

        // Start of a method/block
        public double eyeHeight() {
            // Returns a value to the caller
            return eyeHeight;
        // End of a block/expression
        }

        // Start of a method/block
        public boolean fireImmune() {
            // Returns a value to the caller
            return fireImmune;
        // End of a block/expression
        }

        // Start of a method/block
        public int clientTrackingRange() {
            // Returns a value to the caller
            return clientTrackingRange;
        // End of a block/expression
        }

        /**
         * Gets the entity attachment by name. Typically, will be PASSENGER or VEHICLE, but some entities have custom attachments (e.g. WARDEN_CHEST, NAMETAG)
         *
         * @param attachmentName The attachment to retrieve
         * @return A list of 3 doubles if the attachment is defined for this entity, or null if it is not defined
         */
        // Start of a method/block
        public @Nullable List<Double> entityAttachment(String attachmentName) {
            // Returns a value to the caller
            return entityOffsets.get(attachmentName);
        // End of a block/expression
        }

        // Start of a method/block
        public BoundingBox boundingBox() {
            // Returns a value to the caller
            return boundingBox;
        // End of a block/expression
        }

        // Start of a method/block
        public Map<Attribute, Double> defaultAttributes() {
            // Returns a value to the caller
            return defaultAttributes;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static final class VillagerProfessionEntry implements Entry {
        // Code statement
        private final Key key;
        // Code statement
        private final int id;
        // Code statement
        private final SoundEvent workSound;

        // Start of a method/block
        public VillagerProfessionEntry(String namespace, Properties main) {
            // Access to the current/parent object
            this.key = Key.key(namespace);
            // Access to the current/parent object
            this.id = main.getInt("id");
            // Branch: checks a condition
            if (main.containsKey("workSound")) {
                // Access to the current/parent object
                this.workSound = SoundEvent.fromKey(main.getString("workSound"));
            // Alternative branch of the condition
            } else {
                // Access to the current/parent object
                this.workSound = null;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        public Key key() {
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }

        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return id;
        // End of a block/expression
        }

        // Start of a method/block
        public @Nullable SoundEvent workSound() {
            // Returns a value to the caller
            return workSound;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record FeatureFlagEntry(Key key, int id) implements Entry {
        // Start of a method/block
        public FeatureFlagEntry(String namespace, Properties main) {
            // Calls a method
            this(Key.key(namespace), main.getInt("id"));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record FluidEntry(Key key, int id) implements Entry {
        // Start of a method/block
        public FluidEntry(String namespace, Properties main) {
            // Calls a method
            this(Key.key(namespace), main.getInt("id"));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record PotionEffectEntry(Key key, int id,
                                    // Code statement
                                    String translationKey,
                                    // Code statement
                                    int color,
                                    // Start of a method/block
                                    boolean isInstantaneous) implements Entry {
        // Start of a method/block
        public PotionEffectEntry(String namespace, Properties main) {
            // Code statement
            this(Key.key(namespace),
                    // Code statement
                    main.getInt("id"),
                    // Code statement
                    main.getString("translationKey"),
                    // Code statement
                    main.getInt("color"),
                    // Calls a method
                    main.getBoolean("instantaneous"));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record AttributeEntry(Key key, int id,
                                 // Code statement
                                 String translationKey, double defaultValue,
                                 // Code statement
                                 boolean clientSync,
                                 // Start of a method/block
                                 double maxValue, double minValue) implements Entry {
        // Start of a method/block
        public AttributeEntry(String namespace, Properties main) {
            // Code statement
            this(Key.key(namespace),
                    // Code statement
                    main.getInt("id"),
                    // Code statement
                    main.getString("translationKey"),
                    // Code statement
                    main.getDouble("defaultValue"),
                    // Code statement
                    main.getBoolean("clientSync"),
                    // Code statement
                    main.getDouble("maxValue"),
                    // Calls a method
                    main.getDouble("minValue"));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record BlockSoundTypeEntry(Key key, float volume, float pitch,
                                      // Code statement
                                      SoundEvent breakSound, SoundEvent hitSound, SoundEvent fallSound,
                                      // Start of a method/block
                                      SoundEvent placeSound, SoundEvent stepSound) {
        // Start of a method/block
        public BlockSoundTypeEntry(String namespace, Properties main) {
            // Code statement
            this(Key.key(namespace), main.getFloat("volume"),
                    // Code statement
                    main.getFloat("pitch"), SoundEvent.fromKey(main.getString("breakSound")), SoundEvent.fromKey(main.getString("hitSound")),
                    // Calls a method
                    SoundEvent.fromKey(main.getString("fallSound")), SoundEvent.fromKey(main.getString("placeSound")), SoundEvent.fromKey(main.getString("stepSound")));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public interface Entry {
    // End of a block/expression
    }

    // Start of a method/block
    private static Object readObject(JsonReader reader) throws IOException {
        // Returns a value to the caller
        return switch (reader.peek()) {
            // Multiple branching (switch/case)
            case BEGIN_ARRAY -> {
                // Calls a method
                List<Object> list = new ArrayList<>();
                // Calls a method
                reader.beginArray();
                // Loop: repeats a block
                while (reader.hasNext()) list.add(readObject(reader));
                // Calls a method
                reader.endArray();
                // Calls a method
                yield List.copyOf(list);
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case BEGIN_OBJECT -> {
                // Calls a method
                Map<String, Object> map = new HashMap<>();
                // Calls a method
                reader.beginObject();
                // Loop: repeats a block
                while (reader.hasNext()) map.put(reader.nextName(), readObject(reader));
                // Calls a method
                reader.endObject();
                // Calls a method
                yield Map.copyOf(map);
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case STRING -> reader.nextString();
            // Multiple branching (switch/case)
            case NUMBER -> ToNumberPolicy.LONG_OR_DOUBLE.readNumber(reader);
            // Multiple branching (switch/case)
            case BOOLEAN -> reader.nextBoolean();
            // Multiple branching (switch/case)
            default -> throw new IllegalStateException("Invalid peek: " + reader.peek());
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PropertiesMap(Map<String, Object> map) implements Properties {
        // Start of a method/block
        PropertiesMap {
            // Calls a method
            map = Map.copyOf(map);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public String getString(String name, String defaultValue) {
            // Calls a method
            var element = element(name);
            // Returns a value to the caller
            return element != null ? (String) element : defaultValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public String getString(String name) {
            // Returns a value to the caller
            return element(name);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public double getDouble(String name, double defaultValue) {
            // Calls a method
            var element = element(name);
            // Returns a value to the caller
            return element != null ? ((Number) element).doubleValue() : defaultValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public double getDouble(String name) {
            // Returns a value to the caller
            return ((Number) element(name)).doubleValue();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int getInt(String name, int defaultValue) {
            // Calls a method
            var element = element(name);
            // Returns a value to the caller
            return element != null ? ((Number) element).intValue() : defaultValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int getInt(String name) {
            // Returns a value to the caller
            return ((Number) element(name)).intValue();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float getFloat(String name, float defaultValue) {
            // Calls a method
            var element = element(name);
            // Returns a value to the caller
            return element != null ? ((Number) element).floatValue() : defaultValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float getFloat(String name) {
            // Returns a value to the caller
            return ((Number) element(name)).floatValue();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean getBoolean(String name, boolean defaultValue) {
            // Calls a method
            var element = element(name);
            // Returns a value to the caller
            return element != null ? (boolean) element : defaultValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean getBoolean(String name) {
            // Returns a value to the caller
            return element(name);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <T> List<T> getList(String name, List<T> defaultValue) {
            // Calls a method
            List<T> element = element(name);
            // Returns a value to the caller
            return element != null ? element : defaultValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Properties section(String name) {
            // Calls a method
            Map<String, Object> map = element(name);
            // Branch: checks a condition
            if (map == null) return null;
            // Returns a value to the caller
            return new PropertiesMap(map);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean containsKey(String name) {
            // Returns a value to the caller
            return map.containsKey(name);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Map<String, Object> asMap() {
            // Returns a value to the caller
            return map;
        // End of a block/expression
        }

        // Start of a method/block
        private <T> T element(String name) {
            //noinspection unchecked
            // Returns a value to the caller
            return (T) map.get(name);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public String toString() {
            // Calls a method
            AtomicReference<String> string = new AtomicReference<>("{ ");
            // Access to the current/parent object
            this.map.forEach((s, object) -> string.set(string.get() + " , " + "\"" + s + "\"" + " : " + "\"" + object + "\""));
            // Returns a value to the caller
            return string.updateAndGet(s -> s.replaceFirst(" , ", "") + "}");
        // End of a block/expression
        }

    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public interface Properties extends Iterable<Map.Entry<String, Object>> {
        // Start of a method/block
        static Properties fromMap(Map<String, Object> map) {
            // Returns a value to the caller
            return new PropertiesMap(map);
        // End of a block/expression
        }

        // Calls a method
        String getString(String name, String defaultValue);

        // Calls a method
        String getString(String name);

        // Calls a method
        double getDouble(String name, double defaultValue);

        // Calls a method
        double getDouble(String name);

        // Calls a method
        int getInt(String name, int defaultValue);

        // Calls a method
        int getInt(String name);

        // Calls a method
        float getFloat(String name, float defaultValue);

        // Calls a method
        float getFloat(String name);

        // Calls a method
        boolean getBoolean(String name, boolean defaultValue);

        // Calls a method
        boolean getBoolean(String name);

        // Calls a method
        <T> List<T> getList(String name, List<T> defaultValue);

        // Start of a method/block
        default <T> List<T> getList(String name) {
            // Returns a value to the caller
            return getList(name, List.of());
        // End of a block/expression
        }

        // Annotation for the following element
        @Deprecated(forRemoval = true)
        // Start of a method/block
        default List<List<Double>> getNestedDoubleArray(String name) {
            // Returns a value to the caller
            return getList(name);
        // End of a block/expression
        }

        // Calls a method
        Properties section(String name);

        // Calls a method
        boolean containsKey(String name);

        // Calls a method
        Map<String, Object> asMap();

        // Annotation for the following element
        @Override
        // Start of a method/block
        default Iterator<Map.Entry<String, Object>> iterator() {
            // Returns a value to the caller
            return asMap().entrySet().iterator();
        // End of a block/expression
        }

        // Start of a method/block
        default int size() {
            // Returns a value to the caller
            return asMap().size();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
