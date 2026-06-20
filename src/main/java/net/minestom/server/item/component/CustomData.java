// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
public record CustomData(CompoundBinaryTag nbt) implements TagReadable {
    // Appelle une méthode
    public static final CustomData EMPTY = new CustomData(CompoundBinaryTag.empty());

    // Affecte une valeur
    public static final NetworkBuffer.Type<CustomData> NETWORK_TYPE = NetworkBuffer.NBT_COMPOUND
            // Appelle une méthode
            .transform(CustomData::new, CustomData::nbt);

    // Affecte une valeur
    public static final Codec<CustomData> CODEC = Codec.NBT_COMPOUND
            // Appelle une méthode
            .transform(CustomData::new, CustomData::nbt);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T> @UnknownNullability T getTag(Tag<T> tag) {
        // Appelle une méthode
        final TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Renvoie une valeur à l'appelant
        return tagHandler.getTag(tag);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T> CustomData withTag(Tag<T> tag, T value) {
        // Appelle une méthode
        TagHandler tagHandler = TagHandler.fromCompound(nbt);
        // Appelle une méthode
        tagHandler.setTag(tag, value);
        // Renvoie une valeur à l'appelant
        return new CustomData(tagHandler.asCompound());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
