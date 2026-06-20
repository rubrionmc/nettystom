// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an acquirable element is accessed without proper ownership.
 */
// Déclaration de type (classe/interface/enum/record)
public final class AcquirableOwnershipException extends RuntimeException {
    // Instruction de code
    private final Thread initThread, assignedThread;
    // Instruction de code
    private final Object element;

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Instruction de code
    public AcquirableOwnershipException(Thread initThread, @Nullable Thread assignedThread,
                                        // Début d'une méthode/d'un bloc
                                        Object element) {
        // Accès à l'objet courant/parent
        super(buildMessage(initThread, assignedThread, element));
        // Accès à l'objet courant/parent
        this.initThread = initThread;
        // Accès à l'objet courant/parent
        this.assignedThread = assignedThread;
        // Accès à l'objet courant/parent
        this.element = element;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static String buildMessage(Thread initThread, @Nullable Thread assignedThread,
                                       // Début d'une méthode/d'un bloc
                                       Object value) {
        // Appelle une méthode
        final String valueString = value.toString();
        // Embranchement : vérifie une condition
        if (assignedThread != null) {
            // Renvoie une valeur à l'appelant
            return """
                    Thread ownership assertion failed for %s:
                      Current thread:  %s
                      Assigned thread: %s
                      Problem: The element is assigned to a different thread and is not currently owned.
                      Solution: Use Acquirable#sync() or Acquirable#lock() to acquire ownership before accessing the element.
                    """.formatted(valueString,
                    // Instruction de code
                    Thread.currentThread().getName(),
                    // Instruction de code
                    assignedThread.getName()
            // Fin d'un bloc/d'une expression
            );
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return """
                    Thread ownership assertion failed for %s:
                      Current thread:        %s
                      Initialization thread: %s
                      Problem: The element is not yet initialized and is being accessed from a different thread.
                      Solution: Handle the element in the same thread it has been initialized in until it is fully initialized.
                    """.formatted(valueString,
                    // Instruction de code
                    Thread.currentThread().getName(),
                    // Instruction de code
                    initThread.getName()
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * The thread that initialized the acquirable element.
     */
    // Début d'une méthode/d'un bloc
    public Thread initThread() {
        // Renvoie une valeur à l'appelant
        return initThread;
    // Fin d'un bloc/d'une expression
    }

    /**
     * The thread to which the acquirable element is assigned.
     * May be null if the element is not yet initialized.
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Thread assignedThread() {
        // Renvoie une valeur à l'appelant
        return assignedThread;
    // Fin d'un bloc/d'une expression
    }

    /**
     * The acquirable element that caused the ownership failure.
     */
    // Début d'une méthode/d'un bloc
    public Object element() {
        // Renvoie une valeur à l'appelant
        return element;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
