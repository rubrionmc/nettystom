// Package declaration for this file
package net.minestom.server.event;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.*;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
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
// Type declaration (class/interface/enum/record)
public interface EventFilter<E extends Event, H> {

    // Calls a method
    EventFilter<Event, ?> ALL = from(Event.class, null, null);
    // Calls a method
    EventFilter<EntityEvent, Entity> ENTITY = from(EntityEvent.class, Entity.class, EntityEvent::getEntity);
    // Calls a method
    EventFilter<PlayerEvent, Player> PLAYER = from(PlayerEvent.class, Player.class, PlayerEvent::getPlayer);
    // Calls a method
    EventFilter<ItemEvent, ItemStack> ITEM = from(ItemEvent.class, ItemStack.class, ItemEvent::getItemStack);
    // Calls a method
    EventFilter<InstanceEvent, Instance> INSTANCE = from(InstanceEvent.class, Instance.class, InstanceEvent::getInstance);
    // Calls a method
    EventFilter<InventoryEvent, AbstractInventory> INVENTORY = from(InventoryEvent.class, AbstractInventory.class, InventoryEvent::getInventory);
    // Calls a method
    EventFilter<BlockEvent, Block> BLOCK = from(BlockEvent.class, Block.class, BlockEvent::getBlock);

    // Code statement
    static <E extends Event, H> EventFilter<E, H> from(Class<E> eventType,
                                                       // Annotation for the following element
                                                       @Nullable Class<H> handlerType,
                                                       // Annotation for the following element
                                                       @Nullable Function<E, H> handlerGetter) {
        // Returns a value to the caller
        return new EventFilter<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable H getHandler(E event) {
                // Returns a value to the caller
                return handlerGetter != null ? handlerGetter.apply(event) : null;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Class<E> eventType() {
                // Returns a value to the caller
                return eventType;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable Class<H> handlerType() {
                // Returns a value to the caller
                return handlerType;
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Gets the handler for the given event instance, or null if the event
     * type has no handler.
     *
     * @param event The event instance
     * @return The handler, if it exists for the given event
     */
    // Annotation for the following element
    @Nullable H getHandler(E event);

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    default @Nullable H castHandler(Object event) {
        //noinspection unchecked
        // Returns a value to the caller
        return getHandler((E) event);
    // End of a block/expression
    }

    /**
     * The event type to filter on.
     *
     * @return The event type.
     */
    // Calls a method
    Class<E> eventType();

    /**
     * The type returned by {@link #getHandler(Event)}.
     *
     * @return the handler type, null if not any
     */
    // Annotation for the following element
    @Nullable Class<H> handlerType();
// End of a block/expression
}
