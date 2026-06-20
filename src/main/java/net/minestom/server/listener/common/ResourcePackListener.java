// Déclaration du paquet de ce fichier
package net.minestom.server.listener.common;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerResourcePackStatusEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientResourcePackStatusPacket;

// Déclaration de type (classe/interface/enum/record)
public class ResourcePackListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientResourcePackStatusPacket packet, Player player) {
        // Appelle une méthode
        EventDispatcher.call(new PlayerResourcePackStatusEvent(player, packet.id(), packet.status()));
        // Embranchement : vérifie une condition
        if (!player.isOnline()) return;

        // Run adventure callbacks for the resource pack
        // Appelle une méthode
        player.onResourcePackStatus(packet.id(), packet.status());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
