// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import io.netty.buffer.ByteBuf;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityPose;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.utils.Direction;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;
// Import d'une classe nécessaire
import net.minestom.server.utils.crypto.KeyUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import javax.crypto.Cipher;
// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.lang.foreign.MemorySegment;
// Import d'une classe nécessaire
import java.lang.foreign.ValueLayout;
// Import d'une classe nécessaire
import java.security.PublicKey;
// Import d'une classe nécessaire
import java.time.Instant;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Déclaration de type (classe/interface/enum/record)
public sealed interface NetworkBuffer permits NetworkBufferImpl {
    // Appelle une méthode
    Type<Unit> UNIT = new NetworkBufferTypeImpl.UnitType();
    // Appelle une méthode
    Type<Boolean> BOOLEAN = new NetworkBufferTypeImpl.BooleanType();
    // Appelle une méthode
    Type<Byte> BYTE = new NetworkBufferTypeImpl.ByteType();
    // Appelle une méthode
    Type<Short> UNSIGNED_BYTE = new NetworkBufferTypeImpl.UnsignedByteType();
    // Appelle une méthode
    Type<Short> SHORT = new NetworkBufferTypeImpl.ShortType();
    // Appelle une méthode
    Type<Integer> UNSIGNED_SHORT = new NetworkBufferTypeImpl.UnsignedShortType();
    // Appelle une méthode
    Type<Integer> INT = new NetworkBufferTypeImpl.IntType();
    // Appelle une méthode
    Type<Long> UNSIGNED_INT = new NetworkBufferTypeImpl.UnsignedIntType();
    // Appelle une méthode
    Type<Long> LONG = new NetworkBufferTypeImpl.LongType();
    // Appelle une méthode
    Type<Float> FLOAT = new NetworkBufferTypeImpl.FloatType();
    // Appelle une méthode
    Type<Double> DOUBLE = new NetworkBufferTypeImpl.DoubleType();
    // Appelle une méthode
    Type<Integer> VAR_INT = new NetworkBufferTypeImpl.VarIntType();
    // Appelle une méthode
    Type<@Nullable Integer> OPTIONAL_VAR_INT = new NetworkBufferTypeImpl.OptionalVarIntType();
    // Appelle une méthode
    Type<Integer> VAR_INT_3 = new NetworkBufferTypeImpl.VarInt3Type();
    // Appelle une méthode
    Type<Long> VAR_LONG = new NetworkBufferTypeImpl.VarLongType();
    // Appelle une méthode
    Type<byte[]> RAW_BYTES = new NetworkBufferTypeImpl.RawBytesType(-1);
    // Appelle une méthode
    Type<String> STRING = new NetworkBufferTypeImpl.StringType();
    // Appelle une méthode
    Type<Key> KEY = STRING.transform(Key::key, Key::asString);
    // Appelle une méthode
    Type<String> STRING_TERMINATED = new NetworkBufferTypeImpl.StringTerminatedType();
    // Appelle une méthode
    Type<String> STRING_IO_UTF8 = new NetworkBufferTypeImpl.IOUTF8StringType();
    // Appelle une méthode
    Type<BinaryTag> NBT = new NetworkBufferTypeImpl.NbtType();
    // Annotation pour l'élément suivant
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Appelle une méthode
    Type<CompoundBinaryTag> NBT_COMPOUND = (Type) new NetworkBufferTypeImpl.NbtType();
    // Appelle une méthode
    Type<Point> BLOCK_POSITION = new NetworkBufferTypeImpl.BlockPositionType();
    // Appelle une méthode
    Type<Component> COMPONENT = new ComponentNetworkBufferTypeImpl();
    // Appelle une méthode
    Type<Component> JSON_COMPONENT = new NetworkBufferTypeImpl.JsonComponentType();
    // Appelle une méthode
    Type<java.util.UUID> UUID = new NetworkBufferTypeImpl.UUIDType();
    // Appelle une méthode
    Type<Pos> POS = new NetworkBufferTypeImpl.PosType();

    // Appelle une méthode
    Type<byte[]> BYTE_ARRAY = new NetworkBufferTypeImpl.ByteArrayType();
    // Appelle une méthode
    Type<long[]> LONG_ARRAY = new NetworkBufferTypeImpl.LongArrayType();
    // Appelle une méthode
    Type<int[]> VAR_INT_ARRAY = new NetworkBufferTypeImpl.VarIntArrayType();
    // Appelle une méthode
    Type<long[]> VAR_LONG_ARRAY = new NetworkBufferTypeImpl.VarLongArrayType();

