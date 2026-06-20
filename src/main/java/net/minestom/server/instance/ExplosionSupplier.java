// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
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
    // Appelle une méthode
    Explosion createExplosion(float centerX, float centerY, float centerZ, float strength, @Nullable CompoundBinaryTag additionalData);

// Fin d'un bloc/d'une expression
}
