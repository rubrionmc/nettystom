// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.IntList;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.inventory.click.ClickUtils.*;
// Import statique d'un membre
import static net.minestom.server.network.packet.client.play.ClientClickWindowPacket.ClickType.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ClickPreprocessorTest {

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        MinecraftServer.init();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPickupType() {
        // Appelle une méthode
        assertProcessed(new Click.LeftDropCursor(), clickPacket(PICKUP, 1, 0, -999));
        // Appelle une méthode
        assertProcessed(new Click.RightDropCursor(), clickPacket(PICKUP, 1, 1, -999));
        // Appelle une méthode
        assertProcessed(new Click.MiddleDropCursor(), clickPacket(CLONE, 1, 2, -999));

        // Appelle une méthode
        assertProcessed(new Click.Left(0), clickPacket(PICKUP, 1, 0, 0));
        // Appelle une méthode
        assertProcessed(new Click.Left(SIZE + 9), clickPacket(PICKUP, 1, 0, 5));
        // Appelle une méthode
        assertProcessed(null, clickPacket(PICKUP, 1, 0, 99));

        // Appelle une méthode
        assertProcessed(new Click.Right(0), clickPacket(PICKUP, 1, 1, 0));
        // Appelle une méthode
        assertProcessed(new Click.Right(SIZE + 9), clickPacket(PICKUP, 1, 1, 5));
        // Appelle une méthode
        assertProcessed(null, clickPacket(PICKUP, 1, 1, 99));

        // Appelle une méthode
        assertProcessed(null, clickPacket(PICKUP, 1, -1, 0));
        // Appelle une méthode
        assertProcessed(null, clickPacket(PICKUP, 1, 2, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testQuickMoveType() {
        // Appelle une méthode
        assertProcessed(new Click.LeftShift(0), clickPacket(QUICK_MOVE, 1, 0, 0));
        // Appelle une méthode
        assertProcessed(new Click.LeftShift(SIZE + 9), clickPacket(QUICK_MOVE, 1, 0, 5));
        // Appelle une méthode
        assertProcessed(null, clickPacket(QUICK_MOVE, 1, 0, -1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSwapType() {
        // Appelle une méthode
        assertProcessed(null, clickPacket(SWAP, 1, 0, -1));
        // Appelle une méthode
        assertProcessed(new Click.HotbarSwap(0, 2), clickPacket(SWAP, 1, 0, 2));
        // Appelle une méthode
        assertProcessed(new Click.HotbarSwap(8, 2), clickPacket(SWAP, 1, 8, 2));
        // Appelle une méthode
        assertProcessed(new Click.OffhandSwap(2), clickPacket(SWAP, 1, 40, 2));

        // Appelle une méthode
        assertProcessed(null, clickPacket(SWAP, 1, 9, 2));
        // Appelle une méthode
        assertProcessed(null, clickPacket(SWAP, 1, 39, 2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCloneType() {
        // Appelle une méthode
        assertProcessed(new Click.Middle(0), clickPacket(CLONE, 1, 0, 0));
        // Appelle une méthode
        assertProcessed(null, clickPacket(CLONE, 1, 0, -1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testThrowType() {
        // Appelle une méthode
        assertProcessed(new Click.LeftDropCursor(), clickPacket(THROW, 1, 0, -999));
        // Appelle une méthode
        assertProcessed(new Click.RightDropCursor(), clickPacket(THROW, 1, 1, -999));

        // Appelle une méthode
        assertProcessed(new Click.DropSlot(0, true), clickPacket(THROW, 1, 1, 0));

        // Appelle une méthode
        assertProcessed(new Click.DropSlot(0, false), clickPacket(THROW, 1, 0, 0));
        // Appelle une méthode
        assertProcessed(new Click.DropSlot(0, true), clickPacket(THROW, 1, 1, 0));

        // Appelle une méthode
        assertProcessed(new Click.DropSlot(1, false), clickPacket(THROW, 1, 0, 1));
        // Appelle une méthode
        assertProcessed(new Click.DropSlot(1, true), clickPacket(THROW, 1, 1, 1));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testQuickCraft() {
        // Appelle une méthode
        var processor = new ClickPreprocessor();

        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 0, 0));
        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 1, 0));
        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 1, 1));
        // Appelle une méthode
        assertProcessed(processor, new Click.LeftDrag(IntList.of(0, 1)), clickPacket(QUICK_CRAFT, 1, 2, -999));

        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 4, 0));
        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 5, 0));
        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 5, 1));
        // Appelle une méthode
        assertProcessed(processor, new Click.RightDrag(IntList.of(0, 1)), clickPacket(QUICK_CRAFT, 1, 6, -999));

        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 8, 0));
        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 9, 0));
        // Appelle une méthode
        assertProcessed(processor, null, clickPacket(QUICK_CRAFT, 1, 9, 1));
        // Appelle une méthode
        assertProcessed(processor, new Click.MiddleDrag(IntList.of(0, 1)), clickPacket(QUICK_CRAFT, 1, 10, -999));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testCreativeClicks() {
        // Appelle une méthode
        var processor = new ClickPreprocessor();

        // Appelle une méthode
        assertTrue(processor.isCreativeClick(new Click.Middle(0), false));
        // Appelle une méthode
        assertFalse(processor.isCreativeClick(new Click.Middle(0), true));

        // Appelle une méthode
        assertTrue(processor.isCreativeClick(new Click.MiddleDrag(List.of(1, 2)), false));
        // Appelle une méthode
        assertTrue(processor.isCreativeClick(new Click.MiddleDrag(List.of(1, 2)), true));

        // Appelle une méthode
        assertFalse(processor.isCreativeClick(new Click.Left(5), true));
        // Appelle une méthode
        assertFalse(processor.isCreativeClick(new Click.Right(5), true));
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}