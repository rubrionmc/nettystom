// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.projectile;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;

// Déclaration de type (classe/interface/enum/record)
public class AbstractArrowMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    protected AbstractArrowMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCritical() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractArrow.IS_CRITICAL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCritical(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractArrow.IS_CRITICAL, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isNoClip() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractArrow.IS_NO_CLIP);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setNoClip(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractArrow.IS_NO_CLIP, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte getPiercingLevel() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractArrow.PIERCING_LEVEL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPiercingLevel(byte value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractArrow.PIERCING_LEVEL, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isInGround() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.AbstractArrow.IN_GROUND);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setInGround(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.AbstractArrow.IN_GROUND, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
