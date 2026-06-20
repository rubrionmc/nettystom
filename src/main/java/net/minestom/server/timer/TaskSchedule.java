// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;

// Déclaration de type (classe/interface/enum/record)
public sealed interface TaskSchedule permits
        // Instruction de code
        TaskScheduleImpl.DurationSchedule,
        // Instruction de code
        TaskScheduleImpl.FutureSchedule,
        // Instruction de code
        TaskScheduleImpl.Immediate,
        // Instruction de code
        TaskScheduleImpl.Park,
        // Instruction de code
        TaskScheduleImpl.Stop,
        // Début d'une méthode/d'un bloc
        TaskScheduleImpl.TickSchedule {
    // Début d'une méthode/d'un bloc
    static TaskSchedule duration(Duration duration) {
        // Renvoie une valeur à l'appelant
        return new TaskScheduleImpl.DurationSchedule(duration);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule tick(int tick) {
        // Renvoie une valeur à l'appelant
        return new TaskScheduleImpl.TickSchedule(tick);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule future(CompletableFuture<?> future) {
        // Renvoie une valeur à l'appelant
        return new TaskScheduleImpl.FutureSchedule(future);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule park() {
        // Renvoie une valeur à l'appelant
        return TaskScheduleImpl.PARK;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule stop() {
        // Renvoie une valeur à l'appelant
        return TaskScheduleImpl.STOP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule immediate() {
        // Renvoie une valeur à l'appelant
        return TaskScheduleImpl.IMMEDIATE;
    // Fin d'un bloc/d'une expression
    }

    // Shortcuts

    // Début d'une méthode/d'un bloc
    static TaskSchedule duration(long amount, TemporalUnit unit) {
        // Renvoie une valeur à l'appelant
        return duration(Duration.of(amount, unit));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule nextTick() {
        // Renvoie une valeur à l'appelant
        return TaskScheduleImpl.NEXT_TICK;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule hours(long hours) {
        // Renvoie une valeur à l'appelant
        return duration(Duration.ofHours(hours));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule minutes(long minutes) {
        // Renvoie une valeur à l'appelant
        return duration(Duration.ofMinutes(minutes));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule seconds(long seconds) {
        // Renvoie une valeur à l'appelant
        return duration(Duration.ofSeconds(seconds));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static TaskSchedule millis(long millis) {
        // Renvoie une valeur à l'appelant
        return duration(Duration.ofMillis(millis));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
