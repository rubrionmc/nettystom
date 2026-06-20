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
import org.jetbrains.annotations.Nullable;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record StopSoundPacket(byte flags, @Nullable Sound.Source source,
                              // Annotation pour l'élément suivant
                              @Nullable String sound) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<StopSoundPacket> SERIALIZER = new Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, StopSoundPacket value) {
            // Appelle une méthode
            buffer.write(BYTE, value.flags());
            // Embranchement : vérifie une condition
            if (value.flags == 3 || value.flags == 1) {
                // Instruction de code
                assert value.source != null;
                // Appelle une méthode
                buffer.write(VAR_INT, AdventurePacketConvertor.getSoundSourceValue(value.source));
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (value.flags == 2 || value.flags == 3) {
                // Instruction de code
                assert value.sound != null;
                // Appelle une méthode
                buffer.write(STRING, value.sound);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StopSoundPacket read(NetworkBuffer buffer) {
            // Appelle une méthode
            byte flags = buffer.read(BYTE);
            // Appelle une méthode
            var source = flags == 3 || flags == 1 ? buffer.read(NetworkBuffer.Enum(Sound.Source.class)) : null;
            // Appelle une méthode
            var sound = flags == 2 || flags == 3 ? buffer.read(STRING) : null;
            // Renvoie une valeur à l'appelant
            return new StopSoundPacket(flags, source, sound);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
