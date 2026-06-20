// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.nio.ByteBuffer;
// Import of a required class
import java.nio.ByteOrder;
// Import of a required class
import java.util.Comparator;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.zip.CRC32C;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class TranscoderCrc32Impl implements Transcoder<Integer> {
    // Calls a method
    static final TranscoderCrc32Impl INSTANCE = new TranscoderCrc32Impl();

    // Calls a method
    private static final Comparator<Map.Entry<Integer, Integer>> KEY_COMPARATOR = Map.Entry.comparingByKey(Comparator.comparingLong(Integer::toUnsignedLong));
    // Calls a method
    private static final Comparator<Map.Entry<Integer, Integer>> VALUE_COMPARATOR = Map.Entry.comparingByValue(Comparator.comparingLong(Integer::toUnsignedLong));
    // Calls a method
    private static final Comparator<Map.Entry<Integer, Integer>> MAP_COMPARATOR = KEY_COMPARATOR.thenComparing(VALUE_COMPARATOR);

    // Assigns a value
    private static final byte TAG_EMPTY = 1;
    // Assigns a value
    private static final byte TAG_MAP_START = 2;
    // Assigns a value
    private static final byte TAG_MAP_END = 3;
    // Assigns a value
    private static final byte TAG_LIST_START = 4;
    // Assigns a value
    private static final byte TAG_LIST_END = 5;
    // Assigns a value
    private static final byte TAG_BYTE = 6;
    // Assigns a value
    private static final byte TAG_SHORT = 7;
    // Assigns a value
    private static final byte TAG_INT = 8;
    // Assigns a value
    private static final byte TAG_LONG = 9;
    // Assigns a value
    private static final byte TAG_FLOAT = 10;
    // Assigns a value
    private static final byte TAG_DOUBLE = 11;
    // Assigns a value
    private static final byte TAG_STRING = 12;
    // Assigns a value
    private static final byte TAG_BOOLEAN = 13;
    // Assigns a value
    private static final byte TAG_BYTE_ARRAY_START = 14;
    // Assigns a value
    private static final byte TAG_BYTE_ARRAY_END = 15;
    // Assigns a value
    private static final byte TAG_INT_ARRAY_START = 16;
    // Assigns a value
    private static final byte TAG_INT_ARRAY_END = 17;
    // Assigns a value
    private static final byte TAG_LONG_ARRAY_START = 18;
    // Assigns a value
    private static final byte TAG_LONG_ARRAY_END = 19;

    // Calls a method
    private static final int EMPTY = new Hasher().putByte(TAG_EMPTY).hash();
    // Calls a method
    private static final int EMPTY_MAP = new Hasher().putByte(TAG_MAP_START).putByte(TAG_MAP_END).hash();
    // Calls a method
    private static final int EMPTY_LIST = new Hasher().putByte(TAG_LIST_START).putByte(TAG_LIST_END).hash();
    // Calls a method
    private static final int FALSE = new Hasher().putByte(TAG_BOOLEAN).putByte((byte) 0).hash();
    // Calls a method
    private static final int TRUE = new Hasher().putByte(TAG_BOOLEAN).putByte((byte) 1).hash();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createNull() {
        // Returns a value to the caller
        return EMPTY;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createBoolean(boolean value) {
        // Returns a value to the caller
        return value ? TRUE : FALSE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createByte(byte value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_BYTE).putByte(value).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createShort(short value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_SHORT).putShort(value).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createInt(int value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_INT).putInt(value).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createLong(long value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_LONG).putLong(value).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createFloat(float value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_FLOAT).putFloat(value).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createDouble(double value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_DOUBLE).putDouble(value).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createString(String value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_STRING)
                // Code statement
                .putInt(value.length())
                // Code statement
                .putChars(value)
                // Calls a method
                .hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer emptyList() {
        // Returns a value to the caller
        return EMPTY_LIST;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ListBuilder<Integer> createList(int expectedSize) {
        // Calls a method
        final Hasher hasher = new Hasher().putByte(TAG_LIST_START);
        // Returns a value to the caller
        return new ListBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public ListBuilder<Integer> add(Integer value) {
                // Calls a method
                hasher.putIntBytes(value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Integer build() {
                // Returns a value to the caller
                return hasher.putByte(TAG_LIST_END).hash();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer emptyMap() {
        // Returns a value to the caller
        return EMPTY_MAP;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public MapBuilder<Integer> createMap() {
        // Calls a method
        final HashMap<Integer, Integer> map = new HashMap<>();
        // Returns a value to the caller
        return new MapBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<Integer> put(Integer key, Integer value) {
                // Branch: checks a condition
                if (value != EMPTY)
                    // Calls a method
                    map.put(key, value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<Integer> put(String key, Integer value) {
                // Returns a value to the caller
                return put(createString(key), value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Integer build() {
                // Branch: checks a condition
                if (map.isEmpty()) return EMPTY_MAP;
                // Calls a method
                final Hasher hasher = new Hasher().putByte(TAG_MAP_START);
                // Start of a method/block
                map.entrySet().stream().sorted(MAP_COMPARATOR).forEach(entry -> {
                    // Calls a method
                    hasher.putIntBytes(entry.getKey());
                    // Calls a method
                    hasher.putIntBytes(entry.getValue());
                // End of a block/expression
                });
                // Returns a value to the caller
                return hasher.putByte(TAG_MAP_END).hash();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createByteArray(byte[] value) {
        // Returns a value to the caller
        return new Hasher().putByte(TAG_BYTE_ARRAY_START)
                // Calls a method
                .putBytes(value).putByte(TAG_BYTE_ARRAY_END).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createIntArray(int[] value) {
        // Calls a method
        final Hasher hasher = new Hasher().putByte(TAG_INT_ARRAY_START);
        // Loop: repeats a block
        for (final int item : value) hasher.putInt(item);
        // Returns a value to the caller
        return hasher.putByte(TAG_INT_ARRAY_END).hash();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Integer createLongArray(long[] value) {
        // Calls a method
        final Hasher hasher = new Hasher().putByte(TAG_LONG_ARRAY_START);
        // Loop: repeats a block
        for (final long item : value) hasher.putLong(item);
        // Returns a value to the caller
        return hasher.putByte(TAG_LONG_ARRAY_END).hash();
    // End of a block/expression
    }

    // Noop read implementation below

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Boolean> getBoolean(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Byte> getByte(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Short> getShort(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Integer> getInt(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Long> getLong(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Float> getFloat(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Double> getDouble(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<String> getString(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<byte[]> getByteArray(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<int[]> getIntArray(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<long[]> getLongArray(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<List<Integer>> getList(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<MapLike<Integer>> getMap(Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <O> Result<O> convertTo(Transcoder<O> coder, Integer value) {
        // Returns a value to the caller
        return writeOnly();
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> Result<T> writeOnly() {
        // Returns a value to the caller
        return new Result.Error<>("CRC32 transcoder only supports encoding");
    // End of a block/expression
    }


    // Loosely based on the Hasher implementation from Guava, licensed under the Apache 2.0 license.
    // Type declaration (class/interface/enum/record)
    private record Hasher(CRC32C crc32, ByteBuffer buffer) {
        // Start of a method/block
        public Hasher() {
            // Calls a method
            this(new CRC32C(), ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN));
        // End of a block/expression
        }

        // Start of a method/block
        private Hasher update(int bytes) {
            // Calls a method
            crc32.update(buffer.array(), 0, bytes);
            // Calls a method
            buffer.position(0);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putByte(byte b) {
            // Calls a method
            crc32.update(b);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putShort(short s) {
            // Calls a method
            buffer.putShort(s);
            // Returns a value to the caller
            return update(Short.BYTES);
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putInt(int i) {
            // Calls a method
            buffer.putInt(i);
            // Returns a value to the caller
            return update(Integer.BYTES);
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putIntBytes(int i) {
            // Calls a method
            putByte((byte) i);
            // Calls a method
            putByte((byte) (i >> 8));
            // Calls a method
            putByte((byte) (i >> 16));
            // Calls a method
            putByte((byte) (i >> 24));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putLong(long l) {
            // Calls a method
            buffer.putLong(l);
            // Returns a value to the caller
            return update(Long.BYTES);
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putFloat(float f) {
            // Returns a value to the caller
            return putInt(Float.floatToRawIntBits(f));
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putDouble(double d) {
            // Returns a value to the caller
            return putLong(Double.doubleToRawLongBits(d));
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putChar(char c) {
            // Access to the current/parent object
            this.putByte((byte) c);
            // Access to the current/parent object
            this.putByte((byte) (c >>> 8));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putChars(String string) {
            // Loop: repeats a block
            for (int i = 0; i < string.length(); ++i)
                // Access to the current/parent object
                this.putChar(string.charAt(i));
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public Hasher putBytes(byte[] bytes) {
            // Calls a method
            crc32.update(bytes);
            // Returns a value to the caller
            return this;
        // End of a block/expression
        }

        // Start of a method/block
        public int hash() {
            // Returns a value to the caller
            return (int) crc32.getValue();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
