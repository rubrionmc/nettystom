// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug.info;

// Import d'une classe nécessaire
import net.minestom.server.collision.BlockBoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record DebugStructureInfo(BlockBoundingBox boundingBox, List<Piece> pieces) {

    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugStructureInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BlockBoundingBox.NETWORK_TYPE, DebugStructureInfo::boundingBox,
            // Instruction de code
            Piece.SERIALIZER.list(), DebugStructureInfo::pieces,
            // Instruction de code
            DebugStructureInfo::new);

    // Début d'une méthode/d'un bloc
    public DebugStructureInfo {
        // Appelle une méthode
        pieces = List.copyOf(pieces);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Piece(BlockBoundingBox boundingBox, boolean isStart) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Piece> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                BlockBoundingBox.NETWORK_TYPE, Piece::boundingBox,
                // Instruction de code
                NetworkBuffer.BOOLEAN, Piece::isStart,
                // Instruction de code
                Piece::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
