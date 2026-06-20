// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;
// Import of a required class
import java.util.concurrent.CompletableFuture;

// Type declaration (class/interface/enum/record)
public sealed interface TaskSchedule permits
        // Code statement
        TaskScheduleImpl.DurationSchedule,
        // Code statement
        TaskScheduleImpl.FutureSchedule,
        // Code statement
        TaskScheduleImpl.Immediate,
        // Code statement
        TaskScheduleImpl.Park,
        // Code statement
        TaskScheduleImpl.Stop,
        // Start of a method/block
        TaskScheduleImpl.TickSchedule {
    // Start of a method/block
    static TaskSchedule duration(Duration duration) {
        // Returns a value to the caller
        return new TaskScheduleImpl.DurationSchedule(duration);
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule tick(int tick) {
        // Returns a value to the caller
        return new TaskScheduleImpl.TickSchedule(tick);
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule future(CompletableFuture<?> future) {
        // Returns a value to the caller
        return new TaskScheduleImpl.FutureSchedule(future);
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule park() {
        // Returns a value to the caller
        return TaskScheduleImpl.PARK;
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule stop() {
        // Returns a value to the caller
        return TaskScheduleImpl.STOP;
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule immediate() {
        // Returns a value to the caller
        return TaskScheduleImpl.IMMEDIATE;
    // End of a block/expression
    }

    // Shortcuts

    // Start of a method/block
    static TaskSchedule duration(long amount, TemporalUnit unit) {
        // Returns a value to the caller
        return duration(Duration.of(amount, unit));
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule nextTick() {
        // Returns a value to the caller
        return TaskScheduleImpl.NEXT_TICK;
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule hours(long hours) {
        // Returns a value to the caller
        return duration(Duration.ofHours(hours));
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule minutes(long minutes) {
        // Returns a value to the caller
        return duration(Duration.ofMinutes(minutes));
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule seconds(long seconds) {
        // Returns a value to the caller
        return duration(Duration.ofSeconds(seconds));
    // End of a block/expression
    }

    // Start of a method/block
    static TaskSchedule millis(long millis) {
        // Returns a value to the caller
        return duration(Duration.ofMillis(millis));
    // End of a block/expression
    }
// End of a block/expression
}
