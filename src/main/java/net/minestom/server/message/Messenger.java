// Déclaration du paquet de ce fichier
package net.minestom.server.message;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SystemChatPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;

/**
 * Utility class to handle client chat settings.
 */
// Déclaration de type (classe/interface/enum/record)
public final class Messenger {
    /**
     * The message sent to the client if they send a chat message but it is rejected by the server.
     */
    // Appelle une méthode
    public static final Component CANNOT_SEND_MESSAGE = Component.translatable("chat.cannotSend", NamedTextColor.RED);
    // Appelle une méthode
    private static final SystemChatPacket CANNOT_SEND_PACKET = new SystemChatPacket(CANNOT_SEND_MESSAGE, false);

    /**
     * Sends a message to a player, respecting their chat settings.
     *
     * @param player   the player
     * @param message  the message
     * @param position the position
     * @return if the message was sent
     */
    // Début d'une méthode/d'un bloc
    public static boolean sendMessage(Player player, Component message, ChatPosition position) {
        // Embranchement : vérifie une condition
        if (getChatMessageType(player).accepts(position)) {
            // Appelle une méthode
            player.sendPacket(new SystemChatPacket(message, false));
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a message to some players, respecting their chat settings.
     *
     * @param players  the players
     * @param message  the message
     * @param position the position
     */
    // Début d'une méthode/d'un bloc
    public static void sendMessage(Collection<? extends Player> players, Component message, ChatPosition position) {
        // Instruction de code
        PacketSendingUtils.sendGroupedPacket(players, new SystemChatPacket(message, false),
                // Appelle une méthode
                player -> getChatMessageType(player).accepts(position));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the server should receive messages from a player, given their chat settings.
     *
     * @param player the player
     * @return if the server should receive messages from them
     */
    // Début d'une méthode/d'un bloc
    public static boolean canReceiveMessage(Player player) {
        // Renvoie une valeur à l'appelant
        return getChatMessageType(player) == ChatMessageType.FULL;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the server should receive commands from a player, given their chat settings.
     *
     * @param player the player
     * @return if the server should receive commands from them
     */
    // Début d'une méthode/d'un bloc
    public static boolean canReceiveCommand(Player player) {
        // Renvoie une valeur à l'appelant
        return getChatMessageType(player) != ChatMessageType.NONE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a message to the player informing them we are rejecting their message or command.
     *
     * @param player the player
     */
    // Début d'une méthode/d'un bloc
    public static void sendRejectionMessage(Player player) {
        // Appelle une méthode
        player.sendPacket(CANNOT_SEND_PACKET);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the chat message type for a player, returning {@link ChatMessageType#FULL} if not set.
     *
     * @param player the player
     * @return the chat message type
     */
    // Début d'une méthode/d'un bloc
    private static ChatMessageType getChatMessageType(Player player) {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNullElse(player.getSettings().chatMessageType(), ChatMessageType.FULL);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
