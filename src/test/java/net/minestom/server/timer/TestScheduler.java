// Déclaration du paquet de ce fichier
package net.minestom.server.timer;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class TestScheduler {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tickTask() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        Task task = scheduler.scheduleNextTick(() -> result.set(true));
        // Appelle une méthode
        assertEquals(task.executionType(), ExecutionType.TICK_START, "Tasks default execution type should be tick start");

        // Appelle une méthode
        assertFalse(result.get(), "Tick task should not be executed after scheduling");
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertFalse(result.get(), "Tick task should not be executed after process");
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertFalse(result.get(), "Tick task should not be executed after processTickEnd");
        // Appelle une méthode
        scheduler.processTick();
        // Appelle une méthode
        assertTrue(result.get(), "Tick task must be executed after tick process");
        // Appelle une méthode
        assertFalse(task.isAlive(), "Tick task should be cancelled after execution");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void durationTask() throws InterruptedException {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Instruction de code
        scheduler.buildTask(() -> result.set(true))
                // Instruction de code
                .delay(TaskSchedule.seconds(1))
                // Appelle une méthode
                .schedule();
        // Appelle une méthode
        Thread.sleep(100);
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertFalse(result.get(), "900ms remaining");
        // Appelle une méthode
        Thread.sleep(1200);
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertTrue(result.get(), "Tick task must be executed after 1 second");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void immediateTask() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        scheduler.scheduleNextProcess(() -> result.set(true));
        // Appelle une méthode
        assertFalse(result.get());
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertFalse(result.get(), "processTickEnd should never execute immediate tasks unless it is of type TICK_END");
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertTrue(result.get());

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertFalse(result.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void cancelTask() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Affecte une valeur
        var task = scheduler.buildTask(() -> result.set(true))
                // Appelle une méthode
                .schedule();
        // Appelle une méthode
        assertTrue(task.isAlive(), "Task should still be alive");
        // Appelle une méthode
        task.cancel();
        // Appelle une méthode
        assertFalse(task.isAlive(), "Task should not be alive anymore");
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertFalse(result.get(), "Task should be cancelled");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void parkTask() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Ignored parked task
        // Instruction de code
        scheduler.buildTask(() -> fail("This parked task should never be executed"))
                // Instruction de code
                .executionType(ExecutionType.TICK_START)
                // Instruction de code
                .delay(TaskSchedule.park())
                // Appelle une méthode
                .schedule();

        // Unpark task
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Affecte une valeur
        var task = scheduler.buildTask(() -> result.set(true))
                // Instruction de code
                .delay(TaskSchedule.park())
                // Appelle une méthode
                .schedule();
        // Appelle une méthode
        assertTrue(task.isParked());
        // Appelle une méthode
        assertFalse(result.get(), "Task hasn't been unparked yet");
        // Appelle une méthode
        task.unpark();
        // Appelle une méthode
        assertFalse(task.isParked());
        // Appelle une méthode
        assertFalse(result.get(), "Tasks must be processed first");
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertFalse(task.isParked());
        // Appelle une méthode
        assertTrue(result.get(), "Parked task should be executed");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void futureTask() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Affecte une valeur
        CompletableFuture<Void> future = new CompletableFuture<>();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Instruction de code
        scheduler.buildTask(() -> result.set(true))
                // Instruction de code
                .delay(TaskSchedule.future(future))
                // Appelle une méthode
                .schedule();
        // Appelle une méthode
        assertFalse(result.get(), "Future is not completed yet");
        // Appelle une méthode
        future.complete(null);
        // Appelle une méthode
        assertFalse(result.get(), "Tasks must be processed first");
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertTrue(result.get(), "Future should be completed");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void exceptionTask() {
        // Appelle une méthode
        MinecraftServer.init();
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Début d'une méthode/d'un bloc
        scheduler.scheduleNextTick(() -> {
            // Lève une exception
            throw new RuntimeException("Test exception");
        // Fin d'un bloc/d'une expression
        });

        // This is a bit of a weird use case. I dont want this test to depend on the order the scheduler executes in
        // so this is a guess that the first one wont be before all 100 of the ones scheduled below.
        // Not great, but should be fine anyway.
        // Appelle une méthode
        AtomicInteger executed = new AtomicInteger(0);
        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            scheduler.scheduleNextTick(executed::incrementAndGet);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        assertDoesNotThrow(scheduler::processTick);
        // Appelle une méthode
        assertEquals(100, executed.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void scheduleEndOfTick() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Appelle une méthode
        scheduler.scheduleEndOfTick(() -> result.set(true));
        // Appelle une méthode
        assertFalse(result.get(), "End of tick tasks should not be executed immediately upon submission");
        // Appelle une méthode
        scheduler.processTick();
        // Appelle une méthode
        assertFalse(result.get(), "End of tick tasks should not be executed by processTick()");
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertTrue(result.get(), "scheduleEndOfTick(...) tasks should be executed after the next call to processTickEnd()");

        // Appelle une méthode
        result.set(false);
        // Appelle une méthode
        scheduler.scheduleEndOfTick(() -> result.set(true));
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertTrue(result.get(), "scheduleEndOfTick(...) tasks should always execute on the very next processTickEnd()");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void delayedEndOfTick() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Instruction de code
        scheduler.buildTask(() -> result.set(true)).delay(TaskSchedule.tick(1))
                // Appelle une méthode
                .executionType(ExecutionType.TICK_END).schedule();

        // Appelle une méthode
        scheduler.processTickEnd(); scheduler.processTickEnd();
        // Appelle une méthode
        assertFalse(result.get(), "processTickEnd() should not increment the scheduler's internal tick counter");
        // Appelle une méthode
        scheduler.processTick();
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertTrue(result.get(), "processTick() should increment the current tick counter processTickEnd() uses");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void repeatingEndOfTick() {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicInteger result = new AtomicInteger(0);
        // Appelle une méthode
        Task task = scheduler.scheduleTask(result::getAndIncrement, TaskSchedule.immediate(), TaskSchedule.tick(1), ExecutionType.TICK_END);
        // Appelle une méthode
        assertEquals(0, result.get(), "TICK_END tasks should not be executed immediately upon submission");
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertEquals(1, result.get(), "processTickEnd() should always execute TaskSchedule.immediate() TICK_END tasks");
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertEquals(1, result.get(), "task should not executed on processTickEnd() again until processTick() is called");
        // Appelle une méthode
        scheduler.processTick();
        // Appelle une méthode
        assertEquals(1, result.get(), "processTick() should never execute TICK_END tasks");
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertEquals(2, result.get(), "processTickEnd() should execute this task");

        // Appelle une méthode
        task.cancel();
        // Appelle une méthode
        scheduler.processTick();
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertEquals(2, result.get(), "this task should have been cancelled");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void durationEndOfTick() throws InterruptedException {
        // Appelle une méthode
        Scheduler scheduler = Scheduler.newScheduler();
        // Appelle une méthode
        AtomicBoolean result = new AtomicBoolean(false);
        // Instruction de code
        scheduler.buildTask(() -> result.set(true))
                // Instruction de code
                .delay(TaskSchedule.seconds(1))
                // Instruction de code
                .executionType(ExecutionType.TICK_END)
                // Appelle une méthode
                .schedule();
        // Appelle une méthode
        Thread.sleep(100);
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertFalse(result.get(), "900ms remaining");
        // Appelle une méthode
        Thread.sleep(1200);
        // Appelle une méthode
        scheduler.process();
        // Appelle une méthode
        assertFalse(result.get(), "process() should never execute TICK_END tasks");
        // Appelle une méthode
        scheduler.processTickEnd();
        // Appelle une méthode
        assertTrue(result.get(), "Tick end task must be executed after 1 second");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
