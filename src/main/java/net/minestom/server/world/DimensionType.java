// Déclaration du paquet de ce fichier
package net.minestom.server.world;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.registry.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.IntProvider;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.server.world.timeline.Timeline;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

/**
 * <a href="https://minecraft.wiki/w/Dimension_type">Dimension type</a>
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface DimensionType extends DimensionTypes permits DimensionTypeImpl {
    // Affecte une valeur
    int VANILLA_MIN_Y = -64;
    // Affecte une valeur
    int VANILLA_MAX_Y = 319;

    // Affecte une valeur
    Codec<DimensionType> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "has_fixed_time", Codec.BOOLEAN.optional(false), DimensionType::hasFixedTime,
            // Instruction de code
            "has_skylight", Codec.BOOLEAN, DimensionType::hasSkylight,
            // Instruction de code
            "has_ceiling", Codec.BOOLEAN, DimensionType::hasCeiling,
            // Instruction de code
            "has_ender_dragon_fight", Codec.BOOLEAN, DimensionType::hasEnderDragonFight,
            // Instruction de code
            "coordinate_scale", Codec.DOUBLE, DimensionType::coordinateScale,
            // Instruction de code
            "min_y", Codec.INT, DimensionType::minY,
            // Instruction de code
            "height", Codec.INT, DimensionType::height,
            // Instruction de code
            "logical_height", Codec.INT, DimensionType::logicalHeight,
            // Instruction de code
            "infiniburn", Codec.STRING, DimensionType::infiniburn,
            // Instruction de code
            "ambient_light", Codec.FLOAT, DimensionType::ambientLight,
            // Instruction de code
            "monster_spawn_light_level", IntProvider.CODEC, DimensionType::monsterSpawnLightLevel,
            // Instruction de code
            "monster_spawn_block_light_limit", Codec.INT, DimensionType::monsterSpawnBlockLightLimit,
            // Instruction de code
            "skybox", Skybox.CODEC.optional(Skybox.OVERWORLD), DimensionType::skybox,
            // Instruction de code
            "cardinal_light", CardinalLight.CODEC.optional(CardinalLight.DEFAULT), DimensionType::cardinalLight,
            // Instruction de code
            "attributes", EnvironmentAttributeMap.CODEC.optional(EnvironmentAttributeMap.EMPTY), DimensionType::attributes,
            // Instruction de code
            "timelines", RegistryTag.codec(Registries::timeline).optional(RegistryTag.empty()), DimensionType::timelines,
            // Instruction de code
            "default_clock", WorldClock.CODEC.optional(), DimensionType::defaultClock,
            // Instruction de code
            DimensionType::create);

    // Instruction de code
    static DimensionType create(
            // Instruction de code
            boolean hasFixedTime, boolean hasSkyLight, boolean hasCeiling, boolean hasEnderDragonFight,
            // Instruction de code
            double coordinateScale, int minY, int height, int logicalHeight,
            // Instruction de code
            String infiniburn, float ambientLight,
            // Instruction de code
            IntProvider monsterSpawnLightLevel, int monsterSpawnBlockLightLimit,
            // Instruction de code
            Skybox skybox, CardinalLight cardinalLight,
            // Instruction de code
            EnvironmentAttributeMap attributes, RegistryTag<Timeline> timelines, RegistryKey<WorldClock> defaultClock
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new DimensionTypeImpl(hasFixedTime, hasSkyLight, hasCeiling, hasEnderDragonFight,
                // Instruction de code
                coordinateScale, minY, height, logicalHeight, infiniburn,
                // Instruction de code
                ambientLight, monsterSpawnLightLevel, monsterSpawnBlockLightLimit,
                // Instruction de code
                skybox, cardinalLight, attributes, timelines, defaultClock
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder(DimensionType existing) {
        // Renvoie une valeur à l'appelant
        return new Builder(existing);
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for dimension types, loading the vanilla dimension types.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<DimensionType> createDefaultRegistry(Registries registries) {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("dimension_type"),
                // Instruction de code
                REGISTRY_CODEC, registries, RegistryData.Resource.DIMENSION_TYPES);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    boolean hasFixedTime();

    // Appelle une méthode
    boolean hasSkylight();

    // Appelle une méthode
    boolean hasCeiling();

    // Appelle une méthode
    boolean hasEnderDragonFight();

    // Appelle une méthode
    double coordinateScale();

    // Appelle une méthode
    int minY();

    // Début d'une méthode/d'un bloc
    default int maxY() {
        // Renvoie une valeur à l'appelant
        return minY() + height();
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    int height();

    // Appelle une méthode
    int logicalHeight();

    // Appelle une méthode
    String infiniburn();

    // Appelle une méthode
    float ambientLight();

    // Appelle une méthode
    IntProvider monsterSpawnLightLevel();

    // Appelle une méthode
    int monsterSpawnBlockLightLimit();

    // Appelle une méthode
    Skybox skybox();

    // Appelle une méthode
    CardinalLight cardinalLight();

    // Appelle une méthode
    EnvironmentAttributeMap attributes();

    // Appelle une méthode
    RegistryTag<Timeline> timelines();

    // Appelle une méthode
    RegistryKey<WorldClock> defaultClock();

    // Début d'une méthode/d'un bloc
    default int totalHeight() {
        // Renvoie une valeur à l'appelant
        return minY() + height();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum Skybox {
        // Instruction de code
        NONE,
        // Instruction de code
        OVERWORLD,
        // Instruction de code
        END;

        // Appelle une méthode
        public static final Codec<Skybox> CODEC = Codec.Enum(Skybox.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum CardinalLight {
        // Instruction de code
        DEFAULT,
        // Instruction de code
        NETHER;

        // Appelle une méthode
        public static final Codec<CardinalLight> CODEC = Codec.Enum(CardinalLight.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Affecte une valeur
        private boolean hasFixedTime = false;
        // Affecte une valeur
        private boolean hasSkylight = true;
        // Affecte une valeur
        private boolean hasCeiling = false;
        // Affecte une valeur
        private boolean hasEnderDragonFight = false;
        // Affecte une valeur
        private double coordinateScale = 1;
        // Affecte une valeur
        private int minY = VANILLA_MIN_Y;
        // Affecte une valeur
        private int height = VANILLA_MAX_Y - VANILLA_MIN_Y + 1;
        // Affecte une valeur
        private int logicalHeight = VANILLA_MAX_Y - VANILLA_MIN_Y + 1;
        // Affecte une valeur
        private String infiniburn = "#minecraft:infiniburn_overworld";
        // Affecte une valeur
        private float ambientLight = 0f;
        // Appelle une méthode
        private IntProvider monsterSpawnLightLevel = new IntProvider.Uniform(0, 7);
        // Affecte une valeur
        private int monsterSpawnBlockLightLimit = 0;
        // Affecte une valeur
        private Skybox skybox = Skybox.OVERWORLD;
        // Affecte une valeur
        private CardinalLight cardinalLight = CardinalLight.DEFAULT;
        // Instruction de code
        private final EnvironmentAttributeMap.Builder attributes;
        // Appelle une méthode
        private RegistryTag<Timeline> timelines = RegistryTag.empty();
        // Affecte une valeur
        private RegistryKey<WorldClock> defaultClock = null;

        // Début d'une méthode/d'un bloc
        private Builder() {
            // Appelle une méthode
            attributes = EnvironmentAttributeMap.builder();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private Builder(DimensionType existing) {
            // Accès à l'objet courant/parent
            this.hasFixedTime = existing.hasFixedTime();
            // Accès à l'objet courant/parent
            this.hasSkylight = existing.hasSkylight();
            // Accès à l'objet courant/parent
            this.hasCeiling = existing.hasCeiling();
            // Accès à l'objet courant/parent
            this.hasEnderDragonFight = existing.hasEnderDragonFight();
            // Accès à l'objet courant/parent
            this.coordinateScale = existing.coordinateScale();
            // Accès à l'objet courant/parent
            this.minY = existing.minY();
            // Accès à l'objet courant/parent
            this.height = existing.height();
            // Accès à l'objet courant/parent
            this.logicalHeight = existing.logicalHeight();
            // Accès à l'objet courant/parent
            this.infiniburn = existing.infiniburn();
            // Accès à l'objet courant/parent
            this.ambientLight = existing.ambientLight();
            // Accès à l'objet courant/parent
            this.monsterSpawnLightLevel = existing.monsterSpawnLightLevel();
            // Accès à l'objet courant/parent
            this.monsterSpawnBlockLightLimit = existing.monsterSpawnBlockLightLimit();
            // Accès à l'objet courant/parent
            this.skybox = existing.skybox();
            // Accès à l'objet courant/parent
            this.cardinalLight = existing.cardinalLight();
            // Accès à l'objet courant/parent
            this.attributes = EnvironmentAttributeMap.builder(existing.attributes());
            // Accès à l'objet courant/parent
            this.timelines = existing.timelines();
            // Accès à l'objet courant/parent
            this.defaultClock = existing.defaultClock();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder fixedTime(boolean hasFixedTime) {
            // Accès à l'objet courant/parent
            this.hasFixedTime = hasFixedTime;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder skylight(boolean hasSkylight) {
            // Accès à l'objet courant/parent
            this.hasSkylight = hasSkylight;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder ceiling(boolean hasCeiling) {
            // Accès à l'objet courant/parent
            this.hasCeiling = hasCeiling;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract
        // Début d'une méthode/d'un bloc
        public Builder enderDragonFight(boolean hasEnderDragonFight) {
            // Accès à l'objet courant/parent
            this.hasEnderDragonFight = hasEnderDragonFight;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder coordinateScale(double coordinateScale) {
            // Appelle une méthode
            Check.argCondition(coordinateScale < 0.00001 || coordinateScale > 30000000.0, "coordinateScale must be between 0.00001 and 30000000.0");
            // Accès à l'objet courant/parent
            this.coordinateScale = coordinateScale;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder minY(int minY) {
            // Appelle une méthode
            Check.argCondition(minY % 16 != 0, "minY must be a multiple of 16");
            // Appelle une méthode
            Check.argCondition(minY < -2032 || minY > 2031, "minY must be between -2032 and 2031");
            // Accès à l'objet courant/parent
            this.minY = minY;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder height(int height) {
            // Appelle une méthode
            Check.argCondition(height % 16 != 0, "height must be a multiple of 16");
            // Appelle une méthode
            Check.argCondition(height < 16 || height > 4064, "height must be between 16 and 4064");
            // Accès à l'objet courant/parent
            this.height = height;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder logicalHeight(int logicalHeight) {
            // Appelle une méthode
            Check.argCondition(logicalHeight < 0, "logicalHeight must be 0 or greater");
            // Accès à l'objet courant/parent
            this.logicalHeight = logicalHeight;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder infiniburn(String infiniburn) {
            // Appelle une méthode
            Check.argCondition(!infiniburn.startsWith("#"), "blockTag has to start with #");
            // Accès à l'objet courant/parent
            this.infiniburn = infiniburn;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder ambientLight(float ambientLight) {
            // Accès à l'objet courant/parent
            this.ambientLight = ambientLight;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder monsterSpawnLightLevel(IntProvider monsterSpawnLightLevel) {
            // Accès à l'objet courant/parent
            this.monsterSpawnLightLevel = monsterSpawnLightLevel;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder monsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
            // Appelle une méthode
            Check.argCondition(monsterSpawnBlockLightLimit < 0 || monsterSpawnBlockLightLimit > 15, "monsterSpawnBlockLightLimit must be between 0 and 15");
            // Accès à l'objet courant/parent
            this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder skybox(Skybox skybox) {
            // Accès à l'objet courant/parent
            this.skybox = skybox;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder cardinalLight(CardinalLight cardinalLight) {
            // Accès à l'objet courant/parent
            this.cardinalLight = cardinalLight;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_, _ -> this")
        // Début d'une méthode/d'un bloc
        public <T> Builder setAttribute(EnvironmentAttribute<T> attribute, T value) {
            // Appelle une méthode
            attributes.set(attribute, value);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_, _, _ -> this")
        // Début d'une méthode/d'un bloc
        public <T, Arg> Builder modifyAttribute(EnvironmentAttribute<T> attribute, EnvironmentAttribute.Modifier<T, Arg> modifier, Arg argument) {
            // Appelle une méthode
            attributes.modify(attribute, modifier, argument);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder timelines(RegistryTag<Timeline> timelines) {
            // Accès à l'objet courant/parent
            this.timelines = timelines;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder defaultClock(RegistryKey<WorldClock> defaultClock) {
            // Accès à l'objet courant/parent
            this.defaultClock = defaultClock;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public DimensionType build() {
            // Appelle une méthode
            Check.argCondition(height < logicalHeight, "logicalHeight must be less than or equals height");
            // Appelle une méthode
            Check.argCondition(minY + height - 1 > 2031, "the maximum building height (minY + height -1) must be less than 3032");

            // Renvoie une valeur à l'appelant
            return DimensionType.create(hasFixedTime, hasSkylight, hasCeiling, hasEnderDragonFight, coordinateScale,
                    // Instruction de code
                    minY, height, logicalHeight, infiniburn, ambientLight, monsterSpawnLightLevel,
                    // Appelle une méthode
                    monsterSpawnBlockLightLimit, skybox, cardinalLight, attributes.build(), timelines, defaultClock);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
