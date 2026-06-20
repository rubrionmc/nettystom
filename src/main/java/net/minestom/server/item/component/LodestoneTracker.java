// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

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
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.WorldPos;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public record LodestoneTracker(@Nullable WorldPos target, boolean tracked) {

    // Affecte une valeur
    public static final NetworkBuffer.Type<LodestoneTracker> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            WorldPos.NETWORK_TYPE.optional(), LodestoneTracker::target,
            // Instruction de code
            NetworkBuffer.BOOLEAN, LodestoneTracker::tracked,
            // Instruction de code
            LodestoneTracker::new);
    // Affecte une valeur
    public static final Codec<LodestoneTracker> CODEC = StructCodec.struct(
            // Instruction de code
            "target", WorldPos.CODEC.optional(), LodestoneTracker::target,
            // Instruction de code
            "tracked", Codec.BOOLEAN.optional(true), LodestoneTracker::tracked,
            // Instruction de code
            LodestoneTracker::new);

    // Début d'une méthode/d'un bloc
    public LodestoneTracker(String dimension, Point blockPosition, boolean tracked) {
        // Appelle une méthode
        this(new WorldPos(dimension, blockPosition), tracked);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LodestoneTracker withTarget(@Nullable WorldPos target) {
        // Renvoie une valeur à l'appelant
        return new LodestoneTracker(target, tracked);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LodestoneTracker withTracked(boolean tracked) {
        // Renvoie une valeur à l'appelant
        return new LodestoneTracker(target, tracked);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
