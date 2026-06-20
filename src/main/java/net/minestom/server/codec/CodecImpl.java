// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.util.TriState;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.ListBuilder;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapBuilder;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder.MapLike;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.gamedata.DataPack;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryKey;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.ThrowingFunction;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
final class CodecImpl {

    // Déclaration de type (classe/interface/enum/record)
    record RawValueImpl<D>(Transcoder<D> coder, D value) implements Codec.RawValue {
        // Début d'une méthode/d'un bloc
        RawValueImpl {
            // Appelle une méthode
            Objects.requireNonNull(coder, "coder");
            // Appelle une méthode
            Objects.requireNonNull(value, "value");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D1> Result<D1> convertTo(Transcoder<D1> coder) {
            // If the two transcoders are the same instance, we can immediately return the value.
            // Embranchement : vérifie une condition
            if (TranscoderProxy.extractDelegate(this.coder) == TranscoderProxy.extractDelegate(coder))
                //noinspection unchecked
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>((D1) value);
            // Renvoie une valeur à l'appelant
            return this.coder.convertTo(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RawValueCodecImpl() implements Codec<Codec.RawValue> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<RawValue> decode(Transcoder<D> coder, D value) {
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(new RawValueImpl<>(coder, value));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable RawValue value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return value.convertTo(coder);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface PrimitiveEncoder<T> {
        // Appelle une méthode
        <D> D encode(Transcoder<D> coder, T value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PrimitiveImpl<T>(PrimitiveEncoder<T> encoder, Decoder<T> decoder) implements Codec<T> {
        // Début d'une méthode/d'un bloc
        PrimitiveImpl {
            // Appelle une méthode
            Objects.requireNonNull(encoder, "encoder");
            // Appelle une méthode
            Objects.requireNonNull(decoder, "decoder");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Renvoie une valeur à l'appelant
            return decoder.decode(coder, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(encoder.encode(coder, value));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TriStateImpl() implements Codec<TriState> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<TriState> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<Boolean> boolResult = coder.getBoolean(value);
            // Embranchement : vérifie une condition
            if (boolResult instanceof Result.Ok(Boolean bool))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(TriState.byBoolean(bool));
            // Appelle une méthode
            final Result<String> stringResult = coder.getString(value);
            // Embranchement : vérifie une condition
            if (stringResult instanceof Result.Ok(String string)) {
                // Embranchement : vérifie une condition
                if ("true".equalsIgnoreCase(string)) return new Result.Ok<>(TriState.TRUE);
                // Embranchement : vérifie une condition
                if ("false".equalsIgnoreCase(string)) return new Result.Ok<>(TriState.FALSE);
                // Embranchement : vérifie une condition
                if ("default".equalsIgnoreCase(string)) return new Result.Ok<>(TriState.NOT_SET);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("expected true, false, or \"default\", got: " + stringResult);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable TriState value) {
            // Renvoie une valeur à l'appelant
            return switch (value) {
                // Embranchement multiple (switch/case)
                case TRUE -> new Result.Ok<>(coder.createBoolean(true));
                // Embranchement multiple (switch/case)
                case FALSE -> new Result.Ok<>(coder.createBoolean(false));
                // Embranchement multiple (switch/case)
                case null, default -> new Result.Ok<>(coder.createString("default"));
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OptionalImpl<T>(Codec<T> inner, @Nullable T defaultValue) implements Codec<T> {
        // Début d'une méthode/d'un bloc
        OptionalImpl {
            // Appelle une méthode
            Objects.requireNonNull(inner, "inner");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(inner.decode(coder, value).orElse(defaultValue));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Embranchement : vérifie une condition
            if (value == null || Objects.equals(value, defaultValue))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(coder.createNull());
            // Renvoie une valeur à l'appelant
            return inner.encode(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TransformImpl<T, S>(
            // Instruction de code
            Codec<T> inner, ThrowingFunction<T, S> to,
            // Instruction de code
            ThrowingFunction<@Nullable S, T> from
    // Début d'une méthode/d'un bloc
    ) implements Codec<S> {
        // Début d'une méthode/d'un bloc
        TransformImpl {
            // Appelle une méthode
            Objects.requireNonNull(inner, "inner");
            // Appelle une méthode
            Objects.requireNonNull(to, "to");
            // Appelle une méthode
            Objects.requireNonNull(from, "from");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<S> decode(Transcoder<D> coder, D value) {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                final Result<T> innerResult = inner.decode(coder, value);
                // Renvoie une valeur à l'appelant
                return switch (innerResult) {
                    // Embranchement multiple (switch/case)
                    case Result.Ok(T innerValue) -> new Result.Ok<>(to.apply(innerValue));
                    // Embranchement multiple (switch/case)
                    case Result.Error(String error) -> new Result.Error<>(error);
                // Fin d'un bloc/d'une expression
                };
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Renvoie une valeur à l'appelant
                return new Result.Error<>(e.getClass().getSimpleName() + ": " + e.getMessage());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable S value) {
            // Gestion des exceptions
            try {
                // Renvoie une valeur à l'appelant
                return inner.encode(coder, from.apply(value));
            // Début d'une méthode/d'un bloc
            } catch (Exception e) {
                // Renvoie une valeur à l'appelant
                return new Result.Error<>(e.getMessage());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ListImpl<T>(Codec<T> inner, int maxSize) implements Codec<@Unmodifiable List<T>> {
        // Début d'une méthode/d'un bloc
        ListImpl {
            // Appelle une méthode
            Objects.requireNonNull(inner, "inner");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<@Unmodifiable List<T>> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<List<D>> listResult = coder.getList(value);
            // Embranchement : vérifie une condition
            if (!(listResult instanceof Result.Ok(List<D> list)))
                // Renvoie une valeur à l'appelant
                return listResult.cast();
            // Embranchement : vérifie une condition
            if (list.size() > maxSize)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("List size exceeds maximum allowed size: " + maxSize);

            // Appelle une méthode
            final List<T> decodedList = new ArrayList<>(list.size());
            // Boucle : répète un bloc
            for (final D item : list) {
                // Appelle une méthode
                Result<T> decodedItem = inner.decode(coder, item);
                // Embranchement : vérifie une condition
                if (!(decodedItem instanceof Result.Ok(T valueItem)))
                    // Renvoie une valeur à l'appelant
                    return decodedItem.cast();
                // Appelle une méthode
                decodedList.add(valueItem);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(List.copyOf(decodedList));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable List<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (value.size() > maxSize)
                // Lève une exception
                throw new IllegalArgumentException("List size exceeds maximum allowed size: " + maxSize);
            // Appelle une méthode
            final ListBuilder<D> encodedList = coder.createList(value.size());
            // Boucle : répète un bloc
            for (T item : value) {
                // Appelle une méthode
                final Result<D> itemResult = inner.encode(coder, item);
                // Embranchement : vérifie une condition
                if (!(itemResult instanceof Result.Ok(D encodedItem)))
                    // Renvoie une valeur à l'appelant
                    return itemResult.cast();
                // Embranchement : vérifie une condition
                if (encodedItem != null)
                    // Appelle une méthode
                    encodedList.add(encodedItem);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(encodedList.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SetImpl<T>(Codec<T> inner, int maxSize) implements Codec<@Unmodifiable Set<T>> {
        // Début d'une méthode/d'un bloc
        SetImpl {
            // Appelle une méthode
            Objects.requireNonNull(inner, "inner");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<@Unmodifiable Set<T>> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<List<D>> listResult = coder.getList(value);
            // Embranchement : vérifie une condition
            if (!(listResult instanceof Result.Ok(List<D> list)))
                // Renvoie une valeur à l'appelant
                return listResult.cast();
            // Embranchement : vérifie une condition
            if (list.size() > maxSize)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Set size exceeds maximum allowed size: " + maxSize);

            // Appelle une méthode
            final Set<T> decodedSet = new HashSet<>(list.size());
            // Boucle : répète un bloc
            for (final D item : list) {
                // Appelle une méthode
                Result<T> decodedItem = inner.decode(coder, item);
                // Embranchement : vérifie une condition
                if (!(decodedItem instanceof Result.Ok(T valueItem)))
                    // Renvoie une valeur à l'appelant
                    return decodedItem.cast();
                // Appelle une méthode
                decodedSet.add(valueItem);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(Set.copyOf(decodedSet));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Set<T> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (value.size() > maxSize)
                // Lève une exception
                throw new IllegalArgumentException("List size exceeds maximum allowed size: " + maxSize);
            // Appelle une méthode
            ListBuilder<D> encodedList = coder.createList(value.size());
            // Boucle : répète un bloc
            for (T item : value) {
                // Appelle une méthode
                final Result<D> itemResult = inner.encode(coder, item);
                // Embranchement : vérifie une condition
                if (!(itemResult instanceof Result.Ok(D encodedItem)))
                    // Renvoie une valeur à l'appelant
                    return itemResult.cast();
                // Appelle une méthode
                encodedList.add(encodedItem);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(encodedList.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record MapImpl<K, V>(
            // Instruction de code
            Codec<K> keyCodec, Codec<V> valueCodec,
            // Instruction de code
            int maxSize
    // Début d'une méthode/d'un bloc
    ) implements Codec<@Unmodifiable Map<K, V>> {
        // Début d'une méthode/d'un bloc
        MapImpl {
            // Appelle une méthode
            Objects.requireNonNull(keyCodec, "keyCodec");
            // Appelle une méthode
            Objects.requireNonNull(valueCodec, "valueCodec");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<@Unmodifiable Map<K, V>> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<MapLike<D>> mapResult = coder.getMap(value);
            // Embranchement : vérifie une condition
            if (!(mapResult instanceof Result.Ok(MapLike<D> map)))
                // Renvoie une valeur à l'appelant
                return mapResult.cast();

            // Embranchement : vérifie une condition
            if (map.size() > maxSize)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Embranchement : vérifie une condition
            if (map.isEmpty()) return new Result.Ok<>(Map.of());

            // Appelle une méthode
            final Map<K, V> decodedMap = new HashMap<>(map.size());
            // Boucle : répète un bloc
            for (final String key : map.keys()) {
                // Appelle une méthode
                final Result<K> keyResult = keyCodec.decode(coder, coder.createString(key));
                // Embranchement : vérifie une condition
                if (!(keyResult instanceof Result.Ok(K decodedKey)))
                    // Renvoie une valeur à l'appelant
                    return keyResult.cast();
                // The throwing decode here is fine since we are already iterating over known keys.
                // Appelle une méthode
                final Result<V> valueResult = valueCodec.decode(coder, map.getValue(key).orElseThrow());
                // Embranchement : vérifie une condition
                if (!(valueResult instanceof Result.Ok(V decodedValue)))
                    // Renvoie une valeur à l'appelant
                    return valueResult.cast();
                // Appelle une méthode
                decodedMap.put(decodedKey, decodedValue);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(Map.copyOf(decodedMap));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Map<K, V> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (value.size() > maxSize)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Embranchement : vérifie une condition
            if (value.isEmpty()) return new Result.Ok<>(coder.createMap().build());

            // Appelle une méthode
            final MapBuilder<D> map = coder.createMap();
            // Boucle : répète un bloc
            for (final Map.Entry<K, V> entry : value.entrySet()) {
                // Appelle une méthode
                final Result<D> keyResult = keyCodec.encode(coder, entry.getKey());
                // Embranchement : vérifie une condition
                if (!(keyResult instanceof Result.Ok(D encodedKey)))
                    // Renvoie une valeur à l'appelant
                    return keyResult.cast();
                // Appelle une méthode
                final Result<D> valueResult = valueCodec.encode(coder, entry.getValue());
                // Embranchement : vérifie une condition
                if (!(valueResult instanceof Result.Ok(D encodedValue)))
                    // Renvoie une valeur à l'appelant
                    return valueResult.cast();
                // Appelle une méthode
                map.put(encodedKey, encodedValue);
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(map.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TypedMapImpl<K, V>(
            // Instruction de code
            Codec<K> keyCodec,
            // Instruction de code
            Function<K, Codec<V>> valueMapper,
            // Instruction de code
            int maxSize,
            // Annotation pour l'élément suivant
            @Nullable Map<K, Codec<V>> mutableResolvedValueCache
    // Début d'une méthode/d'un bloc
    ) implements Codec<@Unmodifiable Map<K, V>> {
        // Début d'une méthode/d'un bloc
        TypedMapImpl {
            // Appelle une méthode
            Objects.requireNonNull(keyCodec, "keyCodec");
            // Appelle une méthode
            Objects.requireNonNull(valueMapper, "valueMapper");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<@Unmodifiable Map<K, V>> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<MapLike<D>> mapResult = coder.getMap(value);
            // Embranchement : vérifie une condition
            if (!(mapResult instanceof Result.Ok(MapLike<D> map)))
                // Renvoie une valeur à l'appelant
                return mapResult.cast();

            // Embranchement : vérifie une condition
            if (map.size() > maxSize)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Embranchement : vérifie une condition
            if (map.isEmpty()) return new Result.Ok<>(Map.of());

            // Appelle une méthode
            final Map<K, V> decodedMap = new HashMap<>(map.size());
            // Boucle : répète un bloc
            for (final String key : map.keys()) {
                // Appelle une méthode
                final Result<K> keyResult = keyCodec.decode(coder, coder.createString(key));
                // Embranchement : vérifie une condition
                if (!(keyResult instanceof Result.Ok(K decodedKey)))
                    // Renvoie une valeur à l'appelant
                    return keyResult.cast();

                // Affecte une valeur
                final Codec<V> valueCodec = mutableResolvedValueCache != null
                        // Instruction de code
                        ? mutableResolvedValueCache.computeIfAbsent(decodedKey, valueMapper)
                        // Appelle une méthode
                        : valueMapper.apply(decodedKey);

                // The throwing decode here is fine since we are already iterating over known keys.
                // Appelle une méthode
                final Result<V> valueResult = valueCodec.decode(coder, map.getValue(key).orElseThrow());
                // Embranchement : vérifie une condition
                if (!(valueResult instanceof Result.Ok(V decodedValue)))
                    // Renvoie une valeur à l'appelant
                    return valueResult.mapError(e -> key + ": " + e).cast();
                // Appelle une méthode
                decodedMap.put(decodedKey, decodedValue);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(Map.copyOf(decodedMap));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Map<K, V> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Embranchement : vérifie une condition
            if (value.size() > maxSize)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Embranchement : vérifie une condition
            if (value.isEmpty()) return new Result.Ok<>(coder.createMap().build());

            // Appelle une méthode
            final MapBuilder<D> map = coder.createMap();
            // Boucle : répète un bloc
            for (final Map.Entry<K, V> entry : value.entrySet()) {
                // Appelle une méthode
                final Result<D> keyResult = keyCodec.encode(coder, entry.getKey());
                // Embranchement : vérifie une condition
                if (!(keyResult instanceof Result.Ok(D encodedKey)))
                    // Renvoie une valeur à l'appelant
                    return keyResult.cast();

                // Affecte une valeur
                final Codec<V> valueCodec = mutableResolvedValueCache != null
                        // Instruction de code
                        ? mutableResolvedValueCache.computeIfAbsent(entry.getKey(), valueMapper)
                        // Appelle une méthode
                        : valueMapper.apply(entry.getKey());

                // Appelle une méthode
                final Result<D> valueResult = valueCodec.encode(coder, entry.getValue());
                // Embranchement : vérifie une condition
                if (!(valueResult instanceof Result.Ok(D encodedValue)))
                    // Renvoie une valeur à l'appelant
                    return valueResult.cast();
                // Appelle une méthode
                map.put(encodedKey, encodedValue);
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(map.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnionImpl<T, R>(
            // Instruction de code
            String keyField, Codec<T> keyCodec,
            // Instruction de code
            Function<T, @Nullable StructCodec<? extends R>> serializers,
            // Instruction de code
            Function<R, ? extends T> keyFunc
    // Début d'une méthode/d'un bloc
    ) implements StructCodec<R> {
        // Début d'une méthode/d'un bloc
        UnionImpl {
            // Appelle une méthode
            Objects.requireNonNull(serializers, "serializers");
            // Appelle une méthode
            Objects.requireNonNull(keyField, "keyField");
            // Appelle une méthode
            Objects.requireNonNull(keyFunc, "keyFunc");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Appelle une méthode
            Result<T> keyResult = map.getValue(keyField).map(key -> keyCodec.decode(coder, key));
            // Embranchement : vérifie une condition
            if (keyResult instanceof Result.Error<T> && keyCodec instanceof OptionalImpl(var _, var defaultValue))
                // Appelle une méthode
                keyResult = new Result.Ok<>(defaultValue);
            // Embranchement : vérifie une condition
            if (!(keyResult instanceof Result.Ok(T key)))
                // Renvoie une valeur à l'appelant
                return keyResult.cast();
            // Appelle une méthode
            final StructCodec<? extends R> serializer = serializers.apply(key);
            // Embranchement : vérifie une condition
            if (serializer == null) return new Result.Error<>("no union value: " + key);
            // Renvoie une valeur à l'appelant
            return (Result<R>) serializer.decodeFromMap(coder, map);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
            // Appelle une méthode
            final T key = keyFunc.apply(value);
            // Appelle une méthode
            final StructCodec<R> serializer = (StructCodec<R>) serializers.apply(key);
            // Embranchement : vérifie une condition
            if (serializer == null) return new Result.Error<>("no union value: " + key);

            // Appelle une méthode
            final Result<D> keyResult = keyCodec.encode(coder, key);
            // Embranchement : vérifie une condition
            if (!(keyResult instanceof Result.Ok(D keyValue)))
                // Renvoie une valeur à l'appelant
                return keyResult.cast();
            // Embranchement : vérifie une condition
            if (keyValue == null) return new Result.Error<>("null");

            // Appelle une méthode
            map.put(keyField, keyValue);
            // Renvoie une valeur à l'appelant
            return serializer.encodeToMap(coder, value, map);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Déclaration de type (classe/interface/enum/record)
    record RegistryTaggedUnionImpl<T>(
            // Instruction de code
            String key,
            // Instruction de code
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Instruction de code
            Function<T, StructCodec<? extends T>> valueToCodec
    // Début d'une méthode/d'un bloc
    ) implements StructCodec<T> {
        // Début d'une méthode/d'un bloc
        RegistryTaggedUnionImpl {
            // Appelle une méthode
            Objects.requireNonNull(key, "key");
            // Appelle une méthode
            Objects.requireNonNull(registrySelector, "registrySelector");
            // Appelle une méthode
            Objects.requireNonNull(valueToCodec, "valueToCodec");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<T> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Appelle une méthode
            final var registry = registrySelector.select(context.registries());

            // Appelle une méthode
            final Result<String> type = map.getValue(key).map(coder::getString);
            // Embranchement : vérifie une condition
            if (!(type instanceof Result.Ok(@KeyPattern String tag)))
                // Renvoie une valeur à l'appelant
                return type.mapError(e -> key + ": " + e).cast();
            // Appelle une méthode
            final StructCodec<T> innerCodec = (StructCodec<T>) registry.get(Key.key(tag));
            // Embranchement : vérifie une condition
            if (innerCodec == null) return new Result.Error<>("No such key: " + tag);

            // Renvoie une valeur à l'appelant
            return innerCodec.decodeFromMap(coder, map);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<@Nullable D> encodeToMap(Transcoder<D> coder, T value, MapBuilder<D> map) {
            // Embranchement : vérifie une condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Missing registries in transcoder");
            // Appelle une méthode
            final var registry = registrySelector.select(context.registries());

            //noinspection unchecked
            // Appelle une méthode
            final StructCodec<T> innerCodec = (StructCodec<T>) valueToCodec.apply(value);
            // Appelle une méthode
            final RegistryKey<StructCodec<? extends T>> type = registry.getKey(innerCodec);
            // Embranchement : vérifie une condition
            if (type == null) return new Result.Error<>("Unregistered serializer for: " + value);
            // Embranchement : vérifie une condition
            if (context.forClient() && registry.getPack(type) != DataPack.MINECRAFT_CORE)
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(null);

            // Appelle une méthode
            map.put(key, coder.createString(type.key().asString()));
            // Renvoie une valeur à l'appelant
            return innerCodec.encodeToMap(coder, value, map);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class RecursiveImpl<T> implements Codec<T> {
        // Instruction de code
        final Codec<T> delegate;

        // Début d'une méthode/d'un bloc
        public RecursiveImpl(Function<Codec<T>, Codec<T>> self) {
            // Appelle une méthode
            Objects.requireNonNull(self, "self");
            // Accès à l'objet courant/parent
            this.delegate = Objects.requireNonNull(self.apply(this), "delegate");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Renvoie une valeur à l'appelant
            return delegate.decode(coder, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Renvoie une valeur à l'appelant
            return delegate.encode(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class ForwardRefImpl<T> implements Codec<T> {
        // Instruction de code
        private final Supplier<Codec<T>> delegateFunc;
        // Instruction de code
        private @Nullable Codec<T> delegate;

        // Début d'une méthode/d'un bloc
        ForwardRefImpl(Supplier<Codec<T>> delegateFunc) {
            // Accès à l'objet courant/parent
            this.delegateFunc = Objects.requireNonNull(delegateFunc, "delegateFunc");
        // Fin d'un bloc/d'une expression
        }

        // Racing should produce the same result (bogon data race, excluding identity)
        // Début d'une méthode/d'un bloc
        private Codec<T> delegate() {
            // Affecte une valeur
            Codec<T> delegate = this.delegate;
            // Embranchement : vérifie une condition
            if (delegate == null)
                // Appelle une méthode
                delegate = this.delegate = Objects.requireNonNull(delegateFunc.get(), "delegate");
            // Renvoie une valeur à l'appelant
            return delegate;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Renvoie une valeur à l'appelant
            return delegate().decode(coder, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Renvoie une valeur à l'appelant
            return delegate().encode(coder, value);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OrElseImpl<T>(Codec<T> primary, Codec<T> secondary) implements Codec<T> {
        // Début d'une méthode/d'un bloc
        OrElseImpl {
            // Appelle une méthode
            Objects.requireNonNull(primary, "primary");
            // Appelle une méthode
            Objects.requireNonNull(secondary, "secondary");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<T> primaryResult = primary.decode(coder, value);
            // Embranchement : vérifie une condition
            if (primaryResult instanceof Result.Ok<T> primaryOk)
                // Renvoie une valeur à l'appelant
                return primaryOk;

            // Primary did not work, try secondary
            // Appelle une méthode
            final Result<T> secondaryResult = secondary.decode(coder, value);
            // Embranchement : vérifie une condition
            if (secondaryResult instanceof Result.Ok<T> secondaryOk)
                // Renvoie une valeur à l'appelant
                return secondaryOk;

            // Secondary did not work either, return error from primary.
            // Renvoie une valeur à l'appelant
            return primaryResult;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Appelle une méthode
            final Result<D> primaryResult = primary.encode(coder, value);
            // Embranchement : vérifie une condition
            if (primaryResult instanceof Result.Ok<D> primaryOk)
                // Renvoie une valeur à l'appelant
                return primaryOk;

            // Primary did not work, try secondary
            // Appelle une méthode
            final Result<D> secondaryResult = secondary.encode(coder, value);
            // Embranchement : vérifie une condition
            if (secondaryResult instanceof Result.Ok<D> secondaryOk)
                // Renvoie une valeur à l'appelant
                return secondaryOk;

            // Secondary did not work either, return error from primary.
            // Renvoie une valeur à l'appelant
            return primaryResult;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BlockPositionImpl() implements Codec<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Point> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<int[]> intArrayResult = coder.getIntArray(value);
            // Embranchement : vérifie une condition
            if (!(intArrayResult instanceof Result.Ok(int[] intArray)))
                // Renvoie une valeur à l'appelant
                return intArrayResult.cast();
            // Embranchement : vérifie une condition
            if (intArray.length != 3)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Invalid length for Point, expected 3 but got " + intArray.length);
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(new Vec(intArray[0], intArray[1], intArray[2]));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Point value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(coder.createIntArray(new int[]{
                    // Instruction de code
                    (int) value.x(),
                    // Instruction de code
                    (int) value.y(),
                    // Instruction de code
                    (int) value.z()
            // Instruction de code
            }));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record EitherImpl<L, R>(Codec<L> leftCodec, Codec<R> rightCodec) implements Codec<Either<L, R>> {
        // Début d'une méthode/d'un bloc
        EitherImpl {
            // Appelle une méthode
            Objects.requireNonNull(leftCodec, "leftCodec");
            // Appelle une méthode
            Objects.requireNonNull(rightCodec, "rightCodec");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Either<L, R>> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<L> leftResult = leftCodec.decode(coder, value);
            // Embranchement : vérifie une condition
            if (leftResult instanceof Result.Ok(L leftValue))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(Either.left(leftValue));
            // Appelle une méthode
            final Result<R> rightResult = rightCodec.decode(coder, value);
            // Embranchement : vérifie une condition
            if (rightResult instanceof Result.Ok(R rightValue))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(Either.right(rightValue));
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Failed to decode Either: " + leftResult + ", " + rightResult);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Either<L, R> value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Renvoie une valeur à l'appelant
            return switch (value) {
                // Embranchement multiple (switch/case)
                case Either.Left(L leftValue) -> leftCodec.encode(coder, leftValue);
                // Embranchement multiple (switch/case)
                case Either.Right(R rightValue) -> rightCodec.encode(coder, rightValue);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record EitherStructImpl<L, R>(
            // Instruction de code
            StructCodec<L> leftCodec,
            // Instruction de code
            StructCodec<R> rightCodec
    // Début d'une méthode/d'un bloc
    ) implements StructCodec<Either<L, R>> {
        // Début d'une méthode/d'un bloc
        public EitherStructImpl {
            // Appelle une méthode
            Objects.requireNonNull(leftCodec, "leftCodec");
            // Appelle une méthode
            Objects.requireNonNull(rightCodec, "rightCodec");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Either<L, R>> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Appelle une méthode
            final Result<L> leftResult = leftCodec.decodeFromMap(coder, map);
            // Embranchement : vérifie une condition
            if (leftResult instanceof Result.Ok(L leftValue))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(Either.left(leftValue));
            // Appelle une méthode
            final Result<R> rightResult = rightCodec.decodeFromMap(coder, map);
            // Embranchement : vérifie une condition
            if (rightResult instanceof Result.Ok(R rightValue))
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(Either.right(rightValue));
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Failed to decode Either: " + leftResult + ", " + rightResult);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, Either<L, R> value, MapBuilder<D> map) {
            // Renvoie une valeur à l'appelant
            return switch (value) {
                // Embranchement multiple (switch/case)
                case Either.Left(L leftValue) -> leftCodec.encodeToMap(coder, leftValue, map);
                // Embranchement multiple (switch/case)
                case Either.Right(R rightValue) -> rightCodec.encodeToMap(coder, rightValue, map);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Vector3DImpl() implements Codec<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<Point> decode(Transcoder<D> coder, D value) {
            // Appelle une méthode
            final Result<List<D>> listResult = coder.getList(value);
            // Embranchement : vérifie une condition
            if (!(listResult instanceof Result.Ok(List<D> list)))
                // Renvoie une valeur à l'appelant
                return listResult.cast();
            // Embranchement : vérifie une condition
            if (list.size() != 3)
                // Renvoie une valeur à l'appelant
                return new Result.Error<>("Invalid length for Vector, expected 3 but got " + list.size());
            // Appelle une méthode
            final Result<Double> xResult = coder.getDouble(list.get(0));
            // Embranchement : vérifie une condition
            if (!(xResult instanceof Result.Ok(Double x)))
                // Renvoie une valeur à l'appelant
                return xResult.cast();
            // Appelle une méthode
            final Result<Double> yResult = coder.getDouble(list.get(1));
            // Embranchement : vérifie une condition
            if (!(yResult instanceof Result.Ok(Double y)))
                // Renvoie une valeur à l'appelant
                return yResult.cast();
            // Appelle une méthode
            final Result<Double> zResult = coder.getDouble(list.get(2));
            // Embranchement : vérifie une condition
            if (!(zResult instanceof Result.Ok(Double z)))
                // Renvoie une valeur à l'appelant
                return zResult.cast();
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(new Vec(x, y, z));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Point value) {
            // Embranchement : vérifie une condition
            if (value == null) return new Result.Error<>("null");
            // Appelle une méthode
            final ListBuilder<D> list = coder.createList(3);
            // Appelle une méthode
            list.add(coder.createDouble(value.x()));
            // Appelle une méthode
            list.add(coder.createDouble(value.y()));
            // Appelle une méthode
            list.add(coder.createDouble(value.z()));
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(list.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record CompoundBinaryTagImpl() implements StructCodec<CompoundBinaryTag> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<CompoundBinaryTag> decodeFromMap(Transcoder<D> coder, Transcoder.MapLike<D> map) {
            // Appelle une méthode
            final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
            // Boucle : répète un bloc
            for (String key : map.keys()) {
                // Affecte une valeur
                final Result<BinaryTag> tagResult = map.getValue(key)
                        // Appelle une méthode
                        .map(nbt -> RawValue.of(coder, nbt).convertTo(Transcoder.NBT));
                // Embranchement : vérifie une condition
                if (!(tagResult instanceof Result.Ok(BinaryTag tag)))
                    // Renvoie une valeur à l'appelant
                    return tagResult.mapError(e -> key + ": " + e).cast();
                // Appelle une méthode
                builder.put(key, tag);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(builder.build());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public <D> Result<D> encodeToMap(Transcoder<D> coder, CompoundBinaryTag value, Transcoder.MapBuilder<D> map) {
            // Boucle : répète un bloc
            for (var entry : value) {
                // Appelle une méthode
                final Result<D> entryValue = RawValue.of(Transcoder.NBT, entry.getValue()).convertTo(coder);
                // Embranchement : vérifie une condition
                if (!(entryValue instanceof Result.Ok(D okValue)))
                    // Renvoie une valeur à l'appelant
                    return entryValue.mapError(e -> entry.getKey() + ": " + e);
                // Appelle une méthode
                map.put(entry.getKey(), okValue);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return new Result.Ok<>(map.build());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
