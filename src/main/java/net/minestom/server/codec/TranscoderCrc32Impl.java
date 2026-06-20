// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.nio.ByteBuffer;
// Import d'une classe nécessaire
import java.nio.ByteOrder;
// Import d'une classe nécessaire
import java.util.Comparator;
// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.zip.CRC32C;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class TranscoderCrc32Impl implements Transcoder<Integer> {
    // Appelle une méthode
    static final TranscoderCrc32Impl INSTANCE = new TranscoderCrc32Impl();

    // Appelle une méthode
    private static final Comparator<Map.Entry<Integer, Integer>> KEY_COMPARATOR = Map.Entry.comparingByKey(Comparator.comparingLong(Integer::toUnsignedLong));
    // Appelle une méthode
    private static final Comparator<Map.Entry<Integer, Integer>> VALUE_COMPARATOR = Map.Entry.comparingByValue(Comparator.comparingLong(Integer::toUnsignedLong));
    // Appelle une méthode
    private static final Comparator<Map.Entry<Integer, Integer>> MAP_COMPARATOR = KEY_COMPARATOR.thenComparing(VALUE_COMPARATOR);

    // Affecte une valeur
    private static final byte TAG_EMPTY = 1;
    // Affecte une valeur
    private static final byte TAG_MAP_START = 2;
    // Affecte une valeur
    private static final byte TAG_MAP_END = 3;
    // Affecte une valeur
    private static final byte TAG_LIST_START = 4;
    // Affecte une valeur
    private static final byte TAG_LIST_END = 5;
    // Affecte une valeur
    private static final byte TAG_BYTE = 6;
    // Affecte une valeur
    private static final byte TAG_SHORT = 7;
    // Affecte une valeur
    private static final byte TAG_INT = 8;
    // Affecte une valeur
    private static final byte TAG_LONG = 9;
    // Affecte une valeur
    private static final byte TAG_FLOAT = 10;
    // Affecte une valeur
    private static final byte TAG_DOUBLE = 11;
    // Affecte une valeur
    private static final byte TAG_STRING = 12;
    // Affecte une valeur
    private static final byte TAG_BOOLEAN = 13;
    // Affecte une valeur
    private static final byte TAG_BYTE_ARRAY_START = 14;
    // Affecte une valeur
    private static final byte TAG_BYTE_ARRAY_END = 15;
    // Affecte une valeur
    private static final byte TAG_INT_ARRAY_START = 16;
    // Affecte une valeur
    private static final byte TAG_INT_ARRAY_END = 17;
    // Affecte une valeur
    private static final byte TAG_LONG_ARRAY_START = 18;
    // Affecte une valeur
    private static final byte TAG_LONG_ARRAY_END = 19;

    // Appelle une méthode
    private static final int EMPTY = new Hasher().putByte(TAG_EMPTY).hash();
    // Appelle une méthode
    private static final int EMPTY_MAP = new Hasher().putByte(TAG_MAP_START).putByte(TAG_MAP_END).hash();
    // Appelle une méthode
    private static final int EMPTY_LIST = new Hasher().putByte(TAG_LIST_START).putByte(TAG_LIST_END).hash();
    // Appelle une méthode
    private static final int FALSE = new Hasher().putByte(TAG_BOOLEAN).putByte((byte) 0).hash();
    // Appelle une méthode
    private static final int TRUE = new Hasher().putByte(TAG_BOOLEAN).putByte((byte) 1).hash();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createNull() {
        // Renvoie une valeur à l'appelant
        return EMPTY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createBoolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return value ? TRUE : FALSE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createByte(byte value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_BYTE).putByte(value).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createShort(short value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_SHORT).putShort(value).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createInt(int value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_INT).putInt(value).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createLong(long value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_LONG).putLong(value).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createFloat(float value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_FLOAT).putFloat(value).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createDouble(double value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_DOUBLE).putDouble(value).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createString(String value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_STRING)
                // Instruction de code
                .putInt(value.length())
                // Instruction de code
                .putChars(value)
                // Appelle une méthode
                .hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer emptyList() {
        // Renvoie une valeur à l'appelant
        return EMPTY_LIST;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ListBuilder<Integer> createList(int expectedSize) {
        // Appelle une méthode
        final Hasher hasher = new Hasher().putByte(TAG_LIST_START);
        // Renvoie une valeur à l'appelant
        return new ListBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public ListBuilder<Integer> add(Integer value) {
                // Appelle une méthode
                hasher.putIntBytes(value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Integer build() {
                // Renvoie une valeur à l'appelant
                return hasher.putByte(TAG_LIST_END).hash();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer emptyMap() {
        // Renvoie une valeur à l'appelant
        return EMPTY_MAP;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public MapBuilder<Integer> createMap() {
        // Affecte une valeur
        final HashMap<Integer, Integer> map = new HashMap<>();
        // Renvoie une valeur à l'appelant
        return new MapBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<Integer> put(Integer key, Integer value) {
                // Embranchement : vérifie une condition
                if (value != EMPTY)
                    // Appelle une méthode
                    map.put(key, value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<Integer> put(String key, Integer value) {
                // Renvoie une valeur à l'appelant
                return put(createString(key), value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Integer build() {
                // Embranchement : vérifie une condition
                if (map.isEmpty()) return EMPTY_MAP;
                // Appelle une méthode
                final Hasher hasher = new Hasher().putByte(TAG_MAP_START);
                // Début d'une méthode/d'un bloc
                map.entrySet().stream().sorted(MAP_COMPARATOR).forEach(entry -> {
                    // Appelle une méthode
                    hasher.putIntBytes(entry.getKey());
                    // Appelle une méthode
                    hasher.putIntBytes(entry.getValue());
                // Fin d'un bloc/d'une expression
                });
                // Renvoie une valeur à l'appelant
                return hasher.putByte(TAG_MAP_END).hash();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createByteArray(byte[] value) {
        // Renvoie une valeur à l'appelant
        return new Hasher().putByte(TAG_BYTE_ARRAY_START)
                // Appelle une méthode
                .putBytes(value).putByte(TAG_BYTE_ARRAY_END).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createIntArray(int[] value) {
        // Appelle une méthode
        final Hasher hasher = new Hasher().putByte(TAG_INT_ARRAY_START);
        // Boucle : répète un bloc
        for (final int item : value) hasher.putInt(item);
        // Renvoie une valeur à l'appelant
        return hasher.putByte(TAG_INT_ARRAY_END).hash();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Integer createLongArray(long[] value) {
        // Appelle une méthode
        final Hasher hasher = new Hasher().putByte(TAG_LONG_ARRAY_START);
        // Boucle : répète un bloc
        for (final long item : value) hasher.putLong(item);
        // Renvoie une valeur à l'appelant
        return hasher.putByte(TAG_LONG_ARRAY_END).hash();
    // Fin d'un bloc/d'une expression
    }

    // Noop read implementation below

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Boolean> getBoolean(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Byte> getByte(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Short> getShort(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Integer> getInt(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Long> getLong(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Float> getFloat(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Double> getDouble(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<String> getString(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<byte[]> getByteArray(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<int[]> getIntArray(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<long[]> getLongArray(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<List<Integer>> getList(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<MapLike<Integer>> getMap(Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <O> Result<O> convertTo(Transcoder<O> coder, Integer value) {
        // Renvoie une valeur à l'appelant
        return writeOnly();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static <T> Result<T> writeOnly() {
        // Renvoie une valeur à l'appelant
        return new Result.Error<>("CRC32 transcoder only supports encoding");
    // Fin d'un bloc/d'une expression
    }


    // Loosely based on the Hasher implementation from Guava, licensed under the Apache 2.0 license.
    // Déclaration de type (classe/interface/enum/record)
    private record Hasher(CRC32C crc32, ByteBuffer buffer) {
        // Début d'une méthode/d'un bloc
        public Hasher() {
            // Appelle une méthode
            this(new CRC32C(), ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private Hasher update(int bytes) {
            // Appelle une méthode
            crc32.update(buffer.array(), 0, bytes);
            // Appelle une méthode
            buffer.position(0);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putByte(byte b) {
            // Appelle une méthode
            crc32.update(b);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putShort(short s) {
            // Appelle une méthode
            buffer.putShort(s);
            // Renvoie une valeur à l'appelant
            return update(Short.BYTES);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putInt(int i) {
            // Appelle une méthode
            buffer.putInt(i);
            // Renvoie une valeur à l'appelant
            return update(Integer.BYTES);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putIntBytes(int i) {
            // Appelle une méthode
            putByte((byte) i);
            // Appelle une méthode
            putByte((byte) (i >> 8));
            // Appelle une méthode
            putByte((byte) (i >> 16));
            // Appelle une méthode
            putByte((byte) (i >> 24));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putLong(long l) {
            // Appelle une méthode
            buffer.putLong(l);
            // Renvoie une valeur à l'appelant
            return update(Long.BYTES);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putFloat(float f) {
            // Renvoie une valeur à l'appelant
            return putInt(Float.floatToRawIntBits(f));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putDouble(double d) {
            // Renvoie une valeur à l'appelant
            return putLong(Double.doubleToRawLongBits(d));
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putChar(char c) {
            // Accès à l'objet courant/parent
            this.putByte((byte) c);
            // Accès à l'objet courant/parent
            this.putByte((byte) (c >>> 8));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putChars(String string) {
            // Boucle : répète un bloc
            for (int i = 0; i < string.length(); ++i)
                // Accès à l'objet courant/parent
                this.putChar(string.charAt(i));
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Hasher putBytes(byte[] bytes) {
            // Appelle une méthode
            crc32.update(bytes);
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int hash() {
            // Renvoie une valeur à l'appelant
            return (int) crc32.getValue();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
