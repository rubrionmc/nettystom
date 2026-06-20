// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class GuardianMeta extends MonsterMeta {
    // Instruction de code
    private Entity target;

    // Début d'une méthode/d'un bloc
    public GuardianMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRetractingSpikes() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Guardian.IS_RETRACTING_SPIKES);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRetractingSpikes(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Guardian.IS_RETRACTING_SPIKES, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getTargetEntityId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Guardian.TARGET_EID);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setTargetEntityId(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Guardian.TARGET_EID, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Entity getTarget() {
        // Renvoie une valeur à l'appelant
        return this.target;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTarget(@Nullable Entity target) {
        // Accès à l'objet courant/parent
        this.target = target;
        // Appelle une méthode
        setTargetEntityId(target == null ? 0 : target.getEntityId());
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
