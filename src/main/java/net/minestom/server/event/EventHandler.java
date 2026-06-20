// Déclaration du paquet de ce fichier
package net.minestom.server.event;

/**
 * Represents an element which can have {@link Event} listeners assigned to it.
 */
// Déclaration de type (classe/interface/enum/record)
public interface EventHandler<T extends Event> {
    // Appelle une méthode
    EventNode<T> eventNode();
// Fin d'un bloc/d'une expression
}
