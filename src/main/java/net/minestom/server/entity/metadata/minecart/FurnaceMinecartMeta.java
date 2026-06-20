// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.minecart;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class FurnaceMinecartMeta extends AbstractMinecartMeta {
    // Début d'une méthode/d'un bloc
    public FurnaceMinecartMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasFuel() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.MinecartFurnace.HAS_FUEL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasFuel(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.MinecartFurnace.HAS_FUEL, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
