// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

/**
 * Represents where is located the main hand of the player (can be changed in Minecraft option).
 */
// Déclaration de type (classe/interface/enum/record)
public enum MainHand {
    // Instruction de code
    LEFT,
    // Instruction de code
    RIGHT;

    // Affecte une valeur
    public static final NetworkBuffer.Type<MainHand> NETWORK_TYPE = NetworkBuffer.Enum(
        // Instruction de code
        MainHand.class);
// Fin d'un bloc/d'une expression
}
