// Package declaration for this file
package net.minestom.server.network.debug.info;

// Import of a required class
import net.minestom.server.collision.BlockBoundingBox;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record DebugStructureInfo(BlockBoundingBox boundingBox, List<Piece> pieces) {

    // Assigns a value
    public static final NetworkBuffer.Type<DebugStructureInfo> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BlockBoundingBox.NETWORK_TYPE, DebugStructureInfo::boundingBox,
            // Code statement
            Piece.SERIALIZER.list(), DebugStructureInfo::pieces,
            // Code statement
            DebugStructureInfo::new);

    // Start of a method/block
    public DebugStructureInfo {
        // Calls a method
        pieces = List.copyOf(pieces);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Piece(BlockBoundingBox boundingBox, boolean isStart) {
        // Assigns a value
        public static final NetworkBuffer.Type<Piece> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                BlockBoundingBox.NETWORK_TYPE, Piece::boundingBox,
                // Code statement
                NetworkBuffer.BOOLEAN, Piece::isStart,
                // Code statement
                Piece::new);
    // End of a block/expression
    }
// End of a block/expression
}
