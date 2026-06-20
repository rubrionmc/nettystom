// Déclaration du paquet de ce fichier
package net.minestom.server.listener.common;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerSettingsChangeEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientSettingsPacket;

// Déclaration de type (classe/interface/enum/record)
public final class SettingsListener {
    // Début d'une méthode/d'un bloc
    public static void listener(ClientSettingsPacket packet, Player player) {
        // Since viewDistance bounds checking is performed in the refresh function, it is not necessary to check it here
        // Appelle une méthode
        player.refreshSettings(packet.settings());
        // Appelle une méthode
        EventDispatcher.call(new PlayerSettingsChangeEvent(player));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
