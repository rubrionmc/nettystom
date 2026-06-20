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
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class FireworkRocketMeta extends EntityMeta implements ProjectileMeta {
    // Instruction de code
    private Entity shooter;

    // Début d'une méthode/d'un bloc
    public FireworkRocketMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getFireworkInfo() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.FireworkRocketEntity.ITEM);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFireworkInfo(ItemStack value) {
        // Appelle une méthode
        metadata.set(MetadataDef.FireworkRocketEntity.ITEM, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Integer getShooterEntityId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.FireworkRocketEntity.SHOOTER_ENTITY_ID);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setShooterEntityId(@Nullable Integer value) {
        // Appelle une méthode
        metadata.set(MetadataDef.FireworkRocketEntity.SHOOTER_ENTITY_ID, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getShooter() {
        // Renvoie une valeur à l'appelant
        return this.shooter;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setShooter(@Nullable Entity value) {
        // Accès à l'objet courant/parent
        this.shooter = value;
        // Appelle une méthode
        Integer entityID = value == null ? null : value.getEntityId();
        // Appelle une méthode
        setShooterEntityId(entityID);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isShotAtAngle() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.FireworkRocketEntity.IS_SHOT_AT_ANGLE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShotAtAngle(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.FireworkRocketEntity.IS_SHOT_AT_ANGLE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
