// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;

// Type declaration (class/interface/enum/record)
public record SeededContainerLoot(String lootTable, long seed) {
    // Assigns a value
    public static final Codec<SeededContainerLoot> CODEC = StructCodec.struct(
            // Code statement
            "loot_table", Codec.STRING, SeededContainerLoot::lootTable,
            // Code statement
            "seed", Codec.LONG, SeededContainerLoot::seed,
            // Code statement
            SeededContainerLoot::new);
// End of a block/expression
}
