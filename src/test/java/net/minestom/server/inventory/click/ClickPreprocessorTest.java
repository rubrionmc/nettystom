// Package declaration for this file
package net.minestom.server.inventory.click;

// Import of a required class
import it.unimi.dsi.fastutil.ints.IntList;
// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.inventory.click.ClickUtils.*;
// Static import of a member
import static net.minestom.server.network.packet.client.play.ClientClickWindowPacket.ClickType.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class ClickPreprocessorTest {

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPickupType() {
        // Calls a method
        assertProcessed(new Click.LeftDropCursor(), clickPacket(PICKUP, 1, 0, -999));
        // Calls a method
        assertProcessed(new Click.RightDropCursor(), clickPacket(PICKUP, 1, 1, -999));
        // Calls a method
        assertProcessed(new Click.MiddleDropCursor(), clickPacket(CLONE, 1, 2, -999));

        // Calls a method
        assertProcessed(new Click.Left(0), clickPacket(PICKUP, 1, 0, 0));
        // Calls a method
        assertProcessed(new Click.Left(SIZE + 9), clickPacket(PICKUP, 1, 0, 5));
        // Calls a method
        assertProcessed(null, clickPacket(PICKUP, 1, 0, 99));

        // Calls a method
        assertProcessed(new Click.Right(0), clickPacket(PICKUP, 1, 1, 0));
        // Calls a method
        assertProcessed(new Click.Right(SIZE + 9), clickPacket(PICKUP, 1, 1, 5));
        // Calls a method
        assertProcessed(null, clickPacket(PICKUP, 1, 1, 99));

        // Calls a method
        assertProcessed(null, clickPacket(PICKUP, 1, -1, 0));
        // Calls a method
        assertProcessed(null, clickPacket(PICKUP, 1, 2, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testQuickMoveType() {
        // Calls a method
        assertProcessed(new Click.LeftShift(0), clickPacket(QUICK_MOVE, 1, 0, 0));
        // Calls a method
        assertProcessed(new Click.LeftShift(SIZE + 9), clickPacket(QUICK_MOVE, 1, 0, 5));
        // Calls a method
        assertProcessed(null, clickPacket(QUICK_MOVE, 1, 0, -1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testSwapType() {
        // Calls a method
        assertProcessed(null, clickPacket(SWAP, 1, 0, -1));
        // Calls a method
        assertProcessed(new Click.HotbarSwap(0, 2), clickPacket(SWAP, 1, 0, 2));
        // Calls a method
        assertProcessed(new Click.HotbarSwap(8, 2), clickPacket(SWAP, 1, 8, 2));
        // Calls a method
        assertProcessed(new Click.OffhandSwap(2), clickPacket(SWAP, 1, 40, 2));

        // Calls a method
        assertProcessed(null, clickPacket(SWAP, 1, 9, 2));
        // Calls a method
        assertProcessed(null, clickPacket(SWAP, 1, 39, 2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCloneType() {
        // Calls a method
        assertProcessed(new Click.Middle(0), clickPacket(CLONE, 1, 0, 0));
        // Calls a method
        assertProcessed(null, clickPacket(CLONE, 1, 0, -1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testThrowType() {
        // Calls a method
        assertProcessed(new Click.LeftDropCursor(), clickPacket(THROW, 1, 0, -999));
        // Calls a method
        assertProcessed(new Click.RightDropCursor(), clickPacket(THROW, 1, 1, -999));

        // Calls a method
        assertProcessed(new Click.DropSlot(0, true), clickPacket(THROW, 1, 1, 0));

        // Calls a method
        assertProcessed(new Click.DropSlot(0, false), clickPacket(THROW, 1, 0, 0));
        // Calls a method
        assertProcessed(new Click.DropSlot(0, true), clickPacket(THROW, 1, 1, 0));

        // Calls a method
        assertProcessed(new Click.DropSlot(1, false), clickPacket(THROW, 1, 0, 1));
        // Calls a method
        assertProcessed(new Click.DropSlot(1, true), clickPacket(THROW, 1, 1, 1));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testQuickCraft() {
        // Calls a method
        var processor = new ClickPreprocessor();

        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 0, 0));
        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 1, 0));
        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 1, 1));
        // Calls a method
        assertProcessed(processor, new Click.LeftDrag(IntList.of(0, 1)), clickPacket(QUICK_CRAFT, 1, 2, -999));

        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 4, 0));
        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 5, 0));
        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 5, 1));
        // Calls a method
        assertProcessed(processor, new Click.RightDrag(IntList.of(0, 1)), clickPacket(QUICK_CRAFT, 1, 6, -999));

        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 8, 0));
        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 9, 0));
        // Calls a method
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 9, 1));
        // Calls a method
        assertProcessed(processor, new Click.MiddleDrag(IntList.of(0, 1)), clickPacket(QUICK_CRAFT, 1, 10, -999));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testCreativeClicks() {
        // Calls a method
        var processor = new ClickPreprocessor();

        // Calls a method
        assertTrue(processor.isCreativeClick(new Click.Middle(0), false));
        // Calls a method
        assertFalse(processor.isCreativeClick(new Click.Middle(0), true));

        // Calls a method
        assertTrue(processor.isCreativeClick(new Click.MiddleDrag(List.of(1, 2)), false));
        // Calls a method
        assertTrue(processor.isCreativeClick(new Click.MiddleDrag(List.of(1, 2)), true));

        // Calls a method
        assertFalse(processor.isCreativeClick(new Click.Left(5), true));
        // Calls a method
        assertFalse(processor.isCreativeClick(new Click.Right(5), true));
    // End of a block/expression
    }

// End of a block/expression
}