// Package declaration for this file
package net.minestom.server.world.biome;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Type declaration (class/interface/enum/record)
public sealed interface Biome extends Biomes permits BiomeImpl {
    // Assigns a value
    Codec<Biome> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "has_precipitation", Codec.BOOLEAN, Biome::hasPrecipitation,
            // Code statement
            "temperature", Codec.FLOAT, Biome::temperature,
            // Code statement
            "temperature_modifier", TemperatureModifier.CODEC.optional(TemperatureModifier.NONE), Biome::temperatureModifier,
            // Code statement
            "downfall", Codec.FLOAT, Biome::downfall,
            // Code statement
            "attributes", EnvironmentAttributeMap.CODEC.optional(EnvironmentAttributeMap.EMPTY), Biome::attributes,
            // Code statement
            "effects", BiomeEffects.CODEC, Biome::effects,
            // Code statement
            Biome::create);
    // We dont currently read generation or mob spawn settings. If we do, we will need
    // to have a separate network codec which does not serialize those fields.
    // Assigns a value
    Codec<Biome> NETWORK_CODEC = REGISTRY_CODEC;

    // Code statement
    static Biome create(
            // Code statement
            boolean hasPrecipitation,
            // Code statement
            float temperature,
            // Code statement
            TemperatureModifier temperatureModifier,
            // Code statement
            float downfall,
            // Code statement
            EnvironmentAttributeMap attributes,
            // Code statement
            BiomeEffects effects
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new BiomeImpl(hasPrecipitation, temperature, temperatureModifier, downfall, attributes, effects);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder(Biome existing) {
        // Returns a value to the caller
        return new Builder(existing);
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for biomes, loading the vanilla trim biomes.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<Biome> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(
                // Code statement
                Key.key("worldgen/biome"), NETWORK_CODEC, null, RegistryData.Resource.BIOMES,
                // We force plains to be first because it allows convenient palette initialization.
                // Maybe worth switching to fetching plains in the palette in the future to avoid this.
                // Code statement
                (a, b) -> a.equals("minecraft:plains") ? -1 : b.equals("minecraft:plains") ? 1 : 0,
                // Code statement
                REGISTRY_CODEC
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Calls a method
    boolean hasPrecipitation();

    // Calls a method
    float temperature();

    // Calls a method
    TemperatureModifier temperatureModifier();

    // Calls a method
    float downfall();

    // Calls a method
    EnvironmentAttributeMap attributes();

    // Calls a method
    BiomeEffects effects();


    // Type declaration (class/interface/enum/record)
    interface Setter {
        // Calls a method
        void setBiome(int x, int y, int z, RegistryKey<Biome> biome);

        // Start of a method/block
        default void setBiome(Point blockPosition, RegistryKey<Biome> biome) {
            // Calls a method
            setBiome(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), biome);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    interface Getter {
        // Calls a method
        RegistryKey<Biome> getBiome(int x, int y, int z);

        // Start of a method/block
        default RegistryKey<Biome> getBiome(Point point) {
            // Returns a value to the caller
            return getBiome(point.blockX(), point.blockY(), point.blockZ());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum TemperatureModifier {
        // Code statement
        NONE, FROZEN;

        // Calls a method
        public static final Codec<TemperatureModifier> CODEC = Codec.Enum(TemperatureModifier.class);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Assigns a value
        private boolean hasPrecipitation = true;
        // Assigns a value
        private float temperature = 0.8f;
        // Assigns a value
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        // Assigns a value
        private float downfall = 0.4f;
        // Code statement
        private final EnvironmentAttributeMap.Builder attributes;
        // Assigns a value
        private BiomeEffects effects = BiomeEffects.DEFAULT;

        // Start of a method/block
        private Builder() {
            // Calls a method
            attributes = EnvironmentAttributeMap.builder();
        // End of a block/expression
        }

        // Start of a method/block
        private Builder(Biome existing) {
            // Calls a method
            hasPrecipitation = existing.hasPrecipitation();
            // Calls a method
            temperature = existing.temperature();
            // Calls a method
            temperatureModifier = existing.temperatureModifier();
            // Calls a method
            downfall = existing.downfall();
            // Calls a method
            attributes = EnvironmentAttributeMap.builder(existing.attributes());
            // Calls a method
            effects = existing.effects();
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder precipitation(boolean hasPrecipitation) {
            // Access to the current/parent object
            this.hasPrecipitation = hasPrecipitation;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder temperature(float temperature) {
            // Access to the current/parent object
            this.temperature = temperature;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder temperatureModifier(TemperatureModifier temperatureModifier) {
            // Access to the current/parent object
            this.temperatureModifier = temperatureModifier;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public Builder downfall(float downfall) {
            // Access to the current/parent object
            this.downfall = downfall;
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
        public Builder effects(BiomeEffects effects) {
            // Access to the current/parent object
            this.effects = effects;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(pure = true)
        // Start of a method/block
        public Biome build() {
            // Returns a value to the caller
            return Biome.create(hasPrecipitation, temperature, temperatureModifier, downfall, attributes.build(), effects);
        // End of a block/expression
        }

    // End of a block/expression
    }
// End of a block/expression
}
