// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.projectile;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class ArrowMeta extends AbstractArrowMeta implements ObjectDataProvider, ProjectileMeta {
    // Instruction de code
    private Entity shooter;

    // Début d'une méthode/d'un bloc
    public ArrowMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getColor() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Arrow.COLOR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setColor(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Arrow.COLOR, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getShooter() {
        // Renvoie une valeur à l'appelant
        return this.shooter;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setShooter(@Nullable Entity shooter) {
        // Accès à l'objet courant/parent
        this.shooter = shooter;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getObjectData() {
        // Renvoie une valeur à l'appelant
        return this.shooter == null ? 0 : this.shooter.getEntityId();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean requiresVelocityPacketAtSpawn() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