    // Appelle une méthode
    Type<BitSet> BITSET = LONG_ARRAY.transform(BitSet::valueOf, BitSet::toLongArray);
    // Appelle une méthode
    Type<Instant> INSTANT_MS = LONG.transform(Instant::ofEpochMilli, Instant::toEpochMilli);
    // Appelle une méthode
    Type<PublicKey> PUBLIC_KEY = BYTE_ARRAY.transform(KeyUtils::publicRSAKeyFrom, PublicKey::getEncoded);

    // Appelle une méthode
    Type<Point> VECTOR3 = new NetworkBufferTypeImpl.Vector3Type();
    // Appelle une méthode
    Type<Point> VECTOR3D = new NetworkBufferTypeImpl.Vector3DType();
    // Appelle une méthode
    Type<Point> VECTOR3I = new NetworkBufferTypeImpl.Vector3IType();
    // Appelle une méthode
    Type<Point> VECTOR3B = new NetworkBufferTypeImpl.Vector3BType();
    // Appelle une méthode
    Type<Vec> LP_VECTOR3 = new NetworkBufferTypeImpl.LpVector3Type();
    // Appelle une méthode
    Type<float[]> QUATERNION = new NetworkBufferTypeImpl.QuaternionType();

    // Appelle une méthode
    Type<@Nullable Component> OPT_CHAT = COMPONENT.optional();
    // Appelle une méthode
    Type<@Nullable Point> OPT_BLOCK_POSITION = BLOCK_POSITION.optional();

    // Appelle une méthode
    Type<Direction> DIRECTION = Enum(Direction.class);
    // Appelle une méthode
    Type<EntityPose> POSE = Enum(EntityPose.class);

    // Combinators

