// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import org.openjdk.jmh.annotations.*;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.function.Consumer;

// Annotation for the following element
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
// Annotation for the following element
@Fork(1)
// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// Annotation for the following element
@State(Scope.Benchmark)
// Type declaration (class/interface/enum/record)
public class AcquirableSyncBenchmark {
    // Assigns a value
    private static final int THREAD_COUNT = 10;

    // Code statement
    TickThread mainThread;
    // Code statement
    Acquirable<Test> acquirable;

    // Code statement
    List<Thread> tickThreads;
    // Code statement
    List<Thread> threads;

    // Code statement
    Consumer<Acquirable<Test>> consumer;

    // Type declaration (class/interface/enum/record)
    static final class Test {
        // Code statement
        int value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Setup(Level.Invocation)
    // Start of a method/block
    public void setup() {
        // Access to the current/parent object
        this.mainThread = new TickThread(0) {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void run() {
                // Access to the current/parent object
                this.lock().lock();
                // Exception handling
                try {
                    // Calls a method
                    consumer.accept(acquirable);
                // Start of a method/block
                } finally {
                    // Access to the current/parent object
                    this.lock().unlock();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Access to the current/parent object
        this.acquirable = Acquirable.unassigned(new Test());
        // Calls a method
        ((AcquirableImpl<Test>) acquirable).assign(mainThread);

        // Start of a block
        {
            // Access to the current/parent object
            this.tickThreads = new ArrayList<>(THREAD_COUNT);
            // Loop: repeats a block
            for (int i = 0; i < THREAD_COUNT; i++) {
                // Assigns a value
                TickThread thread = new TickThread(i + 1) {
                    // Annotation for the following element
                    @Override
                    // Start of a method/block
                    public void run() {
                        // Access to the current/parent object
                        this.lock().lock();
                        // Exception handling
                        try {
                            // Calls a method
                            consumer.accept(acquirable);
                        // Start of a method/block
                        } finally {
                            // Access to the current/parent object
                            this.lock().unlock();
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                };
                // Calls a method
                tickThreads.add(thread);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a block
        {
            // Access to the current/parent object
            this.threads = new ArrayList<>(THREAD_COUNT);
            // Loop: repeats a block
            for (int i = 0; i < THREAD_COUNT; i++) {
                // Calls a method
                Thread thread = new Thread(() -> consumer.accept(acquirable));
                // Calls a method
                threads.add(thread);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void localUnsafe() {
        // Start of a method/block
        launchLocal((acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.unwrap().value++;
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void localSync() {
        // Start of a method/block
        launchLocal((acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void localSynchronizedKeyword() {
        // Calls a method
        Object object = new Object();
        // Start of a method/block
        launchLocal((acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) {
                // Start of a method/block
                synchronized (object) {
                    // Calls a method
                    acquirable.unwrap().value++;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void foreignSync() {
        // Single thread (not main) acquiring the element
        // Start of a method/block
        launch(tickThreads.subList(0, 1), (acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void unsafe() {
        // Start of a method/block
        launch(threads, (acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.unwrap().value++;
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void synchronizedKeyword() {
        // Calls a method
        Object object = new Object();
        // Start of a method/block
        launch(threads, (acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) {
                // Start of a method/block
                synchronized (object) {
                    // Calls a method
                    acquirable.unwrap().value++;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void multiAcquireThread() {
        // Start of a method/block
        launch(threads, (acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void multiAcquireTickThread() {
        // Start of a method/block
        launch(tickThreads, (acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.sync(test -> test.value++);
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void multiDoubleAcquireTickThread() {
        // Start of a method/block
        launch(tickThreads, (acquirable) -> {
            // Loop: repeats a block
            for (int i = 0; i < 10_000; i++) acquirable.sync(t -> acquirable.sync(test -> test.value++));
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    private void launch(List<Thread> threads, Consumer<Acquirable<Test>> consumer) {
        // Calls a method
        final int factor = THREAD_COUNT / threads.size();
        // Access to the current/parent object
        this.consumer = acquirable -> {
            // Loop: repeats a block
            for (int i = 0; i < factor; i++) {
                // Calls a method
                consumer.accept(acquirable);
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Start all
        // Loop: repeats a block
        for (Thread thread : threads) thread.start();
        // Wait for all to finish
        // Loop: repeats a block
        for (Thread thread : threads) {
            // Exception handling
            try {
                // Calls a method
                thread.join();
            // Start of a method/block
            } catch (InterruptedException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void launchLocal(Consumer<Acquirable<Test>> consumer) {
        // Multiply by thread count to simulate the same amount of operations
        // Access to the current/parent object
        this.consumer = acquirable -> {
            // Loop: repeats a block
            for (int i = 0; i < THREAD_COUNT; i++) {
                // Calls a method
                consumer.accept(acquirable);
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Access to the current/parent object
        this.mainThread.start();
        // Exception handling
        try {
            // Access to the current/parent object
            this.mainThread.join();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
