// Package declaration for this file
package net.minestom.server.world;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.registry.*;
// Import of a required class
import net.minestom.server.utils.IntProvider;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.server.world.timeline.Timeline;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;

/**
 * <a href="https://minecraft.wiki/w/Dimension_type">Dimension type</a>
 */
// Type declaration (class/interface/enum/record)
public sealed interface DimensionType extends DimensionTypes permits DimensionTypeImpl {
    // Assigns a value
    int VANILLA_MIN_Y = -64;
    // Assigns a value
    int VANILLA_MAX_Y = 319;

    // Assigns a value
    Codec<DimensionType> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "has_fixed_time", Codec.BOOLEAN.optional(false), DimensionType::hasFixedTime,
            // Code statement
            "has_skylight", Codec.BOOLEAN, DimensionType::hasSkylight,
            // Code statement
            "has_ceiling", Codec.BOOLEAN, DimensionType::hasCeiling,
            // Code statement
            "has_ender_dragon_fight", Codec.BOOLEAN, DimensionType::hasEnderDragonFight,
            // Code statement
            "coordinate_scale", Codec.DOUBLE, DimensionType::coordinateScale,
            // Code statement
            "min_y", Codec.INT, DimensionType::minY,
            // Code statement
            "height", Codec.INT, DimensionType::height,
            // Code statement
            "logical_height", Codec.INT, DimensionType::logicalHeight,
            // Code statement
            "infiniburn", Codec.STRING, DimensionType::infiniburn,
            // Code statement
            "ambient_light", Codec.FLOAT, DimensionType::ambientLight,
            // Code statement
            "monster_spawn_light_level", IntProvider.CODEC, DimensionType::monsterSpawnLightLevel,
            // Code statement
            "monster_spawn_block_light_limit", Codec.INT, DimensionType::monsterSpawnBlockLightLimit,
            // Code statement
            "skybox", Skybox.CODEC.optional(Skybox.OVERWORLD), DimensionType::skybox,
            // Code statement
            "cardinal_light", CardinalLight.CODEC.optional(CardinalLight.DEFAULT), DimensionType::cardinalLight,
            // Code statement
            "attributes", EnvironmentAttributeMap.CODEC.optional(EnvironmentAttributeMap.EMPTY), DimensionType::attributes,
            // Code statement
            "timelines", RegistryTag.codec(Registries::timeline).optional(RegistryTag.empty()), DimensionType::timelines,
            // Code statement
            "default_clock", WorldClock.CODEC.optional(), DimensionType::defaultClock,
            // Code statement
            DimensionType::create);

    // Code statement
    static DimensionType create(
            // Code statement
            boolean hasFixedTime, boolean hasSkyLight, boolean hasCeiling, boolean hasEnderDragonFight,
            // Code statement
            double coordinateScale, int minY, int height, int logicalHeight,
            // Code statement
            String infiniburn, float ambientLight,
            // Code statement
            IntProvider monsterSpawnLightLevel, int monsterSpawnBlockLightLimit,
            // Code statement
            Skybox skybox, CardinalLight cardinalLight,
            // Code statement
            EnvironmentAttributeMap attributes, RegistryTag<Timeline> timelines, RegistryKey<WorldClock> defaultClock
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new DimensionTypeImpl(hasFixedTime, hasSkyLight, hasCeiling, hasEnderDragonFight,
                // Code statement
                coordinateScale, minY, height, logicalHeight, infiniburn,
                // Code statement
                ambientLight, monsterSpawnLightLevel, monsterSpawnBlockLightLimit,
                // Code statement
                skybox, cardinalLight, attributes, timelines, defaultClock
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder(DimensionType existing) {
        // Returns a value to the caller
        return new Builder(existing);
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for dimension types, loading the vanilla dimension types.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<DimensionType> createDefaultRegistry(Registries registries) {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("dimension_type"),
                // Code statement
                REGISTRY_CODEC, registries, RegistryData.Resource.DIMENSION_TYPES);
    // End of a block/expression
    }

    // Calls a method
    boolean hasFixedTime();

    // Calls a method
    boolean hasSkylight();

    // Calls a method
    boolean hasCeiling();

    // Calls a method
    boolean hasEnderDragonFight();

    // Calls a method
    double coordinateScale();

    // Calls a method
    int minY();

    // Start of a method/block
    default int maxY() {
        // Returns a value to the caller
        return minY() + height();
    // End of a block/expression
    }

    // Calls a method
    int height();

    // Calls a method
    int logicalHeight();

    // Calls a method
    String infiniburn();

    // Calls a method
    float ambientLight();

    // Calls a method
    IntProvider monsterSpawnLightLevel();

    // Calls a method
    int monsterSpawnBlockLightLimit();

    // Calls a method
    Skybox skybox();

    // Calls a method
    CardinalLight cardinalLight();

    // Calls a method
    EnvironmentAttributeMap attributes();

    // Calls a method
    RegistryTag<Timeline> timelines();

    // Calls a method
    RegistryKey<WorldClock> defaultClock();

    // Start of a method/block
    default int totalHeight() {
        // Returns a value to the caller
        return minY() + height();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum Skybox {
        // Code statement
        NONE,
        // Code statement
        OVERWORLD,
        // Code statement
        END;

        // Calls a method
        public static final Codec<Skybox> CODEC = Codec.Enum(Skybox.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum CardinalLight {
        // Code statement
        DEFAULT,
        // Code statement
        NETHER;

        // Calls a method
        public static final Codec<CardinalLight> CODEC = Codec.Enum(CardinalLight.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Assigns a value
        private boolean hasFixedTime = false;
        // Assigns a value
        private boolean hasSkylight = true;
        // Assigns a value
        private boolean hasCeiling = false;
        // Assigns a value
        private boolean hasEnderDragonFight = false;
        // Assigns a value
        private double coordinateScale = 1;
        // Assigns a value
        private int minY = VANILLA_MIN_Y;
        // Assigns a value
        private int height = VANILLA_MAX_Y - VANILLA_MIN_Y + 1;
        // Assigns a value
        private int logicalHeight = VANILLA_MAX_Y - VANILLA_MIN_Y + 1;
        // Assigns a value
        private String infiniburn = "#minecraft:infiniburn_overworld";
        // Assigns a value
        private float ambientLight = 0f;
        // Calls a method
        private IntProvider monsterSpawnLightLevel = new IntProvider.Uniform(0, 7);
        // Assigns a value
        private int monsterSpawnBlockLightLimit = 0;
        // Assigns a value
        private Skybox skybox = Skybox.OVERWORLD;
        // Assigns a value
        private CardinalLight cardinalLight = CardinalLight.DEFAULT;
        // Code statement
        private final EnvironmentAttributeMap.Builder attributes;
        // Calls a method
        private RegistryTag<Timeline> timelines = RegistryTag.empty();
        // Assigns a value
        private RegistryKey<WorldClock> defaultClock = null;

        // Start of a method/block
        private Builder() {
            // Calls a method
            attributes = EnvironmentAttributeMap.builder();
        // End of a block/expression
        }

        // Start of a method/block
        private Builder(DimensionType existing) {
            // Access to the current/parent object
            this.hasFixedTime = existing.hasFixedTime();
            // Access to the current/parent object
            this.hasSkylight = existing.hasSkylight();
            // Access to the current/parent object
            this.hasCeiling = existing.hasCeiling();
            // Access to the current/parent object
            this.hasEnderDragonFight = existing.hasEnderDragonFight();
            // Access to the current/parent object
            this.coordinateScale = existing.coordinateScale();
            // Access to the current/parent object
            this.minY = existing.minY();
            // Access to the current/parent object
            this.height = existing.height();
            // Access to the current/parent object
            this.logicalHeight = existing.logicalHeight();
            // Access to the current/parent object
            this.infiniburn = existing.infiniburn();
            // Access to the current/parent object
            this.ambientLight = existing.ambientLight();
            // Access to the current/parent object
            this.monsterSpawnLightLevel = existing.monsterSpawnLightLevel();
            // Access to the current/parent object
            this.monsterSpawnBlockLightLimit = existing.monsterSpawnBlockLightLimit();
            // Access to the current/parent object
            this.skybox = existing.skybox();
            // Access to the current/parent object
            this.cardinalLight = existing.cardinalLight();
            // Access to the current/parent object
            this.attributes = EnvironmentAttributeMap.builder(existing.attributes());
            // Access to the current/parent object
            this.timelines = existing.timelines();
            // Access to the current/parent object
            this.defaultClock = existing.defaultClock();
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder fixedTime(boolean hasFixedTime) {
            // Access to the current/parent object
            this.hasFixedTime = hasFixedTime;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder skylight(boolean hasSkylight) {
            // Access to the current/parent object
            this.hasSkylight = hasSkylight;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder ceiling(boolean hasCeiling) {
            // Access to the current/parent object
            this.hasCeiling = hasCeiling;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract
        // Start of a method/block
        public Builder enderDragonFight(boolean hasEnderDragonFight) {
            // Access to the current/parent object
            this.hasEnderDragonFight = hasEnderDragonFight;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder coordinateScale(double coordinateScale) {
            // Calls a method
            Check.argCondition(coordinateScale < 0.00001 || coordinateScale > 30000000.0, "coordinateScale must be between 0.00001 and 30000000.0");
            // Access to the current/parent object
            this.coordinateScale = coordinateScale;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder minY(int minY) {
            // Calls a method
            Check.argCondition(minY % 16 != 0, "minY must be a multiple of 16");
            // Calls a method
            Check.argCondition(minY < -2032 || minY > 2031, "minY must be between -2032 and 2031");
            // Access to the current/parent object
            this.minY = minY;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder height(int height) {
            // Calls a method
            Check.argCondition(height % 16 != 0, "height must be a multiple of 16");
            // Calls a method
            Check.argCondition(height < 16 || height > 4064, "height must be between 16 and 4064");
            // Access to the current/parent object
            this.height = height;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder logicalHeight(int logicalHeight) {
            // Calls a method
            Check.argCondition(logicalHeight < 0, "logicalHeight must be 0 or greater");
            // Access to the current/parent object
            this.logicalHeight = logicalHeight;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder infiniburn(String infiniburn) {
            // Calls a method
            Check.argCondition(!infiniburn.startsWith("#"), "blockTag has to start with #");
            // Access to the current/parent object
            this.infiniburn = infiniburn;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder ambientLight(float ambientLight) {
            // Access to the current/parent object
            this.ambientLight = ambientLight;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder monsterSpawnLightLevel(IntProvider monsterSpawnLightLevel) {
            // Access to the current/parent object
            this.monsterSpawnLightLevel = monsterSpawnLightLevel;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder monsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
            // Calls a method
            Check.argCondition(monsterSpawnBlockLightLimit < 0 || monsterSpawnBlockLightLimit > 15, "monsterSpawnBlockLightLimit must be between 0 and 15");
            // Access to the current/parent object
            this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder skybox(Skybox skybox) {
            // Access to the current/parent object
            this.skybox = skybox;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder cardinalLight(CardinalLight cardinalLight) {
            // Access to the current/parent object
            this.cardinalLight = cardinalLight;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_, _ -> this")
        // Start of a method/block
        public <T> Builder setAttribute(EnvironmentAttribute<T> attribute, T value) {
            // Calls a method
            attributes.set(attribute, value);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_, _, _ -> this")
        // Start of a method/block
        public <T, Arg> Builder modifyAttribute(EnvironmentAttribute<T> attribute, EnvironmentAttribute.Modifier<T, Arg> modifier, Arg argument) {
            // Calls a method
            attributes.modify(attribute, modifier, argument);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder timelines(RegistryTag<Timeline> timelines) {
            // Access to the current/parent object
            this.timelines = timelines;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder defaultClock(RegistryKey<WorldClock> defaultClock) {
            // Access to the current/parent object
            this.defaultClock = defaultClock;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public DimensionType build() {
            // Calls a method
            Check.argCondition(height < logicalHeight, "logicalHeight must be less than or equals height");
            // Calls a method
            Check.argCondition(minY + height - 1 > 2031, "the maximum building height (minY + height -1) must be less than 3032");

            // Returns a value to the caller
            return DimensionType.create(hasFixedTime, hasSkylight, hasCeiling, hasEnderDragonFight, coordinateScale,
                    // Code statement
                    minY, height, logicalHeight, infiniburn, ambientLight, monsterSpawnLightLevel,
                    // Calls a method
                    monsterSpawnBlockLightLimit, skybox, cardinalLight, attributes.build(), timelines, defaultClock);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
