// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.*;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class TranscoderJavaImpl implements Transcoder<Object> {
    // Calls a method
    public static final Transcoder<Object> INSTANCE = new TranscoderJavaImpl();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createNull() {
        // Returns a value to the caller
        return Optional.empty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Boolean> getBoolean(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Boolean b))
            // Returns a value to the caller
            return new Result.Error<>("Not a boolean: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(b);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createBoolean(boolean value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Byte> getByte(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Number n))
            // Returns a value to the caller
            return new Result.Error<>("Not a byte: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(n.byteValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createByte(byte value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Short> getShort(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Number n))
            // Returns a value to the caller
            return new Result.Error<>("Not a short: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(n.shortValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createShort(short value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Integer> getInt(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Number n))
            // Returns a value to the caller
            return new Result.Error<>("Not an int: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(n.intValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createInt(int value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Long> getLong(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Number n))
            // Returns a value to the caller
            return new Result.Error<>("Not a long: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(n.longValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createLong(long value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Float> getFloat(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Number n))
            // Returns a value to the caller
            return new Result.Error<>("Not a float: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(n.floatValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createFloat(float value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Double> getDouble(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Number n))
            // Returns a value to the caller
            return new Result.Error<>("Not a double: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(n.doubleValue());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createDouble(double value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<String> getString(Object value) {
        // Branch: checks a condition
        if (!(value instanceof String s))
            // Returns a value to the caller
            return new Result.Error<>("Not a string: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(s);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object createString(String value) {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<List<Object>> getList(Object value) {
        // Branch: checks a condition
        if (!(value instanceof List<?> list))
            // Returns a value to the caller
            return new Result.Error<>("Not a list: " + value);
        //noinspection unchecked
        // Returns a value to the caller
        return new Result.Ok<>((List<Object>) list);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ListBuilder<Object> createList(int expectedSize) {
        // Calls a method
        final List<Object> list = new java.util.ArrayList<>(expectedSize);
        // Returns a value to the caller
        return new ListBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public ListBuilder<Object> add(Object value) {
                // Calls a method
                list.add(value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Object build() {
                // Returns a value to the caller
                return List.copyOf(list);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object emptyList() {
        // Returns a value to the caller
        return List.of();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<MapLike<Object>> getMap(Object value) {
        // Branch: checks a condition
        if (!(value instanceof Map<?, ?> map))
            // Returns a value to the caller
            return new Result.Error<>("Not a map: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(new MapLike<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Collection<String> keys() {
                // Branch: checks a condition
                if (map.isEmpty()) return List.of();
                // Calls a method
                var keys = List.copyOf(map.keySet());
                // Branch: checks a condition
                if (keys.getFirst() instanceof String)
                    //noinspection unchecked
                    // Returns a value to the caller
                    return (List<String>) keys;
                // Returns a value to the caller
                return List.of(); // No string keys
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean hasValue(String key) {
                // Returns a value to the caller
                return map.containsKey(key);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Result<Object> getValue(String key) {
                // Branch: checks a condition
                if (!hasValue(key)) return new Result.Error<>("No such key: " + key);
                // Returns a value to the caller
                return new Result.Ok<>(map.get(key));
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public MapBuilder<Object> createMap() {
        // Calls a method
        final Map<String, Object> map = new HashMap<>();
        // Returns a value to the caller
        return new MapBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<Object> put(Object key, Object value) {
                // Branch: checks a condition
                if (!(key instanceof String s)) return this;
                // Calls a method
                map.put(s, value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<Object> put(String key, Object value) {
                // Calls a method
                map.put(key, value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Object build() {
                // Returns a value to the caller
                return Map.copyOf(map);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object emptyMap() {
        // Returns a value to the caller
        return Map.of();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <O> Result<O> convertTo(Transcoder<O> coder, Object value) {
        // Returns a value to the caller
        return switch (value) {
            // Multiple branching (switch/case)
            case Optional<?> o when o.isEmpty() -> new Result.Ok<>(coder.createNull());
            // Multiple branching (switch/case)
            case Boolean b -> new Result.Ok<>(coder.createBoolean(b));
            // Multiple branching (switch/case)
            case Byte n -> new Result.Ok<>(coder.createByte(n));
            // Multiple branching (switch/case)
            case Short n -> new Result.Ok<>(coder.createShort(n));
            // Multiple branching (switch/case)
            case Integer n -> new Result.Ok<>(coder.createInt(n));
            // Multiple branching (switch/case)
            case Long n -> new Result.Ok<>(coder.createLong(n));
            // Multiple branching (switch/case)
            case Float n -> new Result.Ok<>(coder.createFloat(n));
            // Multiple branching (switch/case)
            case Double n -> new Result.Ok<>(coder.createDouble(n));
            // Multiple branching (switch/case)
            case Number n -> new Result.Ok<>(coder.createDouble(n.doubleValue()));
            // Multiple branching (switch/case)
            case String s -> new Result.Ok<>(coder.createString(s));
            // Multiple branching (switch/case)
            case List<?> l -> {
                // Calls a method
                var builder = coder.createList(l.size());
                // Loop: repeats a block
                for (var o : l) {
                    // Calls a method
                    var result = convertTo(coder, o);
                    // Branch: checks a condition
                    if (!(result instanceof Result.Ok(O inner)))
                        // Calls a method
                        yield result.cast();
                    // Calls a method
                    builder.add(inner);
                // End of a block/expression
                }
                // Calls a method
                yield new Result.Ok<>(builder.build());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case Map<?, ?> m -> {
                // Calls a method
                var builder = coder.createMap();
                // Loop: repeats a block
                for (var entry : m.entrySet()) {
                    // Calls a method
                    var key = entry.getKey();
                    // Branch: checks a condition
                    if (!(key instanceof String s))
                        // Calls a method
                        yield new Result.Error<>("Map key is not a string: " + key);
                    // Calls a method
                    var result = convertTo(coder, entry.getValue());
                    // Branch: checks a condition
                    if (!(result instanceof Result.Ok(O inner)))
                        // Calls a method
                        yield result.cast();
                    // Calls a method
                    builder.put(s, inner);
                // End of a block/expression
                }
                // Calls a method
                yield new Result.Ok<>(builder.build());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> new Result.Error<>("Unsupported type: " + value);
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
