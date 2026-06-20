// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.MobMeta;

// Déclaration de type (classe/interface/enum/record)
public class SlimeMeta extends MobMeta {
    // Début d'une méthode/d'un bloc
    public SlimeMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSize() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Slime.SIZE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setSize(int value) {
        // Accès à l'objet courant/parent
        this.consumeEntity((entity) -> {
            // Affecte une valeur
            float boxSize = 0.51000005f * value;
            // Appelle une méthode
            entity.setBoundingBox(boxSize, boxSize, boxSize);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        metadata.set(MetadataDef.Slime.SIZE, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
