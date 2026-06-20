// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jctools.queues.MpscUnboundedArrayQueue;
// Import of a required class
import org.jctools.queues.atomic.MpscUnboundedAtomicArrayQueue;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.Executors;
// Import of a required class
import java.util.concurrent.ScheduledExecutorService;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
final class SchedulerImpl implements Scheduler {
    // Calls a method
    private static final AtomicInteger TASK_COUNTER = new AtomicInteger();
    // Assigns a value
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(r -> {
        // Calls a method
        Thread thread = new Thread(r);
        // Calls a method
        thread.setDaemon(true);
        // Returns a value to the caller
        return thread;
    // End of a block/expression
    });

    // Calls a method
    private final MessagePassingQueue<TaskImpl> tasksToExecute = ServerFlag.UNSAFE_COLLECTIONS ? new MpscUnboundedArrayQueue<>(64) : new MpscUnboundedAtomicArrayQueue<>(64);
    // Calls a method
    private final MessagePassingQueue<TaskImpl> tickEndTasksToExecute = ServerFlag.UNSAFE_COLLECTIONS ? new MpscUnboundedArrayQueue<>(64) : new MpscUnboundedAtomicArrayQueue<>(64);
    // Tasks scheduled on a certain tick/tick end
    // Calls a method
    private final Int2ObjectAVLTreeMap<List<TaskImpl>> tickStartTaskQueue = new Int2ObjectAVLTreeMap<>();
    // Calls a method
    private final Int2ObjectAVLTreeMap<List<TaskImpl>> tickEndTaskQueue = new Int2ObjectAVLTreeMap<>();

    // Code statement
    private int tickState;

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void process() {
        // Calls a method
        processTick(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void processTick() {
        // Calls a method
        processTick(1);
    // End of a block/expression
    }

    // Start of a method/block
    private void processTick(int tickDelta) {
        // Calls a method
        processTickTasks(tickStartTaskQueue, tasksToExecute, tickDelta);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void processTickEnd() {
        // Calls a method
        processTickTasks(tickEndTaskQueue, tickEndTasksToExecute, 0);
    // End of a block/expression
    }

    // Start of a method/block
    private void processTickTasks(Int2ObjectAVLTreeMap<List<TaskImpl>> targetTaskQueue, MessagePassingQueue<TaskImpl> targetTasksToExecute, int tickDelta) {
        // Start of a method/block
        synchronized (this) {
            // Access to the current/parent object
            this.tickState += tickDelta;
            // Code statement
            int tickToProcess;
            // Loop: repeats a block
            while (!targetTaskQueue.isEmpty() && (tickToProcess = targetTaskQueue.firstIntKey()) <= tickState) {
                // Calls a method
                final List<TaskImpl> tickScheduledTasks = targetTaskQueue.remove(tickToProcess);
                // Branch: checks a condition
                if (tickScheduledTasks != null) tickScheduledTasks.forEach(targetTasksToExecute::relaxedOffer);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        runTasks(targetTasksToExecute);
    // End of a block/expression
    }

    // Start of a method/block
    private void runTasks(MessagePassingQueue<TaskImpl> targetQueue) {
        // Run all tasks lock-free, either in the current thread or pool
        // Branch: checks a condition
        if (!targetQueue.isEmpty()) {
            // Start of a method/block
            targetQueue.drain(task -> {
                // Branch: checks a condition
                if (!task.isAlive()) return;
                // Calls a method
                handleTask(task);
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Code statement
    public Task submitTask(Supplier<TaskSchedule> task,
                                    // Start of a method/block
                                    ExecutionType executionType) {
        // Assigns a value
        final TaskImpl taskRef = new TaskImpl(TASK_COUNTER.getAndIncrement(), task,
                // Code statement
                executionType, this);
        // Calls a method
        handleTask(taskRef);
        // Returns a value to the caller
        return taskRef;
    // End of a block/expression
    }

    // Start of a method/block
    void unparkTask(TaskImpl task) {
        // Branch: checks a condition
        if (task.tryUnpark())
            // Access to the current/parent object
            this.tasksToExecute.relaxedOffer(task);
    // End of a block/expression
    }

    // Start of a method/block
    private void safeExecute(TaskImpl task) {
        // Prevent the task from being executed in the current thread
        // By either adding the task to the execution queue or submitting it to the pool
        // Multiple branching (switch/case)
        switch (task.executionType()) {
            // Multiple branching (switch/case)
            case TICK_START -> tasksToExecute.offer(task);
            // Multiple branching (switch/case)
            case TICK_END -> tickEndTasksToExecute.offer(task);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void handleTask(TaskImpl task) {
        // Code statement
        TaskSchedule schedule;
        // Exception handling
        try {
            // Calls a method
            schedule = task.task().get();
        // Start of a method/block
        } catch (Throwable t) {
            // Calls a method
            MinecraftServer.getExceptionManager().handleException(new RuntimeException("Exception in scheduled task", t));
            // Calls a method
            schedule = TaskSchedule.stop();
        // End of a block/expression
        }

        // Multiple branching (switch/case)
        switch (schedule) {
            // Multiple branching (switch/case)
            case TaskScheduleImpl.DurationSchedule durationSchedule -> {
                // Calls a method
                final Duration duration = durationSchedule.duration();
                // Calls a method
                SCHEDULER.schedule(() -> safeExecute(task), duration.toMillis(), TimeUnit.MILLISECONDS);
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case TaskScheduleImpl.TickSchedule tickSchedule -> {
                // Start of a method/block
                synchronized (this) {
                    // Calls a method
                    final int target = tickState + tickSchedule.tick();
                    // Assigns a value
                    var targetTaskQueue = switch (task.executionType()) {
                        // Multiple branching (switch/case)
                        case TICK_START -> tickStartTaskQueue;
                        // Multiple branching (switch/case)
                        case TICK_END -> tickEndTaskQueue;
                    // End of a block/expression
                    };
                    // Calls a method
                    targetTaskQueue.computeIfAbsent(target, i -> new ArrayList<>()).add(task);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case TaskScheduleImpl.FutureSchedule futureSchedule ->
                    // Calls a method
                    futureSchedule.future().thenRun(() -> safeExecute(task));
            // Multiple branching (switch/case)
            case TaskScheduleImpl.Park ignored -> task.parked = true;
            // Multiple branching (switch/case)
            case TaskScheduleImpl.Stop ignored -> task.cancel();
            // Multiple branching (switch/case)
            case TaskScheduleImpl.Immediate ignored -> {
                // Branch: checks a condition
                if (task.executionType() == ExecutionType.TICK_END) {
                    // Calls a method
                    tickEndTasksToExecute.relaxedOffer(task);
                // Alternative branch of the condition
                } else tasksToExecute.relaxedOffer(task);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
