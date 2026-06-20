// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound.Source;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record SoundEffectPacket(
        // Instruction de code
        SoundEvent soundEvent,
        // Instruction de code
        Source source,
        // Instruction de code
        Point origin,
        // Instruction de code
        float volume,
        // Instruction de code
        float pitch,
        // Instruction de code
        long seed
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SoundEffectPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, SoundEffectPacket value) {
            // Appelle une méthode
            buffer.write(SoundEvent.NETWORK_TYPE, value.soundEvent());
            // Appelle une méthode
            buffer.write(VAR_INT, AdventurePacketConvertor.getSoundSourceValue(value.source()));
            // Appelle une méthode
            buffer.write(INT, (int)(value.origin.x() * 8));
            // Appelle une méthode
            buffer.write(INT, (int)(value.origin.y() * 8));
            // Appelle une méthode
            buffer.write(INT, (int)(value.origin.z() * 8));
            // Appelle une méthode
            buffer.write(FLOAT, value.volume());
            // Appelle une méthode
            buffer.write(FLOAT, value.pitch());
            // Appelle une méthode
            buffer.write(LONG, value.seed());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SoundEffectPacket read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new SoundEffectPacket(buffer.read(SoundEvent.NETWORK_TYPE),
                    // Instruction de code
                    buffer.read(NetworkBuffer.Enum(Source.class)),
                    // Crée un nouvel objet
                    new Vec(buffer.read(INT) / 8.0, buffer.read(INT) / 8.0, buffer.read(INT) / 8.0),
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

    /**
     * @deprecated Use {@link #SoundEffectPacket(SoundEvent, Source, Point, float, float, long)}
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public SoundEffectPacket(SoundEvent soundEvent, Source source, int x, int y, int z, float volume, float pitch, long seed) {
        // Appelle une méthode
        this(soundEvent, source, new Vec(x, y, z), volume, pitch, seed);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated Use {@link #origin()} with {@link Point#blockX()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public int x() {
        // Renvoie une valeur à l'appelant
        return origin.blockX();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated Use {@link #origin()} with {@link Point#blockY()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public int y() {
        // Renvoie une valeur à l'appelant
        return origin.blockY();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated Use {@link #origin()} with {@link Point#blockZ()} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Début d'une méthode/d'un bloc
    public int z() {
        // Renvoie une valeur à l'appelant
        return origin.blockZ();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
