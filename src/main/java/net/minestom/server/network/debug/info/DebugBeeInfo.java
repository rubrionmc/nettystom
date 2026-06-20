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

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record DebugBeeInfo(
        // Annotation pour l'élément suivant
        @Nullable Point hivePosition,
        // Annotation pour l'élément suivant
        @Nullable Point flowerPosition,
        // Instruction de code
        int travelTicks,
        // Instruction de code
        List<Point> blacklistedHives
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugBeeInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION.optional(), DebugBeeInfo::hivePosition,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION.optional(), DebugBeeInfo::flowerPosition,
            // Instruction de code
            NetworkBuffer.VAR_INT, DebugBeeInfo::travelTicks,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION.list(), DebugBeeInfo::blacklistedHives,
            // Instruction de code
            DebugBeeInfo::new);

    // Début d'une méthode/d'un bloc
    public DebugBeeInfo {
        // Appelle une méthode
        blacklistedHives = List.copyOf(blacklistedHives);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
