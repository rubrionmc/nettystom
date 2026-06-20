// Déclaration du paquet de ce fichier
package net.minestom.server.entity.damage;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;

/**
 * Represents damage that is associated with a certain position.
 */
// Déclaration de type (classe/interface/enum/record)
public class PositionalDamage extends Damage {

    // Début d'une méthode/d'un bloc
    public PositionalDamage(RegistryKey<DamageType> type, Point sourcePosition, float amount) {
        // Accès à l'objet courant/parent
        super(type, null, null, sourcePosition, amount);
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}