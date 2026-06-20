// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.Function;

/**
 * Results are used in {@link Encoder} and {@link Decoder} to primarily function as a way of passing back exceptions as values.
 * <br>
 * They have two states {@link Ok} and {@link Error}, you can use pattern matching to extract the values
 * or use some of the helper methods provided like {@link #orElseThrow()} or {@link #mapResult(Function)}.
 * <br>
 * To construct simply just do {@code new Result.Ok<>(value) } and {@code new Result.Error<>("Error message!") }
 * <br>
 * You should not rely on the identity of results as they are value candidates.
 *
 * @param <T> the type, can be nullable.
 */
// Type declaration (class/interface/enum/record)
public sealed interface Result<T extends @UnknownNullability Object> {

    /**
     * Represents the {@link Result} was successful.
     *
     * @param value the value of {@link T}
     * @param <T>   the value type
     */
    // Type declaration (class/interface/enum/record)
    record Ok<T extends @UnknownNullability Object>(T value) implements Result<T> {
    // End of a block/expression
    }

    /**
     * Represents the {@link Result} was a failure.
     *
     * @param message the message
     * @param <T>     the type
     */
    // Type declaration (class/interface/enum/record)
    record Error<T>(String message) implements Result<T> {
        // Start of a method/block
        public Error {
            // Calls a method
            Objects.requireNonNull(message, "Message cannot be null");
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Map the {@link Ok} result into the mapper function that creates a new result.
     * Otherwise, returns the error.
     *
     * @param mapper the new result
     * @param <S>    the type of the result.
     * @return the new result or the error.
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default <S extends @UnknownNullability Object> Result<S> map(Function<T, Result<S>> mapper) {
        // Returns a value to the caller
        return this instanceof Ok<T>(T value) ? mapper.apply(value) : cast();
    // End of a block/expression
    }

    /**
     * Maps the {@link Ok} result to the mapper function and creates a new {@link Ok} result
     * Otherwise, returns the error.
     * <br>
     * Similar to {@link #map(Function)} but instead constructs the result instead.
     *
     * @param mapper the new result
     * @param <S>    the type of the result.
     * @return the new result or the error.
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default <S extends @UnknownNullability Object> Result<S> mapResult(Function<T, S> mapper) {
        // Returns a value to the caller
        return this instanceof Ok<T>(T value) ? new Ok<>(mapper.apply(value)) : cast();
    // End of a block/expression
    }

    /**
     * Maps the {@link Error} result to the mapper function and creates a new {@link Error} result
     * Otherwise, returns {@link Ok}.
     * <br>
     * Similar to {@link #map(Function)} but instead constructs the result instead.
     *
     * @param mapper the new result
     * @return the new result or the error.
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default Result<T> mapError(Function<String, String> mapper) {
        // Returns a value to the caller
        return this instanceof Error<?>(String message) ? new Error<>(mapper.apply(message)) : this;
    // End of a block/expression
    }

    /**
     * If the resultant is not {@link Ok}, returns the other value
     *
     * @param other value to be returned
     * @return the resultant
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default @UnknownNullability T orElse(@UnknownNullability T other) {
        // Returns a value to the caller
        return this instanceof Ok<T>(T value)
                // Code statement
                ? value : other;
    // End of a block/expression
    }

    /**
     * Attempts to get the value inside {@link Ok} or throws.
     *
     * @return the value
     * @throws IllegalStateException if this instance of {@link Error}
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default T orElseThrow() {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case Ok<T>(T value) -> value;
            // Multiple branching (switch/case)
            case Error<?>(String errorMessage) -> throw new IllegalArgumentException(errorMessage);
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Attempts to get the value inside {@link Ok} or throws.
     *
     * @param message the message prefix
     * @return the value
     * @throws IllegalStateException if this instance of {@link Error}
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    default T orElseThrow(String message) {
        // Returns a value to the caller
        return switch (this) {
            // Multiple branching (switch/case)
            case Ok<T>(T value) -> value;
            // Multiple branching (switch/case)
            case Error<?>(String errorMessage) -> throw new IllegalArgumentException(
                    // Code statement
                    String.format("%s: %s", message, errorMessage)
            // End of a block/expression
            );
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Casts the error to any type if present.
     * <br>
     * Useful to return the error if it is not the correct type.
     *
     * @param <S> the new result type
     * @return the error
     * @throws ClassCastException if the result is not {@link Error}
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    default <S> Result.Error<S> cast() {
        // Branch: checks a condition
        if (!(this instanceof Result.Error<?>))
            // Throws an exception
            throw new ClassCastException("Cannot cast a Result.Ok to a Result.Error");
        // Returns a value to the caller
        return (Result.Error<S>) this;
    // End of a block/expression
    }

// End of a block/expression
}
