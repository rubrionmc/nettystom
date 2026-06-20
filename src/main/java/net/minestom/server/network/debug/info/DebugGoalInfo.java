// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record DebugGoalInfo(int priority, boolean isRunning, String name) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugGoalInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, DebugGoalInfo::priority,
            // Instruction de code
            NetworkBuffer.BOOLEAN, DebugGoalInfo::isRunning,
            // Instruction de code
            NetworkBuffer.STRING, DebugGoalInfo::name,
            // Instruction de code
            DebugGoalInfo::new);
// Fin d'un bloc/d'une expression
}
