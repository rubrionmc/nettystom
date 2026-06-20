// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import net.minestom.server.tag.Tag;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public class CustomDataTest extends AbstractItemComponentTest<CustomData> {
    // This is not a test, but it creates a compile error if the component type is changed away,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<CustomData>> SHARED_COMPONENTS = List.of(
            // Instruction de code
            DataComponents.CUSTOM_DATA,
            // Instruction de code
            DataComponents.BUCKET_ENTITY_DATA
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<CustomData> component() {
        // Renvoie une valeur à l'appelant
        return SHARED_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, CustomData>> directReadWriteEntries() {
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                entry("simple", new CustomData(CompoundBinaryTag.builder()
                        // Instruction de code
                        .putString("hello", "world")
                        // Instruction de code
                        .put("nested", CompoundBinaryTag.builder()
                                // Instruction de code
                                .putInt("number", 42)
                                // Instruction de code
                                .build())
                        // Instruction de code
                        .build()))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void customDataTagPath() throws IOException {
        // Affecte une valeur
        final ItemStack item = ItemStack.builder(Material.STICK)
                // Instruction de code
                .set(Tag.Integer("num").path("test"), 5)
                // Appelle une méthode
                .build();
        // Appelle une méthode
        final String snbt = MinestomAdventure.tagStringIO().asString(item.get(DataComponents.CUSTOM_DATA).nbt());
        // Appelle une méthode
        assertEquals("{test:{num:5}}", snbt);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void typedCustomDataWrite() throws IOException {
        // Appelle une méthode
        var component = new TypedCustomData<>(EntityType.COD, CompoundBinaryTag.builder().putFloat("Health", 1.5f).build());
        // Appelle une méthode
        var nbt = TypedCustomData.codec(EntityType.CODEC).encode(Transcoder.NBT, component).orElseThrow();
        // Appelle une méthode
        final String snbt = MinestomAdventure.tagStringIO().asString(nbt);
        // Appelle une méthode
        assertEquals("{Health:1.5f,id:\"minecraft:cod\"}", snbt);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
