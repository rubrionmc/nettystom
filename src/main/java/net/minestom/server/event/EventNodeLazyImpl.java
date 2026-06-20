// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import java.lang.invoke.MethodHandles;
// Import of a required class
import java.lang.invoke.VarHandle;
// Import of a required class
import java.lang.ref.WeakReference;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.WeakHashMap;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
final class EventNodeLazyImpl<E extends Event> extends EventNodeImpl<E> {
    // Code statement
    private static final VarHandle MAPPED;

    // Start of a method/block
    static {
        // Exception handling
        try {
            // Calls a method
            MAPPED = MethodHandles.lookup().findVarHandle(EventNodeLazyImpl.class, "mapped", boolean.class);
        // Start of a method/block
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Throws an exception
            throw new IllegalStateException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Code statement
    private final EventNodeImpl<? super E> holder;
    // Code statement
    private final WeakReference<Object> owner;
    // Annotation for the following element
    @SuppressWarnings("unused")
    // Code statement
    private boolean mapped;

    // Code statement
    EventNodeLazyImpl(EventNodeImpl<? super E> holder,
                      // Start of a method/block
                      Object owner, EventFilter<E, ?> filter) {
        // Access to the current/parent object
        super(owner.toString(), filter, null);
        // Access to the current/parent object
        this.holder = holder;
        // Access to the current/parent object
        this.owner = new WeakReference<>(owner);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<E> addChild(EventNode<? extends E> child) {
        // Calls a method
        ensureMap();
        // Returns a value to the caller
        return super.addChild(child);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<E> addListener(EventListener<? extends E> listener) {
        // Calls a method
        ensureMap();
        // Returns a value to the caller
        return super.addListener(listener);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E1 extends E> EventNode<E> addListener(Class<E1> eventType, Consumer<E1> listener) {
        // Calls a method
        ensureMap();
        // Returns a value to the caller
        return super.addListener(eventType, listener);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <E1 extends E, H> EventNode<E1> map(H value, EventFilter<E1, H> filter) {
        // Calls a method
        final Object owner = retrieveOwner();
        // Branch: checks a condition
        if (owner != value) {
            // Throws an exception
            throw new IllegalArgumentException("Cannot map an object to an already mapped node.");
        // End of a block/expression
        }
        // Returns a value to the caller
        return (EventNode<E1>) this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void register(EventBinding<? extends E> binding) {
        // Calls a method
        ensureMap();
        // Access to the current/parent object
        super.register(binding);
    // End of a block/expression
    }

    // Start of a method/block
    private void ensureMap() {
        // Branch: checks a condition
        if (MAPPED.compareAndSet(this, false, true)) {
            // Start of a method/block
            synchronized (GLOBAL_CHILD_LOCK) {
                // Calls a method
                Map registered = new WeakHashMap<>(this.holder.registeredMappedNode);
                // Assigns a value
                var previous = registered.putIfAbsent(retrieveOwner(),
                        // Creates a new object
                        new WeakReference<>(EventNodeLazyImpl.class.cast(this)));
                // Access to the current/parent object
                this.holder.registeredMappedNode = registered;
                // Branch: checks a condition
                if (previous == null) invalidateEventsFor(holder);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private Object retrieveOwner() {
        // Calls a method
        final Object owner = this.owner.get();
        // Branch: checks a condition
        if (owner == null) {
            // Throws an exception
            throw new IllegalStateException("Node handle is null. Be sure to never cache a local node.");
        // End of a block/expression
        }
        // Returns a value to the caller
        return owner;
    // End of a block/expression
    }
// End of a block/expression
}
