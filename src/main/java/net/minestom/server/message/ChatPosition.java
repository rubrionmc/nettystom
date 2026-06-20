// Déclaration du paquet de ce fichier
package net.minestom.server.message;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.MessageType;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * The different positions for chat messages.
 */
// Déclaration de type (classe/interface/enum/record)
public enum ChatPosition {
    /**
     * A player-initiated chat message.
     */
    // Instruction de code
    CHAT(MessageType.CHAT),

    /**
     * Feedback from running a command or other system messages.
     */
    // Instruction de code
    SYSTEM_MESSAGE(MessageType.SYSTEM),

    /**
     * Game state information displayed above the hot bar.
     */
    // Appelle une méthode
    GAME_INFO(null);

    // Instruction de code
    private final MessageType messageType;

    // Début d'une méthode/d'un bloc
    ChatPosition(@Nullable MessageType messageType) {
        // Accès à l'objet courant/parent
        this.messageType = messageType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the Adventure message type from this position. Note that there is no
     * message type for {@link #GAME_INFO}, as Adventure uses the title methods for this.
     *
     * @return the message type, if any
     */
    // Début d'une méthode/d'un bloc
    public @Nullable MessageType getMessageType() {
        // Renvoie une valeur à l'appelant
        return this.messageType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the packet ID of this chat position.
     *
     * @return the ID
     */
    // Début d'une méthode/d'un bloc
    public byte getID() {
        // Renvoie une valeur à l'appelant
        return (byte) this.ordinal();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a position from an Adventure message type.
     *
     * @param messageType the message type
     * @return the position
     */
    // Début d'une méthode/d'un bloc
    public static ChatPosition fromMessageType(MessageType messageType) {
        // Renvoie une valeur à l'appelant
        return switch (messageType) {
            // Embranchement multiple (switch/case)
            case CHAT -> CHAT;
            // Embranchement multiple (switch/case)
            case SYSTEM -> SYSTEM_MESSAGE;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a position from a packet ID.
     *
     * @param id the id
     * @return the chat position
     */
    // Début d'une méthode/d'un bloc
    public static ChatPosition fromPacketID(int id) {
        // Renvoie une valeur à l'appelant
        return switch (id) {
            // Embranchement multiple (switch/case)
            case 0 -> CHAT;
            // Embranchement multiple (switch/case)
            case 1 -> SYSTEM_MESSAGE;
            // Embranchement multiple (switch/case)
            case 2 -> GAME_INFO;
            // Appelle une méthode
            default -> throw new IllegalArgumentException("id must be between 0-2 (inclusive)");
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
