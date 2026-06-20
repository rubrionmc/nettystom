// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.minestom.server.entity.metadata.item.ItemEntityMeta;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.entity.EntityItemMergeEvent;
// Import of a required class
import net.minestom.server.instance.EntityTracker;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.time.Cooldown;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;

/**
 * Represents an item on the ground.
 */
// Type declaration (class/interface/enum/record)
public class ItemEntity extends Entity {

    /**
     * Used to slow down the merge check delay
     */
    // Calls a method
    private static Duration mergeDelay = Duration.of(10, TimeUnit.SERVER_TICK);

    /**
     * The last time that this item has checked his neighbors for merge
     */
    // Code statement
    private long lastMergeCheck;

    // Code statement
    private ItemStack itemStack;

    // Assigns a value
    private boolean pickable = true;
    // Assigns a value
    private boolean mergeable = true;
    // Assigns a value
    private float mergeRange = 1;
    // Assigns a value
    private boolean previousOnGround = false;

    // Spawn time in System#nanoTime
    // Code statement
    private long spawnTime;
    // pickup delay in nanos
    // Code statement
    private long pickupDelay;

    // Start of a method/block
    public ItemEntity(ItemStack itemStack) {
        // Access to the current/parent object
        super(EntityType.ITEM);
        // Calls a method
        setItemStack(itemStack);
        // Calls a method
        setBoundingBox(0.25f, 0.25f, 0.25f);
    // End of a block/expression
    }

