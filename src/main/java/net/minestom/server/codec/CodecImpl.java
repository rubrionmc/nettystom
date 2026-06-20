// Package declaration for this file
package net.minestom.server.codec;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.util.TriState;
// Import of a required class
import net.minestom.server.codec.Transcoder.ListBuilder;
// Import of a required class
import net.minestom.server.codec.Transcoder.MapBuilder;
// Import of a required class
import net.minestom.server.codec.Transcoder.MapLike;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.ThrowingFunction;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
final class CodecImpl {

    // Type declaration (class/interface/enum/record)
    record RawValueImpl<D>(Transcoder<D> coder, D value) implements Codec.RawValue {
        // Start of a method/block
        RawValueImpl {
            // Calls a method
            Objects.requireNonNull(coder, "coder");
            // Calls a method
            Objects.requireNonNull(value, "value");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D1> Result<D1> convertTo(Transcoder<D1> coder) {
            // If the two transcoders are the same instance, we can immediately return the value.
            // Branch: checks a condition
            if (TranscoderProxy.extractDelegate(this.coder) == TranscoderProxy.extractDelegate(coder))
                //noinspection unchecked
                // Returns a value to the caller
                return new Result.Ok<>((D1) value);
            // Returns a value to the caller
            return this.coder.convertTo(coder, value);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RawValueCodecImpl() implements Codec<Codec.RawValue> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<RawValue> decode(Transcoder<D> coder, D value) {
            // Returns a value to the caller
            return new Result.Ok<>(new RawValueImpl<>(coder, value));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable RawValue value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return value.convertTo(coder);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface PrimitiveEncoder<T> {
        // Calls a method
        <D> D encode(Transcoder<D> coder, T value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PrimitiveImpl<T>(PrimitiveEncoder<T> encoder, Decoder<T> decoder) implements Codec<T> {
        // Start of a method/block
        PrimitiveImpl {
            // Calls a method
            Objects.requireNonNull(encoder, "encoder");
            // Calls a method
            Objects.requireNonNull(decoder, "decoder");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Returns a value to the caller
            return decoder.decode(coder, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return new Result.Ok<>(encoder.encode(coder, value));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TriStateImpl() implements Codec<TriState> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<TriState> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<Boolean> boolResult = coder.getBoolean(value);
            // Branch: checks a condition
            if (boolResult instanceof Result.Ok(Boolean bool))
                // Returns a value to the caller
                return new Result.Ok<>(TriState.byBoolean(bool));
            // Calls a method
            final Result<String> stringResult = coder.getString(value);
            // Branch: checks a condition
            if (stringResult instanceof Result.Ok(String string)) {
                // Branch: checks a condition
                if ("true".equalsIgnoreCase(string)) return new Result.Ok<>(TriState.TRUE);
                // Branch: checks a condition
                if ("false".equalsIgnoreCase(string)) return new Result.Ok<>(TriState.FALSE);
                // Branch: checks a condition
                if ("default".equalsIgnoreCase(string)) return new Result.Ok<>(TriState.NOT_SET);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Error<>("expected true, false, or \"default\", got: " + stringResult);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable TriState value) {
            // Returns a value to the caller
            return switch (value) {
                // Multiple branching (switch/case)
                case TRUE -> new Result.Ok<>(coder.createBoolean(true));
                // Multiple branching (switch/case)
                case FALSE -> new Result.Ok<>(coder.createBoolean(false));
                // Multiple branching (switch/case)
                case null, default -> new Result.Ok<>(coder.createString("default"));
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OptionalImpl<T>(Codec<T> inner, @Nullable T defaultValue) implements Codec<T> {
        // Start of a method/block
        OptionalImpl {
            // Calls a method
            Objects.requireNonNull(inner, "inner");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Returns a value to the caller
            return new Result.Ok<>(inner.decode(coder, value).orElse(defaultValue));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Branch: checks a condition
            if (value == null || Objects.equals(value, defaultValue))
                // Returns a value to the caller
                return new Result.Ok<>(coder.createNull());
            // Returns a value to the caller
            return inner.encode(coder, value);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TransformImpl<T, S>(
            // Code statement
            Codec<T> inner, ThrowingFunction<T, S> to,
            // Code statement
            ThrowingFunction<@Nullable S, T> from
    // Start of a method/block
    ) implements Codec<S> {
        // Start of a method/block
        TransformImpl {
            // Calls a method
            Objects.requireNonNull(inner, "inner");
            // Calls a method
            Objects.requireNonNull(to, "to");
            // Calls a method
            Objects.requireNonNull(from, "from");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<S> decode(Transcoder<D> coder, D value) {
            // Exception handling
            try {
                // Calls a method
                final Result<T> innerResult = inner.decode(coder, value);
                // Returns a value to the caller
                return switch (innerResult) {
                    // Multiple branching (switch/case)
                    case Result.Ok(T innerValue) -> new Result.Ok<>(to.apply(innerValue));
                    // Multiple branching (switch/case)
                    case Result.Error(String error) -> new Result.Error<>(error);
                // End of a block/expression
                };
            // Start of a method/block
            } catch (Exception e) {
                // Returns a value to the caller
                return new Result.Error<>(e.getClass().getSimpleName() + ": " + e.getMessage());
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable S value) {
            // Exception handling
            try {
                // Returns a value to the caller
                return inner.encode(coder, from.apply(value));
            // Start of a method/block
            } catch (Exception e) {
                // Returns a value to the caller
                return new Result.Error<>(e.getMessage());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ListImpl<T>(Codec<T> inner, int maxSize) implements Codec<@Unmodifiable List<T>> {
        // Start of a method/block
        ListImpl {
            // Calls a method
            Objects.requireNonNull(inner, "inner");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<@Unmodifiable List<T>> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<List<D>> listResult = coder.getList(value);
            // Branch: checks a condition
            if (!(listResult instanceof Result.Ok(List<D> list)))
                // Returns a value to the caller
                return listResult.cast();
            // Branch: checks a condition
            if (list.size() > maxSize)
                // Returns a value to the caller
                return new Result.Error<>("List size exceeds maximum allowed size: " + maxSize);

            // Calls a method
            final List<T> decodedList = new ArrayList<>(list.size());
            // Loop: repeats a block
            for (final D item : list) {
                // Calls a method
                Result<T> decodedItem = inner.decode(coder, item);
                // Branch: checks a condition
                if (!(decodedItem instanceof Result.Ok(T valueItem)))
                    // Returns a value to the caller
                    return decodedItem.cast();
                // Calls a method
                decodedList.add(valueItem);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(List.copyOf(decodedList));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable List<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (value.size() > maxSize)
                // Throws an exception
                throw new IllegalArgumentException("List size exceeds maximum allowed size: " + maxSize);
            // Calls a method
            final ListBuilder<D> encodedList = coder.createList(value.size());
            // Loop: repeats a block
            for (T item : value) {
                // Calls a method
                final Result<D> itemResult = inner.encode(coder, item);
                // Branch: checks a condition
                if (!(itemResult instanceof Result.Ok(D encodedItem)))
                    // Returns a value to the caller
                    return itemResult.cast();
                // Branch: checks a condition
                if (encodedItem != null)
                    // Calls a method
                    encodedList.add(encodedItem);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(encodedList.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SetImpl<T>(Codec<T> inner, int maxSize) implements Codec<@Unmodifiable Set<T>> {
        // Start of a method/block
        SetImpl {
            // Calls a method
            Objects.requireNonNull(inner, "inner");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<@Unmodifiable Set<T>> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<List<D>> listResult = coder.getList(value);
            // Branch: checks a condition
            if (!(listResult instanceof Result.Ok(List<D> list)))
                // Returns a value to the caller
                return listResult.cast();
            // Branch: checks a condition
            if (list.size() > maxSize)
                // Returns a value to the caller
                return new Result.Error<>("Set size exceeds maximum allowed size: " + maxSize);

            // Calls a method
            final Set<T> decodedSet = new HashSet<>(list.size());
            // Loop: repeats a block
            for (final D item : list) {
                // Calls a method
                Result<T> decodedItem = inner.decode(coder, item);
                // Branch: checks a condition
                if (!(decodedItem instanceof Result.Ok(T valueItem)))
                    // Returns a value to the caller
                    return decodedItem.cast();
                // Calls a method
                decodedSet.add(valueItem);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(Set.copyOf(decodedSet));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Set<T> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (value.size() > maxSize)
                // Throws an exception
                throw new IllegalArgumentException("List size exceeds maximum allowed size: " + maxSize);
            // Calls a method
            ListBuilder<D> encodedList = coder.createList(value.size());
            // Loop: repeats a block
            for (T item : value) {
                // Calls a method
                final Result<D> itemResult = inner.encode(coder, item);
                // Branch: checks a condition
                if (!(itemResult instanceof Result.Ok(D encodedItem)))
                    // Returns a value to the caller
                    return itemResult.cast();
                // Calls a method
                encodedList.add(encodedItem);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(encodedList.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record MapImpl<K, V>(
            // Code statement
            Codec<K> keyCodec, Codec<V> valueCodec,
            // Code statement
            int maxSize
    // Start of a method/block
    ) implements Codec<@Unmodifiable Map<K, V>> {
        // Start of a method/block
        MapImpl {
            // Calls a method
            Objects.requireNonNull(keyCodec, "keyCodec");
            // Calls a method
            Objects.requireNonNull(valueCodec, "valueCodec");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<@Unmodifiable Map<K, V>> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<MapLike<D>> mapResult = coder.getMap(value);
            // Branch: checks a condition
            if (!(mapResult instanceof Result.Ok(MapLike<D> map)))
                // Returns a value to the caller
                return mapResult.cast();

            // Branch: checks a condition
            if (map.size() > maxSize)
                // Returns a value to the caller
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Branch: checks a condition
            if (map.isEmpty()) return new Result.Ok<>(Map.of());

            // Calls a method
            final Map<K, V> decodedMap = new HashMap<>(map.size());
            // Loop: repeats a block
            for (final String key : map.keys()) {
                // Calls a method
                final Result<K> keyResult = keyCodec.decode(coder, coder.createString(key));
                // Branch: checks a condition
                if (!(keyResult instanceof Result.Ok(K decodedKey)))
                    // Returns a value to the caller
                    return keyResult.cast();
                // The throwing decode here is fine since we are already iterating over known keys.
                // Calls a method
                final Result<V> valueResult = valueCodec.decode(coder, map.getValue(key).orElseThrow());
                // Branch: checks a condition
                if (!(valueResult instanceof Result.Ok(V decodedValue)))
                    // Returns a value to the caller
                    return valueResult.cast();
                // Calls a method
                decodedMap.put(decodedKey, decodedValue);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(Map.copyOf(decodedMap));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Map<K, V> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (value.size() > maxSize)
                // Returns a value to the caller
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Branch: checks a condition
            if (value.isEmpty()) return new Result.Ok<>(coder.createMap().build());

            // Calls a method
            final MapBuilder<D> map = coder.createMap();
            // Loop: repeats a block
            for (final Map.Entry<K, V> entry : value.entrySet()) {
                // Calls a method
                final Result<D> keyResult = keyCodec.encode(coder, entry.getKey());
                // Branch: checks a condition
                if (!(keyResult instanceof Result.Ok(D encodedKey)))
                    // Returns a value to the caller
                    return keyResult.cast();
                // Calls a method
                final Result<D> valueResult = valueCodec.encode(coder, entry.getValue());
                // Branch: checks a condition
                if (!(valueResult instanceof Result.Ok(D encodedValue)))
                    // Returns a value to the caller
                    return valueResult.cast();
                // Calls a method
                map.put(encodedKey, encodedValue);
            // End of a block/expression
            }

            // Returns a value to the caller
            return new Result.Ok<>(map.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TypedMapImpl<K, V>(
            // Code statement
            Codec<K> keyCodec,
            // Code statement
            Function<K, Codec<V>> valueMapper,
            // Code statement
            int maxSize,
            // Annotation for the following element
            @Nullable Map<K, Codec<V>> mutableResolvedValueCache
    // Start of a method/block
    ) implements Codec<@Unmodifiable Map<K, V>> {
        // Start of a method/block
        TypedMapImpl {
            // Calls a method
            Objects.requireNonNull(keyCodec, "keyCodec");
            // Calls a method
            Objects.requireNonNull(valueMapper, "valueMapper");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<@Unmodifiable Map<K, V>> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<MapLike<D>> mapResult = coder.getMap(value);
            // Branch: checks a condition
            if (!(mapResult instanceof Result.Ok(MapLike<D> map)))
                // Returns a value to the caller
                return mapResult.cast();

            // Branch: checks a condition
            if (map.size() > maxSize)
                // Returns a value to the caller
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Branch: checks a condition
            if (map.isEmpty()) return new Result.Ok<>(Map.of());

            // Calls a method
            final Map<K, V> decodedMap = new HashMap<>(map.size());
            // Loop: repeats a block
            for (final String key : map.keys()) {
                // Calls a method
                final Result<K> keyResult = keyCodec.decode(coder, coder.createString(key));
                // Branch: checks a condition
                if (!(keyResult instanceof Result.Ok(K decodedKey)))
                    // Returns a value to the caller
                    return keyResult.cast();

                // Assigns a value
                final Codec<V> valueCodec = mutableResolvedValueCache != null
                        // Code statement
                        ? mutableResolvedValueCache.computeIfAbsent(decodedKey, valueMapper)
                        // Calls a method
                        : valueMapper.apply(decodedKey);

                // The throwing decode here is fine since we are already iterating over known keys.
                // Calls a method
                final Result<V> valueResult = valueCodec.decode(coder, map.getValue(key).orElseThrow());
                // Branch: checks a condition
                if (!(valueResult instanceof Result.Ok(V decodedValue)))
                    // Returns a value to the caller
                    return valueResult.mapError(e -> key + ": " + e).cast();
                // Calls a method
                decodedMap.put(decodedKey, decodedValue);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(Map.copyOf(decodedMap));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Map<K, V> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Branch: checks a condition
            if (value.size() > maxSize)
                // Returns a value to the caller
                return new Result.Error<>("Map size exceeds maximum allowed size: " + maxSize);
            // Branch: checks a condition
            if (value.isEmpty()) return new Result.Ok<>(coder.createMap().build());

            // Calls a method
            final MapBuilder<D> map = coder.createMap();
            // Loop: repeats a block
            for (final Map.Entry<K, V> entry : value.entrySet()) {
                // Calls a method
                final Result<D> keyResult = keyCodec.encode(coder, entry.getKey());
                // Branch: checks a condition
                if (!(keyResult instanceof Result.Ok(D encodedKey)))
                    // Returns a value to the caller
                    return keyResult.cast();

                // Assigns a value
                final Codec<V> valueCodec = mutableResolvedValueCache != null
                        // Code statement
                        ? mutableResolvedValueCache.computeIfAbsent(entry.getKey(), valueMapper)
                        // Calls a method
                        : valueMapper.apply(entry.getKey());

                // Calls a method
                final Result<D> valueResult = valueCodec.encode(coder, entry.getValue());
                // Branch: checks a condition
                if (!(valueResult instanceof Result.Ok(D encodedValue)))
                    // Returns a value to the caller
                    return valueResult.cast();
                // Calls a method
                map.put(encodedKey, encodedValue);
            // End of a block/expression
            }

            // Returns a value to the caller
            return new Result.Ok<>(map.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnionImpl<T, R>(
            // Code statement
            String keyField, Codec<T> keyCodec,
            // Code statement
            Function<T, @Nullable StructCodec<? extends R>> serializers,
            // Code statement
            Function<R, ? extends T> keyFunc
    // Start of a method/block
    ) implements StructCodec<R> {
        // Start of a method/block
        UnionImpl {
            // Calls a method
            Objects.requireNonNull(serializers, "serializers");
            // Calls a method
            Objects.requireNonNull(keyField, "keyField");
            // Calls a method
            Objects.requireNonNull(keyFunc, "keyFunc");
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<R> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Calls a method
            Result<T> keyResult = map.getValue(keyField).map(key -> keyCodec.decode(coder, key));
            // Branch: checks a condition
            if (keyResult instanceof Result.Error<T> && keyCodec instanceof OptionalImpl(var _, var defaultValue))
                // Calls a method
                keyResult = new Result.Ok<>(defaultValue);
            // Branch: checks a condition
            if (!(keyResult instanceof Result.Ok(T key)))
                // Returns a value to the caller
                return keyResult.cast();
            // Calls a method
            final StructCodec<? extends R> serializer = serializers.apply(key);
            // Branch: checks a condition
            if (serializer == null) return new Result.Error<>("no union value: " + key);
            // Returns a value to the caller
            return (Result<R>) serializer.decodeFromMap(coder, map);
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, R value, MapBuilder<D> map) {
            // Calls a method
            final T key = keyFunc.apply(value);
            // Calls a method
            final StructCodec<R> serializer = (StructCodec<R>) serializers.apply(key);
            // Branch: checks a condition
            if (serializer == null) return new Result.Error<>("no union value: " + key);

            // Calls a method
            final Result<D> keyResult = keyCodec.encode(coder, key);
            // Branch: checks a condition
            if (!(keyResult instanceof Result.Ok(D keyValue)))
                // Returns a value to the caller
                return keyResult.cast();
            // Branch: checks a condition
            if (keyValue == null) return new Result.Error<>("null");

            // Calls a method
            map.put(keyField, keyValue);
            // Returns a value to the caller
            return serializer.encodeToMap(coder, value, map);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Type declaration (class/interface/enum/record)
    record RegistryTaggedUnionImpl<T>(
            // Code statement
            String key,
            // Code statement
            Registries.Selector<StructCodec<? extends T>> registrySelector,
            // Code statement
            Function<T, StructCodec<? extends T>> valueToCodec
    // Start of a method/block
    ) implements StructCodec<T> {
        // Start of a method/block
        RegistryTaggedUnionImpl {
            // Calls a method
            Objects.requireNonNull(key, "key");
            // Calls a method
            Objects.requireNonNull(registrySelector, "registrySelector");
            // Calls a method
            Objects.requireNonNull(valueToCodec, "valueToCodec");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<T> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Calls a method
            final var registry = registrySelector.select(context.registries());

            // Calls a method
            final Result<String> type = map.getValue(key).map(coder::getString);
            // Branch: checks a condition
            if (!(type instanceof Result.Ok(@KeyPattern String tag)))
                // Returns a value to the caller
                return type.mapError(e -> key + ": " + e).cast();
            // Calls a method
            final StructCodec<T> innerCodec = (StructCodec<T>) registry.get(Key.key(tag));
            // Branch: checks a condition
            if (innerCodec == null) return new Result.Error<>("No such key: " + tag);

            // Returns a value to the caller
            return innerCodec.decodeFromMap(coder, map);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<@Nullable D> encodeToMap(Transcoder<D> coder, T value, MapBuilder<D> map) {
            // Branch: checks a condition
            if (!(coder instanceof RegistryTranscoder<D> context))
                // Returns a value to the caller
                return new Result.Error<>("Missing registries in transcoder");
            // Calls a method
            final var registry = registrySelector.select(context.registries());

            //noinspection unchecked
            // Calls a method
            final StructCodec<T> innerCodec = (StructCodec<T>) valueToCodec.apply(value);
            // Calls a method
            final RegistryKey<StructCodec<? extends T>> type = registry.getKey(innerCodec);
            // Branch: checks a condition
            if (type == null) return new Result.Error<>("Unregistered serializer for: " + value);
            // Branch: checks a condition
            if (context.forClient() && registry.getPack(type) != DataPack.MINECRAFT_CORE)
                // Returns a value to the caller
                return new Result.Ok<>(null);

            // Calls a method
            map.put(key, coder.createString(type.key().asString()));
            // Returns a value to the caller
            return innerCodec.encodeToMap(coder, value, map);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class RecursiveImpl<T> implements Codec<T> {
        // Code statement
        final Codec<T> delegate;

        // Start of a method/block
        public RecursiveImpl(Function<Codec<T>, Codec<T>> self) {
            // Calls a method
            Objects.requireNonNull(self, "self");
            // Access to the current/parent object
            this.delegate = Objects.requireNonNull(self.apply(this), "delegate");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Returns a value to the caller
            return delegate.decode(coder, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Returns a value to the caller
            return delegate.encode(coder, value);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class ForwardRefImpl<T> implements Codec<T> {
        // Code statement
        private final Supplier<Codec<T>> delegateFunc;
        // Code statement
        private @Nullable Codec<T> delegate;

        // Start of a method/block
        ForwardRefImpl(Supplier<Codec<T>> delegateFunc) {
            // Access to the current/parent object
            this.delegateFunc = Objects.requireNonNull(delegateFunc, "delegateFunc");
        // End of a block/expression
        }

        // Racing should produce the same result (bogon data race, excluding identity)
        // Start of a method/block
        private Codec<T> delegate() {
            // Assigns a value
            Codec<T> delegate = this.delegate;
            // Branch: checks a condition
            if (delegate == null)
                // Calls a method
                delegate = this.delegate = Objects.requireNonNull(delegateFunc.get(), "delegate");
            // Returns a value to the caller
            return delegate;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Returns a value to the caller
            return delegate().decode(coder, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Returns a value to the caller
            return delegate().encode(coder, value);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OrElseImpl<T>(Codec<T> primary, Codec<T> secondary) implements Codec<T> {
        // Start of a method/block
        OrElseImpl {
            // Calls a method
            Objects.requireNonNull(primary, "primary");
            // Calls a method
            Objects.requireNonNull(secondary, "secondary");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<T> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<T> primaryResult = primary.decode(coder, value);
            // Branch: checks a condition
            if (primaryResult instanceof Result.Ok<T> primaryOk)
                // Returns a value to the caller
                return primaryOk;

            // Primary did not work, try secondary
            // Calls a method
            final Result<T> secondaryResult = secondary.decode(coder, value);
            // Branch: checks a condition
            if (secondaryResult instanceof Result.Ok<T> secondaryOk)
                // Returns a value to the caller
                return secondaryOk;

            // Secondary did not work either, return error from primary.
            // Returns a value to the caller
            return primaryResult;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable T value) {
            // Calls a method
            final Result<D> primaryResult = primary.encode(coder, value);
            // Branch: checks a condition
            if (primaryResult instanceof Result.Ok<D> primaryOk)
                // Returns a value to the caller
                return primaryOk;

            // Primary did not work, try secondary
            // Calls a method
            final Result<D> secondaryResult = secondary.encode(coder, value);
            // Branch: checks a condition
            if (secondaryResult instanceof Result.Ok<D> secondaryOk)
                // Returns a value to the caller
                return secondaryOk;

            // Secondary did not work either, return error from primary.
            // Returns a value to the caller
            return primaryResult;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BlockPositionImpl() implements Codec<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Point> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<int[]> intArrayResult = coder.getIntArray(value);
            // Branch: checks a condition
            if (!(intArrayResult instanceof Result.Ok(int[] intArray)))
                // Returns a value to the caller
                return intArrayResult.cast();
            // Branch: checks a condition
            if (intArray.length != 3)
                // Returns a value to the caller
                return new Result.Error<>("Invalid length for Point, expected 3 but got " + intArray.length);
            // Returns a value to the caller
            return new Result.Ok<>(new Vec(intArray[0], intArray[1], intArray[2]));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Point value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return new Result.Ok<>(coder.createIntArray(new int[]{
                    // Code statement
                    (int) value.x(),
                    // Code statement
                    (int) value.y(),
                    // Code statement
                    (int) value.z()
            // Code statement
            }));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EitherImpl<L, R>(Codec<L> leftCodec, Codec<R> rightCodec) implements Codec<Either<L, R>> {
        // Start of a method/block
        EitherImpl {
            // Calls a method
            Objects.requireNonNull(leftCodec, "leftCodec");
            // Calls a method
            Objects.requireNonNull(rightCodec, "rightCodec");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Either<L, R>> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<L> leftResult = leftCodec.decode(coder, value);
            // Branch: checks a condition
            if (leftResult instanceof Result.Ok(L leftValue))
                // Returns a value to the caller
                return new Result.Ok<>(Either.left(leftValue));
            // Calls a method
            final Result<R> rightResult = rightCodec.decode(coder, value);
            // Branch: checks a condition
            if (rightResult instanceof Result.Ok(R rightValue))
                // Returns a value to the caller
                return new Result.Ok<>(Either.right(rightValue));
            // Returns a value to the caller
            return new Result.Error<>("Failed to decode Either: " + leftResult + ", " + rightResult);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Either<L, R> value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Returns a value to the caller
            return switch (value) {
                // Multiple branching (switch/case)
                case Either.Left(L leftValue) -> leftCodec.encode(coder, leftValue);
                // Multiple branching (switch/case)
                case Either.Right(R rightValue) -> rightCodec.encode(coder, rightValue);
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EitherStructImpl<L, R>(
            // Code statement
            StructCodec<L> leftCodec,
            // Code statement
            StructCodec<R> rightCodec
    // Start of a method/block
    ) implements StructCodec<Either<L, R>> {
        // Start of a method/block
        public EitherStructImpl {
            // Calls a method
            Objects.requireNonNull(leftCodec, "leftCodec");
            // Calls a method
            Objects.requireNonNull(rightCodec, "rightCodec");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Either<L, R>> decodeFromMap(Transcoder<D> coder, MapLike<D> map) {
            // Calls a method
            final Result<L> leftResult = leftCodec.decodeFromMap(coder, map);
            // Branch: checks a condition
            if (leftResult instanceof Result.Ok(L leftValue))
                // Returns a value to the caller
                return new Result.Ok<>(Either.left(leftValue));
            // Calls a method
            final Result<R> rightResult = rightCodec.decodeFromMap(coder, map);
            // Branch: checks a condition
            if (rightResult instanceof Result.Ok(R rightValue))
                // Returns a value to the caller
                return new Result.Ok<>(Either.right(rightValue));
            // Returns a value to the caller
            return new Result.Error<>("Failed to decode Either: " + leftResult + ", " + rightResult);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, Either<L, R> value, MapBuilder<D> map) {
            // Returns a value to the caller
            return switch (value) {
                // Multiple branching (switch/case)
                case Either.Left(L leftValue) -> leftCodec.encodeToMap(coder, leftValue, map);
                // Multiple branching (switch/case)
                case Either.Right(R rightValue) -> rightCodec.encodeToMap(coder, rightValue, map);
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Vector3DImpl() implements Codec<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<Point> decode(Transcoder<D> coder, D value) {
            // Calls a method
            final Result<List<D>> listResult = coder.getList(value);
            // Branch: checks a condition
            if (!(listResult instanceof Result.Ok(List<D> list)))
                // Returns a value to the caller
                return listResult.cast();
            // Branch: checks a condition
            if (list.size() != 3)
                // Returns a value to the caller
                return new Result.Error<>("Invalid length for Vector, expected 3 but got " + list.size());
            // Calls a method
            final Result<Double> xResult = coder.getDouble(list.get(0));
            // Branch: checks a condition
            if (!(xResult instanceof Result.Ok(Double x)))
                // Returns a value to the caller
                return xResult.cast();
            // Calls a method
            final Result<Double> yResult = coder.getDouble(list.get(1));
            // Branch: checks a condition
            if (!(yResult instanceof Result.Ok(Double y)))
                // Returns a value to the caller
                return yResult.cast();
            // Calls a method
            final Result<Double> zResult = coder.getDouble(list.get(2));
            // Branch: checks a condition
            if (!(zResult instanceof Result.Ok(Double z)))
                // Returns a value to the caller
                return zResult.cast();
            // Returns a value to the caller
            return new Result.Ok<>(new Vec(x, y, z));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encode(Transcoder<D> coder, @Nullable Point value) {
            // Branch: checks a condition
            if (value == null) return new Result.Error<>("null");
            // Calls a method
            final ListBuilder<D> list = coder.createList(3);
            // Calls a method
            list.add(coder.createDouble(value.x()));
            // Calls a method
            list.add(coder.createDouble(value.y()));
            // Calls a method
            list.add(coder.createDouble(value.z()));
            // Returns a value to the caller
            return new Result.Ok<>(list.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record CompoundBinaryTagImpl() implements StructCodec<CompoundBinaryTag> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<CompoundBinaryTag> decodeFromMap(Transcoder<D> coder, Transcoder.MapLike<D> map) {
            // Calls a method
            final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
            // Loop: repeats a block
            for (String key : map.keys()) {
                // Assigns a value
                final Result<BinaryTag> tagResult = map.getValue(key)
                        // Calls a method
                        .map(nbt -> RawValue.of(coder, nbt).convertTo(Transcoder.NBT));
                // Branch: checks a condition
                if (!(tagResult instanceof Result.Ok(BinaryTag tag)))
                    // Returns a value to the caller
                    return tagResult.mapError(e -> key + ": " + e).cast();
                // Calls a method
                builder.put(key, tag);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(builder.build());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public <D> Result<D> encodeToMap(Transcoder<D> coder, CompoundBinaryTag value, Transcoder.MapBuilder<D> map) {
            // Loop: repeats a block
            for (var entry : value) {
                // Calls a method
                final Result<D> entryValue = RawValue.of(Transcoder.NBT, entry.getValue()).convertTo(coder);
                // Branch: checks a condition
                if (!(entryValue instanceof Result.Ok(D okValue)))
                    // Returns a value to the caller
                    return entryValue.mapError(e -> entry.getKey() + ": " + e);
                // Calls a method
                map.put(entry.getKey(), okValue);
            // End of a block/expression
            }
            // Returns a value to the caller
            return new Result.Ok<>(map.build());
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
