// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;

// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
public final class SchedulerManager implements Scheduler {
    // Appelle une méthode
    private final Scheduler scheduler = Scheduler.newScheduler();
    // Appelle une méthode
    private final MessagePassingQueue<Runnable> shutdownTasks = ConcurrentMessageQueues.mpscUnboundedArrayQueue(1024);
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void process() {
        // Accès à l'objet courant/parent
        this.scheduler.process();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void processTick() {
        // Accès à l'objet courant/parent
        this.scheduler.processTick();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void processTickEnd() {
        // Accès à l'objet courant/parent
        this.scheduler.processTickEnd();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Instruction de code
    public Task submitTask(Supplier<TaskSchedule> task,
                                    // Début d'une méthode/d'un bloc
                                    ExecutionType executionType) {
        // Renvoie une valeur à l'appelant
        return scheduler.submitTask(task, executionType);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void shutdown() {
        // Accès à l'objet courant/parent
        this.shutdownTasks.drain(Runnable::run);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void buildShutdownTask(Runnable runnable) {
        // Accès à l'objet courant/parent
        this.shutdownTasks.relaxedOffer(runnable);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
