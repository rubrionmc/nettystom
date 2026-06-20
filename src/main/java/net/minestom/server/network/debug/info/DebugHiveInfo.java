// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record DebugHiveInfo(
        // Instruction de code
        Block type,
        // Instruction de code
        int occupantCount,
        // Instruction de code
        int honeyLevel,
        // Instruction de code
        boolean sedated
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugHiveInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Block.ID_NETWORK_TYPE, DebugHiveInfo::type,
            // Instruction de code
            NetworkBuffer.INT, DebugHiveInfo::occupantCount,
            // Instruction de code
            NetworkBuffer.INT, DebugHiveInfo::honeyLevel,
            // Instruction de code
            NetworkBuffer.BOOLEAN, DebugHiveInfo::sedated,
            // Instruction de code
            DebugHiveInfo::new);
// Fin d'un bloc/d'une expression
}
