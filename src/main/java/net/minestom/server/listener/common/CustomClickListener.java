// Déclaration du paquet de ce fichier
package net.minestom.server.listener.common;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerConfigCustomClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerCustomClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientCustomClickActionPacket;

// Déclaration de type (classe/interface/enum/record)
public final class CustomClickListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientCustomClickActionPacket listener, Player player) {
        // Appelle une méthode
        MinecraftServer.getClickCallbackManager().consumeCustomClick(player, listener);
        // Instruction de code
        var event = player.getPlayerConnection().getClientState() == ConnectionState.PLAY
                // Instruction de code
                ? new PlayerCustomClickEvent(player, listener.key(), listener.payload().type() == BinaryTagTypes.END ? null : listener.payload())
                // Appelle une méthode
                : new PlayerConfigCustomClickEvent(player, listener.key(), listener.payload().type() == BinaryTagTypes.END ? null : listener.payload());
        // Appelle une méthode
        EventDispatcher.call(event);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
