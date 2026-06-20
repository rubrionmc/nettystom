// Déclaration du paquet de ce fichier
package net.minestom.server.message;

// Import d'une classe nécessaire
import java.util.EnumSet;

/**
 * The messages that a player is willing to receive.
 */
// Déclaration de type (classe/interface/enum/record)
public enum ChatMessageType {
    /**
     * The client wants all chat messages.
     */
    // Instruction de code
    FULL(EnumSet.allOf(ChatPosition.class)),

    /**
     * The client only wants messages from commands, or system messages.
     */
    // Instruction de code
    SYSTEM(EnumSet.of(ChatPosition.SYSTEM_MESSAGE, ChatPosition.GAME_INFO)),

    /**
     * The client doesn't want any messages.
     */
    // Appelle une méthode
    NONE(EnumSet.of(ChatPosition.GAME_INFO));

    // Instruction de code
    private final EnumSet<ChatPosition> acceptedPositions;

    // Début d'une méthode/d'un bloc
    ChatMessageType(EnumSet<ChatPosition> acceptedPositions) {
        // Accès à l'objet courant/parent
        this.acceptedPositions = acceptedPositions;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if this message type is accepting of messages from a given position.
     *
     * @param chatPosition the position
     * @return if the message is accepted
     */
    // Début d'une méthode/d'un bloc
    public boolean accepts(ChatPosition chatPosition) {
        // Renvoie une valeur à l'appelant
        return this.acceptedPositions.contains(chatPosition);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
