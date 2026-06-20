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
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class InteractionMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    public InteractionMeta(@Nullable Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getWidth() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Interaction.WIDTH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setWidth(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Interaction.WIDTH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public float getHeight() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Interaction.HEIGHT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHeight(float value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Interaction.HEIGHT, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean getResponse() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Interaction.RESPONSIVE);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setResponse(boolean response) {
        // Appelle une méthode
        metadata.set(MetadataDef.Interaction.RESPONSIVE, response);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
