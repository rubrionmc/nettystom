// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.Click;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.ClickType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Represents an inventory which can receive click input.
 * All methods returning boolean returns true if the action is successful, false otherwise.
 * <p>
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Click_Container">the Minecraft wiki</a> for more information.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface InventoryClickHandler permits AbstractInventory {

    /**
     * Parses a click. This delegates to each individual implementation method.
     * @param player the player who clicked
     * @param click the click that occurred
     * @return whether or not the click was a success
     */
    // Début d'une méthode/d'un bloc
    default boolean handleClick(Player player, Click click) {
        // Maps a click back into the click handler interface.
        // This is so that we can maintain normal
        // Renvoie une valeur à l'appelant
        return switch (click) {
            // Embranchement multiple (switch/case)
            case Click.Left(int slot) -> leftClick(player, slot);
            // Embranchement multiple (switch/case)
            case Click.Right(int slot) -> rightClick(player, slot);
            // Embranchement multiple (switch/case)
            case Click.Middle(int slot) -> middleClick(player, slot);

            // Embranchement multiple (switch/case)
            case Click.LeftShift(int slot) -> shiftClick(player, slot, 0);
            // Embranchement multiple (switch/case)
            case Click.RightShift(int slot) -> shiftClick(player, slot, 1);

            // Embranchement multiple (switch/case)
            case Click.Double(int slot) -> doubleClick(player, slot);

            // Embranchement multiple (switch/case)
            case Click.LeftDrag(List<Integer> slots) -> dragging(player, slots, 2);
            // Embranchement multiple (switch/case)
            case Click.RightDrag(List<Integer> slots) -> dragging(player, slots,  6);
            // Embranchement multiple (switch/case)
            case Click.MiddleDrag(List<Integer> slots) -> dragging(player, slots, 10);

            // Embranchement multiple (switch/case)
            case Click.LeftDropCursor() -> drop(player, true, -999);
            // Embranchement multiple (switch/case)
            case Click.RightDropCursor() -> drop(player, false, -999);
            // Embranchement multiple (switch/case)
            case Click.MiddleDropCursor() -> false; // Does nothing currently

            // Embranchement multiple (switch/case)
            case Click.DropSlot(int slot, boolean all) -> drop(player, all, slot);

            // Embranchement multiple (switch/case)
            case Click.HotbarSwap(int hotbarSlot, int slot) -> changeHeld(player, slot, hotbarSlot);

            // Embranchement multiple (switch/case)
            case Click.OffhandSwap(int slot) -> changeHeld(player, slot, PlayerInventoryUtils.OFFHAND_SLOT);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a {@link Player} left click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Appelle une méthode
    boolean leftClick(Player player, int slot);

    /**
     * Called when a {@link Player} right click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Appelle une méthode
    boolean rightClick(Player player, int slot);

    /**
     * Called when a {@link Player} shift click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @param button the button (same behaviour in vanilla, but can be used for custom behaviour)
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Appelle une méthode
    boolean shiftClick(Player player, int slot, int button);

    /**
     * Called when a {@link Player} held click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @param key    the held slot (0-8) pressed
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Appelle une méthode
    boolean changeHeld(Player player, int slot, int key);

    // Appelle une méthode
    boolean middleClick(Player player, int slot);

    /**
     * Called when a {@link Player} press the drop button
     *
     * @param player the player who clicked
     * @param all
     * @param slot   the slot number (-999 if clicking outside, i.e. dropping cursor)
     * @return true if the drop hasn't been cancelled, false otherwise
     */
    // Appelle une méthode
    boolean drop(Player player, boolean all, int slot);

    // Appelle une méthode
    boolean dragging(Player player, List<Integer> slots, int button);

    /**
     * Called when a {@link Player} double click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Appelle une méthode
    boolean doubleClick(Player player, int slot);

    // Instruction de code
    default void callClickEvent(Player player, AbstractInventory inventory, int slot,
                                // Début d'une méthode/d'un bloc
                                ClickType clickType, ItemStack clicked, ItemStack cursor) {
        // Appelle une méthode
        EventDispatcher.call(new InventoryClickEvent(inventory, player, slot, clickType, clicked, cursor));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
