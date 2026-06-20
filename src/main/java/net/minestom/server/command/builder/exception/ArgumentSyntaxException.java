// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.exception;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.ArgumentCallback;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;

/**
 * Exception triggered when an {@link Argument} is wrongly parsed.
 * <p>
 * Retrieved in {@link ArgumentCallback} defined in {@link Command#setArgumentCallback(ArgumentCallback, Argument)}.
 * <p>
 * Be aware that the message returned by {@link #getMessage()} is only here for debugging purpose,
 * you should refer to {@link #getErrorCode()} to identify the exceptions.
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentSyntaxException extends RuntimeException {

    // Instruction de code
    private final String input;
    // Instruction de code
    private final int errorCode;

    // Début d'une méthode/d'un bloc
    public ArgumentSyntaxException(String message, String input, int errorCode) {
        // Accès à l'objet courant/parent
        super(message);
        // Accès à l'objet courant/parent
        this.input = input;
        // Accès à l'objet courant/parent
        this.errorCode = errorCode;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Throwable fillInStackTrace() {
        // Stacktrace is useless to the parser
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the problematic command input.
     *
     * @return the command input which triggered the exception
     */
    // Début d'une méthode/d'un bloc
    public String getInput() {
        // Renvoie une valeur à l'appelant
        return input;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the error code of the exception.
     * <p>
     * The code is decided arbitrary by the argument,
     * check the argument class to know the meaning of each one.
     *
     * @return the argument error code
     */
    // Début d'une méthode/d'un bloc
    public int getErrorCode() {
        // Renvoie une valeur à l'appelant
        return errorCode;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
