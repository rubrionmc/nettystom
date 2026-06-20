// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Iterator;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.Random;

// Déclaration de type (classe/interface/enum/record)
public final class WeightedList<T> implements Iterable<T> {
    // Début d'une méthode/d'un bloc
    public static <T> NetworkBuffer.Type<WeightedList<T>> networkType(NetworkBuffer.Type<T> valueType) {
        // Renvoie une valeur à l'appelant
        return Entry.networkType(valueType).list().transform(WeightedList::new, WeightedList::entries);
    // Fin d'un bloc/d'une expression
    }
    // Début d'une méthode/d'un bloc
    public static <T> Codec<WeightedList<T>> codec(StructCodec<T> valueCodec) {
        // Renvoie une valeur à l'appelant
        return Entry.codec(valueCodec).list().transform(WeightedList::new, WeightedList::entries);
    // Fin d'un bloc/d'une expression
    }
    // Début d'une méthode/d'un bloc
    public static <T> Codec<WeightedList<T>> codec(Codec<T> valueCodec) {
        // Appelle une méthode
        StructCodec<T> wrapper = StructCodec.struct("data", valueCodec, t -> t, t -> t);
        // Renvoie une valeur à l'appelant
        return Entry.codec(wrapper).list().transform(WeightedList::new, WeightedList::entries);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    public static <T> WeightedList<T> of(Entry<T>... entries) {
        // Renvoie une valeur à l'appelant
        return new WeightedList<>(List.of(entries));
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final List<Entry<T>> entries;
    // Instruction de code
    private final int totalWeight;

    // Début d'une méthode/d'un bloc
    public WeightedList(List<Entry<T>> entries) {
        // Accès à l'objet courant/parent
        this.entries = List.copyOf(entries);

        // Affecte une valeur
        int total = 0;
        // Boucle : répète un bloc
        for (Entry<T> entry : this.entries)
            // Appelle une méthode
            total += entry.weight();
        // Accès à l'objet courant/parent
        this.totalWeight = total;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Entry<T>> entries() {
        // Renvoie une valeur à l'appelant
        return entries;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable T pick(Random random) {
        // Appelle une méthode
        int pick = random.nextInt(totalWeight);
        // Boucle : répète un bloc
        for (Entry<T> entry : entries) {
            // Appelle une méthode
            pick -= entry.weight();
            // Embranchement : vérifie une condition
            if (pick < 0) return entry.value();
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public T pickOrThrow(Random random) {
        // Renvoie une valeur à l'appelant
        return Objects.requireNonNull(pick(random), "Weighted list was empty");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Iterator<T> iterator() {
        // Appelle une méthode
        final var delegate = entries.iterator();
        // Renvoie une valeur à l'appelant
        return new Iterator<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean hasNext() {
                // Renvoie une valeur à l'appelant
                return delegate.hasNext();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public T next() {
                // Renvoie une valeur à l'appelant
                return delegate.next().value();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Entry<T>(T value, int weight) {
        // Début d'une méthode/d'un bloc
        public static <T> NetworkBuffer.Type<Entry<T>> networkType(NetworkBuffer.Type<T> valueType) {
            // Renvoie une valeur à l'appelant
            return NetworkBufferTemplate.template(
                    // Instruction de code
                    valueType, Entry::value,
                    // Instruction de code
                    NetworkBuffer.VAR_INT, Entry::weight,
                    // Instruction de code
                    Entry::new);
        // Fin d'un bloc/d'une expression
        }
        // Début d'une méthode/d'un bloc
        public static <T> StructCodec<Entry<T>> codec(StructCodec<T> valueCodec) {
            // Renvoie une valeur à l'appelant
            return StructCodec.struct(
                    // Instruction de code
                    StructCodec.INLINE, valueCodec, Entry::value,
                    // Instruction de code
                    "weight", Codec.INT, Entry::weight,
                    // Instruction de code
                    Entry::new);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
