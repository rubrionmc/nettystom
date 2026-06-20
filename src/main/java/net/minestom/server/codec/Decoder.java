// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;


/**
 * Decoders are interfaces used in {@link Codec} which purpose is to decode any value of {@link T}
 * with a transcoder.
 * <br>
 * For example:
 * <pre>{@code
 * record Name(String imTheBoss) { }
 * Decoder<Name> decoder = new Decoder<>() {
 *     @Override
 *     public <D> Result<Name> decode(Transcoder<D> coder, D value) {
 *         return coder.getString(value).mapResult(Name::new);
 *     }
 * };
 * Result<Name> result = decoder.decode(Transcoder.NBT, StringBinaryTag.stringBinaryTag("me")); // Result.OK(Name("me"))
 * Result<Name> errorResult = decoder.decode(Transcoder.NBT, EndBinaryTag.endBinaryTag()); // Result.Error(...)
 * }</pre>
 *
 * @param <T> the value type
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface Decoder<T extends @UnknownNullability Object> {

    /**
     * Returns a unit decoder of T
     *
     * @param value the value to always return
     * @param <T>   the type of value
     * @return the unit decoder
     */
    // Début d'une méthode/d'un bloc
    static <T> Decoder<T> unit(T value) {
        // Renvoie une valeur à l'appelant
        return new Decoder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<T> decode(Transcoder<D> coder, D ignored) {
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Decodes a value of {@link D} using the specific {@link Transcoder}
     * <br>
     * The {@link Result} will be of {@link Result.Ok} or {@link Result.Error} and its typed {@link T}
     *
     * @param coder the transcoder to use
     * @param value the value to decode
     * @param <D>   The value type
     * @return the {@link Result} of the encoding with its type determined by the transcoder
     */
    // Appelle une méthode
    <D> Result<T> decode(Transcoder<D> coder, D value);

// Fin d'un bloc/d'une expression
}
