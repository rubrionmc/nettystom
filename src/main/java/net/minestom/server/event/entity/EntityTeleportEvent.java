// Déclaration du paquet de ce fichier
package net.minestom.server.event.entity;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.RelativeFlags;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.EntityEvent;
// Import d'une classe nécessaire
import net.minestom.server.utils.position.PositionUtils;
// Import d'une classe nécessaire
import org.intellij.lang.annotations.MagicConstant;

/**
 * Called with {@link Entity#teleport(Pos)} and its overloads.
 */
// Déclaration de type (classe/interface/enum/record)
public class EntityTeleportEvent implements EntityEvent {

    // Instruction de code
    private final Entity entity;
    // Instruction de code
    private final Pos teleportPosition;
    // Instruction de code
    private final int relativeFlags;

    // Affecte une valeur
    public EntityTeleportEvent(Entity entity, Pos teleportPosition, @MagicConstant(flagsFromClass = RelativeFlags.class) int relativeFlags) {
        // Accès à l'objet courant/parent
        this.entity = entity;
        // Accès à l'objet courant/parent
        this.teleportPosition = teleportPosition;
        // Accès à l'objet courant/parent
        this.relativeFlags = relativeFlags;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return The {@link Entity} that teleported.
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Entity getEntity() {
        // Renvoie une valeur à l'appelant
        return entity;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return The position that the {@link Entity} is about to teleport to. This is an absolute position.
     */
    // Début d'une méthode/d'un bloc
    public Pos getNewPosition() {
        // Renvoie une valeur à l'appelant
        return PositionUtils.getPositionWithRelativeFlags(this.getEntity().getPosition(), getTeleportPosition(), relativeFlags);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return The position that the {@link Entity} is about to teleport to. This may be (partially) relative depending on the flags.
     */
    // Début d'une méthode/d'un bloc
    public Pos getTeleportPosition() {
        // Renvoie une valeur à l'appelant
        return teleportPosition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @return The flags that determine which fields of the position are relative.
     */
    // Annotation pour l'élément suivant
    @MagicConstant(flagsFromClass = RelativeFlags.class)
    // Début d'une méthode/d'un bloc
    public int getRelativeFlags() {
        // Renvoie une valeur à l'appelant
        return relativeFlags;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
