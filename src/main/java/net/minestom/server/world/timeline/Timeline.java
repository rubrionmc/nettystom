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
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.ClockTimeMarker;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import d'une classe nécessaire
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.Function;

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
            "clock", WorldClock.CODEC, Timeline::clock,
            // Instruction de code
            "period_ticks", Codec.INT.optional(), Timeline::periodTicks,
            // Instruction de code
            "tracks", TRACKS_CODEC.optional(Map.of()), Timeline::tracks,
            // Instruction de code
            "time_markers", ClockTimeMarker.CODEC.mapValue(TimeMarkerInfo.REGISTRY_CODEC).optional(Map.of()), Timeline::timeMarkers,
            // Instruction de code
            Timeline::create);

    // Appelle une méthode
    NetworkBuffer.Type<RegistryKey<Timeline>> NETWORK_TYPE = RegistryKey.networkType(Registries::timeline);
    // Appelle une méthode
    Codec<RegistryKey<Timeline>> CODEC = RegistryKey.codec(Registries::timeline);

    // Appelle une méthode
    RegistryKey<WorldClock> clock();

    // Annotation pour l'élément suivant
    @Nullable Integer periodTicks();

    // Appelle une méthode
    Map<EnvironmentAttribute<?>, Track<?, ?>> tracks();

    // Appelle une méthode
    Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers();

    // Début d'une méthode/d'un bloc
    static Timeline create(RegistryKey<WorldClock> clock, @Nullable Integer periodTicks, Map<EnvironmentAttribute<?>, Track<?, ?>> tracks, Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers) {
        // Renvoie une valeur à l'appelant
        return new TimelineImpl(clock, periodTicks, tracks, timeMarkers);
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
    static DynamicRegistry<Timeline> createDefaultRegistry(Registries registries) {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("timeline"),
                // Instruction de code
                REGISTRY_CODEC, registries, RegistryData.Resource.TIMELINES);
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
                    // Appelle une méthode
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
    record TimeMarkerInfo(int ticks, boolean showInCommands) {
        // Affecte une valeur
        public static final Codec<TimeMarkerInfo> CODEC = StructCodec.struct(
                // Instruction de code
                "ticks", Codec.INT, TimeMarkerInfo::ticks,
                // Instruction de code
                "show_in_commands", Codec.BOOLEAN.optional(false), TimeMarkerInfo::showInCommands,
                // Instruction de code
                TimeMarkerInfo::new);
        // Affecte une valeur
        public static final Codec<TimeMarkerInfo> REGISTRY_CODEC = Codec.Either(Codec.INT, CODEC).transform(
                // Instruction de code
                it -> it.unify(TimeMarkerInfo::new, Function.identity()),
                // Appelle une méthode
                it -> !it.showInCommands() ? Either.left(it.ticks()) : Either.right(it));

        // Début d'une méthode/d'un bloc
        public TimeMarkerInfo(int ticks) {
            // Appelle une méthode
            this(ticks, false);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public TimeMarkerInfo {
            // Appelle une méthode
            Check.argCondition(ticks < 0, "ticks must be positive");
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public ClockTimeMarker clockTimeMarker(Timeline timeline) {
            // Renvoie une valeur à l'appelant
            return ClockTimeMarker.create(timeline.clock(), ticks(), timeline.periodTicks(), showInCommands());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private @UnknownNullability RegistryKey<WorldClock> clock;
        // Instruction de code
        private @Nullable Integer periodTicks;
        // Appelle une méthode
        private final Map<EnvironmentAttribute<?>, Track<?, ?>> tracks = new HashMap<>();
        // Appelle une méthode
        private final Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers = new HashMap<>();

        // Début d'une méthode/d'un bloc
        public Builder clock(RegistryKey<WorldClock> clock) {
            // Accès à l'objet courant/parent
            this.clock = clock;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

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
            this.tracks.putAll(tracks);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder timeMarker(RegistryKey<ClockTimeMarker> key, Timeline.TimeMarkerInfo info) {
            // Appelle une méthode
            timeMarkers.put(key, info);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder timeMarkers(Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers) {
            // Accès à l'objet courant/parent
            this.timeMarkers.putAll(timeMarkers);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Timeline build() {
            // Renvoie une valeur à l'appelant
            return Timeline.create(clock, periodTicks, tracks, timeMarkers);
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
