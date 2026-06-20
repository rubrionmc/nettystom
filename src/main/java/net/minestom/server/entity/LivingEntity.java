// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.sound.Sound;
// Import of a required class
import net.kyori.adventure.sound.Sound.Source;
// Import of a required class
import net.minestom.server.adventure.AdventurePacketConvertor;
// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.attribute.Attribute;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeInstance;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeModifier;
// Import of a required class
import net.minestom.server.entity.attribute.AttributeOperation;
// Import of a required class
import net.minestom.server.entity.damage.Damage;
// Import of a required class
import net.minestom.server.entity.damage.DamageType;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.LivingEntityMeta;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.entity.EntityDamageEvent;
// Import of a required class
import net.minestom.server.event.entity.EntityDeathEvent;
// Import of a required class
import net.minestom.server.event.entity.EntityFireExtinguishEvent;
// Import of a required class
import net.minestom.server.event.entity.EntitySetFireEvent;
// Import of a required class
import net.minestom.server.event.item.EntityEquipEvent;
// Import of a required class
import net.minestom.server.event.item.PickupItemEvent;
// Import of a required class
import net.minestom.server.instance.EntityTracker;
// Import of a required class
import net.minestom.server.inventory.EquipmentHandler;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.component.AttributeList;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.server.LazyPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.scoreboard.Team;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.thread.Acquirable;
// Import of a required class
import net.minestom.server.utils.block.BlockIterator;
// Import of a required class
import net.minestom.server.utils.time.Cooldown;
// Import of a required class
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnmodifiableView;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;

// Type declaration (class/interface/enum/record)
public class LivingEntity extends Entity implements EquipmentHandler {

    // Calls a method
    private static final AttributeModifier SPRINTING_SPEED_MODIFIER = new AttributeModifier(Key.key("sprinting"), 0.3, AttributeOperation.ADD_MULTIPLIED_TOTAL);

    /**
     * IDs of modifiers that are protected from removal by methods like {@link AttributeInstance#clearModifiers()}.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    public static final Set<Key> PROTECTED_MODIFIERS = Set.of(SPRINTING_SPEED_MODIFIER.id());

    // ItemStack pickup
    // Code statement
    protected boolean canPickupItem;
    // Calls a method
    protected Cooldown itemPickupCooldown = new Cooldown(Duration.of(5, TimeUnit.SERVER_TICK));

    // Code statement
    protected boolean isDead;

    // Code statement
    protected Damage lastDamage;

    // Bounding box used for items' pickup (see LivingEntity#setBoundingBox)
    // Code statement
    protected BoundingBox expandedBoundingBox;

    // Calls a method
    private final Map<String, AttributeInstance> attributeModifiers = new ConcurrentHashMap<>();
    // Code statement
    private final Collection<AttributeInstance> unmodifiableModifiers =
            // Calls a method
            Collections.unmodifiableCollection(attributeModifiers.values());

    // Abilities
    // Code statement
    protected boolean invulnerable;

    /**
     * Ticks until this entity must be extinguished
     */
    // Code statement
    private int remainingFireTicks;

    // Code statement
    private Team team;

    // Code statement
    private int arrowCount;
    // Assigns a value
    private float health = 1F;

    // Equipments
    // Assigns a value
    private ItemStack mainHandItem = ItemStack.AIR;
    // Assigns a value
    private ItemStack offHandItem = ItemStack.AIR;

    // Assigns a value
    private ItemStack helmet = ItemStack.AIR;
    // Assigns a value
    private ItemStack chestplate = ItemStack.AIR;
    // Assigns a value
    private ItemStack leggings = ItemStack.AIR;
    // Assigns a value
    private ItemStack boots = ItemStack.AIR;
    // Assigns a value
    private ItemStack bodyEquipment = ItemStack.AIR;
    // Assigns a value
    private ItemStack saddleEquipment = ItemStack.AIR;

    /**
     * Constructor which allows to specify an UUID. Only use if you know what you are doing!
     */
    // Start of a method/block
    public LivingEntity(EntityType entityType, UUID uuid) {
        // Access to the current/parent object
        super(entityType, uuid);
    // End of a block/expression
    }

