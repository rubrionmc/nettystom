// Package declaration for this file
package net.minestom.server.listener.preplay;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.server.ClientPingServerEvent;
// Import of a required class
import net.minestom.server.event.server.ServerListPingEvent;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientPingRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.PingResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.ping.ServerListPingType;

// Type declaration (class/interface/enum/record)
public final class StatusListener {

    // Start of a method/block
    public static void requestListener(StatusRequestPacket packet, PlayerConnection connection) {
        // Calls a method
        final ServerListPingType pingVersion = ServerListPingType.fromModernProtocolVersion(connection.getProtocolVersion());
        // Calls a method
        final ServerListPingEvent serverListPingEvent = new ServerListPingEvent(connection, pingVersion);
        // Code statement
        EventDispatcher.callCancellable(serverListPingEvent, () ->
                // Calls a method
                connection.sendPacket(new ResponsePacket(pingVersion.getPingResponse(serverListPingEvent.getStatus()))));
        // Calls a method
        EventsJFR.newServerPing(connection.getRemoteAddress().toString()).commit();
    // End of a block/expression
    }

    // Start of a method/block
    public static void pingRequestListener(ClientPingRequestPacket packet, PlayerConnection connection) {
        // Calls a method
        final ClientPingServerEvent clientPingEvent = new ClientPingServerEvent(connection, packet.number());
        // Calls a method
        EventDispatcher.call(clientPingEvent);

        // Branch: checks a condition
        if (clientPingEvent.isCancelled()) {
            // Calls a method
            connection.disconnect();
        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (clientPingEvent.getDelay().isZero()) {
                // Calls a method
                connection.sendPacket(new PingResponsePacket(clientPingEvent.getPayload()));
                // Calls a method
                connection.disconnect();
            // Alternative branch of the condition
            } else {
                // Start of a method/block
                MinecraftServer.getSchedulerManager().buildTask(() -> {
                    // Calls a method
                    connection.sendPacket(new PingResponsePacket(clientPingEvent.getPayload()));
                    // Calls a method
                    connection.disconnect();
                // Calls a method
                }).delay(clientPingEvent.getDelay()).schedule();
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
