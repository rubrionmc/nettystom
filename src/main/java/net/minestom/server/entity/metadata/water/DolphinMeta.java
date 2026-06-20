// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class DolphinMeta extends AgeableWaterAnimalMeta {
    // Début d'une méthode/d'un bloc
    public DolphinMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Point getTreasurePosition() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Dolphin.TREASURE_POSITION);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setTreasurePosition(Point value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Dolphin.TREASURE_POSITION, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasFish() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Dolphin.HAS_FISH);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasFish(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Dolphin.HAS_FISH, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getMoistureLevel() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Dolphin.MOISTURE_LEVEL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setMoistureLevel(int value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Dolphin.MOISTURE_LEVEL, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
