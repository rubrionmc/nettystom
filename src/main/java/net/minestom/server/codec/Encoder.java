// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
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
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface Encoder<T extends @UnknownNullability Object> {

    /**
     * Creates an empty encoder that only encodes null
     *
     * @param <T> the encoder type
     * @return the empty encoder
     */
    // Start of a method/block
    static <T> Encoder<T> empty() {
        // Returns a value to the caller
        return new Encoder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
                // Returns a value to the caller
                return new Result.Ok<>(coder.createNull());
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
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
    // Calls a method
    <D> Result<D> encode(Transcoder<D> coder, @Nullable T value);

// End of a block/expression
}
