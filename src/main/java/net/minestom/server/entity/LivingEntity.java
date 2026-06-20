// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound;
// Import d'une classe nécessaire
import net.kyori.adventure.sound.Sound.Source;
// Import d'une classe nécessaire
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import d'une classe nécessaire
import net.minestom.server.collision.BoundingBox;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.Attribute;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeInstance;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeModifier;
// Import d'une classe nécessaire
import net.minestom.server.entity.attribute.AttributeOperation;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.Damage;
// Import d'une classe nécessaire
import net.minestom.server.entity.damage.DamageType;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityDamageEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityDeathEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntityFireExtinguishEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.entity.EntitySetFireEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.EntityEquipEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.item.PickupItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.EntityTracker;
// Import d'une classe nécessaire
import net.minestom.server.inventory.EquipmentHandler;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.component.AttributeList;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.LazyPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.scoreboard.Team;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.thread.Acquirable;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockIterator;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.Cooldown;
// Import d'une classe nécessaire
import net.minestom.server.utils.time.TimeUnit;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnmodifiableView;

// Import d'une classe nécessaire
import java.time.Duration;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;

// Déclaration de type (classe/interface/enum/record)
public class LivingEntity extends Entity implements EquipmentHandler {

    // Appelle une méthode
    private static final AttributeModifier SPRINTING_SPEED_MODIFIER = new AttributeModifier(Key.key("sprinting"), 0.3, AttributeOperation.ADD_MULTIPLIED_TOTAL);

    /**
     * IDs of modifiers that are protected from removal by methods like {@link AttributeInstance#clearModifiers()}.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    public static final Set<Key> PROTECTED_MODIFIERS = Set.of(SPRINTING_SPEED_MODIFIER.id());

    // ItemStack pickup
    // Instruction de code
    protected boolean canPickupItem;
    // Appelle une méthode
    protected Cooldown itemPickupCooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Instruction de code
    protected boolean isDead;

    // Instruction de code
    protected Damage lastDamage;

    // Bounding box used for items' pickup (see LivingEntity#setBoundingBox)
    // Instruction de code
    protected BoundingBox expandedBoundingBox;

    // Appelle une méthode
    private final Map<String, AttributeInstance> attributeModifiers = new ConcurrentHashMap<>();
    // Instruction de code
    private final Collection<AttributeInstance> unmodifiableModifiers =
            // Appelle une méthode
            Collections.unmodifiableCollection(attributeModifiers.values());

    // Abilities
    // Instruction de code
    protected boolean invulnerable;

    /**
     * Ticks until this entity must be extinguished
     */
    // Instruction de code
    private int remainingFireTicks;

    // Instruction de code
    private Team team;

    // Instruction de code
    private int arrowCount;
    // Affecte une valeur
    private float health = 1F;

    // Equipments
    // Affecte une valeur
    private ItemStack mainHandItem = ItemStack.AIR;
    // Affecte une valeur
    private ItemStack offHandItem = ItemStack.AIR;

    // Affecte une valeur
    private ItemStack helmet = ItemStack.AIR;
    // Affecte une valeur
    private ItemStack chestplate = ItemStack.AIR;
    // Affecte une valeur
    private ItemStack leggings = ItemStack.AIR;
    // Affecte une valeur
    private ItemStack boots = ItemStack.AIR;
    // Affecte une valeur
    private ItemStack bodyEquipment = ItemStack.AIR;
    // Affecte une valeur
    private ItemStack saddleEquipment = ItemStack.AIR;

