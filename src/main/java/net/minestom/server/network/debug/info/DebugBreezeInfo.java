// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record DebugBreezeInfo(
        // Annotation pour l'élément suivant
        @Nullable Integer attackTarget,
        // Annotation pour l'élément suivant
        @Nullable Point jumpTarget
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugBreezeInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT.optional(), DebugBreezeInfo::attackTarget,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION.optional(), DebugBreezeInfo::jumpTarget,
            // Instruction de code
            DebugBreezeInfo::new);
// Fin d'un bloc/d'une expression
}
