// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record EntitySoundEffectPacket(
        // Instruction de code
        SoundEvent soundEvent,
        // Instruction de code
        Sound.Source source,
        // Instruction de code
        int entityId,
        // Instruction de code
        float volume,
        // Instruction de code
        float pitch,
        // Instruction de code
        long seed
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<EntitySoundEffectPacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EntitySoundEffectPacket value) {
            // Appelle une méthode
            buffer.write(SoundEvent.NETWORK_TYPE, value.soundEvent);
            // Appelle une méthode
            buffer.write(VAR_INT, AdventurePacketConvertor.getSoundSourceValue(value.source));
            // Appelle une méthode
            buffer.write(VAR_INT, value.entityId);
            // Appelle une méthode
            buffer.write(FLOAT, value.volume);
            // Appelle une méthode
            buffer.write(FLOAT, value.pitch);
            // Appelle une méthode
            buffer.write(LONG, value.seed);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EntitySoundEffectPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new EntitySoundEffectPacket(buffer.read(SoundEvent.NETWORK_TYPE),
                    // Instruction de code
                    buffer.read(NetworkBuffer.Enum(Sound.Source.class)),
                    // Instruction de code
                    buffer.read(VAR_INT),
                    // Instruction de code
                    buffer.read(FLOAT),
                    // Instruction de code
                    buffer.read(FLOAT),
                    // Appelle une méthode
                    buffer.read(LONG));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
