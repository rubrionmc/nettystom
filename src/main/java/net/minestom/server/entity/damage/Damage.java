// Package declaration for this file
package net.minestom.server.entity.damage;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.registry.DynamicRegistry;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.Taggable;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Represents a type of damage, required when calling {@link LivingEntity#damage(Damage)}
 * and retrieved in {@link net.minestom.server.event.entity.EntityDamageEvent}.
 * <p>
 * This class can be extended if you need to include custom fields and/or methods.
 */
// Type declaration (class/interface/enum/record)
public class Damage implements Taggable {
    // Calls a method
    private static final DynamicRegistry<DamageType> DAMAGE_TYPE_REGISTRY = MinecraftServer.getDamageTypeRegistry();

    // Code statement
    private final RegistryKey<DamageType> typeKey;
    // Code statement
    private final DamageType type;
    // Code statement
    private final Entity source;
    // Code statement
    private final Entity attacker;
    // Code statement
    private final Point sourcePosition;
    // Calls a method
    private final TagHandler tagHandler = TagHandler.newHandler();

    // Code statement
    private float amount;

    /**
     * Creates a new damage type.
     *
     * @param attacker       The attacker that initiated this damage
     * @param source         The source of the damage. For direct hits (melee), this will be the same as the attacker. For indirect hits (projectiles), this will be the projectile
     * @param type           the type of this damage
     * @param amount         amount of damage
     * @param sourcePosition The position of the source of damage
     */
    // Start of a method/block
    public Damage(RegistryKey<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, @Nullable Point sourcePosition, float amount) {
        // Access to the current/parent object
        this.typeKey = type;
        // Access to the current/parent object
        this.type = DAMAGE_TYPE_REGISTRY.get(type);
        // Calls a method
        Check.argCondition(this.type == null, "Damage type is not registered: {0}", type);
        // Access to the current/parent object
        this.source = source;
        // Access to the current/parent object
        this.attacker = attacker;
        // Access to the current/parent object
        this.sourcePosition = sourcePosition;
        // Access to the current/parent object
        this.amount = amount;
    // End of a block/expression
    }

    /**
     * Gets the type of this damage.
     * <p>
     * It does not have to be unique to this object.
     *
     * @return the damage type
     */
    // Start of a method/block
    public RegistryKey<DamageType> getType() {
        // Returns a value to the caller
        return typeKey;
    // End of a block/expression
    }

    /**
     * Gets the integer id of the damage type that has been set
     *
     * @return The integer id of the damage type
     */
    // Start of a method/block
    public int getTypeId() {
        // Returns a value to the caller
        return DAMAGE_TYPE_REGISTRY.getId(typeKey);
    // End of a block/expression
    }

    /**
     * Gets the "attacker" of the damage.
     * This is the indirect cause of the damage, like the shooter of a projectile, or null if there was none.
     *
     * @return the attacker
     */
    // Start of a method/block
    public @Nullable Entity getAttacker() {
        // Returns a value to the caller
        return attacker;
    // End of a block/expression
    }

    /**
     * Gets the direct source of the damage.
     * This is the entity that directly causes the damage, like a projectile, or null if there was none.
     *
     * @return the source
     */
    // Start of a method/block
    public @Nullable Entity getSource() {
        // Returns a value to the caller
        return source;
    // End of a block/expression
    }

    /**
     * Gets the position of the source of the damage, or null if there is none.
     * This may differ from the source entity's position.
     *
     * @return The source position
     */
    // Start of a method/block
    public @Nullable Point getSourcePosition() {
        // Returns a value to the caller
        return sourcePosition;
    // End of a block/expression
    }

    /**
     * Builds the death message linked to this damage type.
     * <p>
     * Used in {@link Player#kill()} to broadcast the proper message.
     *
     * @param killed the player who has been killed
     * @return the death message, null to do not send anything
     */
    // Start of a method/block
    public @Nullable Component buildDeathMessage(Player killed) {
        // Returns a value to the caller
        return Component.translatable("death.attack." + type.messageId(), Component.text(killed.getUsername()));
    // End of a block/expression
    }

    /**
     * Convenient method to create an {@link EntityProjectileDamage}.
     *
     * @param shooter    the shooter
     * @param projectile the actual projectile
     * @param amount     amount of damage
     * @return a new {@link EntityProjectileDamage}
     */
    // Start of a method/block
    public static Damage fromProjectile(@Nullable Entity shooter, Entity projectile, float amount) {
        // Returns a value to the caller
        return new EntityProjectileDamage(shooter, projectile, amount);
    // End of a block/expression
    }

    /**
     * Convenient method to create an {@link EntityDamage}.
     *
     * @param player the player damager
     * @param amount amount of damage
     * @return a new {@link EntityDamage}
     */
    // Start of a method/block
    public static EntityDamage fromPlayer(Player player, float amount) {
        // Returns a value to the caller
        return new EntityDamage(player, amount);
    // End of a block/expression
    }

    /**
     * Convenient method to create an {@link EntityDamage}.
     *
     * @param entity the entity damager
     * @param amount amount of damage
     * @return a new {@link EntityDamage}
     */
    // Start of a method/block
    public static EntityDamage fromEntity(Entity entity, float amount) {
        // Returns a value to the caller
        return new EntityDamage(entity, amount);
    // End of a block/expression
    }

    // Start of a method/block
    public static PositionalDamage fromPosition(RegistryKey<DamageType> type, Point sourcePosition, float amount) {
        // Returns a value to the caller
        return new PositionalDamage(type, sourcePosition, amount);
    // End of a block/expression
    }

    /**
     * Builds the text sent to a player in his death screen.
     *
     * @param killed the player who has been killed
     * @return the death screen text, null to do not send anything
     */
    // Start of a method/block
    public @Nullable Component buildDeathScreenText(Player killed) {
        // Returns a value to the caller
        return Component.translatable("death.attack." + type.messageId());
    // End of a block/expression
    }

    /**
     * Sound event to play when the given entity is hit by this damage. Possible to return null if no sound should be played
     *
     * @param entity the entity hit by this damage
     * @return the sound to play when the given entity is hurt by this damage type. Can be null if no sound should play
     */
    // Start of a method/block
    public @Nullable SoundEvent getSound(LivingEntity entity) {
        // Branch: checks a condition
        if (entity instanceof Player) {
            // Returns a value to the caller
            return getPlayerSound((Player) entity);
        // End of a block/expression
        }
        // Returns a value to the caller
        return getGenericSound(entity);
    // End of a block/expression
    }

    // Start of a method/block
    protected SoundEvent getGenericSound(LivingEntity entity) {
        // Returns a value to the caller
        return SoundEvent.ENTITY_GENERIC_HURT;
    // End of a block/expression
    }

    // Start of a method/block
    protected SoundEvent getPlayerSound(Player player) {
        // Branch: checks a condition
        if (DamageType.ON_FIRE.equals(typeKey)) return SoundEvent.ENTITY_PLAYER_HURT_ON_FIRE;
        // Returns a value to the caller
        return SoundEvent.ENTITY_PLAYER_HURT;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TagHandler tagHandler() {
        // Returns a value to the caller
        return tagHandler;
    // End of a block/expression
    }

    // Start of a method/block
    public float getAmount() {
        // Returns a value to the caller
        return amount;
    // End of a block/expression
    }

    // Start of a method/block
    public void setAmount(float amount) {
        // Access to the current/parent object
        this.amount = amount;
    // End of a block/expression
    }
// End of a block/expression
}
