// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.concurrent.locks.LockSupport;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread responsible for ticking {@link Chunk chunks} and {@link Entity entities}.
 * <p>
 * Created in {@link ThreadDispatcher}, and awaken every tick with a task to execute.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public class TickThread extends MinestomThread {
    // Appelle une méthode
    private final ReentrantLock lock = new ReentrantLock();
    // Instruction de code
    private volatile boolean stop;

    // Appelle une méthode
    private final AtomicReference<CountDownLatch> latchRef = new AtomicReference<>();
    // Instruction de code
    private volatile long tickTimeNanos;

    // Affecte une valeur
    private long tickNum = 0;
    // Appelle une méthode
    final List<ThreadDispatcherImpl.Partition> entries = new ArrayList<>();

    // Début d'une méthode/d'un bloc
    public TickThread(int number) {
        // Accès à l'objet courant/parent
        super(MinecraftServer.THREAD_NAME_TICK + "-" + number);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public TickThread(String name) {
        // Accès à l'objet courant/parent
        super(name);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void run() {
        // Instruction de code
        LockSupport.park(this); // Wait for first tick
        // Boucle : répète un bloc
        while (!stop) {
            // Appelle une méthode
            final CountDownLatch latch = this.latchRef.get();
            // Embranchement : vérifie une condition
            if (latch == null) {
                // Should not happen, but just in case
                // Appelle une méthode
                LockSupport.park(this);
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            final ReentrantLock lock = this.lock;
            // Appelle une méthode
            lock.lock();
            // Gestion des exceptions
            try {
                // Appelle une méthode
                tick();
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Début d'une méthode/d'un bloc
            } finally {
                // Appelle une méthode
                lock.unlock();
                // #acquire() callbacks
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.latchRef.set(null);
            // Appelle une méthode
            latch.countDown();
            // Appelle une méthode
            LockSupport.park(this);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void tick() {
        // Affecte une valeur
        final ReentrantLock lock = this.lock;
        // Appelle une méthode
        final long tickTime = TimeUnit.NANOSECONDS.toMillis(this.tickTimeNanos);
        // Boucle : répète un bloc
        for (ThreadDispatcherImpl.Partition entry : entries) {
            // Appelle une méthode
            assert entry.thread() == this;
            // Appelle une méthode
            final List<Tickable> elements = entry.elements();
            // Embranchement : vérifie une condition
            if (elements.isEmpty()) continue;
            // Boucle : répète un bloc
            for (Tickable element : elements) {
                // Embranchement : vérifie une condition
                if (lock.hasQueuedThreads()) {
                    // Appelle une méthode
                    lock.unlock();
                    // #acquire() callbacks
                    // Appelle une méthode
                    lock.lock();
                // Fin d'un bloc/d'une expression
                }
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    assert assertElement(element);
                    // Appelle une méthode
                    element.tick(tickTime);
                // Début d'une méthode/d'un bloc
                } catch (Throwable e) {
                    // Appelle une méthode
                    MinecraftServer.getExceptionManager().handleException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean assertElement(Tickable element) {
        // Renvoie une valeur à l'appelant
        return !(element instanceof AcquirableSource<?> source)
                // Instruction de code
                || source.acquirable().assignedThread() == this &&
                // Appelle une méthode
                source.acquirable().assignedThread().lock().isHeldByCurrentThread();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void startTick(CountDownLatch latch, long tickTimeNanos) {
        // Affecte une valeur
        CountDownLatch update = latchRef
                // Appelle une méthode
                .updateAndGet(prevLatch -> prevLatch == null || prevLatch.getCount() == 0 ? latch : prevLatch);
        // Embranchement : vérifie une condition
        if (update != latch) {
            // Tick already in progress, wait for it to complete then start our own tick
            // Gestion des exceptions
            try {
                // Appelle une méthode
                update.await();
            // Début d'une méthode/d'un bloc
            } catch (InterruptedException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            startTick(latch, tickTimeNanos);
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (stop || entries.isEmpty()) {
            // Nothing to tick
            // Appelle une méthode
            latch.countDown();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.tickTimeNanos = tickTimeNanos;
        // Accès à l'objet courant/parent
        this.tickNum++;
        // Appelle une méthode
        LockSupport.unpark(this);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the lock used to ensure the safety of entity acquisition.
     *
     * @return the thread lock
     */
    // Début d'une méthode/d'un bloc
    public ReentrantLock lock() {
        // Renvoie une valeur à l'appelant
        return lock;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public long getTick() {
        // Renvoie une valeur à l'appelant
        return tickNum;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void shutdown() {
        // Accès à l'objet courant/parent
        this.stop = true;
        // Appelle une méthode
        LockSupport.unpark(this);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
