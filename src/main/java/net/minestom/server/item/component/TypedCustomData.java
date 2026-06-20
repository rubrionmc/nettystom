// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
public record TypedCustomData<T>(T type, CompoundBinaryTag nbt) implements TagReadable {

    // Début d'une méthode/d'un bloc
    public static <T> Codec<TypedCustomData<T>> codec(Codec<T> typeCodec) {
        // Renvoie une valeur à l'appelant
        return StructCodec.struct(
                // Instruction de code
                "id", typeCodec, TypedCustomData::type,
                // Instruction de code
                StructCodec.INLINE, Codec.NBT_COMPOUND, TypedCustomData::nbt,
                // Instruction de code
                TypedCustomData::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <T> NetworkBuffer.Type<TypedCustomData<T>> networkType(NetworkBuffer.Type<T> typeNetwork) {
        // Renvoie une valeur à l'appelant
        return NetworkBufferTemplate.template(
                // Instruction de code
                typeNetwork, TypedCustomData::type,
                // Instruction de code
                NetworkBuffer.NBT_COMPOUND, TypedCustomData::nbt,
                // Instruction de code
                TypedCustomData::new
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public TypedCustomData(T type, CompoundBinaryTag nbt) {
        // Accès à l'objet courant/parent
        this.type = type;
        // Accès à l'objet courant/parent
        this.nbt = nbt.remove("id");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <TT> @UnknownNullability TT getTag(Tag<TT> tag) {
        // Appelle une méthode
        final TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Renvoie une valeur à l'appelant
        return tagHandler.getTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <TT> TypedCustomData<T> withTag(Tag<TT> tag, TT value) {
        // Appelle une méthode
        TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Appelle une méthode
        tagHandler.setTag(tag, value);
        // Renvoie une valeur à l'appelant
        return new TypedCustomData<>(type, tagHandler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
