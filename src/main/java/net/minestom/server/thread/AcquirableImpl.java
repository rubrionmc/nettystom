// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicLong;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
final class AcquirableImpl<T> implements Acquirable<T> {
    // Appelle une méthode
    private static final boolean ASSERTIONS_ENABLED = AcquirableImpl.class.desiredAssertionStatus();
    // Appelle une méthode
    static final AtomicLong WAIT_COUNTER_NANO = new AtomicLong();

    /**
     * Global lock used for synchronization.
     */
    // Appelle une méthode
    static final ReentrantLock GLOBAL_LOCK = new ReentrantLock();

    // Instruction de code
    private final T value;
    // Appelle une méthode
    private final Thread initThread = Thread.currentThread();
    // Instruction de code
    private volatile @Nullable TickThread assignedThread;

    // Début d'une méthode/d'un bloc
    public AcquirableImpl(T value) {
        // Accès à l'objet courant/parent
        this.value = value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquired<T> lock() {
        // Affecte une valeur
        final TickThread assignedThread = this.assignedThread;
        // Embranchement : vérifie une condition
        if (assignedThread == null) {
            // Appelle une méthode
            assertInitThread();
            // Renvoie une valeur à l'appelant
            return new AcquiredImpl<>(unwrap(), null);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        ReentrantLock lock = enter(assignedThread);
        // Appelle une méthode
        assert assignedThread.lock().isHeldByCurrentThread();
        // Renvoie une valeur à l'appelant
        return new AcquiredImpl<>(unwrap(), lock);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isLocal() {
        // Affecte une valeur
        final TickThread assignedThread = this.assignedThread;
        // Renvoie une valeur à l'appelant
        return Thread.currentThread() == Objects.requireNonNullElse(assignedThread, initThread);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isOwned() {
        // Affecte une valeur
        final TickThread assignedThread = this.assignedThread;
        // Embranchement : vérifie une condition
        if (assignedThread == null) return Thread.currentThread() == initThread;
        // Renvoie une valeur à l'appelant
        return AcquirableImpl.isOwnedImpl(assignedThread);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sync(Consumer<T> consumer) {
        // Affecte une valeur
        final TickThread assignedThread = this.assignedThread;
        // Embranchement : vérifie une condition
        if (assignedThread == null) {
            // Appelle une méthode
            assertInitThread();
            // Appelle une méthode
            consumer.accept(unwrap());
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        ReentrantLock lock = enter(assignedThread);
        // Gestion des exceptions
        try {
            // Appelle une méthode
            assert assignedThread.lock().isHeldByCurrentThread();
            // Appelle une méthode
            consumer.accept(unwrap());
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            leave(lock);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean trySync(Consumer<T> consumer) {
        // Embranchement : vérifie une condition
        if (isOwned()) {
            // Appelle une méthode
            consumer.accept(unwrap());
            // Renvoie une valeur à l'appelant
            return true;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        TickThread assignedThread = this.assignedThread;
        // Embranchement : vérifie une condition
        if (assignedThread != null) {
            // Appelle une méthode
            ReentrantLock lock = assignedThread.lock();
            // Embranchement : vérifie une condition
            if (lock.tryLock()) {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    consumer.accept(unwrap());
                    // Renvoie une valeur à l'appelant
                    return true;
                // Début d'une méthode/d'un bloc
                } finally {
                    // Appelle une méthode
                    lock.unlock();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T unwrap() {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @UnknownNullability TickThread assignedThread() {
        // Renvoie une valeur à l'appelant
        return assignedThread;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assign(TickThread thread) {
        // Accès à l'objet courant/parent
        this.assignedThread = thread;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void assertOwnership() {
        // Embranchement : vérifie une condition
        if (!ASSERTIONS_ENABLED && !ServerFlag.ACQUIRABLE_STRICT) return;
        // Embranchement : vérifie une condition
        if (isOwned()) return;
        // Affecte une valeur
        TickThread assignedThread = this.assignedThread;
        // Affecte une valeur
        Thread initThread = this.initThread;
        // Embranchement : vérifie une condition
        if (assignedThread == null && Thread.currentThread() == initThread) return;
        // Lève une exception
        throw new AcquirableOwnershipException(initThread, assignedThread, unwrap().toString());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void assertInitThread() {
        // Embranchement : vérifie une condition
        if (Thread.currentThread() != initThread)
            // Lève une exception
            throw new IllegalStateException("Cannot lock an uninitialized Acquirable from a different thread");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean isOwnedImpl(TickThread elementThread) {
        // Embranchement : vérifie une condition
        if (Thread.currentThread() == elementThread) return true;
        // Renvoie une valeur à l'appelant
        return elementThread.lock().isHeldByCurrentThread();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable ReentrantLock enter(TickThread elementThread) {
        // Embranchement : vérifie une condition
        if (isOwnedImpl(elementThread)) return null; // Nothing to lock, already owned by the current thread.
        // Appelle une méthode
        final long time = System.nanoTime();
        // Enter the target thread
        // Embranchement : vérifie une condition
        if (Thread.currentThread() instanceof TickThread tickThread && tickThread.lock().isHeldByCurrentThread()) {
            // Boucle : répète un bloc
            while (!GLOBAL_LOCK.tryLock()) {
                // Appelle une méthode
                tickThread.lock().unlock();
                // Appelle une méthode
                tickThread.lock().lock();
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            GLOBAL_LOCK.lock();
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final ReentrantLock targetLock = elementThread.lock();
        // Appelle une méthode
        targetLock.lock();
        // Appelle une méthode
        WAIT_COUNTER_NANO.addAndGet(System.nanoTime() - time);
        // Renvoie une valeur à l'appelant
        return targetLock;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void leave(@Nullable ReentrantLock lock) {
        // Embranchement : vérifie une condition
        if (lock != null) {
            // Appelle une méthode
            lock.unlock();
            // Appelle une méthode
            GLOBAL_LOCK.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
