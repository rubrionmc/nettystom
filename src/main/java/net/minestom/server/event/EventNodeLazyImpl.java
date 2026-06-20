// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import java.lang.invoke.MethodHandles;
// Import d'une classe nécessaire
import java.lang.invoke.VarHandle;
// Import d'une classe nécessaire
import java.lang.ref.WeakReference;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.WeakHashMap;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
final class EventNodeLazyImpl<E extends Event> extends EventNodeImpl<E> {
    // Instruction de code
    private static final VarHandle MAPPED;

    // Début d'une méthode/d'un bloc
    static {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            MAPPED = MethodHandles.lookup().findVarHandle(EventNodeLazyImpl.class, "mapped", boolean.class);
        // Début d'une méthode/d'un bloc
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Lève une exception
            throw new IllegalStateException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private final EventNodeImpl<? super E> holder;
    // Instruction de code
    private final WeakReference<Object> owner;
    // Annotation pour l'élément suivant
    @SuppressWarnings("unused")
    // Instruction de code
    private boolean mapped;

    // Instruction de code
    EventNodeLazyImpl(EventNodeImpl<? super E> holder,
                      // Début d'une méthode/d'un bloc
                      Object owner, EventFilter<E, ?> filter) {
        // Accès à l'objet courant/parent
        super(owner.toString(), filter, null);
        // Accès à l'objet courant/parent
        this.holder = holder;
        // Accès à l'objet courant/parent
        this.owner = new WeakReference<>(owner);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<E> addChild(EventNode<? extends E> child) {
        // Appelle une méthode
        ensureMap();
        // Renvoie une valeur à l'appelant
        return super.addChild(child);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<E> addListener(EventListener<? extends E> listener) {
        // Appelle une méthode
        ensureMap();
        // Renvoie une valeur à l'appelant
        return super.addListener(listener);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E1 extends E> EventNode<E> addListener(Class<E1> eventType, Consumer<E1> listener) {
        // Appelle une méthode
        ensureMap();
        // Renvoie une valeur à l'appelant
        return super.addListener(eventType, listener);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <E1 extends E, H> EventNode<E1> map(H value, EventFilter<E1, H> filter) {
        // Appelle une méthode
        final Object owner = retrieveOwner();
        // Embranchement : vérifie une condition
        if (owner != value) {
            // Lève une exception
            throw new IllegalArgumentException("Cannot map an object to an already mapped node.");
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return (EventNode<E1>) this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void register(EventBinding<? extends E> binding) {
        // Appelle une méthode
        ensureMap();
        // Accès à l'objet courant/parent
        super.register(binding);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void ensureMap() {
        // Embranchement : vérifie une condition
        if (MAPPED.compareAndSet(this, false, true)) {
            // Début d'une méthode/d'un bloc
            synchronized (GLOBAL_CHILD_LOCK) {
                // Appelle une méthode
                Map registered = new WeakHashMap<>(this.holder.registeredMappedNode);
                // Affecte une valeur
                var previous = registered.putIfAbsent(retrieveOwner(),
                        // Crée un nouvel objet
                        new WeakReference<>(EventNodeLazyImpl.class.cast(this)));
                // Accès à l'objet courant/parent
                this.holder.registeredMappedNode = registered;
                // Embranchement : vérifie une condition
                if (previous == null) invalidateEventsFor(holder);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Object retrieveOwner() {
        // Appelle une méthode
        final Object owner = this.owner.get();
        // Embranchement : vérifie une condition
        if (owner == null) {
            // Lève une exception
            throw new IllegalStateException("Node handle is null. Be sure to never cache a local node.");
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return owner;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
