// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;
// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
public sealed interface Task permits TaskImpl {
    // Calls a method
    int id();

    // Calls a method
    ExecutionType executionType();

    // Calls a method
    Scheduler owner();

    /**
     * Unpark the tasks to be executed during next processing.
     */
    // Calls a method
    void unpark();

    // Calls a method
    boolean isParked();

    // Calls a method
    void cancel();

    // Calls a method
    boolean isAlive();

    // Type declaration (class/interface/enum/record)
    final class Builder {
        // Code statement
        private final Scheduler scheduler;
        // Code statement
        private final Supplier<TaskSchedule> innerTask;
        // Assigns a value
        private ExecutionType executionType = ExecutionType.TICK_START;
        // Calls a method
        private TaskSchedule delay = TaskSchedule.immediate();
        // Calls a method
        private TaskSchedule repeat = TaskSchedule.stop();
        // Code statement
        private boolean repeatOverride;

        // Start of a method/block
        Builder(Scheduler scheduler, Supplier<TaskSchedule> innerTask) {
            // Access to the current/parent object
            this.scheduler = scheduler;
            // Access to the current/parent object
            this.innerTask = innerTask;
        // End of a block/expression
        }

        // Start of a method/block
        Builder(Scheduler scheduler, Runnable runnable) {
            // Access to the current/parent object
            this.scheduler = scheduler;
            // Access to the current/parent object
            this.innerTask = () -> {
                // Calls a method
                runnable.run();
                // Returns a value to the caller
                return TaskSchedule.stop();
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        public Builder executionType(ExecutionType executionType) {
            // Access to the current/parent object
            this.executionType = executionType;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder delay(TaskSchedule schedule) {
            // Access to the current/parent object
            this.delay = schedule;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Builder repeat(TaskSchedule schedule) {
            // Access to the current/parent object
            this.repeat = schedule;
            // Access to the current/parent object
            this.repeatOverride = true;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Task schedule() {
            // Assigns a value
            var innerTask = this.innerTask;
            // Assigns a value
            var delay = this.delay;
            // Assigns a value
            var repeat = this.repeat;
            // Assigns a value
            var repeatOverride = this.repeatOverride;
            // Assigns a value
            var executionType = this.executionType;
            // Returns a value to the caller
            return scheduler.submitTask(new Supplier<>() {
                // Assigns a value
                boolean first = true;

                // Annotation for the following element
                @Override
                // Start of a method/block
                public TaskSchedule get() {
                    // Branch: checks a condition
                    if (first) {
                        // Assigns a value
                        first = false;
                        // Returns a value to the caller
                        return delay;
                    // End of a block/expression
                    }
                    // Calls a method
                    TaskSchedule schedule = innerTask.get();
                    // Branch: checks a condition
                    if (repeatOverride) {
                        // Returns a value to the caller
                        return repeat;
                    // End of a block/expression
                    }
                    // Returns a value to the caller
                    return schedule;
                // End of a block/expression
                }
            // Code statement
            }, executionType);
        // End of a block/expression
        }

        // Start of a method/block
        public Builder delay(Duration duration) {
            // Returns a value to the caller
            return delay(TaskSchedule.duration(duration));
        // End of a block/expression
        }

        // Start of a method/block
        public Builder delay(long time, TemporalUnit unit) {
            // Returns a value to the caller
            return delay(Duration.of(time, unit));
        // End of a block/expression
        }

        // Start of a method/block
        public Builder repeat(Duration duration) {
            // Returns a value to the caller
            return repeat(TaskSchedule.duration(duration));
        // End of a block/expression
        }

        // Start of a method/block
        public Builder repeat(long time, TemporalUnit unit) {
            // Returns a value to the caller
            return repeat(Duration.of(time, unit));
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