    /**
     * Constructor which allows to specify an UUID. Only use if you know what you are doing!
     */
    // Début d'une méthode/d'un bloc
    public LivingEntity(EntityType entityType, UUID uuid) {
        // Accès à l'objet courant/parent
        super(entityType, uuid);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public LivingEntity(EntityType entityType) {
        // Appelle une méthode
        this(entityType, UUID.randomUUID());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setSprinting(boolean sprinting) {
        // Accès à l'objet courant/parent
        super.setSprinting(sprinting);

        // We must set the sprinting attribute serverside because when we resend modifiers it overwrites what
        // the client has, meaning if they are sprinting and we send no modifiers, they will no longer be
        // getting the speed boost of sprinting.
        // Appelle une méthode
        final AttributeInstance speed = getAttribute(Attribute.MOVEMENT_SPEED);
        // Embranchement : vérifie une condition
        if (sprinting) speed.addModifier(SPRINTING_SPEED_MODIFIER);
        // Branche alternative de la condition
        else speed.removeModifier(SPRINTING_SPEED_MODIFIER);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getEquipment(EquipmentSlot slot) {
        // Renvoie une valeur à l'appelant
        return switch (slot) {
            // Embranchement multiple (switch/case)
            case MAIN_HAND -> mainHandItem;
            // Embranchement multiple (switch/case)
            case OFF_HAND -> offHandItem;
            // Embranchement multiple (switch/case)
            case BOOTS -> boots;
            // Embranchement multiple (switch/case)
            case LEGGINGS -> leggings;
            // Embranchement multiple (switch/case)
            case CHESTPLATE -> chestplate;
            // Embranchement multiple (switch/case)
            case HELMET -> helmet;
            // Embranchement multiple (switch/case)
            case BODY -> bodyEquipment;
            // Embranchement multiple (switch/case)
            case SADDLE -> saddleEquipment;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setEquipment(EquipmentSlot slot, ItemStack itemStack) {
        // Appelle une méthode
        ItemStack oldItem = getEquipment(slot);
        // Appelle une méthode
        ItemStack newItem = slotChangeEvent(itemStack, slot);

        // Embranchement multiple (switch/case)
        switch (slot) {
            // Embranchement multiple (switch/case)
            case MAIN_HAND -> mainHandItem = newItem;
            // Embranchement multiple (switch/case)
            case OFF_HAND -> offHandItem = newItem;
            // Embranchement multiple (switch/case)
            case BOOTS -> boots = newItem;
            // Embranchement multiple (switch/case)
            case LEGGINGS -> leggings = newItem;
            // Embranchement multiple (switch/case)
            case CHESTPLATE -> chestplate = newItem;
            // Embranchement multiple (switch/case)
            case HELMET -> helmet = newItem;
            // Embranchement multiple (switch/case)
            case BODY -> bodyEquipment = newItem;
            // Embranchement multiple (switch/case)
            case SADDLE -> saddleEquipment = newItem;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        syncEquipment(slot);
        // Appelle une méthode
        updateEquipmentAttributes(oldItem, newItem, slot);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private ItemStack slotChangeEvent(ItemStack itemStack, EquipmentSlot slot) {
        // Appelle une méthode
        EntityEquipEvent entityEquipEvent = new EntityEquipEvent(this, itemStack, slot);
        // Appelle une méthode
        EventDispatcher.call(entityEquipEvent);
        // Renvoie une valeur à l'appelant
        return entityEquipEvent.getEquippedItem();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Updates the current attributes of the living entity based on
     *
     * @param oldItemStack The ItemStack that has been removed, modifiers on this stack will be removed from the entity
     * @param newItemStack The ItemStack that has been added, modifiers on this stack will be added to the entity
     * @param slot         The slot that changed, this will determine what modifiers are actually changed
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void updateEquipmentAttributes(ItemStack oldItemStack, ItemStack newItemStack, EquipmentSlot slot) {
        // Appelle une méthode
        AttributeList oldAttributes = oldItemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        // Remove old attributes
        // Embranchement : vérifie une condition
        if (oldAttributes != null) {
            // Boucle : répète un bloc
            for (AttributeList.Modifier modifier : oldAttributes.modifiers()) {
                // If the modifier currently modifies the slot we are updating
                // Embranchement : vérifie une condition
                if (modifier.slot().contains(slot)) {
                    // Appelle une méthode
                    AttributeInstance attributeInstance = getAttribute(modifier.attribute());
                    // Appelle une méthode
                    attributeInstance.removeModifier(modifier.modifier().id());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        AttributeList newAttributes = newItemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        // Add new attributes
        // Embranchement : vérifie une condition
        if (newAttributes != null) {
            // Boucle : répète un bloc
            for (AttributeList.Modifier modifier : newAttributes.modifiers()) {
                // If the modifier currently modifies the slot we are updating
                // Embranchement : vérifie une condition
                if (modifier.slot().contains(slot)) {
                    // Appelle une méthode
                    AttributeInstance attributeInstance = getAttribute(modifier.attribute());
                    // Appelle une méthode
                    attributeInstance.addModifier(modifier.modifier());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update(long time) {
        // Fire
        // Embranchement : vérifie une condition
        if (remainingFireTicks > 0 && --remainingFireTicks == 0) {
            // Appelle une méthode
            EventDispatcher.callCancellable(new EntityFireExtinguishEvent(this, true), () -> entityMeta.setOnFire(false));
        // Fin d'un bloc/d'une expression
        }

        // Items picking
        // Embranchement : vérifie une condition
        if (canPickupItem() && itemPickupCooldown.isReady(time)) {
            // Appelle une méthode
            itemPickupCooldown.refreshLastUpdate(time);
            // Accès à l'objet courant/parent
            this.instance.getEntityTracker().nearbyEntities(position, expandedBoundingBox.width(),
                    // Début d'une méthode/d'un bloc
                    EntityTracker.Target.ITEMS, itemEntity -> {
                        // Embranchement : vérifie une condition
                        if (this instanceof Player player && !itemEntity.isViewer(player)) return;
                        // Embranchement : vérifie une condition
                        if (!itemEntity.isPickable()) return;
                        // Embranchement : vérifie une condition
                        if (!expandedBoundingBox.intersectEntity(position, itemEntity)) return;
                        // Appelle une méthode
                        final PickupItemEvent pickupItemEvent = new PickupItemEvent(this, itemEntity);
                        // Début d'une méthode/d'un bloc
                        EventDispatcher.callCancellable(pickupItemEvent, () -> {
                            // Appelle une méthode
                            final ItemStack item = itemEntity.getItemStack();
                            // Appelle une méthode
                            sendPacketToViewersAndSelf(new CollectItemPacket(itemEntity.getEntityId(), getEntityId(), item.amount()));
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

    /**
     * Gets the amount of arrows in the entity.
     *
     * @return the arrow count
     */
    // Début d'une méthode/d'un bloc
    public int getArrowCount() {
        // Renvoie une valeur à l'appelant
        return this.arrowCount;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the amount of arrow stuck in the entity.
     *
     * @param arrowCount the arrow count
     */
    // Début d'une méthode/d'un bloc
    public void setArrowCount(int arrowCount) {
        // Accès à l'objet courant/parent
        this.arrowCount = arrowCount;
        // Appelle une méthode
        LivingEntityMeta meta = getLivingEntityMeta();
        // Embranchement : vérifie une condition
        if (meta != null) {
            // Appelle une méthode
            meta.setArrowCount(arrowCount);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is invulnerable.
     *
     * @return true if the entity is invulnerable
     */
    // Début d'une méthode/d'un bloc
    public boolean isInvulnerable() {
        // Renvoie une valeur à l'appelant
        return invulnerable;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Makes the entity vulnerable or invulnerable.
     *
     * @param invulnerable should the entity be invulnerable
     */
    // Début d'une méthode/d'un bloc
    public void setInvulnerable(boolean invulnerable) {
        // Accès à l'objet courant/parent
        this.invulnerable = invulnerable;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Kills the entity, trigger the {@link EntityDeathEvent} event.
     */
    // Début d'une méthode/d'un bloc
    public void kill() {
        // Instruction de code
        refreshIsDead(true); // So the entity isn't killed over and over again
        // Instruction de code
        triggerStatus((byte) EntityStatuses.LivingEntity.PLAY_DEATH_SOUND); // Start death animation status
        // Appelle une méthode
        setPose(EntityPose.DYING);
        // Appelle une méthode
        setHealth(0);

        // Reset velocity
        // Accès à l'objet courant/parent
        this.velocity = Vec.ZERO;

        // Remove passengers if any
        // Embranchement : vérifie une condition
        if (hasPassenger()) {
            // Appelle une méthode
            getPassengers().forEach(this::removePassenger);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        EntityDeathEvent entityDeathEvent = new EntityDeathEvent(this);
        // Appelle une méthode
        EventDispatcher.call(entityDeathEvent);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the amount of ticks this entity is on fire for.
     *
     * @return the remaining duration of fire in ticks, 0 if not on fire
     */
    // Début d'une méthode/d'un bloc
    public int getFireTicks() {
        // Renvoie une valeur à l'appelant
        return remainingFireTicks;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets this entity on fire for the given ticks.
     *
     * @param ticks duration of fire in ticks
     */
    // Début d'une méthode/d'un bloc
    public void setFireTicks(int ticks) {
        // Appelle une méthode
        int fireTicks = Math.max(0, ticks);
        // Embranchement : vérifie une condition
        if (fireTicks > 0) {
            // Appelle une méthode
            EntitySetFireEvent entitySetFireEvent = new EntitySetFireEvent(this, ticks);
            // Appelle une méthode
            EventDispatcher.call(entitySetFireEvent);
            // Embranchement : vérifie une condition
            if (entitySetFireEvent.isCancelled()) return;

            // Appelle une méthode
            fireTicks = Math.max(0, entitySetFireEvent.getFireTicks());
            // Embranchement : vérifie une condition
            if (fireTicks > 0) {
                // Affecte une valeur
                remainingFireTicks = fireTicks;
                // Appelle une méthode
                entityMeta.setOnFire(true);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (remainingFireTicks != 0) {
            // Appelle une méthode
            EntityFireExtinguishEvent entityFireExtinguishEvent = new EntityFireExtinguishEvent(this, false);
            // Appelle une méthode
            EventDispatcher.callCancellable(entityFireExtinguishEvent, () -> entityMeta.setOnFire(false));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        remainingFireTicks = fireTicks;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean damage(RegistryKey<DamageType> type, float amount) {
        // Renvoie une valeur à l'appelant
        return damage(new Damage(type, null, null, null, amount));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Damages the entity by a value, the type of the damage also has to be specified.
     *
     * @param damage the damage to be applied
     * @return true if damage has been applied, false if it didn't
     */
    // Début d'une méthode/d'un bloc
    public boolean damage(Damage damage) {
        // Embranchement : vérifie une condition
        if (isDead())
            // Renvoie une valeur à l'appelant
            return false;
        // Embranchement : vérifie une condition
        if (isImmune(damage.getType())) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(this, damage, damage.getSound(this));
        // Début d'une méthode/d'un bloc
        EventDispatcher.callCancellable(entityDamageEvent, () -> {
            // Set the last damage type since the event is not cancelled
            // Accès à l'objet courant/parent
            this.lastDamage = entityDamageEvent.getDamage();

            // Appelle une méthode
            float remainingDamage = entityDamageEvent.getDamage().getAmount();

            // Embranchement : vérifie une condition
            if (entityDamageEvent.shouldAnimate()) {
                // Instruction de code
                sendPacketToViewersAndSelf(new DamageEventPacket(
                        // Instruction de code
                        getEntityId(), damage.getTypeId(),
                        // Instruction de code
                        damage.getAttacker() == null ? 0 : damage.getAttacker().getEntityId() + 1,
                        // Instruction de code
                        damage.getSource() == null ? 0 : damage.getSource().getEntityId() + 1,
                        // Instruction de code
                        damage.getSourcePosition()
                // Instruction de code
                ));
            // Fin d'un bloc/d'une expression
            }

            // Additional hearts support
            // Embranchement : vérifie une condition
            if (this instanceof Player player) {
                // Appelle une méthode
                final float additionalHearts = player.getAdditionalHearts();
                // Embranchement : vérifie une condition
                if (additionalHearts > 0) {
                    // Embranchement : vérifie une condition
                    if (remainingDamage > additionalHearts) {
                        // Instruction de code
                        remainingDamage -= additionalHearts;
                        // Appelle une méthode
                        player.setAdditionalHearts(0);
                    // Branche alternative de la condition
                    } else {
                        // Appelle une méthode
                        player.setAdditionalHearts(additionalHearts - remainingDamage);
                        // Affecte une valeur
                        remainingDamage = 0;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Set the final entity health
            // Appelle une méthode
            setHealth(getHealth() - remainingDamage);

            // play damage sound
            // Appelle une méthode
            final SoundEvent sound = entityDamageEvent.getSound();
            // Embranchement : vérifie une condition
            if (sound != null) {
                // Instruction de code
                Source soundCategory;
                // Embranchement : vérifie une condition
                if (this instanceof Player) {
                    // Affecte une valeur
                    soundCategory = Source.PLAYER;
                // Branche alternative de la condition
                } else {
                    // TODO: separate living entity categories
                    // Affecte une valeur
                    soundCategory = Source.HOSTILE;
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                Pos pos = getPosition();
                // Appelle une méthode
                ServerPacket packet = AdventurePacketConvertor.createSoundPacket(Sound.sound(sound, soundCategory, 1f, 1f), pos.x(), pos.y(), pos.z());
                // Appelle une méthode
                sendPacketToViewersAndSelf(packet);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Renvoie une valeur à l'appelant
        return !entityDamageEvent.isCancelled();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Is this entity immune to the given type of damage?
     *
     * @param type the type of damage
     * @return true if this entity is immune to the given type of damage
     */
    // Début d'une méthode/d'un bloc
    public boolean isImmune(RegistryKey<DamageType> type) {
        // Embranchement : vérifie une condition
        if (type.equals(DamageType.OUT_OF_WORLD)) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return isInvulnerable();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the entity health.
     *
     * @return the entity health
     */
    // Début d'une méthode/d'un bloc
    public float getHealth() {
        // Renvoie une valeur à l'appelant
        return this.health;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the entity health, kill it if {@code health} is &lt;= 0 and is not dead yet.
     *
     * @param health the new entity health
     */
    // Début d'une méthode/d'un bloc
    public void setHealth(float health) {
        // Accès à l'objet courant/parent
        this.health = Math.min(health, (float) getAttributeValue(Attribute.MAX_HEALTH));
        // Embranchement : vérifie une condition
        if (this.health <= 0 && !isDead) {
            // Appelle une méthode
            kill();
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        LivingEntityMeta meta = getLivingEntityMeta();
        // Embranchement : vérifie une condition
        if (meta != null) {
            // Appelle une méthode
            meta.setHealth(this.health);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the last damage source which damaged of this entity.
     *
     * @return the last damage source, null if not any
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Damage getLastDamageSource() {
        // Renvoie une valeur à l'appelant
        return lastDamage;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the heal of the entity as its max health.
     * <p>
     * Retrieved from {@link #getAttributeValue(Attribute)} with the attribute {@link Attribute#MAX_HEALTH}.
     */
    // Début d'une méthode/d'un bloc
    public void heal() {
        // Appelle une méthode
        setHealth((float) getAttributeValue(Attribute.MAX_HEALTH));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieves the attribute instance and its modifiers.
     *
     * @param attribute the attribute instance to get
     * @return the attribute instance
     */
    // Début d'une méthode/d'un bloc
    public AttributeInstance getAttribute(Attribute attribute) {
        // Renvoie une valeur à l'appelant
        return attributeModifiers.computeIfAbsent(attribute.name(),
                // Début d'une méthode/d'un bloc
                s -> {
                    // Appelle une méthode
                    double defaultValue = entityType.registry().defaultAttributes().getOrDefault(attribute, attribute.defaultValue());
                    // Renvoie une valeur à l'appelant
                    return new AttributeInstance(attribute, defaultValue, new ArrayList<>(), this::onAttributeChanged);
                // Fin d'un bloc/d'une expression
                });
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieves all {@link AttributeInstance}s on this entity.
     *
     * @return a collection of all attribute instances on this entity
     */
    // Début d'une méthode/d'un bloc
    public @UnmodifiableView Collection<AttributeInstance> getAttributes() {
        // Renvoie une valeur à l'appelant
        return unmodifiableModifiers;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Callback used when an attribute instance has been modified.
     *
     * @param attributeInstance the modified attribute instance
     */
    // Début d'une méthode/d'un bloc
    protected void onAttributeChanged(AttributeInstance attributeInstance) {
        // Embranchement : vérifie une condition
        if (!shouldSendAttributes()) return;

        // Affecte une valeur
        boolean self = false;
        // Embranchement : vérifie une condition
        if (this instanceof Player player) {
            // Affecte une valeur
            PlayerConnection playerConnection = player.playerConnection;
            // connection null during Player initialization (due to #super call)
            // Appelle une méthode
            self = playerConnection != null && playerConnection.getServerState() == ConnectionState.PLAY;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        EntityAttributesPacket propertiesPacket = new EntityAttributesPacket(getEntityId(), List.of(
                // Crée un nouvel objet
                new EntityAttributesPacket.Property(
                        // Instruction de code
                        attributeInstance.attribute(),
                        // Instruction de code
                        attributeInstance.getBaseValue(),
                        // Instruction de code
                        attributeInstance.getModifiers())
        // Instruction de code
        ));
        // Embranchement : vérifie une condition
        if (self) {
            // Appelle une méthode
            sendPacketToViewersAndSelf(propertiesPacket);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            sendPacketToViewers(propertiesPacket);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieves the attribute value.
     *
     * @param attribute the attribute value to get
     * @return the attribute value
     */
    // Début d'une méthode/d'un bloc
    public double getAttributeValue(Attribute attribute) {
        // Appelle une méthode
        AttributeInstance instance = attributeModifiers.get(attribute.name());
        // Embranchement : vérifie une condition
        if (instance != null) return instance.getValue();
        // Renvoie une valeur à l'appelant
        return entityType.registry().defaultAttributes().getOrDefault(attribute, attribute.defaultValue());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is dead or not.
     *
     * @return true if the entity is dead
     */
    // Début d'une méthode/d'un bloc
    public boolean isDead() {
        // Renvoie une valeur à l'appelant
        return isDead;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the entity is able to pickup items.
     *
     * @return true if the entity is able to pickup items
     */
    // Début d'une méthode/d'un bloc
    public boolean canPickupItem() {
        // Renvoie une valeur à l'appelant
        return canPickupItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * When set to false, the entity will not be able to pick {@link ItemEntity} on the ground.
     *
     * @param canPickupItem can the entity pickup item
     */
    // Début d'une méthode/d'un bloc
    public void setCanPickupItem(boolean canPickupItem) {
        // Accès à l'objet courant/parent
        this.canPickupItem = canPickupItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Check if this entity should send an {@link EntityAttributesPacket}. This is true for players and entities whose
     * spawn type is {@code LIVING}, but false for others.
     *
     * @return true if this entity needs to send attributes, false otherwise
     */
    // Début d'une méthode/d'un bloc
    protected boolean shouldSendAttributes() {
        // Renvoie une valeur à l'appelant
        return this.entityType.registry().shouldSendAttributes();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void updateNewViewer(Player player) {
        // Accès à l'objet courant/parent
        super.updateNewViewer(player);
        // Appelle une méthode
        player.sendPacket(new LazyPacket(this::getEquipmentsPacket));

        // Embranchement : vérifie une condition
        if (shouldSendAttributes())
            // Appelle une méthode
            player.sendPacket(new LazyPacket(this::getPropertiesPacket));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBoundingBox(BoundingBox boundingBox) {
        // Accès à l'objet courant/parent
        super.setBoundingBox(boundingBox);
        // Accès à l'objet courant/parent
        this.expandedBoundingBox = boundingBox.growSymmetrically(1, .5, 1);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the main hand
     * (can be used for attack animation).
     */
    // Début d'une méthode/d'un bloc
    public void swingMainHand() {
        // Appelle une méthode
        swingMainHand(false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the off hand
     * (can be used for attack animation).
     */
    // Début d'une méthode/d'un bloc
    public void swingOffHand() {
        // Appelle une méthode
        swingOffHand(false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the main hand
     * (can be used for attack animation).
     *
     * @param fromClient if true, broadcast only to viewers
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void swingMainHand(boolean fromClient) {
        // Appelle une méthode
        swingHand(fromClient, EntityAnimationPacket.Animation.SWING_MAIN_ARM);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the off hand
     * (can be used for attack animation).
     *
     * @param fromClient if true, broadcast only to viewers
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void swingOffHand(boolean fromClient) {
        // Appelle une méthode
        swingHand(fromClient, EntityAnimationPacket.Animation.SWING_OFF_HAND);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void swingHand(boolean fromClient, EntityAnimationPacket.Animation animation) {
        // Appelle une méthode
        EntityAnimationPacket packet = new EntityAnimationPacket(getEntityId(), animation);
        // Embranchement : vérifie une condition
        if (fromClient) {
            // Appelle une méthode
            sendPacketToViewers(packet);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            sendPacketToViewersAndSelf(packet);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void refreshActiveHand(boolean isHandActive, boolean offHand, boolean riptideSpinAttack) {
        // Appelle une méthode
        LivingEntityMeta meta = getLivingEntityMeta();
        // Embranchement : vérifie une condition
        if (meta != null) {
            // Appelle une méthode
            meta.setNotifyAboutChanges(false);
            // Appelle une méthode
            meta.setHandActive(isHandActive);
            // Appelle une méthode
            meta.setActiveHand(offHand ? PlayerHand.OFF : PlayerHand.MAIN);
            // Appelle une méthode
            meta.setInRiptideSpinAttack(riptideSpinAttack);

            // Instruction de code
            updatePose(); // Riptide spin attack has a pose

            // Appelle une méthode
            meta.setNotifyAboutChanges(true);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Kicks the entity out of the bed.
     */
    // Début d'une méthode/d'un bloc
    public void leaveBed() {
        // Appelle une méthode
        LivingEntityMeta meta = getLivingEntityMeta();
        // Embranchement : vérifie une condition
        if (meta != null) {
            // Appelle une méthode
            meta.setBedInWhichSleepingPosition(null);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the {@code point} of the bed in which the entity is sleeping in.
     *
     * @param point the position of the bed
     */
    // Début d'une méthode/d'un bloc
    public void enterBed(Point point) {
        // Appelle une méthode
        LivingEntityMeta meta = getLivingEntityMeta();
        // Embranchement : vérifie une condition
        if (meta != null) {
            // Appelle une méthode
            meta.setBedInWhichSleepingPosition(point);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFlyingWithElytra() {
        // Renvoie une valeur à l'appelant
        return this.entityMeta.isFlyingWithElytra();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFlyingWithElytra(boolean isFlying) {
        // Accès à l'objet courant/parent
        this.entityMeta.setFlyingWithElytra(isFlying);
        // Appelle une méthode
        updatePose();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to change the {@code isDead} internal field.
     *
     * @param isDead the new field value
     */
    // Début d'une méthode/d'un bloc
    protected void refreshIsDead(boolean isDead) {
        // Accès à l'objet courant/parent
        this.isDead = isDead;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets an {@link EntityAttributesPacket} for this entity with all of its attributes values.
     *
     * @return an {@link EntityAttributesPacket} linked to this entity
     */
    // Début d'une méthode/d'un bloc
    protected EntityAttributesPacket getPropertiesPacket() {
        // Appelle une méthode
        List<EntityAttributesPacket.Property> properties = new ArrayList<>();
        // Boucle : répète un bloc
        for (AttributeInstance instance : attributeModifiers.values()) {
            // Appelle une méthode
            properties.add(new EntityAttributesPacket.Property(instance.attribute(), instance.getBaseValue(), instance.getModifiers()));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new EntityAttributesPacket(getEntityId(), properties);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the {@link Team} for the entity.
     *
     * @param team The new team
     */
    // Début d'une méthode/d'un bloc
    public void setTeam(@Nullable Team team) {
        // Embranchement : vérifie une condition
        if (this.team == team) return;
        // Appelle une méthode
        String member = this instanceof Player player ? player.getUsername() : getUuid().toString();
        // Embranchement : vérifie une condition
        if (this.team != null) {
            // Accès à l'objet courant/parent
            this.team.removeMember(member);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.team = team;
        // Embranchement : vérifie une condition
        if (team != null) {
            // Appelle une méthode
            team.addMember(member);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link Team} of the entity.
     *
     * @return the {@link Team}
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Team getTeam() {
        // Renvoie une valeur à l'appelant
        return team;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the target (not-air) block position of the entity.
     *
     * @param maxDistance The max distance to scan before returning null
     * @return The block position targeted by this entity, null if non are found
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Point getTargetBlockPosition(int maxDistance) {
        // Appelle une méthode
        Iterator<Point> it = new BlockIterator(this, maxDistance);
        // Boucle : répète un bloc
        while (it.hasNext()) {
            // Appelle une méthode
            final Point position = it.next();
            // Embranchement : vérifie une condition
            if (!getInstance().getBlock(position).isAir()) return position;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets {@link EntityMeta} of this entity casted to {@link LivingEntityMeta}.
     *
     * @return null if meta of this entity does not inherit {@link LivingEntityMeta}, casted value otherwise.
     */
    // Début d'une méthode/d'un bloc
    public @Nullable LivingEntityMeta getLivingEntityMeta() {
        // Embranchement : vérifie une condition
        if (this.entityMeta instanceof LivingEntityMeta) {
            // Renvoie une valeur à l'appelant
            return (LivingEntityMeta) this.entityMeta;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies knockback
     * <p>
     * Note: The strength is reduced based on knockback resistance
     *
     * @param strength the strength of the knockback, 0.4 is the vanilla value for a bare hand hit
     * @param x        knockback on x axle, for default knockback use the following formula <pre>sin(attacker.yaw * (pi/180))</pre>
     * @param z        knockback on z axle, for default knockback use the following formula <pre>-cos(attacker.yaw * (pi/180))</pre>
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void takeKnockback(float strength, final double x, final double z) {
        // Appelle une méthode
        strength *= (float) (1 - getAttributeValue(Attribute.KNOCKBACK_RESISTANCE));
        // Accès à l'objet courant/parent
        super.takeKnockback(strength, x, z);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Acquirable<? extends LivingEntity> acquirable() {
        // Renvoie une valeur à l'appelant
        return (Acquirable<? extends LivingEntity>) super.acquirable();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
