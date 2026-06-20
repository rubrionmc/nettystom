// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface ExplosionSupplier {

    /**
     * Creates a new explosion
     *
     * @param centerX        center X of the explosion
     * @param centerY        center Y of the explosion
     * @param centerZ        center Z of the explosion
     * @param strength       strength of the explosion
     * @param additionalData data passed via {@link Instance#explode(float, float, float, float, CompoundBinaryTag)} )}. Can be null
     * @return Explosion object representing the algorithm to use
     */
    // Calls a method
    Explosion createExplosion(float centerX, float centerY, float centerZ, float strength, @Nullable CompoundBinaryTag additionalData);

// End of a block/expression
}
