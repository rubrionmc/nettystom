// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import com.google.gson.*;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.AbstractList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class TranscoderJsonImpl implements Transcoder<JsonElement> {
    // Appelle une méthode
    public static final TranscoderJsonImpl INSTANCE = new TranscoderJsonImpl();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createNull() {
        // Renvoie une valeur à l'appelant
        return JsonNull.INSTANCE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Boolean> getBoolean(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a boolean: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsBoolean());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createBoolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Byte> getByte(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a byte: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsByte());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createByte(byte value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Short> getShort(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a short: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsShort());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createShort(short value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Integer> getInt(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not an int: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsInt());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createInt(int value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Long> getLong(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a long: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsLong());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createLong(long value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Float> getFloat(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a float: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsFloat());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createFloat(float value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Double> getDouble(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive) || !primitive.isNumber())
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a double: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsDouble());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createDouble(double value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<String> getString(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonPrimitive primitive))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a string: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(primitive.getAsString());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement createString(String value) {
        // Renvoie une valeur à l'appelant
        return new JsonPrimitive(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<List<JsonElement>> getList(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonArray array)) return new Result.Error<>("Not a list: " + value);
        // Embranchement : vérifie une condition
        if (array.isEmpty()) return new Result.Ok<>(List.of());
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(new AbstractList<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public JsonElement get(int index) {
                // Renvoie une valeur à l'appelant
                return array.get(index);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int size() {
                // Renvoie une valeur à l'appelant
                return array.size();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement emptyList() {
        // Renvoie une valeur à l'appelant
        return new JsonArray();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ListBuilder<JsonElement> createList(int expectedSize) {
        // Appelle une méthode
        final JsonArray list = new JsonArray(expectedSize);
        // Renvoie une valeur à l'appelant
        return new ListBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public ListBuilder<JsonElement> add(JsonElement value) {
                // Appelle une méthode
                list.add(value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public JsonElement build() {
                // Renvoie une valeur à l'appelant
                return list;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<MapLike<JsonElement>> getMap(JsonElement value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof JsonObject object))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not an object: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(new MapLike<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Collection<String> keys() {
                // Renvoie une valeur à l'appelant
                return object.keySet();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean hasValue(String key) {
                // Renvoie une valeur à l'appelant
                return object.has(key);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Result<JsonElement> getValue(String key) {
                // Appelle une méthode
                final JsonElement element = object.get(key);
                // Embranchement : vérifie une condition
                if (element == null) return new Result.Error<>("No such key: " + key);
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(element);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int size() {
                // Renvoie une valeur à l'appelant
                return object.size();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public JsonElement emptyMap() {
        // Renvoie une valeur à l'appelant
        return new JsonObject();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public MapBuilder<JsonElement> createMap() {
        // Appelle une méthode
        final JsonObject object = new JsonObject();
        // Renvoie une valeur à l'appelant
        return new MapBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<JsonElement> put(JsonElement key, JsonElement value) {
                // Renvoie une valeur à l'appelant
                return put(key.getAsString(), value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<JsonElement> put(String key, JsonElement value) {
                // Embranchement : vérifie une condition
                if (value != JsonNull.INSTANCE)
                    // Appelle une méthode
                    object.add(key, value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public JsonElement build() {
                // Renvoie une valeur à l'appelant
                return object;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <O> Result<O> convertTo(Transcoder<O> coder, JsonElement value) {
        // Renvoie une valeur à l'appelant
        return switch (value) {
            // Embranchement multiple (switch/case)
            case JsonObject object -> {
                // Appelle une méthode
                final MapBuilder<O> mapBuilder = coder.createMap();
                // Boucle : répète un bloc
                for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    // Appelle une méthode
                    final String key = entry.getKey();
                    // Embranchement multiple (switch/case)
                    switch (convertTo(coder, entry.getValue())) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok(O data) -> mapBuilder.put(coder.createString(key), data);
                        // Embranchement multiple (switch/case)
                        case Result.Error(String message) -> {
                            // Instruction de code
                            yield new Result.Error<>(key + ": " + message);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                yield new Result.Ok<>(mapBuilder.build());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case JsonArray array -> {
                // Embranchement : vérifie une condition
                if (array.isEmpty()) yield new Result.Ok<>(coder.emptyList());
                // Appelle une méthode
                final ListBuilder<O> listBuilder = coder.createList(array.size());
                // Boucle : répète un bloc
                for (int i = 0; i < array.size(); i++) {
                    // Embranchement multiple (switch/case)
                    switch (convertTo(coder, array.get(i))) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok(O data) -> listBuilder.add(data);
                        // Embranchement multiple (switch/case)
                        case Result.Error(String message) -> {
                            // Instruction de code
                            yield new Result.Error<>(i + ": " + message);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                yield new Result.Ok<>(listBuilder.build());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case JsonPrimitive primitive when primitive.isBoolean() ->
                    // Crée un nouvel objet
                    new Result.Ok<>(coder.createBoolean(primitive.getAsBoolean()));
            // Embranchement multiple (switch/case)
            case JsonPrimitive primitive when primitive.isNumber() ->
                    // Crée un nouvel objet
                    new Result.Ok<>(coder.createDouble(primitive.getAsDouble()));
            // Embranchement multiple (switch/case)
            case JsonPrimitive primitive when primitive.isString() ->
                    // Crée un nouvel objet
                    new Result.Ok<>(coder.createString(primitive.getAsString()));
            // Embranchement multiple (switch/case)
            case JsonNull jsonNull -> new Result.Ok<>(coder.createNull());
            // Instruction de code
            default -> new Result.Error<>("Unknown JSON type: " + value);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
