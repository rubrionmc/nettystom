// Déclaration du paquet de ce fichier
package net.minestom.server.event;

/**
 * Object containing all the global event listeners.
 */
// Déclaration de type (classe/interface/enum/record)
public final class GlobalEventHandler extends EventNodeImpl<Event> {
    // Début d'une méthode/d'un bloc
    public GlobalEventHandler() {
        // Accès à l'objet courant/parent
        super("global", EventFilter.ALL, null);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
