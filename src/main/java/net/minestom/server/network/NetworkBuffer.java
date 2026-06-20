// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import io.netty.buffer.ByteBuf;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.entity.EntityPose;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.utils.Direction;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.Unit;
// Import of a required class
import net.minestom.server.utils.crypto.KeyUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import javax.crypto.Cipher;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.lang.foreign.MemorySegment;
// Import of a required class
import java.lang.foreign.ValueLayout;
// Import of a required class
import java.security.PublicKey;
// Import of a required class
import java.time.Instant;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

// Type declaration (class/interface/enum/record)
public sealed interface NetworkBuffer permits NetworkBufferImpl {
    // Calls a method
    Type<Unit> UNIT = new NetworkBufferTypeImpl.UnitType();
    // Calls a method
    Type<Boolean> BOOLEAN = new NetworkBufferTypeImpl.BooleanType();
    // Calls a method
    Type<Byte> BYTE = new NetworkBufferTypeImpl.ByteType();
    // Calls a method
    Type<Short> UNSIGNED_BYTE = new NetworkBufferTypeImpl.UnsignedByteType();
    // Calls a method
    Type<Short> SHORT = new NetworkBufferTypeImpl.ShortType();
    // Calls a method
    Type<Integer> UNSIGNED_SHORT = new NetworkBufferTypeImpl.UnsignedShortType();
    // Calls a method
    Type<Integer> INT = new NetworkBufferTypeImpl.IntType();
    // Calls a method
    Type<Long> UNSIGNED_INT = new NetworkBufferTypeImpl.UnsignedIntType();
    // Calls a method
    Type<Long> LONG = new NetworkBufferTypeImpl.LongType();
    // Calls a method
    Type<Float> FLOAT = new NetworkBufferTypeImpl.FloatType();
    // Calls a method
    Type<Double> DOUBLE = new NetworkBufferTypeImpl.DoubleType();
    // Calls a method
    Type<Integer> VAR_INT = new NetworkBufferTypeImpl.VarIntType();
    // Calls a method
    Type<@Nullable Integer> OPTIONAL_VAR_INT = new NetworkBufferTypeImpl.OptionalVarIntType();
    // Calls a method
    Type<Integer> VAR_INT_3 = new NetworkBufferTypeImpl.VarInt3Type();
    // Calls a method
    Type<Long> VAR_LONG = new NetworkBufferTypeImpl.VarLongType();
    // Calls a method
    Type<byte[]> RAW_BYTES = new NetworkBufferTypeImpl.RawBytesType(-1);
    // Calls a method
    Type<String> STRING = new NetworkBufferTypeImpl.StringType();
    // Calls a method
    Type<Key> KEY = STRING.transform(Key::key, Key::asString);
    // Calls a method
    Type<String> STRING_TERMINATED = new NetworkBufferTypeImpl.StringTerminatedType();
    // Calls a method
    Type<String> STRING_IO_UTF8 = new NetworkBufferTypeImpl.IOUTF8StringType();
    // Calls a method
    Type<BinaryTag> NBT = new NetworkBufferTypeImpl.NbtType();
    // Annotation for the following element
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Calls a method
    Type<CompoundBinaryTag> NBT_COMPOUND = (Type) new NetworkBufferTypeImpl.NbtType();
    // Calls a method
    Type<Point> BLOCK_POSITION = new NetworkBufferTypeImpl.BlockPositionType();
    // Calls a method
    Type<Component> COMPONENT = new ComponentNetworkBufferTypeImpl();
    // Calls a method
    Type<Component> JSON_COMPONENT = new NetworkBufferTypeImpl.JsonComponentType();
    // Calls a method
    Type<java.util.UUID> UUID = new NetworkBufferTypeImpl.UUIDType();
    // Calls a method
    Type<Pos> POS = new NetworkBufferTypeImpl.PosType();

    // Calls a method
    Type<byte[]> BYTE_ARRAY = new NetworkBufferTypeImpl.ByteArrayType();
    // Calls a method
    Type<long[]> LONG_ARRAY = new NetworkBufferTypeImpl.LongArrayType();
    // Calls a method
    Type<int[]> VAR_INT_ARRAY = new NetworkBufferTypeImpl.VarIntArrayType();
    // Calls a method
    Type<long[]> VAR_LONG_ARRAY = new NetworkBufferTypeImpl.VarLongArrayType();

