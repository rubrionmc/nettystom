// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
public interface TranscoderProxy<D> extends Transcoder<D> {
    /**
     * Recursively extracts the delegate from a {@link TranscoderProxy}.
     *
     * @param transcoder The transcoder (possibly proxy) to extract
     * @return The delegate transcoder
     */
    // Début d'une méthode/d'un bloc
    static <D> Transcoder<D> extractDelegate(Transcoder<D> transcoder) {
        // Embranchement : vérifie une condition
        if (transcoder instanceof TranscoderProxy<D> proxy)
            // Renvoie une valeur à l'appelant
            return extractDelegate(proxy.delegate());
        // Renvoie une valeur à l'appelant
        return transcoder;
    // Fin d'un bloc/d'une expression
    }

    /**
     * The delegate to use; This should be considered immutable during its lifetime.
     * @return the {@link Transcoder} delegated for {@link TranscoderProxy}
     */
    // Appelle une méthode
    Transcoder<D> delegate();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createNull() {
        // Renvoie une valeur à l'appelant
        return delegate().createNull();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Boolean> getBoolean(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getBoolean(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createBoolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return delegate().createBoolean(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Byte> getByte(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getByte(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createByte(byte value) {
        // Renvoie une valeur à l'appelant
        return delegate().createByte(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Short> getShort(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getShort(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createShort(short value) {
        // Renvoie une valeur à l'appelant
        return delegate().createShort(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Integer> getInt(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getInt(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createInt(int value) {
        // Renvoie une valeur à l'appelant
        return delegate().createInt(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Long> getLong(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getLong(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createLong(long value) {
        // Renvoie une valeur à l'appelant
        return delegate().createLong(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Float> getFloat(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getFloat(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createFloat(float value) {
        // Renvoie une valeur à l'appelant
        return delegate().createFloat(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<Double> getDouble(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getDouble(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createDouble(double value) {
        // Renvoie une valeur à l'appelant
        return delegate().createDouble(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<String> getString(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getString(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createString(String value) {
        // Renvoie une valeur à l'appelant
        return delegate().createString(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createByteArray(byte[] value) {
        // Renvoie une valeur à l'appelant
        return delegate().createByteArray(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<byte[]> getByteArray(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getByteArray(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createIntArray(int[] value) {
        // Renvoie une valeur à l'appelant
        return delegate().createIntArray(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<int[]> getIntArray(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getIntArray(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default D createLongArray(long[] value) {
        // Renvoie une valeur à l'appelant
        return delegate().createLongArray(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<long[]> getLongArray(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getLongArray(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<List<D>> getList(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getList(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default Result<MapLike<D>> getMap(D value) {
        // Renvoie une valeur à l'appelant
        return delegate().getMap(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default MapBuilder<D> createMap() {
        // Renvoie une valeur à l'appelant
        return delegate().createMap();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default ListBuilder<D> createList(int expectedSize) {
        // Renvoie une valeur à l'appelant
        return delegate().createList(expectedSize);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    default <O> Result<O> convertTo(Transcoder<O> coder, D value) {
        // Renvoie une valeur à l'appelant
        return delegate().convertTo(coder, value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
