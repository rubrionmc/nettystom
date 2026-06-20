// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jctools.queues.MpscUnboundedArrayQueue;
// Import d'une classe nécessaire
import org.jctools.queues.atomic.MpscUnboundedAtomicArrayQueue;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.Executors;
// Import d'une classe nécessaire
import java.util.concurrent.ScheduledExecutorService;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
final class SchedulerImpl implements Scheduler {
    // Appelle une méthode
    private static final AtomicInteger TASK_COUNTER = new AtomicInteger();
    // Affecte une valeur
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(r -> {
        // Appelle une méthode
        Thread thread = new Thread(r);
        // Appelle une méthode
        thread.setDaemon(true);
        // Renvoie une valeur à l'appelant
        return thread;
    // Fin d'un bloc/d'une expression
    });

    // Affecte une valeur
    private final MessagePassingQueue<TaskImpl> tasksToExecute = ServerFlag.UNSAFE_COLLECTIONS ? new MpscUnboundedArrayQueue<>(64) : new MpscUnboundedAtomicArrayQueue<>(64);
    // Affecte une valeur
    private final MessagePassingQueue<TaskImpl> tickEndTasksToExecute = ServerFlag.UNSAFE_COLLECTIONS ? new MpscUnboundedArrayQueue<>(64) : new MpscUnboundedAtomicArrayQueue<>(64);
    // Tasks scheduled on a certain tick/tick end
    // Affecte une valeur
    private final Int2ObjectAVLTreeMap<List<TaskImpl>> tickStartTaskQueue = new Int2ObjectAVLTreeMap<>();
    // Affecte une valeur
    private final Int2ObjectAVLTreeMap<List<TaskImpl>> tickEndTaskQueue = new Int2ObjectAVLTreeMap<>();

    // Instruction de code
    private int tickState;

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void process() {
        // Appelle une méthode
        processTick(0);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void processTick() {
        // Appelle une méthode
        processTick(1);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processTick(int tickDelta) {
        // Appelle une méthode
        processTickTasks(tickStartTaskQueue, tasksToExecute, tickDelta);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void processTickEnd() {
        // Appelle une méthode
        processTickTasks(tickEndTaskQueue, tickEndTasksToExecute, 0);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processTickTasks(Int2ObjectAVLTreeMap<List<TaskImpl>> targetTaskQueue, MessagePassingQueue<TaskImpl> targetTasksToExecute, int tickDelta) {
        // Début d'une méthode/d'un bloc
        synchronized (this) {
            // Accès à l'objet courant/parent
            this.tickState += tickDelta;
            // Instruction de code
            int tickToProcess;
            // Boucle : répète un bloc
            while (!targetTaskQueue.isEmpty() && (tickToProcess = targetTaskQueue.firstIntKey()) <= tickState) {
                // Appelle une méthode
                final List<TaskImpl> tickScheduledTasks = targetTaskQueue.remove(tickToProcess);
                // Embranchement : vérifie une condition
                if (tickScheduledTasks != null) tickScheduledTasks.forEach(targetTasksToExecute::relaxedOffer);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        runTasks(targetTasksToExecute);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void runTasks(MessagePassingQueue<TaskImpl> targetQueue) {
        // Run all tasks lock-free, either in the current thread or pool
        // Embranchement : vérifie une condition
        if (!targetQueue.isEmpty()) {
            // Début d'une méthode/d'un bloc
            targetQueue.drain(task -> {
                // Embranchement : vérifie une condition
                if (!task.isAlive()) return;
                // Appelle une méthode
                handleTask(task);
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public Task submitTask(Supplier<TaskSchedule> task,
                                    // Début d'une méthode/d'un bloc
                                    ExecutionType executionType) {
        // Affecte une valeur
        final TaskImpl taskRef = new TaskImpl(TASK_COUNTER.getAndIncrement(), task,
                // Instruction de code
                executionType, this);
        // Appelle une méthode
        handleTask(taskRef);
        // Renvoie une valeur à l'appelant
        return taskRef;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void unparkTask(TaskImpl task) {
        // Embranchement : vérifie une condition
        if (task.tryUnpark())
            // Accès à l'objet courant/parent
            this.tasksToExecute.relaxedOffer(task);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void safeExecute(TaskImpl task) {
        // Prevent the task from being executed in the current thread
        // By either adding the task to the execution queue or submitting it to the pool
        // Embranchement multiple (switch/case)
        switch (task.executionType()) {
            // Embranchement multiple (switch/case)
            case TICK_START -> tasksToExecute.offer(task);
            // Embranchement multiple (switch/case)
            case TICK_END -> tickEndTasksToExecute.offer(task);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void handleTask(TaskImpl task) {
        // Instruction de code
        TaskSchedule schedule;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            schedule = task.task().get();
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Appelle une méthode
            MinecraftServer.getExceptionManager().handleException(new RuntimeException("Exception in scheduled task", t));
            // Appelle une méthode
            schedule = TaskSchedule.stop();
        // Fin d'un bloc/d'une expression
        }

        // Embranchement multiple (switch/case)
        switch (schedule) {
            // Embranchement multiple (switch/case)
            case TaskScheduleImpl.DurationSchedule durationSchedule -> {
                // Appelle une méthode
                final Duration duration = durationSchedule.duration();
                // Appelle une méthode
                SCHEDULER.schedule(() -> safeExecute(task), duration.toMillis(), TimeUnit.MILLISECONDS);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case TaskScheduleImpl.TickSchedule tickSchedule -> {
                // Début d'une méthode/d'un bloc
                synchronized (this) {
                    // Appelle une méthode
                    final int target = tickState + tickSchedule.tick();
                    // Affecte une valeur
                    var targetTaskQueue = switch (task.executionType()) {
                        // Embranchement multiple (switch/case)
                        case TICK_START -> tickStartTaskQueue;
                        // Embranchement multiple (switch/case)
                        case TICK_END -> tickEndTaskQueue;
                    // Fin d'un bloc/d'une expression
                    };
                    // Appelle une méthode
                    targetTaskQueue.computeIfAbsent(target, i -> new ArrayList<>()).add(task);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case TaskScheduleImpl.FutureSchedule futureSchedule ->
                    // Appelle une méthode
                    futureSchedule.future().thenRun(() -> safeExecute(task));
            // Embranchement multiple (switch/case)
            case TaskScheduleImpl.Park ignored -> task.parked = true;
            // Embranchement multiple (switch/case)
            case TaskScheduleImpl.Stop ignored -> task.cancel();
            // Embranchement multiple (switch/case)
            case TaskScheduleImpl.Immediate ignored -> {
                // Embranchement : vérifie une condition
                if (task.executionType() == ExecutionType.TICK_END) {
                    // Appelle une méthode
                    tickEndTasksToExecute.relaxedOffer(task);
                // Branche alternative de la condition
                } else tasksToExecute.relaxedOffer(task);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
