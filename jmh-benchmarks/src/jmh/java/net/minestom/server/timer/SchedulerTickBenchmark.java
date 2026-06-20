// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;

// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(3)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Déclaration de type (classe/interface/enum/record)
public class SchedulerTickBenchmark {

    // Annotation pour l'élément suivant
    @Param({"0", "1", "5"})
    // Instruction de code
    public int tickTasks;

    // Instruction de code
    Scheduler scheduler;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Accès à l'objet courant/parent
        this.scheduler = Scheduler.newScheduler();
        // Boucle : répète un bloc
        for (int i = 0; i < this.tickTasks; i++) {
            // Accès à l'objet courant/parent
            this.scheduler.scheduleTask(() -> {
            // Appelle une méthode
            }, TaskSchedule.nextTick(), TaskSchedule.nextTick());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void call() {
        // Accès à l'objet courant/parent
        this.scheduler.processTick();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
