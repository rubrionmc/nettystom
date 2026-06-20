// Package declaration for this file
package net.minestom.server.timer;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class TestScheduler {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void tickTask() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        Task task = scheduler.scheduleNextTick(() -> result.set(true));
        // Calls a method
        assertEquals(ExecutionType.TICK_START, task.executionType(), "Tasks default execution type should be tick start");

        // Calls a method
        assertFalse(result.get(), "Tick task should not be executed after scheduling");
        // Calls a method
        scheduler.process();
        // Calls a method
        assertFalse(result.get(), "Tick task should not be executed after process");
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertFalse(result.get(), "Tick task should not be executed after processTickEnd");
        // Calls a method
        scheduler.processTick();
        // Calls a method
        assertTrue(result.get(), "Tick task must be executed after tick process");
        // Calls a method
        assertFalse(task.isAlive(), "Tick task should be cancelled after execution");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void durationTask() throws InterruptedException {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Code statement
        scheduler.buildTask(() -> result.set(true))
                // Code statement
                .delay(TaskSchedule.seconds(1))
                // Calls a method
                .schedule();
        // Calls a method
        Thread.sleep(100);
        // Calls a method
        scheduler.process();
        // Calls a method
        assertFalse(result.get(), "900ms remaining");
        // Calls a method
        Thread.sleep(1200);
        // Calls a method
        scheduler.process();
        // Calls a method
        assertTrue(result.get(), "Tick task must be executed after 1 second");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void immediateTask() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        scheduler.scheduleNextProcess(() -> result.set(true));
        // Calls a method
        assertFalse(result.get());
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertFalse(result.get(), "processTickEnd should never execute immediate tasks unless it is of type TICK_END");
        // Calls a method
        scheduler.process();
        // Calls a method
        assertTrue(result.get());

        // Calls a method
        result.set(false);
        // Calls a method
        scheduler.process();
        // Calls a method
        assertFalse(result.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void cancelTask() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Assigns a value
        var task = scheduler.buildTask(() -> result.set(true))
                // Calls a method
                .schedule();
        // Calls a method
        assertTrue(task.isAlive(), "Task should still be alive");
        // Calls a method
        task.cancel();
        // Calls a method
        assertFalse(task.isAlive(), "Task should not be alive anymore");
        // Calls a method
        scheduler.process();
        // Calls a method
        assertFalse(result.get(), "Task should be cancelled");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void parkTask() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Ignored parked task
        // Code statement
        scheduler.buildTask(() -> fail("This parked task should never be executed"))
                // Code statement
                .executionType(ExecutionType.TICK_START)
                // Code statement
                .delay(TaskSchedule.park())
                // Calls a method
                .schedule();

        // Unpark task
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Assigns a value
        var task = scheduler.buildTask(() -> result.set(true))
                // Code statement
                .delay(TaskSchedule.park())
                // Calls a method
                .schedule();
        // Calls a method
        assertTrue(task.isParked());
        // Calls a method
        assertFalse(result.get(), "Task hasn't been unparked yet");
        // Calls a method
        task.unpark();
        // Calls a method
        assertFalse(task.isParked());
        // Calls a method
        assertFalse(result.get(), "Tasks must be processed first");
        // Calls a method
        scheduler.process();
        // Calls a method
        assertFalse(task.isParked());
        // Calls a method
        assertTrue(result.get(), "Parked task should be executed");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void futureTask() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        CompletableFuture<Void> future = new CompletableFuture<>();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Code statement
        scheduler.buildTask(() -> result.set(true))
                // Code statement
                .delay(TaskSchedule.future(future))
                // Calls a method
                .schedule();
        // Calls a method
        assertFalse(result.get(), "Future is not completed yet");
        // Calls a method
        future.complete(null);
        // Calls a method
        assertFalse(result.get(), "Tasks must be processed first");
        // Calls a method
        scheduler.process();
        // Calls a method
        assertTrue(result.get(), "Future should be completed");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void exceptionTask() {
        // Calls a method
        MinecraftServer.init();
        // Calls a method
        MinecraftServer.getExceptionManager().setExceptionHandler(Assertions::assertNotNull);
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Start of a method/block
        scheduler.scheduleNextTick(() -> {
            // Throws an exception
            throw new RuntimeException("Test exception");
        // End of a block/expression
        });

        // This is a bit of a weird use case. I dont want this test to depend on the order the scheduler executes in
        // so this is a guess that the first one wont be before all 100 of the ones scheduled below.
        // Not great, but should be fine anyway.
        // Calls a method
        AtomicInteger executed = new AtomicInteger(0);
        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            scheduler.scheduleNextTick(executed::incrementAndGet);
        // End of a block/expression
        }

        // Calls a method
        assertDoesNotThrow(scheduler::processTick);
        // Calls a method
        assertEquals(100, executed.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void scheduleEndOfTick() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Calls a method
        scheduler.scheduleEndOfTick(() -> result.set(true));
        // Calls a method
        assertFalse(result.get(), "End of tick tasks should not be executed immediately upon submission");
        // Calls a method
        scheduler.processTick();
        // Calls a method
        assertFalse(result.get(), "End of tick tasks should not be executed by processTick()");
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertTrue(result.get(), "scheduleEndOfTick(...) tasks should be executed after the next call to processTickEnd()");

        // Calls a method
        result.set(false);
        // Calls a method
        scheduler.scheduleEndOfTick(() -> result.set(true));
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertTrue(result.get(), "scheduleEndOfTick(...) tasks should always execute on the very next processTickEnd()");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void delayedEndOfTick() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Code statement
        scheduler.buildTask(() -> result.set(true)).delay(TaskSchedule.tick(1))
                // Calls a method
                .executionType(ExecutionType.TICK_END).schedule();

        // Calls a method
        scheduler.processTickEnd(); scheduler.processTickEnd();
        // Calls a method
        assertFalse(result.get(), "processTickEnd() should not increment the scheduler's internal tick counter");
        // Calls a method
        scheduler.processTick();
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertTrue(result.get(), "processTick() should increment the current tick counter processTickEnd() uses");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void repeatingEndOfTick() {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicInteger result = new AtomicInteger(0);
        // Calls a method
        Task task = scheduler.scheduleTask(result::getAndIncrement, TaskSchedule.immediate(), TaskSchedule.tick(1), ExecutionType.TICK_END);
        // Calls a method
        assertEquals(0, result.get(), "TICK_END tasks should not be executed immediately upon submission");
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertEquals(1, result.get(), "processTickEnd() should always execute TaskSchedule.immediate() TICK_END tasks");
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertEquals(1, result.get(), "task should not executed on processTickEnd() again until processTick() is called");
        // Calls a method
        scheduler.processTick();
        // Calls a method
        assertEquals(1, result.get(), "processTick() should never execute TICK_END tasks");
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertEquals(2, result.get(), "processTickEnd() should execute this task");

        // Calls a method
        task.cancel();
        // Calls a method
        scheduler.processTick();
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertEquals(2, result.get(), "this task should have been cancelled");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void durationEndOfTick() throws InterruptedException {
        // Calls a method
        Scheduler scheduler = Scheduler.newScheduler();
        // Calls a method
        AtomicBoolean result = new AtomicBoolean(false);
        // Code statement
        scheduler.buildTask(() -> result.set(true))
                // Code statement
                .delay(TaskSchedule.seconds(1))
                // Code statement
                .executionType(ExecutionType.TICK_END)
                // Calls a method
                .schedule();
        // Calls a method
        Thread.sleep(100);
        // Calls a method
        scheduler.process();
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertFalse(result.get(), "900ms remaining");
        // Calls a method
        Thread.sleep(1200);
        // Calls a method
        scheduler.process();
        // Calls a method
        assertFalse(result.get(), "process() should never execute TICK_END tasks");
        // Calls a method
        scheduler.processTickEnd();
        // Calls a method
        assertTrue(result.get(), "Tick end task must be executed after 1 second");
    // End of a block/expression
    }
// End of a block/expression
}
