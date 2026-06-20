// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.avatar;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MainHand;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.LivingEntityMeta;

// Déclaration de type (classe/interface/enum/record)
public class AvatarMeta extends LivingEntityMeta {

    // Début d'une méthode/d'un bloc
    protected AvatarMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }
    
    // Début d'une méthode/d'un bloc
    public MainHand getMainHand() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.MAIN_HAND);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMainHand(MainHand value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.MAIN_HAND, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCapeEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_CAPE_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCapeEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_CAPE_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isJacketEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_JACKET_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setJacketEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_JACKET_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isLeftSleeveEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_LEFT_SLEEVE_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftSleeveEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_LEFT_SLEEVE_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRightSleeveEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_RIGHT_SLEEVE_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightSleeveEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_RIGHT_SLEEVE_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isLeftLegEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_LEFT_PANTS_LEG_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftLegEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_LEFT_PANTS_LEG_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRightLegEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_RIGHT_PANTS_LEG_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightLegEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_RIGHT_PANTS_LEG_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHatEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.IS_HAT_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHatEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.IS_HAT_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte getDisplayedSkinParts() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Avatar.DISPLAYED_MODEL_PARTS_FLAGS);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDisplayedSkinParts(byte skinDisplayByte) {
        // Appelle une méthode
        metadata.set(MetadataDef.Avatar.DISPLAYED_MODEL_PARTS_FLAGS, skinDisplayByte);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
