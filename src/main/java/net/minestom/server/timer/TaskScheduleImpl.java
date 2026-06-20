// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.concurrent.CompletableFuture;

// Type declaration (class/interface/enum/record)
final class TaskScheduleImpl {
    // Calls a method
    static final TaskSchedule NEXT_TICK = new TickSchedule(1);
    // Calls a method
    static final TaskSchedule PARK = new Park();
    // Calls a method
    static final TaskSchedule STOP = new Stop();
    // Calls a method
    static final TaskSchedule IMMEDIATE = new Immediate();

    // Type declaration (class/interface/enum/record)
    record DurationSchedule(Duration duration) implements TaskSchedule {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TickSchedule(int tick) implements TaskSchedule {
        // Start of a method/block
        public TickSchedule {
            // Branch: checks a condition
            if (tick <= 0)
                // Throws an exception
                throw new IllegalArgumentException("Tick must be greater than 0 (" + tick + ")");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record FutureSchedule(CompletableFuture<?> future) implements TaskSchedule {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Park() implements TaskSchedule {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Stop() implements TaskSchedule {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Immediate() implements TaskSchedule {
    // End of a block/expression
    }
// End of a block/expression
}
