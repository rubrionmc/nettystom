// Déclaration du paquet de ce fichier
package net.minestom.server.advancements;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.item.Material;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class AdvancementIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void addAndRemoveViewer(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));

        // Affecte une valeur
        AdvancementRoot root = new AdvancementRoot(
                // Instruction de code
                Component.text("title"),
                // Instruction de code
                Component.text("description"),
                // Instruction de code
                Material.DIAMOND,
                // Instruction de code
                FrameType.TASK,
                // Instruction de code
                0,
                // Instruction de code
                0,
                // Instruction de code
                "minecraft:textures/block/stone.png"
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        AdvancementTab tab = env.process().advancement().createTab("minestom:minestom_tab", root);

        // Add viewer
        // Appelle une méthode
        tab.addViewer(player);
        // Appelle une méthode
        assertEquals(1, tab.getViewers().size());
        // Appelle une méthode
        assertTrue(tab.getViewers().contains(player));

        // Appelle une méthode
        assertNotNull(AdvancementTab.getTabs(player));
        // Appelle une méthode
        assertEquals(1, AdvancementTab.getTabs(player).size());
        // Appelle une méthode
        assertTrue(AdvancementTab.getTabs(player).contains(tab));

        // Remove viewer
        // Appelle une méthode
        tab.removeViewer(player);
        // Appelle une méthode
        assertEquals(0, tab.getViewers().size());

        // Appelle une méthode
        assertNull(AdvancementTab.getTabs(player));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void removeViewerOnDisconnect(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var player = env.createPlayer(instance, new Pos(0, 42, 0));

        // Affecte une valeur
        AdvancementRoot root1 = new AdvancementRoot(
                // Instruction de code
                Component.text("title"),
                // Instruction de code
                Component.text("description"),
                // Instruction de code
                Material.DIAMOND,
                // Instruction de code
                FrameType.TASK,
                // Instruction de code
                0,
                // Instruction de code
                0,
                // Instruction de code
                "minecraft:textures/block/stone.png"
        // Fin d'un bloc/d'une expression
        );

        // Affecte une valeur
        AdvancementRoot root2 = new AdvancementRoot(
                // Instruction de code
                Component.text("title2"),
                // Instruction de code
                Component.text("description"),
                // Instruction de code
                Material.DIAMOND,
                // Instruction de code
                FrameType.TASK,
                // Instruction de code
                0,
                // Instruction de code
                0,
                // Instruction de code
                "minecraft:textures/block/stone.png"
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        AdvancementTab tab1 = env.process().advancement().createTab("minestom:minestom_tab1", root1);
        // Appelle une méthode
        AdvancementTab tab2 = env.process().advancement().createTab("minestom:minestom_tab2", root2);
        // Appelle une méthode
        tab1.addViewer(player);
        // Appelle une méthode
        tab2.addViewer(player);

        // Instruction de code
        player.remove(); // Disconnect
        // Appelle une méthode
        assertEquals(0, tab1.getViewers().size());
        // Appelle une méthode
        assertEquals(0, tab2.getViewers().size());
        // Appelle une méthode
        assertNull(AdvancementTab.getTabs(player));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
