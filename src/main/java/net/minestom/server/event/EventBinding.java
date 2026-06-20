// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.BiConsumer;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Predicate;

// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public interface EventBinding<E extends Event> {

    // Start of a method/block
    static <E extends Event, T> FilteredBuilder<E, T> filtered(EventFilter<E, T> filter, Predicate<T> predicate) {
        // Returns a value to the caller
        return new FilteredBuilder<>(filter, predicate);
    // End of a block/expression
    }

    // Calls a method
    Collection<Class<? extends Event>> eventTypes();

    // Calls a method
    Consumer<E> consumer(Class<? extends Event> eventType);

    // Type declaration (class/interface/enum/record)
    class FilteredBuilder<E extends Event, T> {
        // Code statement
        private final EventFilter<E, T> filter;
        // Code statement
        private final Predicate<T> predicate;
        // Calls a method
        private final Map<Class<? extends Event>, BiConsumer<Object, E>> mapped = new HashMap<>();

        // Start of a method/block
        FilteredBuilder(EventFilter<E, T> filter, Predicate<T> predicate) {
            // Access to the current/parent object
            this.filter = filter;
            // Access to the current/parent object
            this.predicate = predicate;
        // End of a block/expression
        }

        // Code statement
        public <M extends E> FilteredBuilder<E, T> map(Class<M> eventType,
                                                       // Start of a method/block
                                                       BiConsumer<T, M> consumer) {
            //noinspection unchecked
            // Access to the current/parent object
            this.mapped.put(eventType, (BiConsumer<Object, E>) consumer);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public EventBinding<E> build() {
            // Calls a method
            final var copy = Map.copyOf(mapped);
            // Calls a method
            final var eventTypes = copy.keySet();

            // Calls a method
            Map<Class<? extends Event>, Consumer<E>> consumers = new HashMap<>(eventTypes.size());
            // Loop: repeats a block
            for (var eventType : eventTypes) {
                // Calls a method
                final var consumer = copy.get(eventType);
                // Start of a method/block
                consumers.put(eventType, event -> {
                    // Calls a method
                    final T handler = filter.getHandler(event);
                    // Branch: checks a condition
                    if (!predicate.test(handler)) return;
                    // Calls a method
                    consumer.accept(handler, event);
                // End of a block/expression
                });
            // End of a block/expression
            }
            // Returns a value to the caller
            return new EventBinding<>() {
                // Annotation for the following element
                @Override
                // Start of a method/block
                public Collection<Class<? extends Event>> eventTypes() {
                    // Returns a value to the caller
                    return eventTypes;
                // End of a block/expression
                }

                // Annotation for the following element
                @Override
                // Start of a method/block
                public Consumer<E> consumer(Class<? extends Event> eventType) {
                    // Returns a value to the caller
                    return consumers.get(eventType);
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
