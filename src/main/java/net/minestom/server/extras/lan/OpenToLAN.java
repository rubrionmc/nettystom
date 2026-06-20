// Déclaration du paquet de ce fichier
package net.minestom.server.extras.lan;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerListPingEvent;
// Import d'une classe nécessaire
import net.minestom.server.timer.Task;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Cooldown;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.DatagramPacket;
// Import d'une classe nécessaire
import java.net.DatagramSocket;
// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketException;
// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.Objects;

// Import statique d'un membre
import static net.minestom.server.ping.ServerListPingType.OPEN_TO_LAN;

/**
 * Utility class to manage opening the server to LAN. Note that this <b>doesn't</b> actually
 * open your server to LAN if it isn't already visible to anyone on your local network.
 * Instead it simply sends the packets needed to trick the Minecraft client into thinking
 * that this is a single-player world that has been opened to LAN for it to be displayed on
 * the bottom of the server list.
 *
 * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Server_List_Ping#Ping_via_LAN_(Open_to_LAN_in_Singleplayer)">the Minecraft wiki</a>
 */
// Déclaration de type (classe/interface/enum/record)
public class OpenToLAN {
    // Appelle une méthode
    private static final InetSocketAddress PING_ADDRESS = new InetSocketAddress("224.0.2.60", 4445);

    // Appelle une méthode
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenToLAN.class);

    // Instruction de code
    private static volatile Cooldown eventCooldown;
    // Affecte une valeur
    private static volatile DatagramSocket socket = null;
    // Affecte une valeur
    private static volatile DatagramPacket packet = null;
    // Affecte une valeur
    private static volatile Task task = null;

    // Début d'une méthode/d'un bloc
    private OpenToLAN() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Opens the server to LAN with the default config.
     *
     * @return {@code true} if it was opened successfully, {@code false} otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean open() {
        // Renvoie une valeur à l'appelant
        return open(new OpenToLANConfig());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Opens the server to LAN.
     *
     * @param config the configuration
     * @return {@code true} if it was opened successfully, {@code false} otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean open(OpenToLANConfig config) {
        // Appelle une méthode
        Objects.requireNonNull(config, "config");
        // Embranchement : vérifie une condition
        if (socket != null) return false;

        // Gestion des exceptions
        try {
            // Appelle une méthode
            socket = new DatagramSocket(config.port);
        // Début d'une méthode/d'un bloc
        } catch (SocketException e) {
            // Appelle une méthode
            LOGGER.warn("Could not bind to the port!", e);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        eventCooldown = new Cooldown(config.delayBetweenEvent);
        // Affecte une valeur
        task = MinecraftServer.getSchedulerManager().buildTask(OpenToLAN::ping)
                // Instruction de code
                .repeat(config.delayBetweenPings)
                // Appelle une méthode
                .schedule();
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Closes the server to LAN.
     *
     * @return {@code true} if it was closed, {@code false} if it was already closed
     */
    // Début d'une méthode/d'un bloc
    public static boolean close() {
        // Embranchement : vérifie une condition
        if (socket == null) return false;
        // Appelle une méthode
        task.cancel();
        // Appelle une méthode
        socket.close();

        // Affecte une valeur
        task = null;
        // Affecte une valeur
        socket = null;
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the server is currently opened to LAN.
     *
     * @return {@code true} if it is, {@code false} otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean isOpen() {
        // Renvoie une valeur à l'appelant
        return socket != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Performs the ping.
     */
    // Début d'une méthode/d'un bloc
    private static void ping() {
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // Gestion des exceptions
            try {
                // Embranchement : vérifie une condition
                if (!MinecraftServer.getServer().isOpen()) return;
                // Embranchement : vérifie une condition
                if (packet == null || eventCooldown.isReady(System.nanoTime())) {
                    // Appelle une méthode
                    final ServerListPingEvent event = new ServerListPingEvent(OPEN_TO_LAN);
                    // Appelle une méthode
                    EventDispatcher.call(event);

                    // Appelle une méthode
                    final byte[] data = OPEN_TO_LAN.getPingResponse(event.getStatus()).getBytes(StandardCharsets.UTF_8);
                    // Appelle une méthode
                    packet = new DatagramPacket(data, data.length, PING_ADDRESS);
                    // Appelle une méthode
                    eventCooldown.refreshLastUpdate(System.nanoTime());
                // Fin d'un bloc/d'une expression
                }

                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    socket.send(packet);
                // Début d'une méthode/d'un bloc
                } catch (IOException e) {
                    // Appelle une méthode
                    LOGGER.warn("Could not send Open to LAN packet!", e);
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
