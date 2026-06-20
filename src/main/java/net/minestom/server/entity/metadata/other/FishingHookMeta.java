// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.EntityMeta;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class FishingHookMeta extends EntityMeta implements ObjectDataProvider {
    // Instruction de code
    private Entity hooked;
    // Instruction de code
    private Entity owner;

    // Début d'une méthode/d'un bloc
    public FishingHookMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getHookedEntityId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.FishingHook.HOOKED);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setHookedEntityId(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.FishingHook.HOOKED, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getHookedEntity() {
        // Renvoie une valeur à l'appelant
        return this.hooked;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHookedEntity(@Nullable Entity value) {
        // Accès à l'objet courant/parent
        this.hooked = value;
        // Appelle une méthode
        int entityID = value == null ? 0 : value.getEntityId() + 1;
        // Appelle une méthode
        setHookedEntityId(entityID);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isCatchable() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.FishingHook.IS_CATCHABLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCatchable(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.FishingHook.IS_CATCHABLE, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getOwnerEntity() {
        // Renvoie une valeur à l'appelant
        return owner;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setOwnerEntity(@Nullable Entity value) {
        // Accès à l'objet courant/parent
        this.owner = value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getObjectData() {
        // Renvoie une valeur à l'appelant
        return owner != null ? owner.getEntityId() : 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean requiresVelocityPacketAtSpawn() {
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
