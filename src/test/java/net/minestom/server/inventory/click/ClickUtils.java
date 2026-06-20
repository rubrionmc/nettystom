// Package declaration for this file
package net.minestom.server.inventory.click;

// Import of a required class
import net.minestom.server.inventory.Inventory;
// Import of a required class
import net.minestom.server.inventory.InventoryType;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public final class ClickUtils {
    // Assigns a value
    public static final InventoryType TYPE = InventoryType.HOPPER;

    // Assigns a value
    public static final int SIZE = TYPE.getSize(); // Default hopper size

    // Start of a method/block
    public static Inventory createInventory() {
        // Returns a value to the caller
        return new Inventory(TYPE, "TestInventory");
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertProcessed(ClickPreprocessor preprocessor, @Nullable Click info, ClientClickWindowPacket packet) {
        // Calls a method
        assertEquals(info, preprocessor.processClick(packet, SIZE));
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertProcessed(@Nullable Click info, ClientClickWindowPacket packet) {
        // Calls a method
        assertProcessed(new ClickPreprocessor(), info, packet);
    // End of a block/expression
    }

    // Start of a method/block
    public static ClientClickWindowPacket clickPacket(ClientClickWindowPacket.ClickType type, int windowId, int button, int slot) {
        // Returns a value to the caller
        return new ClientClickWindowPacket((byte) windowId, 0, (short) slot, (byte) button, type, Map.of(), ItemStack.Hash.AIR);
    // End of a block/expression
    }
// End of a block/expression
}