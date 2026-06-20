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
public class ZombieNautilusMeta extends AbstractNautilusMeta {
    // Début d'une méthode/d'un bloc
    public ZombieNautilusMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#ZOMBIE_NAUTILUS_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public RegistryKey<ZombieNautilusVariant> getVariant() {
        // Renvoie une valeur à l'appelant
        return this.metadata.get(MetadataDef.ZombieNautilus.VARIANT);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#ZOMBIE_NAUTILUS_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(RegistryKey<ZombieNautilusVariant> value) {
        // Accès à l'objet courant/parent
        this.metadata.set(MetadataDef.ZombieNautilus.VARIANT, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (DataComponents.ZOMBIE_NAUTILUS_VARIANT == component) {
            // Appelle une méthode
            setVariant((RegistryKey<ZombieNautilusVariant>) value);
        // Branche alternative de la condition
        } else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected @Nullable <T> T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (DataComponents.ZOMBIE_NAUTILUS_VARIANT == component) {
            // Renvoie une valeur à l'appelant
            return (T) getVariant();
        // Branche alternative de la condition
        } else return super.get(component);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
