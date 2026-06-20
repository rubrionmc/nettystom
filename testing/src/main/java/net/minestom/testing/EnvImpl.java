// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.EventListener;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import org.junit.jupiter.api.Assertions;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.function.Consumer;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Type declaration (class/interface/enum/record)
final class EnvImpl implements Env {
    // Code statement
    private final ServerProcess process;
    // Calls a method
    private final List<FlexibleListenerImpl<?>> listeners = new CopyOnWriteArrayList<>();

    // Start of a method/block
    public EnvImpl(ServerProcess process) {
        // Access to the current/parent object
        this.process = process;
        // If exceptions reach the exception handler, by default fail the test.
        // Calls a method
        process().exception().setExceptionHandler(EnvImpl::handleException);

        // Start the dispatcher threads if not already started.
        // Calls a method
        process().dispatcher().start();

        // Use player provider to disable queued chunk sending.
        // Set here to allow an individual test to override if they want.
        // Calls a method
        process.connection().setPlayerProvider(TestConnectionImpl.TestPlayerImpl::new);
    // End of a block/expression
    }

    // Start of a method/block
    static void handleException(Throwable exception) {
        // Calls a method
        Assertions.fail("Server threw exception", exception);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ServerProcess process() {
        // Returns a value to the caller
        return process;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TestConnection createConnection(GameProfile gameProfile) {
        // Returns a value to the caller
        return new TestConnectionImpl(this, gameProfile);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E extends Event, H> Collector<E> trackEvent(Class<E> eventType, EventFilter<? super E, H> filter, H actor) {
        // Calls a method
        var tracker = new EventCollector<E>(actor);
        // Access to the current/parent object
        this.process.eventHandler().map(actor, filter).addListener(eventType, tracker.events::add);
        // Returns a value to the caller
        return tracker;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E extends Event> FlexibleListener<E> listen(Class<E> eventType) {
        // Calls a method
        var handler = process.eventHandler();
        // Calls a method
        var flexible = new FlexibleListenerImpl<>(eventType);
        // Calls a method
        var listener = EventListener.of(eventType, e -> flexible.handler.accept(e));
        // Calls a method
        handler.addListener(listener);
        // Access to the current/parent object
        this.listeners.add(flexible);
        // Returns a value to the caller
        return flexible;
    // End of a block/expression
    }

    // Start of a method/block
    void cleanup() {
        // Access to the current/parent object
        this.listeners.forEach(FlexibleListenerImpl::check);
        // Access to the current/parent object
        this.process.stop();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class EventCollector<E extends Event> implements Collector<E> {
        // Code statement
        private final Object handler;
        // Calls a method
        private final List<E> events = new CopyOnWriteArrayList<>();

        // Start of a method/block
        public EventCollector(Object handler) {
            // Access to the current/parent object
            this.handler = handler;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<E> collect() {
            // Calls a method
            process.eventHandler().unmap(handler);
            // Returns a value to the caller
            return List.copyOf(events);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class FlexibleListenerImpl<E extends Event> implements FlexibleListener<E> {
        // Code statement
        private final Class<E> eventType;
        // Assigns a value
        private Consumer<E> handler = e -> {
        // End of a block/expression
        };
        // Code statement
        private boolean initialized;
        // Code statement
        private boolean called;

        // Start of a method/block
        FlexibleListenerImpl(Class<E> eventType) {
            // Access to the current/parent object
            this.eventType = eventType;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void followup(Consumer<E> handler) {
            // Calls a method
            updateHandler(handler);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void failFollowup() {
            // Calls a method
            updateHandler(e -> fail("Event " + e.getClass().getSimpleName() + " was not expected"));
        // End of a block/expression
        }

        // Start of a method/block
        void updateHandler(Consumer<E> handler) {
            // Calls a method
            check();
            // Access to the current/parent object
            this.initialized = true;
            // Access to the current/parent object
            this.called = false;
            // Access to the current/parent object
            this.handler = e -> {
                // Calls a method
                handler.accept(e);
                // Access to the current/parent object
                this.called = true;
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        void check() {
            // Calls a method
            assertTrue(!initialized || called, "Last listener has not been called: " + eventType.getSimpleName());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
