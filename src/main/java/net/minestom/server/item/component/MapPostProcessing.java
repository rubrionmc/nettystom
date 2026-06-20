// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public enum MapPostProcessing {
    // Instruction de code
    LOCK,
    // Instruction de code
    SCALE;

    // Appelle une méthode
    public static final NetworkBuffer.Type<MapPostProcessing> NETWORK_TYPE = NetworkBuffer.Enum(MapPostProcessing.class);
// Fin d'un bloc/d'une expression
}
