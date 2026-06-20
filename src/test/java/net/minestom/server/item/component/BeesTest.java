// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;

// Déclaration de type (classe/interface/enum/record)
public class BeesTest extends AbstractItemComponentTest<List<Bee>> {
    // Affecte une valeur
    private static final CustomData SOME_DATA = new CustomData(CompoundBinaryTag.builder()
            // Instruction de code
            .putString("Id", "minecraft:bee")
            // Appelle une méthode
            .build());

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<List<Bee>> component() {
        // Renvoie une valeur à l'appelant
        return DataComponents.BEES;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public List<Map.Entry<String, List<Bee>>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("empty", List.of()),
                // Instruction de code
                entry("single", List.of(new Bee(SOME_DATA, 1, 2))),
                // Instruction de code
                entry("multiple", List.of(new Bee(SOME_DATA, 1, 2), new Bee(SOME_DATA, 3, 4)))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