    // Calls a method
    Type<BitSet> BITSET = LONG_ARRAY.transform(BitSet::valueOf, BitSet::toLongArray);
    // Calls a method
    Type<Instant> INSTANT_MS = LONG.transform(Instant::ofEpochMilli, Instant::toEpochMilli);
    // Calls a method
    Type<PublicKey> PUBLIC_KEY = BYTE_ARRAY.transform(KeyUtils::publicRSAKeyFrom, PublicKey::getEncoded);

    // Calls a method
    Type<Point> VECTOR3 = new NetworkBufferTypeImpl.Vector3Type();
    // Calls a method
    Type<Point> VECTOR3D = new NetworkBufferTypeImpl.Vector3DType();
    // Calls a method
    Type<Point> VECTOR3I = new NetworkBufferTypeImpl.Vector3IType();
    // Calls a method
    Type<Point> VECTOR3B = new NetworkBufferTypeImpl.Vector3BType();
    // Calls a method
    Type<Vec> LP_VECTOR3 = new NetworkBufferTypeImpl.LpVector3Type();
    // Calls a method
    Type<float[]> QUATERNION = new NetworkBufferTypeImpl.QuaternionType();

    // Calls a method
    Type<@Nullable Component> OPT_CHAT = COMPONENT.optional();
    // Calls a method
    Type<@Nullable Point> OPT_BLOCK_POSITION = BLOCK_POSITION.optional();

    // Calls a method
    Type<Direction> DIRECTION = Enum(Direction.class);
    // Calls a method
    Type<EntityPose> POSE = Enum(EntityPose.class);

    // Combinators

    // Start of a method/block
    static <E extends Enum<E>> Type<E> Enum(Class<E> enumClass) {
        // Calls a method
        final E[] values = enumClass.getEnumConstants();
        // Returns a value to the caller
        return VAR_INT.transform(i -> values[i], Enum::ordinal);
    // End of a block/expression
    }

    // Start of a method/block
    static <E extends Enum<E>> Type<EnumSet<E>> EnumSet(Class<E> enumClass) {
        // Calls a method
        final E[] values = enumClass.getEnumConstants();
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.EnumSetType<>(enumClass, values);
    // End of a block/expression
    }

    // Start of a method/block
    static Type<BitSet> FixedBitSet(int length) {
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.FixedBitSetType(length);
    // End of a block/expression
    }

