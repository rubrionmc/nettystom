// Package declaration for this file
package net.minestom.server.world.timeline;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.utils.EaseFunction;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import net.minestom.server.world.clock.ClockTimeMarker;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttribute.Modifier;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public sealed interface Timeline extends Timelines permits TimelineImpl {
    // Annotation for the following element
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Assigns a value
    Codec<Map<EnvironmentAttribute<?>, Track<?, ?>>> TRACKS_CODEC = EnvironmentAttribute.CODEC
            // Calls a method
            .mapValueTyped(attribute -> (Codec) Track.codec(attribute), true);
    // Assigns a value
    Codec<Timeline> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "clock", WorldClock.CODEC, Timeline::clock,
            // Code statement
            "period_ticks", Codec.INT.optional(), Timeline::periodTicks,
            // Code statement
            "tracks", TRACKS_CODEC.optional(Map.of()), Timeline::tracks,
            // Code statement
            "time_markers", ClockTimeMarker.CODEC.mapValue(TimeMarkerInfo.REGISTRY_CODEC).optional(Map.of()), Timeline::timeMarkers,
            // Code statement
            Timeline::create);

    // Calls a method
    NetworkBuffer.Type<RegistryKey<Timeline>> NETWORK_TYPE = RegistryKey.networkType(Registries::timeline);
    // Calls a method
    Codec<RegistryKey<Timeline>> CODEC = RegistryKey.codec(Registries::timeline);

    // Calls a method
    RegistryKey<WorldClock> clock();

    // Annotation for the following element
    @Nullable Integer periodTicks();

    // Calls a method
    Map<EnvironmentAttribute<?>, Track<?, ?>> tracks();

    // Calls a method
    Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers();

    // Start of a method/block
    static Timeline create(RegistryKey<WorldClock> clock, @Nullable Integer periodTicks, Map<EnvironmentAttribute<?>, Track<?, ?>> tracks, Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers) {
        // Returns a value to the caller
        return new TimelineImpl(clock, periodTicks, tracks, timeMarkers);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /// Creates a new registry for timelines, loading the vanilla timelines.
    ///
    /// @see net.minestom.server.MinecraftServer to get an existing instance of the registry
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<Timeline> createDefaultRegistry(Registries registries) {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("timeline"),
                // Code statement
                REGISTRY_CODEC, registries, RegistryData.Resource.TIMELINES);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Track<T, Arg>(
            // Code statement
            Modifier<T, Arg> modifier,
            // Code statement
            List<Keyframe<Arg>> keyframes,
            // Code statement
            EaseFunction ease
    // Start of a method/block
    ) {
        // Start of a method/block
        public static <T, Arg> Codec<Track<T, Arg>> codec(EnvironmentAttribute<T> attribute) {
            //noinspection unchecked
            // Returns a value to the caller
            return attribute.type().modifierCodec().optional(new Modifier.Override<>(attribute.valueCodec()))
                    // Calls a method
                    .unionType("modifier", modifier -> fullCodec((Modifier<T, Arg>) modifier), Track::modifier);
        // End of a block/expression
        }

        // Start of a method/block
        private static <T, Arg> StructCodec<Track<T, Arg>> fullCodec(Modifier<T, Arg> modifier) {
            // Calls a method
            var keyframesCodec = Keyframe.codec(modifier.argumentCodec()).list();
            // Returns a value to the caller
            return StructCodec.struct(
                    // Code statement
                    "keyframes", keyframesCodec, Track::keyframes,
                    // Code statement
                    "ease", EaseFunction.CODEC.optional(EaseFunction.LINEAR), Track::ease,
                    // Calls a method
                    (keyframes, ease) -> new Track<>(modifier, keyframes, ease));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Keyframe<T>(int ticks, T value) {
        // Start of a method/block
        public static <T> Codec<Keyframe<T>> codec(Codec<T> valueCodec) {
            // Returns a value to the caller
            return StructCodec.struct(
                    // Code statement
                    "ticks", Codec.INT, Keyframe::ticks,
                    // Code statement
                    "value", valueCodec, Keyframe::value,
                    // Code statement
                    Keyframe::new);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TimeMarkerInfo(int ticks, boolean showInCommands) {
        // Assigns a value
        public static final Codec<TimeMarkerInfo> CODEC = StructCodec.struct(
                // Code statement
                "ticks", Codec.INT, TimeMarkerInfo::ticks,
                // Code statement
                "show_in_commands", Codec.BOOLEAN.optional(false), TimeMarkerInfo::showInCommands,
                // Code statement
                TimeMarkerInfo::new);
        // Assigns a value
        public static final Codec<TimeMarkerInfo> REGISTRY_CODEC = Codec.Either(Codec.INT, CODEC).transform(
                // Code statement
                it -> it.unify(TimeMarkerInfo::new, Function.identity()),
                // Calls a method
                it -> !it.showInCommands() ? Either.left(it.ticks()) : Either.right(it));

        // Start of a method/block
        public TimeMarkerInfo(int ticks) {
            // Calls a method
            this(ticks, false);
        // End of a block/expression
        }

        // Start of a method/block
        public TimeMarkerInfo {
            // Calls a method
            Check.argCondition(ticks < 0, "ticks must be positive");
        // End of a block/expression
        }

        // Start of a method/block
        public ClockTimeMarker clockTimeMarker(Timeline timeline) {
            // Returns a value to the caller
            return ClockTimeMarker.create(timeline.clock(), ticks(), timeline.periodTicks(), showInCommands());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private @UnknownNullability RegistryKey<WorldClock> clock;
        // Code statement
        private @Nullable Integer periodTicks;
        // Calls a method
        private final Map<EnvironmentAttribute<?>, Track<?, ?>> tracks = new HashMap<>();
        // Calls a method
        private final Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers = new HashMap<>();

        // Start of a method/block
        public Builder clock(RegistryKey<WorldClock> clock) {
            // Access to the current/parent object
            this.clock = clock;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder periodTicks(int ticks) {
            // Access to the current/parent object
            this.periodTicks = ticks;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder periodTicks(@Nullable Integer ticks) {
            // Access to the current/parent object
            this.periodTicks = ticks;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public <T, Arg> Builder track(EnvironmentAttribute<T> attribute, Track<T, Arg> track) {
            // Calls a method
            tracks.put(attribute, track);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder tracks(Map<EnvironmentAttribute<?>, Track<?, ?>> tracks) {
            // Access to the current/parent object
            this.tracks.putAll(tracks);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder timeMarker(RegistryKey<ClockTimeMarker> key, Timeline.TimeMarkerInfo info) {
            // Calls a method
            timeMarkers.put(key, info);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder timeMarkers(Map<RegistryKey<ClockTimeMarker>, Timeline.TimeMarkerInfo> timeMarkers) {
            // Access to the current/parent object
            this.timeMarkers.putAll(timeMarkers);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Timeline build() {
            // Returns a value to the caller
            return Timeline.create(clock, periodTicks, tracks, timeMarkers);
        // End of a block/expression
        }

    // End of a block/expression
    }
// End of a block/expression
}
