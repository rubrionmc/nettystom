// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.*;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class TranscoderJavaImpl implements Transcoder<Object> {
    // Appelle une méthode
    public static final Transcoder<Object> INSTANCE = new TranscoderJavaImpl();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createNull() {
        // Renvoie une valeur à l'appelant
        return Optional.empty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Boolean> getBoolean(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Boolean b))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a boolean: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(b);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createBoolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Byte> getByte(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Number n))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a byte: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(n.byteValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createByte(byte value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Short> getShort(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Number n))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a short: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(n.shortValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createShort(short value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Integer> getInt(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Number n))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not an int: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(n.intValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createInt(int value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Long> getLong(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Number n))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a long: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(n.longValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createLong(long value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Float> getFloat(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Number n))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a float: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(n.floatValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createFloat(float value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Double> getDouble(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Number n))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a double: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(n.doubleValue());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createDouble(double value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<String> getString(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof String s))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a string: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(s);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object createString(String value) {
        // Renvoie une valeur à l'appelant
        return value;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<List<Object>> getList(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof List<?> list))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a list: " + value);
        //noinspection unchecked
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>((List<Object>) list);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ListBuilder<Object> createList(int expectedSize) {
        // Affecte une valeur
        final List<Object> list = new java.util.ArrayList<>(expectedSize);
        // Renvoie une valeur à l'appelant
        return new ListBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public ListBuilder<Object> add(Object value) {
                // Appelle une méthode
                list.add(value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Object build() {
                // Renvoie une valeur à l'appelant
                return List.copyOf(list);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object emptyList() {
        // Renvoie une valeur à l'appelant
        return List.of();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<MapLike<Object>> getMap(Object value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof Map<?, ?> map))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a map: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(new MapLike<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Collection<String> keys() {
                // Embranchement : vérifie une condition
                if (map.isEmpty()) return List.of();
                // Appelle une méthode
                var keys = List.copyOf(map.keySet());
                // Embranchement : vérifie une condition
                if (keys.getFirst() instanceof String)
                    //noinspection unchecked
                    // Renvoie une valeur à l'appelant
                    return (List<String>) keys;
                // Renvoie une valeur à l'appelant
                return List.of(); // No string keys
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean hasValue(String key) {
                // Renvoie une valeur à l'appelant
                return map.containsKey(key);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Result<Object> getValue(String key) {
                // Embranchement : vérifie une condition
                if (!hasValue(key)) return new Result.Error<>("No such key: " + key);
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(map.get(key));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public MapBuilder<Object> createMap() {
        // Affecte une valeur
        final Map<String, Object> map = new HashMap<>();
        // Renvoie une valeur à l'appelant
        return new MapBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<Object> put(Object key, Object value) {
                // Embranchement : vérifie une condition
                if (!(key instanceof String s)) return this;
                // Appelle une méthode
                map.put(s, value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<Object> put(String key, Object value) {
                // Appelle une méthode
                map.put(key, value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Object build() {
                // Renvoie une valeur à l'appelant
                return Map.copyOf(map);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Object emptyMap() {
        // Renvoie une valeur à l'appelant
        return Map.of();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <O> Result<O> convertTo(Transcoder<O> coder, Object value) {
        // Renvoie une valeur à l'appelant
        return switch (value) {
            // Embranchement multiple (switch/case)
            case Optional<?> o when o.isEmpty() -> new Result.Ok<>(coder.createNull());
            // Embranchement multiple (switch/case)
            case Boolean b -> new Result.Ok<>(coder.createBoolean(b));
            // Embranchement multiple (switch/case)
            case Byte n -> new Result.Ok<>(coder.createByte(n));
            // Embranchement multiple (switch/case)
            case Short n -> new Result.Ok<>(coder.createShort(n));
            // Embranchement multiple (switch/case)
            case Integer n -> new Result.Ok<>(coder.createInt(n));
            // Embranchement multiple (switch/case)
            case Long n -> new Result.Ok<>(coder.createLong(n));
            // Embranchement multiple (switch/case)
            case Float n -> new Result.Ok<>(coder.createFloat(n));
            // Embranchement multiple (switch/case)
            case Double n -> new Result.Ok<>(coder.createDouble(n));
            // Embranchement multiple (switch/case)
            case Number n -> new Result.Ok<>(coder.createDouble(n.doubleValue()));
            // Embranchement multiple (switch/case)
            case String s -> new Result.Ok<>(coder.createString(s));
            // Embranchement multiple (switch/case)
            case List<?> l -> {
                // Appelle une méthode
                var builder = coder.createList(l.size());
                // Boucle : répète un bloc
                for (var o : l) {
                    // Appelle une méthode
                    var result = convertTo(coder, o);
                    // Embranchement : vérifie une condition
                    if (!(result instanceof Result.Ok(O inner)))
                        // Appelle une méthode
                        yield result.cast();
                    // Appelle une méthode
                    builder.add(inner);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                yield new Result.Ok<>(builder.build());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case Map<?, ?> m -> {
                // Appelle une méthode
                var builder = coder.createMap();
                // Boucle : répète un bloc
                for (var entry : m.entrySet()) {
                    // Appelle une méthode
                    var key = entry.getKey();
                    // Embranchement : vérifie une condition
                    if (!(key instanceof String s))
                        // Instruction de code
                        yield new Result.Error<>("Map key is not a string: " + key);
                    // Appelle une méthode
                    var result = convertTo(coder, entry.getValue());
                    // Embranchement : vérifie une condition
                    if (!(result instanceof Result.Ok(O inner)))
                        // Appelle une méthode
                        yield result.cast();
                    // Appelle une méthode
                    builder.put(s, inner);
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                yield new Result.Ok<>(builder.build());
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            default -> new Result.Error<>("Unsupported type: " + value);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
