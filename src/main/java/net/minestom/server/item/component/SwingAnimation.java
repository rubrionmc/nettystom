// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Déclaration de type (classe/interface/enum/record)
public record SwingAnimation(Type type, int duration) {
    // Appelle une méthode
    public static final SwingAnimation DEFAULT = new SwingAnimation(Type.WHACK, 6);

    // Affecte une valeur
    public static final NetworkBuffer.Type<SwingAnimation> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            Type.NETWORK_TYPE, SwingAnimation::type,
            // Instruction de code
            NetworkBuffer.VAR_INT, SwingAnimation::duration,
            // Instruction de code
            SwingAnimation::new);
    // Affecte une valeur
    public static final Codec<SwingAnimation> CODEC = StructCodec.struct(
            // Instruction de code
            "type", Type.CODEC.optional(Type.WHACK), SwingAnimation::type,
            // Instruction de code
            "duration", Codec.INT.optional(6), SwingAnimation::duration,
            // Instruction de code
            SwingAnimation::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        // Instruction de code
        NONE,
        // Instruction de code
        WHACK,
        // Instruction de code
        STAB;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
        // Appelle une méthode
        public static final Codec<Type> CODEC = Codec.Enum(Type.class);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
