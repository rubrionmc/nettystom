// Déclaration du paquet de ce fichier
package net.minestom.server.utils.entity;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;

// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public final class EntityUtils {
    // Affecte une valeur
    private static final Set<EntityType> SITTING_ENTITIES = Set.of(EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED,
            // Instruction de code
            EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE,
            // Instruction de code
            EntityType.ZOMBIFIED_PIGLIN);

    /**
     * @param vehicle the target vehicle
     * @param passenger the target passenger
     * @return the height offset for the passenger of this vehicle
     */
    // Début d'une méthode/d'un bloc
    public static double getPassengerHeightOffset(Entity vehicle, Entity passenger) {
        // TODO: Refactor this in 1.20.5
        // Embranchement : vérifie une condition
        if (vehicle.getEntityType().name().contains("boat")) return -0.1;
        // Embranchement : vérifie une condition
        if (vehicle.getEntityType() == EntityType.MINECART) return 0.0;
        // Embranchement : vérifie une condition
        if (SITTING_ENTITIES.contains(passenger.getEntityType()))
            // Renvoie une valeur à l'appelant
            return vehicle.getBoundingBox().height() * 0.75;
        // Renvoie une valeur à l'appelant
        return vehicle.getBoundingBox().height();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private EntityUtils() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
