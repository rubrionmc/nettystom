// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.debug.DebugSubscription;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.Enum;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.LONG_ARRAY;

// Type declaration (class/interface/enum/record)
public record DebugSamplePacket(long[] sample, Type type) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<DebugSamplePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            LONG_ARRAY, DebugSamplePacket::sample,
            // Code statement
            Enum(Type.class), DebugSamplePacket::type,
            // Code statement
            DebugSamplePacket::new);

    // Start of a method/block
    public DebugSamplePacket {
        // Calls a method
        sample = sample.clone();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object object) {
        // Branch: checks a condition
        if (!(object instanceof DebugSamplePacket(long[] sample1, Type type1))) return false;
        // Returns a value to the caller
        return type() == type1 && Arrays.equals(sample(), sample1);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = Arrays.hashCode(sample());
        // Calls a method
        result = 31 * result + type().hashCode();
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Type {
        // Calls a method
        TICK_TIME(DebugSubscription.DEDICATED_SERVER_TICK_TIME);

        // Code statement
        private final DebugSubscription<?> debugSubscription;

        // Start of a method/block
        Type(DebugSubscription<?> debugSubscription) {
            // Access to the current/parent object
            this.debugSubscription = debugSubscription;
        // End of a block/expression
        }

        // Start of a method/block
        public DebugSubscription<?> getDebugSubscription() {
            // Returns a value to the caller
            return debugSubscription;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
