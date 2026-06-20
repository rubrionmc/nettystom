// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.lang.ref.Cleaner;
// Import d'une classe nécessaire
import java.lang.ref.SoftReference;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public final class ObjectPool<T> {
    // Affecte une valeur
    private static final int QUEUE_SIZE = 32_768;
    // Appelle une méthode
    private static final Cleaner CLEANER = Cleaner.create();

    // Instruction de code
    private final MessagePassingQueue<SoftReference<T>> pool;
    // Instruction de code
    private final Supplier<T> supplier;
    // Instruction de code
    private final UnaryOperator<T> sanitizer;

    // Début d'une méthode/d'un bloc
    public static <T> ObjectPool<T> pool(Supplier<T> supplier, UnaryOperator<T> sanitizer) {
        // Renvoie une valeur à l'appelant
        return new ObjectPool<>(supplier, sanitizer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <T> ObjectPool<T> pool(Supplier<T> supplier) {
        // Renvoie une valeur à l'appelant
        return new ObjectPool<>(supplier, UnaryOperator.identity());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ObjectPool(Supplier<T> supplier, UnaryOperator<T> sanitizer) {
        // Accès à l'objet courant/parent
        this.pool = ConcurrentMessageQueues.mpmcSpecialUnboundedArrayQueue(QUEUE_SIZE);
        // Accès à l'objet courant/parent
        this.supplier = supplier;
        // Accès à l'objet courant/parent
        this.sanitizer = sanitizer;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public T get() {
        // Instruction de code
        T result;
        // Instruction de code
        SoftReference<T> ref;
        // Boucle : répète un bloc
        while ((ref = pool.poll()) != null) {
            // Embranchement : vérifie une condition
            if ((result = ref.get()) != null) return result;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return supplier.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public T getAndRegister(Object ref) {
        // Appelle une méthode
        T result = get();
        // Appelle une méthode
        register(ref, result);
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void add(T object) {
        // Appelle une méthode
        object = sanitizer.apply(object);
        // Accès à l'objet courant/parent
        this.pool.offer(new SoftReference<>(object));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void clear() {
        // Accès à l'objet courant/parent
        this.pool.clear();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int count() {
        // Renvoie une valeur à l'appelant
        return pool.size();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void register(Object ref, AtomicReference<T> objectRef) {
        // Appelle une méthode
        CLEANER.register(ref, new BufferRefCleaner<>(this, objectRef));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void register(Object ref, T object) {
        // Appelle une méthode
        CLEANER.register(ref, new BufferCleaner<>(this, object));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void register(Object ref, Collection<T> objects) {
        // Appelle une méthode
        CLEANER.register(ref, new BuffersCleaner<>(this, objects));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Holder hold() {
        // Renvoie une valeur à l'appelant
        return new Holder(get());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <R> R use(Function<T, R> function) {
        // Appelle une méthode
        T object = get();
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return function.apply(object);
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            add(object);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record BufferRefCleaner<T>(ObjectPool<T> pool, AtomicReference<T> objectRef) implements Runnable {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void run() {
            // Accès à l'objet courant/parent
            this.pool.add(objectRef.get());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record BufferCleaner<T>(ObjectPool<T> pool, T object) implements Runnable {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void run() {
            // Accès à l'objet courant/parent
            this.pool.add(object);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private record BuffersCleaner<T>(ObjectPool<T> pool, Collection<T> objects) implements Runnable {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void run() {
            // Boucle : répète un bloc
            for (T buffer : objects) {
                // Accès à l'objet courant/parent
                this.pool.add(buffer);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public final class Holder implements AutoCloseable {
        // Instruction de code
        private final T object;
        // Appelle une méthode
        private final AtomicBoolean closed = new AtomicBoolean(false);

        // Début d'une méthode/d'un bloc
        Holder(T object) {
            // Accès à l'objet courant/parent
            this.object = object;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public T get() {
            // Embranchement : vérifie une condition
            if (closed.get()) throw new IllegalStateException("Holder is closed");
            // Renvoie une valeur à l'appelant
            return object;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void close() {
            // Embranchement : vérifie une condition
            if (closed.compareAndSet(false, true)) {
                // Appelle une méthode
                add(object);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
