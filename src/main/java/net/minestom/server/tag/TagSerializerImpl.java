// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
final class TagSerializerImpl {
    // Affecte une valeur
    public static final TagSerializer<CompoundBinaryTag> COMPOUND = new TagSerializer<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public CompoundBinaryTag read(TagReadable reader) {
            // Renvoie une valeur à l'appelant
            return ((TagHandler) reader).asCompound();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(TagWritable writer, CompoundBinaryTag value) {
            // Appelle une méthode
            TagNbtSeparator.separate(value, entry -> writer.setTag(entry.tag(), entry.value()));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    static <T> TagSerializer<T> fromCompound(Function<CompoundBinaryTag, T> readFunc, Function<T, CompoundBinaryTag> writeFunc) {
        // Renvoie une valeur à l'appelant
        return new TagSerializer<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public @Nullable T read(TagReadable reader) {
                // Appelle une méthode
                final CompoundBinaryTag compound = COMPOUND.read(reader);
                // Renvoie une valeur à l'appelant
                return readFunc.apply(compound);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(TagWritable writer, T value) {
                // Appelle une méthode
                final CompoundBinaryTag compound = writeFunc.apply(value);
                // Appelle une méthode
                COMPOUND.write(writer, compound);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
