// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public sealed interface Result<T extends @UnknownNullability Object> {

    /**
     * Represents the {@link Result} was successful.
     *
     * @param value the value of {@link T}
     * @param <T>   the value type
     */
    // Déclaration de type (classe/interface/enum/record)
    record Ok<T extends @UnknownNullability Object>(T value) implements Result<T> {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents the {@link Result} was a failure.
     *
     * @param message the message
     * @param <T>     the type
     */
    // Déclaration de type (classe/interface/enum/record)
    record Error<T>(String message) implements Result<T> {
        // Début d'une méthode/d'un bloc
        public Error {
            // Appelle une méthode
            Objects.requireNonNull(message, "Message cannot be null");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Map the {@link Ok} result into the mapper function that creates a new result.
     * Otherwise, returns the error.
     *
     * @param mapper the new result
     * @param <S>    the type of the result.
     * @return the new result or the error.
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default <S extends @UnknownNullability Object> Result<S> map(Function<T, Result<S>> mapper) {
        // Renvoie une valeur à l'appelant
        return this instanceof Ok<T>(T value) ? mapper.apply(value) : cast();
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default <S extends @UnknownNullability Object> Result<S> mapResult(Function<T, S> mapper) {
        // Renvoie une valeur à l'appelant
        return this instanceof Ok<T>(T value) ? new Ok<>(mapper.apply(value)) : cast();
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default Result<T> mapError(Function<String, String> mapper) {
        // Renvoie une valeur à l'appelant
        return this instanceof Error<?>(String message) ? new Error<>(mapper.apply(message)) : this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * If the resultant is not {@link Ok}, returns the other value
     *
     * @param other value to be returned
     * @return the resultant
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default @UnknownNullability T orElse(@UnknownNullability T other) {
        // Renvoie une valeur à l'appelant
        return this instanceof Ok<T>(T value)
                // Instruction de code
                ? value : other;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to get the value inside {@link Ok} or throws.
     *
     * @return the value
     * @throws IllegalStateException if this instance of {@link Error}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default T orElseThrow() {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Ok<T>(T value) -> value;
            // Embranchement multiple (switch/case)
            case Error<?>(String errorMessage) -> throw new IllegalArgumentException(errorMessage);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Attempts to get the value inside {@link Ok} or throws.
     *
     * @param message the message prefix
     * @return the value
     * @throws IllegalStateException if this instance of {@link Error}
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    default T orElseThrow(String message) {
        // Renvoie une valeur à l'appelant
        return switch (this) {
            // Embranchement multiple (switch/case)
            case Ok<T>(T value) -> value;
            // Embranchement multiple (switch/case)
            case Error<?>(String errorMessage) -> throw new IllegalArgumentException(
                    // Instruction de code
                    String.format("%s: %s", message, errorMessage)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    default <S> Result.Error<S> cast() {
        // Embranchement : vérifie une condition
        if (!(this instanceof Result.Error<?>))
            // Lève une exception
            throw new ClassCastException("Cannot cast a Result.Ok to a Result.Error");
        // Renvoie une valeur à l'appelant
        return (Result.Error<S>) this;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
