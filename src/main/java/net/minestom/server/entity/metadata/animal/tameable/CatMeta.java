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
public class CatMeta extends TameableAnimalMeta {
    // Appelle une méthode
    private static final DyeColor[] DYE_VALUES = DyeColor.values();

    // Début d'une méthode/d'un bloc
    public CatMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public RegistryKey<CatVariant> getVariant() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Cat.VARIANT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(RegistryKey<CatVariant> value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Cat.VARIANT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isLying() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Cat.IS_LYING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLying(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Cat.IS_LYING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRelaxed() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Cat.IS_RELAXED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRelaxed(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Cat.IS_RELAXED, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_COLLAR} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public DyeColor getCollarColor() {
        // Renvoie une valeur à l'appelant
        return DYE_VALUES[metadata.get(MetadataDef.Cat.COLLAR_COLOR)];
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_COLLAR} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setCollarColor(DyeColor value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Cat.COLLAR_COLOR, value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CAT_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVariant();
        // Embranchement : vérifie une condition
        if (component == DataComponents.CAT_SOUND_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) metadata.get(MetadataDef.Cat.SOUND_VARIANT);
        // Embranchement : vérifie une condition
        if (component == DataComponents.CAT_COLLAR)
            // Renvoie une valeur à l'appelant
            return (T) getCollarColor();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CAT_VARIANT)
            // Appelle une méthode
            setVariant((RegistryKey<CatVariant>) value);
        // Embranchement : vérifie une condition
        else if (component == DataComponents.CAT_SOUND_VARIANT) {
            // Appelle une méthode
            metadata.set(MetadataDef.Cat.SOUND_VARIANT, (RegistryKey<CatSoundVariant>) value);
        // Embranchement : vérifie une condition
        } else if (component == DataComponents.CAT_COLLAR)
            // Appelle une méthode
            setCollarColor((DyeColor) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
