// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public interface TestConnection {
    // Calls a method
    Player connect(Instance instance, Pos pos);

    // Calls a method
    <T extends ServerPacket> Collector<T> trackIncoming(Class<T> type);

    // Start of a method/block
    default Collector<ServerPacket> trackIncoming() {
        // Returns a value to the caller
        return trackIncoming(ServerPacket.class);
    // End of a block/expression
    }
// End of a block/expression
}
