// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.BlockVec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerPickBlockEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerPickEntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPickItemFromBlockPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientPickItemFromEntityPacket;

// Déclaration de type (classe/interface/enum/record)
public class PlayerPickListener {

    // Début d'une méthode/d'un bloc
    public static void playerPickBlockListener(ClientPickItemFromBlockPacket packet, Player player) {
        // Appelle une méthode
        final Instance instance = player.getInstance();
        // Embranchement : vérifie une condition
        if (instance == null) return;
        // Appelle une méthode
        final Block block = instance.getBlock(packet.pos());
        // Appelle une méthode
        final boolean includeData = packet.includeData();

        // Appelle une méthode
        PlayerPickBlockEvent playerPickBlockEvent = new PlayerPickBlockEvent(player, block, new BlockVec(packet.pos()), includeData);
        // Appelle une méthode
        EventDispatcher.call(playerPickBlockEvent);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void playerPickEntityListener(ClientPickItemFromEntityPacket packet, Player player) {
        // Appelle une méthode
        final Instance instance = player.getInstance();
        // Embranchement : vérifie une condition
        if (instance == null) return;
        // Appelle une méthode
        final Entity entity = instance.getEntityById(packet.entityId());
        // Appelle une méthode
        final boolean includeData = packet.includeData();

        // Appelle une méthode
        PlayerPickEntityEvent playerPickEntityEvent = new PlayerPickEntityEvent(player, entity, includeData);
        // Appelle une méthode
        EventDispatcher.call(playerPickEntityEvent);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
