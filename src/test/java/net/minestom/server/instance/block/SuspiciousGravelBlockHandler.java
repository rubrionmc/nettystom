// Déclaration du paquet de ce fichier
package net.minestom.server.instance.block;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class SuspiciousGravelBlockHandler implements BlockHandler {
    // Appelle une méthode
    public static final SuspiciousGravelBlockHandler INSTANCE = new SuspiciousGravelBlockHandler(true);
    // Appelle une méthode
    public static final SuspiciousGravelBlockHandler INSTANCE_NO_TAGS = new SuspiciousGravelBlockHandler(false);

    // Appelle une méthode
    public static final Tag<String> LOOT_TABLE = Tag.String("LootTable");
    // Appelle une méthode
    public static final Tag<ItemStack> ITEM = Tag.ItemStack("item");

    // Instruction de code
    private final boolean hasTags;

    // Début d'une méthode/d'un bloc
    public SuspiciousGravelBlockHandler(boolean hasTags) {
        // Accès à l'objet courant/parent
        this.hasTags = hasTags;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key getKey() {
        // Renvoie une valeur à l'appelant
        return Key.key("suspicious_gravel");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Collection<Tag<?>> getBlockEntityTags() {
        // Renvoie une valeur à l'appelant
        return hasTags ? List.of(LOOT_TABLE, ITEM) : List.of();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
