// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.LivingEntityMeta;

// Déclaration de type (classe/interface/enum/record)
public class ArmorStandMeta extends LivingEntityMeta {
    // Début d'une méthode/d'un bloc
    public ArmorStandMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isSmall() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.IS_SMALL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSmall(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.IS_SMALL, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasArms() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.HAS_ARMS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasArms(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.HAS_ARMS, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasNoBasePlate() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.HAS_NO_BASE_PLATE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasNoBasePlate(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.HAS_NO_BASE_PLATE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isMarker() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.IS_MARKER);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMarker(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.IS_MARKER, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getHeadRotation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.HEAD_ROTATION).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHeadRotation(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.HEAD_ROTATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getBodyRotation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.BODY_ROTATION).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBodyRotation(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.BODY_ROTATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getLeftArmRotation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.LEFT_ARM_ROTATION).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftArmRotation(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.LEFT_ARM_ROTATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getRightArmRotation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.RIGHT_ARM_ROTATION).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightArmRotation(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.RIGHT_ARM_ROTATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getLeftLegRotation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.LEFT_LEG_ROTATION).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftLegRotation(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.LEFT_LEG_ROTATION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Vec getRightLegRotation() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ArmorStand.RIGHT_LEG_ROTATION).asVec();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightLegRotation(Vec value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ArmorStand.RIGHT_LEG_ROTATION, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
