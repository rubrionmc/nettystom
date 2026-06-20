// Package declaration for this file
package net.minestom.server.tag;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
final class TagSerializerImpl {
    // Assigns a value
    public static final TagSerializer<CompoundBinaryTag> COMPOUND = new TagSerializer<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public CompoundBinaryTag read(TagReadable reader) {
            // Returns a value to the caller
            return ((TagHandler) reader).asCompound();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(TagWritable writer, CompoundBinaryTag value) {
            // Calls a method
            TagNbtSeparator.separate(value, entry -> writer.setTag(entry.tag(), entry.value()));
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    static <T> TagSerializer<T> fromCompound(Function<CompoundBinaryTag, T> readFunc, Function<T, CompoundBinaryTag> writeFunc) {
        // Returns a value to the caller
        return new TagSerializer<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public @Nullable T read(TagReadable reader) {
                // Calls a method
                final CompoundBinaryTag compound = COMPOUND.read(reader);
                // Returns a value to the caller
                return readFunc.apply(compound);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(TagWritable writer, T value) {
                // Calls a method
                final CompoundBinaryTag compound = writeFunc.apply(value);
                // Calls a method
                COMPOUND.write(writer, compound);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
