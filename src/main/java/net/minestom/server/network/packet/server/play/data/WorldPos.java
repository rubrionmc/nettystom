// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play.data;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record WorldPos(String dimension, Point blockPosition) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<WorldPos> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.STRING, WorldPos::dimension,
            // Instruction de code
            NetworkBuffer.BLOCK_POSITION, WorldPos::blockPosition,
            // Instruction de code
            WorldPos::new);
    // Affecte une valeur
    public static final Codec<WorldPos> CODEC = StructCodec.struct(
            // Instruction de code
            "dimension", Codec.STRING, WorldPos::dimension,
            // Instruction de code
            "pos", Codec.BLOCK_POSITION, WorldPos::blockPosition,
            // Instruction de code
            WorldPos::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public WorldPos withDimension(String dimension) {
        // Renvoie une valeur à l'appelant
        return new WorldPos(dimension, blockPosition);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public WorldPos withBlockPosition(Point blockPosition) {
        // Renvoie une valeur à l'appelant
        return new WorldPos(dimension, blockPosition);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}