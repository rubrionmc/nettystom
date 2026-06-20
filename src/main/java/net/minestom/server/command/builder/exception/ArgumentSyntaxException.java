// Package declaration for this file
package net.minestom.server.command.builder.exception;

// Import of a required class
import net.minestom.server.command.builder.ArgumentCallback;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;

/**
 * Exception triggered when an {@link Argument} is wrongly parsed.
 * <p>
 * Retrieved in {@link ArgumentCallback} defined in {@link Command#setArgumentCallback(ArgumentCallback, Argument)}.
 * <p>
 * Be aware that the message returned by {@link #getMessage()} is only here for debugging purpose,
 * you should refer to {@link #getErrorCode()} to identify the exceptions.
 */
// Type declaration (class/interface/enum/record)
public class ArgumentSyntaxException extends RuntimeException {

    // Code statement
    private final String input;
    // Code statement
    private final int errorCode;

    // Start of a method/block
    public ArgumentSyntaxException(String message, String input, int errorCode) {
        // Access to the current/parent object
        super(message);
        // Access to the current/parent object
        this.input = input;
        // Access to the current/parent object
        this.errorCode = errorCode;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Throwable fillInStackTrace() {
        // Stacktrace is useless to the parser
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Gets the problematic command input.
     *
     * @return the command input which triggered the exception
     */
    // Start of a method/block
    public String getInput() {
        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    /**
     * Gets the error code of the exception.
     * <p>
     * The code is decided arbitrary by the argument,
     * check the argument class to know the meaning of each one.
     *
     * @return the argument error code
     */
    // Start of a method/block
    public int getErrorCode() {
        // Returns a value to the caller
        return errorCode;
    // End of a block/expression
    }
// End of a block/expression
}
