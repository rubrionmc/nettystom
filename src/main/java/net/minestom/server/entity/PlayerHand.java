// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

/**
 * Represents the main or off hand of the player.
 */
// Déclaration de type (classe/interface/enum/record)
public enum PlayerHand {
    // Instruction de code
    MAIN,
    // Instruction de code
    OFF;

    // Appelle une méthode
    public static final NetworkBuffer.Type<PlayerHand> NETWORK_TYPE = NetworkBuffer.Enum(PlayerHand.class);
// Fin d'un bloc/d'une expression
}
