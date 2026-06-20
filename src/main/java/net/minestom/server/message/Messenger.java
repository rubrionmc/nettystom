// Package declaration for this file
package net.minestom.server.message;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.play.SystemChatPacket;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;

/**
 * Utility class to handle client chat settings.
 */
// Type declaration (class/interface/enum/record)
public final class Messenger {
    /**
     * The message sent to the client if they send a chat message but it is rejected by the server.
     */
    // Calls a method
    public static final Component CANNOT_SEND_MESSAGE = Component.translatable("chat.cannotSend", NamedTextColor.RED);
    // Calls a method
    private static final SystemChatPacket CANNOT_SEND_PACKET = new SystemChatPacket(CANNOT_SEND_MESSAGE, false);

    /**
     * Sends a message to a player, respecting their chat settings.
     *
     * @param player   the player
     * @param message  the message
     * @param position the position
     * @return if the message was sent
     */
    // Start of a method/block
    public static boolean sendMessage(Player player, Component message, ChatPosition position) {
        // Branch: checks a condition
        if (getChatMessageType(player).accepts(position)) {
            // Calls a method
            player.sendPacket(new SystemChatPacket(message, false));
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    /**
     * Sends a message to some players, respecting their chat settings.
     *
     * @param players  the players
     * @param message  the message
     * @param position the position
     */
    // Start of a method/block
    public static void sendMessage(Collection<? extends Player> players, Component message, ChatPosition position) {
        // Code statement
        PacketSendingUtils.sendGroupedPacket(players, new SystemChatPacket(message, false),
                // Calls a method
                player -> getChatMessageType(player).accepts(position));
    // End of a block/expression
    }

    /**
     * Checks if the server should receive messages from a player, given their chat settings.
     *
     * @param player the player
     * @return if the server should receive messages from them
     */
    // Start of a method/block
    public static boolean canReceiveMessage(Player player) {
        // Returns a value to the caller
        return getChatMessageType(player) == ChatMessageType.FULL;
    // End of a block/expression
    }

    /**
     * Checks if the server should receive commands from a player, given their chat settings.
     *
     * @param player the player
     * @return if the server should receive commands from them
     */
    // Start of a method/block
    public static boolean canReceiveCommand(Player player) {
        // Returns a value to the caller
        return getChatMessageType(player) != ChatMessageType.NONE;
    // End of a block/expression
    }

    /**
     * Sends a message to the player informing them we are rejecting their message or command.
     *
     * @param player the player
     */
    // Start of a method/block
    public static void sendRejectionMessage(Player player) {
        // Calls a method
        player.sendPacket(CANNOT_SEND_PACKET);
    // End of a block/expression
    }

    /**
     * Gets the chat message type for a player, returning {@link ChatMessageType#FULL} if not set.
     *
     * @param player the player
     * @return the chat message type
     */
    // Start of a method/block
    private static ChatMessageType getChatMessageType(Player player) {
        // Returns a value to the caller
        return Objects.requireNonNullElse(player.getSettings().chatMessageType(), ChatMessageType.FULL);
    // End of a block/expression
    }
// End of a block/expression
}
