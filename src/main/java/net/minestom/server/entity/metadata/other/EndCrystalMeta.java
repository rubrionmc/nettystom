// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
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
public class EndCrystalMeta extends EntityMeta {
    // Début d'une méthode/d'un bloc
    public EndCrystalMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable Point getBeamTarget() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.EndCrystal.BEAM_TARGET);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setBeamTarget(@Nullable Point value) {
        // Appelle une méthode
        metadata.set(MetadataDef.EndCrystal.BEAM_TARGET, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isShowingBottom() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.EndCrystal.SHOW_BOTTOM);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setShowingBottom(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.EndCrystal.SHOW_BOTTOM, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
