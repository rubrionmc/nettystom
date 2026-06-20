// Déclaration du paquet de ce fichier
package net.minestom.server.listener.preplay;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ClientPingServerEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerListPingEvent;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPingRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.PingResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.ping.ServerListPingType;

// Déclaration de type (classe/interface/enum/record)
public final class StatusListener {

    // Début d'une méthode/d'un bloc
    public static void requestListener(StatusRequestPacket packet, PlayerConnection connection) {
        // Appelle une méthode
        final ServerListPingType pingVersion = ServerListPingType.fromModernProtocolVersion(connection.getProtocolVersion());
        // Appelle une méthode
        final ServerListPingEvent serverListPingEvent = new ServerListPingEvent(connection, pingVersion);
        // Instruction de code
        EventDispatcher.callCancellable(serverListPingEvent, () ->
                // Appelle une méthode
                connection.sendPacket(new ResponsePacket(pingVersion.getPingResponse(serverListPingEvent.getStatus()))));
        // Appelle une méthode
        EventsJFR.newServerPing(connection.getRemoteAddress().toString()).commit();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void pingRequestListener(ClientPingRequestPacket packet, PlayerConnection connection) {
        // Appelle une méthode
        final ClientPingServerEvent clientPingEvent = new ClientPingServerEvent(connection, packet.number());
        // Appelle une méthode
        EventDispatcher.call(clientPingEvent);

        // Embranchement : vérifie une condition
        if (clientPingEvent.isCancelled()) {
            // Appelle une méthode
            connection.disconnect();
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (clientPingEvent.getDelay().isZero()) {
                // Appelle une méthode
                connection.sendPacket(new PingResponsePacket(clientPingEvent.getPayload()));
                // Appelle une méthode
                connection.disconnect();
            // Branche alternative de la condition
            } else {
                // Début d'une méthode/d'un bloc
                MinecraftServer.getSchedulerManager().buildTask(() -> {
                    // Appelle une méthode
                    connection.sendPacket(new PingResponsePacket(clientPingEvent.getPayload()));
                    // Appelle une méthode
                    connection.disconnect();
                // Appelle une méthode
                }).delay(clientPingEvent.getDelay()).schedule();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
