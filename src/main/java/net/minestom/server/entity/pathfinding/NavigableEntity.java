// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding;

/**
 * Represents an entity which can use the pathfinder.
 * <p>
 * All pathfinder methods are available with {@link #getNavigator()}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface NavigableEntity {
    // Appelle une méthode
    Navigator getNavigator();
// Fin d'un bloc/d'une expression
}