    // Début d'une méthode/d'un bloc
    static <E extends Enum<E>> Type<E> Enum(Class<E> enumClass) {
        // Appelle une méthode
        final E[] values = enumClass.getEnumConstants();
        // Renvoie une valeur à l'appelant
        return VAR_INT.transform(i -> values[i], Enum::ordinal);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <E extends Enum<E>> Type<EnumSet<E>> EnumSet(Class<E> enumClass) {
        // Appelle une méthode
        final E[] values = enumClass.getEnumConstants();
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.EnumSetType<>(enumClass, values);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Type<BitSet> FixedBitSet(int length) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.FixedBitSetType(length);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Type<byte[]> FixedRawBytes(int length) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.RawBytesType(length);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Type<T> Lazy(Supplier<Type<T>> supplier) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.LazyType<>(supplier);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Type<T> TypedNBT(Codec<T> serializer) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.TypedNbtType<>(serializer);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <L, R> Type<Either<L, R>> Either(Type<L> left, Type<R> right) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.EitherType<>(left, right);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> Type<T> Recursive(Function<Type<T>, Type<T>> func) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.RecursiveType<>(func).delegate;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static <T, D> Type<T> Tagged(Type<D> discriminator, Function<? super T, ? extends D> discriminatorFromValue,
                                 // Début d'une méthode/d'un bloc
                                 Map<? super D, ? extends Type<? extends T>> serializerMap, @Nullable Type<? extends T> fallback) {
        // Map.copyOf does some trickery with the generic bounds here.
        // Renvoie une valeur à l'appelant
        return new NetworkBufferTypeImpl.TaggedType<>(discriminator, discriminatorFromValue, Map.copyOf(serializerMap), fallback);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static <T, D> Type<T> Tagged(Type<D> discriminator, Function<? super T, ? extends D> discriminatorFromValue,
                                 // Début d'une méthode/d'un bloc
                                 Map<? super D, ? extends Type<? extends T>> serializerMap) {
        // Renvoie une valeur à l'appelant
        return Tagged(discriminator, discriminatorFromValue, serializerMap, null);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    <T> void write(Type<T> type, @UnknownNullability T value) throws IndexOutOfBoundsException;

    // Appelle une méthode
    <T> @UnknownNullability T read(Type<T> type) throws IndexOutOfBoundsException;

    // Appelle une méthode
    <T> void writeAt(long index, Type<T> type, @UnknownNullability T value) throws IndexOutOfBoundsException;

    // Appelle une méthode
    <T> @UnknownNullability T readAt(long index, Type<T> type) throws IndexOutOfBoundsException;

    /**
     * @deprecated Use {@link #copyTo(long, byte[], int, int)} instead, as longs can easily overflow arrays.
     */
    // Annotation pour l'élément suivant
    @Deprecated(forRemoval = true)
    // Appelle une méthode
    void copyTo(long srcOffset, byte[] dest, long destOffset, long length);

    // Appelle une méthode
    void copyTo(long srcOffset, byte[] dest, int destOffset, int length);

    // Appelle une méthode
    void copyTo(long srcOffset, MemorySegment dest, long destOffset, long length);

    // Appelle une méthode
    byte[] extractBytes(Consumer<NetworkBuffer> extractor);

    // Appelle une méthode
    NetworkBuffer clear();

    // Appelle une méthode
    long writeIndex();
    // Appelle une méthode
    long readIndex();

    // Appelle une méthode
    NetworkBuffer writeIndex(long writeIndex);

    // Appelle une méthode
    NetworkBuffer readIndex(long readIndex);

    // Appelle une méthode
    NetworkBuffer index(long readIndex, long writeIndex);

    // Appelle une méthode
    long advanceWrite(long length);

    // Appelle une méthode
    long advanceRead(long length);

    // Appelle une méthode
    long readableBytes();

    // Appelle une méthode
    long writableBytes();

    // Appelle une méthode
    long capacity();

    // Appelle une méthode
    void readOnly();

    // Appelle une méthode
    boolean isReadOnly();

    // Appelle une méthode
    void resize(long newSize);

    // Appelle une méthode
    void ensureWritable(long length);

    // Appelle une méthode
    void compact();

    // Appelle une méthode
    NetworkBuffer copy(long index, long length, long readIndex, long writeIndex);

    // Début d'une méthode/d'un bloc
    default NetworkBuffer copy(long index, long length) {
        // Renvoie une valeur à l'appelant
        return copy(index, length, readIndex(), writeIndex());
    // Fin d'un bloc/d'une expression
    }


    // Appelle une méthode
    int readFromByteBuf(ByteBuf in);


    // Appelle une méthode
    boolean writeToByteBuf(ByteBuf out);

    // Appelle une méthode
    void cipher(Cipher cipher, long start, long length);

    // Appelle une méthode
    long compress(long start, long length, NetworkBuffer output) throws IOException;

    // Appelle une méthode
    long decompress(long start, long length, NetworkBuffer output) throws IOException;

    // Annotation pour l'élément suivant
    @Nullable Registries registries();

    // Appelle une méthode
    void registries(@Nullable Registries registries);

    // Déclaration de type (classe/interface/enum/record)
    interface Type<T extends @UnknownNullability Object> {
        // Appelle une méthode
        void write(NetworkBuffer buffer, T value);

        // Appelle une méthode
        T read(NetworkBuffer buffer);

        // Début d'une méthode/d'un bloc
        default long sizeOf(T value, @Nullable Registries registries) {
            // Renvoie une valeur à l'appelant
            return NetworkBufferTypeImpl.sizeOf(this, value, registries);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default long sizeOf(T value) {
            // Renvoie une valeur à l'appelant
            return sizeOf(value, null);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default <S> Type<S> transform(Function<T, S> to, Function<S, T> from) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.TransformType<>(this, to, from);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default <V> Type<Map<T, V>> mapValue(Type<V> valueType, int maxSize) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.MapType<>(this, valueType, maxSize);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default <V> Type<Map<T, V>> mapValue(Type<V> valueType) {
            // Renvoie une valeur à l'appelant
            return mapValue(valueType, Integer.MAX_VALUE);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<List<T>> list(int maxSize) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.ListType<>(this, maxSize);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<List<T>> list() {
            // Renvoie une valeur à l'appelant
            return list(Integer.MAX_VALUE);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<Set<T>> set(int maxSize) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.SetType<>(this, maxSize);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<Set<T>> set() {
            // Renvoie une valeur à l'appelant
            return set(Integer.MAX_VALUE);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<@Nullable T> optional() {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.OptionalType<>(this);
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        default <R, TR extends R> Type<R> unionType(
                // Instruction de code
                Function<T, NetworkBuffer.Type<TR>> serializers,
                // Début d'une méthode/d'un bloc
                Function<R, ? extends T> keyFunc) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.UnionType<>(this, keyFunc, serializers);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<T> lengthPrefixed(int maxLength) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.LengthPrefixedType<>(this, maxLength);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default Type<T> maxLength(long maxLength) {
            // Renvoie une valeur à l'appelant
            return new NetworkBufferTypeImpl.MaxLength<>(this, maxLength);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder(long size) {
        // Renvoie une valeur à l'appelant
        return new NetworkBufferImpl.Builder(size);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer staticBuffer(long size, Registries registries) {
        // Renvoie une valeur à l'appelant
        return builder(size).registry(registries).build();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer staticBuffer(long size) {
        // Renvoie une valeur à l'appelant
        return staticBuffer(size, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer resizableBuffer(long initialSize, Registries registries) {
        // Renvoie une valeur à l'appelant
        return builder(initialSize)
                // Instruction de code
                .autoResize(AutoResize.DOUBLE)
                // Instruction de code
                .registry(registries)
                // Appelle une méthode
                .build();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer resizableBuffer(int initialSize) {
        // Renvoie une valeur à l'appelant
        return resizableBuffer(initialSize, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer resizableBuffer(Registries registries) {
        // Renvoie une valeur à l'appelant
        return resizableBuffer(256, registries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer resizableBuffer() {
        // Renvoie une valeur à l'appelant
        return resizableBuffer((Registries) null);
    // Fin d'un bloc/d'une expression
    }

    // todo: maybe remove segments for netty impl
    // Début d'une méthode/d'un bloc
    static NetworkBuffer wrap(MemorySegment segment, long readIndex, long writeIndex, @Nullable Registries registries) {
        // Renvoie une valeur à l'appelant
        return wrap(segment.toArray(ValueLayout.JAVA_BYTE), Math.toIntExact(readIndex), Math.toIntExact(writeIndex), registries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer wrap(MemorySegment segment, long readIndex, long writeIndex) {
        // Renvoie une valeur à l'appelant
        return wrap(segment, readIndex, writeIndex, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer wrap(byte[] bytes, int readIndex, int writeIndex, @Nullable Registries registries) {
        /* TODO(next) remove me for zero copy. The old behavior didnt actually modify the underlying array.
            quite unfortunate and will require until waiting for the next release to change this behavior. */
        // Appelle une méthode
        bytes = bytes.clone();
        // Renvoie une valeur à l'appelant
        return NetworkBufferImpl.wrap(bytes, readIndex, writeIndex, registries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer wrap(byte[] bytes, int readIndex, int writeIndex) {
        // Renvoie une valeur à l'appelant
        return wrap(bytes, readIndex, writeIndex, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer fromByteBuf(ByteBuf buf, @Nullable Registries registries) {
        // Renvoie une valeur à l'appelant
        return NetworkBufferImpl.fromByteBuf(buf, registries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static NetworkBuffer fromByteBuf(ByteBuf buf) {
        // Renvoie une valeur à l'appelant
        return fromByteBuf(buf, null);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Builder permits NetworkBufferImpl.Builder {
        // Appelle une méthode
        Builder autoResize(@Nullable AutoResize autoResize);

        // Appelle une méthode
        Builder registry(@Nullable Registries registries);

        // Appelle une méthode
        NetworkBuffer build();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface AutoResize {
        // Appelle une méthode
        AutoResize DOUBLE = (capacity, targetSize) -> Math.max(capacity * 2, targetSize);

        // Appelle une méthode
        long resize(long capacity, long targetSize);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static byte[] makeArray(Consumer<NetworkBuffer> writing, @Nullable Registries registries) {
        // Appelle une méthode
        NetworkBuffer buffer = resizableBuffer(256, registries);
        // Appelle une méthode
        writing.accept(buffer);
        // Renvoie une valeur à l'appelant
        return buffer.read(RAW_BYTES);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static byte[] makeArray(Consumer<NetworkBuffer> writing) {
        // Renvoie une valeur à l'appelant
        return makeArray(writing, null);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> byte[] makeArray(Type<T> type, T value, @Nullable Registries registries) {
        // Renvoie une valeur à l'appelant
        return makeArray(buffer -> buffer.write(type, value), registries);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> byte[] makeArray(Type<T> type, T value) {
        // Renvoie une valeur à l'appelant
        return makeArray(type, value, null);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    static void copy(NetworkBuffer srcBuffer, long srcOffset,
                     // Début d'une méthode/d'un bloc
                     NetworkBuffer dstBuffer, long dstOffset, long length) {
        // Appelle une méthode
        NetworkBufferImpl.copy(srcBuffer, srcOffset, dstBuffer, dstOffset, length);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static boolean equals(NetworkBuffer buffer1, NetworkBuffer buffer2) {
        // Renvoie une valeur à l'appelant
        return NetworkBufferImpl.equals(buffer1, buffer2);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}