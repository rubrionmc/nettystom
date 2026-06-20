// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record PotDecorations(
        // Code statement
        Material back,
        // Code statement
        Material left,
        // Code statement
        Material right,
        // Code statement
        Material front
// Start of a method/block
) {
    // Assigns a value
    public static final Material DEFAULT_ITEM = Material.BRICK;
    // Calls a method
    public static final PotDecorations EMPTY = new PotDecorations(DEFAULT_ITEM, DEFAULT_ITEM, DEFAULT_ITEM, DEFAULT_ITEM);

    // Calls a method
    public static final NetworkBuffer.Type<PotDecorations> NETWORK_TYPE = Material.NETWORK_TYPE.list(4).transform(PotDecorations::new, PotDecorations::asList);
    // Calls a method
    public static final Codec<PotDecorations> NBT_TYPE = Material.CODEC.list(4).transform(PotDecorations::new, PotDecorations::asList);

    // Start of a method/block
    public PotDecorations(List<Material> list) {
        // Calls a method
        this(getOrAir(list, 0), getOrAir(list, 1), getOrAir(list, 2), getOrAir(list, 3));
    // End of a block/expression
    }

    // Start of a method/block
    public PotDecorations(Material material) {
        // Calls a method
        this(material, material, material, material);
    // End of a block/expression
    }

    // Start of a method/block
    public List<Material> asList() {
        // Returns a value to the caller
        return List.of(back, left, right, front);
    // End of a block/expression
    }

    // Start of a method/block
    private static Material getOrAir(List<Material> list, int index) {
        // Returns a value to the caller
        return index < list.size() ? list.get(index) : Material.BRICK;
    // End of a block/expression
    }
// End of a block/expression
}
