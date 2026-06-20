// Package declaration for this file
package net.minestom.server.network;

/**
 * Represents the connection state of a client.
 */
// Type declaration (class/interface/enum/record)
public enum ConnectionState {
    /**
     * Default state before any packet is received.
     */
    // Code statement
    HANDSHAKE,
    /**
     * Client declares `Status` intent during handshake.
     */
    // Code statement
    STATUS,
    /**
     * Client declares `Login` intent during handshake.
     */
    // Code statement
    LOGIN,
    /**
     * Client acknowledged login and is now configuring the game.
     * Can also go back to configuration from play.
     */
    // Code statement
    CONFIGURATION,
    /**
     * Client (re-)finished configuration.
     */
    // Code statement
    PLAY
// End of a block/expression
}
