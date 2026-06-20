// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record SetTimePacket(long gameTime,
                            // Début d'une méthode/d'un bloc
                            Map<RegistryKey<WorldClock>, ClockState> clocks) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SetTimePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            LONG, SetTimePacket::gameTime,
            // Instruction de code
            WorldClock.NETWORK_TYPE.mapValue(ClockState.NETWORK_TYPE), SetTimePacket::clocks,
            // Instruction de code
            SetTimePacket::new);

    // Début d'une méthode/d'un bloc
    public SetTimePacket {
        // Appelle une méthode
        clocks = Map.copyOf(clocks);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents a clock state update for time.
     *
     * @param totalTicks the number of ticks since this clock was ticking
     * @param partialTick the partial tick of the clock (based on rate), wiped on full update
     * @param rate the rate of the clock in ticks, 1 for normal
     */
    // Déclaration de type (classe/interface/enum/record)
    public record ClockState(long totalTicks, float partialTick, float rate) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<ClockState> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                VAR_LONG, ClockState::totalTicks,
                // Instruction de code
                FLOAT, ClockState::partialTick,
                // Instruction de code
                FLOAT, ClockState::rate,
                // Instruction de code
                ClockState::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
