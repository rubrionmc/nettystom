// Déclaration du paquet de ce fichier
package net.minestom.demo.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.ListBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockHandler;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagSerializer;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagWritable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class CampfireHandler implements BlockHandler {

    // Affecte une valeur
    public static final Tag<List<ItemStack>> ITEMS = Tag.View(new TagSerializer<>() {
        // Appelle une méthode
        private final Tag<BinaryTag> internal = Tag.NBT("Items");

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable List<ItemStack> read(TagReadable reader) {
            // Appelle une méthode
            ListBinaryTag item = (ListBinaryTag) reader.getTag(internal);
            // Embranchement : vérifie une condition
            if (item == null)
                // Renvoie une valeur à l'appelant
                return null;
            // Appelle une méthode
            List<ItemStack> result = new ArrayList<>();
            // Début d'une méthode/d'un bloc
            item.forEach(childTag -> {
                // Appelle une méthode
                CompoundBinaryTag nbtCompound = (CompoundBinaryTag) childTag;
                // Appelle une méthode
                int amount = nbtCompound.getByte("Count");
                // Appelle une méthode
                String id = nbtCompound.getString("id");
                // Appelle une méthode
                Material material = Material.fromKey(id);
                // Appelle une méthode
                result.add(ItemStack.of(material, amount));
            // Fin d'un bloc/d'une expression
            });
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(TagWritable writer, @Nullable List<ItemStack> value) {
            // Embranchement : vérifie une condition
            if (value == null) {
                // Appelle une méthode
                writer.removeTag(internal);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            writer.setTag(internal, ListBinaryTag.listBinaryTag(
                    // Instruction de code
                    BinaryTagTypes.COMPOUND,
                    // Instruction de code
                    value.stream()
                            // Instruction de code
                            .map(item -> (BinaryTag) CompoundBinaryTag.builder()
                                    // Instruction de code
                                    .putByte("Count", (byte) item.amount())
                                    // Instruction de code
                                    .putByte("Slot", (byte) 1)
                                    // Instruction de code
                                    .putString("id", item.material().name())
                                    // Instruction de code
                                    .build())
                            // Instruction de code
                            .toList()
            // Instruction de code
            ));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    });

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Tag<?>> getBlockEntityTags() {
        // Renvoie une valeur à l'appelant
        return List.of(ITEMS);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key getKey() {
        // Renvoie une valeur à l'appelant
        return Key.key("minestom:test");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
