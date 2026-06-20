// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.avatar;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.network.player.ResolvableProfile;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class MannequinMeta extends AvatarMeta {
    // Début d'une méthode/d'un bloc
    public MannequinMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ResolvableProfile getProfile() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.PROFILE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setProfile(ResolvableProfile value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.PROFILE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isImmovable() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IMMOVABLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setImmovable(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IMMOVABLE, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Component getDescription() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.DESCRIPTION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setDescription(@Nullable Component value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.DESCRIPTION, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCapeEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_CAPE_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCapeEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IS_CAPE_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isJacketEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_JACKET_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setJacketEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IS_JACKET_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isLeftSleeveEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_LEFT_SLEEVE_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setLeftSleeveEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IS_LEFT_SLEEVE_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isRightSleeveEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_RIGHT_SLEEVE_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setRightSleeveEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IS_RIGHT_SLEEVE_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isLeftLegEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_LEFT_PANTS_LEG_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setLeftLegEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IS_LEFT_PANTS_LEG_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isRightLegEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_RIGHT_PANTS_LEG_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setRightLegEnabled(boolean value) {
        // Appelle une méthode
        metadata.get(MetadataDef.Mannequin.IS_RIGHT_PANTS_LEG_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isHatEnabled() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.IS_HAT_ENABLED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setHatEnabled(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.IS_HAT_ENABLED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte getDisplayedSkinParts() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mannequin.DISPLAYED_MODEL_PARTS_FLAGS);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setDisplayedSkinParts(byte skinDisplayByte) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mannequin.DISPLAYED_MODEL_PARTS_FLAGS, skinDisplayByte);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
