// Déclaration du paquet de ce fichier
package net.minestom.server.recipe;

// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.Registries;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record Ingredient(RegistryTag<Material> tag) {
    // Affecte une valeur
    public static final NetworkBuffer.Type<Ingredient> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            RegistryTag.networkType(Registries::material), Ingredient::tag,
            // Instruction de code
            Ingredient::new
    // Fin d'un bloc/d'une expression
    );

    // Début d'une méthode/d'un bloc
    public Ingredient(Material... items) {
        // Appelle une méthode
        this(List.of(items));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Ingredient(List<Material> items) {
        // Appelle une méthode
        Check.argCondition(items.isEmpty(), "Ingredients can't be empty");
        // Appelle une méthode
        Check.argCondition(items.contains(Material.AIR), "Ingredient can't contain air");
        // Appelle une méthode
        this(RegistryTag.direct(items));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
