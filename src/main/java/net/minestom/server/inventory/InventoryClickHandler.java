// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.inventory.InventoryClickEvent;
// Import of a required class
import net.minestom.server.inventory.click.Click;
// Import of a required class
import net.minestom.server.inventory.click.ClickType;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Import of a required class
import java.util.List;

/**
 * Represents an inventory which can receive click input.
 * All methods returning boolean returns true if the action is successful, false otherwise.
 * <p>
 * See <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Click_Container">the Minecraft wiki</a> for more information.
 */
// Type declaration (class/interface/enum/record)
public sealed interface InventoryClickHandler permits AbstractInventory {

    /**
     * Parses a click. This delegates to each individual implementation method.
     *
     * @param player the player who clicked
     * @param click  the click that occurred
     * @return whether the click was a success
     */
    // Start of a method/block
    default boolean handleClick(Player player, Click click) {
        // Maps a click back into the click handler interface.
        // This is so that we can maintain normal
        // Returns a value to the caller
        return switch (click) {
            // Multiple branching (switch/case)
            case Click.Left(int slot) -> leftClick(player, slot);
            // Multiple branching (switch/case)
            case Click.Right(int slot) -> rightClick(player, slot);
            // Multiple branching (switch/case)
            case Click.Middle(int slot) -> middleClick(player, slot);

            // Multiple branching (switch/case)
            case Click.LeftShift(int slot) -> shiftClick(player, slot, 0);
            // Multiple branching (switch/case)
            case Click.RightShift(int slot) -> shiftClick(player, slot, 1);

            // Multiple branching (switch/case)
            case Click.Double(int slot) -> doubleClick(player, slot);

            // Multiple branching (switch/case)
            case Click.LeftDrag(List<Integer> slots) -> dragging(player, slots, 2);
            // Multiple branching (switch/case)
            case Click.RightDrag(List<Integer> slots) -> dragging(player, slots, 6);
            // Multiple branching (switch/case)
            case Click.MiddleDrag(List<Integer> slots) -> dragging(player, slots, 10);

            // Multiple branching (switch/case)
            case Click.LeftDropCursor() -> drop(player, true, -999);
            // Multiple branching (switch/case)
            case Click.RightDropCursor() -> drop(player, false, -999);
            // Multiple branching (switch/case)
            case Click.MiddleDropCursor() -> false; // Does nothing currently

            // Multiple branching (switch/case)
            case Click.DropSlot(int slot, boolean all) -> drop(player, all, slot);

            // Multiple branching (switch/case)
            case Click.HotbarSwap(int hotbarSlot, int slot) -> changeHeld(player, slot, hotbarSlot);

            // Multiple branching (switch/case)
            case Click.OffhandSwap(int slot) -> changeHeld(player, slot, PlayerInventoryUtils.OFFHAND_SLOT);
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Called when a {@link Player} left click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Calls a method
    boolean leftClick(Player player, int slot);

    /**
     * Called when a {@link Player} right click in the inventory. Can also be to drop the cursor item
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Calls a method
    boolean rightClick(Player player, int slot);

    /**
     * Called when a {@link Player} shift click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @param button the button (same behaviour in vanilla, but can be used for custom behaviour)
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Calls a method
    boolean shiftClick(Player player, int slot, int button);

    /**
     * Called when a {@link Player} held click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @param key    the held slot (0-8) pressed
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Calls a method
    boolean changeHeld(Player player, int slot, int key);

    // Calls a method
    boolean middleClick(Player player, int slot);

    /**
     * Called when a {@link Player} press the drop button
     *
     * @param player the player who clicked
     * @param all
     * @param slot   the slot number (-999 if clicking outside, i.e. dropping cursor)
     * @return true if the drop hasn't been cancelled, false otherwise
     */
    // Calls a method
    boolean drop(Player player, boolean all, int slot);

    // Calls a method
    boolean dragging(Player player, List<Integer> slots, int button);

    /**
     * Called when a {@link Player} double click in the inventory
     *
     * @param player the player who clicked
     * @param slot   the slot number
     * @return true if the click hasn't been cancelled, false otherwise
     */
    // Calls a method
    boolean doubleClick(Player player, int slot);

    // Code statement
    default void callClickEvent(Player player, AbstractInventory inventory, int slot,
                                // Start of a method/block
                                ClickType clickType, ItemStack clicked, ItemStack cursor) {
        // Calls a method
        EventDispatcher.call(new InventoryClickEvent(inventory, player, slot, clickType, clicked, cursor));
    // End of a block/expression
    }
// End of a block/expression
}
