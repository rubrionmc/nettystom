// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public enum DebugEntityBlockIntersection {
    // Instruction de code
    IN_BLOCK,
    // Instruction de code
    IN_FLUID,
    // Instruction de code
    IN_AIR;

    // Appelle une méthode
    public static final NetworkBuffer.Type<DebugEntityBlockIntersection> SERIALIZER = NetworkBuffer.Enum(DebugEntityBlockIntersection.class);
// Fin d'un bloc/d'une expression
}
