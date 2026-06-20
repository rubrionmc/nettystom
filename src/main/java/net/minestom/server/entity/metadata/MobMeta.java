// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class MobMeta extends LivingEntityMeta {
    // Début d'une méthode/d'un bloc
    protected MobMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isNoAi() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mob.NO_AI);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setNoAi(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mob.NO_AI, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isLeftHanded() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mob.IS_LEFT_HANDED);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLeftHanded(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mob.IS_LEFT_HANDED, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isAggressive() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Mob.IS_AGGRESSIVE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAggressive(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Mob.IS_AGGRESSIVE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
