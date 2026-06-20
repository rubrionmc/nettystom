// Déclaration du paquet de ce fichier
package net.minestom.server.network;

// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.Result;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTranscoder;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.Unit;
// Import d'une classe nécessaire
import net.minestom.server.utils.json.JsonUtil;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.io.UTFDataFormatException;
// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Supplier;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBufferImpl.impl;

// Déclaration de type (classe/interface/enum/record)
interface NetworkBufferTypeImpl<T> extends NetworkBuffer.Type<T> {
    // Affecte une valeur
    int SEGMENT_BITS = 0x7F;
    // Affecte une valeur
    int CONTINUE_BIT = 0x80;

    // Déclaration de type (classe/interface/enum/record)
    record UnitType() implements NetworkBufferTypeImpl<Unit> {
        // Annotation pour l'élément suivant
        @Override public void write(NetworkBuffer buffer, Unit value) {}
        // Annotation pour l'élément suivant
        @Override public Unit read(NetworkBuffer buffer) { return Unit.INSTANCE; }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BooleanType() implements NetworkBufferTypeImpl<Boolean> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Boolean value) {
            // Appelle une méthode
            buffer.ensureWritable(1);
            // Appelle une méthode
            impl(buffer)._putByte(buffer.writeIndex(), value ? (byte) 1 : (byte) 0);
            // Appelle une méthode
            buffer.advanceWrite(1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Boolean read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte value = impl(buffer)._getByte(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(1);
            // Renvoie une valeur à l'appelant
            return value == 1;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ByteType() implements NetworkBufferTypeImpl<Byte> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Byte value) {
            // Appelle une méthode
            buffer.ensureWritable(1);
            // Appelle une méthode
            impl(buffer)._putByte(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Byte read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte value = impl(buffer)._getByte(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(1);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnsignedByteType() implements NetworkBufferTypeImpl<Short> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Short value) {
            // Appelle une méthode
            buffer.ensureWritable(1);
            // Appelle une méthode
            impl(buffer)._putByte(buffer.writeIndex(), (byte) (value & 0xFF));
            // Appelle une méthode
            buffer.advanceWrite(1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Short read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte value = impl(buffer)._getByte(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(1);
            // Renvoie une valeur à l'appelant
            return (short) (value & 0xFF);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ShortType() implements NetworkBufferTypeImpl<Short> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Short value) {
            // Appelle une méthode
            buffer.ensureWritable(2);
            // Appelle une méthode
            impl(buffer)._putShort(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(2);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Short read(NetworkBuffer buffer) {
            // Appelle une méthode
            final short value = impl(buffer)._getShort(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(2);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnsignedShortType() implements NetworkBufferTypeImpl<Integer> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Integer value) {
            // Appelle une méthode
            buffer.ensureWritable(2);
            // Appelle une méthode
            impl(buffer)._putShort(buffer.writeIndex(), (short) (value & 0xFFFF));
            // Appelle une méthode
            buffer.advanceWrite(2);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Integer read(NetworkBuffer buffer) {
            // Appelle une méthode
            final short value = impl(buffer)._getShort(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(2);
            // Renvoie une valeur à l'appelant
            return value & 0xFFFF;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record IntType() implements NetworkBufferTypeImpl<Integer> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Integer value) {
            // Appelle une méthode
            buffer.ensureWritable(4);
            // Appelle une méthode
            impl(buffer)._putInt(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(4);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Integer read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int value = impl(buffer)._getInt(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(4);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnsignedIntType() implements NetworkBufferTypeImpl<Long> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Long value) {
            // Appelle une méthode
            buffer.ensureWritable(4);
            // Appelle une méthode
            impl(buffer)._putInt(buffer.writeIndex(), (int) (value & 0xFFFFFFFFL));
            // Appelle une méthode
            buffer.advanceWrite(4);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Long read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int value = impl(buffer)._getInt(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(4);
            // Renvoie une valeur à l'appelant
            return value & 0xFFFFFFFFL;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LongType() implements NetworkBufferTypeImpl<Long> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Long value) {
            // Appelle une méthode
            buffer.ensureWritable(8);
            // Appelle une méthode
            impl(buffer)._putLong(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(8);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Long read(NetworkBuffer buffer) {
            // Appelle une méthode
            final long value = impl(buffer)._getLong(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(8);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record FloatType() implements NetworkBufferTypeImpl<Float> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Float value) {
            // Appelle une méthode
            buffer.ensureWritable(4);
            // Appelle une méthode
            impl(buffer)._putFloat(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(4);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Float read(NetworkBuffer buffer) {
            // Appelle une méthode
            final float value = impl(buffer)._getFloat(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(4);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DoubleType() implements NetworkBufferTypeImpl<Double> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Double value) {
            // Appelle une méthode
            buffer.ensureWritable(8);
            // Appelle une méthode
            impl(buffer)._putDouble(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(8);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Double read(NetworkBuffer buffer) {
            // Appelle une méthode
            final double value = impl(buffer)._getDouble(buffer.readIndex());
            // Appelle une méthode
            buffer.advanceRead(8);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record VarIntType() implements NetworkBufferTypeImpl<Integer> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Integer boxed) {
            // Appelle une méthode
            buffer.ensureWritable(5);
            // Appelle une méthode
            long index = buffer.writeIndex();
            // Affecte une valeur
            int value = boxed;
            // Appelle une méthode
            var nio = impl(buffer);
            // Boucle : répète un bloc
            while (true) {
                // Embranchement : vérifie une condition
                if ((value & ~SEGMENT_BITS) == 0) {
                    // Appelle une méthode
                    nio._putByte(index++, (byte) value);
                    // Appelle une méthode
                    buffer.advanceWrite(index - buffer.writeIndex());
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                nio._putByte(index++, (byte) ((byte) (value & SEGMENT_BITS) | CONTINUE_BIT));
                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                // Instruction de code
                value >>>= 7;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Integer read(NetworkBuffer buffer) {
            // Appelle une méthode
            long index = buffer.readIndex();
            // https://github.com/jvm-profiling-tools/async-profiler/blob/a38a375dc62b31a8109f3af97366a307abb0fe6f/src/converter/one/jfr/JfrReader.java#L393
            // Affecte une valeur
            int result = 0;
            // Boucle : répète un bloc
            for (int shift = 0; ; shift += 7) {
                // Appelle une méthode
                byte b = impl(buffer)._getByte(index++);
                // Appelle une méthode
                result |= (b & 0x7f) << shift;
                // Embranchement : vérifie une condition
                if (b >= 0) {
                    // Appelle une méthode
                    buffer.advanceRead(index - buffer.readIndex());
                    // Renvoie une valeur à l'appelant
                    return result;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OptionalVarIntType() implements NetworkBufferTypeImpl<@Nullable Integer> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, @Nullable Integer value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value == null ? 0 : value + 1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable Integer read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int value = buffer.read(VAR_INT);
            // Renvoie une valeur à l'appelant
            return value == 0 ? null : value - 1;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record VarInt3Type() implements NetworkBufferTypeImpl<Integer> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Integer boxed) {
            // Affecte une valeur
            final int value = boxed;
            // Value must be between 0 and 2^21
            // Appelle une méthode
            Check.argCondition(value < 0 || value >= (1 << 21), "VarInt3 out of bounds: {0}", value);
            // Appelle une méthode
            buffer.ensureWritable(3);
            // Appelle une méthode
            final long startIndex = buffer.writeIndex();
            // Appelle une méthode
            var impl = impl(buffer);
            // Appelle une méthode
            impl._putByte(startIndex, (byte) (value & 0x7F | 0x80));
            // Appelle une méthode
            impl._putByte(startIndex + 1, (byte) ((value >>> 7) & 0x7F | 0x80));
            // Appelle une méthode
            impl._putByte(startIndex + 2, (byte) (value >>> 14));
            // Appelle une méthode
            buffer.advanceWrite(3);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Integer read(NetworkBuffer buffer) {
            // Ensure that the buffer can read other var-int sizes
            // The optimization is mostly relevant for writing
            // Renvoie une valeur à l'appelant
            return buffer.read(VAR_INT);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record VarLongType() implements NetworkBufferTypeImpl<Long> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Long value) {
            // Appelle une méthode
            buffer.ensureWritable(10);
            // Affecte une valeur
            int size = 0;
            // Boucle : répète un bloc
            while (true) {
                // Embranchement : vérifie une condition
                if ((value & ~((long) SEGMENT_BITS)) == 0) {
                    // Appelle une méthode
                    impl(buffer)._putByte(buffer.writeIndex() + size, (byte) value.intValue());
                    // Appelle une méthode
                    buffer.advanceWrite(size + 1);
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }
                // Instruction de code
                impl(buffer)._putByte(buffer.writeIndex() + size,
                        // Appelle une méthode
                        (byte) (value & SEGMENT_BITS | CONTINUE_BIT));
                // Instruction de code
                size++;
                // note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                // Instruction de code
                value >>>= 7;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Long read(NetworkBuffer buffer) {
            // Affecte une valeur
            int length = 0;
            // Affecte une valeur
            long value = 0;
            // Affecte une valeur
            int position = 0;
            // Instruction de code
            byte currentByte;
            // Boucle : répète un bloc
            while (true) {
                // Appelle une méthode
                currentByte = impl(buffer)._getByte(buffer.readIndex() + length);
                // Instruction de code
                length++;
                // Appelle une méthode
                value |= (long) (currentByte & SEGMENT_BITS) << position;
                // Embranchement : vérifie une condition
                if ((currentByte & CONTINUE_BIT) == 0) break;
                // Instruction de code
                position += 7;
                // Embranchement : vérifie une condition
                if (position >= 64) throw new RuntimeException("VarLong is too big");
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            buffer.advanceRead(length);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RawBytesType(int length) implements NetworkBufferTypeImpl<byte[]> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, byte[] value) {
            // Embranchement : vérifie une condition
            if (length != -1 && value.length != length) {
                // Lève une exception
                throw new IllegalArgumentException("Invalid length: " + value.length + " != " + length);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            final int length = value.length;
            // Embranchement : vérifie une condition
            if (length == 0) return;
            // Appelle une méthode
            buffer.ensureWritable(length);
            // Appelle une méthode
            impl(buffer)._putBytes(buffer.writeIndex(), value);
            // Appelle une méthode
            buffer.advanceWrite(length);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public byte[] read(NetworkBuffer buffer) {
            // Appelle une méthode
            long len = (this.length == -1) ? buffer.readableBytes() : this.length;
            // Embranchement : vérifie une condition
            if (len == 0) return new byte[0];
            // Instruction de code
            assert len > 0 : "Invalid remaining length: " + len;

            // Appelle une méthode
            long available = buffer.readableBytes();
            // Embranchement : vérifie une condition
            if (len > available) {
                // Lève une exception
                throw new IndexOutOfBoundsException("Buffer needs " + len + " bytes to read, but only found " + available);
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final int arrayLen = Math.toIntExact(len);
            // Affecte une valeur
            final byte[] bytes = new byte[arrayLen];

            // Appelle une méthode
            impl(buffer)._getBytes(buffer.readIndex(), bytes);
            // Appelle une méthode
            buffer.advanceRead(arrayLen);

            // Renvoie une valeur à l'appelant
            return bytes;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record StringType() implements NetworkBufferTypeImpl<String> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, String value) {
            // Appelle une méthode
            final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            // Appelle une méthode
            buffer.write(BYTE_ARRAY, bytes);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public String read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte[] bytes = buffer.read(BYTE_ARRAY);
            // Renvoie une valeur à l'appelant
            return new String(bytes, StandardCharsets.UTF_8);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record StringTerminatedType() implements NetworkBufferTypeImpl<String> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, String value) {
            // Appelle une méthode
            final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            // Affecte une valeur
            final byte[] terminated = new byte[bytes.length + 1];
            // Appelle une méthode
            System.arraycopy(bytes, 0, terminated, 0, bytes.length);
            // Affecte une valeur
            terminated[terminated.length - 1] = 0;
            // Appelle une méthode
            buffer.write(RAW_BYTES, terminated);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public String read(NetworkBuffer buffer) {
            // Appelle une méthode
            final ByteArrayList bytes = new ByteArrayList();
            // Instruction de code
            byte b;
            // Boucle : répète un bloc
            while ((b = buffer.read(BYTE)) != 0) bytes.add(b);
            // Renvoie une valeur à l'appelant
            return new String(bytes.elements(), StandardCharsets.UTF_8);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record NbtType() implements NetworkBufferTypeImpl<BinaryTag> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, BinaryTag value) {
            // Gestion des exceptions
            try {
                // Appelle une méthode
                impl(buffer).nbtWriter().writeNameless(value);
            // Début d'une méthode/d'un bloc
            } catch (IOException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public BinaryTag read(NetworkBuffer buffer) {
            // Gestion des exceptions
            try {
                // Renvoie une valeur à l'appelant
                return impl(buffer).nbtReader().readNameless();
            // Début d'une méthode/d'un bloc
            } catch (IOException e) {
                // Lève une exception
                throw new RuntimeException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record BlockPositionType() implements NetworkBufferTypeImpl<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Point value) {
            // Appelle une méthode
            final int blockX = value.blockX();
            // Appelle une méthode
            final int blockY = value.blockY();
            // Appelle une méthode
            final int blockZ = value.blockZ();
            // Affecte une valeur
            final long longPos = (((long) blockX & 0x3FFFFFF) << 38) |
                    // Instruction de code
                    (((long) blockZ & 0x3FFFFFF) << 12) |
                    // Appelle une méthode
                    ((long) blockY & 0xFFF);
            // Appelle une méthode
            buffer.write(LONG, longPos);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Point read(NetworkBuffer buffer) {
            // Appelle une méthode
            final long value = buffer.read(LONG);
            // Appelle une méthode
            final int x = (int) (value >> 38);
            // Appelle une méthode
            final int y = (int) (value << 52 >> 52);
            // Appelle une méthode
            final int z = (int) (value << 26 >> 38);
            // Renvoie une valeur à l'appelant
            return new Vec(x, y, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record JsonComponentType() implements NetworkBufferTypeImpl<Component> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Component value) {
            // Affecte une valeur
            final Transcoder<JsonElement> coder = buffer.registries() != null
                    // Instruction de code
                    ? new RegistryTranscoder<>(Transcoder.JSON, buffer.registries())
                    // Instruction de code
                    : Transcoder.JSON;
            // Appelle une méthode
            buffer.write(STRING, JsonUtil.toJson(Codec.COMPONENT.encode(coder, value).orElseThrow()));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Component read(NetworkBuffer buffer) {
            // Affecte une valeur
            final Transcoder<JsonElement> coder = buffer.registries() != null
                    // Instruction de code
                    ? new RegistryTranscoder<>(Transcoder.JSON, buffer.registries())
                    // Instruction de code
                    : Transcoder.JSON;
            // Renvoie une valeur à l'appelant
            return Codec.COMPONENT.decode(coder, JsonUtil.fromJson(buffer.read(STRING))).orElseThrow();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UUIDType() implements NetworkBufferTypeImpl<UUID> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, UUID value) {
            // Appelle une méthode
            buffer.write(LONG, value.getMostSignificantBits());
            // Appelle une méthode
            buffer.write(LONG, value.getLeastSignificantBits());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public UUID read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return new java.util.UUID(buffer.read(LONG), buffer.read(LONG));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record PosType() implements NetworkBufferTypeImpl<Pos> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Pos value) {
            // Appelle une méthode
            buffer.write(DOUBLE, value.x());
            // Appelle une méthode
            buffer.write(DOUBLE, value.y());
            // Appelle une méthode
            buffer.write(DOUBLE, value.z());
            // Appelle une méthode
            buffer.write(FLOAT,  value.yaw());
            // Appelle une méthode
            buffer.write(FLOAT,  value.pitch());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Pos read(NetworkBuffer buffer) {
            // Appelle une méthode
            final double x = buffer.read(DOUBLE);
            // Appelle une méthode
            final double y = buffer.read(DOUBLE);
            // Appelle une méthode
            final double z = buffer.read(DOUBLE);
            // Appelle une méthode
            final float yaw = buffer.read(FLOAT);
            // Appelle une méthode
            final float pitch = buffer.read(FLOAT);
            // Renvoie une valeur à l'appelant
            return new Pos(x, y, z, yaw, pitch);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ByteArrayType() implements NetworkBufferTypeImpl<byte[]> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, byte[] value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.length);
            // Appelle une méthode
            buffer.write(RAW_BYTES, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public byte[] read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int length = buffer.read(VAR_INT);
            // Embranchement : vérifie une condition
            if (length == 0) return new byte[0];
            // Appelle une méthode
            final long remaining = buffer.readableBytes();
            // Appelle une méthode
            Check.argCondition(length > remaining, "String is too long (length: {0}, readable: {1})", length, remaining);
            // Renvoie une valeur à l'appelant
            return buffer.read(FixedRawBytes(length));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LongArrayType() implements NetworkBufferTypeImpl<long[]> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, long[] value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.length);
            // Boucle : répète un bloc
            for (long l : value) buffer.write(LONG, l);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public long[] read(NetworkBuffer buffer) {
            // Appelle une méthode
            final long[] longs = new long[buffer.read(VAR_INT)];
            // Boucle : répète un bloc
            for (int i = 0; i < longs.length; i++) longs[i] = buffer.read(LONG);
            // Renvoie une valeur à l'appelant
            return longs;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record VarIntArrayType() implements NetworkBufferTypeImpl<int[]> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, int[] value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.length);
            // Boucle : répète un bloc
            for (int i : value) buffer.write(VAR_INT, i);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int[] read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int[] ints = new int[buffer.read(VAR_INT)];
            // Boucle : répète un bloc
            for (int i = 0; i < ints.length; i++) ints[i] = buffer.read(VAR_INT);
            // Renvoie une valeur à l'appelant
            return ints;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record VarLongArrayType() implements NetworkBufferTypeImpl<long[]> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, long[] value) {
            // Appelle une méthode
            buffer.write(VAR_INT, value.length);
            // Boucle : répète un bloc
            for (long l : value) buffer.write(VAR_LONG, l);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public long[] read(NetworkBuffer buffer) {
            // Appelle une méthode
            final long[] longs = new long[buffer.read(VAR_INT)];
            // Boucle : répète un bloc
            for (int i = 0; i < longs.length; i++) longs[i] = buffer.read(VAR_LONG);
            // Renvoie une valeur à l'appelant
            return longs;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Vector3Type() implements NetworkBufferTypeImpl<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Point value) {
            // Appelle une méthode
            buffer.write(FLOAT, (float) value.x());
            // Appelle une méthode
            buffer.write(FLOAT, (float) value.y());
            // Appelle une méthode
            buffer.write(FLOAT, (float) value.z());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Point read(NetworkBuffer buffer) {
            // Appelle une méthode
            final float x = buffer.read(FLOAT);
            // Appelle une méthode
            final float y = buffer.read(FLOAT);
            // Appelle une méthode
            final float z = buffer.read(FLOAT);
            // Renvoie une valeur à l'appelant
            return new Vec(x, y, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Vector3DType() implements NetworkBufferTypeImpl<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Point value) {
            // Appelle une méthode
            buffer.write(DOUBLE, value.x());
            // Appelle une méthode
            buffer.write(DOUBLE, value.y());
            // Appelle une méthode
            buffer.write(DOUBLE, value.z());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Point read(NetworkBuffer buffer) {
            // Appelle une méthode
            final double x = buffer.read(DOUBLE);
            // Appelle une méthode
            final double y = buffer.read(DOUBLE);
            // Appelle une méthode
            final double z = buffer.read(DOUBLE);
            // Renvoie une valeur à l'appelant
            return new Vec(x, y, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Vector3IType() implements NetworkBufferTypeImpl<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Point value) {
            // Appelle une méthode
            buffer.write(VAR_INT, (int) value.x());
            // Appelle une méthode
            buffer.write(VAR_INT, (int) value.y());
            // Appelle une méthode
            buffer.write(VAR_INT, (int) value.z());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Point read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int x = buffer.read(VAR_INT);
            // Appelle une méthode
            final int y = buffer.read(VAR_INT);
            // Appelle une méthode
            final int z = buffer.read(VAR_INT);
            // Renvoie une valeur à l'appelant
            return new Vec(x, y, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Vector3BType() implements NetworkBufferTypeImpl<Point> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Point value) {
            // Appelle une méthode
            buffer.write(BYTE, (byte) value.x());
            // Appelle une méthode
            buffer.write(BYTE, (byte) value.y());
            // Appelle une méthode
            buffer.write(BYTE, (byte) value.z());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Point read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte x = buffer.read(BYTE);
            // Appelle une méthode
            final byte y = buffer.read(BYTE);
            // Appelle une méthode
            final byte z = buffer.read(BYTE);
            // Renvoie une valeur à l'appelant
            return new Vec(x, y, z);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LpVector3Type() implements NetworkBufferTypeImpl<Vec> {
        // Affecte une valeur
        private static final int DATA_BITS_MASK = 0b111111111111111;
        // Affecte une valeur
        private static final double MAX_QUANTIZED_VALUE = 32766.0;
        // Affecte une valeur
        private static final int SCALE_BITS_MASK = 0b11;
        // Affecte une valeur
        private static final int CONTINUATION_FLAG = 4;
        // Affecte une valeur
        private static final int X_OFFSET = 3;
        // Affecte une valeur
        private static final int Y_OFFSET = 18;
        // Affecte une valeur
        private static final int Z_OFFSET = 33;
        // Affecte une valeur
        public static final double ABS_MAX_VALUE = 1.7179869183E10;
        // Affecte une valeur
        public static final double ABS_MIN_VALUE = 3.051944088384301E-5;

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Vec value) {
            // Appelle une méthode
            double x = sanitize(value.x()), y = sanitize(value.y()), z = sanitize(value.z());
            // Appelle une méthode
            double max = MathUtils.absMax(x, MathUtils.absMax(y, z));
            // Embranchement : vérifie une condition
            if (max < ABS_MIN_VALUE) {
                // Appelle une méthode
                buffer.write(BYTE, (byte) 0);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                long i = MathUtils.ceilLong(max);
                // Appelle une méthode
                boolean hasContinuation = (i & SCALE_BITS_MASK) != i;
                // Affecte une valeur
                long flags = hasContinuation ? i & SCALE_BITS_MASK | CONTINUATION_FLAG : i;
                // Appelle une méthode
                long px = pack(x / i) << X_OFFSET;
                // Appelle une méthode
                long py = pack(y / i) << Y_OFFSET;
                // Appelle une méthode
                long pz = pack(z / i) << Z_OFFSET;
                // Affecte une valeur
                long packed = flags | px | py | pz;
                // Appelle une méthode
                buffer.write(BYTE, (byte) packed);
                // Appelle une méthode
                buffer.write(BYTE, (byte) (packed >> 8));
                // Appelle une méthode
                buffer.write(INT, (int) (packed >> 16));
                // Embranchement : vérifie une condition
                if (hasContinuation)
                    // Appelle une méthode
                    buffer.write(VAR_INT, (int) (i >> 2));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Vec read(NetworkBuffer buffer) {
            // Appelle une méthode
            int flags = buffer.read(UNSIGNED_BYTE);
            // Embranchement : vérifie une condition
            if (flags == 0) {
                // Renvoie une valeur à l'appelant
                return Vec.ZERO;
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                int p2 = buffer.read(UNSIGNED_BYTE);
                // Appelle une méthode
                long p3 = buffer.read(UNSIGNED_INT);
                // Affecte une valeur
                long value = p3 << 16 | p2 << 8 | flags;
                // Affecte une valeur
                long scale = flags & SCALE_BITS_MASK;
                // Embranchement : vérifie une condition
                if ((flags & CONTINUATION_FLAG) == CONTINUATION_FLAG)
                    // Appelle une méthode
                    scale |= (buffer.read(VAR_INT) & 0xFFFFFFFFL) << 2;
                // Renvoie une valeur à l'appelant
                return new Vec(
                        // Instruction de code
                        unpack(value >> X_OFFSET) * scale,
                        // Instruction de code
                        unpack(value >> Y_OFFSET) * scale,
                        // Instruction de code
                        unpack(value >> Z_OFFSET) * scale
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static double sanitize(double value) {
            // Renvoie une valeur à l'appelant
            return Double.isNaN(value) ? 0.0 : Math.clamp(value, -ABS_MAX_VALUE, ABS_MAX_VALUE);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static long pack(double value) {
            // Renvoie une valeur à l'appelant
            return Math.round((value * 0.5 + 0.5) * MAX_QUANTIZED_VALUE);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private static double unpack(long value) {
            // Renvoie une valeur à l'appelant
            return Math.min((double) (value & DATA_BITS_MASK), MAX_QUANTIZED_VALUE) * 2.0 / MAX_QUANTIZED_VALUE - 1.0;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record QuaternionType() implements NetworkBufferTypeImpl<float[]> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, float[] value) {
            // Appelle une méthode
            buffer.write(FLOAT, value[0]);
            // Appelle une méthode
            buffer.write(FLOAT, value[1]);
            // Appelle une méthode
            buffer.write(FLOAT, value[2]);
            // Appelle une méthode
            buffer.write(FLOAT, value[3]);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public float[] read(NetworkBuffer buffer) {
            // Appelle une méthode
            final float x = buffer.read(FLOAT);
            // Appelle une méthode
            final float y = buffer.read(FLOAT);
            // Appelle une méthode
            final float z = buffer.read(FLOAT);
            // Appelle une méthode
            final float w = buffer.read(FLOAT);
            // Renvoie une valeur à l'appelant
            return new float[]{x, y, z, w};
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Combinators

    // Déclaration de type (classe/interface/enum/record)
    record EnumSetType<E extends Enum<E>>(Class<E> enumType,
                                          // Début d'une méthode/d'un bloc
                                          E[] values, Type<BitSet> bitSetType) implements Type<EnumSet<E>> {
        // Début d'une méthode/d'un bloc
        public EnumSetType {
            // Appelle une méthode
            Objects.requireNonNull(enumType, "enumType");
            // Appelle une méthode
            Objects.requireNonNull(values, "values");
            // Appelle une méthode
            Objects.requireNonNull(bitSetType, "bitSetType");
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public EnumSetType(Class<E> enumClass, E[] values) {
            // Appelle une méthode
            this(enumClass, values, FixedBitSet(values.length));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, EnumSet<E> value) {
            // Appelle une méthode
            BitSet bitSet = new BitSet(values.length);
            // Boucle : répète un bloc
            for (int i = 0; i < values.length; ++i) {
                // Appelle une méthode
                bitSet.set(i, value.contains(values[i]));
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            bitSetType.write(buffer, bitSet);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public EnumSet<E> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final BitSet bitSet = bitSetType.read(buffer);
            // Appelle une méthode
            EnumSet<E> enumSet = EnumSet.noneOf(enumType);
            // Boucle : répète un bloc
            for (int i = 0; i < values.length; ++i) {
                // Embranchement : vérifie une condition
                if (bitSet.get(i)) {
                    // Appelle une méthode
                    enumSet.add(values[i]);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return enumSet;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record FixedBitSetType(int length, Type<byte[]> arrayType) implements Type<BitSet> {
        // Début d'une méthode/d'un bloc
        public FixedBitSetType {
            // Appelle une méthode
            Check.argCondition(length < 0, "Length is negative found {0}", length);
            // Appelle une méthode
            Objects.requireNonNull(arrayType, "arrayType");
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public FixedBitSetType(int length) {
            // Appelle une méthode
            this(length, FixedRawBytes((length + 7) / Long.BYTES));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, BitSet value) {
            // Embranchement : vérifie une condition
            if (value.length() > length) {
                // Lève une exception
                throw new IllegalArgumentException("BitSet is larger than expected size (" + value.length() + ">" + length + ")");
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            byte[] array = value.toByteArray();
            // Appelle une méthode
            final int length = (this.length + 7) / Long.BYTES;
            // Embranchement : vérifie une condition
            if (array.length != length) {
                // Appelle une méthode
                array = Arrays.copyOf(array, length);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            arrayType.write(buffer, array);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public BitSet read(NetworkBuffer buffer) {
            // Appelle une méthode
            final byte[] array = arrayType.read(buffer);
            // Renvoie une valeur à l'appelant
            return BitSet.valueOf(array);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OptionalType<T>(Type<T> parent) implements NetworkBufferTypeImpl<@Nullable T> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Appelle une méthode
            buffer.write(BOOLEAN, value != null);
            // Embranchement : vérifie une condition
            if (value != null) buffer.write(parent, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return buffer.read(BOOLEAN) ? buffer.read(parent) : null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LengthPrefixedType<T>(Type<T> parent, int maxLength) implements NetworkBufferTypeImpl<T> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Write to another buffer and copy (kinda inefficient, but currently unused serverside so its ok for now)
            // Appelle une méthode
            final byte[] componentData = NetworkBuffer.makeArray(b -> parent.write(b, value), buffer.registries());
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE_ARRAY, componentData);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int length = buffer.read(VAR_INT);
            // Appelle une méthode
            Check.argCondition(length > maxLength, "Value is too long (length: {0}, max: {1})", length, maxLength);

            // Appelle une méthode
            final long availableBytes = buffer.readableBytes();
            // Appelle une méthode
            Check.argCondition(length > availableBytes, "Value is too long (length: {0}, available: {1})", length, availableBytes);
            // Appelle une méthode
            final T value = parent.read(buffer);
            // Appelle une méthode
            Check.argCondition(buffer.readableBytes() != availableBytes - length, "Value is too short (length: {0}, available: {1})", length, availableBytes);

            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record MaxLength<T>(Type<T> parent, long maxLength) implements NetworkBufferTypeImpl<T> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Appelle une méthode
            final long length = parent.sizeOf(value);
            // Appelle une méthode
            Check.argCondition(length > maxLength, "Value is too long (length: {0}, max: {1})", length, maxLength);
            // Appelle une méthode
            buffer.write(parent, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Appelle une méthode
            final long index = buffer.readIndex();
            // Appelle une méthode
            final T value = parent.read(buffer);
            // Appelle une méthode
            final long length = buffer.readIndex() - index;
            // Appelle une méthode
            Check.argCondition(length > maxLength, "Value is too long (length: {0}, max: {1})", length, maxLength);
            // Renvoie une valeur à l'appelant
            return value;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class LazyType<T> implements NetworkBufferTypeImpl<T> {
        // Instruction de code
        private final Supplier<NetworkBuffer.Type<T>> supplier;
        // Instruction de code
        private Type<T> type;

        // Début d'une méthode/d'un bloc
        public LazyType(Supplier<NetworkBuffer.Type<T>> supplier) {
            // Accès à l'objet courant/parent
            this.supplier = supplier;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Embranchement : vérifie une condition
            if (type == null) type = supplier.get();
            // Appelle une méthode
            type.write(buffer, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Embranchement : vérifie une condition
            if (type == null) type = supplier.get();
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class RecursiveType<T> implements NetworkBufferTypeImpl<T> {
        // Instruction de code
        final Type<T> delegate;

        // Début d'une méthode/d'un bloc
        public RecursiveType(Function<Type<T>, Type<T>> self) {
            // Appelle une méthode
            Objects.requireNonNull(self, "self");
            // Accès à l'objet courant/parent
            this.delegate = Objects.requireNonNull(self.apply(this), "delegate");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Appelle une méthode
            delegate.write(buffer, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return delegate.read(buffer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TypedNbtType<T>(Codec<T> nbtType) implements NetworkBufferTypeImpl<T> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Appelle une méthode
            final Registries registries = impl(buffer).registries;
            // Appelle une méthode
            Check.stateCondition(registries == null, "Buffer does not have registries");
            // Appelle une méthode
            final Result<BinaryTag> result = nbtType.encode(new RegistryTranscoder<>(Transcoder.NBT, registries), value);
            // Embranchement multiple (switch/case)
            switch (result) {
                // Embranchement multiple (switch/case)
                case Result.Ok(BinaryTag tag) -> buffer.write(NBT, tag);
                // Embranchement multiple (switch/case)
                case Result.Error(String message) -> throw new IllegalArgumentException("Invalid NBT tag: " + message);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Appelle une méthode
            final Registries registries = impl(buffer).registries;
            // Appelle une méthode
            Check.stateCondition(registries == null, "Buffer does not have registries");
            // Appelle une méthode
            final Result<T> result = nbtType.decode(new RegistryTranscoder<>(Transcoder.NBT, registries), buffer.read(NBT));
            // Renvoie une valeur à l'appelant
            return switch (result) {
                // Embranchement multiple (switch/case)
                case Result.Ok(T value) -> value;
                // Embranchement multiple (switch/case)
                case Result.Error(String message) -> throw new IllegalArgumentException("Invalid NBT tag: " + message);
            // Fin d'un bloc/d'une expression
            };
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record EitherType<L, R>(
            // Instruction de code
            NetworkBuffer.Type<L> left,
            // Instruction de code
            NetworkBuffer.Type<R> right
    // Début d'une méthode/d'un bloc
    ) implements NetworkBuffer.Type<Either<L, R>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Either<L, R> value) {
            // Embranchement multiple (switch/case)
            switch (value) {
                // Embranchement multiple (switch/case)
                case Either.Left(L leftValue) -> {
                    // Appelle une méthode
                    buffer.write(BOOLEAN, true);
                    // Appelle une méthode
                    buffer.write(left, leftValue);
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case Either.Right(R rightValue) -> {
                    // Appelle une méthode
                    buffer.write(BOOLEAN, false);
                    // Appelle une méthode
                    buffer.write(right, rightValue);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Either<L, R> read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return buffer.read(BOOLEAN)
                    // Instruction de code
                    ? Either.left(buffer.read(left))
                    // Appelle une méthode
                    : Either.right(buffer.read(right));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TransformType<T, S>(Type<T> parent,
                               // Instruction de code
                               Function<T, S> to,
                               // Début d'une méthode/d'un bloc
                               Function<S, T> from) implements NetworkBufferTypeImpl<S> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, S value) {
            // Appelle une méthode
            parent.write(buffer, from.apply(value));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public S read(NetworkBuffer buffer) {
            // Renvoie une valeur à l'appelant
            return to.apply(parent.read(buffer));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record MapType<K, V>(Type<K> parent, NetworkBuffer.Type<V> valueType,
                         // Début d'une méthode/d'un bloc
                         int maxSize) implements NetworkBufferTypeImpl<Map<K, V>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Map<K, V> map) {
            // Appelle une méthode
            buffer.write(VAR_INT, map.size());
            // Boucle : répète un bloc
            for (Map.Entry<K, V> entry : map.entrySet()) {
                // Appelle une méthode
                buffer.write(parent, entry.getKey());
                // Appelle une méthode
                buffer.write(valueType, entry.getValue());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Map<K, V> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int size = buffer.read(VAR_INT);
            // Appelle une méthode
            Check.argCondition(size > maxSize, "Map size ({0}) is higher than the maximum allowed size ({1})", size, maxSize);
            // Appelle une méthode
            K[] keys = (K[]) new Object[size];
            // Appelle une méthode
            V[] values = (V[]) new Object[size];
            // Boucle : répète un bloc
            for (int i = 0; i < size; i++) {
                // Appelle une méthode
                keys[i] = buffer.read(parent);
                // Appelle une méthode
                values[i] = buffer.read(valueType);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return Map.copyOf(new Object2ObjectArrayMap<>(keys, values, size));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record ListType<T>(Type<T> parent, int maxSize) implements NetworkBufferTypeImpl<List<T>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, List<T> values) {
            // Embranchement : vérifie une condition
            if (values == null) { buffer.write(BYTE, (byte) 0); return; }
            // Appelle une méthode
            buffer.write(VAR_INT, values.size());
            // Boucle : répète un bloc
            for (T value : values) buffer.write(parent, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<T> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int size = buffer.read(VAR_INT);
            // Appelle une méthode
            Check.argCondition(size > maxSize, "Collection size ({0}) > max ({1})", size, maxSize);
            // Appelle une méthode
            T[] values = (T[]) new Object[size];
            // Boucle : répète un bloc
            for (int i = 0; i < size; i++) values[i] = buffer.read(parent);
            // Renvoie une valeur à l'appelant
            return List.of(values);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record SetType<T>(Type<T> parent, int maxSize) implements NetworkBufferTypeImpl<Set<T>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Set<T> values) {
            // Embranchement : vérifie une condition
            if (values == null) { buffer.write(BYTE, (byte) 0); return; }
            // Appelle une méthode
            buffer.write(VAR_INT, values.size());
            // Boucle : répète un bloc
            for (T v : values) buffer.write(parent, v);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Set<T> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int size = buffer.read(VAR_INT);
            // Appelle une méthode
            Check.argCondition(size > maxSize, "Collection size ({0}) > max ({1})", size, maxSize);
            // Appelle une méthode
            T[] values = (T[]) new Object[size];
            // Boucle : répète un bloc
            for (int i = 0; i < size; i++) values[i] = buffer.read(parent);
            // Renvoie une valeur à l'appelant
            return Set.of(values);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record UnionType<T, K, TR extends T>(
            // Instruction de code
            Type<K> keyType, Function<T, ? extends K> keyFunc,
            // Instruction de code
            Function<K, NetworkBuffer.Type<TR>> serializers
    // Début d'une méthode/d'un bloc
    ) implements NetworkBufferTypeImpl<T> {

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Much nicer than using the correct wildcard type for returns, pretty much ensuring T has subtypes already.
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Appelle une méthode
            final K key = keyFunc.apply(value);
            // Appelle une méthode
            buffer.write(keyType, key);
            // Appelle une méthode
            final var ser = serializers.apply(key);
            // Embranchement : vérifie une condition
            if (ser == null) throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Appelle une méthode
            ser.write(buffer, (TR) value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Appelle une méthode
            final K key = buffer.read(keyType);
            // Appelle une méthode
            final var ser = serializers.apply(key);
            // Embranchement : vérifie une condition
            if (ser == null) throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Renvoie une valeur à l'appelant
            return ser.read(buffer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record TaggedType<T, D>(
            // Instruction de code
            Type<D> discriminatorType, Function<? super T, ? extends D> discriminatorFromValue,
            // Instruction de code
            Map<? super D, Type<? extends T>> serializerMap, @Nullable Type<? extends T> fallback
    // Début d'une méthode/d'un bloc
    ) implements NetworkBufferTypeImpl<T> {
        // Début d'une méthode/d'un bloc
        public TaggedType {
            // Appelle une méthode
            Objects.requireNonNull(discriminatorType, "discriminatorType");
            // Appelle une méthode
            Objects.requireNonNull(discriminatorFromValue, "discriminatorFromValue");
            // Appelle une méthode
            serializerMap = Map.copyOf(serializerMap);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked") // Likely fine here
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, T value) {
            // Appelle une méthode
            final D key = discriminatorFromValue.apply(value);
            // Appelle une méthode
            buffer.write(discriminatorType, key);
            // Appelle une méthode
            var serializer = serializerMap.getOrDefault(key, fallback);
            // Embranchement : vérifie une condition
            if (serializer == null)
                // Lève une exception
                throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Appelle une méthode
            ((Type<T>) serializer).write(buffer, value);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T read(NetworkBuffer buffer) {
            // Appelle une méthode
            final D key = buffer.read(discriminatorType);
            // Appelle une méthode
            var serializer = serializerMap.getOrDefault(key, fallback);
            // Embranchement : vérifie une condition
            if (serializer == null) throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Renvoie une valeur à l'appelant
            return serializer.read(buffer);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record IOUTF8StringType() implements NetworkBufferTypeImpl<String> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, String value) {
            // Appelle une méthode
            final int strlen = value.length();
            // Affecte une valeur
            int utflen = strlen; // optimized for ASCII

            // Boucle : répète un bloc
            for (int i = 0; i < strlen; i++) {
                // Appelle une méthode
                int c = value.charAt(i);
                // Embranchement : vérifie une condition
                if (c >= 0x80 || c == 0) utflen += (c >= 0x800) ? 2 : 1;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (utflen > 65535 || /* overflow */ utflen < strlen)
                // Lève une exception
                throw new RuntimeException("UTF-8 string too long");

            // Appelle une méthode
            buffer.write(SHORT, (short) utflen);
            // Appelle une méthode
            buffer.ensureWritable(utflen);
            // Appelle une méthode
            var impl = (NetworkBufferImpl) buffer;
            // Instruction de code
            int i;
            // Boucle : répète un bloc
            for (i = 0; i < strlen; i++) { // optimized for initial run of ASCII
                // Appelle une méthode
                int c = value.charAt(i);
                // Embranchement : vérifie une condition
                if (c >= 0x80 || c == 0) break;
                // Appelle une méthode
                impl._putByte(buffer.writeIndex(), (byte) c);
                // Appelle une méthode
                impl.advanceWrite(1);
            // Fin d'un bloc/d'une expression
            }

            // Boucle : répète un bloc
            for (; i < strlen; i++) {
                // Appelle une méthode
                int c = value.charAt(i);
                // Embranchement : vérifie une condition
                if (c < 0x80 && c != 0) {
                    // Appelle une méthode
                    impl._putByte(buffer.writeIndex(), (byte) c);
                    // Appelle une méthode
                    impl.advanceWrite(1);
                // Embranchement : vérifie une condition
                } else if (c >= 0x800) {
                    // Appelle une méthode
                    impl._putByte(buffer.writeIndex(), (byte) (0xE0 | ((c >> 12) & 0x0F)));
                    // Appelle une méthode
                    impl._putByte(buffer.writeIndex() + 1, (byte) (0x80 | ((c >> 6) & 0x3F)));
                    // Appelle une méthode
                    impl._putByte(buffer.writeIndex() + 2, (byte) (0x80 | ((c >> 0) & 0x3F)));
                    // Appelle une méthode
                    impl.advanceWrite(3);
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    impl._putByte(buffer.writeIndex(), (byte) (0xC0 | ((c >> 6) & 0x1F)));
                    // Appelle une méthode
                    impl._putByte(buffer.writeIndex() + 1, (byte) (0x80 | ((c >> 0) & 0x3F)));
                    // Appelle une méthode
                    impl.advanceWrite(2);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public String read(NetworkBuffer buffer) {
            // Appelle une méthode
            final int utflen = buffer.read(UNSIGNED_SHORT);
            // Embranchement : vérifie une condition
            if (buffer.readableBytes() < utflen)
                // Lève une exception
                throw new IllegalArgumentException("Invalid String size.");
            // Appelle une méthode
            final byte[] bytearr = buffer.read(FixedRawBytes(utflen));
            // Affecte une valeur
            final char[] chararr = new char[utflen];
            // Affecte une valeur
            int c, char2, char3, count = 0, chararr_count = 0;
            // Boucle : répète un bloc
            while (count < utflen) {
                // Affecte une valeur
                c = bytearr[count] & 0xFF;
                // Embranchement : vérifie une condition
                if (c > 127) break;
                // Instruction de code
                count++;
                // Appelle une méthode
                chararr[chararr_count++] = (char) c;
            // Fin d'un bloc/d'une expression
            }

            // Boucle : répète un bloc
            while (count < utflen) {
                // Affecte une valeur
                c = bytearr[count] & 0xFF;
                // Gestion des exceptions
                try {
                    // Embranchement multiple (switch/case)
                    switch (c >> 4) {
                        // Embranchement multiple (switch/case)
                        case 0, 1, 2, 3, 4, 5, 6, 7 -> { count++; chararr[chararr_count++] = (char) c; }
                        // Embranchement multiple (switch/case)
                        case 12, 13 -> {
                            /* 110x xxxx   10xx xxxx*/
                            // Instruction de code
                            count += 2;
                            // Embranchement : vérifie une condition
                            if (count > utflen) throw new UTFDataFormatException("partial char at end");
                            // Affecte une valeur
                            char2 = bytearr[count - 1];
                            // Embranchement : vérifie une condition
                            if ((char2 & 0xC0) != 0x80) throw new UTFDataFormatException("malformed @" + count);
                            // Appelle une méthode
                            chararr[chararr_count++] = (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement multiple (switch/case)
                        case 14 -> {
                            /* 1110 xxxx  10xx xxxx  10xx xxxx */
                            // Instruction de code
                            count += 3;
                            // Embranchement : vérifie une condition
                            if (count > utflen) throw new UTFDataFormatException("partial char at end");
                            // Affecte une valeur
                            char2 = bytearr[count - 2];
                            // Affecte une valeur
                            char3 = bytearr[count - 1];
                            // Embranchement : vérifie une condition
                            if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                                // Lève une exception
                                throw new UTFDataFormatException("malformed @" + (count - 1));
                            // Appelle une méthode
                            chararr[chararr_count++] = (char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | (char3 & 0x3F));
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement multiple (switch/case)
                        default -> throw new UTFDataFormatException("malformed @" + count);
                    // Fin d'un bloc/d'une expression
                    }
                // Début d'une méthode/d'un bloc
                } catch (UTFDataFormatException e) {
                    // Lève une exception
                    throw new IllegalArgumentException(e);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // The number of chars produced may be less than utflen
            // Renvoie une valeur à l'appelant
            return new String(chararr, 0, chararr_count);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static <T> long sizeOf(Type<T> type, T value, Registries registries) {
        // Appelle une méthode
        final NetworkBuffer dummy = NetworkBufferImpl.dummy(registries);
        // Appelle une méthode
        type.write(dummy, value);
        // Renvoie une valeur à l'appelant
        return dummy.writeIndex();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
