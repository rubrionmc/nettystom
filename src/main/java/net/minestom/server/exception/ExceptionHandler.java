// Package declaration for this file
package net.minestom.server.exception;

/**
 * Used when you want to implement your own exception handling, instead of just printing the stack trace.
 * <p>
 * Sets with {@link ExceptionManager#setExceptionHandler(ExceptionHandler)}.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface ExceptionHandler {

    /**
     * Called when an exception was caught.
     *
     * @param e the thrown exception
     */
    // Calls a method
    void handleException(Throwable e);
// End of a block/expression
}
