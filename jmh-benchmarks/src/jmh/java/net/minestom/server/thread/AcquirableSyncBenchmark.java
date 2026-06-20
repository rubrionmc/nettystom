// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Annotation pour l'élément suivant
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@Fork(1)
// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Déclaration de type (classe/interface/enum/record)
public class AcquirableSyncBenchmark {
    // Affecte une valeur
    private static final int THREAD_COUNT = 10;

    // Instruction de code
    TickThread mainThread;
    // Instruction de code
    Acquirable<Test> acquirable;

    // Instruction de code
    List<Thread> tickThreads;
    // Instruction de code
    List<Thread> threads;

    // Instruction de code
    Consumer<Acquirable<Test>> consumer;

    // Déclaration de type (classe/interface/enum/record)
    static final class Test {
        // Instruction de code
        int value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Setup(Level.Invocation)
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Accès à l'objet courant/parent
        this.mainThread = new TickThread(0) {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void run() {
                // Accès à l'objet courant/parent
                this.lock().lock();
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    consumer.accept(acquirable);
                // Début d'une méthode/d'un bloc
                } finally {
                    // Accès à l'objet courant/parent
                    this.lock().unlock();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Accès à l'objet courant/parent
        this.acquirable = Acquirable.unassigned(new Test());
        // Appelle une méthode
        ((AcquirableImpl<Test>) acquirable).assign(mainThread);

        // Début d'un bloc
        {
            // Accès à l'objet courant/parent
            this.tickThreads = new ArrayList<>(THREAD_COUNT);
            // Boucle : répète un bloc
            for (int i = 0; i < THREAD_COUNT; i++) {
                // Affecte une valeur
                TickThread thread = new TickThread(i + 1) {
                    // Annotation pour l'élément suivant
                    @Override
                    // Début d'une méthode/d'un bloc
                    public void run() {
                        // Accès à l'objet courant/parent
                        this.lock().lock();
                        // Gestion des exceptions
                        try {
                            // Appelle une méthode
                            consumer.accept(acquirable);
                        // Début d'une méthode/d'un bloc
                        } finally {
                            // Accès à l'objet courant/parent
                            this.lock().unlock();
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                };
                // Appelle une méthode
                tickThreads.add(thread);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'un bloc
        {
            // Accès à l'objet courant/parent
            this.threads = new ArrayList<>(THREAD_COUNT);
            // Boucle : répète un bloc
            for (int i = 0; i < THREAD_COUNT; i++) {
                // Appelle une méthode
                Thread thread = new Thread(() -> consumer.accept(acquirable));
                // Appelle une méthode
                threads.add(thread);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void localUnsafe() {
        // Début d'une méthode/d'un bloc
        launchLocal((acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.unwrap().value++;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void localSync() {
        // Début d'une méthode/d'un bloc
        launchLocal((acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void localSynchronizedKeyword() {
        // Appelle une méthode
        Object object = new Object();
        // Début d'une méthode/d'un bloc
        launchLocal((acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) {
                // Début d'une méthode/d'un bloc
                synchronized (object) {
                    // Appelle une méthode
                    acquirable.unwrap().value++;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void foreignSync() {
        // Single thread (not main) acquiring the element
        // Début d'une méthode/d'un bloc
        launch(tickThreads.subList(0, 1), (acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void unsafe() {
        // Début d'une méthode/d'un bloc
        launch(threads, (acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.unwrap().value++;
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void synchronizedKeyword() {
        // Appelle une méthode
        Object object = new Object();
        // Début d'une méthode/d'un bloc
        launch(threads, (acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) {
                // Début d'une méthode/d'un bloc
                synchronized (object) {
                    // Appelle une méthode
                    acquirable.unwrap().value++;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void multiAcquireThread() {
        // Début d'une méthode/d'un bloc
        launch(threads, (acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void multiAcquireTickThread() {
        // Début d'une méthode/d'un bloc
        launch(tickThreads, (acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void multiDoubleAcquireTickThread() {
        // Début d'une méthode/d'un bloc
        launch(tickThreads, (acquirable) -> {
            // Boucle : répète un bloc
            for (int i = 0; i < 10_000; i++) acquirable.sync(t -> acquirable.sync(test -> test.value++));
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void launch(List<Thread> threads, Consumer<Acquirable<Test>> consumer) {
        // Appelle une méthode
        final int factor = THREAD_COUNT / threads.size();
        // Accès à l'objet courant/parent
        this.consumer = acquirable -> {
            // Boucle : répète un bloc
            for (int i = 0; i < factor; i++) {
                // Appelle une méthode
                consumer.accept(acquirable);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Start all
        // Boucle : répète un bloc
        for (Thread thread : threads) thread.start();
        // Wait for all to finish
        // Boucle : répète un bloc
        for (Thread thread : threads) {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                thread.join();
            // Début d'une méthode/d'un bloc
            } catch (InterruptedException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void launchLocal(Consumer<Acquirable<Test>> consumer) {
        // Multiply by thread count to simulate the same amount of operations
        // Accès à l'objet courant/parent
        this.consumer = acquirable -> {
            // Boucle : répète un bloc
            for (int i = 0; i < THREAD_COUNT; i++) {
                // Appelle une méthode
                consumer.accept(acquirable);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Accès à l'objet courant/parent
        this.mainThread.start();
        // Gestion des exceptions
        try {
            // Accès à l'objet courant/parent
            this.mainThread.join();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
