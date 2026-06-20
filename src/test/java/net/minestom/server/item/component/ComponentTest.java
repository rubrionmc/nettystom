// Déclaration du paquet de ce fichier
package net.minestom.server.item.component;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.codec.Transcoder;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.minestom.server.codec.CodecAssertions.assertOk;

// Déclaration de type (classe/interface/enum/record)
public class ComponentTest extends AbstractItemComponentTest<Component> {
    // This is not a test, but it creates a compile error if the component type is changed away from Component,
    // as a reminder that tests should be added for that new component type.
    // Affecte une valeur
    private static final List<DataComponent<Component>> SHARED_COMPONENTS = List.of(
            // Instruction de code
            DataComponents.CUSTOM_NAME,
            // Instruction de code
            DataComponents.ITEM_NAME
    // Fin d'un bloc/d'une expression
    );

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected DataComponent<Component> component() {
        // Renvoie une valeur à l'appelant
        return SHARED_COMPONENTS.getFirst();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected List<Map.Entry<String, Component>> directReadWriteEntries() {
        // Component serialization is well tested elsewhere, this is just a sanity check really.
        // Renvoie une valeur à l'appelant
        return List.of(
                // Instruction de code
                Map.entry("empty component", Component.empty()),
                // Instruction de code
                Map.entry("text component", Component.text("Hello, world!"))
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    void testItemNameParseRegression() throws Exception {
        // Appelle une méthode
        var nbt = MinestomAdventure.tagStringIO().asTag("{translate: \"item.minecraft.diamond\"}");
        // Appelle une méthode
        var component = DataComponents.ITEM_NAME.decode(Transcoder.NBT, nbt);
        // Appelle une méthode
        assertOk(component);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
