// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.util.RGBLike;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.color.Color;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record FireworkExplosion(
        // Instruction de code
        Shape shape,
        // Instruction de code
        List<RGBLike> colors,
        // Instruction de code
        List<RGBLike> fadeColors,
        // Instruction de code
        boolean hasTrail,
        // Instruction de code
        boolean hasTwinkle
// Début d'une méthode/d'un bloc
) {

    // Déclaration de type (classe/interface/enum/record)
    public enum Shape {
        // Instruction de code
        SMALL_BALL,
        // Instruction de code
        LARGE_BALL,
        // Instruction de code
        STAR,
        // Instruction de code
        CREEPER,
        // Instruction de code
        BURST
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<FireworkExplosion> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(Shape.class), FireworkExplosion::shape,
            // Instruction de code
            Color.NETWORK_TYPE.list(Short.MAX_VALUE), FireworkExplosion::colors,
            // Instruction de code
            Color.NETWORK_TYPE.list(Short.MAX_VALUE), FireworkExplosion::fadeColors,
            // Instruction de code
            BOOLEAN, FireworkExplosion::hasTrail,
            // Instruction de code
            BOOLEAN, FireworkExplosion::hasTwinkle,
            // Instruction de code
            FireworkExplosion::new);
    // Affecte une valeur
    public static final Codec<FireworkExplosion> CODEC = StructCodec.struct(
            // Instruction de code
            "shape", Codec.Enum(Shape.class), FireworkExplosion::shape,
            // Instruction de code
            "colors", Color.CODEC.list().optional(List.of()), FireworkExplosion::colors,
            // Instruction de code
            "fade_colors", Color.CODEC.list().optional(List.of()), FireworkExplosion::fadeColors,
            // Instruction de code
            "has_trail", Codec.BOOLEAN.optional(false), FireworkExplosion::hasTrail,
            // Instruction de code
            "has_twinkle", Codec.BOOLEAN.optional(false), FireworkExplosion::hasTwinkle,
            // Instruction de code
            FireworkExplosion::new);

    // Début d'une méthode/d'un bloc
    public FireworkExplosion {
        // Appelle une méthode
        colors = List.copyOf(colors);
        // Appelle une méthode
        fadeColors = List.copyOf(fadeColors);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
