// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

/**
 * Encoders are interfaces used in {@link Codec} which purpose is to encode any value of {@link T}
 * with a transcoder.
 * <br>
 * For example:
 * <pre>{@code
 * record Name(String imTheBoss) { }
 * Encoder<Name> encoder = new Encoder<>() {
 *     @Override
 *     public <D> Result<D> encode(Transcoder<D> coder, @Nullable Name value) {
 *         if (value == null) return new Result.Error<>("null");
 *         return new Result.Ok<>(coder.createString(value.imTheBoss()));
 *     }
 * };
 * Result<BinaryTag> result = encoder.encode(Transcoder.NBT, new Name("me")); // Result.OK(StringBinaryTag("me"))
 * Result<BinaryTag> errorResult = encoder.encode(Transcoder.NBT, null); // Result.Error("null")
 * }</pre>
 *
 * @param <T> the value type
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface Encoder<T extends @UnknownNullability Object> {

    /**
     * Creates an empty encoder that only encodes null
     *
     * @param <T> the encoder type
     * @return the empty encoder
     */
    // Début d'une méthode/d'un bloc
    static <T> Encoder<T> empty() {
        // Renvoie une valeur à l'appelant
        return new Encoder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(coder.createNull());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Encodes a value of {@link T} using the specific {@link Transcoder}
     * <br>
     * The {@link Result} will be of {@link Result.Ok} or {@link Result.Error} and its typed {@link D}
     *
     * @param coder the transcoder to use
     * @param value the value to encode
     * @param <D>   The resultant type
     * @return the {@link Result} of the encoding with its type determined by the transcoder
     */
    // Appelle une méthode
    <D> Result<D> encode(Transcoder<D> coder, @Nullable T value);

// Fin d'un bloc/d'une expression
}
