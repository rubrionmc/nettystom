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
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record StopSoundPacket(byte flags, @Nullable Sound.Source source,
                              // Annotation for the following element
                              @Nullable String sound) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<StopSoundPacket> SERIALIZER = new Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, StopSoundPacket value) {
            // Calls a method
            buffer.write(BYTE, value.flags());
            // Branch: checks a condition
            if (value.flags == 3 || value.flags == 1) {
                // Code statement
                assert value.source != null;
                // Calls a method
                buffer.write(VAR_INT, AdventurePacketConvertor.getSoundSourceValue(value.source));
            // End of a block/expression
            }
            // Branch: checks a condition
            if (value.flags == 2 || value.flags == 3) {
                // Code statement
                assert value.sound != null;
                // Calls a method
                buffer.write(STRING, value.sound);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public StopSoundPacket read(NetworkBuffer buffer) {
            // Calls a method
            byte flags = buffer.read(BYTE);
            // Calls a method
            var source = flags == 3 || flags == 1 ? buffer.read(NetworkBuffer.Enum(Sound.Source.class)) : null;
            // Calls a method
            var sound = flags == 2 || flags == 3 ? buffer.read(STRING) : null;
            // Returns a value to the caller
            return new StopSoundPacket(flags, source, sound);
        // End of a block/expression
        }
    // End of a block/expression
    };
// End of a block/expression
}
