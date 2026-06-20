// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public record PotDecorations(
        // Instruction de code
        Material back,
        // Instruction de code
        Material left,
        // Instruction de code
        Material right,
        // Instruction de code
        Material front
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final Material DEFAULT_ITEM = Material.BRICK;
    // Appelle une méthode
    public static final PotDecorations EMPTY = new PotDecorations(DEFAULT_ITEM, DEFAULT_ITEM, DEFAULT_ITEM, DEFAULT_ITEM);

    // Appelle une méthode
    public static final NetworkBuffer.Type<PotDecorations> NETWORK_TYPE = Material.NETWORK_TYPE.list(4).transform(PotDecorations::new, PotDecorations::asList);
    // Appelle une méthode
    public static final Codec<PotDecorations> NBT_TYPE = Material.CODEC.list(4).transform(PotDecorations::new, PotDecorations::asList);

    // Début d'une méthode/d'un bloc
    public PotDecorations(List<Material> list) {
        // Appelle une méthode
        this(getOrAir(list, 0), getOrAir(list, 1), getOrAir(list, 2), getOrAir(list, 3));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PotDecorations(Material material) {
        // Appelle une méthode
        this(material, material, material, material);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Material> asList() {
        // Renvoie une valeur à l'appelant
        return List.of(back, left, right, front);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Material getOrAir(List<Material> list, int index) {
        // Renvoie une valeur à l'appelant
        return index < list.size() ? list.get(index) : Material.BRICK;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
