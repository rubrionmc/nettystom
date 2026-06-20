// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.EventListener;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Déclaration de type (classe/interface/enum/record)
final class EnvImpl implements Env {
    // Instruction de code
    private final ServerProcess process;
    // Appelle une méthode
    private final List<FlexibleListenerImpl<?>> listeners = new CopyOnWriteArrayList<>();

    // Début d'une méthode/d'un bloc
    public EnvImpl(ServerProcess process) {
        // Accès à l'objet courant/parent
        this.process = process;
        // If exceptions reach the exception handler, by default fail the test.
        // Appelle une méthode
        process().exception().setExceptionHandler(EnvImpl::handleException);

        // Start the dispatcher threads if not already started.
        // Appelle une méthode
        process().dispatcher().start();

        // Use player provider to disable queued chunk sending.
        // Set here to allow an individual test to override if they want.
        // Appelle une méthode
        process.connection().setPlayerProvider(TestConnectionImpl.TestPlayerImpl::new);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static void handleException(Throwable exception) {
        // Appelle une méthode
        Assertions.fail("Server threw exception", exception);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ServerProcess process() {
        // Renvoie une valeur à l'appelant
        return process;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TestConnection createConnection(GameProfile gameProfile) {
        // Renvoie une valeur à l'appelant
        return new TestConnectionImpl(this, gameProfile);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E extends Event, H> Collector<E> trackEvent(Class<E> eventType, EventFilter<? super E, H> filter, H actor) {
        // Appelle une méthode
        var tracker = new EventCollector<E>(actor);
        // Accès à l'objet courant/parent
        this.process.eventHandler().map(actor, filter).addListener(eventType, tracker.events::add);
        // Renvoie une valeur à l'appelant
        return tracker;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E extends Event> FlexibleListener<E> listen(Class<E> eventType) {
        // Appelle une méthode
        var handler = process.eventHandler();
        // Appelle une méthode
        var flexible = new FlexibleListenerImpl<>(eventType);
        // Appelle une méthode
        var listener = EventListener.of(eventType, e -> flexible.handler.accept(e));
        // Appelle une méthode
        handler.addListener(listener);
        // Accès à l'objet courant/parent
        this.listeners.add(flexible);
        // Renvoie une valeur à l'appelant
        return flexible;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void cleanup() {
        // Accès à l'objet courant/parent
        this.listeners.forEach(FlexibleListenerImpl::check);
        // Accès à l'objet courant/parent
        this.process.stop();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class EventCollector<E extends Event> implements Collector<E> {
        // Instruction de code
        private final Object handler;
        // Appelle une méthode
        private final List<E> events = new CopyOnWriteArrayList<>();

        // Début d'une méthode/d'un bloc
        public EventCollector(Object handler) {
            // Accès à l'objet courant/parent
            this.handler = handler;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<E> collect() {
            // Appelle une méthode
            process.eventHandler().unmap(handler);
            // Renvoie une valeur à l'appelant
            return List.copyOf(events);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class FlexibleListenerImpl<E extends Event> implements FlexibleListener<E> {
        // Instruction de code
        private final Class<E> eventType;
        // Affecte une valeur
        private Consumer<E> handler = e -> {
        // Fin d'un bloc/d'une expression
        };
        // Instruction de code
        private boolean initialized;
        // Instruction de code
        private boolean called;

        // Début d'une méthode/d'un bloc
        FlexibleListenerImpl(Class<E> eventType) {
            // Accès à l'objet courant/parent
            this.eventType = eventType;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void followup(Consumer<E> handler) {
            // Appelle une méthode
            updateHandler(handler);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void failFollowup() {
            // Appelle une méthode
            updateHandler(e -> fail("Event " + e.getClass().getSimpleName() + " was not expected"));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void updateHandler(Consumer<E> handler) {
            // Appelle une méthode
            check();
            // Accès à l'objet courant/parent
            this.initialized = true;
            // Accès à l'objet courant/parent
            this.called = false;
            // Accès à l'objet courant/parent
            this.handler = e -> {
                // Appelle une méthode
                handler.accept(e);
                // Accès à l'objet courant/parent
                this.called = true;
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void check() {
            // Appelle une méthode
            assertTrue(!initialized || called, "Last listener has not been called: " + eventType.getSimpleName());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
