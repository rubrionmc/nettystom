// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.function.BiPredicate;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Predicate;

/**
 * Represents a single node in an event graph.
 * <p>
 * A node may contain any number of children and/or listeners. When an event is called,
 * the node will filter it based on the parameters given at creation and then propagate
 * it down to child nodes and listeners if it passes.
 *
 * @param <T> The event type accepted by this node
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface EventNode<T extends Event> permits EventNodeImpl {

    /**
     * Creates an event node which accepts any event type with no filtering.
     *
     * @param name The name of the node
     * @return An event node with no filtering
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_ -> new", pure = true)
    // Début d'une méthode/d'un bloc
    static EventNode<Event> all(String name) {
        // Renvoie une valeur à l'appelant
        return type(name, EventFilter.ALL);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an event node which accepts any event of the given type. The type is provided
     * by the {@link EventFilter}.
     * <p>
     * For example, you could create an event filter which only accepts player events with the following
     * <p><pre>
     * var playerEventNode = EventNode.type("demo", EventFilter.PLAYER);
     * </pre>
     *
     * @param name   The name of the event node
     * @param filter The event type filter to apply
     * @param <E>    The resulting event type of the node
     * @return A node with just an event type filter
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _ -> new", pure = true)
    // Instruction de code
    static <E extends Event, V> EventNode<E> type(String name,
                                                           // Début d'une méthode/d'un bloc
                                                           EventFilter<E, V> filter) {
        // Renvoie une valeur à l'appelant
        return create(name, filter, null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an event node which accepts any event of the given type which passes
     * the provided condition. The condition is based on the event object itself.
     * <p>
     * For example, you could create an event filter which only accepts player events
     * where the player is in the pos x/z quadrant of the world.
     * <p><pre>{@code
     * var playerInPosXZNode = EventNode.event("abc", EventFilter.PLAYER, event -> {
     *     var position = event.getPlayer().getPosition();
     *     return position.getX() > 0 && position.getZ() > 0;
     * });
     * }</pre>
     *
     * @param name      The name of the event node
     * @param filter    The event type filter to apply
     * @param predicate The event condition
     * @param <E>       The resulting event type of the node
     * @return A node with an event type filter as well as a condition on the event.
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _, _ -> new", pure = true)
    // Instruction de code
    static <E extends Event, V> EventNode<E> event(String name,
                                                            // Instruction de code
                                                            EventFilter<E, V> filter,
                                                            // Début d'une méthode/d'un bloc
                                                            Predicate<E> predicate) {
        // Renvoie une valeur à l'appelant
        return create(name, filter, (e, h) -> predicate.test(e));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an event node which accepts any event of the given type which passes
     * the provided condition. The condition is based on the event object as well as
     * the event handler type defined in the {@link EventFilter}.
     * <p>
     * For example, you could create an event filter which only accepts player events
     * where the player is in the pos x/z quadrant of the world.
     * <p><pre>{@code
     * var playerInPosXZNode = EventNode.type("abc", EventFilter.PLAYER, (event, player) -> {
     *     var position = player.getPosition();
     *     return position.getX() > 0 && position.getZ() > 0;
     * });
     * }</pre>
     *
     * @param name      The name of the event node
     * @param filter    The event type filter to apply
     * @param predicate The event condition
     * @param <E>       The resulting event type of the node
     * @param <V>       The handler type of the event filter
     * @return A node with an event type filter as well as a condition on the event.
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _, _ -> new", pure = true)
    // Instruction de code
    static <E extends Event, V> EventNode<E> type(String name,
                                                           // Instruction de code
                                                           EventFilter<E, V> filter,
                                                           // Début d'une méthode/d'un bloc
                                                           BiPredicate<E, V> predicate) {
        // Renvoie une valeur à l'appelant
        return create(name, filter, predicate);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an event node which accepts any event of the given type which passes
     * the provided condition. The condition is based on the event handler defined
     * by the {@link EventFilter}.
     * <p>
     * For example, you could create an event filter which only accepts player events
     * where the player is in creative mode.
     * <p><pre>
     * var playerIsCreative = EventNode.value("abc", EventFilter.PLAYER, Player::isCreative);
     * </pre>
     *
     * @param name      The name of the event node
     * @param filter    The event type filter to apply
     * @param predicate The event condition
     * @param <E>       The resulting event type of the node
     * @param <V>       The handler type of the event filter
     * @return A node with an event type filter as well as a condition on the event.
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _, _ -> new", pure = true)
    // Instruction de code
    static <E extends Event, V> EventNode<E> value(String name,
                                                            // Instruction de code
                                                            EventFilter<E, V> filter,
                                                            // Début d'une méthode/d'un bloc
                                                            Predicate<V> predicate) {
        // Renvoie une valeur à l'appelant
        return create(name, filter, (e, h) -> predicate.test(h));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an event node which accepts any event of the given type which has a handler who
     * has the given tag.
     * <p>
     * The {@link EventFilter}'s resulting event type must be {@link TagReadable}.
     *
     * @param name   The name of the event node
     * @param filter The event type filter to apply
     * @param tag    The tag which must be contained on the event handler
     * @param <E>    The resulting event type of the node
     * @return A node with an event type filter as well as a handler with the provided tag
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _, _ -> new", pure = true)
    // Instruction de code
    static <E extends Event> EventNode<E> tag(String name,
                                                       // Instruction de code
                                                       EventFilter<E, ? extends TagReadable> filter,
                                                       // Début d'une méthode/d'un bloc
                                                       Tag<?> tag) {
        // Renvoie une valeur à l'appelant
        return create(name, filter, (e, h) -> h.hasTag(tag));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates an event node which accepts any event of the given type which has a handler who
     * has an applicable tag. An applicable tag means that it passes the given condition.
     *
     * @param name     The name of the event node
     * @param filter   The event type filter to apply
     * @param tag      The tag which must be contained on the event handler
     * @param consumer The condition to test against the tag, if it exists.
     * @param <E>      The resulting event type of the node
     * @return A node with an event type filter as well as a handler with the provided tag
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_, _, _, _ -> new", pure = true)
    // Instruction de code
    static <E extends Event, V> EventNode<E> tag(String name,
                                                          // Instruction de code
                                                          EventFilter<E, ? extends TagReadable> filter,
                                                          // Instruction de code
                                                          Tag<V> tag,
                                                          // Début d'une méthode/d'un bloc
                                                          Predicate<@Nullable V> consumer) {
        // Renvoie une valeur à l'appelant
        return create(name, filter, (e, h) -> consumer.test(h.getTag(tag)));
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static <E extends Event, V> EventNode<E> create(String name,
                                                            // Instruction de code
                                                            EventFilter<E, V> filter,
                                                            // Annotation pour l'élément suivant
                                                            @Nullable BiPredicate<E, V> predicate) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return new EventNodeImpl<>(name, filter, predicate != null ? (e, o) -> predicate.test(e, (V) o) : null);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Calls an event starting from this node.
     *
     * @param event the event to call
     */
    // Début d'une méthode/d'un bloc
    default void call(T event) {
        //noinspection unchecked
        // Appelle une méthode
        getHandle((Class<T>) event.getClass()).call(event);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default boolean hasListener(Class<? extends T> type) {
        // Renvoie une valeur à l'appelant
        return getHandle(type).hasListener();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the handle of an event type.
     *
     * @param handleType the handle type
     * @param <E>        the event type
     * @return the handle linked to {@code handleType}
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Appelle une méthode
    <E extends T> ListenerHandle<E> getHandle(Class<E> handleType);

    /**
     * Execute a cancellable event with a callback to execute if the event is successful.
     * Event conditions and propagation is the same as {@link #call(Event)}.
     *
     * @param event           The event to execute
     * @param successCallback A callback if the event is not cancelled
     */
    // Début d'une méthode/d'un bloc
    default void callCancellable(T event, Runnable successCallback) {
        // Appelle une méthode
        call(event);
        // Embranchement : vérifie une condition
        if (!(event instanceof CancellableEvent cancellableEvent) || !cancellableEvent.isCancelled()) {
            // Appelle une méthode
            successCallback.run();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Class<T> getEventType();

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    String getName();

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    int getPriority();

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> this")
    // Appelle une méthode
    EventNode<T> setPriority(int priority);

    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @Nullable EventNode<? super T> getParent();

    /**
     * Returns an unmodifiable view of the children in this node.
     *
     * @see #addChild(EventNode)
     * @see #removeChild(EventNode)
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    Set<EventNode<T>> getChildren();

    /**
     * Locates all child nodes with the given name and event type recursively starting at this node.
     *
     * @param name      The event node name to filter for
     * @param eventType The event node type to filter for
     * @return All matching event nodes
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Appelle une méthode
    <E extends T> List<EventNode<E>> findChildren(String name, Class<E> eventType);

    /**
     * Locates all child nodes with the given name and event type recursively starting at this node.
     *
     * @param name The event name to filter for
     * @return All matching event nodes
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default List<EventNode<T>> findChildren(String name) {
        // Renvoie une valeur à l'appelant
        return findChildren(name, getEventType());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Replaces all children matching the given name and type recursively starting from this node.
     * <p>
     * Node: The callee may not be replaced by this call.
     *
     * @param name      The event name to filter for
     * @param eventType The event node type to filter for
     * @param eventNode The replacement node
     */
    // Appelle une méthode
    <E extends T> void replaceChildren(String name, Class<E> eventType, EventNode<E> eventNode);

    /**
     * Replaces all children matching the given name and type recursively starting from this node.
     * <p>
     * Node: The callee may not be replaced by this call.
     *
     * @param name      The node name to filter for
     * @param eventNode The replacement node
     */
    // Début d'une méthode/d'un bloc
    default void replaceChildren(String name, EventNode<T> eventNode) {
        // Appelle une méthode
        replaceChildren(name, getEventType(), eventNode);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Recursively removes children with the given name and type starting at this node.
     *
     * @param name      The node name to filter for
     * @param eventType The node type to filter for
     */
    // Appelle une méthode
    void removeChildren(String name, Class<? extends T> eventType);

    /**
     * Recursively removes children with the given name starting at this node.
     *
     * @param name The node name to filter for
     */
    // Début d'une méthode/d'un bloc
    default void removeChildren(String name) {
        // Appelle une méthode
        removeChildren(name, getEventType());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Directly adds a child node to this node.
     *
     * @param child The child to add
     * @return this, can be used for chaining
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_ -> this")
    // Appelle une méthode
    EventNode<T> addChild(EventNode<? extends T> child);

    /**
     * Directly removes the given child from this node.
     *
     * @param child The child to remove
     * @return this, can be used for chaining
     */
    // Annotation pour l'élément suivant
    @Contract(value = "_ -> this")
    // Appelle une méthode
    EventNode<T> removeChild(EventNode<? extends T> child);

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> this")
    // Appelle une méthode
    EventNode<T> addListener(EventListener<? extends T> listener);

    // Annotation pour l'élément suivant
    @Contract(value = "_, _ -> this")
    // Début d'une méthode/d'un bloc
    default <E extends T> EventNode<T> addListener(Class<E> eventType, Consumer<E> listener) {
        // Renvoie une valeur à l'appelant
        return addListener(EventListener.of(eventType, listener));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract(value = "_ -> this")
    // Appelle une méthode
    EventNode<T> removeListener(EventListener<? extends T> listener);

    /**
     * Maps a specific object to a node.
     * <p>
     * Be aware that such structure have huge performance penalty as they will
     * always require a map lookup. Use only at last resort.
     *
     * @param value  the mapped value
     * @param filter the filter to use
     * @return the node (which may have already been registered) directly linked to {@code value}
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Appelle une méthode
    <E extends T, H> EventNode<E> map(H value, EventFilter<E, H> filter);

    /**
     * Prevents the node from {@link #map(Object, EventFilter)} to be called.
     *
     * @param value the value to unmap
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Appelle une méthode
    void unmap(Object value);

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Appelle une méthode
    void register(EventBinding<? extends T> binding);

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Appelle une méthode
    void unregister(EventBinding<? extends T> binding);
// Fin d'un bloc/d'une expression
}
