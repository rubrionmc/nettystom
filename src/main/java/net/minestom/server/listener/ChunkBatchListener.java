// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientChunkBatchReceivedPacket;

// Type declaration (class/interface/enum/record)
public final class ChunkBatchListener {

    // Start of a method/block
    public static void batchReceivedListener(ClientChunkBatchReceivedPacket packet, Player player) {
        // Calls a method
        player.onChunkBatchReceived(packet.targetChunksPerTick());
    // End of a block/expression
    }
// End of a block/expression
}
