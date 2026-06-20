// Déclaration du paquet de ce fichier
package net.minestom.server.potion;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.EntityEffectPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.RemoveEntityEffectPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

/**
 * Represents a potion effect that can be added to an {@link net.minestom.server.entity.Entity}.
 *
 * @param effect    the potion effect
 * @param amplifier the amplifier starting at 0 (level 1)
 * @param duration  the duration (in ticks) that the potion will last
 * @param flags     the flags of the potion, see {@link #flags()}
 */
// Déclaration de type (classe/interface/enum/record)
public record Potion(PotionEffect effect, int amplifier, int duration, byte flags) {
    /**
     * A flag indicating that this Potion is ambient (it came from a beacon).
     *
     * @see #PARTICLES_FLAG
     * @see #ICON_FLAG
     * @see #flags()
     */
    // Affecte une valeur
    public static final byte AMBIENT_FLAG = 0x01;

    /**
     * A flag indicating that this Potion has particles.
     *
     * @see #AMBIENT_FLAG
     * @see #ICON_FLAG
     * @see #flags()
     */
    // Affecte une valeur
    public static final byte PARTICLES_FLAG = 0x02;

    /**
     * A flag indicating that this Potion has an icon.
     *
     * @see #AMBIENT_FLAG
     * @see #PARTICLES_FLAG
     * @see #flags()
     */
    // Affecte une valeur
    public static final byte ICON_FLAG = 0x04;

    /**
     * A flag instructing the client to use its builtin blending effect, only used with the darkness effect currently.
     */
    // Affecte une valeur
    public static final byte BLEND_FLAG = 0x08;

    /**
     * A duration constant which sets a Potion duration to infinite.
     */
    // Affecte une valeur
    public static final int INFINITE_DURATION = -1;

    /**
     * @see #Potion(PotionEffect, int, int, byte)
     */
    // Début d'une méthode/d'un bloc
    public Potion(PotionEffect effect, int amplifier, int duration, int flags) {
        // Appelle une méthode
        this(effect, amplifier, duration, (byte) flags);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new Potion with no flags.
     *
     * @see #Potion(PotionEffect, int, int, byte)
     */
    // Début d'une méthode/d'un bloc
    public Potion(PotionEffect effect, int amplifier, int duration) {
        // Appelle une méthode
        this(effect, amplifier, duration, (byte) 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the flags that this Potion has.
     *
     * @see #AMBIENT_FLAG
     * @see #PARTICLES_FLAG
     * @see #ICON_FLAG
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte flags() {
        // Renvoie une valeur à l'appelant
        return flags;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns whether this Potion is ambient (it came from a beacon) or not.
     *
     * @return <code>true</code> if the Potion is ambient
     */
    // Début d'une méthode/d'un bloc
    public boolean isAmbient() {
        // Renvoie une valeur à l'appelant
        return (flags & AMBIENT_FLAG) == AMBIENT_FLAG;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns whether this Potion has particles or not.
     *
     * @return <code>true</code> if the Potion has particles
     */
    // Début d'une méthode/d'un bloc
    public boolean hasParticles() {
        // Renvoie une valeur à l'appelant
        return (flags & PARTICLES_FLAG) == PARTICLES_FLAG;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns whether this Potion has an icon or not.
     *
     * @return <code>true</code> if the Potion has an icon
     */
    // Début d'une méthode/d'un bloc
    public boolean hasIcon() {
        // Renvoie une valeur à l'appelant
        return (flags & ICON_FLAG) == ICON_FLAG;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean hasBlend() {
        // Renvoie une valeur à l'appelant
        return (flags & BLEND_FLAG) == BLEND_FLAG;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a packet that a potion effect has been applied to the entity.
     * <p>
     * Used internally by {@link net.minestom.server.entity.Player#addEffect(Potion)}
     *
     * @param entity the entity to add the effect to
     */
    // Début d'une méthode/d'un bloc
    public void sendAddPacket(Entity entity) {
        // Appelle une méthode
        entity.sendPacketToViewersAndSelf(new EntityEffectPacket(entity.getEntityId(), this));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a packet that a potion effect has been removed from the entity.
     * <p>
     * Used internally by {@link net.minestom.server.entity.Player#removeEffect(PotionEffect)}
     *
     * @param entity the entity to remove the effect from
     */
    // Début d'une méthode/d'un bloc
    public void sendRemovePacket(Entity entity) {
        // Appelle une méthode
        entity.sendPacketToViewersAndSelf(new RemoveEntityEffectPacket(entity.getEntityId(), effect));
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<Potion> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            PotionEffect.NETWORK_TYPE, Potion::effect,
            // Instruction de code
            VAR_INT, Potion::amplifier,
            // Instruction de code
            VAR_INT, Potion::duration,
            // Instruction de code
            BYTE, Potion::flags,
            // Instruction de code
            Potion::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
