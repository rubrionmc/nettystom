// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
public sealed interface Task permits TaskImpl {
    // Appelle une méthode
    int id();

    // Appelle une méthode
    ExecutionType executionType();

    // Appelle une méthode
    Scheduler owner();

    /**
     * Unpark the tasks to be executed during next processing.
     */
    // Appelle une méthode
    void unpark();

    // Appelle une méthode
    boolean isParked();

    // Appelle une méthode
    void cancel();

    // Appelle une méthode
    boolean isAlive();

    // Déclaration de type (classe/interface/enum/record)
    final class Builder {
        // Instruction de code
        private final Scheduler scheduler;
        // Instruction de code
        private final Supplier<TaskSchedule> innerTask;
        // Affecte une valeur
        private ExecutionType executionType = ExecutionType.TICK_START;
        // Appelle une méthode
        private TaskSchedule delay = TaskSchedule.immediate();
        // Appelle une méthode
        private TaskSchedule repeat = TaskSchedule.stop();
        // Instruction de code
        private boolean repeatOverride;

        // Début d'une méthode/d'un bloc
        Builder(Scheduler scheduler, Supplier<TaskSchedule> innerTask) {
            // Accès à l'objet courant/parent
            this.scheduler = scheduler;
            // Accès à l'objet courant/parent
            this.innerTask = innerTask;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        Builder(Scheduler scheduler, Runnable runnable) {
            // Accès à l'objet courant/parent
            this.scheduler = scheduler;
            // Accès à l'objet courant/parent
            this.innerTask = () -> {
                // Appelle une méthode
                runnable.run();
                // Renvoie une valeur à l'appelant
                return TaskSchedule.stop();
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder executionType(ExecutionType executionType) {
            // Accès à l'objet courant/parent
            this.executionType = executionType;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder delay(TaskSchedule schedule) {
            // Accès à l'objet courant/parent
            this.delay = schedule;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder repeat(TaskSchedule schedule) {
            // Accès à l'objet courant/parent
            this.repeat = schedule;
            // Accès à l'objet courant/parent
            this.repeatOverride = true;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Task schedule() {
            // Affecte une valeur
            var innerTask = this.innerTask;
            // Affecte une valeur
            var delay = this.delay;
            // Affecte une valeur
            var repeat = this.repeat;
            // Affecte une valeur
            var repeatOverride = this.repeatOverride;
            // Affecte une valeur
            var executionType = this.executionType;
            // Renvoie une valeur à l'appelant
            return scheduler.submitTask(new Supplier<>() {
                // Affecte une valeur
                boolean first = true;

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public TaskSchedule get() {
                    // Embranchement : vérifie une condition
                    if (first) {
                        // Affecte une valeur
                        first = false;
                        // Renvoie une valeur à l'appelant
                        return delay;
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    TaskSchedule schedule = innerTask.get();
                    // Embranchement : vérifie une condition
                    if (repeatOverride) {
                        // Renvoie une valeur à l'appelant
                        return repeat;
                    // Fin d'un bloc/d'une expression
                    }
                    // Renvoie une valeur à l'appelant
                    return schedule;
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            }, executionType);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder delay(Duration duration) {
            // Renvoie une valeur à l'appelant
            return delay(TaskSchedule.duration(duration));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder delay(long time, TemporalUnit unit) {
            // Renvoie une valeur à l'appelant
            return delay(Duration.of(time, unit));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder repeat(Duration duration) {
            // Renvoie une valeur à l'appelant
            return repeat(TaskSchedule.duration(duration));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Builder repeat(long time, TemporalUnit unit) {
            // Renvoie une valeur à l'appelant
            return repeat(Duration.of(time, unit));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
