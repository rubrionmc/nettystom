// Déclaration du paquet de ce fichier
package net.minestom.server.network;

/**
 * Represents the connection state of a client.
 */
// Déclaration de type (classe/interface/enum/record)
public enum ConnectionState {
    /**
     * Default state before any packet is received.
     */
    // Instruction de code
    HANDSHAKE,
    /**
     * Client declares `Status` intent during handshake.
     */
    // Instruction de code
    STATUS,
    /**
     * Client declares `Login` intent during handshake.
     */
    // Instruction de code
    LOGIN,
    /**
     * Client acknowledged login and is now configuring the game.
     * Can also go back to configuration from play.
     */
    // Instruction de code
    CONFIGURATION,
    /**
     * Client (re-)finished configuration.
     */
    // Instruction de code
    PLAY
// Fin d'un bloc/d'une expression
}
