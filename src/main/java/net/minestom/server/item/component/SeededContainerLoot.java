// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;

// Déclaration de type (classe/interface/enum/record)
public record SeededContainerLoot(String lootTable, long seed) {
    // Affecte une valeur
    public static final Codec<SeededContainerLoot> CODEC = StructCodec.struct(
            // Instruction de code
            "loot_table", Codec.STRING, SeededContainerLoot::lootTable,
            // Instruction de code
            "seed", Codec.LONG, SeededContainerLoot::seed,
            // Instruction de code
            SeededContainerLoot::new);
// Fin d'un bloc/d'une expression
}
