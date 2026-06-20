// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ExplosionPacket;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;
// Import d'une classe nécessaire
import net.minestom.server.sound.SoundEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.WeightedList;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Abstract explosion.
 * Instance can provide a supplier through {@link Instance#setExplosionSupplier}
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class Explosion {

    // Instruction de code
    private final float centerX;
    // Instruction de code
    private final float centerY;
    // Instruction de code
    private final float centerZ;
    // Instruction de code
    private final float strength;

    // Début d'une méthode/d'un bloc
    public Explosion(float centerX, float centerY, float centerZ, float strength) {
        // Accès à l'objet courant/parent
        this.centerX = centerX;
        // Accès à l'objet courant/parent
        this.centerY = centerY;
        // Accès à l'objet courant/parent
        this.centerZ = centerZ;
        // Accès à l'objet courant/parent
        this.strength = strength;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getStrength() {
        // Renvoie une valeur à l'appelant
        return strength;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getCenterX() {
        // Renvoie une valeur à l'appelant
        return centerX;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getCenterY() {
        // Renvoie une valeur à l'appelant
        return centerY;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getCenterZ() {
        // Renvoie une valeur à l'appelant
        return centerZ;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Prepares the list of blocks that will be broken. Also pushes and damage entities affected by this explosion
     *
     * @param instance instance to perform this explosion in
     * @return list of blocks that will be broken.
     */
    // Appelle une méthode
    protected abstract List<Point> prepare(Instance instance);

    /**
     * Performs the explosion and send the corresponding packet
     *
     * @param instance instance to perform this explosion in
     */
    // Début d'une méthode/d'un bloc
    public void apply(Instance instance) {
        // Appelle une méthode
        List<Point> blocks = prepare(instance);
        // Boucle : répète un bloc
        for (final Point pos : blocks) {
            // Appelle une méthode
            instance.setBlock(pos, Block.AIR);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        ExplosionPacket packet = new ExplosionPacket(
                // TODO(1.21.9): explosion update
                // Crée un nouvel objet
                new Vec(centerX, centerY, centerZ), 0, 0, Vec.ZERO,
                // Appelle une méthode
                Particle.EXPLOSION, SoundEvent.ENTITY_GENERIC_EXPLODE, WeightedList.of());
        // Appelle une méthode
        postExplosion(instance, blocks, packet);
        // Appelle une méthode
        PacketSendingUtils.sendGroupedPacket(instance.getPlayers(), packet);

        // Appelle une méthode
        postSend(instance, blocks);
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    protected void postExplosion(Instance instance, List<Point> blocks, ExplosionPacket packet) {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called after sending the explosion packet. Can be used to (re)set blocks that have been destroyed.
     * This is necessary to do after the packet being sent, because the client sets the positions received to air.
     *
     * @param instance the instance in which the explosion occurs
     * @param blocks   the block positions returned by prepare
     */
    // Début d'une méthode/d'un bloc
    protected void postSend(Instance instance, List<Point> blocks) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
