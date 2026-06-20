// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;

// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record SetTimePacket(long gameTime,
                            // Start of a method/block
                            Map<RegistryKey<WorldClock>, ClockState> clocks) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SetTimePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            LONG, SetTimePacket::gameTime,
            // Code statement
            WorldClock.NETWORK_TYPE.mapValue(ClockState.NETWORK_TYPE), SetTimePacket::clocks,
            // Code statement
            SetTimePacket::new);

    // Start of a method/block
    public SetTimePacket {
        // Calls a method
        clocks = Map.copyOf(clocks);
    // End of a block/expression
    }

    /**
     * Represents a clock state update for time.
     *
     * @param totalTicks the number of ticks since this clock was ticking
     * @param partialTick the partial tick of the clock (based on rate), wiped on full update
     * @param rate the rate of the clock in ticks, 1 for normal
     */
    // Type declaration (class/interface/enum/record)
    public record ClockState(long totalTicks, float partialTick, float rate) {
        // Assigns a value
        public static final NetworkBuffer.Type<ClockState> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                VAR_LONG, ClockState::totalTicks,
                // Code statement
                FLOAT, ClockState::partialTick,
                // Code statement
                FLOAT, ClockState::rate,
                // Code statement
                ClockState::new);
    // End of a block/expression
    }
// End of a block/expression
}
