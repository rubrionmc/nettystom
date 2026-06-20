// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandManager;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerChatEvent;
// Import of a required class
import net.minestom.server.message.ChatPosition;
// Import of a required class
import net.minestom.server.message.Messenger;
// Import of a required class
import net.minestom.server.monitoring.EventsJFR;
// Import of a required class
import net.minestom.server.network.ConnectionManager;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientChatMessagePacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientCommandChatPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientSignedCommandChatPacket;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public class ChatMessageListener {
    // Calls a method
    private static final CommandManager COMMAND_MANAGER = MinecraftServer.getCommandManager();
    // Calls a method
    private static final ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();

    // Start of a method/block
    public static void commandChatListener(ClientCommandChatPacket packet, Player player) {
        // Calls a method
        final String command = packet.message();
        // Calls a method
        EventsJFR.newPlayerCommand(player.getUuid(), command).commit();
        // Branch: checks a condition
        if (Messenger.canReceiveCommand(player)) {
            // Calls a method
            COMMAND_MANAGER.execute(player, command);
        // Alternative branch of the condition
        } else {
            // Calls a method
            Messenger.sendRejectionMessage(player);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void signedCommandChatListener(ClientSignedCommandChatPacket packet, Player player) {
        // Intentionally do the same thing as commandChatListener. We don't use signed commands, but Geyser
        // and Gate (the proxy) always send them so this is for compatibility with them.
        // Calls a method
        final String command = packet.message();
        // Calls a method
        EventsJFR.newPlayerCommand(player.getUuid(), command).commit();
        // Branch: checks a condition
        if (Messenger.canReceiveCommand(player)) {
            // Calls a method
            COMMAND_MANAGER.execute(player, command);
        // Alternative branch of the condition
        } else {
            // Calls a method
            Messenger.sendRejectionMessage(player);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static void chatMessageListener(ClientChatMessagePacket packet, Player player) {
        // Calls a method
        final String message = packet.message();
        // Calls a method
        EventsJFR.newPlayerChat(player.getUuid(), message).commit();
        // Branch: checks a condition
        if (!Messenger.canReceiveMessage(player)) {
            // Calls a method
            Messenger.sendRejectionMessage(player);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }

        // Calls a method
        final Collection<Player> players = CONNECTION_MANAGER.getOnlinePlayers();
        // Calls a method
        PlayerChatEvent playerChatEvent = new PlayerChatEvent(player, players, message);

        // Call the event
        // Start of a method/block
        EventDispatcher.callCancellable(playerChatEvent, () -> {
            // Calls a method
            final Collection<Player> recipients = playerChatEvent.getRecipients();

            // Branch: checks a condition
            if (!recipients.isEmpty()) {
                // delegate to the messenger to avoid sending messages we shouldn't be
                // Code statement
                Messenger.sendMessage(
                        // Code statement
                        recipients,
                        // Code statement
                        playerChatEvent.getFormattedMessage(),
                        // Code statement
                        ChatPosition.CHAT);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
