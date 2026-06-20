// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.flying;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class PhantomMeta extends FlyingMeta {
    // Début d'une méthode/d'un bloc
    public PhantomMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSize() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Phantom.SIZE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSize(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Phantom.SIZE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
