// Package declaration for this file
package net.minestom.server.exception;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Manages the handling of exceptions.
 */
// Type declaration (class/interface/enum/record)
public final class ExceptionManager {

    // Code statement
    private ExceptionHandler exceptionHandler;

    /**
     * Handles an exception, if no {@link ExceptionHandler} is set, it just prints the stack trace.
     *
     * @param e the occurred exception
     */
    // Start of a method/block
    public void handleException(Throwable e) {
        // Branch: checks a condition
        if (e instanceof OutOfMemoryError) {
            // OOM should be handled manually
            // Calls a method
            e.printStackTrace();
            // Calls a method
            MinecraftServer.stopCleanly();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Access to the current/parent object
        this.getExceptionHandler().handleException(e);
    // End of a block/expression
    }

    /**
     * Changes the exception handler, to allow custom exception handling.
     *
     * @param exceptionHandler the new {@link ExceptionHandler}, can be set to null to apply the default provider
     */
    // Start of a method/block
    public void setExceptionHandler(@Nullable ExceptionHandler exceptionHandler) {
        // Access to the current/parent object
        this.exceptionHandler = exceptionHandler;
    // End of a block/expression
    }

    /**
     * Retrieves the current {@link ExceptionHandler}, can be the default one if none is defined.
     *
     * @return the current {@link ExceptionHandler}
     */
    // Start of a method/block
    public ExceptionHandler getExceptionHandler() {
        // Returns a value to the caller
        return this.exceptionHandler == null ? exceptionHandler = Throwable::printStackTrace : this.exceptionHandler;
    // End of a block/expression
    }
// End of a block/expression
}
