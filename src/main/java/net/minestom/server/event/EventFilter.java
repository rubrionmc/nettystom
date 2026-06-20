// Déclaration du paquet de ce fichier
package net.minestom.server.event;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.*;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.function.Function;

/**
 * Represents a filter for a specific {@link Event} type.
 * <p>
 * The handler represents a "target" of the event. This can be used
 * to create filters for all events of a specific type using information
 * about the target.
 * <p>
 * For example, the target of a {@link PlayerEvent} is a {@link Player} so
 * you could create a player event filter which checks if the target player
 * is in creative mode.
 *
 * @param <E> The event type to filter
 * @param <H> The handler type to filter on.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EventFilter<E extends Event, H> {

    // Appelle une méthode
    EventFilter<Event, ?> ALL = from(Event.class, null, null);
    // Appelle une méthode
    EventFilter<EntityEvent, Entity> ENTITY = from(EntityEvent.class, Entity.class, EntityEvent::getEntity);
    // Appelle une méthode
    EventFilter<PlayerEvent, Player> PLAYER = from(PlayerEvent.class, Player.class, PlayerEvent::getPlayer);
    // Appelle une méthode
    EventFilter<ItemEvent, ItemStack> ITEM = from(ItemEvent.class, ItemStack.class, ItemEvent::getItemStack);
    // Appelle une méthode
    EventFilter<InstanceEvent, Instance> INSTANCE = from(InstanceEvent.class, Instance.class, InstanceEvent::getInstance);
    // Appelle une méthode
    EventFilter<InventoryEvent, AbstractInventory> INVENTORY = from(InventoryEvent.class, AbstractInventory.class, InventoryEvent::getInventory);
    // Appelle une méthode
    EventFilter<BlockEvent, Block> BLOCK = from(BlockEvent.class, Block.class, BlockEvent::getBlock);

    // Instruction de code
    static <E extends Event, H> EventFilter<E, H> from(Class<E> eventType,
                                                       // Annotation pour l'élément suivant
                                                       @Nullable Class<H> handlerType,
                                                       // Annotation pour l'élément suivant
                                                       @Nullable Function<E, H> handlerGetter) {
        // Renvoie une valeur à l'appelant
        return new EventFilter<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable H getHandler(E event) {
                // Renvoie une valeur à l'appelant
                return handlerGetter != null ? handlerGetter.apply(event) : null;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Class<E> eventType() {
                // Renvoie une valeur à l'appelant
                return eventType;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable Class<H> handlerType() {
                // Renvoie une valeur à l'appelant
                return handlerType;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the handler for the given event instance, or null if the event
     * type has no handler.
     *
     * @param event The event instance
     * @return The handler, if it exists for the given event
     */
    // Annotation pour l'élément suivant
    @Nullable H getHandler(E event);

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    default @Nullable H castHandler(Object event) {
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return getHandler((E) event);
    // Fin d'un bloc/d'une expression
    }

    /**
     * The event type to filter on.
     *
     * @return The event type.
     */
    // Appelle une méthode
    Class<E> eventType();

    /**
     * The type returned by {@link #getHandler(Event)}.
     *
     * @return the handler type, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable Class<H> handlerType();
// Fin d'un bloc/d'une expression
}
