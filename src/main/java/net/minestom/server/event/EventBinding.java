// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.function.BiConsumer;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;

// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public interface EventBinding<E extends Event> {

    // Début d'une méthode/d'un bloc
    static <E extends Event, T> FilteredBuilder<E, T> filtered(EventFilter<E, T> filter, Predicate<T> predicate) {
        // Renvoie une valeur à l'appelant
        return new FilteredBuilder<>(filter, predicate);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Collection<Class<? extends Event>> eventTypes();

    // Appelle une méthode
    Consumer<E> consumer(Class<? extends Event> eventType);

    // Déclaration de type (classe/interface/enum/record)
    class FilteredBuilder<E extends Event, T> {
        // Instruction de code
        private final EventFilter<E, T> filter;
        // Instruction de code
        private final Predicate<T> predicate;
        // Appelle une méthode
        private final Map<Class<? extends Event>, BiConsumer<Object, E>> mapped = new HashMap<>();

        // Début d'une méthode/d'un bloc
        FilteredBuilder(EventFilter<E, T> filter, Predicate<T> predicate) {
            // Accès à l'objet courant/parent
            this.filter = filter;
            // Accès à l'objet courant/parent
            this.predicate = predicate;
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        public <M extends E> FilteredBuilder<E, T> map(Class<M> eventType,
                                                       // Début d'une méthode/d'un bloc
                                                       BiConsumer<T, M> consumer) {
            //noinspection unchecked
            // Accès à l'objet courant/parent
            this.mapped.put(eventType, (BiConsumer<Object, E>) consumer);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public EventBinding<E> build() {
            // Appelle une méthode
            final var copy = Map.copyOf(mapped);
            // Appelle une méthode
            final var eventTypes = copy.keySet();

            // Appelle une méthode
            Map<Class<? extends Event>, Consumer<E>> consumers = new HashMap<>(eventTypes.size());
            // Boucle : répète un bloc
            for (var eventType : eventTypes) {
                // Appelle une méthode
                final var consumer = copy.get(eventType);
                // Début d'une méthode/d'un bloc
                consumers.put(eventType, event -> {
                    // Appelle une méthode
                    final T handler = filter.getHandler(event);
                    // Embranchement : vérifie une condition
                    if (!predicate.test(handler)) return;
                    // Appelle une méthode
                    consumer.accept(handler, event);
                // Fin d'un bloc/d'une expression
                });
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new EventBinding<>() {
                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public Collection<Class<? extends Event>> eventTypes() {
                    // Renvoie une valeur à l'appelant
                    return eventTypes;
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public Consumer<E> consumer(Class<? extends Event> eventType) {
                    // Renvoie une valeur à l'appelant
                    return consumers.get(eventType);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
