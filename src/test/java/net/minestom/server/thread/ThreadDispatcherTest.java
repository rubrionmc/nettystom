// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;
// Import of a required class
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.stream.Collectors;
// Import of a required class
import java.util.stream.IntStream;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ThreadDispatcherTest {
    // Type declaration (class/interface/enum/record)
    record World() {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static abstract class Element implements Tickable, AcquirableSource<Element> {
        // Calls a method
        final Acquirable<Element> acquirable = Acquirable.unassigned(this);

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Acquirable<? extends Element> acquirable() {
            // Returns a value to the caller
            return acquirable;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basic() {
        // Type declaration (class/interface/enum/record)
        final class Element implements Tickable {
            // Code statement
            int value;

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void tick(long time) {
                // Code statement
                value++;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        World world = new World();
        // Calls a method
        Element element = new Element();

        // Calls a method
        ThreadDispatcher<World, Element> dispatcher = ThreadDispatcher.singleThread();
        // Calls a method
        dispatcher.createPartition(world);
        // Calls a method
        dispatcher.updateElement(element, world);
        // Calls a method
        dispatcher.start();

        // Calls a method
        assertEquals(0, element.value);
        // Calls a method
        dispatcher.updateAndAwait(0);
        // Calls a method
        assertEquals(1, element.value);

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void basicAcquirable() {
        // Calls a method
        World world = new World();
        // Assigns a value
        Element element = new Element() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void tick(long time) {
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Calls a method
        ThreadDispatcher<World, Element> dispatcher = ThreadDispatcher.singleThread();
        // Calls a method
        dispatcher.createPartition(world);
        // Calls a method
        dispatcher.updateElement(element, world);
        // Calls a method
        dispatcher.start();

        // Calls a method
        assertNull(element.acquirable().assignedThread());
        // Calls a method
        dispatcher.updateAndAwait(0);
        // Calls a method
        assertNotNull(element.acquirable().assignedThread());

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void elementTick() {
        // Calls a method
        final AtomicInteger counter = new AtomicInteger();
        // Calls a method
        ThreadDispatcher<World, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Calls a method
        dispatcher.start();
        // Calls a method
        assertEquals(1, dispatcher.threads().size());
        // Calls a method
        assertThrows(Exception.class, () -> dispatcher.threads().add(new TickThread(1)));

        // Calls a method
        var partition = new World();
        // Calls a method
        Tickable element = (time) -> counter.incrementAndGet();
        // Calls a method
        dispatcher.createPartition(partition);
        // Calls a method
        dispatcher.updateElement(element, partition);
        // Calls a method
        assertEquals(0, counter.get());

        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Code statement
        dispatcher.updateElement(element, partition); // Should be ignored
        // Code statement
        dispatcher.createPartition(partition); // Ignored too
        // Calls a method
        assertEquals(1, counter.get());

        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Calls a method
        assertEquals(2, counter.get());

        // Calls a method
        dispatcher.removeElement(element);
        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Calls a method
        assertEquals(2, counter.get());

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void elementTickLoop() {
        // Calls a method
        final AtomicInteger counter = new AtomicInteger();
        // Calls a method
        ThreadDispatcher<World, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Calls a method
        dispatcher.start();

        // Calls a method
        var partition = new World();
        // Calls a method
        Tickable element = (time) -> counter.incrementAndGet();
        // Calls a method
        dispatcher.createPartition(partition);
        // Calls a method
        dispatcher.updateElement(element, partition);
        // Calls a method
        assertEquals(0, counter.get());

        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            dispatcher.updateAndAwait(System.nanoTime());
            // Calls a method
            assertEquals(i + 1, counter.get());
        // End of a block/expression
        }

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void elementTickLoopAsync() {
        // Calls a method
        final AtomicInteger counter = new AtomicInteger();
        // Calls a method
        ThreadDispatcher<World, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Calls a method
        dispatcher.start();

        // Calls a method
        var partition = new World();
        // Calls a method
        Tickable element = (time) -> counter.incrementAndGet();
        // Calls a method
        dispatcher.createPartition(partition);
        // Calls a method
        dispatcher.updateElement(element, partition);
        // Calls a method
        assertEquals(0, counter.get());

        // Assigns a value
        final int count = 100;
        // Calls a method
        CountDownLatch latch = new CountDownLatch(count);
        // Loop: repeats a block
        for (int i = 0; i < count; i++) {
            // Start of a method/block
            Thread.startVirtualThread(() -> {
                // Calls a method
                dispatcher.updateAndAwait(System.nanoTime());
                // Calls a method
                latch.countDown();
            // End of a block/expression
            });
        // End of a block/expression
        }
        // Exception handling
        try {
            // Calls a method
            latch.await();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Calls a method
            Thread.currentThread().interrupt();
            // Calls a method
            fail("Latch was interrupted");
        // End of a block/expression
        }
        // Calls a method
        assertEquals(count, counter.get());

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void partitionTick() {
        // Partitions implementing Tickable should be ticked same as elements
        // Calls a method
        final AtomicInteger counter1 = new AtomicInteger();
        // Calls a method
        final AtomicInteger counter2 = new AtomicInteger();
        // Calls a method
        ThreadDispatcher<Tickable, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Calls a method
        dispatcher.start();
        // Calls a method
        assertEquals(1, dispatcher.threads().size());

        // Calls a method
        Tickable partition = (time) -> counter1.incrementAndGet();
        // Calls a method
        Tickable element = (time) -> counter2.incrementAndGet();
        // Calls a method
        dispatcher.createPartition(partition);
        // Calls a method
        dispatcher.updateElement(element, partition);
        // Calls a method
        assertEquals(0, counter1.get());
        // Calls a method
        assertEquals(0, counter2.get());

        // Loop: repeats a block
        for (int i = 0; i < 100; i++) {
            // Calls a method
            dispatcher.updateAndAwait(System.nanoTime());
            // Calls a method
            assertEquals(i + 1, counter1.get());
            // Calls a method
            assertEquals(i + 1, counter2.get());
        // End of a block/expression
        }

        // Calls a method
        dispatcher.deletePartition(partition);
        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Calls a method
        assertEquals(100, counter1.get());
        // Calls a method
        assertEquals(100, counter2.get());

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void uniqueThread() {
        // Ensure that partitions are properly dispatched across threads
        // Assigns a value
        final int threadCount = 10;
        // Calls a method
        ThreadDispatcher<Tickable, Tickable> dispatcher = ThreadDispatcher.dispatcher(ThreadProvider.counter(), threadCount);
        // Calls a method
        assertEquals(threadCount, dispatcher.threads().size());
        // Calls a method
        dispatcher.start();

        // Calls a method
        final AtomicInteger counter = new AtomicInteger();
        // Calls a method
        Set<Thread> threads = new CopyOnWriteArraySet<>();
        // Assigns a value
        Set<Tickable> partitions = IntStream.range(0, threadCount)
                // Start of a method/block
                .mapToObj(value -> (Tickable) (time) -> {
                    // Calls a method
                    final Thread thread = Thread.currentThread();
                    // Calls a method
                    assertInstanceOf(TickThread.class, thread);
                    // Calls a method
                    assertEquals(1, ((TickThread) thread).entries.size());
                    // Calls a method
                    assertTrue(threads.add(thread));
                    // Calls a method
                    counter.getAndIncrement();
                // Code statement
                })
                // Calls a method
                .collect(Collectors.toUnmodifiableSet());
        // Calls a method
        assertEquals(threadCount, partitions.size());

        // Calls a method
        partitions.forEach(dispatcher::createPartition);
        // Calls a method
        assertEquals(0, counter.get());

        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());
        // Calls a method
        assertEquals(threadCount, counter.get());

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void threadUpdate() {
        // Ensure that partitions threads are properly updated every tick
        // when RefreshType.ALWAYS is used
        // Type declaration (class/interface/enum/record)
        interface Updater extends Tickable {
            // Calls a method
            int getValue();
        // End of a block/expression
        }

        // Assigns a value
        final int threadCount = 10;
        // Assigns a value
        ThreadDispatcher<Updater, Tickable> dispatcher = ThreadDispatcher.dispatcher(new ThreadProvider<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public int findThread(Updater partition) {
                // Returns a value to the caller
                return partition.getValue();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public RefreshType refreshType() {
                // Returns a value to the caller
                return RefreshType.ALWAYS;
            // End of a block/expression
            }
        // Code statement
        }, threadCount);
        // Calls a method
        assertEquals(threadCount, dispatcher.threads().size());
        // Calls a method
        dispatcher.start();

        // Calls a method
        Map<Updater, Thread> threads = new ConcurrentHashMap<>();
        // Calls a method
        Map<Updater, Thread> threads2 = new ConcurrentHashMap<>();
        // Assigns a value
        Set<Updater> partitions = IntStream.range(0, threadCount)
                // Start of a method/block
                .mapToObj(value -> new Updater() {
                    // Assigns a value
                    private int v = value;

                    // Annotation for the following element
                    @Override
                    // Start of a method/block
                    public int getValue() {
                        // Returns a value to the caller
                        return v;
                    // End of a block/expression
                    }

                    // Annotation for the following element
                    @Override
                    // Start of a method/block
                    public void tick(long time) {
                        // Calls a method
                        final Thread currentThread = Thread.currentThread();
                        // Calls a method
                        assertInstanceOf(TickThread.class, currentThread);
                        // Branch: checks a condition
                        if (threads.putIfAbsent(this, currentThread) == null) {
                            // Access to the current/parent object
                            this.v = value + 1;
                        // Alternative branch of the condition
                        } else {
                            // Calls a method
                            assertEquals(value + 1, v);
                            // Calls a method
                            threads2.putIfAbsent(this, currentThread);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // Calls a method
                }).collect(Collectors.toUnmodifiableSet());
        // Calls a method
        assertEquals(threadCount, partitions.size());

        // Calls a method
        partitions.forEach(dispatcher::createPartition);

        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());

        // Calls a method
        dispatcher.refreshThreads();

        // Calls a method
        dispatcher.updateAndAwait(System.nanoTime());

        // Calls a method
        assertEquals(threads2.size(), threads.size());
        // Calls a method
        assertNotEquals(threads, threads2, "Threads have not been updated at all");
        // Loop: repeats a block
        for (var entry : threads.entrySet()) {
            // Calls a method
            final Thread thread1 = entry.getValue();
            // Calls a method
            final Thread thread2 = threads2.get(entry.getKey());
            // Calls a method
            assertNotEquals(thread1, thread2);
        // End of a block/expression
        }

        // Calls a method
        dispatcher.shutdown();
    // End of a block/expression
    }
// End of a block/expression
}
