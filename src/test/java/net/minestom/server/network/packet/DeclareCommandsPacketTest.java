// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.packet.server.play.DeclareCommandsPacket.getFlag;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class DeclareCommandsPacketTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testWriteGameProfileArg() {
        // Calls a method
        var root = new DeclareCommandsPacket.Node();
        // Calls a method
        root.flags = getFlag(DeclareCommandsPacket.NodeType.ARGUMENT, false, false, false);
        // Assigns a value
        root.parser = ArgumentParserType.GAME_PROFILE;
        // Calls a method
        var packet = new DeclareCommandsPacket(List.of(root), 0);

        // Calls a method
        var array = NetworkBuffer.makeArray(DeclareCommandsPacket.SERIALIZER, packet);
        // Calls a method
        var readPacket = NetworkBuffer.wrap(array, 0, array.length).read(DeclareCommandsPacket.SERIALIZER);
        // Calls a method
        assertEquals(ArgumentParserType.GAME_PROFILE, readPacket.nodes().getFirst().parser);
    // End of a block/expression
    }
// End of a block/expression
}
