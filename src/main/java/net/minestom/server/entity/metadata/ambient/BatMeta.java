// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.ambient;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class BatMeta extends AmbientCreatureMeta {
    // Début d'une méthode/d'un bloc
    public BatMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHanging() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Bat.IS_HANGING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHanging(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Bat.IS_HANGING, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
