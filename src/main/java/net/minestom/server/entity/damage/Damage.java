// Déclaration du paquet de ce fichier
package net.minestom.server.entity.damage;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.LivingEntity;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.registry.DynamicRegistry;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Taggable;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Represents a type of damage, required when calling {@link LivingEntity#damage(Damage)}
 * and retrieved in {@link net.minestom.server.event.entity.EntityDamageEvent}.
 * <p>
 * This class can be extended if you need to include custom fields and/or methods.
 */
// Déclaration de type (classe/interface/enum/record)
public class Damage implements Taggable {
    // Appelle une méthode
    private static final DynamicRegistry<DamageType> DAMAGE_TYPE_REGISTRY = MinecraftServer.getDamageTypeRegistry();

    // Instruction de code
    private final RegistryKey<DamageType> typeKey;
    // Instruction de code
    private final DamageType type;
    // Instruction de code
    private final Entity source;
    // Instruction de code
    private final Entity attacker;
    // Instruction de code
    private final Point sourcePosition;
    // Appelle une méthode
    private final TagHandler tagHandler = TagHandler.newHandler();

    // Instruction de code
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
    // Début d'une méthode/d'un bloc
    public Damage(RegistryKey<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, @Nullable Point sourcePosition, float amount) {
        // Accès à l'objet courant/parent
        this.typeKey = type;
        // Accès à l'objet courant/parent
        this.type = DAMAGE_TYPE_REGISTRY.get(type);
        // Appelle une méthode
        Check.argCondition(this.type == null, "Damage type is not registered: {0}", type);
        // Accès à l'objet courant/parent
        this.source = source;
        // Accès à l'objet courant/parent
        this.attacker = attacker;
        // Accès à l'objet courant/parent
        this.sourcePosition = sourcePosition;
        // Accès à l'objet courant/parent
        this.amount = amount;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the type of this damage.
     * <p>
     * It does not have to be unique to this object.
     *
     * @return the damage type
     */
    // Début d'une méthode/d'un bloc
    public RegistryKey<DamageType> getType() {
        // Renvoie une valeur à l'appelant
        return typeKey;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the integer id of the damage type that has been set
     *
     * @return The integer id of the damage type
     */
    // Début d'une méthode/d'un bloc
    public int getTypeId() {
        // Renvoie une valeur à l'appelant
        return DAMAGE_TYPE_REGISTRY.getId(typeKey);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the "attacker" of the damage.
     * This is the indirect cause of the damage, like the shooter of a projectile, or null if there was none.
     *
     * @return the attacker
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getAttacker() {
        // Renvoie une valeur à l'appelant
        return attacker;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the direct source of the damage.
     * This is the entity that directly causes the damage, like a projectile, or null if there was none.
     *
     * @return the source
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Entity getSource() {
        // Renvoie une valeur à l'appelant
        return source;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the position of the source of the damage, or null if there is none.
     * This may differ from the source entity's position.
     *
     * @return The source position
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Point getSourcePosition() {
        // Renvoie une valeur à l'appelant
        return sourcePosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Builds the death message linked to this damage type.
     * <p>
     * Used in {@link Player#kill()} to broadcast the proper message.
     *
     * @param killed the player who has been killed
     * @return the death message, null to do not send anything
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Component buildDeathMessage(Player killed) {
        // Renvoie une valeur à l'appelant
        return Component.translatable("death.attack." + type.messageId(), Component.text(killed.getUsername()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Convenient method to create an {@link EntityProjectileDamage}.
     *
     * @param shooter    the shooter
     * @param projectile the actual projectile
     * @param amount     amount of damage
     * @return a new {@link EntityProjectileDamage}
     */
    // Début d'une méthode/d'un bloc
    public static Damage fromProjectile(@Nullable Entity shooter, Entity projectile, float amount) {
        // Renvoie une valeur à l'appelant
        return new EntityProjectileDamage(shooter, projectile, amount);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Convenient method to create an {@link EntityDamage}.
     *
     * @param player the player damager
     * @param amount amount of damage
     * @return a new {@link EntityDamage}
     */
    // Début d'une méthode/d'un bloc
    public static EntityDamage fromPlayer(Player player, float amount) {
        // Renvoie une valeur à l'appelant
        return new EntityDamage(player, amount);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Convenient method to create an {@link EntityDamage}.
     *
     * @param entity the entity damager
     * @param amount amount of damage
     * @return a new {@link EntityDamage}
     */
    // Début d'une méthode/d'un bloc
    public static EntityDamage fromEntity(Entity entity, float amount) {
        // Renvoie une valeur à l'appelant
        return new EntityDamage(entity, amount);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static PositionalDamage fromPosition(RegistryKey<DamageType> type, Point sourcePosition, float amount) {
        // Renvoie une valeur à l'appelant
        return new PositionalDamage(type, sourcePosition, amount);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Builds the text sent to a player in his death screen.
     *
     * @param killed the player who has been killed
     * @return the death screen text, null to do not send anything
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Component buildDeathScreenText(Player killed) {
        // Renvoie une valeur à l'appelant
        return Component.translatable("death.attack." + type.messageId());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sound event to play when the given entity is hit by this damage. Possible to return null if no sound should be played
     *
     * @param entity the entity hit by this damage
     * @return the sound to play when the given entity is hurt by this damage type. Can be null if no sound should play
     */
    // Début d'une méthode/d'un bloc
    public @Nullable SoundEvent getSound(LivingEntity entity) {
        // Embranchement : vérifie une condition
        if (entity instanceof Player) {
            // Renvoie une valeur à l'appelant
            return getPlayerSound((Player) entity);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return getGenericSound(entity);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected SoundEvent getGenericSound(LivingEntity entity) {
        // Renvoie une valeur à l'appelant
        return SoundEvent.ENTITY_GENERIC_HURT;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected SoundEvent getPlayerSound(Player player) {
        // Embranchement : vérifie une condition
        if (DamageType.ON_FIRE.equals(typeKey)) return SoundEvent.ENTITY_PLAYER_HURT_ON_FIRE;
        // Renvoie une valeur à l'appelant
        return SoundEvent.ENTITY_PLAYER_HURT;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TagHandler tagHandler() {
        // Renvoie une valeur à l'appelant
        return tagHandler;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getAmount() {
        // Renvoie une valeur à l'appelant
        return amount;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAmount(float amount) {
        // Accès à l'objet courant/parent
        this.amount = amount;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
