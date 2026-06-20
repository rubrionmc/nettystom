// Déclaration du paquet de ce fichier
package net.minestom.server.recipe;


// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.recipe.display.SlotDisplay;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record Ingredient(List<Material> items) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<Ingredient> NETWORK_TYPE = NetworkBufferTemplate.template(
            // FIXME(1.21.2): This is really an ObjectSet, but currently ObjectSet does not properly support
            //  non-dynamic registry types. We need to improve how the tag system works generally.
            // Crée un nouvel objet
            new NetworkBuffer.Type<>() {
                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public void write(NetworkBuffer buffer, List<Material> value) {
                    // +1 because 0 indicates that an item tag name follows (in this case it does not).
                    // Appelle une méthode
                    buffer.write(VAR_INT, value.size() + 1);
                    // Boucle : répète un bloc
                    for (Material material : value) {
                        // Appelle une méthode
                        buffer.write(Material.NETWORK_TYPE, material);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }

                // Annotation pour l'élément suivant
                @Override
                // Début d'une méthode/d'un bloc
                public List<Material> read(NetworkBuffer buffer) {
                    // Appelle une méthode
                    int size = buffer.read(VAR_INT) - 1;
                    // Appelle une méthode
                    Check.notNull(size > Short.MAX_VALUE, "too many ingredients");
                    // Embranchement : vérifie une condition
                    if (size == -1) {
                        // Lève une exception
                        throw new UnsupportedOperationException("cannot read ingredient tags yet");
                    // Fin d'un bloc/d'une expression
                    }

                    // Affecte une valeur
                    final List<Material> materials = new ArrayList<>(size);
                    // Boucle : répète un bloc
                    for (int i = 0; i < size; i++)
                        // Appelle une méthode
                        materials.add(buffer.read(Material.NETWORK_TYPE));
                    // Renvoie une valeur à l'appelant
                    return materials;
                // Fin d'un bloc/d'une expression
                }
            // Instruction de code
            }, Ingredient::items,
            // Instruction de code
            Ingredient::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public Ingredient {
        // Appelle une méthode
        items = List.copyOf(items);
        // Appelle une méthode
        Check.argCondition(items.isEmpty(), "Ingredients can't be empty");
        // Appelle une méthode
        Check.argCondition(items.contains(Material.AIR), "Ingredient can't contain air");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Ingredient(Material ... items) {
        // Appelle une méthode
        this(List.of(items));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static @Nullable Ingredient fromSlotDisplay(SlotDisplay slotDisplay) {
        // Renvoie une valeur à l'appelant
        return switch (slotDisplay) {
            // Embranchement multiple (switch/case)
            case SlotDisplay.Item item -> new Ingredient(item.material());
            // Embranchement multiple (switch/case)
            case SlotDisplay.Tag ignored -> {
                // TODO: Support tags in ingredients (ObjectSet for non static registries)
                // Instruction de code
                yield null;
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            default -> null;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
