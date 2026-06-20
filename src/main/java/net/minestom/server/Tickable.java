// Déclaration du paquet de ce fichier
package net.minestom.server;

/**
 * Represents an element which is ticked at a regular interval.
 */
// Déclaration de type (classe/interface/enum/record)
public interface Tickable {

    /**
     * Ticks this element.
     *
     * @param time the time of the tick in milliseconds
     */
    // Appelle une méthode
    void tick(long time);
// Fin d'un bloc/d'une expression
}
