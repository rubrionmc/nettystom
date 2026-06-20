// Package declaration for this file
package net.minestom.server.potion;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.play.EntityEffectPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.RemoveEntityEffectPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

/**
 * Represents a potion effect that can be added to an {@link net.minestom.server.entity.Entity}.
 *
 * @param effect    the potion effect
 * @param amplifier the amplifier starting at 0 (level 1)
 * @param duration  the duration (in ticks) that the potion will last
 * @param flags     the flags of the potion, see {@link #flags()}
 */
// Type declaration (class/interface/enum/record)
public record Potion(PotionEffect effect, int amplifier, int duration, byte flags) {
    /**
     * A flag indicating that this Potion is ambient (it came from a beacon).
     *
     * @see #PARTICLES_FLAG
     * @see #ICON_FLAG
     * @see #flags()
     */
    // Assigns a value
    public static final byte AMBIENT_FLAG = 0x01;

    /**
     * A flag indicating that this Potion has particles.
     *
     * @see #AMBIENT_FLAG
     * @see #ICON_FLAG
     * @see #flags()
     */
    // Assigns a value
    public static final byte PARTICLES_FLAG = 0x02;

    /**
     * A flag indicating that this Potion has an icon.
     *
     * @see #AMBIENT_FLAG
     * @see #PARTICLES_FLAG
     * @see #flags()
     */
    // Assigns a value
    public static final byte ICON_FLAG = 0x04;

    /**
     * A flag instructing the client to use its builtin blending effect, only used with the darkness effect currently.
     */
    // Assigns a value
    public static final byte BLEND_FLAG = 0x08;

    /**
     * A duration constant which sets a Potion duration to infinite.
     */
    // Assigns a value
    public static final int INFINITE_DURATION = -1;

    /**
     * @see #Potion(PotionEffect, int, int, byte)
     */
    // Start of a method/block
    public Potion(PotionEffect effect, int amplifier, int duration, int flags) {
        // Calls a method
        this(effect, amplifier, duration, (byte) flags);
    // End of a block/expression
    }

    /**
     * Creates a new Potion with no flags.
     *
     * @see #Potion(PotionEffect, int, int, byte)
     */
    // Start of a method/block
    public Potion(PotionEffect effect, int amplifier, int duration) {
        // Calls a method
        this(effect, amplifier, duration, (byte) 0);
    // End of a block/expression
    }

    /**
     * Returns the flags that this Potion has.
     *
     * @see #AMBIENT_FLAG
     * @see #PARTICLES_FLAG
     * @see #ICON_FLAG
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte flags() {
        // Returns a value to the caller
        return flags;
    // End of a block/expression
    }

    /**
     * Returns whether this Potion is ambient (it came from a beacon) or not.
     *
     * @return <code>true</code> if the Potion is ambient
     */
    // Start of a method/block
    public boolean isAmbient() {
        // Returns a value to the caller
        return (flags & AMBIENT_FLAG) == AMBIENT_FLAG;
    // End of a block/expression
    }

    /**
     * Returns whether this Potion has particles or not.
     *
     * @return <code>true</code> if the Potion has particles
     */
    // Start of a method/block
    public boolean hasParticles() {
        // Returns a value to the caller
        return (flags & PARTICLES_FLAG) == PARTICLES_FLAG;
    // End of a block/expression
    }

    /**
     * Returns whether this Potion has an icon or not.
     *
     * @return <code>true</code> if the Potion has an icon
     */
    // Start of a method/block
    public boolean hasIcon() {
        // Returns a value to the caller
        return (flags & ICON_FLAG) == ICON_FLAG;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasBlend() {
        // Returns a value to the caller
        return (flags & BLEND_FLAG) == BLEND_FLAG;
    // End of a block/expression
    }

    /**
     * Sends a packet that a potion effect has been applied to the entity.
     * <p>
     * Used internally by {@link net.minestom.server.entity.Player#addEffect(Potion)}
     *
     * @param entity the entity to add the effect to
     */
    // Start of a method/block
    public void sendAddPacket(Entity entity) {
        // Calls a method
        entity.sendPacketToViewersAndSelf(new EntityEffectPacket(entity.getEntityId(), this));
    // End of a block/expression
    }

    /**
     * Sends a packet that a potion effect has been removed from the entity.
     * <p>
     * Used internally by {@link net.minestom.server.entity.Player#removeEffect(PotionEffect)}
     *
     * @param entity the entity to remove the effect from
     */
    // Start of a method/block
    public void sendRemovePacket(Entity entity) {
        // Calls a method
        entity.sendPacketToViewersAndSelf(new RemoveEntityEffectPacket(entity.getEntityId(), effect));
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<Potion> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            PotionEffect.NETWORK_TYPE, Potion::effect,
            // Code statement
            VAR_INT, Potion::amplifier,
            // Code statement
            VAR_INT, Potion::duration,
            // Code statement
            BYTE, Potion::flags,
            // Code statement
            Potion::new
    // End of a block/expression
    );
// End of a block/expression
}