    /**
     * Gets the update option for the merging feature.
     *
     * @return the merge update option
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public static Duration getMergeDelay() {
        // Returns a value to the caller
        return mergeDelay;
    // End of a block/expression
    }

    /**
     * Changes the merge delay.
     * Can be set to null to entirely remove the delay.
     *
     * @param delay the new merge delay
     */
    // Start of a method/block
    public static void setMergeDelay(@Nullable Duration delay) {
        // Assigns a value
        ItemEntity.mergeDelay = delay;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update(long time) {
        // Branch: checks a condition
        if (isMergeable() && isPickable() &&
                // Start of a method/block
                (mergeDelay == null || !Cooldown.hasCooldown(time, lastMergeCheck, mergeDelay))) {
            // Access to the current/parent object
            this.lastMergeCheck = time;

            // Access to the current/parent object
            this.instance.getEntityTracker().nearbyEntities(position, mergeRange,
                    // Start of a method/block
                    EntityTracker.Target.ITEMS, itemEntity -> {
                        // Branch: checks a condition
                        if (itemEntity == this) return;
                        // Branch: checks a condition
                        if (!itemEntity.isPickable() || !itemEntity.isMergeable()) return;

                        // Calls a method
                        final ItemStack itemStackEntity = itemEntity.getItemStack();
                        // Calls a method
                        final boolean canStack = itemStack.isSimilar(itemStackEntity);

                        // Branch: checks a condition
                        if (!canStack) return;
                        // Calls a method
                        final int totalAmount = itemStack.amount() + itemStackEntity.amount();
                        // Branch: checks a condition
                        if (!MathUtils.isBetween(totalAmount, 0, itemStack.maxStackSize())) return;
                        // Calls a method
                        final ItemStack result = itemStack.withAmount(totalAmount);
                        // Calls a method
                        EntityItemMergeEvent entityItemMergeEvent = new EntityItemMergeEvent(this, itemEntity, result);
                        // Start of a method/block
                        EventDispatcher.callCancellable(entityItemMergeEvent, () -> {
                            // Calls a method
                            setItemStack(entityItemMergeEvent.getResult());
                            // Calls a method
                            itemEntity.remove();
                        // End of a block/expression
                        });
                    // End of a block/expression
                    });
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void movementTick() {
        // Access to the current/parent object
        super.movementTick();

        // Branch: checks a condition
        if (!previousOnGround && onGround) {
            // Calls a method
            synchronizePosition();
            // Calls a method
            sendPacketToViewers(getVelocityPacket());
        // End of a block/expression
        }

        // Assigns a value
        previousOnGround = onGround;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void spawn() {
        // Access to the current/parent object
        this.spawnTime = System.nanoTime();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemEntityMeta getEntityMeta() {
        // Returns a value to the caller
        return (ItemEntityMeta) super.getEntityMeta();
    // End of a block/expression
    }

    /**
     * Gets the item stack on ground.
     *
     * @return the item stack
     */
    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return itemStack;
    // End of a block/expression
    }

    /**
     * Changes the item stack on ground.
     *
     * @param itemStack the item stack
     */
    // Start of a method/block
    public void setItemStack(ItemStack itemStack) {
        // Access to the current/parent object
        this.itemStack = itemStack;
        // Calls a method
        getEntityMeta().setItem(itemStack);
    // End of a block/expression
    }

    /**
     * Gets if the item is currently pickable.
     * <p>
     * {@link #setPickable(boolean)} needs to be true and the delay {@link #getPickupDelay()}
     * to be long gone.
     *
     * @return true if the item is pickable, false otherwise
     */
    // Start of a method/block
    public boolean isPickable() {
        // Returns a value to the caller
        return pickable && getTimeSinceSpawn() >= pickupDelay;
    // End of a block/expression
    }

    /**
     * Makes the item pickable.
     *
     * @param pickable true to make the item pickable, false otherwise
     */
    // Start of a method/block
    public void setPickable(boolean pickable) {
        // Access to the current/parent object
        this.pickable = pickable;
    // End of a block/expression
    }

    /**
     * Gets if the item is mergeable.
     *
     * @return true if the entity is mergeable, false otherwise
     */
    // Start of a method/block
    public boolean isMergeable() {
        // Returns a value to the caller
        return mergeable;
    // End of a block/expression
    }

    /**
     * When set to true, close {@link ItemEntity} will try to merge together as a single entity
     * when their {@link #getItemStack()} is similar and allowed to stack together.
     *
     * @param mergeable should the entity merge with other {@link ItemEntity}
     */
    // Start of a method/block
    public void setMergeable(boolean mergeable) {
        // Access to the current/parent object
        this.mergeable = mergeable;
    // End of a block/expression
    }

    /**
     * Gets the merge range.
     *
     * @return the merge range
     */
    // Start of a method/block
    public float getMergeRange() {
        // Returns a value to the caller
        return mergeRange;
    // End of a block/expression
    }

    /**
     * Changes the merge range.
     *
     * @param mergeRange the merge range
     */
    // Start of a method/block
    public void setMergeRange(float mergeRange) {
        // Access to the current/parent object
        this.mergeRange = mergeRange;
    // End of a block/expression
    }

    /**
     * Gets the pickup delay in milliseconds, defined by {@link #setPickupDelay(Duration)}.
     *
     * @return the pickup delay
     */
    // Start of a method/block
    public long getPickupDelay() {
        // Returns a value to the caller
        return pickupDelay;
    // End of a block/expression
    }

    /**
     * Sets the pickup delay of the ItemEntity.
     *
     * @param delay        the pickup delay
     * @param temporalUnit the unit of the delay
     */
    // Start of a method/block
    public void setPickupDelay(long delay, TemporalUnit temporalUnit) {
        // Calls a method
        setPickupDelay(Duration.of(delay, temporalUnit));
    // End of a block/expression
    }

    /**
     * Sets the pickup delay of the ItemEntity.
     *
     * @param delay the pickup delay
     */
    // Start of a method/block
    public void setPickupDelay(Duration delay) {
        // Access to the current/parent object
        this.pickupDelay = delay.toMillis();
    // End of a block/expression
    }
    
    /**
     * Used to know if the ItemEntity can be picked up.
     *
     * @return the elapsed time in milliseconds since this entity has spawned
     */
    // Start of a method/block
    public long getTimeSinceSpawn() {
        // Returns a value to the caller
        return java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - spawnTime);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends ItemEntity> acquirable() {
        // Returns a value to the caller
        return (Acquirable<? extends ItemEntity>) super.acquirable();
    // End of a block/expression
    }
// End of a block/expression
}
