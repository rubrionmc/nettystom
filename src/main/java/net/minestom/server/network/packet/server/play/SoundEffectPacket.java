// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.sound.Sound.Source;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record SoundEffectPacket(
        // Code statement
        SoundEvent soundEvent,
        // Code statement
        Source source,
        // Code statement
        Point origin,
        // Code statement
        float volume,
        // Code statement
        float pitch,
        // Code statement
        long seed
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SoundEffectPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, SoundEffectPacket value) {
            // Calls a method
            buffer.write(SoundEvent.NETWORK_TYPE, value.soundEvent());
            // Calls a method
            buffer.write(VAR_INT, AdventurePacketConvertor.getSoundSourceValue(value.source()));
            // Calls a method
            buffer.write(INT, (int)(value.origin.x() * 8));
            // Calls a method
            buffer.write(INT, (int)(value.origin.y() * 8));
            // Calls a method
            buffer.write(INT, (int)(value.origin.z() * 8));
            // Calls a method
            buffer.write(FLOAT, value.volume());
            // Calls a method
            buffer.write(FLOAT, value.pitch());
            // Calls a method
            buffer.write(LONG, value.seed());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SoundEffectPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new SoundEffectPacket(buffer.read(SoundEvent.NETWORK_TYPE),
                    // Code statement
                    buffer.read(NetworkBuffer.Enum(Source.class)),
                    // Creates a new object
                    new Vec(buffer.read(INT) / 8.0, buffer.read(INT) / 8.0, buffer.read(INT) / 8.0),
                    // Code statement
                    buffer.read(FLOAT),
                    // Code statement
                    buffer.read(FLOAT),
                    // Calls a method
                    buffer.read(LONG));
        // End of a block/expression
        }
    // End of a block/expression
    };

    /**
     * @deprecated Use {@link #SoundEffectPacket(SoundEvent, Source, Point, float, float, long)}
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public SoundEffectPacket(SoundEvent soundEvent, Source source, int x, int y, int z, float volume, float pitch, long seed) {
        // Calls a method
        this(soundEvent, source, new Vec(x, y, z), volume, pitch, seed);
    // End of a block/expression
    }

    /**
     * @deprecated Use {@link #origin()} with {@link Point#blockX()} instead.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public int x() {
        // Returns a value to the caller
        return origin.blockX();
    // End of a block/expression
    }

    /**
     * @deprecated Use {@link #origin()} with {@link Point#blockY()} instead.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public int y() {
        // Returns a value to the caller
        return origin.blockY();
    // End of a block/expression
    }

    /**
     * @deprecated Use {@link #origin()} with {@link Point#blockZ()} instead.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Start of a method/block
    public int z() {
        // Returns a value to the caller
        return origin.blockZ();
    // End of a block/expression
    }
// End of a block/expression
}
