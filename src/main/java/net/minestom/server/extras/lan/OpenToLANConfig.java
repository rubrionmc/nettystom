// Déclaration du paquet de ce fichier
package net.minestom.server.extras.lan;

// Import d'une classe nécessaire
import net.minestom.server.event.server.ServerListPingEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.Objects;

/**
 * Configuration for opening the server to LAN.
 *
 * @see OpenToLAN#open(OpenToLANConfig)
 */
// Déclaration de type (classe/interface/enum/record)
public class OpenToLANConfig {
    // Instruction de code
    int port;
    // Instruction de code
    Duration delayBetweenPings, delayBetweenEvent;

    /**
     * Creates a new config with the port set to random and the delay between pings set
     * to 1.5 seconds and the delay between event calls set to 30 seconds.
     */
    // Début d'une méthode/d'un bloc
    public OpenToLANConfig() {
        // Accès à l'objet courant/parent
        this.port = 0;
        // Accès à l'objet courant/parent
        this.delayBetweenPings = Duration.of(1500, TimeUnit.MILLISECOND);
        // Accès à l'objet courant/parent
        this.delayBetweenEvent = Duration.of(30, TimeUnit.SECOND);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the port used to send pings from. Use {@code 0} to pick a random free port.
     *
     * @param port the port
     * @return {@code this}, for chaining
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public OpenToLANConfig port(int port) {
        // Accès à l'objet courant/parent
        this.port = port;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the delay between outgoing pings.
     *
     * @param delay the delay
     * @return {@code this}, for chaining
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public OpenToLANConfig pingDelay(Duration delay) {
        // Accès à l'objet courant/parent
        this.delayBetweenPings = Objects.requireNonNull(delay, "delay");
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the delay between calls of {@link ServerListPingEvent}.
     *
     * @param delay the delay
     * @return {@code this}, for chaining
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public OpenToLANConfig eventCallDelay(Duration delay) {
        // Accès à l'objet courant/parent
        this.delayBetweenEvent = Objects.requireNonNull(delay, "delay");
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
