// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandManager;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerChatEvent;
// Import d'une classe nécessaire
import net.minestom.server.message.ChatPosition;
// Import d'une classe nécessaire
import net.minestom.server.message.Messenger;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.EventsJFR;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionManager;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientChatMessagePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientCommandChatPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSignedCommandChatPacket;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public class ChatMessageListener {
    // Appelle une méthode
    private static final CommandManager COMMAND_MANAGER = MinecraftServer.getCommandManager();
    // Appelle une méthode
    private static final ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();

    // Début d'une méthode/d'un bloc
    public static void commandChatListener(ClientCommandChatPacket packet, Player player) {
        // Appelle une méthode
        final String command = packet.message();
        // Appelle une méthode
        EventsJFR.newPlayerCommand(player.getUuid(), command).commit();
        // Embranchement : vérifie une condition
        if (Messenger.canReceiveCommand(player)) {
            // Appelle une méthode
            COMMAND_MANAGER.execute(player, command);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            Messenger.sendRejectionMessage(player);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void signedCommandChatListener(ClientSignedCommandChatPacket packet, Player player) {
        // Intentionally do the same thing as commandChatListener. We don't use signed commands, but Geyser
        // and Gate (the proxy) always send them so this is for compatibility with them.
        // Appelle une méthode
        final String command = packet.message();
        // Appelle une méthode
        EventsJFR.newPlayerCommand(player.getUuid(), command).commit();
        // Embranchement : vérifie une condition
        if (Messenger.canReceiveCommand(player)) {
            // Appelle une méthode
            COMMAND_MANAGER.execute(player, command);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            Messenger.sendRejectionMessage(player);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void chatMessageListener(ClientChatMessagePacket packet, Player player) {
        // Appelle une méthode
        final String message = packet.message();
        // Appelle une méthode
        EventsJFR.newPlayerChat(player.getUuid(), message).commit();
        // Embranchement : vérifie une condition
        if (!Messenger.canReceiveMessage(player)) {
            // Appelle une méthode
            Messenger.sendRejectionMessage(player);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final Collection<Player> players = CONNECTION_MANAGER.getOnlinePlayers();
        // Appelle une méthode
        PlayerChatEvent playerChatEvent = new PlayerChatEvent(player, players, message);

        // Call the event
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(playerChatEvent, () -> {
            // Appelle une méthode
            final Collection<Player> recipients = playerChatEvent.getRecipients();

            // Embranchement : vérifie une condition
            if (!recipients.isEmpty()) {
                // delegate to the messenger to avoid sending messages we shouldn't be
                // Instruction de code
                Messenger.sendMessage(
                        // Instruction de code
                        recipients,
                        // Instruction de code
                        playerChatEvent.getFormattedMessage(),
                        // Instruction de code
                        ChatPosition.CHAT,
                        // Appelle une méthode
                        player.getUuid());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
