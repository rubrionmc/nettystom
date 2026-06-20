// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import java.util.List;

/**
 * Proxies all transcoder calls to the given delegate. Exists to allow passing context into
 * codec implementations by creating {@link Transcoder} subclasses.
 *
 * <p>Note: TranscoderProxy has some special handling assuming that <i>all</i> calls are forwarded.
 * If that is not the case, you should inherit from Transcoder itself and NOT TranscoderProxy.</p>
 *
 * @param <D> the type of the delegate transcoder
 */
// Type declaration (class/interface/enum/record)
public interface TranscoderProxy<D> extends Transcoder<D> {
    /**
     * Recursively extracts the delegate from a {@link TranscoderProxy}.
     *
     * @param transcoder The transcoder (possibly proxy) to extract
     * @return The delegate transcoder
     */
    // Start of a method/block
    static <D> Transcoder<D> extractDelegate(Transcoder<D> transcoder) {
        // Branch: checks a condition
        if (transcoder instanceof TranscoderProxy<D> proxy)
            // Returns a value to the caller
            return extractDelegate(proxy.delegate());
        // Returns a value to the caller
        return transcoder;
    // End of a block/expression
    }

    /**
     * The delegate to use; This should be considered immutable during its lifetime.
     * @return the {@link Transcoder} delegated for {@link TranscoderProxy}
     */
    // Calls a method
    Transcoder<D> delegate();

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createNull() {
        // Returns a value to the caller
        return delegate().createNull();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Boolean> getBoolean(D value) {
        // Returns a value to the caller
        return delegate().getBoolean(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createBoolean(boolean value) {
        // Returns a value to the caller
        return delegate().createBoolean(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Byte> getByte(D value) {
        // Returns a value to the caller
        return delegate().getByte(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createByte(byte value) {
        // Returns a value to the caller
        return delegate().createByte(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Short> getShort(D value) {
        // Returns a value to the caller
        return delegate().getShort(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createShort(short value) {
        // Returns a value to the caller
        return delegate().createShort(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Integer> getInt(D value) {
        // Returns a value to the caller
        return delegate().getInt(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createInt(int value) {
        // Returns a value to the caller
        return delegate().createInt(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Long> getLong(D value) {
        // Returns a value to the caller
        return delegate().getLong(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createLong(long value) {
        // Returns a value to the caller
        return delegate().createLong(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Float> getFloat(D value) {
        // Returns a value to the caller
        return delegate().getFloat(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createFloat(float value) {
        // Returns a value to the caller
        return delegate().createFloat(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<Double> getDouble(D value) {
        // Returns a value to the caller
        return delegate().getDouble(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createDouble(double value) {
        // Returns a value to the caller
        return delegate().createDouble(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<String> getString(D value) {
        // Returns a value to the caller
        return delegate().getString(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createString(String value) {
        // Returns a value to the caller
        return delegate().createString(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createByteArray(byte[] value) {
        // Returns a value to the caller
        return delegate().createByteArray(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<byte[]> getByteArray(D value) {
        // Returns a value to the caller
        return delegate().getByteArray(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createIntArray(int[] value) {
        // Returns a value to the caller
        return delegate().createIntArray(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<int[]> getIntArray(D value) {
        // Returns a value to the caller
        return delegate().getIntArray(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default D createLongArray(long[] value) {
        // Returns a value to the caller
        return delegate().createLongArray(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<long[]> getLongArray(D value) {
        // Returns a value to the caller
        return delegate().getLongArray(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<List<D>> getList(D value) {
        // Returns a value to the caller
        return delegate().getList(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default Result<MapLike<D>> getMap(D value) {
        // Returns a value to the caller
        return delegate().getMap(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default MapBuilder<D> createMap() {
        // Returns a value to the caller
        return delegate().createMap();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default ListBuilder<D> createList(int expectedSize) {
        // Returns a value to the caller
        return delegate().createList(expectedSize);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    default <O> Result<O> convertTo(Transcoder<O> coder, D value) {
        // Returns a value to the caller
        return delegate().convertTo(coder, value);
    // End of a block/expression
    }
// End of a block/expression
}
