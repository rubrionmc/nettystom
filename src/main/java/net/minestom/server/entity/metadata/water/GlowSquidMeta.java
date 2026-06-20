// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class GlowSquidMeta extends AgeableWaterAnimalMeta {
    // Début d'une méthode/d'un bloc
    public GlowSquidMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int getDarkTicksRemaining() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.GlowSquid.DARK_TICKS_REMAINING);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void setDarkTicksRemaining(int ticks) {
        // Appelle une méthode
        metadata.set(MetadataDef.GlowSquid.DARK_TICKS_REMAINING, ticks);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
