// Déclaration du paquet de ce fichier
package net.minestom.server.world.timeline;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.utils.EaseFunction;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Timeline extends Timelines permits TimelineImpl {
    // Annotation pour l'élément suivant
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Affecte une valeur
    Codec<Map<EnvironmentAttribute<?>, Track<?, ?>>> TRACKS_CODEC = EnvironmentAttribute.CODEC
            // Appelle une méthode
            .mapValueTyped(attribute -> (Codec) Track.codec(attribute), true);
    // Affecte une valeur
    Codec<Timeline> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "period_ticks", Codec.INT.optional(), Timeline::periodTicks,
            // Instruction de code
            "tracks", TRACKS_CODEC.optional(Map.of()), Timeline::tracks,
            // Instruction de code
            Timeline::create);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<Timeline>> NETWORK_TYPE = RegistryKey.networkType(Registries::timeline);
    // Appelle une méthode
    Codec<RegistryKey<Timeline>> CODEC = RegistryKey.codec(Registries::timeline);

    // Annotation pour l'élément suivant
    @Nullable Integer periodTicks();

    // Appelle une méthode
    Map<EnvironmentAttribute<?>, Track<?, ?>> tracks();

    // Début d'une méthode/d'un bloc
    static Timeline create(@Nullable Integer periodTicks, Map<EnvironmentAttribute<?>, Track<?, ?>> tracks) {
        // Renvoie une valeur à l'appelant
        return new TimelineImpl(periodTicks, tracks);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /// Creates a new registry for timelines, loading the vanilla timelines.
    ///
    /// @see net.minestom.server.MinecraftServer to get an existing instance of the registry
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<Timeline> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("timeline"),
                // Instruction de code
                REGISTRY_CODEC, RegistryData.Resource.TIMELINES);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Track<T, Arg>(
            // Instruction de code
            Modifier<T, Arg> modifier,
            // Instruction de code
            List<Keyframe<Arg>> keyframes,
            // Instruction de code
            EaseFunction ease
    // Début d'une méthode/d'un bloc
    ) {
        // Début d'une méthode/d'un bloc
        public static <T, Arg> Codec<Track<T, Arg>> codec(EnvironmentAttribute<T> attribute) {
            //noinspection unchecked
            // Renvoie une valeur à l'appelant
            return attribute.type().modifierCodec().optional(new Modifier.Override<>(attribute.valueCodec()))
                    // Appelle une méthode
                    .unionType("modifier", modifier -> fullCodec((Modifier<T, Arg>) modifier), Track::modifier);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static <T, Arg> StructCodec<Track<T, Arg>> fullCodec(Modifier<T, Arg> modifier) {
            // Appelle une méthode
            var keyframesCodec = Keyframe.codec(modifier.argumentCodec()).list();
            // Renvoie une valeur à l'appelant
            return StructCodec.struct(
                    // Instruction de code
                    "keyframes", keyframesCodec, Track::keyframes,
                    // Instruction de code
                    "ease", EaseFunction.CODEC.optional(EaseFunction.LINEAR), Track::ease,
                    // Instruction de code
                    (keyframes, ease) -> new Track<>(modifier, keyframes, ease));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Keyframe<T>(int ticks, T value) {
        // Début d'une méthode/d'un bloc
        public static <T> Codec<Keyframe<T>> codec(Codec<T> valueCodec) {
            // Renvoie une valeur à l'appelant
            return StructCodec.struct(
                    // Instruction de code
                    "ticks", Codec.INT, Keyframe::ticks,
                    // Instruction de code
                    "value", valueCodec, Keyframe::value,
                    // Instruction de code
                    Keyframe::new);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private @Nullable Integer periodTicks;
        // Affecte une valeur
        private Map<EnvironmentAttribute<?>, Track<?, ?>> tracks = new HashMap<>();

        // Début d'une méthode/d'un bloc
        public Builder periodTicks(int ticks) {
            // Accès à l'objet courant/parent
            this.periodTicks = ticks;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder periodTicks(@Nullable Integer ticks) {
            // Accès à l'objet courant/parent
            this.periodTicks = ticks;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public <T, Arg> Builder track(EnvironmentAttribute<T> attribute, Track<T, Arg> track) {
            // Appelle une méthode
            tracks.put(attribute, track);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder tracks(Map<EnvironmentAttribute<?>, Track<?, ?>> tracks) {
            // Accès à l'objet courant/parent
            this.tracks = tracks;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Timeline build() {
            // Renvoie une valeur à l'appelant
            return Timeline.create(periodTicks, tracks);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
