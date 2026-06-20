// Package declaration for this file
package net.minestom.server.extras.lan;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.server.ServerListPingEvent;
// Import of a required class
import net.minestom.server.timer.Task;
// Import of a required class
import net.minestom.server.utils.time.Cooldown;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.DatagramPacket;
// Import of a required class
import java.net.DatagramSocket;
// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.SocketException;
// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.util.Objects;

// Static import of a member
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
// Type declaration (class/interface/enum/record)
public class OpenToLAN {
    // Calls a method
    private static final InetSocketAddress PING_ADDRESS = new InetSocketAddress("224.0.2.60", 4445);

    // Calls a method
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenToLAN.class);

    // Code statement
    private static volatile Cooldown eventCooldown;
    // Assigns a value
    private static volatile DatagramSocket socket = null;
    // Assigns a value
    private static volatile DatagramPacket packet = null;
    // Assigns a value
    private static volatile Task task = null;

    // Start of a method/block
    private OpenToLAN() {
    // End of a block/expression
    }

    /**
     * Opens the server to LAN with the default config.
     *
     * @return {@code true} if it was opened successfully, {@code false} otherwise
     */
    // Start of a method/block
    public static boolean open() {
        // Returns a value to the caller
        return open(new OpenToLANConfig());
    // End of a block/expression
    }

    /**
     * Opens the server to LAN.
     *
     * @param config the configuration
     * @return {@code true} if it was opened successfully, {@code false} otherwise
     */
    // Start of a method/block
    public static boolean open(OpenToLANConfig config) {
        // Calls a method
        Objects.requireNonNull(config, "config");
        // Branch: checks a condition
        if (socket != null) return false;

        // Exception handling
        try {
            // Calls a method
            socket = new DatagramSocket(config.port);
        // Start of a method/block
        } catch (SocketException e) {
            // Calls a method
            LOGGER.warn("Could not bind to the port!", e);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Calls a method
        eventCooldown = new Cooldown(config.delayBetweenEvent);
        // Assigns a value
        task = MinecraftServer.getSchedulerManager().buildTask(OpenToLAN::ping)
                // Code statement
                .repeat(config.delayBetweenPings)
                // Calls a method
                .schedule();
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Closes the server to LAN.
     *
     * @return {@code true} if it was closed, {@code false} if it was already closed
     */
    // Start of a method/block
    public static boolean close() {
        // Branch: checks a condition
        if (socket == null) return false;
        // Calls a method
        task.cancel();
        // Calls a method
        socket.close();

        // Assigns a value
        task = null;
        // Assigns a value
        socket = null;
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Checks if the server is currently opened to LAN.
     *
     * @return {@code true} if it is, {@code false} otherwise
     */
    // Start of a method/block
    public static boolean isOpen() {
        // Returns a value to the caller
        return socket != null;
    // End of a block/expression
    }

    /**
     * Performs the ping.
     */
    // Start of a method/block
    private static void ping() {
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // Exception handling
            try {
                // Branch: checks a condition
                if (!MinecraftServer.getServer().isOpen()) return;
                // Branch: checks a condition
                if (packet == null || eventCooldown.isReady(System.nanoTime())) {
                    // Calls a method
                    final ServerListPingEvent event = new ServerListPingEvent(OPEN_TO_LAN);
                    // Calls a method
                    EventDispatcher.call(event);

                    // Calls a method
                    final byte[] data = OPEN_TO_LAN.getPingResponse(event.getStatus()).getBytes(StandardCharsets.UTF_8);
                    // Calls a method
                    packet = new DatagramPacket(data, data.length, PING_ADDRESS);
                    // Calls a method
                    eventCooldown.refreshLastUpdate(System.nanoTime());
                // End of a block/expression
                }

                // Exception handling
                try {
                    // Calls a method
                    socket.send(packet);
                // Start of a method/block
                } catch (IOException e) {
                    // Calls a method
                    LOGGER.warn("Could not send Open to LAN packet!", e);
                // End of a block/expression
                }
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
