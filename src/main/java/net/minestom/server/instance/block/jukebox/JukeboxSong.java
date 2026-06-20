// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block.jukebox;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.*;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Déclaration de type (classe/interface/enum/record)
public sealed interface JukeboxSong extends Holder.Direct<JukeboxSong>, JukeboxSongs permits JukeboxSongImpl {
    // Affecte une valeur
    NetworkBuffer.Type<JukeboxSong> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            SoundEvent.NETWORK_TYPE, JukeboxSong::soundEvent,
            // Instruction de code
            NetworkBuffer.COMPONENT, JukeboxSong::description,
            // Instruction de code
            NetworkBuffer.FLOAT, JukeboxSong::lengthInSeconds,
            // Instruction de code
            NetworkBuffer.VAR_INT, JukeboxSong::comparatorOutput,
            // Instruction de code
            JukeboxSong::create);
    // Affecte une valeur
    Codec<JukeboxSong> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "sound_event", SoundEvent.CODEC, JukeboxSong::soundEvent,
            // Instruction de code
            "description", Codec.COMPONENT, JukeboxSong::description,
            // Instruction de code
            "length_in_seconds", Codec.FLOAT, JukeboxSong::lengthInSeconds,
            // Instruction de code
            "comparator_output", Codec.INT, JukeboxSong::comparatorOutput,
            // Instruction de code
            JukeboxSong::create);

    // This is a similar case to PaintingVariant, see comment there for why one of these is a holder and not the other.
    // However, in this case, this component _must_ be hashable, which uses the regular codec on the client which does not
    // support holders. So it is **never valid** to use a direct holder here, so we use a weirdly serialized registrykey here.
    // Affecte une valeur
    NetworkBuffer.Type<RegistryKey<JukeboxSong>> NETWORK_TYPE = Holder.networkType(Registries::jukeboxSong, REGISTRY_NETWORK_TYPE)
            // Appelle une méthode
            .transform(Holder::asKey, key -> key);
    // Appelle une méthode
    Codec<RegistryKey<JukeboxSong>> CODEC = RegistryKey.codec(Registries::jukeboxSong);

    // The network type of jukebox playable is an EitherHolder, but as discussed it always has to be a registry key,
    // so we just map to that type and dont think about it any more.
    // Affecte une valeur
    NetworkBuffer.Type<RegistryKey<JukeboxSong>> JUKEBOX_PLAYABLE_NETWORK_TYPE = NetworkBuffer.Either(NETWORK_TYPE, NETWORK_TYPE)
            // Appelle une méthode
            .transform(e -> ((Either.Left<RegistryKey<JukeboxSong>, RegistryKey<JukeboxSong>>) e).value(), Either::left);

    // Instruction de code
    static JukeboxSong create(
            // Instruction de code
            SoundEvent soundEvent,
            // Instruction de code
            Component description,
            // Instruction de code
            float lengthInSeconds,
            // Instruction de code
            int comparatorOutput
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new JukeboxSongImpl(soundEvent, description, lengthInSeconds, comparatorOutput);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for banner patterns, loading the vanilla banner patterns.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<JukeboxSong> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("jukebox_song"), REGISTRY_CODEC, RegistryData.Resource.JUKEBOX_SONGS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    SoundEvent soundEvent();

    // Appelle une méthode
    Component description();

    // Appelle une méthode
    float lengthInSeconds();

    // Appelle une méthode
    int comparatorOutput();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private SoundEvent soundEvent;
        // Instruction de code
        private Component description;
        // Instruction de code
        private float lengthInSeconds;
        // Affecte une valeur
        private int comparatorOutput = 0;

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
        public Builder description(Component description) {
            // Accès à l'objet courant/parent
            this.description = description;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder lengthInSeconds(float lengthInSeconds) {
            // Accès à l'objet courant/parent
            this.lengthInSeconds = lengthInSeconds;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder comparatorOutput(int comparatorOutput) {
            // Accès à l'objet courant/parent
            this.comparatorOutput = comparatorOutput;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public JukeboxSong build() {
            // Renvoie une valeur à l'appelant
            return new JukeboxSongImpl(soundEvent, description, lengthInSeconds, comparatorOutput);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
