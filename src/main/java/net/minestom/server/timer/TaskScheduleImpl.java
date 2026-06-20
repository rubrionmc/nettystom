// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

// Déclaration de type (classe/interface/enum/record)
final class TaskScheduleImpl {
    // Appelle une méthode
    static TaskSchedule NEXT_TICK = new TickSchedule(1);
    // Appelle une méthode
    static TaskSchedule PARK = new Park();
    // Appelle une méthode
    static TaskSchedule STOP = new Stop();
    // Appelle une méthode
    static TaskSchedule IMMEDIATE = new Immediate();

    // Déclaration de type (classe/interface/enum/record)
    record DurationSchedule(Duration duration) implements TaskSchedule {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TickSchedule(int tick) implements TaskSchedule {
        // Début d'une méthode/d'un bloc
        public TickSchedule {
            // Embranchement : vérifie une condition
            if (tick <= 0)
                // Lève une exception
                throw new IllegalArgumentException("Tick must be greater than 0 (" + tick + ")");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record FutureSchedule(CompletableFuture<?> future) implements TaskSchedule {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Park() implements TaskSchedule {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Stop() implements TaskSchedule {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Immediate() implements TaskSchedule {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
