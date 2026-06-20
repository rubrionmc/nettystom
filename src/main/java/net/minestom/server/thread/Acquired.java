// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

/**
 * Represents an object that has been safely acquired and can be freed again.
 * <p>
 * This class should not be shared, and it is recommended to call {@link #unlock()}
 * once the acquisition goal has been fulfilled to limit blocking time.
 *
 * @param <T> the type of the acquired object
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Acquired<T> permits AcquiredImpl {
    // Appelle une méthode
    T get();

    // Appelle une méthode
    void unlock();
// Fin d'un bloc/d'une expression
}
