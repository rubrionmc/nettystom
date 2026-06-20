// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.monster;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class WitherMeta extends MonsterMeta {
    // Instruction de code
    private Entity centerHead;
    // Instruction de code
    private Entity leftHead;
    // Instruction de code
    private Entity rightHead;

    // Début d'une méthode/d'un bloc
    public WitherMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getCenterHeadEntityId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wither.CENTER_HEAD_TARGET);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setCenterHeadEntityId(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wither.CENTER_HEAD_TARGET, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getCenterHead() {
        // Renvoie une valeur à l'appelant
        return this.centerHead;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setCenterHead(@Nullable Entity value) {
        // Accès à l'objet courant/parent
        this.centerHead = value;
        // Appelle une méthode
        setCenterHeadEntityId(value == null ? 0 : value.getEntityId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getLeftHeadEntityId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wither.LEFT_HEAD_TARGET);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setLeftHeadEntityId(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wither.LEFT_HEAD_TARGET, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getLeftHead() {
        // Renvoie une valeur à l'appelant
        return this.leftHead;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftHead(@Nullable Entity value) {
        // Accès à l'objet courant/parent
        this.leftHead = value;
        // Appelle une méthode
        setLeftHeadEntityId(value == null ? 0 : value.getEntityId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getRightHeadEntityId() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wither.RIGHT_HEAD_TARGET);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public void setRightHeadEntityId(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wither.RIGHT_HEAD_TARGET, value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Entity getRightHead() {
        // Renvoie une valeur à l'appelant
        return this.rightHead;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRightHead(@Nullable Entity value) {
        // Accès à l'objet courant/parent
        this.rightHead = value;
        // Appelle une méthode
        setRightHeadEntityId(value == null ? 0 : value.getEntityId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getInvulnerableTime() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Wither.INVULNERABLE_TIME);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setInvulnerableTime(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Wither.INVULNERABLE_TIME, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
