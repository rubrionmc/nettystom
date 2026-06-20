// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

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
public class ChickenMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public ChickenMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }


    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CHICKEN_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public RegistryKey<ChickenVariant> getVariant() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Chicken.VARIANT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CHICKEN_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(RegistryKey<ChickenVariant> variant) {
        // Appelle une méthode
        metadata.set(MetadataDef.Chicken.VARIANT, variant);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.CHICKEN_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVariant();
        // Embranchement : vérifie une condition
        if (component == DataComponents.CHICKEN_SOUND_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) metadata.get(MetadataDef.Chicken.SOUND_VARIANT);
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
        if (component == DataComponents.CHICKEN_VARIANT)
            // Appelle une méthode
            setVariant((RegistryKey<ChickenVariant>) value);
        // Embranchement : vérifie une condition
        else if (component == DataComponents.CHICKEN_SOUND_VARIANT)
            // Appelle une méthode
            metadata.set(MetadataDef.Chicken.SOUND_VARIANT, (RegistryKey<ChickenSoundVariant>) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
