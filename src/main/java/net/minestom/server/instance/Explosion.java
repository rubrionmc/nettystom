// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.packet.server.play.ExplosionPacket;
// Import of a required class
import net.minestom.server.particle.Particle;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import net.minestom.server.utils.WeightedList;

// Import of a required class
import java.util.List;

/**
 * Abstract explosion.
 * Instance can provide a supplier through {@link Instance#setExplosionSupplier}
 */
// Type declaration (class/interface/enum/record)
public abstract class Explosion {

    // Code statement
    private final float centerX;
    // Code statement
    private final float centerY;
    // Code statement
    private final float centerZ;
    // Code statement
    private final float strength;

    // Start of a method/block
    public Explosion(float centerX, float centerY, float centerZ, float strength) {
        // Access to the current/parent object
        this.centerX = centerX;
        // Access to the current/parent object
        this.centerY = centerY;
        // Access to the current/parent object
        this.centerZ = centerZ;
        // Access to the current/parent object
        this.strength = strength;
    // End of a block/expression
    }

    // Start of a method/block
    public float getStrength() {
        // Returns a value to the caller
        return strength;
    // End of a block/expression
    }

    // Start of a method/block
    public float getCenterX() {
        // Returns a value to the caller
        return centerX;
    // End of a block/expression
    }

    // Start of a method/block
    public float getCenterY() {
        // Returns a value to the caller
        return centerY;
    // End of a block/expression
    }

    // Start of a method/block
    public float getCenterZ() {
        // Returns a value to the caller
        return centerZ;
    // End of a block/expression
    }

    /**
     * Prepares the list of blocks that will be broken. Also pushes and damage entities affected by this explosion
     *
     * @param instance instance to perform this explosion in
     * @return list of blocks that will be broken.
     */
    // Calls a method
    protected abstract List<Point> prepare(Instance instance);

    /**
     * Performs the explosion and send the corresponding packet
     *
     * @param instance instance to perform this explosion in
     */
    // Start of a method/block
    public void apply(Instance instance) {
        // Calls a method
        List<Point> blocks = prepare(instance);
        // Loop: repeats a block
        for (final Point pos : blocks) {
            // Calls a method
            instance.setBlock(pos, Block.AIR);
        // End of a block/expression
        }

        // Assigns a value
        ExplosionPacket packet = new ExplosionPacket(
                // TODO(1.21.9): explosion update
                // Creates a new object
                new Vec(centerX, centerY, centerZ), 0, 0, Vec.ZERO,
                // Calls a method
                Particle.EXPLOSION, SoundEvent.ENTITY_GENERIC_EXPLODE, WeightedList.of());
        // Calls a method
        postExplosion(instance, blocks, packet);
        // Calls a method
        PacketSendingUtils.sendGroupedPacket(instance.getPlayers(), packet);

        // Calls a method
        postSend(instance, blocks);
    // End of a block/expression
    }

    /**
     * Called after removing blocks and preparing the packet, but before sending it.
     *
     * @param instance the instance in which the explosion occurs
     * @param blocks   the block positions returned by prepare
     * @param packet   the explosion packet to sent to the client. Be careful with what you're doing.
     *                 It is initialized with the center and radius of the explosion. The positions in 'blocks' are also
     *                 stored in the packet before this call, but you are free to modify 'records' to modify the blocks sent to the client.
     *                 Just be careful, you might just crash the server or the client. Or you're lucky, both at the same time.
     */
    // Start of a method/block
    protected void postExplosion(Instance instance, List<Point> blocks, ExplosionPacket packet) {
    // End of a block/expression
    }

    /**
     * Called after sending the explosion packet. Can be used to (re)set blocks that have been destroyed.
     * This is necessary to do after the packet being sent, because the client sets the positions received to air.
     *
     * @param instance the instance in which the explosion occurs
     * @param blocks   the block positions returned by prepare
     */
    // Start of a method/block
    protected void postSend(Instance instance, List<Point> blocks) {
    // End of a block/expression
    }
// End of a block/expression
}
