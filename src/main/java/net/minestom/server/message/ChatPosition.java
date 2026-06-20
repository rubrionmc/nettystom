// Déclaration du paquet de ce fichier
package net.minestom.server.message;

/**
 * The different positions for chat messages.
 */
// Déclaration de type (classe/interface/enum/record)
public enum ChatPosition {
    /**
     * A player-initiated chat message.
     */
    // Instruction de code
    CHAT,

    /**
     * Feedback from running a command or other system messages.
     */
    // Instruction de code
    SYSTEM_MESSAGE,

    /**
     * Game state information displayed above the hot bar.
     */
    // Instruction de code
    GAME_INFO;
// Fin d'un bloc/d'une expression
}
