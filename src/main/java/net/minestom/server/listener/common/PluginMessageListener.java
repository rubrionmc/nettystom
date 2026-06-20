// Déclaration du paquet de ce fichier
package net.minestom.server.listener.common;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerPluginMessageEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;

// Déclaration de type (classe/interface/enum/record)
public class PluginMessageListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientPluginMessagePacket packet, Player player) {
        // Appelle une méthode
        PlayerPluginMessageEvent pluginMessageEvent = new PlayerPluginMessageEvent(player, packet.channel(), packet.data());
        // Appelle une méthode
        EventDispatcher.call(pluginMessageEvent);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