    // Start of a method/block
    static Type<byte[]> FixedRawBytes(int length) {
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.RawBytesType(length);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Type<T> Lazy(Supplier<Type<T>> supplier) {
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.LazyType<>(supplier);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Type<T> TypedNBT(Codec<T> serializer) {
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.TypedNbtType<>(serializer);
    // End of a block/expression
    }

    // Start of a method/block
    static <L, R> Type<Either<L, R>> Either(Type<L> left, Type<R> right) {
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.EitherType<>(left, right);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> Type<T> Recursive(Function<Type<T>, Type<T>> func) {
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.RecursiveType<>(func).delegate;
    // End of a block/expression
    }

    // Code statement
    static <T, D> Type<T> Tagged(Type<D> discriminator, Function<? super T, ? extends D> discriminatorFromValue,
                                 // Start of a method/block
                                 Map<? super D, ? extends Type<? extends T>> serializerMap, @Nullable Type<? extends T> fallback) {
        // Map.copyOf does some trickery with the generic bounds here.
        // Returns a value to the caller
        return new NetworkBufferTypeImpl.TaggedType<>(discriminator, discriminatorFromValue, Map.copyOf(serializerMap), fallback);
    // End of a block/expression
    }

    // Code statement
    static <T, D> Type<T> Tagged(Type<D> discriminator, Function<? super T, ? extends D> discriminatorFromValue,
                                 // Start of a method/block
                                 Map<? super D, ? extends Type<? extends T>> serializerMap) {
        // Returns a value to the caller
        return Tagged(discriminator, discriminatorFromValue, serializerMap, null);
    // End of a block/expression
    }

    // Calls a method
    <T> void write(Type<T> type, @UnknownNullability T value) throws IndexOutOfBoundsException;

    // Calls a method
    <T> @UnknownNullability T read(Type<T> type) throws IndexOutOfBoundsException;

    // Calls a method
    <T> void writeAt(long index, Type<T> type, @UnknownNullability T value) throws IndexOutOfBoundsException;

    // Calls a method
    <T> @UnknownNullability T readAt(long index, Type<T> type) throws IndexOutOfBoundsException;

    /**
     * @deprecated Use {@link #copyTo(long, byte[], int, int)} instead, as longs can easily overflow arrays.
     */
    // Annotation for the following element
    @Deprecated(forRemoval = true)
    // Calls a method
    void copyTo(long srcOffset, byte[] dest, long destOffset, long length);

    // Calls a method
    void copyTo(long srcOffset, byte[] dest, int destOffset, int length);

    // Calls a method
    void copyTo(long srcOffset, MemorySegment dest, long destOffset, long length);

    // Calls a method
    byte[] extractBytes(Consumer<NetworkBuffer> extractor);

    // Calls a method
    NetworkBuffer clear();

    // Calls a method
    long writeIndex();
    // Calls a method
    long readIndex();

    // Calls a method
    NetworkBuffer writeIndex(long writeIndex);

    // Calls a method
    NetworkBuffer readIndex(long readIndex);

    // Calls a method
    NetworkBuffer index(long readIndex, long writeIndex);

    // Calls a method
    long advanceWrite(long length);

    // Calls a method
    long advanceRead(long length);

    // Calls a method
    long readableBytes();

    // Calls a method
    long writableBytes();

    // Calls a method
    long capacity();

    // Calls a method
    void readOnly();

    // Calls a method
    boolean isReadOnly();

    // Calls a method
    void resize(long newSize);

    // Calls a method
    void ensureWritable(long length);

    // Calls a method
    void compact();

    // Calls a method
    NetworkBuffer copy(long index, long length, long readIndex, long writeIndex);

    // Start of a method/block
    default NetworkBuffer copy(long index, long length) {
        // Returns a value to the caller
        return copy(index, length, readIndex(), writeIndex());
    // End of a block/expression
    }


    // Calls a method
    int readFromByteBuf(ByteBuf in);


    // Calls a method
    boolean writeToByteBuf(ByteBuf out);

    // Calls a method
    void cipher(Cipher cipher, long start, long length);

    // Calls a method
    long compress(long start, long length, NetworkBuffer output) throws IOException;

    // Calls a method
    long decompress(long start, long length, NetworkBuffer output) throws IOException;

    // Annotation for the following element
    @Nullable Registries registries();

    // Calls a method
    void registries(@Nullable Registries registries);

    // Type declaration (class/interface/enum/record)
    interface Type<T extends @UnknownNullability Object> {
        // Calls a method
        void write(NetworkBuffer buffer, T value);

        // Calls a method
        T read(NetworkBuffer buffer);

        // Start of a method/block
        default long sizeOf(T value, @Nullable Registries registries) {
            // Returns a value to the caller
            return NetworkBufferTypeImpl.sizeOf(this, value, registries);
        // End of a block/expression
        }

        // Start of a method/block
        default long sizeOf(T value) {
            // Returns a value to the caller
            return sizeOf(value, null);
        // End of a block/expression
        }

        // Start of a method/block
        default <S> Type<S> transform(Function<T, S> to, Function<S, T> from) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.TransformType<>(this, to, from);
        // End of a block/expression
        }

        // Start of a method/block
        default <V> Type<Map<T, V>> mapValue(Type<V> valueType, int maxSize) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.MapType<>(this, valueType, maxSize);
        // End of a block/expression
        }

        // Start of a method/block
        default <V> Type<Map<T, V>> mapValue(Type<V> valueType) {
            // Returns a value to the caller
            return mapValue(valueType, Integer.MAX_VALUE);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<List<T>> list(int maxSize) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.ListType<>(this, maxSize);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<List<T>> list() {
            // Returns a value to the caller
            return list(Integer.MAX_VALUE);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<Set<T>> set(int maxSize) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.SetType<>(this, maxSize);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<Set<T>> set() {
            // Returns a value to the caller
            return set(Integer.MAX_VALUE);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<@Nullable T> optional() {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.OptionalType<>(this);
        // End of a block/expression
        }

        // Code statement
        default <R, TR extends R> Type<R> unionType(
                // Code statement
                Function<T, NetworkBuffer.Type<TR>> serializers,
                // Start of a method/block
                Function<R, ? extends T> keyFunc) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.UnionType<>(this, keyFunc, serializers);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<T> lengthPrefixed(int maxLength) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.LengthPrefixedType<>(this, maxLength);
        // End of a block/expression
        }

        // Start of a method/block
        default Type<T> maxLength(long maxLength) {
            // Returns a value to the caller
            return new NetworkBufferTypeImpl.MaxLength<>(this, maxLength);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static Builder builder(long size) {
        // Returns a value to the caller
        return new NetworkBufferImpl.Builder(size);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer staticBuffer(long size, Registries registries) {
        // Returns a value to the caller
        return builder(size).registry(registries).build();
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer staticBuffer(long size) {
        // Returns a value to the caller
        return staticBuffer(size, null);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer resizableBuffer(long initialSize, Registries registries) {
        // Returns a value to the caller
        return builder(initialSize)
                // Code statement
                .autoResize(AutoResize.DOUBLE)
                // Code statement
                .registry(registries)
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer resizableBuffer(int initialSize) {
        // Returns a value to the caller
        return resizableBuffer(initialSize, null);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer resizableBuffer(Registries registries) {
        // Returns a value to the caller
        return resizableBuffer(256, registries);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer resizableBuffer() {
        // Returns a value to the caller
        return resizableBuffer((Registries) null);
    // End of a block/expression
    }

    // todo: maybe remove segments for netty impl
    // Start of a method/block
    static NetworkBuffer wrap(MemorySegment segment, long readIndex, long writeIndex, @Nullable Registries registries) {
        // Returns a value to the caller
        return wrap(segment.toArray(ValueLayout.JAVA_BYTE), Math.toIntExact(readIndex), Math.toIntExact(writeIndex), registries);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer wrap(MemorySegment segment, long readIndex, long writeIndex) {
        // Returns a value to the caller
        return wrap(segment, readIndex, writeIndex, null);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer wrap(byte[] bytes, int readIndex, int writeIndex, @Nullable Registries registries) {
        /* TODO(next) remove me for zero copy. The old behavior didnt actually modify the underlying array.
            quite unfortunate and will require until waiting for the next release to change this behavior. */
        // Calls a method
        bytes = bytes.clone();
        // Returns a value to the caller
        return NetworkBufferImpl.wrap(bytes, readIndex, writeIndex, registries);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer wrap(byte[] bytes, int readIndex, int writeIndex) {
        // Returns a value to the caller
        return wrap(bytes, readIndex, writeIndex, null);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer fromByteBuf(ByteBuf buf, @Nullable Registries registries) {
        // Returns a value to the caller
        return NetworkBufferImpl.fromByteBuf(buf, registries);
    // End of a block/expression
    }

    // Start of a method/block
    static NetworkBuffer fromByteBuf(ByteBuf buf) {
        // Returns a value to the caller
        return fromByteBuf(buf, null);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    sealed interface Builder permits NetworkBufferImpl.Builder {
        // Calls a method
        Builder autoResize(@Nullable AutoResize autoResize);

        // Calls a method
        Builder registry(@Nullable Registries registries);

        // Calls a method
        NetworkBuffer build();
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface AutoResize {
        // Calls a method
        AutoResize DOUBLE = (capacity, targetSize) -> Math.max(capacity * 2, targetSize);

        // Calls a method
        long resize(long capacity, long targetSize);
    // End of a block/expression
    }

    // Start of a method/block
    static byte[] makeArray(Consumer<NetworkBuffer> writing, @Nullable Registries registries) {
        // Calls a method
        NetworkBuffer buffer = resizableBuffer(256, registries);
        // Calls a method
        writing.accept(buffer);
        // Returns a value to the caller
        return buffer.read(RAW_BYTES);
    // End of a block/expression
    }

    // Start of a method/block
    static byte[] makeArray(Consumer<NetworkBuffer> writing) {
        // Returns a value to the caller
        return makeArray(writing, null);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> byte[] makeArray(Type<T> type, T value, @Nullable Registries registries) {
        // Returns a value to the caller
        return makeArray(buffer -> buffer.write(type, value), registries);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> byte[] makeArray(Type<T> type, T value) {
        // Returns a value to the caller
        return makeArray(type, value, null);
    // End of a block/expression
    }

    // Code statement
    static void copy(NetworkBuffer srcBuffer, long srcOffset,
                     // Start of a method/block
                     NetworkBuffer dstBuffer, long dstOffset, long length) {
        // Calls a method
        NetworkBufferImpl.copy(srcBuffer, srcOffset, dstBuffer, dstOffset, length);
    // End of a block/expression
    }

    // Start of a method/block
    static boolean equals(NetworkBuffer buffer1, NetworkBuffer buffer2) {
        // Returns a value to the caller
        return NetworkBufferImpl.equals(buffer1, buffer2);
    // End of a block/expression
    }
// End of a block/expression
}