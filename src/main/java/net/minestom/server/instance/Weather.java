// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.ChangeGameStatePacket;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

/**
 * Represents the possible weather properties of an instance
 *
 * @param rainLevel    a percentage between 0 and 1
 *                     used to change how heavy the rain is
 *                     higher values darken the sky and increase rain opacity
 * @param thunderLevel a percentage between 0 and 1
 *                     used to change how heavy the thunder is
 *                     higher values further darken the sky
 */
// Type declaration (class/interface/enum/record)
public record Weather(float rainLevel, float thunderLevel) {
    // Calls a method
    public static final Weather CLEAR = new Weather(0, 0);
    // Calls a method
    public static final Weather RAIN = new Weather(1, 0);
    // Calls a method
    public static final Weather THUNDER = new Weather(1, 1);

    /**
     * @throws IllegalArgumentException if {@code rainLevel} is not between 0 and 1
     * @throws IllegalArgumentException if {@code thunderLevel} is not between 0 and 1
     */
    // Start of a method/block
    public Weather {
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(rainLevel, 0, 1), "Rain level should be between 0 and 1");
        // Calls a method
        Check.argCondition(!MathUtils.isBetween(thunderLevel, 0, 1), "Thunder level should be between 0 and 1");
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Weather withRainLevel(float rainLevel) {
        // Returns a value to the caller
        return new Weather(rainLevel, thunderLevel);
    // End of a block/expression
    }

    /**
     * @return true if {@code rainLevel} is > 0
     */
    // Start of a method/block
    public boolean isRaining() {
        // Returns a value to the caller
        return rainLevel > 0;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Weather withRainLevel(FloatUnaryOperator operator) {
        // Returns a value to the caller
        return withRainLevel(operator.apply(rainLevel));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Weather withThunderLevel(float thunderLevel) {
        // Returns a value to the caller
        return new Weather(rainLevel, thunderLevel);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Weather withThunderLevel(FloatUnaryOperator operator) {
        // Returns a value to the caller
        return withRainLevel(operator.apply(thunderLevel));
    // End of a block/expression
    }

    // Start of a method/block
    public ChangeGameStatePacket createIsRainingPacket() {
        // Returns a value to the caller
        return new ChangeGameStatePacket(isRaining() ? ChangeGameStatePacket.Reason.BEGIN_RAINING : ChangeGameStatePacket.Reason.END_RAINING, 0);
    // End of a block/expression
    }

    // Start of a method/block
    public ChangeGameStatePacket createRainLevelPacket() {
        // Returns a value to the caller
        return new ChangeGameStatePacket(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, rainLevel);
    // End of a block/expression
    }

    // Start of a method/block
    public ChangeGameStatePacket createThunderLevelPacket() {
        // Returns a value to the caller
        return new ChangeGameStatePacket(ChangeGameStatePacket.Reason.THUNDER_LEVEL_CHANGE, thunderLevel);
    // End of a block/expression
    }

    // Start of a method/block
    public Collection<SendablePacket> createWeatherPackets() {
        // Returns a value to the caller
        return List.of(createIsRainingPacket(), createRainLevelPacket(), createThunderLevelPacket());
    // End of a block/expression
    }
// End of a block/expression
}
