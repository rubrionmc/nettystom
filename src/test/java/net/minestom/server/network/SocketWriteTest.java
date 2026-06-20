// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.minestom.server.network.packet.PacketVanilla;
// Import of a required class
import net.minestom.server.network.packet.PacketWriting;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.nio.charset.StandardCharsets;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Type declaration (class/interface/enum/record)
public class SocketWriteTest {

    // Type declaration (class/interface/enum/record)
    record IntPacket(int value) implements ServerPacket.Play {
        // Assigns a value
        public static final NetworkBuffer.Type<IntPacket> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                INT, IntPacket::value,
                // Code statement
                IntPacket::new);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CompressiblePacket(String value) implements ServerPacket.Play {
        // Assigns a value
        public static final NetworkBuffer.Type<CompressiblePacket> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, CompressiblePacket::value,
                // Code statement
                CompressiblePacket::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void writeSingleUncompressed() {
        // Calls a method
        var packet = new IntPacket(5);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, -1);

        // 3 bytes length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Calls a method
        assertEquals(3 + 1 + 4, buffer.writeIndex(), "Invalid buffer position");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void writeMultiUncompressed() {
        // Calls a method
        var packet = new IntPacket(5);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, -1);
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, -1);

        // 3 bytes length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Calls a method
        assertEquals((3 + 1 + 4) * 2, buffer.writeIndex(), "Invalid buffer position");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void writeSingleCompressed() {
        // Calls a method
        var string = "Hello world!".repeat(200);
        // Calls a method
        var stringLength = string.getBytes(StandardCharsets.UTF_8).length;
        // Calls a method
        var lengthLength = getVarIntSize(stringLength);

        // Calls a method
        var packet = new CompressiblePacket(string);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, CompressiblePacket.SERIALIZER, 1, packet, 256);

        // 3 bytes packet length [var-int] + 3 bytes data length [var-int] + 1 byte packet id [var-int] + payload
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Calls a method
        assertNotEquals(3 + 3 + 1 + lengthLength + stringLength, buffer.writeIndex(), "Buffer position does not account for compression");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void writeSingleCompressedSmall() {
        // Calls a method
        var packet = new IntPacket(5);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, 256);

        // 3 bytes packet length [var-int] + 3 bytes data length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Calls a method
        assertEquals(3 + 3 + 1 + 4, buffer.writeIndex(), "Invalid buffer position");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void writeMultiCompressedSmall() {
        // Calls a method
        var packet = new IntPacket(5);

        // Calls a method
        var buffer = PacketVanilla.PACKET_POOL.get();
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, 256);
        // Calls a method
        PacketWriting.writeFramedPacket(buffer, IntPacket.SERIALIZER, 1, packet, 256);

        // 3 bytes packet length [var-int] + 3 bytes data length [var-int] + 1 byte packet id [var-int] + 4 bytes int
        // The 3 bytes var-int length is hardcoded for performance purpose, could change in the future
        // Calls a method
        assertEquals((3 + 3 + 1 + 4) * 2, buffer.writeIndex(), "Invalid buffer position");
    // End of a block/expression
    }

    // Start of a method/block
    private static int getVarIntSize(int input) {
        // Returns a value to the caller
        return (input & 0xFFFFFF80) == 0
                // Code statement
                ? 1 : (input & 0xFFFFC000) == 0
                // Code statement
                ? 2 : (input & 0xFFE00000) == 0
                // Code statement
                ? 3 : (input & 0xF0000000) == 0
                // Code statement
                ? 4 : 5;
    // End of a block/expression
    }
// End of a block/expression
}
