// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.InventoryType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.util.*;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Déclaration de type (classe/interface/enum/record)
public final class ClickUtils {
    // Affecte une valeur
    public static final InventoryType TYPE = InventoryType.HOPPER;

    // Affecte une valeur
    public static final int SIZE = TYPE.getSize(); // Default hopper size

    // Début d'une méthode/d'un bloc
    public static Inventory createInventory() {
        // Renvoie une valeur à l'appelant
        return new Inventory(TYPE, "TestInventory");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertProcessed(ClickPreprocessor preprocessor, @Nullable Click info, ClientClickWindowPacket packet) {
        // Appelle une méthode
        assertEquals(info, preprocessor.processClick(packet, SIZE));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertProcessed(@Nullable Click info, ClientClickWindowPacket packet) {
        // Appelle une méthode
        assertProcessed(new ClickPreprocessor(), info, packet);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ClientClickWindowPacket clickPacket(ClientClickWindowPacket.ClickType type, int windowId, int button, int slot) {
        // Renvoie une valeur à l'appelant
        return new ClientClickWindowPacket((byte) windowId, 0, (short) slot, (byte) button, type, Map.of(), ItemStack.Hash.AIR);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}