// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.function.BiPredicate;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public sealed interface EventNode<T extends Event> permits EventNodeImpl {

    /**
     * Creates an event node which accepts any event type with no filtering.
     *
     * @param name The name of the node
     * @return An event node with no filtering
     */
    // Annotation for the following element
    @Contract(value = "_ -> new", pure = true)
    // Start of a method/block
    static EventNode<Event> all(String name) {
        // Returns a value to the caller
        return type(name, EventFilter.ALL);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(value = "_, _ -> new", pure = true)
    // Code statement
    static <E extends Event, V> EventNode<E> type(String name,
                                                           // Start of a method/block
                                                           EventFilter<E, V> filter) {
        // Returns a value to the caller
        return create(name, filter, null);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(value = "_, _, _ -> new", pure = true)
    // Code statement
    static <E extends Event, V> EventNode<E> event(String name,
                                                            // Code statement
                                                            EventFilter<E, V> filter,
                                                            // Start of a method/block
                                                            Predicate<E> predicate) {
        // Returns a value to the caller
        return create(name, filter, (e, h) -> predicate.test(e));
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(value = "_, _, _ -> new", pure = true)
    // Code statement
    static <E extends Event, V> EventNode<E> type(String name,
                                                           // Code statement
                                                           EventFilter<E, V> filter,
                                                           // Start of a method/block
                                                           BiPredicate<E, V> predicate) {
        // Returns a value to the caller
        return create(name, filter, predicate);
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(value = "_, _, _ -> new", pure = true)
    // Code statement
    static <E extends Event, V> EventNode<E> value(String name,
                                                            // Code statement
                                                            EventFilter<E, V> filter,
                                                            // Start of a method/block
                                                            Predicate<V> predicate) {
        // Returns a value to the caller
        return create(name, filter, (e, h) -> predicate.test(h));
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(value = "_, _, _ -> new", pure = true)
    // Code statement
    static <E extends Event> EventNode<E> tag(String name,
                                                       // Code statement
                                                       EventFilter<E, ? extends TagReadable> filter,
                                                       // Start of a method/block
                                                       Tag<?> tag) {
        // Returns a value to the caller
        return create(name, filter, (e, h) -> h.hasTag(tag));
    // End of a block/expression
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
    // Annotation for the following element
    @Contract(value = "_, _, _, _ -> new", pure = true)
    // Code statement
    static <E extends Event, V> EventNode<E> tag(String name,
                                                          // Code statement
                                                          EventFilter<E, ? extends TagReadable> filter,
                                                          // Code statement
                                                          Tag<V> tag,
                                                          // Start of a method/block
                                                          Predicate<@Nullable V> consumer) {
        // Returns a value to the caller
        return create(name, filter, (e, h) -> consumer.test(h.getTag(tag)));
    // End of a block/expression
    }

    // Code statement
    private static <E extends Event, V> EventNode<E> create(String name,
                                                            // Code statement
                                                            EventFilter<E, V> filter,
                                                            // Annotation for the following element
                                                            @Nullable BiPredicate<E, V> predicate) {
        //noinspection unchecked
        // Returns a value to the caller
        return new EventNodeImpl<>(name, filter, predicate != null ? (e, o) -> predicate.test(e, (V) o) : null);
    // End of a block/expression
    }

    /**
     * Calls an event starting from this node.
     *
     * @param event the event to call
     */
    // Start of a method/block
    default void call(T event) {
        //noinspection unchecked
        // Calls a method
        getHandle((Class<T>) event.getClass()).call(event);
    // End of a block/expression
    }

    // Start of a method/block
    default boolean hasListener(Class<? extends T> type) {
        // Returns a value to the caller
        return getHandle(type).hasListener();
    // End of a block/expression
    }

    /**
     * Gets the handle of an event type.
     *
     * @param handleType the handle type
     * @param <E>        the event type
     * @return the handle linked to {@code handleType}
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Calls a method
    <E extends T> ListenerHandle<E> getHandle(Class<E> handleType);

    /**
     * Execute a cancellable event with a callback to execute if the event is successful.
     * Event conditions and propagation is the same as {@link #call(Event)}.
     *
     * @param event           The event to execute
     * @param successCallback A callback if the event is not cancelled
     */
    // Start of a method/block
    default void callCancellable(T event, Runnable successCallback) {
        // Calls a method
        call(event);
        // Branch: checks a condition
        if (!(event instanceof CancellableEvent cancellableEvent) || !cancellableEvent.isCancelled()) {
            // Calls a method
            successCallback.run();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Class<T> getEventType();

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    String getName();

    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    int getPriority();

    // Annotation for the following element
    @Contract(value = "_ -> this")
    // Calls a method
    EventNode<T> setPriority(int priority);

    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @Nullable EventNode<? super T> getParent();

    /**
     * Returns an unmodifiable view of the children in this node.
     *
     * @see #addChild(EventNode)
     * @see #removeChild(EventNode)
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    Set<EventNode<T>> getChildren();

    /**
     * Locates all child nodes with the given name and event type recursively starting at this node.
     *
     * @param name      The event node name to filter for
     * @param eventType The event node type to filter for
     * @return All matching event nodes
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Calls a method
    <E extends T> List<EventNode<E>> findChildren(String name, Class<E> eventType);

    /**
     * Locates all child nodes with the given name and event type recursively starting at this node.
     *
     * @param name The event name to filter for
     * @return All matching event nodes
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default List<EventNode<T>> findChildren(String name) {
        // Returns a value to the caller
        return findChildren(name, getEventType());
    // End of a block/expression
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
    // Calls a method
    <E extends T> void replaceChildren(String name, Class<E> eventType, EventNode<E> eventNode);

    /**
     * Replaces all children matching the given name and type recursively starting from this node.
     * <p>
     * Node: The callee may not be replaced by this call.
     *
     * @param name      The node name to filter for
     * @param eventNode The replacement node
     */
    // Start of a method/block
    default void replaceChildren(String name, EventNode<T> eventNode) {
        // Calls a method
        replaceChildren(name, getEventType(), eventNode);
    // End of a block/expression
    }

    /**
     * Recursively removes children with the given name and type starting at this node.
     *
     * @param name      The node name to filter for
     * @param eventType The node type to filter for
     */
    // Calls a method
    void removeChildren(String name, Class<? extends T> eventType);

    /**
     * Recursively removes children with the given name starting at this node.
     *
     * @param name The node name to filter for
     */
    // Start of a method/block
    default void removeChildren(String name) {
        // Calls a method
        removeChildren(name, getEventType());
    // End of a block/expression
    }

    /**
     * Directly adds a child node to this node.
     *
     * @param child The child to add
     * @return this, can be used for chaining
     */
    // Annotation for the following element
    @Contract(value = "_ -> this")
    // Calls a method
    EventNode<T> addChild(EventNode<? extends T> child);

    /**
     * Directly removes the given child from this node.
     *
     * @param child The child to remove
     * @return this, can be used for chaining
     */
    // Annotation for the following element
    @Contract(value = "_ -> this")
    // Calls a method
    EventNode<T> removeChild(EventNode<? extends T> child);

    // Annotation for the following element
    @Contract(value = "_ -> this")
    // Calls a method
    EventNode<T> addListener(EventListener<? extends T> listener);

    // Annotation for the following element
    @Contract(value = "_, _ -> this")
    // Start of a method/block
    default <E extends T> EventNode<T> addListener(Class<E> eventType, Consumer<E> listener) {
        // Returns a value to the caller
        return addListener(EventListener.of(eventType, listener));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract(value = "_ -> this")
    // Calls a method
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
    // Annotation for the following element
    @ApiStatus.Experimental
    // Calls a method
    <E extends T, H> EventNode<E> map(H value, EventFilter<E, H> filter);

    /**
     * Prevents the node from {@link #map(Object, EventFilter)} to be called.
     *
     * @param value the value to unmap
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Calls a method
    void unmap(Object value);

    // Annotation for the following element
    @ApiStatus.Experimental
    // Calls a method
    void register(EventBinding<? extends T> binding);

    // Annotation for the following element
    @ApiStatus.Experimental
    // Calls a method
    void unregister(EventBinding<? extends T> binding);
// End of a block/expression
}
