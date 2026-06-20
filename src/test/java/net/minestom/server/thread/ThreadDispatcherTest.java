// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;
// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.stream.Collectors;
// Import d'une classe nécessaire
import java.util.stream.IntStream;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ThreadDispatcherTest {
    // Déclaration de type (classe/interface/enum/record)
    record World() {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static abstract class Element implements Tickable, AcquirableSource<Element> {
        // Appelle une méthode
        final Acquirable<Element> acquirable = Acquirable.unassigned(this);

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Acquirable<? extends Element> acquirable() {
            // Renvoie une valeur à l'appelant
            return acquirable;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basic() {
        // Déclaration de type (classe/interface/enum/record)
        final class Element implements Tickable {
            // Instruction de code
            int value;

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(long time) {
                // Instruction de code
                value++;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        World world = new World();
        // Appelle une méthode
        Element element = new Element();

        // Appelle une méthode
        ThreadDispatcher<World, Element> dispatcher = ThreadDispatcher.singleThread();
        // Appelle une méthode
        dispatcher.createPartition(world);
        // Appelle une méthode
        dispatcher.updateElement(element, world);
        // Appelle une méthode
        dispatcher.start();

        // Appelle une méthode
        assertEquals(0, element.value);
        // Appelle une méthode
        dispatcher.updateAndAwait(0);
        // Appelle une méthode
        assertEquals(1, element.value);

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void basicAcquirable() {
        // Appelle une méthode
        World world = new World();
        // Affecte une valeur
        Element element = new Element() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void tick(long time) {
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        ThreadDispatcher<World, Element> dispatcher = ThreadDispatcher.singleThread();
        // Appelle une méthode
        dispatcher.createPartition(world);
        // Appelle une méthode
        dispatcher.updateElement(element, world);
        // Appelle une méthode
        dispatcher.start();

        // Appelle une méthode
        assertNull(element.acquirable().assignedThread());
        // Appelle une méthode
        dispatcher.updateAndAwait(0);
        // Appelle une méthode
        assertNotNull(element.acquirable().assignedThread());

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void elementTick() {
        // Appelle une méthode
        final AtomicInteger counter = new AtomicInteger();
        // Appelle une méthode
        ThreadDispatcher<World, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Appelle une méthode
        dispatcher.start();
        // Appelle une méthode
        assertEquals(1, dispatcher.threads().size());
        // Appelle une méthode
        assertThrows(Exception.class, () -> dispatcher.threads().add(new TickThread(1)));

        // Appelle une méthode
        var partition = new World();
        // Appelle une méthode
        Tickable element = (time) -> counter.incrementAndGet();
        // Appelle une méthode
        dispatcher.createPartition(partition);
        // Appelle une méthode
        dispatcher.updateElement(element, partition);
        // Appelle une méthode
        assertEquals(0, counter.get());

        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Instruction de code
        dispatcher.updateElement(element, partition); // Should be ignored
        // Instruction de code
        dispatcher.createPartition(partition); // Ignored too
        // Appelle une méthode
        assertEquals(1, counter.get());

        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Appelle une méthode
        assertEquals(2, counter.get());

        // Appelle une méthode
        dispatcher.removeElement(element);
        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Appelle une méthode
        assertEquals(2, counter.get());

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void elementTickLoop() {
        // Appelle une méthode
        final AtomicInteger counter = new AtomicInteger();
        // Appelle une méthode
        ThreadDispatcher<World, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Appelle une méthode
        dispatcher.start();

        // Appelle une méthode
        var partition = new World();
        // Appelle une méthode
        Tickable element = (time) -> counter.incrementAndGet();
        // Appelle une méthode
        dispatcher.createPartition(partition);
        // Appelle une méthode
        dispatcher.updateElement(element, partition);
        // Appelle une méthode
        assertEquals(0, counter.get());

        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            dispatcher.updateAndAwait(System.nanoTime());
            // Appelle une méthode
            assertEquals(i + 1, counter.get());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void elementTickLoopAsync() {
        // Appelle une méthode
        final AtomicInteger counter = new AtomicInteger();
        // Appelle une méthode
        ThreadDispatcher<World, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Appelle une méthode
        dispatcher.start();

        // Appelle une méthode
        var partition = new World();
        // Appelle une méthode
        Tickable element = (time) -> counter.incrementAndGet();
        // Appelle une méthode
        dispatcher.createPartition(partition);
        // Appelle une méthode
        dispatcher.updateElement(element, partition);
        // Appelle une méthode
        assertEquals(0, counter.get());

        // Affecte une valeur
        final int count = 100;
        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(count);
        // Boucle : répète un bloc
        for (int i = 0; i < count; i++) {
            // Début d'une méthode/d'un bloc
            Thread.startVirtualThread(() -> {
                // Appelle une méthode
                dispatcher.updateAndAwait(System.nanoTime());
                // Appelle une méthode
                latch.countDown();
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
        // Gestion des exceptions
        try {
            // Appelle une méthode
            latch.await();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Appelle une méthode
            Thread.currentThread().interrupt();
            // Appelle une méthode
            fail("Latch was interrupted");
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(count, counter.get());

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void partitionTick() {
        // Partitions implementing Tickable should be ticked same as elements
        // Appelle une méthode
        final AtomicInteger counter1 = new AtomicInteger();
        // Appelle une méthode
        final AtomicInteger counter2 = new AtomicInteger();
        // Appelle une méthode
        ThreadDispatcher<Tickable, Tickable> dispatcher = ThreadDispatcher.singleThread();
        // Appelle une méthode
        dispatcher.start();
        // Appelle une méthode
        assertEquals(1, dispatcher.threads().size());

        // Appelle une méthode
        Tickable partition = (time) -> counter1.incrementAndGet();
        // Appelle une méthode
        Tickable element = (time) -> counter2.incrementAndGet();
        // Appelle une méthode
        dispatcher.createPartition(partition);
        // Appelle une méthode
        dispatcher.updateElement(element, partition);
        // Appelle une méthode
        assertEquals(0, counter1.get());
        // Appelle une méthode
        assertEquals(0, counter2.get());

        // Boucle : répète un bloc
        for (int i = 0; i < 100; i++) {
            // Appelle une méthode
            dispatcher.updateAndAwait(System.nanoTime());
            // Appelle une méthode
            assertEquals(i + 1, counter1.get());
            // Appelle une méthode
            assertEquals(i + 1, counter2.get());
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        dispatcher.deletePartition(partition);
        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Appelle une méthode
        assertEquals(100, counter1.get());
        // Appelle une méthode
        assertEquals(100, counter2.get());

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void uniqueThread() {
        // Ensure that partitions are properly dispatched across threads
        // Affecte une valeur
        final int threadCount = 10;
        // Appelle une méthode
        ThreadDispatcher<Tickable, Tickable> dispatcher = ThreadDispatcher.dispatcher(ThreadProvider.counter(), threadCount);
        // Appelle une méthode
        assertEquals(threadCount, dispatcher.threads().size());
        // Appelle une méthode
        dispatcher.start();

        // Appelle une méthode
        final AtomicInteger counter = new AtomicInteger();
        // Appelle une méthode
        Set<Thread> threads = new CopyOnWriteArraySet<>();
        // Affecte une valeur
        Set<Tickable> partitions = IntStream.range(0, threadCount)
                // Début d'une méthode/d'un bloc
                .mapToObj(value -> (Tickable) (time) -> {
                    // Appelle une méthode
                    final Thread thread = Thread.currentThread();
                    // Appelle une méthode
                    assertInstanceOf(TickThread.class, thread);
                    // Appelle une méthode
                    assertEquals(1, ((TickThread) thread).entries.size());
                    // Appelle une méthode
                    assertTrue(threads.add(thread));
                    // Appelle une méthode
                    counter.getAndIncrement();
                // Instruction de code
                })
                // Appelle une méthode
                .collect(Collectors.toUnmodifiableSet());
        // Appelle une méthode
        assertEquals(threadCount, partitions.size());

        // Appelle une méthode
        partitions.forEach(dispatcher::createPartition);
        // Appelle une méthode
        assertEquals(0, counter.get());

        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());
        // Appelle une méthode
        assertEquals(threadCount, counter.get());

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void threadUpdate() {
        // Ensure that partitions threads are properly updated every tick
        // when RefreshType.ALWAYS is used
        // Déclaration de type (classe/interface/enum/record)
        interface Updater extends Tickable {
            // Appelle une méthode
            int getValue();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final int threadCount = 10;
        // Affecte une valeur
        ThreadDispatcher<Updater, Tickable> dispatcher = ThreadDispatcher.dispatcher(new ThreadProvider<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int findThread(Updater partition) {
                // Renvoie une valeur à l'appelant
                return partition.getValue();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public RefreshType refreshType() {
                // Renvoie une valeur à l'appelant
                return RefreshType.ALWAYS;
            // Fin d'un bloc/d'une expression
            }
        // Instruction de code
        }, threadCount);
        // Appelle une méthode
        assertEquals(threadCount, dispatcher.threads().size());
        // Appelle une méthode
        dispatcher.start();

        // Appelle une méthode
        Map<Updater, Thread> threads = new ConcurrentHashMap<>();
        // Appelle une méthode
        Map<Updater, Thread> threads2 = new ConcurrentHashMap<>();
        // Affecte une valeur
        Set<Updater> partitions = IntStream.range(0, threadCount)
                // Début d'une méthode/d'un bloc
                .mapToObj(value -> new Updater() {
                    // Affecte une valeur
                    private int v = value;

                    // Annotation pour l'élément suivant
                    @Override
                    // Début d'une méthode/d'un bloc
                    public int getValue() {
                        // Renvoie une valeur à l'appelant
                        return v;
                    // Fin d'un bloc/d'une expression
                    }

                    // Annotation pour l'élément suivant
                    @Override
                    // Début d'une méthode/d'un bloc
                    public void tick(long time) {
                        // Appelle une méthode
                        final Thread currentThread = Thread.currentThread();
                        // Appelle une méthode
                        assertInstanceOf(TickThread.class, currentThread);
                        // Embranchement : vérifie une condition
                        if (threads.putIfAbsent(this, currentThread) == null) {
                            // Accès à l'objet courant/parent
                            this.v = value + 1;
                        // Branche alternative de la condition
                        } else {
                            // Appelle une méthode
                            assertEquals(value + 1, v);
                            // Appelle une méthode
                            threads2.putIfAbsent(this, currentThread);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Appelle une méthode
                }).collect(Collectors.toUnmodifiableSet());
        // Appelle une méthode
        assertEquals(threadCount, partitions.size());

        // Appelle une méthode
        partitions.forEach(dispatcher::createPartition);

        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());

        // Appelle une méthode
        dispatcher.refreshThreads();

        // Appelle une méthode
        dispatcher.updateAndAwait(System.nanoTime());

        // Appelle une méthode
        assertEquals(threads2.size(), threads.size());
        // Appelle une méthode
        assertNotEquals(threads, threads2, "Threads have not been updated at all");
        // Boucle : répète un bloc
        for (var entry : threads.entrySet()) {
            // Appelle une méthode
            final Thread thread1 = entry.getValue();
            // Appelle une méthode
            final Thread thread2 = threads2.get(entry.getKey());
            // Appelle une méthode
            assertNotEquals(thread1, thread2);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        dispatcher.shutdown();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
