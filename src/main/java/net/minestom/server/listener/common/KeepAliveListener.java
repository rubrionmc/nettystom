// Déclaration du paquet de ce fichier
package net.minestom.server.listener.common;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientKeepAlivePacket;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Déclaration de type (classe/interface/enum/record)
public final class KeepAliveListener {
    // Appelle une méthode
    private static final Component KICK_MESSAGE = Component.text("Bad Keep Alive packet", NamedTextColor.RED);

    // Début d'une méthode/d'un bloc
    public static void listener(ClientKeepAlivePacket packet, Player player) {
        // Appelle une méthode
        final long packetId = packet.id();
        // Embranchement : vérifie une condition
        if (packetId != player.getLastKeepAlive()) {
            // Appelle une méthode
            player.kick(KICK_MESSAGE);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        player.refreshAnswerKeepAlive(true);
        // Update latency
        // Appelle une méthode
        final long latencyNanos = System.nanoTime() - packetId;

        // Appelle une méthode
        final int latency = (int) TimeUnit.NANOSECONDS.toMillis(latencyNanos);
        // Appelle une méthode
        player.refreshLatency(latency);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
