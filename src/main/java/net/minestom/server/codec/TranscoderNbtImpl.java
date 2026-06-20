// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.AbstractList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class TranscoderNbtImpl implements Transcoder<BinaryTag> {
    // Calls a method
    static final TranscoderNbtImpl INSTANCE = new TranscoderNbtImpl();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createNull() {
        // Returns a value to the caller
        return EndBinaryTag.endBinaryTag();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Boolean> getBoolean(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.byteValue() != 0)
                // Calls a method
                : new Result.Error<>("Not a boolean: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createBoolean(boolean value) {
        // Returns a value to the caller
        return value ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Byte> getByte(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.byteValue())
                // Calls a method
                : new Result.Error<>("Not a byte: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createByte(byte value) {
        // Branch: checks a condition
        if (value == 0) return ByteBinaryTag.ZERO;
        // Branch: checks a condition
        if (value == 1) return ByteBinaryTag.ONE;
        // Returns a value to the caller
        return ByteBinaryTag.byteBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Short> getShort(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.shortValue())
                // Calls a method
                : new Result.Error<>("Not a short: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createShort(short value) {
        // Returns a value to the caller
        return ShortBinaryTag.shortBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Integer> getInt(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.intValue())
                // Calls a method
                : new Result.Error<>("Not an int: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createInt(int value) {
        // Returns a value to the caller
        return IntBinaryTag.intBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Long> getLong(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.longValue())
                // Calls a method
                : new Result.Error<>("Not a long: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createLong(long value) {
        // Returns a value to the caller
        return LongBinaryTag.longBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Float> getFloat(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.floatValue())
                // Calls a method
                : new Result.Error<>("Not a float: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createFloat(float value) {
        // Returns a value to the caller
        return FloatBinaryTag.floatBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Double> getDouble(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof NumberBinaryTag number
                // Code statement
                ? new Result.Ok<>(number.doubleValue())
                // Calls a method
                : new Result.Error<>("Not a double: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createDouble(double value) {
        // Returns a value to the caller
        return DoubleBinaryTag.doubleBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<String> getString(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof StringBinaryTag string
                // Code statement
                ? new Result.Ok<>(string.value())
                // Calls a method
                : new Result.Error<>("Not a string: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createString(String value) {
        // Returns a value to the caller
        return StringBinaryTag.stringBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<List<BinaryTag>> getList(BinaryTag value) {
        // Branch: checks a condition
        if (!(value instanceof ListBinaryTag listTagWrapped))
            // Returns a value to the caller
            return new Result.Error<>("Not a list: " + value);
        // Calls a method
        final ListBinaryTag listTag = listTagWrapped.unwrapHeterogeneity();
        // Returns a value to the caller
        return new Result.Ok<>(new AbstractList<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public BinaryTag get(int index) {
                // Returns a value to the caller
                return listTag.get(index);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public int size() {
                // Returns a value to the caller
                return listTag.size();
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag emptyList() {
        // Returns a value to the caller
        return ListBinaryTag.empty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ListBuilder<BinaryTag> createList(int expectedSize) {
        // Calls a method
        final ListBinaryTag.Builder<BinaryTag> elements = ListBinaryTag.heterogeneousListBinaryTag();
        // Returns a value to the caller
        return new ListBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public ListBuilder<BinaryTag> add(BinaryTag value) {
                // Calls a method
                elements.add(value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public BinaryTag build() {
                // Returns a value to the caller
                return elements.build();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<MapLike<BinaryTag>> getMap(BinaryTag value) {
        // Branch: checks a condition
        if (!(value instanceof CompoundBinaryTag compoundTag))
            // Returns a value to the caller
            return new Result.Error<>("Not a compound: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(new MapLike<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Collection<String> keys() {
                // Returns a value to the caller
                return compoundTag.keySet();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean hasValue(String key) {
                // Returns a value to the caller
                return compoundTag.get(key) != null;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Result<BinaryTag> getValue(String key) {
                // Calls a method
                final BinaryTag tag = compoundTag.get(key);
                // Branch: checks a condition
                if (tag == null) return new Result.Error<>("No such key: " + key);
                // Returns a value to the caller
                return new Result.Ok<>(tag);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public int size() {
                // Returns a value to the caller
                return compoundTag.size();
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag emptyMap() {
        // Returns a value to the caller
        return CompoundBinaryTag.empty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public MapBuilder<BinaryTag> createMap() {
        // Calls a method
        final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        // Returns a value to the caller
        return new MapBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<BinaryTag> put(BinaryTag key, BinaryTag value) {
                // Branch: checks a condition
                if (!(value instanceof EndBinaryTag) && key instanceof StringBinaryTag string)
                    // Calls a method
                    builder.put(string.value(), value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<BinaryTag> put(String key, BinaryTag value) {
                // Branch: checks a condition
                if (!(value instanceof EndBinaryTag))
                    // Calls a method
                    builder.put(key, value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public BinaryTag build() {
                // Returns a value to the caller
                return builder.build();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<byte[]> getByteArray(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof ByteArrayBinaryTag byteArray
                // Code statement
                ? new Result.Ok<>(byteArray.value())
                // Calls a method
                : new Result.Error<>("Not a byte array: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createByteArray(byte[] value) {
        // Returns a value to the caller
        return ByteArrayBinaryTag.byteArrayBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<int[]> getIntArray(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof IntArrayBinaryTag intArray
                // Code statement
                ? new Result.Ok<>(intArray.value())
                // Calls a method
                : new Result.Error<>("Not an int array: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createIntArray(int[] value) {
        // Returns a value to the caller
        return IntArrayBinaryTag.intArrayBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<long[]> getLongArray(BinaryTag value) {
        // Returns a value to the caller
        return value instanceof LongArrayBinaryTag longArray
                // Code statement
                ? new Result.Ok<>(longArray.value())
                // Calls a method
                : new Result.Error<>("Not a long array: " + value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag createLongArray(long[] value) {
        // Returns a value to the caller
        return LongArrayBinaryTag.longArrayBinaryTag(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <O> Result<O> convertTo(Transcoder<O> coder, BinaryTag value) {
        // Returns a value to the caller
        return switch (value) {
            // Multiple branching (switch/case)
            case EndBinaryTag ignored -> new Result.Ok<>(coder.createNull());
            // Multiple branching (switch/case)
            case ByteBinaryTag byteTag -> new Result.Ok<>(coder.createByte(byteTag.byteValue()));
            // Multiple branching (switch/case)
            case ShortBinaryTag shortTag -> new Result.Ok<>(coder.createShort(shortTag.shortValue()));
            // Multiple branching (switch/case)
            case IntBinaryTag intTag -> new Result.Ok<>(coder.createInt(intTag.intValue()));
            // Multiple branching (switch/case)
            case LongBinaryTag longTag -> new Result.Ok<>(coder.createLong(longTag.longValue()));
            // Multiple branching (switch/case)
            case FloatBinaryTag floatTag -> new Result.Ok<>(coder.createFloat(floatTag.floatValue()));
            // Multiple branching (switch/case)
            case DoubleBinaryTag doubleTag -> new Result.Ok<>(coder.createDouble(doubleTag.doubleValue()));
            // Multiple branching (switch/case)
            case ByteArrayBinaryTag byteArrayTag -> new Result.Ok<>(coder.createByteArray(byteArrayTag.value()));
            // Multiple branching (switch/case)
            case StringBinaryTag stringTag -> new Result.Ok<>(coder.createString(stringTag.value()));
            // Multiple branching (switch/case)
            case ListBinaryTag listTag -> {
                // Calls a method
                listTag = listTag.unwrapHeterogeneity();
                // Calls a method
                final ListBuilder<O> list = coder.createList(listTag.size());
                // Loop: repeats a block
                for (int i = 0; i < listTag.size(); i++) {
                    // Multiple branching (switch/case)
                    switch (convertTo(coder, listTag.get(i))) {
                        // Multiple branching (switch/case)
                        case Result.Ok<O> ok -> list.add(ok.value());
                        // Multiple branching (switch/case)
                        case Result.Error<O> error -> {
                            // Calls a method
                            yield new Result.Error<>(i + ": " + error);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                yield new Result.Ok<>(list.build());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case CompoundBinaryTag compoundTag -> {
                // Calls a method
                final MapBuilder<O> map = coder.createMap();
                // Loop: repeats a block
                for (Map.Entry<String, ? extends BinaryTag> entry : compoundTag) {
                    // Multiple branching (switch/case)
                    switch (convertTo(coder, entry.getValue())) {
                        // Multiple branching (switch/case)
                        case Result.Ok<O> ok -> map.put(coder.createString(entry.getKey()), ok.value());
                        // Multiple branching (switch/case)
                        case Result.Error<O> error -> {
                            // Calls a method
                            yield new Result.Error<>(entry.getKey() + ": " + error);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                yield new Result.Ok<>(map.build());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case IntArrayBinaryTag intArrayTag -> new Result.Ok<>(coder.createIntArray(intArrayTag.value()));
            // Multiple branching (switch/case)
            case LongArrayBinaryTag longArrayTag -> new Result.Ok<>(coder.createLongArray(longArrayTag.value()));
            // Multiple branching (switch/case)
            default -> new Result.Error<>("Unsupported type: " + value);
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
