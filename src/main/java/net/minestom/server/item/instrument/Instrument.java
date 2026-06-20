// Package declaration for this file
package net.minestom.server.item.instrument;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.Holder;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryData;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public sealed interface Instrument extends Holder.Direct<Instrument>, Instruments permits InstrumentImpl {
    // Assigns a value
    NetworkBuffer.Type<Instrument> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            SoundEvent.NETWORK_TYPE, Instrument::soundEvent,
            // Code statement
            NetworkBuffer.FLOAT, Instrument::useDuration,
            // Code statement
            NetworkBuffer.FLOAT, Instrument::range,
            // Code statement
            NetworkBuffer.COMPONENT, Instrument::description,
            // Code statement
            InstrumentImpl::new);
    // Assigns a value
    Codec<Instrument> REGISTRY_CODEC = StructCodec.struct(
            // Code statement
            "sound_event", SoundEvent.CODEC, Instrument::soundEvent,
            // Code statement
            "use_duration", Codec.FLOAT, Instrument::useDuration,
            // Code statement
            "range", Codec.FLOAT, Instrument::range,
            // Code statement
            "description", Codec.COMPONENT, Instrument::description,
            // Code statement
            InstrumentImpl::new);

    // Calls a method
    NetworkBuffer.Type<Holder<Instrument>> NETWORK_TYPE = Holder.networkType(Registries::instrument, REGISTRY_NETWORK_TYPE);
    // Calls a method
    Codec<Holder<Instrument>> CODEC = Holder.codec(Registries::instrument, REGISTRY_CODEC);

    // Code statement
    static Instrument create(
            // Code statement
            SoundEvent soundEvent,
            // Code statement
            float useDuration,
            // Code statement
            float range,
            // Code statement
            Component description
    // Start of a method/block
    ) {
        // Returns a value to the caller
        return new InstrumentImpl(soundEvent, useDuration, range, description);
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder() {
        // Returns a value to the caller
        return new Builder();
    // End of a block/expression
    }

    /**
     * <p>Creates a new registry for instruments, loading the vanilla instruments.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static DynamicRegistry<Instrument> createDefaultRegistry() {
        // Returns a value to the caller
        return DynamicRegistry.create(Key.key("instrument"), REGISTRY_CODEC, RegistryData.Resource.INSTRUMENTS);
    // End of a block/expression
    }

    // Calls a method
    SoundEvent soundEvent();

    // Calls a method
    float useDuration();

    // Start of a method/block
    default int useDurationTicks() {
        // Returns a value to the caller
        return (int) (useDuration() * ServerFlag.SERVER_TICKS_PER_SECOND);
    // End of a block/expression
    }

    // Calls a method
    float range();

    // Calls a method
    Component description();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private SoundEvent soundEvent;
        // Code statement
        private float useDuration;
        // Code statement
        private float range;
        // Code statement
        private Component description;

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
        public Builder useDuration(float useDuration) {
            // Access to the current/parent object
            this.useDuration = useDuration;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder range(float range) {
            // Access to the current/parent object
            this.range = range;
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
        public Instrument build() {
            // Returns a value to the caller
            return new InstrumentImpl(soundEvent, useDuration, range, description);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
