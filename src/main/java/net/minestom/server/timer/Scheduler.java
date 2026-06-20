// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import java.util.concurrent.Executor;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Represents a scheduler that will execute tasks with a precision based on its ticking rate.
 * If precision is important, consider using a JDK executor service or any third party library.
 * <p>
 * Tasks are by default executed in the caller thread.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Scheduler extends Executor permits SchedulerImpl, SchedulerManager {
    // Début d'une méthode/d'un bloc
    static Scheduler newScheduler() {
        // Renvoie une valeur à l'appelant
        return new SchedulerImpl();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Process scheduled tasks based on time to increase scheduling precision.
     * <p>
     * This method is not thread-safe.
     */
    // Appelle une méthode
    void process();

    /**
     * Advance 1 tick and call {@link #process()}.
     * <p>
     * This method is not thread-safe.
     */
    // Appelle une méthode
    void processTick();

    /**
     * Execute tasks set to run at the end of this tick.
     * <p>
     * This method is not thread-safe.
     */
    // Appelle une méthode
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
    // Appelle une méthode
    Task submitTask(Supplier<TaskSchedule> task, ExecutionType executionType);

    // Début d'une méthode/d'un bloc
    default Task submitTask(Supplier<TaskSchedule> task) {
        // Renvoie une valeur à l'appelant
        return submitTask(task, ExecutionType.TICK_START);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task.Builder buildTask(Runnable task) {
        // Renvoie une valeur à l'appelant
        return new Task.Builder(this, task);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    default Task scheduleTask(Runnable task,
                                       // Instruction de code
                                       TaskSchedule delay, TaskSchedule repeat,
                                       // Début d'une méthode/d'un bloc
                                       ExecutionType executionType) {
        // Renvoie une valeur à l'appelant
        return buildTask(task).delay(delay).repeat(repeat).executionType(executionType).schedule();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleTask(Runnable task, TaskSchedule delay, TaskSchedule repeat) {
        // Renvoie une valeur à l'appelant
        return scheduleTask(task, delay, repeat, ExecutionType.TICK_START);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleTask(Supplier<TaskSchedule> task, TaskSchedule delay) {
        // Renvoie une valeur à l'appelant
        return new Task.Builder(this, task).delay(delay).schedule();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleNextTick(Runnable task, ExecutionType executionType) {
        // Renvoie une valeur à l'appelant
        return buildTask(task).delay(TaskSchedule.nextTick()).executionType(executionType).schedule();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleNextTick(Runnable task) {
        // Renvoie une valeur à l'appelant
        return scheduleNextTick(task, ExecutionType.TICK_START);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleEndOfTick(Runnable task) {
        // Renvoie une valeur à l'appelant
        return scheduleNextProcess(task, ExecutionType.TICK_END);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleNextProcess(Runnable task, ExecutionType executionType) {
        // Renvoie une valeur à l'appelant
        return buildTask(task).delay(TaskSchedule.immediate()).executionType(executionType).schedule();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default Task scheduleNextProcess(Runnable task) {
        // Renvoie une valeur à l'appelant
        return scheduleNextProcess(task, ExecutionType.TICK_START);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Implementation of {@link Executor}, proxies to {@link #scheduleNextTick(Runnable)}.
     * @param command the task to execute on the next tick
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default void execute(Runnable command) {
        // Appelle une méthode
        scheduleNextTick(command);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
