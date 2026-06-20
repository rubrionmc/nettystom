// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.Iterator;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;
// Import d'une classe nécessaire
import java.util.function.Function;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public record MappedCollection<O, R>(Collection<O> original,
                                     // Début d'une méthode/d'un bloc
                                     Function<O, R> mapper) implements Collection<R> {
    // Début d'une méthode/d'un bloc
    public static <O extends AtomicReference<R>, R> MappedCollection<O, R> plainReferences(Collection<O> original) {
        // Renvoie une valeur à l'appelant
        return new MappedCollection<>(original, AtomicReference::getPlain);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int size() {
        // Renvoie une valeur à l'appelant
        return original.size();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isEmpty() {
        // Renvoie une valeur à l'appelant
        return original.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean contains(Object o) {
        // Boucle : répète un bloc
        for (var entry : original) {
            // Embranchement : vérifie une condition
            if (mapper.apply(entry).equals(o)) return true;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterator<R> iterator() {
        // Appelle une méthode
        var iterator = original.iterator();
        // Renvoie une valeur à l'appelant
        return new Iterator<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean hasNext() {
                // Renvoie une valeur à l'appelant
                return iterator.hasNext();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public R next() {
                // Renvoie une valeur à l'appelant
                return mapper.apply(iterator.next());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object [] toArray() {
        // TODO
        // Lève une exception
        throw new UnsupportedOperationException("Unsupported array object");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> T [] toArray(T [] a) {
        // TODO
        // Lève une exception
        throw new UnsupportedOperationException("Unsupported array generic");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean containsAll(Collection<?> c) {
        // Embranchement : vérifie une condition
        if (c.size() > original.size()) return false;
        // Boucle : répète un bloc
        for (var entry : c) {
            // Embranchement : vérifie une condition
            if (!contains(entry)) return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean add(R t) {
        // Lève une exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean remove(Object o) {
        // Lève une exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addAll(Collection<? extends R> c) {
        // Lève une exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeAll(Collection<?> c) {
        // Lève une exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean retainAll(Collection<?> c) {
        // Lève une exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clear() {
        // Lève une exception
        throw new UnsupportedOperationException("Unmodifiable collection");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
