// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
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
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface Decoder<T extends @UnknownNullability Object> {

    /**
     * Returns a unit decoder of T
     *
     * @param value the value to always return
     * @param <T>   the type of value
     * @return the unit decoder
     */
    // Start of a method/block
    static <T> Decoder<T> unit(T value) {
        // Returns a value to the caller
        return new Decoder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public <D> Result<T> decode(Transcoder<D> coder, D ignored) {
                // Returns a value to the caller
                return new Result.Ok<>(value);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
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
    // Calls a method
    <D> Result<T> decode(Transcoder<D> coder, D value);

// End of a block/expression
}
