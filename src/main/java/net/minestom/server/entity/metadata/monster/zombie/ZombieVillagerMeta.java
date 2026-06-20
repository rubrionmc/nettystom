// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster.zombie;

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
import net.minestom.server.entity.VillagerType;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.villager.VillagerMeta;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class ZombieVillagerMeta extends ZombieMeta {
    // Début d'une méthode/d'un bloc
    public ZombieVillagerMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isConverting() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ZombieVillager.IS_CONVERTING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setConverting(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ZombieVillager.IS_CONVERTING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public VillagerMeta.VillagerData getVillagerData() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ZombieVillager.VILLAGER_DATA);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setVillagerData(VillagerMeta.VillagerData data) {
        // Appelle une méthode
        metadata.set(MetadataDef.ZombieVillager.VILLAGER_DATA, data);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFinalized() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ZombieVillager.IS_FINALIZED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFinalized(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ZombieVillager.IS_FINALIZED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.VILLAGER_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVillagerData().type();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.VILLAGER_VARIANT)
            // Appelle une méthode
            setVillagerData(getVillagerData().withType((VillagerType) value));
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
