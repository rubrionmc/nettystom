// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientChunkBatchReceivedPacket;

// Déclaration de type (classe/interface/enum/record)
public final class ChunkBatchListener {

    // Début d'une méthode/d'un bloc
    public static void batchReceivedListener(ClientChunkBatchReceivedPacket packet, Player player) {
        // Appelle une méthode
        player.onChunkBatchReceived(packet.targetChunksPerTick());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
