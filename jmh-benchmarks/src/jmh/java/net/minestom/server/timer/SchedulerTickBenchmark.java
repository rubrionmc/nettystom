// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.concurrent.TimeUnit;

// Annotation for the following element
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Fork(3)
// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@State(Scope.Benchmark)
// Type declaration (class/interface/enum/record)
public class SchedulerTickBenchmark {

    // Annotation for the following element
    @Param({"0", "1", "5"})
    // Code statement
    public int tickTasks;

    // Code statement
    Scheduler scheduler;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Access to the current/parent object
        this.scheduler = Scheduler.newScheduler();
        // Loop: repeats a block
        for (int i = 0; i < this.tickTasks; i++) {
            // Access to the current/parent object
            this.scheduler.scheduleTask(() -> {
            // Calls a method
            }, TaskSchedule.nextTick(), TaskSchedule.nextTick());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void call() {
        // Access to the current/parent object
        this.scheduler.processTick();
    // End of a block/expression
    }
// End of a block/expression
}
