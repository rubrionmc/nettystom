// Déclaration du paquet de ce fichier
package net.minestom.server.exception;

/**
 * Used when you want to implement your own exception handling, instead of just printing the stack trace.
 * <p>
 * Sets with {@link ExceptionManager#setExceptionHandler(ExceptionHandler)}.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface ExceptionHandler {

    /**
     * Called when an exception was caught.
     *
     * @param e the thrown exception
     */
    // Appelle une méthode
    void handleException(Throwable e);
// Fin d'un bloc/d'une expression
}
