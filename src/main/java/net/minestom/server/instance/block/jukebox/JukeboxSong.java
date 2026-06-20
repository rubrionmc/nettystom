// Package declaration for this file
package net.minestom.server.instance.block.jukebox;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.*;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public sealed interface JukeboxSong extends Holder.Direct<JukeboxSong>, JukeboxSongs permits JukeboxSongImpl {
    // Assigns a value
    NetworkBuffer.Type<JukeboxSong> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            SoundEvent.NETWORK_TYPE, JukeboxSong::soundEvent,
            // Code statement
            NetworkBuffer.COMPONENT, JukeboxSong::description,
            // Code statement
            NetworkBuffer.FLOAT, JukeboxSong::lengthInSeconds,
            // Code statement
            NetworkBuffer.VAR_INT, JukeboxSong::comparatorOutput,
            // Code statement
            JukeboxSong::create);
    // Assigns a value
    Codec<JukeboxSong> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "sound_event", SoundEvent.CODEC, JukeboxSong::soundEvent,
            // Code statement
            "description", Codec.COMPONENT, JukeboxSong::description,
            // Code statement
            "length_in_seconds", Codec.FLOAT, JukeboxSong::lengthInSeconds,
            // Code statement
            "comparator_output", Codec.INT, JukeboxSong::comparatorOutput,
            // Code statement
            JukeboxSong::create);

    // This is a similar case to PaintingVariant, see comment there for why one of these is a holder and not the other.
    // However, in this case, this component _must_ be hashable, which uses the regular codec on the client which does not
    // support holders. So it is **never valid** to use a direct holder here, so we use a weirdly serialized registrykey here.
    // Assigns a value
    NetworkBuffer.Type<RegistryKey<JukeboxSong>> NETWORK_TYPE = Holder.networkType(Registries::jukeboxSong, REGISTRY_NETWORK_TYPE)
            // Calls a method
            .transform(Holder::asKey, key -> key);
    // Calls a method
    Codec<RegistryKey<JukeboxSong>> CODEC = RegistryKey.codec(Registries::jukeboxSong);

    // The network type of jukebox playable is an EitherHolder, but as discussed it always has to be a registry key,
    // so we just map to that type and dont think about it any more.
    // Assigns a value
    NetworkBuffer.Type<RegistryKey<JukeboxSong>> JUKEBOX_PLAYABLE_NETWORK_TYPE = NetworkBuffer.Either(NETWORK_TYPE, NETWORK_TYPE)
            // Calls a method
            .transform(e -> ((Either.Left<RegistryKey<JukeboxSong>, RegistryKey<JukeboxSong>>) e).value(), Either::left);

    // Code statement
    static JukeboxSong create(
            // Code statement
            SoundEvent soundEvent,
            // Code statement
            Component description,
            // Code statement
            float lengthInSeconds,
            // Code statement
            int comparatorOutput
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new JukeboxSongImpl(soundEvent, description, lengthInSeconds, comparatorOutput);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for banner patterns, loading the vanilla banner patterns.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<JukeboxSong> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("jukebox_song"), REGISTRY_CODEC, RegistryData.Resource.JUKEBOX_SONGS);
    // End of a block/expression
    }

    // Calls a method
    SoundEvent soundEvent();

    // Calls a method
    Component description();

    // Calls a method
    float lengthInSeconds();

    // Calls a method
    int comparatorOutput();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private SoundEvent soundEvent;
        // Code statement
        private Component description;
        // Code statement
        private float lengthInSeconds;
        // Assigns a value
        private int comparatorOutput = 0;

        // Start of a method/block
        private Builder() {
        // End of a block/expression
        }

        // Start of a method/block
        public Builder soundEvent(SoundEvent soundEvent) {
            // Access to the current/parent object
            this.soundEvent = soundEvent;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder description(Component description) {
            // Access to the current/parent object
            this.description = description;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder lengthInSeconds(float lengthInSeconds) {
            // Access to the current/parent object
            this.lengthInSeconds = lengthInSeconds;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder comparatorOutput(int comparatorOutput) {
            // Access to the current/parent object
            this.comparatorOutput = comparatorOutput;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public JukeboxSong build() {
            // Returns a value to the caller
            return new JukeboxSongImpl(soundEvent, description, lengthInSeconds, comparatorOutput);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
