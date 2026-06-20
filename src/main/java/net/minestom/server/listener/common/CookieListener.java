// Package declaration for this file
package net.minestom.server.listener.common;

// Import of a required class
import net.minestom.server.network.packet.client.common.ClientCookieResponsePacket;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;

// Type declaration (class/interface/enum/record)
public final class CookieListener {

    // Start of a method/block
    public static void handleCookieResponse(ClientCookieResponsePacket packet, PlayerConnection connection) {
        // Calls a method
        connection.receiveCookieResponse(packet.key(), packet.value());
    // End of a block/expression
    }

    // Code statement
    private CookieListener() {}
// End of a block/expression
}
