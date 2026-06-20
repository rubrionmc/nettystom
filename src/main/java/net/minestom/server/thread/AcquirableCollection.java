// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.locks.ReentrantLock;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public class AcquirableCollection<E> implements Collection<Acquirable<E>> {
    // Instruction de code
    private final Collection<Acquirable<E>> acquirableCollection;

    // Début d'une méthode/d'un bloc
    public AcquirableCollection(Collection<Acquirable<E>> acquirableCollection) {
        // Accès à l'objet courant/parent
        this.acquirableCollection = acquirableCollection;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void acquireSync(Consumer<E> consumer) {
        // Appelle une méthode
        final Map<TickThread, List<E>> threadEntitiesMap = retrieveOptionalThreadMap(acquirableCollection, consumer);
        // Acquire all the threads one by one
        // Boucle : répète un bloc
        for (Map.Entry<TickThread, List<E>> entry : threadEntitiesMap.entrySet()) {
            // Appelle une méthode
            final TickThread tickThread = entry.getKey();
            // Appelle une méthode
            ReentrantLock lock = AcquirableImpl.enter(tickThread);
            // Gestion des exceptions
            try {
                // Appelle une méthode
                final List<E> values = entry.getValue();
                // Boucle : répète un bloc
                for (E value : values) {
                    // Appelle une méthode
                    consumer.accept(value);
                // Fin d'un bloc/d'une expression
                }
            // Début d'une méthode/d'un bloc
            } finally {
                // Appelle une méthode
                AcquirableImpl.leave(lock);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Stream<E> unwrap() {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.stream().map(Acquirable::unwrap);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int size() {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.size();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isEmpty() {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean contains(Object o) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.contains(o);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterator<Acquirable<E>> iterator() {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.iterator();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object[] toArray() {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.toArray();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> T[] toArray(T[] a) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.toArray(a);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean add(Acquirable<E> eAcquirable) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.add(eAcquirable);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean remove(Object o) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.remove(o);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean containsAll(Collection<?> c) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.containsAll(c);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addAll(Collection<? extends Acquirable<E>> c) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.addAll(c);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeAll(Collection<?> c) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.removeAll(c);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean retainAll(Collection<?> c) {
        // Renvoie une valeur à l'appelant
        return acquirableCollection.retainAll(c);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clear() {
        // Accès à l'objet courant/parent
        this.acquirableCollection.clear();
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param collection the acquirable collection
     * @param consumer   the consumer to execute when an element is already in the current thread
     * @return a new Thread to acquirable elements map
     */
    // Instruction de code
    protected static <T> Map<TickThread, List<T>> retrieveOptionalThreadMap(Collection<Acquirable<T>> collection,
                                                                            // Début d'une méthode/d'un bloc
                                                                            Consumer<T> consumer) {
        // Separate a collection of acquirable elements into a map of thread->elements
        // Useful to reduce the number of acquisition
        // Affecte une valeur
        Map<TickThread, List<T>> threadCacheMap = new HashMap<>();
        // Boucle : répète un bloc
        for (var element : collection) {
            // Appelle une méthode
            final T value = element.unwrap();
            // Appelle une méthode
            final TickThread elementThread = element.assignedThread();
            // Embranchement : vérifie une condition
            if (Thread.currentThread() == elementThread) {
                // The element is managed in the current thread, consumer can be immediately called
                // Appelle une méthode
                consumer.accept(value);
            // Branche alternative de la condition
            } else {
                // The element is manager in a different thread, cache it
                // Appelle une méthode
                List<T> threadCacheList = threadCacheMap.computeIfAbsent(elementThread, tickThread -> new ArrayList<>());
                // Appelle une méthode
                threadCacheList.add(value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return threadCacheMap;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
