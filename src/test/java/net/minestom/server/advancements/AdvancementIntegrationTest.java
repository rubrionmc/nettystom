// Package declaration for this file
package net.minestom.server.advancements;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class AdvancementIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void addAndRemoveViewer(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));

        // Assigns a value
        AdvancementRoot root = new AdvancementRoot(
                // Code statement
                Component.text("title"),
                // Code statement
                Component.text("description"),
                // Code statement
                Material.DIAMOND,
                // Code statement
                FrameType.TASK,
                // Code statement
                0,
                // Code statement
                0,
                // Code statement
                "minecraft:textures/block/stone.png"
        // End of a block/expression
        );

        // Calls a method
        AdvancementTab tab = env.process().advancement().createTab("minestom:minestom_tab", root);

        // Add viewer
        // Calls a method
        tab.addViewer(player);
        // Calls a method
        assertEquals(1, tab.getViewers().size());
        // Calls a method
        assertTrue(tab.getViewers().contains(player));

        // Calls a method
        assertNotNull(AdvancementTab.getTabs(player));
        // Calls a method
        assertEquals(1, AdvancementTab.getTabs(player).size());
        // Calls a method
        assertTrue(AdvancementTab.getTabs(player).contains(tab));

        // Remove viewer
        // Calls a method
        tab.removeViewer(player);
        // Calls a method
        assertEquals(0, tab.getViewers().size());

        // Calls a method
        assertNull(AdvancementTab.getTabs(player));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void removeViewerOnDisconnect(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var player = env.createPlayer(instance, new Pos(0, 42, 0));

        // Assigns a value
        AdvancementRoot root1 = new AdvancementRoot(
                // Code statement
                Component.text("title"),
                // Code statement
                Component.text("description"),
                // Code statement
                Material.DIAMOND,
                // Code statement
                FrameType.TASK,
                // Code statement
                0,
                // Code statement
                0,
                // Code statement
                "minecraft:textures/block/stone.png"
        // End of a block/expression
        );

        // Assigns a value
        AdvancementRoot root2 = new AdvancementRoot(
                // Code statement
                Component.text("title2"),
                // Code statement
                Component.text("description"),
                // Code statement
                Material.DIAMOND,
                // Code statement
                FrameType.TASK,
                // Code statement
                0,
                // Code statement
                0,
                // Code statement
                "minecraft:textures/block/stone.png"
        // End of a block/expression
        );

        // Calls a method
        AdvancementTab tab1 = env.process().advancement().createTab("minestom:minestom_tab1", root1);
        // Calls a method
        AdvancementTab tab2 = env.process().advancement().createTab("minestom:minestom_tab2", root2);
        // Calls a method
        tab1.addViewer(player);
        // Calls a method
        tab2.addViewer(player);

        // Code statement
        player.remove(); // Disconnect
        // Calls a method
        assertEquals(0, tab1.getViewers().size());
        // Calls a method
        assertEquals(0, tab2.getViewers().size());
        // Calls a method
        assertNull(AdvancementTab.getTabs(player));
    // End of a block/expression
    }
// End of a block/expression
}
