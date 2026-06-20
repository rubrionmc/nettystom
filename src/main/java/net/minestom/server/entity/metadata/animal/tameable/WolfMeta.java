// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

// Import d'une classe nécessaire
import net.minestom.server.color.DyeColor;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class WolfMeta extends TameableAnimalMeta {
    // Début d'une méthode/d'un bloc
    public WolfMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isBegging() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wolf.IS_BEGGING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBegging(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wolf.IS_BEGGING, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_COLLAR} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public DyeColor getCollarColor() {
        // Renvoie une valeur à l'appelant
        return DyeColor.values()[metadata.get(MetadataDef.Wolf.COLLAR_COLOR)];
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_COLLAR} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setCollarColor(DyeColor value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wolf.COLLAR_COLOR, value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public long getAngerTime() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wolf.ANGER_TIME);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAngerTime(long value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wolf.ANGER_TIME, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public RegistryKey<WolfVariant> getVariant() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wolf.VARIANT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(RegistryKey<WolfVariant> value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wolf.VARIANT, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_SOUND_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public RegistryKey<WolfSoundVariant> getSoundVariant() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wolf.SOUND_VARIANT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_SOUND_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setSoundVariant(RegistryKey<WolfSoundVariant> value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wolf.SOUND_VARIANT, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.WOLF_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVariant();
        // Embranchement : vérifie une condition
        if (component == DataComponents.WOLF_SOUND_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getSoundVariant();
        // Embranchement : vérifie une condition
        if (component == DataComponents.WOLF_COLLAR)
            // Renvoie une valeur à l'appelant
            return (T) getCollarColor();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.WOLF_VARIANT)
            // Appelle une méthode
            setVariant((RegistryKey<WolfVariant>) value);
        // Embranchement : vérifie une condition
        else if (component == DataComponents.WOLF_SOUND_VARIANT)
            // Appelle une méthode
            setSoundVariant((RegistryKey<WolfSoundVariant>) value);
        // Embranchement : vérifie une condition
        else if (component == DataComponents.WOLF_COLLAR)
            // Appelle une méthode
            setCollarColor((DyeColor) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
