// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ClientPluginMessagePacketTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testClientPluginMessagePacket() {
        // Assigns a value
        var array = NetworkBuffer.makeArray(
                // Code statement
                ClientPluginMessagePacket.SERIALIZER,
                // Creates a new object
                new ClientPluginMessagePacket("channel", new byte[0]));

        // Calls a method
        var readBuffer = NetworkBuffer.wrap(array, 0, array.length);
        // Calls a method
        var packet = readBuffer.read(ClientPluginMessagePacket.SERIALIZER);

        // Calls a method
        assertEquals("channel", packet.channel());
        // Calls a method
        assertArrayEquals(new byte[0], packet.data());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testClientPluginMessagePacketClone() {
        // Assigns a value
        var bytes = new byte[]{0x10, 0x11};
        // Calls a method
        var message = new ClientPluginMessagePacket("channel", bytes);
        // Calls a method
        message.data()[0] = 0x00;
        // Calls a method
        assertArrayEquals(new byte[]{0x00, 0x11}, message.data());
        // Calls a method
        assertNotSame(message.data(), bytes);
    // End of a block/expression
    }

// End of a block/expression
}
