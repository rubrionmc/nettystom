// Package declaration for this file
package net.minestom.server.listener.common;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerConfigCustomClickEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerCustomClickEvent;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.client.common.ClientCustomClickActionPacket;

// Type declaration (class/interface/enum/record)
public final class CustomClickListener {

    // Start of a method/block
    public static void listener(ClientCustomClickActionPacket listener, Player player) {
        // Calls a method
        MinecraftServer.getClickCallbackManager().consumeCustomClick(player, listener);
        // Assigns a value
        var event = player.getPlayerConnection().getClientState() == ConnectionState.PLAY
                // Code statement
                ? new PlayerCustomClickEvent(player, listener.key(), listener.payload().type() == BinaryTagTypes.END ? null : listener.payload())
                // Calls a method
                : new PlayerConfigCustomClickEvent(player, listener.key(), listener.payload().type() == BinaryTagTypes.END ? null : listener.payload());
        // Calls a method
        EventDispatcher.call(event);
    // End of a block/expression
    }

// End of a block/expression
}
