// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.item.ItemEntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityItemMergeEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.EntityTracker;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Cooldown;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.time.temporal.TemporalUnit;

/**
 * Represents an item on the ground.
 */
// Déclaration de type (classe/interface/enum/record)
public class ItemEntity extends Entity {

    /**
     * Used to slow down the merge check delay
     */
    // Appelle une méthode
    private static Duration mergeDelay = Duration.of(10, TimeUnit.SERVER_TICK);

    /**
     * The last time that this item has checked his neighbors for merge
     */
    // Instruction de code
    private long lastMergeCheck;

    // Instruction de code
    private ItemStack itemStack;

    // Affecte une valeur
    private boolean pickable = true;
    // Affecte une valeur
    private boolean mergeable = true;
    // Affecte une valeur
    private float mergeRange = 1;
    // Affecte une valeur
    private boolean previousOnGround = false;

    // Spawn time in System#nanoTime
    // Instruction de code
    private long spawnTime;
    // pickup delay in nanos
    // Instruction de code
    private long pickupDelay;

    // Début d'une méthode/d'un bloc
    public ItemEntity(ItemStack itemStack) {
        // Accès à l'objet courant/parent
        super(EntityType.ITEM);
        // Appelle une méthode
        setItemStack(itemStack);
        // Appelle une méthode
        setBoundingBox(0.25f, 0.25f, 0.25f);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the update option for the merging feature.
     *
     * @return the merge update option
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public static Duration getMergeDelay() {
        // Renvoie une valeur à l'appelant
        return mergeDelay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the merge delay.
     * Can be set to null to entirely remove the delay.
     *
     * @param delay the new merge delay
     */
    // Début d'une méthode/d'un bloc
    public static void setMergeDelay(@Nullable Duration delay) {
        // Affecte une valeur
        ItemEntity.mergeDelay = delay;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update(long time) {
        // Embranchement : vérifie une condition
        if (isMergeable() && isPickable() &&
                // Début d'une méthode/d'un bloc
                (mergeDelay == null || !Cooldown.hasCooldown(time, lastMergeCheck, mergeDelay))) {
            // Accès à l'objet courant/parent
            this.lastMergeCheck = time;

            // Accès à l'objet courant/parent
            this.instance.getEntityTracker().nearbyEntities(position, mergeRange,
                    // Début d'une méthode/d'un bloc
                    EntityTracker.Target.ITEMS, itemEntity -> {
                        // Embranchement : vérifie une condition
                        if (itemEntity == this) return;
                        // Embranchement : vérifie une condition
                        if (!itemEntity.isPickable() || !itemEntity.isMergeable()) return;

                        // Appelle une méthode
                        final ItemStack itemStackEntity = itemEntity.getItemStack();
                        // Appelle une méthode
                        final boolean canStack = itemStack.isSimilar(itemStackEntity);

                        // Embranchement : vérifie une condition
                        if (!canStack) return;
                        // Appelle une méthode
                        final int totalAmount = itemStack.amount() + itemStackEntity.amount();
                        // Embranchement : vérifie une condition
                        if (!MathUtils.isBetween(totalAmount, 0, itemStack.maxStackSize())) return;
                        // Appelle une méthode
                        final ItemStack result = itemStack.withAmount(totalAmount);
                        // Appelle une méthode
                        EntityItemMergeEvent entityItemMergeEvent = new EntityItemMergeEvent(this, itemEntity, result);
                        // Début d'une méthode/d'un bloc
                        EventDispatcher.callCancellable(entityItemMergeEvent, () -> {
                            // Appelle une méthode
                            setItemStack(entityItemMergeEvent.getResult());
                            // Appelle une méthode
                            itemEntity.remove();
                        // Fin d'un bloc/d'une expression
                        });
                    // Fin d'un bloc/d'une expression
                    });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void movementTick() {
        // Accès à l'objet courant/parent
        super.movementTick();

        // Embranchement : vérifie une condition
        if (!previousOnGround && onGround) {
            // Appelle une méthode
            synchronizePosition();
            // Appelle une méthode
            sendPacketToViewers(getVelocityPacket());
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        previousOnGround = onGround;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void spawn() {
        // Accès à l'objet courant/parent
        this.spawnTime = System.nanoTime();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemEntityMeta getEntityMeta() {
        // Renvoie une valeur à l'appelant
        return (ItemEntityMeta) super.getEntityMeta();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item stack on ground.
     *
     * @return the item stack
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the item stack on ground.
     *
     * @param itemStack the item stack
     */
    // Début d'une méthode/d'un bloc
    public void setItemStack(ItemStack itemStack) {
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
        // Appelle une méthode
        getEntityMeta().setItem(itemStack);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the item is currently pickable.
     * <p>
     * {@link #setPickable(boolean)} needs to be true and the delay {@link #getPickupDelay()}
     * to be long gone.
     *
     * @return true if the item is pickable, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isPickable() {
        // Renvoie une valeur à l'appelant
        return pickable && getTimeSinceSpawn() >= pickupDelay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Makes the item pickable.
     *
     * @param pickable true to make the item pickable, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void setPickable(boolean pickable) {
        // Accès à l'objet courant/parent
        this.pickable = pickable;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the item is mergeable.
     *
     * @return true if the entity is mergeable, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean isMergeable() {
        // Renvoie une valeur à l'appelant
        return mergeable;
    // Fin d'un bloc/d'une expression
    }

    /**
     * When set to true, close {@link ItemEntity} will try to merge together as a single entity
     * when their {@link #getItemStack()} is similar and allowed to stack together.
     *
     * @param mergeable should the entity merge with other {@link ItemEntity}
     */
    // Début d'une méthode/d'un bloc
    public void setMergeable(boolean mergeable) {
        // Accès à l'objet courant/parent
        this.mergeable = mergeable;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the merge range.
     *
     * @return the merge range
     */
    // Début d'une méthode/d'un bloc
    public float getMergeRange() {
        // Renvoie une valeur à l'appelant
        return mergeRange;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the merge range.
     *
     * @param mergeRange the merge range
     */
    // Début d'une méthode/d'un bloc
    public void setMergeRange(float mergeRange) {
        // Accès à l'objet courant/parent
        this.mergeRange = mergeRange;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the pickup delay in milliseconds, defined by {@link #setPickupDelay(Duration)}.
     *
     * @return the pickup delay
     */
    // Début d'une méthode/d'un bloc
    public long getPickupDelay() {
        // Renvoie une valeur à l'appelant
        return pickupDelay;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the pickup delay of the ItemEntity.
     *
     * @param delay        the pickup delay
     * @param temporalUnit the unit of the delay
     */
    // Début d'une méthode/d'un bloc
    public void setPickupDelay(long delay, TemporalUnit temporalUnit) {
        // Appelle une méthode
        setPickupDelay(Duration.of(delay, temporalUnit));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the pickup delay of the ItemEntity.
     *
     * @param delay the pickup delay
     */
    // Début d'une méthode/d'un bloc
    public void setPickupDelay(Duration delay) {
        // Accès à l'objet courant/parent
        this.pickupDelay = delay.toMillis();
    // Fin d'un bloc/d'une expression
    }
    
    /**
     * Used to know if the ItemEntity can be picked up.
     *
     * @return the elapsed time in milliseconds since this entity has spawned
     */
    // Début d'une méthode/d'un bloc
    public long getTimeSinceSpawn() {
        // Renvoie une valeur à l'appelant
        return java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - spawnTime);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends ItemEntity> acquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<? extends ItemEntity>) super.acquirable();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
