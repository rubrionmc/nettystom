// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class BeeMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public BeeMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isRolling() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Bee.IS_ROLLING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setRolling(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Bee.IS_ROLLING, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasStung() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Bee.HAS_STUNG);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasStung(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Bee.HAS_STUNG, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasNectar() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Bee.HAS_NECTAR);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasNectar(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Bee.HAS_NECTAR, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public long getAngerEndTime() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Bee.ANGER_END_TIME);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setAngerEndTime(long value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Bee.ANGER_END_TIME, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
