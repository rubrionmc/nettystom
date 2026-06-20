// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.world.clock.WorldClock;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Clock permits Instance.ClockInstance {

    // Appelle une méthode
    RegistryKey<WorldClock> clock();

    /// Gets the rate at which the clock advances per tick, in partial ticks.
    ///
    /// The default is 1 (advance one tick per tick).
    // Appelle une méthode
    float rate();

    /// Sets the rate at which the clock advances per tick, in partial ticks.
    ///
    /// The default is 1 (advance one tick per tick).
    // Appelle une méthode
    void rate(float rate);

    /// Returns the current time (in ticks).
    // Appelle une méthode
    long time();

    /// Sets the current time (in ticks).
    // Appelle une méthode
    void time(long time);

    // Appelle une méthode
    boolean paused();

    // Appelle une méthode
    void pause();

    // Appelle une méthode
    void resume();

// Fin d'un bloc/d'une expression
}
