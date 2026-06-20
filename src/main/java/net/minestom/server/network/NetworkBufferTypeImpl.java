// Package declaration for this file
package net.minestom.server.network;

// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.Result;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.Unit;
// Import of a required class
import net.minestom.server.utils.json.JsonUtil;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.io.UTFDataFormatException;
// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;
// Static import of a member
import static net.minestom.server.network.NetworkBufferImpl.impl;

// Type declaration (class/interface/enum/record)
interface NetworkBufferTypeImpl<T> extends NetworkBuffer.Type<T> {
    // Assigns a value
    int SEGMENT_BITS = 0x7F;
    // Assigns a value
    int CONTINUE_BIT = 0x80;

    // Type declaration (class/interface/enum/record)
    record UnitType() implements NetworkBufferTypeImpl<Unit> {
        // Annotation for the following element
        @Override public void write(NetworkBuffer buffer, Unit value) {}
        // Annotation for the following element
        @Override public Unit read(NetworkBuffer buffer) { return Unit.INSTANCE; }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BooleanType() implements NetworkBufferTypeImpl<Boolean> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Boolean value) {
            // Calls a method
            buffer.ensureWritable(1);
            // Calls a method
            impl(buffer)._putByte(buffer.writeIndex(), value ? (byte) 1 : (byte) 0);
            // Calls a method
            buffer.advanceWrite(1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Boolean read(NetworkBuffer buffer) {
            // Calls a method
            final byte value = impl(buffer)._getByte(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(1);
            // Returns a value to the caller
            return value == 1;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ByteType() implements NetworkBufferTypeImpl<Byte> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Byte value) {
            // Calls a method
            buffer.ensureWritable(1);
            // Calls a method
            impl(buffer)._putByte(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Byte read(NetworkBuffer buffer) {
            // Calls a method
            final byte value = impl(buffer)._getByte(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(1);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnsignedByteType() implements NetworkBufferTypeImpl<Short> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Short value) {
            // Calls a method
            buffer.ensureWritable(1);
            // Calls a method
            impl(buffer)._putByte(buffer.writeIndex(), (byte) (value & 0xFF));
            // Calls a method
            buffer.advanceWrite(1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Short read(NetworkBuffer buffer) {
            // Calls a method
            final byte value = impl(buffer)._getByte(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(1);
            // Returns a value to the caller
            return (short) (value & 0xFF);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ShortType() implements NetworkBufferTypeImpl<Short> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Short value) {
            // Calls a method
            buffer.ensureWritable(2);
            // Calls a method
            impl(buffer)._putShort(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(2);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Short read(NetworkBuffer buffer) {
            // Calls a method
            final short value = impl(buffer)._getShort(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(2);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnsignedShortType() implements NetworkBufferTypeImpl<Integer> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Integer value) {
            // Calls a method
            buffer.ensureWritable(2);
            // Calls a method
            impl(buffer)._putShort(buffer.writeIndex(), (short) (value & 0xFFFF));
            // Calls a method
            buffer.advanceWrite(2);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Integer read(NetworkBuffer buffer) {
            // Calls a method
            final short value = impl(buffer)._getShort(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(2);
            // Returns a value to the caller
            return value & 0xFFFF;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record IntType() implements NetworkBufferTypeImpl<Integer> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Integer value) {
            // Calls a method
            buffer.ensureWritable(4);
            // Calls a method
            impl(buffer)._putInt(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(4);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Integer read(NetworkBuffer buffer) {
            // Calls a method
            final int value = impl(buffer)._getInt(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(4);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnsignedIntType() implements NetworkBufferTypeImpl<Long> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Long value) {
            // Calls a method
            buffer.ensureWritable(4);
            // Calls a method
            impl(buffer)._putInt(buffer.writeIndex(), (int) (value & 0xFFFFFFFFL));
            // Calls a method
            buffer.advanceWrite(4);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Long read(NetworkBuffer buffer) {
            // Calls a method
            final int value = impl(buffer)._getInt(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(4);
            // Returns a value to the caller
            return value & 0xFFFFFFFFL;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LongType() implements NetworkBufferTypeImpl<Long> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Long value) {
            // Calls a method
            buffer.ensureWritable(8);
            // Calls a method
            impl(buffer)._putLong(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(8);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Long read(NetworkBuffer buffer) {
            // Calls a method
            final long value = impl(buffer)._getLong(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(8);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record FloatType() implements NetworkBufferTypeImpl<Float> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Float value) {
            // Calls a method
            buffer.ensureWritable(4);
            // Calls a method
            impl(buffer)._putFloat(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(4);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Float read(NetworkBuffer buffer) {
            // Calls a method
            final float value = impl(buffer)._getFloat(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(4);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DoubleType() implements NetworkBufferTypeImpl<Double> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Double value) {
            // Calls a method
            buffer.ensureWritable(8);
            // Calls a method
            impl(buffer)._putDouble(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(8);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Double read(NetworkBuffer buffer) {
            // Calls a method
            final double value = impl(buffer)._getDouble(buffer.readIndex());
            // Calls a method
            buffer.advanceRead(8);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record VarIntType() implements NetworkBufferTypeImpl<Integer> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Integer boxed) {
            // Calls a method
            buffer.ensureWritable(5);
            // Calls a method
            long index = buffer.writeIndex();
            // Assigns a value
            int value = boxed;
            // Calls a method
            var nio = impl(buffer);
            // Loop: repeats a block
            while (true) {
                // Branch: checks a condition
                if ((value & ~SEGMENT_BITS) == 0) {
                    // Calls a method
                    nio._putByte(index++, (byte) value);
                    // Calls a method
                    buffer.advanceWrite(index - buffer.writeIndex());
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }
                // Calls a method
                nio._putByte(index++, (byte) ((byte) (value & SEGMENT_BITS) | CONTINUE_BIT));
                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                // Code statement
                value >>>= 7;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Integer read(NetworkBuffer buffer) {
            // Calls a method
            long index = buffer.readIndex();
            // https://github.com/jvm-profiling-tools/async-profiler/blob/a38a375dc62b31a8109f3af97366a307abb0fe6f/src/converter/one/jfr/JfrReader.java#L393
            // Assigns a value
            int result = 0;
            // Loop: repeats a block
            for (int shift = 0; shift <= 28; shift += 7) {
                // Calls a method
                byte b = impl(buffer)._getByte(index++);
                // Calls a method
                result |= (b & 0x7f) << shift;
                // Branch: checks a condition
                if (b >= 0) {
                    // Calls a method
                    buffer.advanceRead(index - buffer.readIndex());
                    // Returns a value to the caller
                    return result;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Throws an exception
            throw new IndexOutOfBoundsException("VarInt too long");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OptionalVarIntType() implements NetworkBufferTypeImpl<@Nullable Integer> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, @Nullable Integer value) {
            // Calls a method
            buffer.write(VAR_INT, value == null ? 0 : value + 1);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Integer read(NetworkBuffer buffer) {
            // Calls a method
            final int value = buffer.read(VAR_INT);
            // Returns a value to the caller
            return value == 0 ? null : value - 1;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record VarInt3Type() implements NetworkBufferTypeImpl<Integer> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Integer boxed) {
            // Assigns a value
            final int value = boxed;
            // Value must be between 0 and 2^21
            // Calls a method
            Check.argCondition(value < 0 || value >= (1 << 21), "VarInt3 out of bounds: {0}", value);
            // Calls a method
            buffer.ensureWritable(3);
            // Calls a method
            final long startIndex = buffer.writeIndex();
            // Calls a method
            var impl = impl(buffer);
            // Calls a method
            impl._putByte(startIndex, (byte) (value & 0x7F | 0x80));
            // Calls a method
            impl._putByte(startIndex + 1, (byte) ((value >>> 7) & 0x7F | 0x80));
            // Calls a method
            impl._putByte(startIndex + 2, (byte) (value >>> 14));
            // Calls a method
            buffer.advanceWrite(3);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Integer read(NetworkBuffer buffer) {
            // Ensure that the buffer can read other var-int sizes
            // The optimization is mostly relevant for writing
            // Returns a value to the caller
            return buffer.read(VAR_INT);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record VarLongType() implements NetworkBufferTypeImpl<Long> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Long value) {
            // Calls a method
            buffer.ensureWritable(10);
            // Assigns a value
            int size = 0;
            // Loop: repeats a block
            while (true) {
                // Branch: checks a condition
                if ((value & ~((long) SEGMENT_BITS)) == 0) {
                    // Calls a method
                    impl(buffer)._putByte(buffer.writeIndex() + size, (byte) value.intValue());
                    // Calls a method
                    buffer.advanceWrite(size + 1);
                    // Returns a value to the caller
                    return;
                // End of a block/expression
                }
                // Code statement
                impl(buffer)._putByte(buffer.writeIndex() + size,
                        // Calls a method
                        (byte) (value & SEGMENT_BITS | CONTINUE_BIT));
                // Code statement
                size++;
                // note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                // Code statement
                value >>>= 7;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Long read(NetworkBuffer buffer) {
            // Assigns a value
            int length = 0;
            // Assigns a value
            long value = 0;
            // Assigns a value
            int position = 0;
            // Code statement
            byte currentByte;
            // Loop: repeats a block
            while (true) {
                // Calls a method
                currentByte = impl(buffer)._getByte(buffer.readIndex() + length);
                // Code statement
                length++;
                // Calls a method
                value |= (long) (currentByte & SEGMENT_BITS) << position;
                // Branch: checks a condition
                if ((currentByte & CONTINUE_BIT) == 0) break;
                // Code statement
                position += 7;
                // Branch: checks a condition
                if (position >= 64) throw new RuntimeException("VarLong is too big");
            // End of a block/expression
            }
            // Calls a method
            buffer.advanceRead(length);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RawBytesType(int length) implements NetworkBufferTypeImpl<byte[]> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, byte[] value) {
            // Branch: checks a condition
            if (length != -1 && value.length != length) {
                // Throws an exception
                throw new IllegalArgumentException("Invalid length: " + value.length + " != " + length);
            // End of a block/expression
            }
            // Assigns a value
            final int length = value.length;
            // Branch: checks a condition
            if (length == 0) return;
            // Calls a method
            buffer.ensureWritable(length);
            // Calls a method
            impl(buffer)._putBytes(buffer.writeIndex(), value);
            // Calls a method
            buffer.advanceWrite(length);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public byte[] read(NetworkBuffer buffer) {
            // Calls a method
            long len = (this.length == -1) ? buffer.readableBytes() : this.length;
            // Branch: checks a condition
            if (len == 0) return new byte[0];
            // Code statement
            assert len > 0 : "Invalid remaining length: " + len;

            // Calls a method
            long available = buffer.readableBytes();
            // Branch: checks a condition
            if (len > available) {
                // Throws an exception
                throw new IndexOutOfBoundsException("Buffer needs " + len + " bytes to read, but only found " + available);
            // End of a block/expression
            }

            // Calls a method
            final int arrayLen = Math.toIntExact(len);
            // Assigns a value
            final byte[] bytes = new byte[arrayLen];

            // Calls a method
            impl(buffer)._getBytes(buffer.readIndex(), bytes);
            // Calls a method
            buffer.advanceRead(arrayLen);

            // Returns a value to the caller
            return bytes;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record StringType() implements NetworkBufferTypeImpl<String> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, String value) {
            // Calls a method
            final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            // Calls a method
            buffer.write(BYTE_ARRAY, bytes);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public String read(NetworkBuffer buffer) {
            // Calls a method
            final byte[] bytes = buffer.read(BYTE_ARRAY);
            // Returns a value to the caller
            return new String(bytes, StandardCharsets.UTF_8);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record StringTerminatedType() implements NetworkBufferTypeImpl<String> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, String value) {
            // Calls a method
            final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            // Assigns a value
            final byte[] terminated = new byte[bytes.length + 1];
            // Calls a method
            System.arraycopy(bytes, 0, terminated, 0, bytes.length);
            // Assigns a value
            terminated[terminated.length - 1] = 0;
            // Calls a method
            buffer.write(RAW_BYTES, terminated);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public String read(NetworkBuffer buffer) {
            // Calls a method
            final ByteArrayList bytes = new ByteArrayList();
            // Code statement
            byte b;
            // Loop: repeats a block
            while ((b = buffer.read(BYTE)) != 0) bytes.add(b);
            // Returns a value to the caller
            return new String(bytes.elements(), StandardCharsets.UTF_8);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record NbtType() implements NetworkBufferTypeImpl<BinaryTag> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, BinaryTag value) {
            // Exception handling
            try {
                // Calls a method
                impl(buffer).nbtWriter().writeNameless(value);
            // Start of a method/block
            } catch (IOException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public BinaryTag read(NetworkBuffer buffer) {
            // Exception handling
            try {
                // Returns a value to the caller
                return impl(buffer).nbtReader().readNameless();
            // Start of a method/block
            } catch (IOException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record BlockPositionType() implements NetworkBufferTypeImpl<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Point value) {
            // Calls a method
            final int blockX = value.blockX();
            // Calls a method
            final int blockY = value.blockY();
            // Calls a method
            final int blockZ = value.blockZ();
            // Assigns a value
            final long longPos = (((long) blockX & 0x3FFFFFF) << 38) |
                    // Code statement
                    (((long) blockZ & 0x3FFFFFF) << 12) |
                    // Calls a method
                    ((long) blockY & 0xFFF);
            // Calls a method
            buffer.write(LONG, longPos);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Point read(NetworkBuffer buffer) {
            // Calls a method
            final long value = buffer.read(LONG);
            // Calls a method
            final int x = (int) (value >> 38);
            // Calls a method
            final int y = (int) (value << 52 >> 52);
            // Calls a method
            final int z = (int) (value << 26 >> 38);
            // Returns a value to the caller
            return new Vec(x, y, z);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record JsonComponentType() implements NetworkBufferTypeImpl<Component> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Component value) {
            // Assigns a value
            final Transcoder<JsonElement> coder = buffer.registries() != null
                    // Code statement
                    ? new RegistryTranscoder<>(Transcoder.JSON, buffer.registries())
                    // Code statement
                    : Transcoder.JSON;
            // Calls a method
            buffer.write(STRING, JsonUtil.toJson(Codec.COMPONENT.encode(coder, value).orElseThrow()));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Component read(NetworkBuffer buffer) {
            // Assigns a value
            final Transcoder<JsonElement> coder = buffer.registries() != null
                    // Code statement
                    ? new RegistryTranscoder<>(Transcoder.JSON, buffer.registries())
                    // Code statement
                    : Transcoder.JSON;
            // Returns a value to the caller
            return Codec.COMPONENT.decode(coder, JsonUtil.fromJson(buffer.read(STRING))).orElseThrow();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UUIDType() implements NetworkBufferTypeImpl<UUID> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, UUID value) {
            // Calls a method
            buffer.write(LONG, value.getMostSignificantBits());
            // Calls a method
            buffer.write(LONG, value.getLeastSignificantBits());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public UUID read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return new java.util.UUID(buffer.read(LONG), buffer.read(LONG));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record PosType() implements NetworkBufferTypeImpl<Pos> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Pos value) {
            // Calls a method
            buffer.write(DOUBLE, value.x());
            // Calls a method
            buffer.write(DOUBLE, value.y());
            // Calls a method
            buffer.write(DOUBLE, value.z());
            // Calls a method
            buffer.write(FLOAT,  value.yaw());
            // Calls a method
            buffer.write(FLOAT,  value.pitch());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Pos read(NetworkBuffer buffer) {
            // Calls a method
            final double x = buffer.read(DOUBLE);
            // Calls a method
            final double y = buffer.read(DOUBLE);
            // Calls a method
            final double z = buffer.read(DOUBLE);
            // Calls a method
            final float yaw = buffer.read(FLOAT);
            // Calls a method
            final float pitch = buffer.read(FLOAT);
            // Returns a value to the caller
            return new Pos(x, y, z, yaw, pitch);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ByteArrayType() implements NetworkBufferTypeImpl<byte[]> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, byte[] value) {
            // Calls a method
            buffer.write(VAR_INT, value.length);
            // Calls a method
            buffer.write(RAW_BYTES, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public byte[] read(NetworkBuffer buffer) {
            // Calls a method
            final int length = buffer.read(VAR_INT);
            // Branch: checks a condition
            if (length == 0) return new byte[0];
            // Calls a method
            final long remaining = buffer.readableBytes();
            // Calls a method
            Check.argCondition(length > remaining, "String is too long (length: {0}, readable: {1})", length, remaining);
            // Returns a value to the caller
            return buffer.read(FixedRawBytes(length));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LongArrayType() implements NetworkBufferTypeImpl<long[]> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, long[] value) {
            // Calls a method
            buffer.write(VAR_INT, value.length);
            // Loop: repeats a block
            for (long l : value) buffer.write(LONG, l);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public long[] read(NetworkBuffer buffer) {
            // Calls a method
            final long[] longs = new long[buffer.read(VAR_INT)];
            // Loop: repeats a block
            for (int i = 0; i < longs.length; i++) longs[i] = buffer.read(LONG);
            // Returns a value to the caller
            return longs;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record VarIntArrayType() implements NetworkBufferTypeImpl<int[]> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, int[] value) {
            // Calls a method
            buffer.write(VAR_INT, value.length);
            // Loop: repeats a block
            for (int i : value) buffer.write(VAR_INT, i);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int[] read(NetworkBuffer buffer) {
            // Calls a method
            final int[] ints = new int[buffer.read(VAR_INT)];
            // Loop: repeats a block
            for (int i = 0; i < ints.length; i++) ints[i] = buffer.read(VAR_INT);
            // Returns a value to the caller
            return ints;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record VarLongArrayType() implements NetworkBufferTypeImpl<long[]> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, long[] value) {
            // Calls a method
            buffer.write(VAR_INT, value.length);
            // Loop: repeats a block
            for (long l : value) buffer.write(VAR_LONG, l);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public long[] read(NetworkBuffer buffer) {
            // Calls a method
            final long[] longs = new long[buffer.read(VAR_INT)];
            // Loop: repeats a block
            for (int i = 0; i < longs.length; i++) longs[i] = buffer.read(VAR_LONG);
            // Returns a value to the caller
            return longs;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Vector3Type() implements NetworkBufferTypeImpl<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Point value) {
            // Calls a method
            buffer.write(FLOAT, (float) value.x());
            // Calls a method
            buffer.write(FLOAT, (float) value.y());
            // Calls a method
            buffer.write(FLOAT, (float) value.z());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Point read(NetworkBuffer buffer) {
            // Calls a method
            final float x = buffer.read(FLOAT);
            // Calls a method
            final float y = buffer.read(FLOAT);
            // Calls a method
            final float z = buffer.read(FLOAT);
            // Returns a value to the caller
            return new Vec(x, y, z);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Vector3DType() implements NetworkBufferTypeImpl<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Point value) {
            // Calls a method
            buffer.write(DOUBLE, value.x());
            // Calls a method
            buffer.write(DOUBLE, value.y());
            // Calls a method
            buffer.write(DOUBLE, value.z());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Point read(NetworkBuffer buffer) {
            // Calls a method
            final double x = buffer.read(DOUBLE);
            // Calls a method
            final double y = buffer.read(DOUBLE);
            // Calls a method
            final double z = buffer.read(DOUBLE);
            // Returns a value to the caller
            return new Vec(x, y, z);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Vector3IType() implements NetworkBufferTypeImpl<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Point value) {
            // Calls a method
            buffer.write(VAR_INT, (int) value.x());
            // Calls a method
            buffer.write(VAR_INT, (int) value.y());
            // Calls a method
            buffer.write(VAR_INT, (int) value.z());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Point read(NetworkBuffer buffer) {
            // Calls a method
            final int x = buffer.read(VAR_INT);
            // Calls a method
            final int y = buffer.read(VAR_INT);
            // Calls a method
            final int z = buffer.read(VAR_INT);
            // Returns a value to the caller
            return new Vec(x, y, z);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Vector3BType() implements NetworkBufferTypeImpl<Point> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Point value) {
            // Calls a method
            buffer.write(BYTE, (byte) value.x());
            // Calls a method
            buffer.write(BYTE, (byte) value.y());
            // Calls a method
            buffer.write(BYTE, (byte) value.z());
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Point read(NetworkBuffer buffer) {
            // Calls a method
            final byte x = buffer.read(BYTE);
            // Calls a method
            final byte y = buffer.read(BYTE);
            // Calls a method
            final byte z = buffer.read(BYTE);
            // Returns a value to the caller
            return new Vec(x, y, z);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LpVector3Type() implements NetworkBufferTypeImpl<Vec> {
        // Assigns a value
        private static final int DATA_BITS_MASK = 0b111111111111111;
        // Assigns a value
        private static final double MAX_QUANTIZED_VALUE = 32766.0;
        // Assigns a value
        private static final int SCALE_BITS_MASK = 0b11;
        // Assigns a value
        private static final int CONTINUATION_FLAG = 4;
        // Assigns a value
        private static final int X_OFFSET = 3;
        // Assigns a value
        private static final int Y_OFFSET = 18;
        // Assigns a value
        private static final int Z_OFFSET = 33;
        // Assigns a value
        public static final double ABS_MAX_VALUE = 1.7179869183E10;
        // Assigns a value
        public static final double ABS_MIN_VALUE = 3.051944088384301E-5;

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Vec value) {
            // Calls a method
            double x = sanitize(value.x()), y = sanitize(value.y()), z = sanitize(value.z());
            // Calls a method
            double max = MathUtils.absMax(x, MathUtils.absMax(y, z));
            // Branch: checks a condition
            if (max < ABS_MIN_VALUE) {
                // Calls a method
                buffer.write(BYTE, (byte) 0);
            // Alternative branch of the condition
            } else {
                // Calls a method
                long i = MathUtils.ceilLong(max);
                // Calls a method
                boolean hasContinuation = (i & SCALE_BITS_MASK) != i;
                // Assigns a value
                long flags = hasContinuation ? i & SCALE_BITS_MASK | CONTINUATION_FLAG : i;
                // Calls a method
                long px = pack(x / i) << X_OFFSET;
                // Calls a method
                long py = pack(y / i) << Y_OFFSET;
                // Calls a method
                long pz = pack(z / i) << Z_OFFSET;
                // Assigns a value
                long packed = flags | px | py | pz;
                // Calls a method
                buffer.write(BYTE, (byte) packed);
                // Calls a method
                buffer.write(BYTE, (byte) (packed >> 8));
                // Calls a method
                buffer.write(INT, (int) (packed >> 16));
                // Branch: checks a condition
                if (hasContinuation)
                    // Calls a method
                    buffer.write(VAR_INT, (int) (i >> 2));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Vec read(NetworkBuffer buffer) {
            // Calls a method
            int flags = buffer.read(UNSIGNED_BYTE);
            // Branch: checks a condition
            if (flags == 0) {
                // Returns a value to the caller
                return Vec.ZERO;
            // Alternative branch of the condition
            } else {
                // Calls a method
                int p2 = buffer.read(UNSIGNED_BYTE);
                // Calls a method
                long p3 = buffer.read(UNSIGNED_INT);
                // Assigns a value
                long value = p3 << 16 | p2 << 8 | flags;
                // Assigns a value
                long scale = flags & SCALE_BITS_MASK;
                // Branch: checks a condition
                if ((flags & CONTINUATION_FLAG) == CONTINUATION_FLAG)
                    // Calls a method
                    scale |= (buffer.read(VAR_INT) & 0xFFFFFFFFL) << 2;
                // Returns a value to the caller
                return new Vec(
                        // Code statement
                        unpack(value >> X_OFFSET) * scale,
                        // Code statement
                        unpack(value >> Y_OFFSET) * scale,
                        // Code statement
                        unpack(value >> Z_OFFSET) * scale
                // End of a block/expression
                );
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        private static double sanitize(double value) {
            // Returns a value to the caller
            return Double.isNaN(value) ? 0.0 : Math.clamp(value, -ABS_MAX_VALUE, ABS_MAX_VALUE);
        // End of a block/expression
        }

        // Start of a method/block
        private static long pack(double value) {
            // Returns a value to the caller
            return Math.round((value * 0.5 + 0.5) * MAX_QUANTIZED_VALUE);
        // End of a block/expression
        }

        // Start of a method/block
        private static double unpack(long value) {
            // Returns a value to the caller
            return Math.min((double) (value & DATA_BITS_MASK), MAX_QUANTIZED_VALUE) * 2.0 / MAX_QUANTIZED_VALUE - 1.0;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record QuaternionType() implements NetworkBufferTypeImpl<float[]> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, float[] value) {
            // Calls a method
            buffer.write(FLOAT, value[0]);
            // Calls a method
            buffer.write(FLOAT, value[1]);
            // Calls a method
            buffer.write(FLOAT, value[2]);
            // Calls a method
            buffer.write(FLOAT, value[3]);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public float[] read(NetworkBuffer buffer) {
            // Calls a method
            final float x = buffer.read(FLOAT);
            // Calls a method
            final float y = buffer.read(FLOAT);
            // Calls a method
            final float z = buffer.read(FLOAT);
            // Calls a method
            final float w = buffer.read(FLOAT);
            // Returns a value to the caller
            return new float[]{x, y, z, w};
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Combinators

    // Type declaration (class/interface/enum/record)
    record EnumSetType<E extends Enum<E>>(Class<E> enumType,
                                          // Start of a method/block
                                          E[] values, Type<BitSet> bitSetType) implements Type<EnumSet<E>> {
        // Start of a method/block
        public EnumSetType {
            // Calls a method
            Objects.requireNonNull(enumType, "enumType");
            // Calls a method
            Objects.requireNonNull(values, "values");
            // Calls a method
            Objects.requireNonNull(bitSetType, "bitSetType");
        // End of a block/expression
        }

        // Start of a method/block
        public EnumSetType(Class<E> enumClass, E[] values) {
            // Calls a method
            this(enumClass, values, FixedBitSet(values.length));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, EnumSet<E> value) {
            // Calls a method
            BitSet bitSet = new BitSet(values.length);
            // Loop: repeats a block
            for (int i = 0; i < values.length; ++i) {
                // Calls a method
                bitSet.set(i, value.contains(values[i]));
            // End of a block/expression
            }
            // Calls a method
            bitSetType.write(buffer, bitSet);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public EnumSet<E> read(NetworkBuffer buffer) {
            // Calls a method
            final BitSet bitSet = bitSetType.read(buffer);
            // Calls a method
            EnumSet<E> enumSet = EnumSet.noneOf(enumType);
            // Loop: repeats a block
            for (int i = 0; i < values.length; ++i) {
                // Branch: checks a condition
                if (bitSet.get(i)) {
                    // Calls a method
                    enumSet.add(values[i]);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Returns a value to the caller
            return enumSet;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record FixedBitSetType(int length, Type<byte[]> arrayType) implements Type<BitSet> {
        // Start of a method/block
        public FixedBitSetType {
            // Calls a method
            Check.argCondition(length < 0, "Length is negative found {0}", length);
            // Calls a method
            Objects.requireNonNull(arrayType, "arrayType");
        // End of a block/expression
        }

        // Start of a method/block
        public FixedBitSetType(int length) {
            // Calls a method
            this(length, FixedRawBytes((length + 7) / Long.BYTES));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, BitSet value) {
            // Branch: checks a condition
            if (value.length() > length) {
                // Throws an exception
                throw new IllegalArgumentException("BitSet is larger than expected size (" + value.length() + ">" + length + ")");
            // End of a block/expression
            }
            // Calls a method
            byte[] array = value.toByteArray();
            // Calls a method
            final int length = (this.length + 7) / Long.BYTES;
            // Branch: checks a condition
            if (array.length != length) {
                // Calls a method
                array = Arrays.copyOf(array, length);
            // End of a block/expression
            }
            // Calls a method
            arrayType.write(buffer, array);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public BitSet read(NetworkBuffer buffer) {
            // Calls a method
            final byte[] array = arrayType.read(buffer);
            // Returns a value to the caller
            return BitSet.valueOf(array);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OptionalType<T>(Type<T> parent) implements NetworkBufferTypeImpl<@Nullable T> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Calls a method
            buffer.write(BOOLEAN, value != null);
            // Branch: checks a condition
            if (value != null) buffer.write(parent, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return buffer.read(BOOLEAN) ? buffer.read(parent) : null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LengthPrefixedType<T>(Type<T> parent, int maxLength) implements NetworkBufferTypeImpl<T> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Write to another buffer and copy (kinda inefficient, but currently unused serverside so its ok for now)
            // Calls a method
            final byte[] componentData = NetworkBuffer.makeArray(b -> parent.write(b, value), buffer.registries());
            // Calls a method
            buffer.write(NetworkBuffer.BYTE_ARRAY, componentData);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Calls a method
            final int length = buffer.read(VAR_INT);
            // Calls a method
            Check.argCondition(length > maxLength, "Value is too long (length: {0}, max: {1})", length, maxLength);

            // Calls a method
            final long availableBytes = buffer.readableBytes();
            // Calls a method
            Check.argCondition(length > availableBytes, "Value is too long (length: {0}, available: {1})", length, availableBytes);
            // Calls a method
            final T value = parent.read(buffer);
            // Calls a method
            Check.argCondition(buffer.readableBytes() != availableBytes - length, "Value is too short (length: {0}, available: {1})", length, availableBytes);

            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record MaxLength<T>(Type<T> parent, long maxLength) implements NetworkBufferTypeImpl<T> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Calls a method
            final long length = parent.sizeOf(value);
            // Calls a method
            Check.argCondition(length > maxLength, "Value is too long (length: {0}, max: {1})", length, maxLength);
            // Calls a method
            buffer.write(parent, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Calls a method
            final long index = buffer.readIndex();
            // Calls a method
            final T value = parent.read(buffer);
            // Calls a method
            final long length = buffer.readIndex() - index;
            // Calls a method
            Check.argCondition(length > maxLength, "Value is too long (length: {0}, max: {1})", length, maxLength);
            // Returns a value to the caller
            return value;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class LazyType<T> implements NetworkBufferTypeImpl<T> {
        // Code statement
        private final Supplier<NetworkBuffer.Type<T>> supplier;
        // Code statement
        private Type<T> type;

        // Start of a method/block
        public LazyType(Supplier<NetworkBuffer.Type<T>> supplier) {
            // Access to the current/parent object
            this.supplier = supplier;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Branch: checks a condition
            if (type == null) type = supplier.get();
            // Calls a method
            type.write(buffer, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Branch: checks a condition
            if (type == null) type = supplier.get();
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class RecursiveType<T> implements NetworkBufferTypeImpl<T> {
        // Code statement
        final Type<T> delegate;

        // Start of a method/block
        public RecursiveType(Function<Type<T>, Type<T>> self) {
            // Calls a method
            Objects.requireNonNull(self, "self");
            // Access to the current/parent object
            this.delegate = Objects.requireNonNull(self.apply(this), "delegate");
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Calls a method
            delegate.write(buffer, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return delegate.read(buffer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TypedNbtType<T>(Codec<T> nbtType) implements NetworkBufferTypeImpl<T> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Calls a method
            final Registries registries = impl(buffer).registries;
            // Calls a method
            Check.stateCondition(registries == null, "Buffer does not have registries");
            // Calls a method
            final Result<BinaryTag> result = nbtType.encode(new RegistryTranscoder<>(Transcoder.NBT, registries), value);
            // Multiple branching (switch/case)
            switch (result) {
                // Multiple branching (switch/case)
                case Result.Ok(BinaryTag tag) -> buffer.write(NBT, tag);
                // Multiple branching (switch/case)
                case Result.Error(String message) -> throw new IllegalArgumentException("Invalid NBT tag: " + message);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Calls a method
            final Registries registries = impl(buffer).registries;
            // Calls a method
            Check.stateCondition(registries == null, "Buffer does not have registries");
            // Calls a method
            final Result<T> result = nbtType.decode(new RegistryTranscoder<>(Transcoder.NBT, registries), buffer.read(NBT));
            // Returns a value to the caller
            return switch (result) {
                // Multiple branching (switch/case)
                case Result.Ok(T value) -> value;
                // Multiple branching (switch/case)
                case Result.Error(String message) -> throw new IllegalArgumentException("Invalid NBT tag: " + message);
            // End of a block/expression
            };
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record EitherType<L, R>(
            // Code statement
            NetworkBuffer.Type<L> left,
            // Code statement
            NetworkBuffer.Type<R> right
    // Start of a method/block
    ) implements NetworkBuffer.Type<Either<L, R>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Either<L, R> value) {
            // Multiple branching (switch/case)
            switch (value) {
                // Multiple branching (switch/case)
                case Either.Left(L leftValue) -> {
                    // Calls a method
                    buffer.write(BOOLEAN, true);
                    // Calls a method
                    buffer.write(left, leftValue);
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case Either.Right(R rightValue) -> {
                    // Calls a method
                    buffer.write(BOOLEAN, false);
                    // Calls a method
                    buffer.write(right, rightValue);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Either<L, R> read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return buffer.read(BOOLEAN)
                    // Code statement
                    ? Either.left(buffer.read(left))
                    // Calls a method
                    : Either.right(buffer.read(right));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TransformType<T, S>(Type<T> parent,
                               // Code statement
                               Function<T, S> to,
                               // Start of a method/block
                               Function<S, T> from) implements NetworkBufferTypeImpl<S> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, S value) {
            // Calls a method
            parent.write(buffer, from.apply(value));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public S read(NetworkBuffer buffer) {
            // Returns a value to the caller
            return to.apply(parent.read(buffer));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record MapType<K, V>(Type<K> parent, NetworkBuffer.Type<V> valueType,
                         // Start of a method/block
                         int maxSize) implements NetworkBufferTypeImpl<Map<K, V>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Map<K, V> map) {
            // Calls a method
            buffer.write(VAR_INT, map.size());
            // Loop: repeats a block
            for (Map.Entry<K, V> entry : map.entrySet()) {
                // Calls a method
                buffer.write(parent, entry.getKey());
                // Calls a method
                buffer.write(valueType, entry.getValue());
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Map<K, V> read(NetworkBuffer buffer) {
            // Calls a method
            final int size = buffer.read(VAR_INT);
            // Calls a method
            Check.argCondition(size > maxSize, "Map size ({0}) is higher than the maximum allowed size ({1})", size, maxSize);
            // Calls a method
            K[] keys = (K[]) new Object[size];
            // Calls a method
            V[] values = (V[]) new Object[size];
            // Loop: repeats a block
            for (int i = 0; i < size; i++) {
                // Calls a method
                keys[i] = buffer.read(parent);
                // Calls a method
                values[i] = buffer.read(valueType);
            // End of a block/expression
            }
            // Returns a value to the caller
            return Map.copyOf(new Object2ObjectArrayMap<>(keys, values, size));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record ListType<T>(Type<T> parent, int maxSize) implements NetworkBufferTypeImpl<List<T>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, List<T> values) {
            // Branch: checks a condition
            if (values == null) { buffer.write(BYTE, (byte) 0); return; }
            // Calls a method
            buffer.write(VAR_INT, values.size());
            // Loop: repeats a block
            for (T value : values) buffer.write(parent, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<T> read(NetworkBuffer buffer) {
            // Calls a method
            final int size = buffer.read(VAR_INT);
            // Calls a method
            Check.argCondition(size > maxSize, "Collection size ({0}) > max ({1})", size, maxSize);
            // Calls a method
            T[] values = (T[]) new Object[size];
            // Loop: repeats a block
            for (int i = 0; i < size; i++) values[i] = buffer.read(parent);
            // Returns a value to the caller
            return List.of(values);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record SetType<T>(Type<T> parent, int maxSize) implements NetworkBufferTypeImpl<Set<T>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Set<T> values) {
            // Branch: checks a condition
            if (values == null) { buffer.write(BYTE, (byte) 0); return; }
            // Calls a method
            buffer.write(VAR_INT, values.size());
            // Loop: repeats a block
            for (T v : values) buffer.write(parent, v);
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Set<T> read(NetworkBuffer buffer) {
            // Calls a method
            final int size = buffer.read(VAR_INT);
            // Calls a method
            Check.argCondition(size > maxSize, "Collection size ({0}) > max ({1})", size, maxSize);
            // Calls a method
            T[] values = (T[]) new Object[size];
            // Loop: repeats a block
            for (int i = 0; i < size; i++) values[i] = buffer.read(parent);
            // Returns a value to the caller
            return Set.of(values);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record UnionType<T, K, TR extends T>(
            // Code statement
            Type<K> keyType, Function<T, ? extends K> keyFunc,
            // Code statement
            Function<K, NetworkBuffer.Type<TR>> serializers
    // Start of a method/block
    ) implements NetworkBufferTypeImpl<T> {

        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Much nicer than using the correct wildcard type for returns, pretty much ensuring T has subtypes already.
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Calls a method
            final K key = keyFunc.apply(value);
            // Calls a method
            buffer.write(keyType, key);
            // Calls a method
            final var ser = serializers.apply(key);
            // Branch: checks a condition
            if (ser == null) throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Calls a method
            ser.write(buffer, (TR) value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Calls a method
            final K key = buffer.read(keyType);
            // Calls a method
            final var ser = serializers.apply(key);
            // Branch: checks a condition
            if (ser == null) throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Returns a value to the caller
            return ser.read(buffer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record TaggedType<T, D>(
            // Code statement
            Type<D> discriminatorType, Function<? super T, ? extends D> discriminatorFromValue,
            // Code statement
            Map<? super D, Type<? extends T>> serializerMap, @Nullable Type<? extends T> fallback
    // Start of a method/block
    ) implements NetworkBufferTypeImpl<T> {
        // Start of a method/block
        public TaggedType {
            // Calls a method
            Objects.requireNonNull(discriminatorType, "discriminatorType");
            // Calls a method
            Objects.requireNonNull(discriminatorFromValue, "discriminatorFromValue");
            // Calls a method
            serializerMap = Map.copyOf(serializerMap);
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings("unchecked") // Likely fine here
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, T value) {
            // Calls a method
            final D key = discriminatorFromValue.apply(value);
            // Calls a method
            buffer.write(discriminatorType, key);
            // Calls a method
            var serializer = serializerMap.getOrDefault(key, fallback);
            // Branch: checks a condition
            if (serializer == null)
                // Throws an exception
                throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Calls a method
            ((Type<T>) serializer).write(buffer, value);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T read(NetworkBuffer buffer) {
            // Calls a method
            final D key = buffer.read(discriminatorType);
            // Calls a method
            var serializer = serializerMap.getOrDefault(key, fallback);
            // Branch: checks a condition
            if (serializer == null) throw new UnsupportedOperationException("Unrecognized type: " + key);
            // Returns a value to the caller
            return serializer.read(buffer);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record IOUTF8StringType() implements NetworkBufferTypeImpl<String> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, String value) {
            // Calls a method
            final int strlen = value.length();
            // Assigns a value
            int utflen = strlen; // optimized for ASCII

            // Loop: repeats a block
            for (int i = 0; i < strlen; i++) {
                // Calls a method
                int c = value.charAt(i);
                // Branch: checks a condition
                if (c >= 0x80 || c == 0) utflen += (c >= 0x800) ? 2 : 1;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (utflen > 65535 || /* overflow */ utflen < strlen)
                // Throws an exception
                throw new RuntimeException("UTF-8 string too long");

            // Calls a method
            buffer.write(SHORT, (short) utflen);
            // Calls a method
            buffer.ensureWritable(utflen);
            // Calls a method
            var impl = (NetworkBufferImpl) buffer;
            // Code statement
            int i;
            // Loop: repeats a block
            for (i = 0; i < strlen; i++) { // optimized for initial run of ASCII
                // Calls a method
                int c = value.charAt(i);
                // Branch: checks a condition
                if (c >= 0x80 || c == 0) break;
                // Calls a method
                impl._putByte(buffer.writeIndex(), (byte) c);
                // Calls a method
                impl.advanceWrite(1);
            // End of a block/expression
            }

            // Loop: repeats a block
            for (; i < strlen; i++) {
                // Calls a method
                int c = value.charAt(i);
                // Branch: checks a condition
                if (c < 0x80 && c != 0) {
                    // Calls a method
                    impl._putByte(buffer.writeIndex(), (byte) c);
                    // Calls a method
                    impl.advanceWrite(1);
                // Branch: checks a condition
                } else if (c >= 0x800) {
                    // Calls a method
                    impl._putByte(buffer.writeIndex(), (byte) (0xE0 | ((c >> 12) & 0x0F)));
                    // Calls a method
                    impl._putByte(buffer.writeIndex() + 1, (byte) (0x80 | ((c >> 6) & 0x3F)));
                    // Calls a method
                    impl._putByte(buffer.writeIndex() + 2, (byte) (0x80 | ((c >> 0) & 0x3F)));
                    // Calls a method
                    impl.advanceWrite(3);
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    impl._putByte(buffer.writeIndex(), (byte) (0xC0 | ((c >> 6) & 0x1F)));
                    // Calls a method
                    impl._putByte(buffer.writeIndex() + 1, (byte) (0x80 | ((c >> 0) & 0x3F)));
                    // Calls a method
                    impl.advanceWrite(2);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public String read(NetworkBuffer buffer) {
            // Calls a method
            final int utflen = buffer.read(UNSIGNED_SHORT);
            // Branch: checks a condition
            if (buffer.readableBytes() < utflen)
                // Throws an exception
                throw new IllegalArgumentException("Invalid String size.");
            // Calls a method
            final byte[] bytearr = buffer.read(FixedRawBytes(utflen));
            // Assigns a value
            final char[] chararr = new char[utflen];
            // Assigns a value
            int c, char2, char3, count = 0, chararr_count = 0;
            // Loop: repeats a block
            while (count < utflen) {
                // Assigns a value
                c = bytearr[count] & 0xFF;
                // Branch: checks a condition
                if (c > 127) break;
                // Code statement
                count++;
                // Calls a method
                chararr[chararr_count++] = (char) c;
            // End of a block/expression
            }

            // Loop: repeats a block
            while (count < utflen) {
                // Assigns a value
                c = bytearr[count] & 0xFF;
                // Exception handling
                try {
                    // Multiple branching (switch/case)
                    switch (c >> 4) {
                        // Multiple branching (switch/case)
                        case 0, 1, 2, 3, 4, 5, 6, 7 -> { count++; chararr[chararr_count++] = (char) c; }
                        // Multiple branching (switch/case)
                        case 12, 13 -> {
                            /* 110x xxxx   10xx xxxx*/
                            // Code statement
                            count += 2;
                            // Branch: checks a condition
                            if (count > utflen) throw new UTFDataFormatException("partial char at end");
                            // Assigns a value
                            char2 = bytearr[count - 1];
                            // Branch: checks a condition
                            if ((char2 & 0xC0) != 0x80) throw new UTFDataFormatException("malformed @" + count);
                            // Calls a method
                            chararr[chararr_count++] = (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
                        // End of a block/expression
                        }
                        // Multiple branching (switch/case)
                        case 14 -> {
                            /* 1110 xxxx  10xx xxxx  10xx xxxx */
                            // Code statement
                            count += 3;
                            // Branch: checks a condition
                            if (count > utflen) throw new UTFDataFormatException("partial char at end");
                            // Assigns a value
                            char2 = bytearr[count - 2];
                            // Assigns a value
                            char3 = bytearr[count - 1];
                            // Branch: checks a condition
                            if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                                // Throws an exception
                                throw new UTFDataFormatException("malformed @" + (count - 1));
                            // Calls a method
                            chararr[chararr_count++] = (char) (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | (char3 & 0x3F));
                        // End of a block/expression
                        }
                        // Multiple branching (switch/case)
                        default -> throw new UTFDataFormatException("malformed @" + count);
                    // End of a block/expression
                    }
                // Start of a method/block
                } catch (UTFDataFormatException e) {
                    // Throws an exception
                    throw new IllegalArgumentException(e);
                // End of a block/expression
                }
            // End of a block/expression
            }
            // The number of chars produced may be less than utflen
            // Returns a value to the caller
            return new String(chararr, 0, chararr_count);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    static <T> long sizeOf(Type<T> type, T value, Registries registries) {
        // Calls a method
        final NetworkBuffer dummy = NetworkBufferImpl.dummy(registries);
        // Calls a method
        type.write(dummy, value);
        // Returns a value to the caller
        return dummy.writeIndex();
    // End of a block/expression
    }
// End of a block/expression
}
