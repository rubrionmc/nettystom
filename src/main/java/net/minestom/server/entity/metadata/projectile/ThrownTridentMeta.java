// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.projectile;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;

// Déclaration de type (classe/interface/enum/record)
public class ThrownTridentMeta extends AbstractArrowMeta {
    // Début d'une méthode/d'un bloc
    public ThrownTridentMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public byte getLoyaltyLevel() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ThrownTrident.LOYALTY_LEVEL);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setLoyaltyLevel(byte value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ThrownTrident.LOYALTY_LEVEL, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isHasEnchantmentGlint() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.ThrownTrident.HAS_ENCHANTMENT_GLINT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setHasEnchantmentGlint(boolean value) {
        // Appelle une méthode
        metadata.set(MetadataDef.ThrownTrident.HAS_ENCHANTMENT_GLINT, value);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
