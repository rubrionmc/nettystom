// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.event.trait.AsyncEvent;
// Import of a required class
import net.minestom.server.event.trait.RecursiveEvent;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;
// Import of a required class
import java.util.function.BiConsumer;
// Import of a required class
import java.util.function.BiPredicate;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
non-sealed class EventNodeImpl<T extends Event> implements EventNode<T> {

    // Calls a method
    static final Object GLOBAL_CHILD_LOCK = new Object();

    // Calls a method
    private final Map<Class, Handle<T>> handleMap = new ConcurrentHashMap<>();
    // Calls a method
    final Map<Class<? extends T>, ListenerEntry<T>> listenerMap = new ConcurrentHashMap<>();
    // Calls a method
    final Set<EventNodeImpl<T>> children = new CopyOnWriteArraySet<>();

    // Used to store mapped nodes before any listener is added
    // Necessary to avoid creating multiple nodes for the same object
    // Always accessed through the global lock.
    // Calls a method
    final Map<Object, WeakReference<EventNodeLazyImpl<T>>> mappedNodeCache = new WeakHashMap<>();
    // Store mapped nodes with at least one listener
    // Map is copied and mutated for each new active mapped node
    // Can be considered immutable.
    // Calls a method
    volatile Map<Object, WeakReference<EventNodeLazyImpl<T>>> registeredMappedNode = new WeakHashMap<>();

    // Code statement
    final String name;
    // Code statement
    final EventFilter<T, ?> filter;
    // Code statement
    final @Nullable BiPredicate<T, Object> predicate;
    // Code statement
    final Class<T> eventType;
    // Code statement
    volatile int priority;
    // Code statement
    volatile @Nullable EventNodeImpl<? super T> parent;

    // Code statement
    EventNodeImpl(String name,
                  // Code statement
                  EventFilter<T, ?> filter,
                  // Annotation for the following element
                  @Nullable BiPredicate<T, Object> predicate) {
        // Access to the current/parent object
        this.name = name;
        // Access to the current/parent object
        this.filter = filter;
        // Access to the current/parent object
        this.predicate = predicate;
        // Access to the current/parent object
        this.eventType = filter.eventType();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    public <E extends T> ListenerHandle<E> getHandle(Class<E> handleType) {
        // Returns a value to the caller
        return (ListenerHandle<E>) handleMap.computeIfAbsent(handleType,
                // Calls a method
                aClass -> new Handle<>((Class<T>) aClass));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E extends T> List<EventNode<E>> findChildren(String name, Class<E> eventType) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final Set<EventNode<T>> children = getChildren();
            // Branch: checks a condition
            if (children.isEmpty()) return List.of();
            // Calls a method
            List<EventNode<E>> result = new ArrayList<>();
            // Loop: repeats a block
            for (EventNode<T> child : children) {
                // Branch: checks a condition
                if (equals(child, name, eventType)) {
                    // Calls a method
                    result.add((EventNode<E>) child);
                // End of a block/expression
                }
                // Calls a method
                result.addAll(child.findChildren(name, eventType));
            // End of a block/expression
            }
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    public Set<EventNode<T>> getChildren() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(children);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E extends T> void replaceChildren(String name, Class<E> eventType, EventNode<E> eventNode) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final Set<EventNode<T>> children = getChildren();
            // Branch: checks a condition
            if (children.isEmpty()) return;
            // Loop: repeats a block
            for (EventNode<T> child : children) {
                // Branch: checks a condition
                if (equals(child, name, eventType)) {
                    // Calls a method
                    removeChild(child);
                    // Calls a method
                    addChild(eventNode);
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
                // Calls a method
                child.replaceChildren(name, eventType, eventNode);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void removeChildren(String name, Class<? extends T> eventType) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final Set<EventNode<T>> children = getChildren();
            // Branch: checks a condition
            if (children.isEmpty()) return;
            // Loop: repeats a block
            for (EventNode<T> child : children) {
                // Branch: checks a condition
                if (equals(child, name, eventType)) {
                    // Calls a method
                    removeChild(child);
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }
                // Calls a method
                child.removeChildren(name, eventType);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<T> addChild(EventNode<? extends T> child) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final var childImpl = (EventNodeImpl<? extends T>) child;
            // Calls a method
            Check.stateCondition(!ServerFlag.EVENT_NODE_ALLOW_MULTIPLE_PARENTS && childImpl.parent != null, "Node already has a parent");
            // Calls a method
            Check.stateCondition(Objects.equals(parent, child), "Cannot have a child as parent");
            // Branch: checks a condition
            if (!children.add((EventNodeImpl<T>) childImpl)) return this; // Couldn't add the child (already present?)
            // Assigns a value
            childImpl.parent = this;
            // Calls a method
            childImpl.invalidateEventsFor(this);
        // End of a block/expression
        }
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<T> removeChild(EventNode<? extends T> child) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final var childImpl = (EventNodeImpl<? extends T>) child;
            // Calls a method
            final boolean result = this.children.remove(childImpl);
            // Branch: checks a condition
            if (!result) return this; // Child not found
            // Assigns a value
            childImpl.parent = null;
            // Calls a method
            childImpl.invalidateEventsFor(this);
        // End of a block/expression
        }
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<T> addListener(EventListener<? extends T> listener) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final var eventType = listener.eventType();
            // Calls a method
            ListenerEntry<T> entry = getEntry(eventType);
            // Calls a method
            entry.listeners.add((EventListener<T>) listener);
            // Calls a method
            invalidateEvent(eventType);
        // End of a block/expression
        }
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<T> removeListener(EventListener<? extends T> listener) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            final var eventType = listener.eventType();
            // Calls a method
            ListenerEntry<T> entry = listenerMap.get(eventType);
            // Branch: checks a condition
            if (entry == null) return this; // There is no listener with such type
            // Branch: checks a condition
            if (entry.listeners.remove(listener)) invalidateEvent(eventType);
        // End of a block/expression
        }
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E extends T, H> EventNode<E> map(H value, EventFilter<E, H> filter) {
        // Code statement
        EventNodeImpl<E> node;
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            node = new EventNodeLazyImpl<>(this, value, filter);
            // Calls a method
            Check.stateCondition(node.parent != null, "Node already has a parent");
            // Calls a method
            Check.stateCondition(Objects.equals(parent, node), "Cannot map to self");
            // Assigns a value
            WeakReference<EventNodeLazyImpl<T>> previousRef = this.mappedNodeCache.putIfAbsent(value,
                    // Creates a new object
                    new WeakReference<>((EventNodeLazyImpl<T>) node));
            // Code statement
            EventNodeImpl<T> previous;
            // Branch: checks a condition
            if (previousRef != null && (previous = previousRef.get()) != null) return (EventNode<E>) previous;
            // Assigns a value
            node.parent = this;
        // End of a block/expression
        }
        // Returns a value to the caller
        return node;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void unmap(Object value) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            Map<Object, WeakReference<EventNodeLazyImpl<T>>> registered = new WeakHashMap<>(registeredMappedNode);
            // Calls a method
            final WeakReference<EventNodeLazyImpl<T>> mappedNodeRef = registered.remove(value);
            // Access to the current/parent object
            this.registeredMappedNode = registered;
            // Code statement
            EventNodeLazyImpl<T> mappedNode;
            // Branch: checks a condition
            if (mappedNodeRef != null && (mappedNode = mappedNodeRef.get()) != null) {
                // Calls a method
                mappedNode.invalidateEventsFor(this);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void register(EventBinding<? extends T> binding) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Loop: repeats a block
            for (var eventType : binding.eventTypes()) {
                // Calls a method
                ListenerEntry<T> entry = getEntry((Class<? extends T>) eventType);
                // Calls a method
                final boolean added = entry.bindingConsumers.add((Consumer<T>) binding.consumer(eventType));
                // Branch: checks a condition
                if (added) invalidateEvent((Class<? extends T>) eventType);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void unregister(EventBinding<? extends T> binding) {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Loop: repeats a block
            for (var eventType : binding.eventTypes()) {
                // Calls a method
                ListenerEntry<T> entry = listenerMap.get(eventType);
                // Branch: checks a condition
                if (entry == null) return;
                // Calls a method
                final boolean removed = entry.bindingConsumers.remove(binding.consumer(eventType));
                // Branch: checks a condition
                if (removed) invalidateEvent((Class<? extends T>) eventType);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Class<T> getEventType() {
        // Returns a value to the caller
        return eventType;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String getName() {
        // Returns a value to the caller
        return name;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getPriority() {
        // Returns a value to the caller
        return priority;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<T> setPriority(int priority) {
        // Access to the current/parent object
        this.priority = priority;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @Nullable EventNode<? super T> getParent() {
        // Calls a method
        Check.stateCondition(ServerFlag.EVENT_NODE_ALLOW_MULTIPLE_PARENTS, "Cannot use getParent when multiple parents are allowed");
        // Returns a value to the caller
        return parent;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return createStringGraph(createGraph());
    // End of a block/expression
    }

    // Start of a method/block
    Graph createGraph() {
        // Start of a method/block
        synchronized (GLOBAL_CHILD_LOCK) {
            // Calls a method
            List<Graph> children = this.children.stream().map(EventNodeImpl::createGraph).toList();
            // Returns a value to the caller
            return new Graph(getName(), getEventType().getSimpleName(), getPriority(), children);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static String createStringGraph(Graph graph) {
        // Calls a method
        StringBuilder buffer = new StringBuilder();
        // Calls a method
        genToStringTree(buffer, "", "", graph);
        // Returns a value to the caller
        return buffer.toString();
    // End of a block/expression
    }

    // Start of a method/block
    private static void genToStringTree(StringBuilder buffer, String prefix, String childrenPrefix, Graph graph) {
        // Calls a method
        buffer.append(prefix);
        // Calls a method
        buffer.append(String.format("%s - EventType: %s - Priority: %d", graph.name(), graph.eventType(), graph.priority()));
        // Calls a method
        buffer.append('\n');
        // Calls a method
        var nextNodes = graph.children();
        // Loop: repeats a block
        for (Iterator<? extends Graph> iterator = nextNodes.iterator(); iterator.hasNext(); ) {
            // Calls a method
            Graph next = iterator.next();
            // Branch: checks a condition
            if (iterator.hasNext()) {
                // Calls a method
                genToStringTree(buffer, childrenPrefix + '├' + '─' + " ", childrenPrefix + '│' + "   ", next);
            // Alternative branch of the condition
            } else {
                // Calls a method
                genToStringTree(buffer, childrenPrefix + '└' + '─' + " ", childrenPrefix + "    ", next);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Graph(String name, String eventType, int priority,
                 // Start of a method/block
                 List<Graph> children) {
        // Start of a method/block
        public Graph {
            // Calls a method
            children = children.stream().sorted(Comparator.comparingInt(Graph::priority)).toList();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    void invalidateEventsFor(EventNodeImpl<? super T> node) {
        // Calls a method
        assert Thread.holdsLock(GLOBAL_CHILD_LOCK);
        // Loop: repeats a block
        for (Class<? extends T> eventType : listenerMap.keySet()) {
            // Calls a method
            node.invalidateEvent(eventType);
        // End of a block/expression
        }
        // TODO bindings?
        // Loop: repeats a block
        for (EventNodeImpl<T> child : children) {
            // Calls a method
            child.invalidateEventsFor(node);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void invalidateEvent(Class<? extends T> eventClass) {
        // Start of a method/block
        forTargetEvents(eventClass, type -> {
            // Assigns a value
            Handle<T> handle = handleMap.computeIfAbsent(type,
                    // Calls a method
                    aClass -> new Handle<>((Class<T>) aClass));
            // Calls a method
            handle.invalidate();
        // End of a block/expression
        });
        // Calls a method
        invalidateRecursiveSuperclasses(eventClass);
        // Assigns a value
        final EventNodeImpl<? super T> parent = this.parent;
        // Branch: checks a condition
        if (parent != null) parent.invalidateEvent(eventClass);
    // End of a block/expression
    }

    // Start of a method/block
    private void invalidateRecursiveSuperclasses(Class<?> eventClass) {
        // Branch: checks a condition
        if (RecursiveEvent.class.isAssignableFrom(eventClass)) {
            // Loop: repeats a block
            for (var cls : this.handleMap.keySet()) {
                // Branch: checks a condition
                if (eventClass.isAssignableFrom(cls)) {
                    // Access to the current/parent object
                    this.handleMap.get(cls).invalidate();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private ListenerEntry<T> getEntry(Class<? extends T> type) {
        // Returns a value to the caller
        return listenerMap.computeIfAbsent(type, aClass -> new ListenerEntry<>());
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean equals(EventNode<?> node, String name, Class<?> eventType) {
        // Returns a value to the caller
        return node.getName().equals(name) && eventType.isAssignableFrom((node.getEventType()));
    // End of a block/expression
    }

    // Start of a method/block
    private static void forTargetEvents(Class<?> type, Consumer<Class<?>> consumer) {
        // Calls a method
        consumer.accept(type);
        // Recursion
        // Branch: checks a condition
        if (RecursiveEvent.class.isAssignableFrom(type)) {
            // Calls a method
            final Class<?> superclass = type.getSuperclass();
            // Branch: checks a condition
            if (superclass != null && RecursiveEvent.class.isAssignableFrom(superclass)) {
                // Calls a method
                forTargetEvents(superclass, consumer);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private static class ListenerEntry<T extends Event> {
        // Calls a method
        final List<EventListener<T>> listeners = new CopyOnWriteArrayList<>();
        // Calls a method
        final Set<Consumer<T>> bindingConsumers = new CopyOnWriteArraySet<>();
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Type declaration (class/interface/enum/record)
    final class Handle<E extends Event> implements ListenerHandle<E> {
        // Code statement
        private final Class<E> eventType;
        // Assigns a value
        private @Nullable Consumer<E> listener = null;
        // Code statement
        private volatile boolean updated;

        // Start of a method/block
        Handle(Class<E> eventType) {
            // Access to the current/parent object
            this.eventType = eventType;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void call(E event) {
            // Code statement
            assert !(event instanceof AsyncEvent) || Thread.currentThread().isVirtual() :
                    // Calls a method
                    "AsyncEvent must be called within a Virtual Thread, got " + Thread.currentThread();
            // Calls a method
            final Consumer<E> listener = updatedListener();
            // Branch: checks a condition
            if (listener == null) return;
            // Exception handling
            try {
                // Calls a method
                listener.accept(event);
            // Start of a method/block
            } catch (Throwable e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean hasListener() {
            // Returns a value to the caller
            return updatedListener() != null;
        // End of a block/expression
        }

        // Start of a method/block
        void invalidate() {
            // Access to the current/parent object
            this.updated = false;
            // Access to the current/parent object
            this.listener = null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Nullable Consumer<E> updatedListener() {
            // Branch: checks a condition
            if (updated) return listener;
            // Start of a method/block
            synchronized (GLOBAL_CHILD_LOCK) {
                // Branch: checks a condition
                if (updated) return listener;
                // Calls a method
                final Consumer<E> listener = createConsumer();
                // Access to the current/parent object
                this.listener = listener;
                // Access to the current/parent object
                this.updated = true;
                // Returns a value to the caller
                return listener;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        private @Nullable Consumer<E> createConsumer() {
            // Calls a method
            var node = (EventNodeImpl<E>) EventNodeImpl.this;
            // Standalone listeners
            // Calls a method
            List<Consumer<E>> listeners = new ArrayList<>();
            // Start of a method/block
            forTargetEvents(eventType, type -> {
                // Calls a method
                final ListenerEntry<E> entry = node.listenerMap.get(type);
                // Branch: checks a condition
                if (entry != null) {
                    // Calls a method
                    final Consumer<E> result = listenersConsumer(entry);
                    // Branch: checks a condition
                    if (result != null) listeners.add(result);
                // End of a block/expression
                }
            // End of a block/expression
            });
            // Calls a method
            final Consumer<E>[] listenersArray = listeners.toArray(Consumer[]::new);
            // Mapped
            // Calls a method
            final Consumer<E> mappedListener = mappedConsumer();
            // Children
            // Assigns a value
            final Consumer<E>[] childrenListeners = node.children.stream()
                    // Code statement
                    .filter(child -> child.eventType.isAssignableFrom(eventType)) // Invalid event type
                    // Code statement
                    .sorted(Comparator.comparing(EventNode::getPriority))
                    // Code statement
                    .map(child -> ((Handle<E>) child.getHandle(eventType)).updatedListener())
                    // Code statement
                    .filter(Objects::nonNull)
                    // Calls a method
                    .toArray(Consumer[]::new);
            // Empty check
            // Assigns a value
            final BiPredicate<E, Object> predicate = node.predicate;
            // Assigns a value
            final EventFilter<E, ?> filter = node.filter;
            // Assigns a value
            final boolean hasPredicate = predicate != null;
            // Assigns a value
            final boolean hasListeners = listenersArray.length > 0;
            // Assigns a value
            final boolean hasMap = mappedListener != null;
            // Assigns a value
            final boolean hasChildren = childrenListeners.length > 0;
            // Branch: checks a condition
            if (!hasListeners && !hasMap && !hasChildren) {
                // No listener
                // Returns a value to the caller
                return null;
            // End of a block/expression
            }
            // Returns a value to the caller
            return e -> {
                // Filtering
                // Branch: checks a condition
                if (hasPredicate) {
                    // Calls a method
                    final Object value = filter.getHandler(e);
                    // Branch: checks a condition
                    if (!predicate.test(e, value)) return;
                // End of a block/expression
                }
                // Normal listeners
                // Branch: checks a condition
                if (hasListeners) {
                    // Loop: repeats a block
                    for (Consumer<E> listener : listenersArray) {
                        // Calls a method
                        listener.accept(e);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Mapped nodes
                // Branch: checks a condition
                if (hasMap) mappedListener.accept(e);
                // Children
                // Branch: checks a condition
                if (hasChildren) {
                    // Loop: repeats a block
                    for (Consumer<E> childHandle : childrenListeners) {
                        // Calls a method
                        childHandle.accept(e);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }

        /**
         * Create a consumer calling all listeners from {@link EventNode#addListener(EventListener)} and
         * {@link EventNode#register(EventBinding)}.
         * <p>
         * Most computation should ideally be done outside the consumers as a one-time cost.
         */
        // Start of a method/block
        private @Nullable Consumer<E> listenersConsumer(ListenerEntry<E> entry) {
            // Calls a method
            final EventListener<E>[] listenersCopy = entry.listeners.toArray(EventListener[]::new);
            // Calls a method
            final Consumer<E>[] bindingsCopy = entry.bindingConsumers.toArray(Consumer[]::new);
            // Assigns a value
            final boolean listenersEmpty = listenersCopy.length == 0;
            // Assigns a value
            final boolean bindingsEmpty = bindingsCopy.length == 0;
            // Branch: checks a condition
            if (listenersEmpty && bindingsEmpty) return null;
            // Branch: checks a condition
            if (bindingsEmpty && listenersCopy.length == 1) {
                // Only one normal listener
                // Assigns a value
                final EventListener<E> listener = listenersCopy[0];
                // Returns a value to the caller
                return e -> callListener(listener, e);
            // End of a block/expression
            }
            // Worse case scenario, try to run everything
            // Returns a value to the caller
            return e -> {
                // Branch: checks a condition
                if (!listenersEmpty) {
                    // Loop: repeats a block
                    for (EventListener<E> listener : listenersCopy) {
                        // Calls a method
                        callListener(listener, e);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Branch: checks a condition
                if (!bindingsEmpty) {
                    // Loop: repeats a block
                    for (Consumer<E> eConsumer : bindingsCopy) {
                        // Calls a method
                        eConsumer.accept(e);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            };
        // End of a block/expression
        }

        /**
         * Create a consumer handling {@link EventNode#map(Object, EventFilter)}.
         * The goal is to limit the amount of map lookup.
         */
        // Start of a method/block
        private @Nullable Consumer<E> mappedConsumer() {
            // Calls a method
            var node = (EventNodeImpl<E>) EventNodeImpl.this;
            // Assigns a value
            final var mappedNodeCache = node.registeredMappedNode;
            // Branch: checks a condition
            if (mappedNodeCache.isEmpty()) return null;
            // Calls a method
            Set<EventFilter<E, ?>> filters = new HashSet<>(mappedNodeCache.size());
            // Calls a method
            Map<Object, WeakReference<Handle<E>>> handlers = new WeakHashMap<>(mappedNodeCache.size());

            // Retrieve all filters used to retrieve potential handlers
            // Loop: repeats a block
            for (var mappedEntry : mappedNodeCache.entrySet()) {
                // Calls a method
                final WeakReference<EventNodeLazyImpl<E>> mappedNodeRef = mappedEntry.getValue();
                // Calls a method
                final EventNodeLazyImpl<E> mappedNode = mappedNodeRef.get();
                // Branch: checks a condition
                if (mappedNode == null) continue; // Weak reference collected
                // Calls a method
                final Handle<E> handle = (Handle<E>) mappedNode.getHandle(eventType);
                // Branch: checks a condition
                if (!handle.hasListener()) continue; // Implicit update
                // Calls a method
                filters.add(mappedNode.filter);
                // Calls a method
                handlers.put(mappedEntry.getKey(), new WeakReference<>(handle));
            // End of a block/expression
            }
            // If at least one mapped node listen to this handle type,
            // loop through them and forward to mapped node if there is a match
            // Branch: checks a condition
            if (filters.isEmpty()) return null;
            // Calls a method
            final EventFilter<E, ?>[] filterList = filters.toArray(EventFilter[]::new);
            // Assigns a value
            final BiConsumer<EventFilter<E, ?>, E> mapper = (filter, event) -> {
                // Calls a method
                final Object handler = filter.castHandler(event);
                // Calls a method
                final WeakReference<Handle<E>> handleRef = handlers.get(handler);
                // Calls a method
                final Handle<E> handle = handleRef != null ? handleRef.get() : null;
                // Branch: checks a condition
                if (handle != null) handle.call(event);
            // End of a block/expression
            };
            // Specialize the consumer depending on the number of filters to avoid looping
            // Returns a value to the caller
            return switch (filterList.length) {
                // Multiple branching (switch/case)
                case 1 -> event -> mapper.accept(filterList[0], event);
                // Multiple branching (switch/case)
                case 2 -> event -> {
                    // Calls a method
                    mapper.accept(filterList[0], event);
                    // Calls a method
                    mapper.accept(filterList[1], event);
                // End of a block/expression
                };
                // Multiple branching (switch/case)
                case 3 -> event -> {
                    // Calls a method
                    mapper.accept(filterList[0], event);
                    // Calls a method
                    mapper.accept(filterList[1], event);
                    // Calls a method
                    mapper.accept(filterList[2], event);
                // End of a block/expression
                };
                // Multiple branching (switch/case)
                default -> event -> {
                    // Loop: repeats a block
                    for (var filter : filterList) {
                        // Calls a method
                        mapper.accept(filter, event);
                    // End of a block/expression
                    }
                // End of a block/expression
                };
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        void callListener(EventListener<E> listener, E event) {
            // Calls a method
            var node = (EventNodeImpl<E>) EventNodeImpl.this;
            // Calls a method
            EventListener.Result result = listener.run(event);
            // Branch: checks a condition
            if (result == EventListener.Result.EXPIRED) {
                // Calls a method
                node.removeListener(listener);
                // Calls a method
                invalidate();
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
