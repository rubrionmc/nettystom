// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.RecursiveEvent;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Predicate;

/**
 * Represents an event listener (handler) in an event graph.
 * <p>
 * A listener is responsible for executing some action based on an event triggering.
 *
 * @param <T> The event type being handled.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EventListener<T extends Event> {

    // Appelle une méthode
    Class<T> eventType();

    // Appelle une méthode
    Result run(T event);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    static <T extends Event> EventListener.Builder<T> builder(Class<T> eventType) {
        // Renvoie une valeur à l'appelant
        return new EventListener.Builder<>(eventType);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Create an event listener without any special options. The given listener will be executed
     * if the event passes all parent filtering.
     *
     * @param eventType The event type to handle
     * @param listener  The handler function
     * @param <T>       The event type to handle
     * @return An event listener with the given properties
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    static <T extends Event> EventListener<T> of(Class<T> eventType, Consumer<T> listener) {
        // Embranchement : vérifie une condition
        if (CancellableEvent.class.isAssignableFrom(eventType) || RecursiveEvent.class.isAssignableFrom(eventType)) {
            // Renvoie une valeur à l'appelant
            return new Builder.ListenerImpl<>(eventType, event -> {
                // Embranchement : vérifie une condition
                if (event instanceof CancellableEvent cancellableEvent && cancellableEvent.isCancelled()) {
                    // Renvoie une valeur à l'appelant
                    return Result.INVALID;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                listener.accept(event);
                // Renvoie une valeur à l'appelant
                return Result.SUCCESS;
            // Fin d'un bloc/d'une expression
            });
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return new Builder.ListenerImpl<>(eventType, event -> {
                // Appelle une méthode
                listener.accept(event);
                // Renvoie une valeur à l'appelant
                return Result.SUCCESS;
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    class Builder<T extends Event> {
        // Déclaration de type (classe/interface/enum/record)
        private record ListenerImpl<T extends Event>(
                // Instruction de code
                Class<T> eventType,
                // Instruction de code
                Function<T, EventListener.Result> function
        // Début d'une méthode/d'un bloc
        ) implements EventListener<T> {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Result run(T t) {
                // Renvoie une valeur à l'appelant
                return function.apply(t);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private final Class<T> eventType;
        // Appelle une méthode
        private final List<Predicate<T>> filters = new ArrayList<>();
        // Affecte une valeur
        private boolean ignoreCancelled = true;
        // Instruction de code
        private int expireCount;
        // Instruction de code
        private Predicate<T> expireWhen;
        // Instruction de code
        private Consumer<T> handler;

        // Début d'une méthode/d'un bloc
        protected Builder(Class<T> eventType) {
            // Accès à l'objet courant/parent
            this.eventType = eventType;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Adds a filter to the executor of this listener. The executor will only
         * be called if this condition passes on the given event.
         */
        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public EventListener.Builder<T> filter(Predicate<T> filter) {
            // Accès à l'objet courant/parent
            this.filters.add(filter);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Specifies if the handler should still be called if {@link CancellableEvent#isCancelled()} returns {@code true}.
         * <p>
         * Default is set to {@code true}.
         *
         * @param ignoreCancelled True to stop processing the event when cancelled
         */
        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public EventListener.Builder<T> ignoreCancelled(boolean ignoreCancelled) {
            // Accès à l'objet courant/parent
            this.ignoreCancelled = ignoreCancelled;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Removes this listener after it has been executed the given number of times.
         *
         * @param expireCount The number of times to execute
         */
        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public EventListener.Builder<T> expireCount(int expireCount) {
            // Accès à l'objet courant/parent
            this.expireCount = expireCount;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Expires this listener when it passes the given condition. The expiration will
         * happen before the event is executed.
         *
         * @param expireWhen The condition to test
         */
        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public EventListener.Builder<T> expireWhen(Predicate<T> expireWhen) {
            // Accès à l'objet courant/parent
            this.expireWhen = expireWhen;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        /**
         * Sets the handler for this event listener. This will be executed if the listener passes
         * all conditions.
         */
        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this")
        // Début d'une méthode/d'un bloc
        public EventListener.Builder<T> handler(Consumer<T> handler) {
            // Accès à l'objet courant/parent
            this.handler = handler;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "-> new", pure = true)
        // Début d'une méthode/d'un bloc
        public EventListener<T> build() {
            // Affecte une valeur
            final boolean ignoreCancelled = this.ignoreCancelled;
            // Appelle une méthode
            AtomicInteger expirationCount = new AtomicInteger(this.expireCount);
            // Appelle une méthode
            final boolean hasExpirationCount = expirationCount.get() > 0;

            // Affecte une valeur
            final Predicate<T> expireWhen = this.expireWhen;

            // Appelle une méthode
            final var filters = new ArrayList<>(this.filters);
            // Affecte une valeur
            final var handler = this.handler;
            // Renvoie une valeur à l'appelant
            return new ListenerImpl<>(eventType, event -> {
                // Event cancellation
                // Embranchement : vérifie une condition
                if (ignoreCancelled && event instanceof CancellableEvent cancellableEvent &&
                        // Début d'une méthode/d'un bloc
                        cancellableEvent.isCancelled()) {
                    // Renvoie une valeur à l'appelant
                    return Result.INVALID;
                // Fin d'un bloc/d'une expression
                }
                // Expiration predicate
                // Embranchement : vérifie une condition
                if (expireWhen != null && expireWhen.test(event)) {
                    // Renvoie une valeur à l'appelant
                    return Result.EXPIRED;
                // Fin d'un bloc/d'une expression
                }
                // Filtering
                // Embranchement : vérifie une condition
                if (!filters.isEmpty()) {
                    // Boucle : répète un bloc
                    for (var filter : filters) {
                        // Embranchement : vérifie une condition
                        if (!filter.test(event)) {
                            // Cancelled
                            // Renvoie une valeur à l'appelant
                            return Result.INVALID;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Handler
                // Embranchement : vérifie une condition
                if (handler != null) {
                    // Appelle une méthode
                    handler.accept(event);
                // Fin d'un bloc/d'une expression
                }
                // Expiration count
                // Embranchement : vérifie une condition
                if (hasExpirationCount && expirationCount.decrementAndGet() == 0) {
                    // Renvoie une valeur à l'appelant
                    return Result.EXPIRED;
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return Result.SUCCESS;
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum Result {
        // Instruction de code
        SUCCESS,
        // Instruction de code
        INVALID,
        // Instruction de code
        EXPIRED,
        // Instruction de code
        EXCEPTION
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
