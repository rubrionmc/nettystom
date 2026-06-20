// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.RecursiveEvent;
// Import of a required class
import org.jetbrains.annotations.Contract;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Predicate;

/**
 * Represents an event listener (handler) in an event graph.
 * <p>
 * A listener is responsible for executing some action based on an event triggering.
 *
 * @param <T> The event type being handled.
 */
// Type declaration (class/interface/enum/record)
public interface EventListener<T extends Event> {

    // Calls a method
    Class<T> eventType();

    // Calls a method
    Result run(T event);

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    static <T extends Event> EventListener.Builder<T> builder(Class<T> eventType) {
        // Returns a value to the caller
        return new EventListener.Builder<>(eventType);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    static <T extends Event> EventListener<T> of(Class<T> eventType, Consumer<T> listener) {
        // Branch: checks a condition
        if (CancellableEvent.class.isAssignableFrom(eventType) || RecursiveEvent.class.isAssignableFrom(eventType)) {
            // Returns a value to the caller
            return new Builder.ListenerImpl<>(eventType, event -> {
                // Branch: checks a condition
                if (event instanceof CancellableEvent cancellableEvent && cancellableEvent.isCancelled()) {
                    // Returns a value to the caller
                    return Result.INVALID;
                // End of a block/expression
                }
                // Calls a method
                listener.accept(event);
                // Returns a value to the caller
                return Result.SUCCESS;
            // End of a block/expression
            });
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return new Builder.ListenerImpl<>(eventType, event -> {
                // Calls a method
                listener.accept(event);
                // Returns a value to the caller
                return Result.SUCCESS;
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    class Builder<T extends Event> {
        // Type declaration (class/interface/enum/record)
        private record ListenerImpl<T extends Event>(
                // Code statement
                Class<T> eventType,
                // Code statement
                Function<T, EventListener.Result> function
        // Start of a method/block
        ) implements EventListener<T> {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Result run(T t) {
                // Returns a value to the caller
                return function.apply(t);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Code statement
        private final Class<T> eventType;
        // Calls a method
        private final List<Predicate<T>> filters = new ArrayList<>();
        // Assigns a value
        private boolean ignoreCancelled = true;
        // Code statement
        private int expireCount;
        // Code statement
        private Predicate<T> expireWhen;
        // Code statement
        private Consumer<T> handler;

        // Start of a method/block
        protected Builder(Class<T> eventType) {
            // Access to the current/parent object
            this.eventType = eventType;
        // End of a block/expression
        }

        /**
         * Adds a filter to the executor of this listener. The executor will only
         * be called if this condition passes on the given event.
         */
        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public EventListener.Builder<T> filter(Predicate<T> filter) {
            // Access to the current/parent object
            this.filters.add(filter);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        /**
         * Specifies if the handler should still be called if {@link CancellableEvent#isCancelled()} returns {@code true}.
         * <p>
         * Default is set to {@code true}.
         *
         * @param ignoreCancelled True to stop processing the event when cancelled
         */
        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public EventListener.Builder<T> ignoreCancelled(boolean ignoreCancelled) {
            // Access to the current/parent object
            this.ignoreCancelled = ignoreCancelled;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        /**
         * Removes this listener after it has been executed the given number of times.
         *
         * @param expireCount The number of times to execute
         */
        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public EventListener.Builder<T> expireCount(int expireCount) {
            // Access to the current/parent object
            this.expireCount = expireCount;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        /**
         * Expires this listener when it passes the given condition. The expiration will
         * happen before the event is executed.
         *
         * @param expireWhen The condition to test
         */
        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public EventListener.Builder<T> expireWhen(Predicate<T> expireWhen) {
            // Access to the current/parent object
            this.expireWhen = expireWhen;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        /**
         * Sets the handler for this event listener. This will be executed if the listener passes
         * all conditions.
         */
        // Annotation for the following element
        @Contract(value = "_ -> this")
        // Start of a method/block
        public EventListener.Builder<T> handler(Consumer<T> handler) {
            // Access to the current/parent object
            this.handler = handler;
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Annotation for the following element
        @Contract(value = "-> new", pure = true)
        // Start of a method/block
        public EventListener<T> build() {
            // Assigns a value
            final boolean ignoreCancelled = this.ignoreCancelled;
            // Calls a method
            AtomicInteger expirationCount = new AtomicInteger(this.expireCount);
            // Calls a method
            final boolean hasExpirationCount = expirationCount.get() > 0;

            // Assigns a value
            final Predicate<T> expireWhen = this.expireWhen;

            // Calls a method
            final var filters = new ArrayList<>(this.filters);
            // Assigns a value
            final var handler = this.handler;
            // Returns a value to the caller
            return new ListenerImpl<>(eventType, event -> {
                // Event cancellation
                // Branch: checks a condition
                if (ignoreCancelled && event instanceof CancellableEvent cancellableEvent &&
                        // Start of a method/block
                        cancellableEvent.isCancelled()) {
                    // Returns a value to the caller
                    return Result.INVALID;
                // End of a block/expression
                }
                // Expiration predicate
                // Branch: checks a condition
                if (expireWhen != null && expireWhen.test(event)) {
                    // Returns a value to the caller
                    return Result.EXPIRED;
                // End of a block/expression
                }
                // Filtering
                // Branch: checks a condition
                if (!filters.isEmpty()) {
                    // Loop: repeats a block
                    for (var filter : filters) {
                        // Branch: checks a condition
                        if (!filter.test(event)) {
                            // Cancelled
                            // Returns a value to the caller
                            return Result.INVALID;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Handler
                // Branch: checks a condition
                if (handler != null) {
                    // Calls a method
                    handler.accept(event);
                // End of a block/expression
                }
                // Expiration count
                // Branch: checks a condition
                if (hasExpirationCount && expirationCount.decrementAndGet() == 0) {
                    // Returns a value to the caller
                    return Result.EXPIRED;
                // End of a block/expression
                }
                // Returns a value to the caller
                return Result.SUCCESS;
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum Result {
        // Code statement
        SUCCESS,
        // Code statement
        INVALID,
        // Code statement
        EXPIRED,
        // Code statement
        EXCEPTION
    // End of a block/expression
    }
// End of a block/expression
}