    // Start of a method/block
    public LivingEntity(EntityType entityType) {
        // Calls a method
        this(entityType, UUID.randomUUID());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setSprinting(boolean sprinting) {
        // Access to the current/parent object
        super.setSprinting(sprinting);

        // We must set the sprinting attribute serverside because when we resend modifiers it overwrites what
        // the client has, meaning if they are sprinting and we send no modifiers, they will no longer be
        // getting the speed boost of sprinting.
        // Calls a method
        final AttributeInstance speed = getAttribute(Attribute.MOVEMENT_SPEED);
        // Branch: checks a condition
        if (sprinting) speed.addModifier(SPRINTING_SPEED_MODIFIER);
        // Alternative branch of the condition
        else speed.removeModifier(SPRINTING_SPEED_MODIFIER);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemStack getEquipment(EquipmentSlot slot) {
        // Returns a value to the caller
        return switch (slot) {
            // Multiple branching (switch/case)
            case MAIN_HAND -> mainHandItem;
            // Multiple branching (switch/case)
            case OFF_HAND -> offHandItem;
            // Multiple branching (switch/case)
            case BOOTS -> boots;
            // Multiple branching (switch/case)
            case LEGGINGS -> leggings;
            // Multiple branching (switch/case)
            case CHESTPLATE -> chestplate;
            // Multiple branching (switch/case)
            case HELMET -> helmet;
            // Multiple branching (switch/case)
            case BODY -> bodyEquipment;
            // Multiple branching (switch/case)
            case SADDLE -> saddleEquipment;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setEquipment(EquipmentSlot slot, ItemStack itemStack) {
        // Calls a method
        ItemStack oldItem = getEquipment(slot);
        // Calls a method
        ItemStack newItem = slotChangeEvent(itemStack, slot);

        // Multiple branching (switch/case)
        switch (slot) {
            // Multiple branching (switch/case)
            case MAIN_HAND -> mainHandItem = newItem;
            // Multiple branching (switch/case)
            case OFF_HAND -> offHandItem = newItem;
            // Multiple branching (switch/case)
            case BOOTS -> boots = newItem;
            // Multiple branching (switch/case)
            case LEGGINGS -> leggings = newItem;
            // Multiple branching (switch/case)
            case CHESTPLATE -> chestplate = newItem;
            // Multiple branching (switch/case)
            case HELMET -> helmet = newItem;
            // Multiple branching (switch/case)
            case BODY -> bodyEquipment = newItem;
            // Multiple branching (switch/case)
            case SADDLE -> saddleEquipment = newItem;
        // End of a block/expression
        }

        // Calls a method
        syncEquipment(slot);
        // Calls a method
        updateEquipmentAttributes(oldItem, newItem, slot);
    // End of a block/expression
    }

    // Start of a method/block
    private ItemStack slotChangeEvent(ItemStack itemStack, EquipmentSlot slot) {
        // Calls a method
        EntityEquipEvent entityEquipEvent = new EntityEquipEvent(this, itemStack, slot);
        // Calls a method
        EventDispatcher.call(entityEquipEvent);
        // Returns a value to the caller
        return entityEquipEvent.getEquippedItem();
    // End of a block/expression
    }

    /**
     * Updates the current attributes of the living entity based on
     *
     * @param oldItemStack The ItemStack that has been removed, modifiers on this stack will be removed from the entity
     * @param newItemStack The ItemStack that has been added, modifiers on this stack will be added to the entity
     * @param slot         The slot that changed, this will determine what modifiers are actually changed
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void updateEquipmentAttributes(ItemStack oldItemStack, ItemStack newItemStack, EquipmentSlot slot) {
        // Calls a method
        AttributeList oldAttributes = oldItemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        // Remove old attributes
        // Branch: checks a condition
        if (oldAttributes != null) {
            // Loop: repeats a block
            for (AttributeList.Modifier modifier : oldAttributes.modifiers()) {
                // If the modifier currently modifies the slot we are updating
                // Branch: checks a condition
                if (modifier.slot().contains(slot)) {
                    // Calls a method
                    AttributeInstance attributeInstance = getAttribute(modifier.attribute());
                    // Calls a method
                    attributeInstance.removeModifier(modifier.modifier().id());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        AttributeList newAttributes = newItemStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        // Add new attributes
        // Branch: checks a condition
        if (newAttributes != null) {
            // Loop: repeats a block
            for (AttributeList.Modifier modifier : newAttributes.modifiers()) {
                // If the modifier currently modifies the slot we are updating
                // Branch: checks a condition
                if (modifier.slot().contains(slot)) {
                    // Calls a method
                    AttributeInstance attributeInstance = getAttribute(modifier.attribute());
                    // Calls a method
                    attributeInstance.addModifier(modifier.modifier());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update(long time) {
        // Fire
        // Branch: checks a condition
        if (remainingFireTicks > 0 && --remainingFireTicks == 0) {
            // Calls a method
            EventDispatcher.callCancellable(new EntityFireExtinguishEvent(this, true), () -> entityMeta.setOnFire(false));
        // End of a block/expression
        }

        // Items picking
        // Branch: checks a condition
        if (canPickupItem() && itemPickupCooldown.isReady(time)) {
            // Calls a method
            itemPickupCooldown.refreshLastUpdate(time);
            // Access to the current/parent object
            this.instance.getEntityTracker().nearbyEntities(position, expandedBoundingBox.width(),
                    // Start of a method/block
                    EntityTracker.Target.ITEMS, itemEntity -> {
                        // Branch: checks a condition
                        if (this instanceof Player player && !itemEntity.isViewer(player)) return;
                        // Branch: checks a condition
                        if (!itemEntity.isPickable()) return;
                        // Branch: checks a condition
                        if (!expandedBoundingBox.intersectEntity(position, itemEntity)) return;
                        // Calls a method
                        final PickupItemEvent pickupItemEvent = new PickupItemEvent(this, itemEntity);
                        // Start of a method/block
                        EventDispatcher.callCancellable(pickupItemEvent, () -> {
                            // Calls a method
                            final ItemStack item = itemEntity.getItemStack();
                            // Calls a method
                            sendPacketToViewersAndSelf(new CollectItemPacket(itemEntity.getEntityId(), getEntityId(), item.amount()));
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

    /**
     * Gets the amount of arrows in the entity.
     *
     * @return the arrow count
     */
    // Start of a method/block
    public int getArrowCount() {
        // Returns a value to the caller
        return this.arrowCount;
    // End of a block/expression
    }

    /**
     * Changes the amount of arrow stuck in the entity.
     *
     * @param arrowCount the arrow count
     */
    // Start of a method/block
    public void setArrowCount(int arrowCount) {
        // Access to the current/parent object
        this.arrowCount = arrowCount;
        // Calls a method
        LivingEntityMeta meta = getLivingEntityMeta();
        // Branch: checks a condition
        if (meta != null) {
            // Calls a method
            meta.setArrowCount(arrowCount);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets if the entity is invulnerable.
     *
     * @return true if the entity is invulnerable
     */
    // Start of a method/block
    public boolean isInvulnerable() {
        // Returns a value to the caller
        return invulnerable;
    // End of a block/expression
    }

    /**
     * Makes the entity vulnerable or invulnerable.
     *
     * @param invulnerable should the entity be invulnerable
     */
    // Start of a method/block
    public void setInvulnerable(boolean invulnerable) {
        // Access to the current/parent object
        this.invulnerable = invulnerable;
    // End of a block/expression
    }

    /**
     * Kills the entity, trigger the {@link EntityDeathEvent} event.
     */
    // Start of a method/block
    public void kill() {
        // Code statement
        refreshIsDead(true); // So the entity isn't killed over and over again
        // Code statement
        triggerStatus((byte) EntityStatuses.LivingEntity.PLAY_DEATH_SOUND); // Start death animation status
        // Calls a method
        setPose(EntityPose.DYING);
        // Calls a method
        setHealth(0);

        // Reset velocity
        // Access to the current/parent object
        this.velocity = Vec.ZERO;

        // Remove passengers if any
        // Branch: checks a condition
        if (hasPassenger()) {
            // Calls a method
            getPassengers().forEach(this::removePassenger);
        // End of a block/expression
        }

        // Calls a method
        EntityDeathEvent entityDeathEvent = new EntityDeathEvent(this);
        // Calls a method
        EventDispatcher.call(entityDeathEvent);
    // End of a block/expression
    }

    /**
     * Gets the amount of ticks this entity is on fire for.
     *
     * @return the remaining duration of fire in ticks, 0 if not on fire
     */
    // Start of a method/block
    public int getFireTicks() {
        // Returns a value to the caller
        return remainingFireTicks;
    // End of a block/expression
    }

    /**
     * Sets this entity on fire for the given ticks.
     *
     * @param ticks duration of fire in ticks
     */
    // Start of a method/block
    public void setFireTicks(int ticks) {
        // Calls a method
        int fireTicks = Math.max(0, ticks);
        // Branch: checks a condition
        if (fireTicks > 0) {
            // Calls a method
            EntitySetFireEvent entitySetFireEvent = new EntitySetFireEvent(this, ticks);
            // Calls a method
            EventDispatcher.call(entitySetFireEvent);
            // Branch: checks a condition
            if (entitySetFireEvent.isCancelled()) return;

            // Calls a method
            fireTicks = Math.max(0, entitySetFireEvent.getFireTicks());
            // Branch: checks a condition
            if (fireTicks > 0) {
                // Assigns a value
                remainingFireTicks = fireTicks;
                // Calls a method
                entityMeta.setOnFire(true);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (remainingFireTicks != 0) {
            // Calls a method
            EntityFireExtinguishEvent entityFireExtinguishEvent = new EntityFireExtinguishEvent(this, false);
            // Calls a method
            EventDispatcher.callCancellable(entityFireExtinguishEvent, () -> entityMeta.setOnFire(false));
        // End of a block/expression
        }

        // Assigns a value
        remainingFireTicks = fireTicks;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean damage(RegistryKey<DamageType> type, float amount) {
        // Returns a value to the caller
        return damage(new Damage(type, null, null, null, amount));
    // End of a block/expression
    }

    /**
     * Damages the entity by a value, the type of the damage also has to be specified.
     *
     * @param damage the damage to be applied
     * @return true if damage has been applied, false if it didn't
     */
    // Start of a method/block
    public boolean damage(Damage damage) {
        // Branch: checks a condition
        if (isDead())
            // Returns a value to the caller
            return false;
        // Branch: checks a condition
        if (isImmune(damage.getType())) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Calls a method
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(this, damage, damage.getSound(this));
        // Start of a method/block
        EventDispatcher.callCancellable(entityDamageEvent, () -> {
            // Set the last damage type since the event is not cancelled
            // Access to the current/parent object
            this.lastDamage = entityDamageEvent.getDamage();

            // Calls a method
            float remainingDamage = entityDamageEvent.getDamage().getAmount();

            // Branch: checks a condition
            if (entityDamageEvent.shouldAnimate()) {
                // Code statement
                sendPacketToViewersAndSelf(new DamageEventPacket(
                        // Code statement
                        getEntityId(), damage.getTypeId(),
                        // Code statement
                        damage.getAttacker() == null ? 0 : damage.getAttacker().getEntityId() + 1,
                        // Code statement
                        damage.getSource() == null ? 0 : damage.getSource().getEntityId() + 1,
                        // Code statement
                        damage.getSourcePosition()
                // Code statement
                ));
            // End of a block/expression
            }

            // Additional hearts support
            // Branch: checks a condition
            if (this instanceof Player player) {
                // Calls a method
                final float additionalHearts = player.getAdditionalHearts();
                // Branch: checks a condition
                if (additionalHearts > 0) {
                    // Branch: checks a condition
                    if (remainingDamage > additionalHearts) {
                        // Code statement
                        remainingDamage -= additionalHearts;
                        // Calls a method
                        player.setAdditionalHearts(0);
                    // Alternative branch of the condition
                    } else {
                        // Calls a method
                        player.setAdditionalHearts(additionalHearts - remainingDamage);
                        // Assigns a value
                        remainingDamage = 0;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Set the final entity health
            // Calls a method
            setHealth(getHealth() - remainingDamage);

            // play damage sound
            // Calls a method
            final SoundEvent sound = entityDamageEvent.getSound();
            // Branch: checks a condition
            if (sound != null) {
                // Code statement
                Source soundCategory;
                // Branch: checks a condition
                if (this instanceof Player) {
                    // Assigns a value
                    soundCategory = Source.PLAYER;
                // Alternative branch of the condition
                } else {
                    // TODO: separate living entity categories
                    // Assigns a value
                    soundCategory = Source.HOSTILE;
                // End of a block/expression
                }

                // Calls a method
                Pos pos = getPosition();
                // Calls a method
                ServerPacket packet = AdventurePacketConvertor.createSoundPacket(Sound.sound(sound, soundCategory, 1f, 1f), pos.x(), pos.y(), pos.z());
                // Calls a method
                sendPacketToViewersAndSelf(packet);
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Returns a value to the caller
        return !entityDamageEvent.isCancelled();
    // End of a block/expression
    }

    /**
     * Is this entity immune to the given type of damage?
     *
     * @param type the type of damage
     * @return true if this entity is immune to the given type of damage
     */
    // Start of a method/block
    public boolean isImmune(RegistryKey<DamageType> type) {
        // Branch: checks a condition
        if (type.equals(DamageType.OUT_OF_WORLD)) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return isInvulnerable();
    // End of a block/expression
    }

    /**
     * Gets the entity health.
     *
     * @return the entity health
     */
    // Start of a method/block
    public float getHealth() {
        // Returns a value to the caller
        return this.health;
    // End of a block/expression
    }

    /**
     * Changes the entity health, kill it if {@code health} is &lt;= 0 and is not dead yet.
     *
     * @param health the new entity health
     */
    // Start of a method/block
    public void setHealth(float health) {
        // Access to the current/parent object
        this.health = Math.min(health, (float) getAttributeValue(Attribute.MAX_HEALTH));
        // Branch: checks a condition
        if (this.health <= 0 && !isDead) {
            // Calls a method
            kill();
        // End of a block/expression
        }
        // Calls a method
        LivingEntityMeta meta = getLivingEntityMeta();
        // Branch: checks a condition
        if (meta != null) {
            // Calls a method
            meta.setHealth(this.health);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the last damage source which damaged of this entity.
     *
     * @return the last damage source, null if not any
     */
    // Start of a method/block
    public @Nullable Damage getLastDamageSource() {
        // Returns a value to the caller
        return lastDamage;
    // End of a block/expression
    }

    /**
     * Sets the heal of the entity as its max health.
     * <p>
     * Retrieved from {@link #getAttributeValue(Attribute)} with the attribute {@link Attribute#MAX_HEALTH}.
     */
    // Start of a method/block
    public void heal() {
        // Calls a method
        setHealth((float) getAttributeValue(Attribute.MAX_HEALTH));
    // End of a block/expression
    }

    /**
     * Retrieves the attribute instance and its modifiers.
     *
     * @param attribute the attribute instance to get
     * @return the attribute instance
     */
    // Start of a method/block
    public AttributeInstance getAttribute(Attribute attribute) {
        // Returns a value to the caller
        return attributeModifiers.computeIfAbsent(attribute.name(),
                // Start of a method/block
                s -> {
                    // Calls a method
                    double defaultValue = entityType.registry().defaultAttributes().getOrDefault(attribute, attribute.defaultValue());
                    // Returns a value to the caller
                    return new AttributeInstance(attribute, defaultValue, new ArrayList<>(), this::onAttributeChanged);
                // End of a block/expression
                });
    // End of a block/expression
    }

    /**
     * Retrieves all {@link AttributeInstance}s on this entity.
     *
     * @return a collection of all attribute instances on this entity
     */
    // Start of a method/block
    public @UnmodifiableView Collection<AttributeInstance> getAttributes() {
        // Returns a value to the caller
        return unmodifiableModifiers;
    // End of a block/expression
    }

    /**
     * Callback used when an attribute instance has been modified.
     *
     * @param attributeInstance the modified attribute instance
     */
    // Start of a method/block
    protected void onAttributeChanged(AttributeInstance attributeInstance) {
        // Branch: checks a condition
        if (!shouldSendAttributes()) return;

        // Assigns a value
        boolean self = false;
        // Branch: checks a condition
        if (this instanceof Player player) {
            // Assigns a value
            PlayerConnection playerConnection = player.playerConnection;
            // connection null during Player initialization (due to #super call)
            // Calls a method
            self = playerConnection != null && playerConnection.getServerState() == ConnectionState.PLAY;
        // End of a block/expression
        }
        // Assigns a value
        EntityAttributesPacket propertiesPacket = new EntityAttributesPacket(getEntityId(), List.of(
                // Creates a new object
                new EntityAttributesPacket.Property(
                        // Code statement
                        attributeInstance.attribute(),
                        // Code statement
                        attributeInstance.getBaseValue(),
                        // Code statement
                        attributeInstance.getModifiers())
        // Code statement
        ));
        // Branch: checks a condition
        if (self) {
            // Calls a method
            sendPacketToViewersAndSelf(propertiesPacket);
        // Alternative branch of the condition
        } else {
            // Calls a method
            sendPacketToViewers(propertiesPacket);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Retrieves the attribute value.
     *
     * @param attribute the attribute value to get
     * @return the attribute value
     */
    // Start of a method/block
    public double getAttributeValue(Attribute attribute) {
        // Calls a method
        AttributeInstance instance = attributeModifiers.get(attribute.name());
        // Branch: checks a condition
        if (instance != null) return instance.getValue();
        // Returns a value to the caller
        return entityType.registry().defaultAttributes().getOrDefault(attribute, attribute.defaultValue());
    // End of a block/expression
    }

    /**
     * Gets if the entity is dead or not.
     *
     * @return true if the entity is dead
     */
    // Start of a method/block
    public boolean isDead() {
        // Returns a value to the caller
        return isDead;
    // End of a block/expression
    }

    /**
     * Gets if the entity is able to pickup items.
     *
     * @return true if the entity is able to pickup items
     */
    // Start of a method/block
    public boolean canPickupItem() {
        // Returns a value to the caller
        return canPickupItem;
    // End of a block/expression
    }

    /**
     * When set to false, the entity will not be able to pick {@link ItemEntity} on the ground.
     *
     * @param canPickupItem can the entity pickup item
     */
    // Start of a method/block
    public void setCanPickupItem(boolean canPickupItem) {
        // Access to the current/parent object
        this.canPickupItem = canPickupItem;
    // End of a block/expression
    }

    /**
     * Check if this entity should send an {@link EntityAttributesPacket}. This is true for players and entities whose
     * spawn type is {@code LIVING}, but false for others.
     *
     * @return true if this entity needs to send attributes, false otherwise
     */
    // Start of a method/block
    protected boolean shouldSendAttributes() {
        // Returns a value to the caller
        return this.entityType.registry().shouldSendAttributes();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void updateNewViewer(Player player) {
        // Access to the current/parent object
        super.updateNewViewer(player);
        // Calls a method
        player.sendPacket(new LazyPacket(this::getEquipmentsPacket));

        // Branch: checks a condition
        if (shouldSendAttributes())
            // Calls a method
            player.sendPacket(new LazyPacket(this::getPropertiesPacket));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setBoundingBox(BoundingBox boundingBox) {
        // Access to the current/parent object
        super.setBoundingBox(boundingBox);
        // Access to the current/parent object
        this.expandedBoundingBox = boundingBox.growSymmetrically(1, .5, 1);
    // End of a block/expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the main hand
     * (can be used for attack animation).
     */
    // Start of a method/block
    public void swingMainHand() {
        // Calls a method
        swingMainHand(false);
    // End of a block/expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the off hand
     * (can be used for attack animation).
     */
    // Start of a method/block
    public void swingOffHand() {
        // Calls a method
        swingOffHand(false);
    // End of a block/expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the main hand
     * (can be used for attack animation).
     *
     * @param fromClient if true, broadcast only to viewers
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void swingMainHand(boolean fromClient) {
        // Calls a method
        swingHand(fromClient, EntityAnimationPacket.Animation.SWING_MAIN_ARM);
    // End of a block/expression
    }

    /**
     * Sends a {@link EntityAnimationPacket} to swing the off hand
     * (can be used for attack animation).
     *
     * @param fromClient if true, broadcast only to viewers
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public void swingOffHand(boolean fromClient) {
        // Calls a method
        swingHand(fromClient, EntityAnimationPacket.Animation.SWING_OFF_HAND);
    // End of a block/expression
    }

    // Start of a method/block
    private void swingHand(boolean fromClient, EntityAnimationPacket.Animation animation) {
        // Calls a method
        EntityAnimationPacket packet = new EntityAnimationPacket(getEntityId(), animation);
        // Branch: checks a condition
        if (fromClient) {
            // Calls a method
            sendPacketToViewers(packet);
        // Alternative branch of the condition
        } else {
            // Calls a method
            sendPacketToViewersAndSelf(packet);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public void refreshActiveHand(boolean isHandActive, boolean offHand, boolean riptideSpinAttack) {
        // Calls a method
        LivingEntityMeta meta = getLivingEntityMeta();
        // Branch: checks a condition
        if (meta != null) {
            // Calls a method
            meta.setNotifyAboutChanges(false);
            // Calls a method
            meta.setHandActive(isHandActive);
            // Calls a method
            meta.setActiveHand(offHand ? PlayerHand.OFF : PlayerHand.MAIN);
            // Calls a method
            meta.setInRiptideSpinAttack(riptideSpinAttack);

            // Code statement
            updatePose(); // Riptide spin attack has a pose

            // Calls a method
            meta.setNotifyAboutChanges(true);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Kicks the entity out of the bed.
     */
    // Start of a method/block
    public void leaveBed() {
        // Calls a method
        LivingEntityMeta meta = getLivingEntityMeta();
        // Branch: checks a condition
        if (meta != null) {
            // Calls a method
            meta.setBedInWhichSleepingPosition(null);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Sets the {@code point} of the bed in which the entity is sleeping in.
     *
     * @param point the position of the bed
     */
    // Start of a method/block
    public void enterBed(Point point) {
        // Calls a method
        LivingEntityMeta meta = getLivingEntityMeta();
        // Branch: checks a condition
        if (meta != null) {
            // Calls a method
            meta.setBedInWhichSleepingPosition(point);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFlyingWithElytra() {
        // Returns a value to the caller
        return this.entityMeta.isFlyingWithElytra();
    // End of a block/expression
    }

    // Start of a method/block
    public void setFlyingWithElytra(boolean isFlying) {
        // Access to the current/parent object
        this.entityMeta.setFlyingWithElytra(isFlying);
        // Calls a method
        updatePose();
    // End of a block/expression
    }

    /**
     * Used to change the {@code isDead} internal field.
     *
     * @param isDead the new field value
     */
    // Start of a method/block
    protected void refreshIsDead(boolean isDead) {
        // Access to the current/parent object
        this.isDead = isDead;
    // End of a block/expression
    }

    /**
     * Gets an {@link EntityAttributesPacket} for this entity with all of its attributes values.
     *
     * @return an {@link EntityAttributesPacket} linked to this entity
     */
    // Start of a method/block
    protected EntityAttributesPacket getPropertiesPacket() {
        // Calls a method
        List<EntityAttributesPacket.Property> properties = new ArrayList<>();
        // Loop: repeats a block
        for (AttributeInstance instance : attributeModifiers.values()) {
            // Calls a method
            properties.add(new EntityAttributesPacket.Property(instance.attribute(), instance.getBaseValue(), instance.getModifiers()));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new EntityAttributesPacket(getEntityId(), properties);
    // End of a block/expression
    }

    /**
     * Changes the {@link Team} for the entity.
     *
     * @param team The new team
     */
    // Start of a method/block
    public void setTeam(@Nullable Team team) {
        // Branch: checks a condition
        if (this.team == team) return;
        // Calls a method
        String member = this instanceof Player player ? player.getUsername() : getUuid().toString();
        // Branch: checks a condition
        if (this.team != null) {
            // Access to the current/parent object
            this.team.removeMember(member);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.team = team;
        // Branch: checks a condition
        if (team != null) {
            // Calls a method
            team.addMember(member);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets the {@link Team} of the entity.
     *
     * @return the {@link Team}
     */
    // Start of a method/block
    public @Nullable Team getTeam() {
        // Returns a value to the caller
        return team;
    // End of a block/expression
    }

    /**
     * Gets the target (not-air) block position of the entity.
     *
     * @param maxDistance The max distance to scan before returning null
     * @return The block position targeted by this entity, null if non are found
     */
    // Start of a method/block
    public @Nullable Point getTargetBlockPosition(int maxDistance) {
        // Calls a method
        Iterator<Point> it = new BlockIterator(this, maxDistance);
        // Loop: repeats a block
        while (it.hasNext()) {
            // Calls a method
            final Point position = it.next();
            // Branch: checks a condition
            if (!getInstance().getBlock(position).isAir()) return position;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * Gets {@link EntityMeta} of this entity casted to {@link LivingEntityMeta}.
     *
     * @return null if meta of this entity does not inherit {@link LivingEntityMeta}, casted value otherwise.
     */
    // Start of a method/block
    public @Nullable LivingEntityMeta getLivingEntityMeta() {
        // Branch: checks a condition
        if (this.entityMeta instanceof LivingEntityMeta) {
            // Returns a value to the caller
            return (LivingEntityMeta) this.entityMeta;
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
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
    // Annotation for the following element
    @Override
    // Start of a method/block
    public void takeKnockback(float strength, final double x, final double z) {
        // Calls a method
        strength *= (float) (1 - getAttributeValue(Attribute.KNOCKBACK_RESISTANCE));
        // Access to the current/parent object
        super.takeKnockback(strength, x, z);
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquirable<? extends LivingEntity> acquirable() {
        // Returns a value to the caller
        return (Acquirable<? extends LivingEntity>) super.acquirable();
    // End of a block/expression
    }
// End of a block/expression
}
