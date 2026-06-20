// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Type declaration (class/interface/enum/record)
public record SwingAnimation(Type type, int duration) {
    // Calls a method
    public static final SwingAnimation DEFAULT = new SwingAnimation(Type.WHACK, 6);

    // Assigns a value
    public static final NetworkBuffer.Type<SwingAnimation> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            Type.NETWORK_TYPE, SwingAnimation::type,
            // Code statement
            NetworkBuffer.VAR_INT, SwingAnimation::duration,
            // Code statement
            SwingAnimation::new);
    // Assigns a value
    public static final Codec<SwingAnimation> CODEC = StructCodec.struct(
            // Code statement
            "type", Type.CODEC.optional(Type.WHACK), SwingAnimation::type,
            // Code statement
            "duration", Codec.INT.optional(6), SwingAnimation::duration,
            // Code statement
            SwingAnimation::new);

    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Code statement
        NONE,
        // Code statement
        WHACK,
        // Code statement
        STAB;

        // Calls a method
        public static final NetworkBuffer.Type<Type> NETWORK_TYPE = NetworkBuffer.Enum(Type.class);
        // Calls a method
        public static final Codec<Type> CODEC = Codec.Enum(Type.class);
    // End of a block/expression
    }
// End of a block/expression
}
