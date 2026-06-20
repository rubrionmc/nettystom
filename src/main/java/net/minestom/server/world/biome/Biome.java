// Déclaration du paquet de ce fichier
package net.minestom.server.world.biome;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Biome extends Biomes permits BiomeImpl {
    // Affecte une valeur
    Codec<Biome> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "has_precipitation", Codec.BOOLEAN, Biome::hasPrecipitation,
            // Instruction de code
            "temperature", Codec.FLOAT, Biome::temperature,
            // Instruction de code
            "temperature_modifier", TemperatureModifier.CODEC.optional(TemperatureModifier.NONE), Biome::temperatureModifier,
            // Instruction de code
            "downfall", Codec.FLOAT, Biome::downfall,
            // Instruction de code
            "attributes", EnvironmentAttributeMap.CODEC.optional(EnvironmentAttributeMap.EMPTY), Biome::attributes,
            // Instruction de code
            "effects", BiomeEffects.CODEC, Biome::effects,
            // Instruction de code
            Biome::create);
    // We dont currently read generation or mob spawn settings. If we do, we will need
    // to have a separate network codec which does not serialize those fields.
    // Affecte une valeur
    Codec<Biome> NETWORK_CODEC = REGISTRY_CODEC;

    // Instruction de code
    static Biome create(
            // Instruction de code
            boolean hasPrecipitation,
            // Instruction de code
            float temperature,
            // Instruction de code
            TemperatureModifier temperatureModifier,
            // Instruction de code
            float downfall,
            // Instruction de code
            EnvironmentAttributeMap attributes,
            // Instruction de code
            BiomeEffects effects
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new BiomeImpl(hasPrecipitation, temperature, temperatureModifier, downfall, attributes, effects);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder(Biome existing) {
        // Renvoie une valeur à l'appelant
        return new Builder(existing);
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for biomes, loading the vanilla trim biomes.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<Biome> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(
                // Instruction de code
                Key.key("worldgen/biome"), NETWORK_CODEC, null, RegistryData.Resource.BIOMES,
                // We force plains to be first because it allows convenient palette initialization.
                // Maybe worth switching to fetching plains in the palette in the future to avoid this.
                // Instruction de code
                (a, b) -> a.equals("minecraft:plains") ? -1 : b.equals("minecraft:plains") ? 1 : 0,
                // Instruction de code
                REGISTRY_CODEC
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    boolean hasPrecipitation();

    // Appelle une méthode
    float temperature();

    // Appelle une méthode
    TemperatureModifier temperatureModifier();

    // Appelle une méthode
    float downfall();

    // Appelle une méthode
    EnvironmentAttributeMap attributes();

    // Appelle une méthode
    BiomeEffects effects();


    // Déclaration de type (classe/interface/enum/record)
    interface Setter {
        // Appelle une méthode
        void setBiome(int x, int y, int z, RegistryKey<Biome> biome);

        // Début d'une méthode/d'un bloc
        default void setBiome(Point blockPosition, RegistryKey<Biome> biome) {
            // Appelle une méthode
            setBiome(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), biome);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    interface Getter {
        // Appelle une méthode
        RegistryKey<Biome> getBiome(int x, int y, int z);

        // Début d'une méthode/d'un bloc
        default RegistryKey<Biome> getBiome(Point point) {
            // Renvoie une valeur à l'appelant
            return getBiome(point.blockX(), point.blockY(), point.blockZ());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum TemperatureModifier {
        // Instruction de code
        NONE, FROZEN;

        // Appelle une méthode
        public static final Codec<TemperatureModifier> CODEC = Codec.Enum(TemperatureModifier.class);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Affecte une valeur
        private boolean hasPrecipitation = true;
        // Affecte une valeur
        private float temperature = 0.8f;
        // Affecte une valeur
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        // Affecte une valeur
        private float downfall = 0.4f;
        // Instruction de code
        private final EnvironmentAttributeMap.Builder attributes;
        // Affecte une valeur
        private BiomeEffects effects = BiomeEffects.DEFAULT;

        // Début d'une méthode/d'un bloc
        private Builder() {
            // Appelle une méthode
            attributes = EnvironmentAttributeMap.builder();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private Builder(Biome existing) {
            // Appelle une méthode
            hasPrecipitation = existing.hasPrecipitation();
            // Appelle une méthode
            temperature = existing.temperature();
            // Appelle une méthode
            temperatureModifier = existing.temperatureModifier();
            // Appelle une méthode
            downfall = existing.downfall();
            // Appelle une méthode
            attributes = EnvironmentAttributeMap.builder(existing.attributes());
            // Appelle une méthode
            effects = existing.effects();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder precipitation(boolean hasPrecipitation) {
            // Accès à l'objet courant/parent
            this.hasPrecipitation = hasPrecipitation;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder temperature(float temperature) {
            // Accès à l'objet courant/parent
            this.temperature = temperature;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder temperatureModifier(TemperatureModifier temperatureModifier) {
            // Accès à l'objet courant/parent
            this.temperatureModifier = temperatureModifier;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public Builder downfall(float downfall) {
            // Accès à l'objet courant/parent
            this.downfall = downfall;
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
        public Builder effects(BiomeEffects effects) {
            // Accès à l'objet courant/parent
            this.effects = effects;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(pure = true)
        // Début d'une méthode/d'un bloc
        public Biome build() {
            // Renvoie une valeur à l'appelant
            return Biome.create(hasPrecipitation, temperature, temperatureModifier, downfall, attributes.build(), effects);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
