// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChangeGameStatePacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public record Weather(float rainLevel, float thunderLevel) {
    // Appelle une méthode
    public static final Weather CLEAR = new Weather(0, 0);
    // Appelle une méthode
    public static final Weather RAIN = new Weather(1, 0);
    // Appelle une méthode
    public static final Weather THUNDER = new Weather(1, 1);

    /**
     * @throws IllegalArgumentException if {@code rainLevel} is not between 0 and 1
     * @throws IllegalArgumentException if {@code thunderLevel} is not between 0 and 1
     */
    // Début d'une méthode/d'un bloc
    public Weather {
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(rainLevel, 0, 1), "Rain level should be between 0 and 1");
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(thunderLevel, 0, 1), "Thunder level should be between 0 and 1");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Weather withRainLevel(float rainLevel) {
        // Renvoie une valeur à l'appelant
        return new Weather(rainLevel, thunderLevel);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return true if {@code rainLevel} is > 0
     */
    // Début d'une méthode/d'un bloc
    public boolean isRaining() {
        // Renvoie une valeur à l'appelant
        return rainLevel > 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Weather withRainLevel(FloatUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withRainLevel(operator.apply(rainLevel));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Weather withThunderLevel(float thunderLevel) {
        // Renvoie une valeur à l'appelant
        return new Weather(rainLevel, thunderLevel);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Weather withThunderLevel(FloatUnaryOperator operator) {
        // Renvoie une valeur à l'appelant
        return withRainLevel(operator.apply(thunderLevel));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ChangeGameStatePacket createIsRainingPacket() {
        // Renvoie une valeur à l'appelant
        return new ChangeGameStatePacket(isRaining() ? ChangeGameStatePacket.Reason.BEGIN_RAINING : ChangeGameStatePacket.Reason.END_RAINING, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ChangeGameStatePacket createRainLevelPacket() {
        // Renvoie une valeur à l'appelant
        return new ChangeGameStatePacket(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, rainLevel);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ChangeGameStatePacket createThunderLevelPacket() {
        // Renvoie une valeur à l'appelant
        return new ChangeGameStatePacket(ChangeGameStatePacket.Reason.THUNDER_LEVEL_CHANGE, thunderLevel);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Collection<SendablePacket> createWeatherPackets() {
        // Renvoie une valeur à l'appelant
        return List.of(createIsRainingPacket(), createRainLevelPacket(), createThunderLevelPacket());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
