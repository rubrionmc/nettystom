// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import java.util.concurrent.Executor;
// Import of a required class
import java.util.function.Supplier;

/**
 * Represents a scheduler that will execute tasks with a precision based on its ticking rate.
 * If precision is important, consider using a JDK executor service or any third party library.
 * <p>
 * Tasks are by default executed in the caller thread.
 */
// Type declaration (class/interface/enum/record)
public sealed interface Scheduler extends Executor permits SchedulerImpl, SchedulerManager {
    // Start of a method/block
    static Scheduler newScheduler() {
        // Returns a value to the caller
        return new SchedulerImpl();
    // End of a block/expression
    }

    /**
     * Process scheduled tasks based on time to increase scheduling precision.
     * <p>
     * This method is not thread-safe.
     */
    // Calls a method
    void process();

    /**
     * Advance 1 tick and call {@link #process()}.
     * <p>
     * This method is not thread-safe.
     */
    // Calls a method
    void processTick();

    /**
     * Execute tasks set to run at the end of this tick.
     * <p>
     * This method is not thread-safe.
     */
    // Calls a method
    void processTickEnd();

    /**
     * Submits a new task with custom scheduling logic.
     * <p>
     * This is the primitive method used by all scheduling shortcuts,
     * {@code task} is immediately executed in the caller thread to retrieve its scheduling state
     * and the task will stay alive as long as {@link TaskSchedule#stop()} is not returned (or {@link Task#cancel()} is called).
     *
     * @param task          the task to be directly executed in the caller thread
     * @param executionType the execution type
     * @return the created task
     */
    // Calls a method
    Task submitTask(Supplier<TaskSchedule> task, ExecutionType executionType);

    // Start of a method/block
    default Task submitTask(Supplier<TaskSchedule> task) {
        // Returns a value to the caller
        return submitTask(task, ExecutionType.TICK_START);
    // End of a block/expression
    }

    // Start of a method/block
    default Task.Builder buildTask(Runnable task) {
        // Returns a value to the caller
        return new Task.Builder(this, task);
    // End of a block/expression
    }

    // Code statement
    default Task scheduleTask(Runnable task,
                                       // Code statement
                                       TaskSchedule delay, TaskSchedule repeat,
                                       // Start of a method/block
                                       ExecutionType executionType) {
        // Returns a value to the caller
        return buildTask(task).delay(delay).repeat(repeat).executionType(executionType).schedule();
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleTask(Runnable task, TaskSchedule delay, TaskSchedule repeat) {
        // Returns a value to the caller
        return scheduleTask(task, delay, repeat, ExecutionType.TICK_START);
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleTask(Supplier<TaskSchedule> task, TaskSchedule delay) {
        // Returns a value to the caller
        return new Task.Builder(this, task).delay(delay).schedule();
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleNextTick(Runnable task, ExecutionType executionType) {
        // Returns a value to the caller
        return buildTask(task).delay(TaskSchedule.nextTick()).executionType(executionType).schedule();
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleNextTick(Runnable task) {
        // Returns a value to the caller
        return scheduleNextTick(task, ExecutionType.TICK_START);
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleEndOfTick(Runnable task) {
        // Returns a value to the caller
        return scheduleNextProcess(task, ExecutionType.TICK_END);
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleNextProcess(Runnable task, ExecutionType executionType) {
        // Returns a value to the caller
        return buildTask(task).delay(TaskSchedule.immediate()).executionType(executionType).schedule();
    // End of a block/expression
    }

    // Start of a method/block
    default Task scheduleNextProcess(Runnable task) {
        // Returns a value to the caller
        return scheduleNextProcess(task, ExecutionType.TICK_START);
    // End of a block/expression
    }

    /**
     * Implementation of {@link Executor}, proxies to {@link #scheduleNextTick(Runnable)}.
     * @param command the task to execute on the next tick
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    default void execute(Runnable command) {
        // Calls a method
        scheduleNextTick(command);
    // End of a block/expression
    }
// End of a block/expression
}
