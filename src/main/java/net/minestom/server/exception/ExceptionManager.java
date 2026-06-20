// Déclaration du paquet de ce fichier
package net.minestom.server.exception;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Manages the handling of exceptions.
 */
// Déclaration de type (classe/interface/enum/record)
public final class ExceptionManager {

    // Instruction de code
    private ExceptionHandler exceptionHandler;

    /**
     * Handles an exception, if no {@link ExceptionHandler} is set, it just prints the stack trace.
     *
     * @param e the occurred exception
     */
    // Début d'une méthode/d'un bloc
    public void handleException(Throwable e) {
        // Embranchement : vérifie une condition
        if (e instanceof OutOfMemoryError) {
            // OOM should be handled manually
            // Appelle une méthode
            e.printStackTrace();
            // Appelle une méthode
            MinecraftServer.stopCleanly();
            // Renvoie une valeur à l'appelant
            return;
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.getExceptionHandler().handleException(e);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the exception handler, to allow custom exception handling.
     *
     * @param exceptionHandler the new {@link ExceptionHandler}, can be set to null to apply the default provider
     */
    // Début d'une méthode/d'un bloc
    public void setExceptionHandler(@Nullable ExceptionHandler exceptionHandler) {
        // Accès à l'objet courant/parent
        this.exceptionHandler = exceptionHandler;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieves the current {@link ExceptionHandler}, can be the default one if none is defined.
     *
     * @return the current {@link ExceptionHandler}
     */
    // Début d'une méthode/d'un bloc
    public ExceptionHandler getExceptionHandler() {
        // Renvoie une valeur à l'appelant
        return this.exceptionHandler == null ? exceptionHandler = Throwable::printStackTrace : this.exceptionHandler;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
