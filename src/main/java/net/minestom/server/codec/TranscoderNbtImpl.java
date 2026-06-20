// Déclaration du paquet de ce fichier
package net.minestom.server.codec;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
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
final class TranscoderNbtImpl implements Transcoder<BinaryTag> {
    // Appelle une méthode
    static final TranscoderNbtImpl INSTANCE = new TranscoderNbtImpl();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createNull() {
        // Renvoie une valeur à l'appelant
        return EndBinaryTag.endBinaryTag();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Boolean> getBoolean(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.byteValue() != 0)
                // Instruction de code
                : new Result.Error<>("Not a boolean: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createBoolean(boolean value) {
        // Renvoie une valeur à l'appelant
        return value ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Byte> getByte(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.byteValue())
                // Instruction de code
                : new Result.Error<>("Not a byte: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createByte(byte value) {
        // Embranchement : vérifie une condition
        if (value == 0) return ByteBinaryTag.ZERO;
        // Embranchement : vérifie une condition
        if (value == 1) return ByteBinaryTag.ONE;
        // Renvoie une valeur à l'appelant
        return ByteBinaryTag.byteBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Short> getShort(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.shortValue())
                // Instruction de code
                : new Result.Error<>("Not a short: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createShort(short value) {
        // Renvoie une valeur à l'appelant
        return ShortBinaryTag.shortBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Integer> getInt(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.intValue())
                // Instruction de code
                : new Result.Error<>("Not an int: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createInt(int value) {
        // Renvoie une valeur à l'appelant
        return IntBinaryTag.intBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Long> getLong(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.longValue())
                // Instruction de code
                : new Result.Error<>("Not a long: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createLong(long value) {
        // Renvoie une valeur à l'appelant
        return LongBinaryTag.longBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Float> getFloat(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.floatValue())
                // Instruction de code
                : new Result.Error<>("Not a float: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createFloat(float value) {
        // Renvoie une valeur à l'appelant
        return FloatBinaryTag.floatBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<Double> getDouble(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof NumberBinaryTag number
                // Instruction de code
                ? new Result.Ok<>(number.doubleValue())
                // Instruction de code
                : new Result.Error<>("Not a double: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createDouble(double value) {
        // Renvoie une valeur à l'appelant
        return DoubleBinaryTag.doubleBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<String> getString(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof StringBinaryTag string
                // Instruction de code
                ? new Result.Ok<>(string.value())
                // Instruction de code
                : new Result.Error<>("Not a string: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createString(String value) {
        // Renvoie une valeur à l'appelant
        return StringBinaryTag.stringBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<List<BinaryTag>> getList(BinaryTag value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof ListBinaryTag listTagWrapped))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a list: " + value);
        // Appelle une méthode
        final ListBinaryTag listTag = listTagWrapped.unwrapHeterogeneity();
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(new AbstractList<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public BinaryTag get(int index) {
                // Renvoie une valeur à l'appelant
                return listTag.get(index);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int size() {
                // Renvoie une valeur à l'appelant
                return listTag.size();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag emptyList() {
        // Renvoie une valeur à l'appelant
        return ListBinaryTag.empty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ListBuilder<BinaryTag> createList(int expectedSize) {
        // Appelle une méthode
        final ListBinaryTag.Builder<BinaryTag> elements = ListBinaryTag.heterogeneousListBinaryTag();
        // Renvoie une valeur à l'appelant
        return new ListBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public ListBuilder<BinaryTag> add(BinaryTag value) {
                // Appelle une méthode
                elements.add(value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public BinaryTag build() {
                // Renvoie une valeur à l'appelant
                return elements.build();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<MapLike<BinaryTag>> getMap(BinaryTag value) {
        // Embranchement : vérifie une condition
        if (!(value instanceof CompoundBinaryTag compoundTag))
            // Renvoie une valeur à l'appelant
            return new Result.Error<>("Not a compound: " + value);
        // Renvoie une valeur à l'appelant
        return new Result.Ok<>(new MapLike<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Collection<String> keys() {
                // Renvoie une valeur à l'appelant
                return compoundTag.keySet();
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public boolean hasValue(String key) {
                // Renvoie une valeur à l'appelant
                return compoundTag.get(key) != null;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Result<BinaryTag> getValue(String key) {
                // Appelle une méthode
                final BinaryTag tag = compoundTag.get(key);
                // Embranchement : vérifie une condition
                if (tag == null) return new Result.Error<>("No such key: " + key);
                // Renvoie une valeur à l'appelant
                return new Result.Ok<>(tag);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public int size() {
                // Renvoie une valeur à l'appelant
                return compoundTag.size();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag emptyMap() {
        // Renvoie une valeur à l'appelant
        return CompoundBinaryTag.empty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public MapBuilder<BinaryTag> createMap() {
        // Appelle une méthode
        final CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        // Renvoie une valeur à l'appelant
        return new MapBuilder<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<BinaryTag> put(BinaryTag key, BinaryTag value) {
                // Embranchement : vérifie une condition
                if (!(value instanceof EndBinaryTag) && key instanceof StringBinaryTag string)
                    // Appelle une méthode
                    builder.put(string.value(), value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public MapBuilder<BinaryTag> put(String key, BinaryTag value) {
                // Embranchement : vérifie une condition
                if (!(value instanceof EndBinaryTag))
                    // Appelle une méthode
                    builder.put(key, value);
                // Renvoie une valeur à l'appelant
                return this;
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public BinaryTag build() {
                // Renvoie une valeur à l'appelant
                return builder.build();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<byte[]> getByteArray(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof ByteArrayBinaryTag byteArray
                // Instruction de code
                ? new Result.Ok<>(byteArray.value())
                // Instruction de code
                : new Result.Error<>("Not a byte array: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createByteArray(byte[] value) {
        // Renvoie une valeur à l'appelant
        return ByteArrayBinaryTag.byteArrayBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<int[]> getIntArray(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof IntArrayBinaryTag intArray
                // Instruction de code
                ? new Result.Ok<>(intArray.value())
                // Instruction de code
                : new Result.Error<>("Not an int array: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createIntArray(int[] value) {
        // Renvoie une valeur à l'appelant
        return IntArrayBinaryTag.intArrayBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Result<long[]> getLongArray(BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return value instanceof LongArrayBinaryTag longArray
                // Instruction de code
                ? new Result.Ok<>(longArray.value())
                // Instruction de code
                : new Result.Error<>("Not a long array: " + value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag createLongArray(long[] value) {
        // Renvoie une valeur à l'appelant
        return LongArrayBinaryTag.longArrayBinaryTag(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <O> Result<O> convertTo(Transcoder<O> coder, BinaryTag value) {
        // Renvoie une valeur à l'appelant
        return switch (value) {
            // Embranchement multiple (switch/case)
            case EndBinaryTag ignored -> new Result.Ok<>(coder.createNull());
            // Embranchement multiple (switch/case)
            case ByteBinaryTag byteTag -> new Result.Ok<>(coder.createByte(byteTag.byteValue()));
            // Embranchement multiple (switch/case)
            case ShortBinaryTag shortTag -> new Result.Ok<>(coder.createShort(shortTag.shortValue()));
            // Embranchement multiple (switch/case)
            case IntBinaryTag intTag -> new Result.Ok<>(coder.createInt(intTag.intValue()));
            // Embranchement multiple (switch/case)
            case LongBinaryTag longTag -> new Result.Ok<>(coder.createLong(longTag.longValue()));
            // Embranchement multiple (switch/case)
            case FloatBinaryTag floatTag -> new Result.Ok<>(coder.createFloat(floatTag.floatValue()));
            // Embranchement multiple (switch/case)
            case DoubleBinaryTag doubleTag -> new Result.Ok<>(coder.createDouble(doubleTag.doubleValue()));
            // Embranchement multiple (switch/case)
            case ByteArrayBinaryTag byteArrayTag -> new Result.Ok<>(coder.createByteArray(byteArrayTag.value()));
            // Embranchement multiple (switch/case)
            case StringBinaryTag stringTag -> new Result.Ok<>(coder.createString(stringTag.value()));
            // Embranchement multiple (switch/case)
            case ListBinaryTag listTag -> {
                // Appelle une méthode
                listTag = listTag.unwrapHeterogeneity();
                // Appelle une méthode
                final ListBuilder<O> list = coder.createList(listTag.size());
                // Boucle : répète un bloc
                for (int i = 0; i < listTag.size(); i++) {
                    // Embranchement multiple (switch/case)
                    switch (convertTo(coder, listTag.get(i))) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok<O> ok -> list.add(ok.value());
                        // Embranchement multiple (switch/case)
                        case Result.Error<O> error -> {
                            // Instruction de code
                            yield new Result.Error<>(i + ": " + error);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                yield new Result.Ok<>(list.build());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case CompoundBinaryTag compoundTag -> {
                // Appelle une méthode
                final MapBuilder<O> map = coder.createMap();
                // Boucle : répète un bloc
                for (Map.Entry<String, ? extends BinaryTag> entry : compoundTag) {
                    // Embranchement multiple (switch/case)
                    switch (convertTo(coder, entry.getValue())) {
                        // Embranchement multiple (switch/case)
                        case Result.Ok<O> ok -> map.put(coder.createString(entry.getKey()), ok.value());
                        // Embranchement multiple (switch/case)
                        case Result.Error<O> error -> {
                            // Appelle une méthode
                            yield new Result.Error<>(entry.getKey() + ": " + error);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                yield new Result.Ok<>(map.build());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case IntArrayBinaryTag intArrayTag -> new Result.Ok<>(coder.createIntArray(intArrayTag.value()));
            // Embranchement multiple (switch/case)
            case LongArrayBinaryTag longArrayTag -> new Result.Ok<>(coder.createLongArray(longArrayTag.value()));
            // Instruction de code
            default -> new Result.Error<>("Unsupported type: " + value);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
