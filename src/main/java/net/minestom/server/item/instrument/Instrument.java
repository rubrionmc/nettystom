// Déclaration du paquet de ce fichier
package net.minestom.server.item.instrument;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.Holder;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryData;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Instrument extends Holder.Direct<Instrument>, Instruments permits InstrumentImpl {
    // Affecte une valeur
    NetworkBuffer.Type<Instrument> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            SoundEvent.NETWORK_TYPE, Instrument::soundEvent,
            // Instruction de code
            NetworkBuffer.FLOAT, Instrument::useDuration,
            // Instruction de code
            NetworkBuffer.FLOAT, Instrument::range,
            // Instruction de code
            NetworkBuffer.COMPONENT, Instrument::description,
            // Instruction de code
            InstrumentImpl::new);
    // Affecte une valeur
    Codec<Instrument> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "sound_event", SoundEvent.CODEC, Instrument::soundEvent,
            // Instruction de code
            "use_duration", Codec.FLOAT, Instrument::useDuration,
            // Instruction de code
            "range", Codec.FLOAT, Instrument::range,
            // Instruction de code
            "description", Codec.COMPONENT, Instrument::description,
            // Instruction de code
            InstrumentImpl::new);

    // Appelle une méthode
    NetworkBuffer.Type<Holder<Instrument>> NETWORK_TYPE = Holder.networkType(Registries::instrument, REGISTRY_NETWORK_TYPE);
    // Appelle une méthode
    Codec<Holder<Instrument>> CODEC = Holder.codec(Registries::instrument, REGISTRY_CODEC);

    // Instruction de code
    static Instrument create(
            // Instruction de code
            SoundEvent soundEvent,
            // Instruction de code
            float useDuration,
            // Instruction de code
            float range,
            // Instruction de code
            Component description
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new InstrumentImpl(soundEvent, useDuration, range, description);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for instruments, loading the vanilla instruments.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<Instrument> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("instrument"), REGISTRY_CODEC, RegistryData.Resource.INSTRUMENTS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    SoundEvent soundEvent();

    // Appelle une méthode
    float useDuration();

    // Début d'une méthode/d'un bloc
    default int useDurationTicks() {
        // Renvoie une valeur à l'appelant
        return (int) (useDuration() * ServerFlag.SERVER_TICKS_PER_SECOND);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    float range();

    // Appelle une méthode
    Component description();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private SoundEvent soundEvent;
        // Instruction de code
        private float useDuration;
        // Instruction de code
        private float range;
        // Instruction de code
        private Component description;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder soundEvent(SoundEvent soundEvent) {
            // Accès à l'objet courant/parent
            this.soundEvent = soundEvent;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder useDuration(float useDuration) {
            // Accès à l'objet courant/parent
            this.useDuration = useDuration;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder range(float range) {
            // Accès à l'objet courant/parent
            this.range = range;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder description(Component description) {
            // Accès à l'objet courant/parent
            this.description = description;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Instrument build() {
            // Renvoie une valeur à l'appelant
            return new InstrumentImpl(soundEvent, useDuration, range, description);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
