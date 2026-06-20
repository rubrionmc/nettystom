// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.AsyncEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.RecursiveEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;
// Import d'une classe nécessaire
import java.util.function.BiConsumer;
// Import d'une classe nécessaire
import java.util.function.BiPredicate;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
non-sealed class EventNodeImpl<T extends Event> implements EventNode<T> {

    // Appelle une méthode
    static final Object GLOBAL_CHILD_LOCK = new Object();

    // Appelle une méthode
    private final Map<Class, Handle<T>> handleMap = new ConcurrentHashMap<>();
    // Appelle une méthode
    final Map<Class<? extends T>, ListenerEntry<T>> listenerMap = new ConcurrentHashMap<>();
    // Appelle une méthode
    final Set<EventNodeImpl<T>> children = new CopyOnWriteArraySet<>();

    // Used to store mapped nodes before any listener is added
    // Necessary to avoid creating multiple nodes for the same object
    // Always accessed through the global lock.
    // Appelle une méthode
    final Map<Object, WeakReference<EventNodeLazyImpl<T>>> mappedNodeCache = new WeakHashMap<>();
    // Store mapped nodes with at least one listener
    // Map is copied and mutated for each new active mapped node
    // Can be considered immutable.
    // Appelle une méthode
    volatile Map<Object, WeakReference<EventNodeLazyImpl<T>>> registeredMappedNode = new WeakHashMap<>();

    // Instruction de code
    final String name;
    // Instruction de code
    final EventFilter<T, ?> filter;
    // Instruction de code
    final @Nullable BiPredicate<T, Object> predicate;
    // Instruction de code
    final Class<T> eventType;
    // Instruction de code
    volatile int priority;
    // Instruction de code
    volatile @Nullable EventNodeImpl<? super T> parent;

    // Instruction de code
    EventNodeImpl(String name,
                  // Instruction de code
                  EventFilter<T, ?> filter,
                  // Annotation pour l'élément suivant
                  @Nullable BiPredicate<T, Object> predicate) {
        // Accès à l'objet courant/parent
        this.name = name;
        // Accès à l'objet courant/parent
        this.filter = filter;
        // Accès à l'objet courant/parent
        this.predicate = predicate;
        // Accès à l'objet courant/parent
        this.eventType = filter.eventType();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    public <E extends T> ListenerHandle<E> getHandle(Class<E> handleType) {
        // Renvoie une valeur à l'appelant
        return (ListenerHandle<E>) handleMap.computeIfAbsent(handleType,
                // Appelle une méthode
                aClass -> new Handle<>((Class<T>) aClass));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E extends T> List<EventNode<E>> findChildren(String name, Class<E> eventType) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final Set<EventNode<T>> children = getChildren();
            // Embranchement : vérifie une condition
            if (children.isEmpty()) return List.of();
            // Appelle une méthode
            List<EventNode<E>> result = new ArrayList<>();
            // Boucle : répète un bloc
            for (EventNode<T> child : children) {
                // Embranchement : vérifie une condition
                if (equals(child, name, eventType)) {
                    // Appelle une méthode
                    result.add((EventNode<E>) child);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                result.addAll(child.findChildren(name, eventType));
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    public Set<EventNode<T>> getChildren() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(children);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E extends T> void replaceChildren(String name, Class<E> eventType, EventNode<E> eventNode) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final Set<EventNode<T>> children = getChildren();
            // Embranchement : vérifie une condition
            if (children.isEmpty()) return;
            // Boucle : répète un bloc
            for (EventNode<T> child : children) {
                // Embranchement : vérifie une condition
                if (equals(child, name, eventType)) {
                    // Appelle une méthode
                    removeChild(child);
                    // Appelle une méthode
                    addChild(eventNode);
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                child.replaceChildren(name, eventType, eventNode);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void removeChildren(String name, Class<? extends T> eventType) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final Set<EventNode<T>> children = getChildren();
            // Embranchement : vérifie une condition
            if (children.isEmpty()) return;
            // Boucle : répète un bloc
            for (EventNode<T> child : children) {
                // Embranchement : vérifie une condition
                if (equals(child, name, eventType)) {
                    // Appelle une méthode
                    removeChild(child);
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                child.removeChildren(name, eventType);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<T> addChild(EventNode<? extends T> child) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final var childImpl = (EventNodeImpl<? extends T>) child;
            // Appelle une méthode
            Check.stateCondition(!ServerFlag.EVENT_NODE_ALLOW_MULTIPLE_PARENTS && childImpl.parent != null, "Node already has a parent");
            // Appelle une méthode
            Check.stateCondition(Objects.equals(parent, child), "Cannot have a child as parent");
            // Embranchement : vérifie une condition
            if (!children.add((EventNodeImpl<T>) childImpl)) return this; // Couldn't add the child (already present?)
            // Affecte une valeur
            childImpl.parent = this;
            // Appelle une méthode
            childImpl.invalidateEventsFor(this);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<T> removeChild(EventNode<? extends T> child) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final var childImpl = (EventNodeImpl<? extends T>) child;
            // Appelle une méthode
            final boolean result = this.children.remove(childImpl);
            // Embranchement : vérifie une condition
            if (!result) return this; // Child not found
            // Affecte une valeur
            childImpl.parent = null;
            // Appelle une méthode
            childImpl.invalidateEventsFor(this);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<T> addListener(EventListener<? extends T> listener) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final var eventType = listener.eventType();
            // Appelle une méthode
            ListenerEntry<T> entry = getEntry(eventType);
            // Appelle une méthode
            entry.listeners.add((EventListener<T>) listener);
            // Appelle une méthode
            invalidateEvent(eventType);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<T> removeListener(EventListener<? extends T> listener) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            final var eventType = listener.eventType();
            // Appelle une méthode
            ListenerEntry<T> entry = listenerMap.get(eventType);
            // Embranchement : vérifie une condition
            if (entry == null) return this; // There is no listener with such type
            // Embranchement : vérifie une condition
            if (entry.listeners.remove(listener)) invalidateEvent(eventType);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E extends T, H> EventNode<E> map(H value, EventFilter<E, H> filter) {
        // Instruction de code
        EventNodeImpl<E> node;
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            node = new EventNodeLazyImpl<>(this, value, filter);
            // Appelle une méthode
            Check.stateCondition(node.parent != null, "Node already has a parent");
            // Appelle une méthode
            Check.stateCondition(Objects.equals(parent, node), "Cannot map to self");
            // Affecte une valeur
            WeakReference<EventNodeLazyImpl<T>> previousRef = this.mappedNodeCache.putIfAbsent(value,
                    // Crée un nouvel objet
                    new WeakReference<>((EventNodeLazyImpl<T>) node));
            // Instruction de code
            EventNodeImpl<T> previous;
            // Embranchement : vérifie une condition
            if (previousRef != null && (previous = previousRef.get()) != null) return (EventNode<E>) previous;
            // Affecte une valeur
            node.parent = this;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return node;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void unmap(Object value) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            Map<Object, WeakReference<EventNodeLazyImpl<T>>> registered = new WeakHashMap<>(registeredMappedNode);
            // Appelle une méthode
            final WeakReference<EventNodeLazyImpl<T>> mappedNodeRef = registered.remove(value);
            // Accès à l'objet courant/parent
            this.registeredMappedNode = registered;
            // Instruction de code
            EventNodeLazyImpl<T> mappedNode;
            // Embranchement : vérifie une condition
            if (mappedNodeRef != null && (mappedNode = mappedNodeRef.get()) != null) {
                // Appelle une méthode
                mappedNode.invalidateEventsFor(this);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void register(EventBinding<? extends T> binding) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Boucle : répète un bloc
            for (var eventType : binding.eventTypes()) {
                // Appelle une méthode
                ListenerEntry<T> entry = getEntry((Class<? extends T>) eventType);
                // Appelle une méthode
                final boolean added = entry.bindingConsumers.add((Consumer<T>) binding.consumer(eventType));
                // Embranchement : vérifie une condition
                if (added) invalidateEvent((Class<? extends T>) eventType);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void unregister(EventBinding<? extends T> binding) {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Boucle : répète un bloc
            for (var eventType : binding.eventTypes()) {
                // Appelle une méthode
                ListenerEntry<T> entry = listenerMap.get(eventType);
                // Embranchement : vérifie une condition
                if (entry == null) return;
                // Appelle une méthode
                final boolean removed = entry.bindingConsumers.remove(binding.consumer(eventType));
                // Embranchement : vérifie une condition
                if (removed) invalidateEvent((Class<? extends T>) eventType);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Class<T> getEventType() {
        // Renvoie une valeur à l'appelant
        return eventType;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String getName() {
        // Renvoie une valeur à l'appelant
        return name;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getPriority() {
        // Renvoie une valeur à l'appelant
        return priority;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<T> setPriority(int priority) {
        // Accès à l'objet courant/parent
        this.priority = priority;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @Nullable EventNode<? super T> getParent() {
        // Appelle une méthode
        Check.stateCondition(ServerFlag.EVENT_NODE_ALLOW_MULTIPLE_PARENTS, "Cannot use getParent when multiple parents are allowed");
        // Renvoie une valeur à l'appelant
        return parent;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return createStringGraph(createGraph());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    Graph createGraph() {
        // Début d'une méthode/d'un bloc
        synchronized (GLOBAL_CHILD_LOCK) {
            // Appelle une méthode
            List<Graph> children = this.children.stream().map(EventNodeImpl::createGraph).toList();
            // Renvoie une valeur à l'appelant
            return new Graph(getName(), getEventType().getSimpleName(), getPriority(), children);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static String createStringGraph(Graph graph) {
        // Appelle une méthode
        StringBuilder buffer = new StringBuilder();
        // Appelle une méthode
        genToStringTree(buffer, "", "", graph);
        // Renvoie une valeur à l'appelant
        return buffer.toString();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void genToStringTree(StringBuilder buffer, String prefix, String childrenPrefix, Graph graph) {
        // Appelle une méthode
        buffer.append(prefix);
        // Appelle une méthode
        buffer.append(String.format("%s - EventType: %s - Priority: %d", graph.name(), graph.eventType(), graph.priority()));
        // Appelle une méthode
        buffer.append('\n');
        // Appelle une méthode
        var nextNodes = graph.children();
        // Boucle : répète un bloc
        for (Iterator<? extends Graph> iterator = nextNodes.iterator(); iterator.hasNext(); ) {
            // Appelle une méthode
            Graph next = iterator.next();
            // Embranchement : vérifie une condition
            if (iterator.hasNext()) {
                // Appelle une méthode
                genToStringTree(buffer, childrenPrefix + '├' + '─' + " ", childrenPrefix + '│' + "   ", next);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                genToStringTree(buffer, childrenPrefix + '└' + '─' + " ", childrenPrefix + "    ", next);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Graph(String name, String eventType, int priority,
                 // Début d'une méthode/d'un bloc
                 List<Graph> children) {
        // Début d'une méthode/d'un bloc
        public Graph {
            // Appelle une méthode
            children = children.stream().sorted(Comparator.comparingInt(Graph::priority)).toList();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void invalidateEventsFor(EventNodeImpl<? super T> node) {
        // Appelle une méthode
        assert Thread.holdsLock(GLOBAL_CHILD_LOCK);
        // Boucle : répète un bloc
        for (Class<? extends T> eventType : listenerMap.keySet()) {
            // Appelle une méthode
            node.invalidateEvent(eventType);
        // Fin d'un bloc/d'une expression
        }
        // TODO bindings?
        // Boucle : répète un bloc
        for (EventNodeImpl<T> child : children) {
            // Appelle une méthode
            child.invalidateEventsFor(node);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void invalidateEvent(Class<? extends T> eventClass) {
        // Début d'une méthode/d'un bloc
        forTargetEvents(eventClass, type -> {
            // Affecte une valeur
            Handle<T> handle = handleMap.computeIfAbsent(type,
                    // Appelle une méthode
                    aClass -> new Handle<>((Class<T>) aClass));
            // Appelle une méthode
            handle.invalidate();
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        invalidateRecursiveSuperclasses(eventClass);
        // Affecte une valeur
        final EventNodeImpl<? super T> parent = this.parent;
        // Embranchement : vérifie une condition
        if (parent != null) parent.invalidateEvent(eventClass);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void invalidateRecursiveSuperclasses(Class<?> eventClass) {
        // Embranchement : vérifie une condition
        if (RecursiveEvent.class.isAssignableFrom(eventClass)) {
            // Boucle : répète un bloc
            for (var cls : this.handleMap.keySet()) {
                // Embranchement : vérifie une condition
                if (eventClass.isAssignableFrom(cls)) {
                    // Accès à l'objet courant/parent
                    this.handleMap.get(cls).invalidate();
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ListenerEntry<T> getEntry(Class<? extends T> type) {
        // Renvoie une valeur à l'appelant
        return listenerMap.computeIfAbsent(type, aClass -> new ListenerEntry<>());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static boolean equals(EventNode<?> node, String name, Class<?> eventType) {
        // Renvoie une valeur à l'appelant
        return node.getName().equals(name) && eventType.isAssignableFrom((node.getEventType()));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void forTargetEvents(Class<?> type, Consumer<Class<?>> consumer) {
        // Appelle une méthode
        consumer.accept(type);
        // Recursion
        // Embranchement : vérifie une condition
        if (RecursiveEvent.class.isAssignableFrom(type)) {
            // Appelle une méthode
            final Class<?> superclass = type.getSuperclass();
            // Embranchement : vérifie une condition
            if (superclass != null && RecursiveEvent.class.isAssignableFrom(superclass)) {
                // Appelle une méthode
                forTargetEvents(superclass, consumer);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private static class ListenerEntry<T extends Event> {
        // Appelle une méthode
        final List<EventListener<T>> listeners = new CopyOnWriteArrayList<>();
        // Appelle une méthode
        final Set<Consumer<T>> bindingConsumers = new CopyOnWriteArraySet<>();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Déclaration de type (classe/interface/enum/record)
    final class Handle<E extends Event> implements ListenerHandle<E> {
        // Instruction de code
        private final Class<E> eventType;
        // Affecte une valeur
        private @Nullable Consumer<E> listener = null;
        // Instruction de code
        private volatile boolean updated;

        // Début d'une méthode/d'un bloc
        Handle(Class<E> eventType) {
            // Accès à l'objet courant/parent
            this.eventType = eventType;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void call(E event) {
            // Instruction de code
            assert !(event instanceof AsyncEvent) || Thread.currentThread().isVirtual() :
                    // Appelle une méthode
                    "AsyncEvent must be called within a Virtual Thread, got " + Thread.currentThread();
            // Appelle une méthode
            final Consumer<E> listener = updatedListener();
            // Embranchement : vérifie une condition
            if (listener == null) return;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                listener.accept(event);
            // Début d'une méthode/d'un bloc
            } catch (Throwable e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean hasListener() {
            // Renvoie une valeur à l'appelant
            return updatedListener() != null;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void invalidate() {
            // Accès à l'objet courant/parent
            this.updated = false;
            // Accès à l'objet courant/parent
            this.listener = null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Nullable Consumer<E> updatedListener() {
            // Embranchement : vérifie une condition
            if (updated) return listener;
            // Début d'une méthode/d'un bloc
            synchronized (GLOBAL_CHILD_LOCK) {
                // Embranchement : vérifie une condition
                if (updated) return listener;
                // Appelle une méthode
                final Consumer<E> listener = createConsumer();
                // Accès à l'objet courant/parent
                this.listener = listener;
                // Accès à l'objet courant/parent
                this.updated = true;
                // Renvoie une valeur à l'appelant
                return listener;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private @Nullable Consumer<E> createConsumer() {
            // Appelle une méthode
            var node = (EventNodeImpl<E>) EventNodeImpl.this;
            // Standalone listeners
            // Appelle une méthode
            List<Consumer<E>> listeners = new ArrayList<>();
            // Début d'une méthode/d'un bloc
            forTargetEvents(eventType, type -> {
                // Appelle une méthode
                final ListenerEntry<E> entry = node.listenerMap.get(type);
                // Embranchement : vérifie une condition
                if (entry != null) {
                    // Appelle une méthode
                    final Consumer<E> result = listenersConsumer(entry);
                    // Embranchement : vérifie une condition
                    if (result != null) listeners.add(result);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            final Consumer<E>[] listenersArray = listeners.toArray(Consumer[]::new);
            // Mapped
            // Appelle une méthode
            final Consumer<E> mappedListener = mappedConsumer();
            // Children
            // Affecte une valeur
            final Consumer<E>[] childrenListeners = node.children.stream()
                    // Instruction de code
                    .filter(child -> child.eventType.isAssignableFrom(eventType)) // Invalid event type
                    // Instruction de code
                    .sorted(Comparator.comparing(EventNode::getPriority))
                    // Instruction de code
                    .map(child -> ((Handle<E>) child.getHandle(eventType)).updatedListener())
                    // Instruction de code
                    .filter(Objects::nonNull)
                    // Appelle une méthode
                    .toArray(Consumer[]::new);
            // Empty check
            // Affecte une valeur
            final BiPredicate<E, Object> predicate = node.predicate;
            // Affecte une valeur
            final EventFilter<E, ?> filter = node.filter;
            // Affecte une valeur
            final boolean hasPredicate = predicate != null;
            // Affecte une valeur
            final boolean hasListeners = listenersArray.length > 0;
            // Affecte une valeur
            final boolean hasMap = mappedListener != null;
            // Affecte une valeur
            final boolean hasChildren = childrenListeners.length > 0;
            // Embranchement : vérifie une condition
            if (!hasListeners && !hasMap && !hasChildren) {
                // No listener
                // Renvoie une valeur à l'appelant
                return null;
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return e -> {
                // Filtering
                // Embranchement : vérifie une condition
                if (hasPredicate) {
                    // Appelle une méthode
                    final Object value = filter.getHandler(e);
                    // Embranchement : vérifie une condition
                    if (!predicate.test(e, value)) return;
                // Fin d'un bloc/d'une expression
                }
                // Normal listeners
                // Embranchement : vérifie une condition
                if (hasListeners) {
                    // Boucle : répète un bloc
                    for (Consumer<E> listener : listenersArray) {
                        // Appelle une méthode
                        listener.accept(e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Mapped nodes
                // Embranchement : vérifie une condition
                if (hasMap) mappedListener.accept(e);
                // Children
                // Embranchement : vérifie une condition
                if (hasChildren) {
                    // Boucle : répète un bloc
                    for (Consumer<E> childHandle : childrenListeners) {
                        // Appelle une méthode
                        childHandle.accept(e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        /**
         * Create a consumer calling all listeners from {@link EventNode#addListener(EventListener)} and
         * {@link EventNode#register(EventBinding)}.
         * <p>
         * Most computation should ideally be done outside the consumers as a one-time cost.
         */
        // Début d'une méthode/d'un bloc
        private @Nullable Consumer<E> listenersConsumer(ListenerEntry<E> entry) {
            // Appelle une méthode
            final EventListener<E>[] listenersCopy = entry.listeners.toArray(EventListener[]::new);
            // Appelle une méthode
            final Consumer<E>[] bindingsCopy = entry.bindingConsumers.toArray(Consumer[]::new);
            // Affecte une valeur
            final boolean listenersEmpty = listenersCopy.length == 0;
            // Affecte une valeur
            final boolean bindingsEmpty = bindingsCopy.length == 0;
            // Embranchement : vérifie une condition
            if (listenersEmpty && bindingsEmpty) return null;
            // Embranchement : vérifie une condition
            if (bindingsEmpty && listenersCopy.length == 1) {
                // Only one normal listener
                // Affecte une valeur
                final EventListener<E> listener = listenersCopy[0];
                // Renvoie une valeur à l'appelant
                return e -> callListener(listener, e);
            // Fin d'un bloc/d'une expression
            }
            // Worse case scenario, try to run everything
            // Renvoie une valeur à l'appelant
            return e -> {
                // Embranchement : vérifie une condition
                if (!listenersEmpty) {
                    // Boucle : répète un bloc
                    for (EventListener<E> listener : listenersCopy) {
                        // Appelle une méthode
                        callListener(listener, e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (!bindingsEmpty) {
                    // Boucle : répète un bloc
                    for (Consumer<E> eConsumer : bindingsCopy) {
                        // Appelle une méthode
                        eConsumer.accept(e);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        /**
         * Create a consumer handling {@link EventNode#map(Object, EventFilter)}.
         * The goal is to limit the amount of map lookup.
         */
        // Début d'une méthode/d'un bloc
        private @Nullable Consumer<E> mappedConsumer() {
            // Appelle une méthode
            var node = (EventNodeImpl<E>) EventNodeImpl.this;
            // Affecte une valeur
            final var mappedNodeCache = node.registeredMappedNode;
            // Embranchement : vérifie une condition
            if (mappedNodeCache.isEmpty()) return null;
            // Appelle une méthode
            Set<EventFilter<E, ?>> filters = new HashSet<>(mappedNodeCache.size());
            // Appelle une méthode
            Map<Object, WeakReference<Handle<E>>> handlers = new WeakHashMap<>(mappedNodeCache.size());

            // Retrieve all filters used to retrieve potential handlers
            // Boucle : répète un bloc
            for (var mappedEntry : mappedNodeCache.entrySet()) {
                // Appelle une méthode
                final WeakReference<EventNodeLazyImpl<E>> mappedNodeRef = mappedEntry.getValue();
                // Appelle une méthode
                final EventNodeLazyImpl<E> mappedNode = mappedNodeRef.get();
                // Embranchement : vérifie une condition
                if (mappedNode == null) continue; // Weak reference collected
                // Appelle une méthode
                final Handle<E> handle = (Handle<E>) mappedNode.getHandle(eventType);
                // Embranchement : vérifie une condition
                if (!handle.hasListener()) continue; // Implicit update
                // Appelle une méthode
                filters.add(mappedNode.filter);
                // Appelle une méthode
                handlers.put(mappedEntry.getKey(), new WeakReference<>(handle));
            // Fin d'un bloc/d'une expression
            }
            // If at least one mapped node listen to this handle type,
            // loop through them and forward to mapped node if there is a match
            // Embranchement : vérifie une condition
            if (filters.isEmpty()) return null;
            // Appelle une méthode
            final EventFilter<E, ?>[] filterList = filters.toArray(EventFilter[]::new);
            // Affecte une valeur
            final BiConsumer<EventFilter<E, ?>, E> mapper = (filter, event) -> {
                // Appelle une méthode
                final Object handler = filter.castHandler(event);
                // Appelle une méthode
                final WeakReference<Handle<E>> handleRef = handlers.get(handler);
                // Appelle une méthode
                final Handle<E> handle = handleRef != null ? handleRef.get() : null;
                // Embranchement : vérifie une condition
                if (handle != null) handle.call(event);
            // Fin d'un bloc/d'une expression
            };
            // Specialize the consumer depending on the number of filters to avoid looping
            // Renvoie une valeur à l'appelant
            return switch (filterList.length) {
                // Embranchement multiple (switch/case)
                case 1 -> event -> mapper.accept(filterList[0], event);
                // Embranchement multiple (switch/case)
                case 2 -> event -> {
                    // Appelle une méthode
                    mapper.accept(filterList[0], event);
                    // Appelle une méthode
                    mapper.accept(filterList[1], event);
                // Fin d'un bloc/d'une expression
                };
                // Embranchement multiple (switch/case)
                case 3 -> event -> {
                    // Appelle une méthode
                    mapper.accept(filterList[0], event);
                    // Appelle une méthode
                    mapper.accept(filterList[1], event);
                    // Appelle une méthode
                    mapper.accept(filterList[2], event);
                // Fin d'un bloc/d'une expression
                };
                // Embranchement multiple (switch/case)
                default -> event -> {
                    // Boucle : répète un bloc
                    for (var filter : filterList) {
                        // Appelle une méthode
                        mapper.accept(filter, event);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                };
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        void callListener(EventListener<E> listener, E event) {
            // Appelle une méthode
            var node = (EventNodeImpl<E>) EventNodeImpl.this;
            // Appelle une méthode
            EventListener.Result result = listener.run(event);
            // Embranchement : vérifie une condition
            if (result == EventListener.Result.EXPIRED) {
                // Appelle une méthode
                node.removeListener(listener);
                // Appelle une méthode
                invalidate();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
