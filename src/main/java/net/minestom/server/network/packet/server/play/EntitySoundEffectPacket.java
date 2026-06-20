// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.sound.SoundEvent;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record EntitySoundEffectPacket(
        // Code statement
        SoundEvent soundEvent,
        // Code statement
        Sound.Source source,
        // Code statement
        int entityId,
        // Code statement
        float volume,
        // Code statement
        float pitch,
        // Code statement
        long seed
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntitySoundEffectPacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, EntitySoundEffectPacket value) {
            // Calls a method
            buffer.write(SoundEvent.NETWORK_TYPE, value.soundEvent);
            // Calls a method
            buffer.write(VAR_INT, AdventurePacketConvertor.getSoundSourceValue(value.source));
            // Calls a method
            buffer.write(VAR_INT, value.entityId);
            // Calls a method
            buffer.write(FLOAT, value.volume);
            // Calls a method
            buffer.write(FLOAT, value.pitch);
            // Calls a method
            buffer.write(LONG, value.seed);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public EntitySoundEffectPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new EntitySoundEffectPacket(buffer.read(SoundEvent.NETWORK_TYPE),
                    // Code statement
                    buffer.read(NetworkBuffer.Enum(Sound.Source.class)),
                    // Code statement
                    buffer.read(VAR_INT),
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
// End of a block/expression
}
