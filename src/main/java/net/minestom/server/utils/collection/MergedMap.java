// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.BiConsumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class MergedMap<K, V> extends AbstractMap<K, V> {
    // Instruction de code
    private final Map<K, V> first, second;

    // Début d'une méthode/d'un bloc
    public MergedMap(Map<K, V> first, Map<K, V> second) {
        // Accès à l'objet courant/parent
        this.first = Objects.requireNonNull(first);
        // Accès à l'objet courant/parent
        this.second = Objects.requireNonNull(second);
    // Fin d'un bloc/d'une expression
    }

    // mandatory methods

    // Affecte une valeur
    final Set<Entry<K, V>> entrySet = new AbstractSet<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<Map.Entry<K, V>> iterator() {
            // Renvoie une valeur à l'appelant
            return stream().iterator();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int size() {
            // Renvoie une valeur à l'appelant
            return (int) stream().count();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Stream<Entry<K, V>> stream() {
            // Renvoie une valeur à l'appelant
            return Stream.concat(first.entrySet().stream(), secondStream())
                    // Appelle une méthode
                    .map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), e.getValue()));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Stream<Entry<K, V>> parallelStream() {
            // Renvoie une valeur à l'appelant
            return stream().parallel();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Spliterator<Entry<K, V>> spliterator() {
            // Renvoie une valeur à l'appelant
            return stream().spliterator();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    Stream<Entry<K, V>> secondStream() {
        // Renvoie une valeur à l'appelant
        return second.entrySet().stream().filter(e -> !first.containsKey(e.getKey()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<Map.Entry<K, V>> entrySet() {
        // Renvoie une valeur à l'appelant
        return entrySet;
    // Fin d'un bloc/d'une expression
    }

    // optimizations

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean containsKey(Object key) {
        // Renvoie une valeur à l'appelant
        return first.containsKey(key) || second.containsKey(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean containsValue(Object value) {
        // Renvoie une valeur à l'appelant
        return first.containsValue(value) ||
                // Appelle une méthode
                secondStream().anyMatch(Predicate.isEqual(value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public V get(Object key) {
        // Appelle une méthode
        V v = first.get(key);
        // Renvoie une valeur à l'appelant
        return v != null ? v : second.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public V getOrDefault(Object key, V defaultValue) {
        // Appelle une méthode
        V v = first.get(key);
        // Renvoie une valeur à l'appelant
        return v != null ? v : second.getOrDefault(key, defaultValue);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void forEach(BiConsumer<? super K, ? super V> action) {
        // Appelle une méthode
        first.forEach(action);
        // Début d'une méthode/d'un bloc
        second.forEach((k, v) -> {
            // Embranchement : vérifie une condition
            if (!first.containsKey(k)) action.accept(k, v);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
