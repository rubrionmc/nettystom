// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.world.clock.WorldClock;

// Type declaration (class/interface/enum/record)
public sealed interface Clock permits Instance.ClockInstance {

    // Calls a method
    RegistryKey<WorldClock> clock();

    /// Gets the rate at which the clock advances per tick, in partial ticks.
    ///
    /// The default is 1 (advance one tick per tick).
    // Calls a method
    float rate();

    /// Sets the rate at which the clock advances per tick, in partial ticks.
    ///
    /// The default is 1 (advance one tick per tick).
    // Calls a method
    void rate(float rate);

    /// Returns the current time (in ticks).
    // Calls a method
    long time();

    /// Sets the current time (in ticks).
    // Calls a method
    void time(long time);

    // Calls a method
    boolean paused();

    // Calls a method
    void pause();

    // Calls a method
    void resume();

// End of a block/expression
}
