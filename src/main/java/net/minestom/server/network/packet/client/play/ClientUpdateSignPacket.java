// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientUpdateSignPacket(
        // Code statement
        Point blockPosition,
        // Code statement
        boolean isFrontText,
        // Code statement
        List<String> lines
// Start of a method/block
) implements ClientPacket.Play {
    // Start of a method/block
    public ClientUpdateSignPacket {
        // Calls a method
        lines = List.copyOf(lines);
        // Branch: checks a condition
        if (lines.size() != 4) {
            // Throws an exception
            throw new IllegalArgumentException("Signs must have 4 lines!");
        // End of a block/expression
        }
        // Loop: repeats a block
        for (String line : lines) {
            // Branch: checks a condition
            if (line.length() > 384) {
                // Throws an exception
                throw new IllegalArgumentException("Signs must have a maximum of 384 characters per line!");
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<ClientUpdateSignPacket> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, ClientUpdateSignPacket value) {
            // Calls a method
            buffer.write(BLOCK_POSITION, value.blockPosition);
            // Calls a method
            buffer.write(BOOLEAN, value.isFrontText);
            // Calls a method
            buffer.write(STRING, value.lines.get(0));
            // Calls a method
            buffer.write(STRING, value.lines.get(1));
            // Calls a method
            buffer.write(STRING, value.lines.get(2));
            // Calls a method
            buffer.write(STRING, value.lines.get(3));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ClientUpdateSignPacket read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new ClientUpdateSignPacket(buffer.read(BLOCK_POSITION), buffer.read(BOOLEAN), readLines(buffer));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    private static List<String> readLines(NetworkBuffer reader) {
        // Returns a value to the caller
        return List.of(reader.read(STRING), reader.read(STRING),
                // Calls a method
                reader.read(STRING), reader.read(STRING));
    // End of a block/expression
    }
// End of a block/expression
}
