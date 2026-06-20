// Package declaration for this file
package net.minestom.server.recipe;

// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.registry.Registries;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record Ingredient(RegistryTag<Material> tag) {
    // Assigns a value
    public static final NetworkBuffer.Type<Ingredient> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            RegistryTag.networkType(Registries::material), Ingredient::tag,
            // Code statement
            Ingredient::new
    // End of a block/expression
    );

    // Start of a method/block
    public Ingredient(Material... items) {
        // Calls a method
        this(List.of(items));
    // End of a block/expression
    }

    // Start of a method/block
    public Ingredient(List<Material> items) {
        // Calls a method
        Check.argCondition(items.isEmpty(), "Ingredients can't be empty");
        // Calls a method
        Check.argCondition(items.contains(Material.AIR), "Ingredient can't contain air");
        // Calls a method
        this(RegistryTag.direct(items));
    // End of a block/expression
    }
// End of a block/expression
}
