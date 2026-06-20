// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.PlayerHand;
// Import of a required class
import net.minestom.server.network.packet.PacketReading;
// Import of a required class
import net.minestom.server.network.packet.PacketWriting;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientAnimationPacket;
// Import of a required class
import net.minestom.server.network.packet.server.CachedPacket;
// Import of a required class
import net.minestom.server.network.packet.server.LazyPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SystemChatPacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.zip.DataFormatException;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class SendablePacketTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lazy() {
        // Calls a method
        var packet = new SystemChatPacket(Component.text("Hello World!"), false);
        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);
        // Assigns a value
        var lazy = new LazyPacket(() -> {
            // Branch: checks a condition
            if (called.getAndSet(true))
                // Calls a method
                fail();
            // Returns a value to the caller
            return packet;
        // End of a block/expression
        });
        // Calls a method
        assertSame(packet, lazy.packet());
        // Calls a method
        assertSame(packet, lazy.packet());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cached() {
        // Calls a method
        var packet = new SystemChatPacket(Component.text("Hello World!"), false);
        // Calls a method
        var cached = new CachedPacket(packet);
        // Calls a method
        assertSame(packet, cached.packet(ConnectionState.PLAY));

        // Assigns a value
        var buffer = PacketWriting.allocateTrimmedPacket(ConnectionState.PLAY, packet,
                // Calls a method
                MinecraftServer.getCompressionThreshold());
        // Calls a method
        var cachedBuffer = cached.body(ConnectionState.PLAY);
        // Calls a method
        assertTrue(NetworkBuffer.equals(buffer, cachedBuffer));
        // May fail in the very unlikely case where soft references are cleared
        // Rare enough to make this test worth it
        // Calls a method
        assertSame(cached.body(ConnectionState.PLAY), cachedBuffer);

        // Calls a method
        assertSame(packet, cached.packet(ConnectionState.PLAY));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void trimmed() throws DataFormatException {
        // Calls a method
        var packet = new ClientAnimationPacket(PlayerHand.MAIN);

        // Calls a method
        var buffer = PacketWriting.allocateTrimmedPacket(ConnectionState.PLAY, packet, 0);

        // Calls a method
        var result = PacketReading.readClient(buffer, ConnectionState.PLAY, false);
        // Branch: checks a condition
        if (!(result instanceof PacketReading.Result.Success<ClientPacket>(
                // Code statement
                List<PacketReading.ParsedPacket<ClientPacket>> packets
        // Start of a method/block
        ))) {
            // Calls a method
            fail();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        assertEquals(1, packets.size());
        // Calls a method
        ClientPacket readPacket = packets.getFirst().packet();
        // Calls a method
        assertEquals(packet, readPacket);
    // End of a block/expression
    }
// End of a block/expression
}
