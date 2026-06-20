// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;

// Type declaration (class/interface/enum/record)
public record ClientInputPacket(byte flags) implements ClientPacket.Play {
    // Assigns a value
    private static final byte FLAG_FORWARD = 1;
    // Assigns a value
    private static final byte FLAG_BACKWARD = 1 << 1;
    // Assigns a value
    private static final byte FLAG_LEFT = 1 << 2;
    // Assigns a value
    private static final byte FLAG_RIGHT = 1 << 3;
    // Assigns a value
    private static final byte FLAG_JUMP = 1 << 4;
    // Assigns a value
    private static final byte FLAG_SHIFT = 1 << 5;
    // Assigns a value
    private static final byte FLAG_SPRINT = 1 << 6;

    // Assigns a value
    public static final NetworkBuffer.Type<ClientInputPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, ClientInputPacket::flags,
            // Code statement
            ClientInputPacket::new);

    // Start of a method/block
    public ClientInputPacket(boolean forward, boolean backward, boolean left, boolean right, boolean jump, boolean shift, boolean sprint) {
        // Code statement
        this((byte) ((forward ? FLAG_FORWARD : 0) |
                // Code statement
                (backward ? FLAG_BACKWARD : 0) |
                // Code statement
                (left ? FLAG_LEFT : 0) |
                // Code statement
                (right ? FLAG_RIGHT : 0) |
                // Code statement
                (jump ? FLAG_JUMP : 0) |
                // Code statement
                (shift ? FLAG_SHIFT : 0) |
                // Calls a method
                (sprint ? FLAG_SPRINT : 0)));
    // End of a block/expression
    }

    // Start of a method/block
    public boolean forward() {
        // Returns a value to the caller
        return (flags & FLAG_FORWARD) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean backward() {
        // Returns a value to the caller
        return (flags & FLAG_BACKWARD) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean left() {
        // Returns a value to the caller
        return (flags & FLAG_LEFT) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean right() {
        // Returns a value to the caller
        return (flags & FLAG_RIGHT) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean jump() {
        // Returns a value to the caller
        return (flags & FLAG_JUMP) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean shift() {
        // Returns a value to the caller
        return (flags & FLAG_SHIFT) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean sprint() {
        // Returns a value to the caller
        return (flags & FLAG_SPRINT) != 0;
    // End of a block/expression
    }
// End of a block/expression
}
