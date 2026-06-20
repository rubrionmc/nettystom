// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import com.google.gson.*;
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
final class TranscoderJsonImpl implements Transcoder<JsonElement> {
    // Calls a method
    public static final TranscoderJsonImpl INSTANCE = new TranscoderJsonImpl();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createNull() {
        // Returns a value to the caller
        return JsonNull.INSTANCE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Boolean> getBoolean(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive))
            // Returns a value to the caller
            return new Result.Error<>("Not a boolean: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsBoolean());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createBoolean(boolean value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Byte> getByte(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Returns a value to the caller
            return new Result.Error<>("Not a byte: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsByte());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createByte(byte value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Short> getShort(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Returns a value to the caller
            return new Result.Error<>("Not a short: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsShort());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createShort(short value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Integer> getInt(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Returns a value to the caller
            return new Result.Error<>("Not an int: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsInt());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createInt(int value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Long> getLong(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Returns a value to the caller
            return new Result.Error<>("Not a long: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsLong());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createLong(long value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Float> getFloat(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Returns a value to the caller
            return new Result.Error<>("Not a float: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsFloat());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createFloat(float value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<Double> getDouble(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Returns a value to the caller
            return new Result.Error<>("Not a double: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsDouble());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createDouble(double value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<String> getString(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonPrimitive primitive))
            // Returns a value to the caller
            return new Result.Error<>("Not a string: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(primitive.getAsString());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement createString(String value) {
        // Returns a value to the caller
        return new JsonPrimitive(value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<List<JsonElement>> getList(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonArray array)) return new Result.Error<>("Not a list: " + value);
        // Branch: checks a condition
        if (array.isEmpty()) return new Result.Ok<>(List.of());
        // Returns a value to the caller
        return new Result.Ok<>(new AbstractList<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public JsonElement get(int index) {
                // Returns a value to the caller
                return array.get(index);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public int size() {
                // Returns a value to the caller
                return array.size();
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement emptyList() {
        // Returns a value to the caller
        return new JsonArray();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ListBuilder<JsonElement> createList(int expectedSize) {
        // Calls a method
        final JsonArray list = new JsonArray(expectedSize);
        // Returns a value to the caller
        return new ListBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public ListBuilder<JsonElement> add(JsonElement value) {
                // Calls a method
                list.add(value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public JsonElement build() {
                // Returns a value to the caller
                return list;
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Result<MapLike<JsonElement>> getMap(JsonElement value) {
        // Branch: checks a condition
        if (!(value instanceof JsonObject object))
            // Returns a value to the caller
            return new Result.Error<>("Not an object: " + value);
        // Returns a value to the caller
        return new Result.Ok<>(new MapLike<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public Collection<String> keys() {
                // Returns a value to the caller
                return object.keySet();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean hasValue(String key) {
                // Returns a value to the caller
                return object.has(key);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Result<JsonElement> getValue(String key) {
                // Calls a method
                final JsonElement element = object.get(key);
                // Branch: checks a condition
                if (element == null) return new Result.Error<>("No such key: " + key);
                // Returns a value to the caller
                return new Result.Ok<>(element);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public int size() {
                // Returns a value to the caller
                return object.size();
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public JsonElement emptyMap() {
        // Returns a value to the caller
        return new JsonObject();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public MapBuilder<JsonElement> createMap() {
        // Calls a method
        final JsonObject object = new JsonObject();
        // Returns a value to the caller
        return new MapBuilder<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<JsonElement> put(JsonElement key, JsonElement value) {
                // Returns a value to the caller
                return put(key.getAsString(), value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public MapBuilder<JsonElement> put(String key, JsonElement value) {
                // Branch: checks a condition
                if (value != JsonNull.INSTANCE)
                    // Calls a method
                    object.add(key, value);
                // Returns a value to the caller
                return this;
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public JsonElement build() {
                // Returns a value to the caller
                return object;
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <O> Result<O> convertTo(Transcoder<O> coder, JsonElement value) {
        // Returns a value to the caller
        return switch (value) {
            // Multiple branching (switch/case)
            case JsonObject object -> {
                // Calls a method
                final MapBuilder<O> mapBuilder = coder.createMap();
                // Loop: repeats a block
                for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    // Calls a method
                    final String key = entry.getKey();
                    // Multiple branching (switch/case)
                    switch (convertTo(coder, entry.getValue())) {
                        // Multiple branching (switch/case)
                        case Result.Ok(O data) -> mapBuilder.put(coder.createString(key), data);
                        // Multiple branching (switch/case)
                        case Result.Error(String message) -> {
                            // Calls a method
                            yield new Result.Error<>(key + ": " + message);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                yield new Result.Ok<>(mapBuilder.build());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case JsonArray array -> {
                // Branch: checks a condition
                if (array.isEmpty()) yield new Result.Ok<>(coder.emptyList());
                // Calls a method
                final ListBuilder<O> listBuilder = coder.createList(array.size());
                // Loop: repeats a block
                for (int i = 0; i < array.size(); i++) {
                    // Multiple branching (switch/case)
                    switch (convertTo(coder, array.get(i))) {
                        // Multiple branching (switch/case)
                        case Result.Ok(O data) -> listBuilder.add(data);
                        // Multiple branching (switch/case)
                        case Result.Error(String message) -> {
                            // Calls a method
                            yield new Result.Error<>(i + ": " + message);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                yield new Result.Ok<>(listBuilder.build());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case JsonPrimitive primitive when primitive.isBoolean() ->
                    // Creates a new object
                    new Result.Ok<>(coder.createBoolean(primitive.getAsBoolean()));
            // Multiple branching (switch/case)
            case JsonPrimitive primitive when primitive.isNumber() ->
                    // Creates a new object
                    new Result.Ok<>(coder.createDouble(primitive.getAsDouble()));
            // Multiple branching (switch/case)
            case JsonPrimitive primitive when primitive.isString() ->
                    // Creates a new object
                    new Result.Ok<>(coder.createString(primitive.getAsString()));
            // Multiple branching (switch/case)
            case JsonNull jsonNull -> new Result.Ok<>(coder.createNull());
            // Multiple branching (switch/case)
            default -> new Result.Error<>("Unknown JSON type: " + value);
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
