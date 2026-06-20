// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

/**
 * Represents a type of {@link Inventory}
 */
// Déclaration de type (classe/interface/enum/record)
public enum InventoryType {

    // Instruction de code
    CHEST_1_ROW(9),
    // Instruction de code
    CHEST_2_ROW(18),
    // Instruction de code
    CHEST_3_ROW(27),
    // Instruction de code
    CHEST_4_ROW(36),
    // Instruction de code
    CHEST_5_ROW(45),
    // Instruction de code
    CHEST_6_ROW(54),
    // Instruction de code
    WINDOW_3X3(9),
    // Instruction de code
    CRAFTER_3X3(9),
    // Instruction de code
    ANVIL(3),
    // Instruction de code
    BEACON(1),
    // Instruction de code
    BLAST_FURNACE(3),
    // Instruction de code
    BREWING_STAND(5),
    // Instruction de code
    CRAFTING(10),
    // Instruction de code
    ENCHANTMENT(2),
    // Instruction de code
    FURNACE(3),
    // Instruction de code
    GRINDSTONE(3),
    // Instruction de code
    HOPPER(5),
    // Instruction de code
    LECTERN(1),
    // Instruction de code
    LOOM(4),
    // Instruction de code
    MERCHANT(3),
    // Instruction de code
    SHULKER_BOX(27),
    // Instruction de code
    SMITHING(4),
    // Instruction de code
    SMOKER(3),
    // Instruction de code
    CARTOGRAPHY(3),
    // Appelle une méthode
    STONE_CUTTER(2);

    // Instruction de code
    private final int size;

    // Début d'une méthode/d'un bloc
    InventoryType(int size) {
        // Accès à l'objet courant/parent
        this.size = size;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getWindowType() {
        // Renvoie une valeur à l'appelant
        return ordinal();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getSize() {
        // Renvoie une valeur à l'appelant
        return size;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link #getSize()}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public int getAdditionalSlot() {
        // Renvoie une valeur à l'appelant
        return size;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
