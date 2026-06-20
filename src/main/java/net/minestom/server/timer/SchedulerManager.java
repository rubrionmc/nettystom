// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;

// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
public final class SchedulerManager implements Scheduler {
    // Calls a method
    private final Scheduler scheduler = Scheduler.newScheduler();
    // Calls a method
    private final MessagePassingQueue<Runnable> shutdownTasks = ConcurrentMessageQueues.mpscUnboundedArrayQueue(1024);
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void process() {
        // Access to the current/parent object
        this.scheduler.process();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void processTick() {
        // Access to the current/parent object
        this.scheduler.processTick();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void processTickEnd() {
        // Access to the current/parent object
        this.scheduler.processTickEnd();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public Task submitTask(Supplier<TaskSchedule> task,
                                    // Start of a method/block
                                    ExecutionType executionType) {
        // Returns a value to the caller
        return scheduler.submitTask(task, executionType);
    // End of a block/expression
    }

    // Start of a method/block
    public void shutdown() {
        // Access to the current/parent object
        this.shutdownTasks.drain(Runnable::run);
    // End of a block/expression
    }

    // Start of a method/block
    public void buildShutdownTask(Runnable runnable) {
        // Access to the current/parent object
        this.shutdownTasks.relaxedOffer(runnable);
    // End of a block/expression
    }
// End of a block/expression
}
