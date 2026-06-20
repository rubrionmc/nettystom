// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.inventory.click.ClickType;
// Import of a required class
import net.minestom.server.inventory.click.InventoryClickResult;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.server.play.OpenWindowPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.WindowPropertyPacket;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an inventory which can be viewed by a collection of {@link Player}.
 * <p>
 * You can create one with {@link Inventory#Inventory(InventoryType, String)} or by making your own subclass.
 * It can then be opened using {@link Player#openInventory(Inventory)}.
 */
// Type declaration (class/interface/enum/record)
public non-sealed class Inventory extends AbstractInventory {
    // Calls a method
    private static final AtomicInteger ID_COUNTER = new AtomicInteger();

    // Code statement
    private final byte id;
    // Code statement
    private final InventoryType inventoryType;
    // Code statement
    private Component title;

    // Code statement
    private final int offset;

    // Start of a method/block
    public Inventory(InventoryType inventoryType, Component title) {
        // Access to the current/parent object
        super(inventoryType.getSize());
        // Access to the current/parent object
        this.id = generateId();
        // Access to the current/parent object
        this.inventoryType = inventoryType;
        // Access to the current/parent object
        this.title = title;

        // Access to the current/parent object
        this.offset = getSize();
    // End of a block/expression
    }

    // Start of a method/block
    public Inventory(InventoryType inventoryType, String title) {
        // Calls a method
        this(inventoryType, Component.text(title));
    // End of a block/expression
    }

    // Start of a method/block
    private static byte generateId() {
        // Returns a value to the caller
        return (byte) ID_COUNTER.updateAndGet(i -> i + 1 >= 128 ? 1 : i + 1);
    // End of a block/expression
    }

    /**
     * Gets the inventory type.
     *
     * @return the inventory type
     */
    // Start of a method/block
    public InventoryType getInventoryType() {
        // Returns a value to the caller
        return inventoryType;
    // End of a block/expression
    }

    /**
     * Gets the inventory title.
     *
     * @return the inventory title
     */
    // Start of a method/block
    public Component getTitle() {
        // Returns a value to the caller
        return title;
    // End of a block/expression
    }

    /**
     * Changes the inventory title.
     *
     * @param title the new inventory title
     */
    // Start of a method/block
    public void setTitle(Component title) {
        // Access to the current/parent object
        this.title = title;
        // Re-open the inventory
        // Calls a method
        sendPacketToViewers(new OpenWindowPacket(getWindowId(), getInventoryType().getWindowType(), title));
        // Send inventory items
        // Calls a method
        update();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte getWindowId() {
        // Returns a value to the caller
        return id;
    // End of a block/expression
    }

    /**
     * This will not open the inventory for {@code player}, use {@link Player#openInventory(Inventory)}.
     *
     * @param player the viewer to add
     * @return true if the player has successfully been added
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addViewer(Player player) {
        // Branch: checks a condition
        if (!this.viewers.add(player)) return false;

        // Also send the open window packet
        // Calls a method
        player.sendPacket(new OpenWindowPacket(id, getInventoryType().getWindowType(), title));
        // Calls a method
        update(player);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * This will not close the inventory for {@code player}, use {@link Player#closeInventory()}.
     *
     * @param player the viewer to remove
     * @return true if the player has successfully been removed
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeViewer(Player player) {
        // Branch: checks a condition
        if (!super.removeViewer(player)) return false;

        // Calls a method
        player.getClickPreprocessor().clearCache();
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Gets the cursor item of a player.
     *
     * @deprecated normal inventories no longer store cursor items
     * @see <a href="https://github.com/Minestom/Minestom/pull/2294">the relevant PR</a>
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public ItemStack getCursorItem(Player player) {
        // Returns a value to the caller
        return player.getInventory().getCursorItem();
    // End of a block/expression
    }

    /**
     * Changes the cursor item of a player.
     *
     * @deprecated normal inventories no longer store cursor items
     * @see <a href="https://github.com/Minestom/Minestom/pull/2294">the relevant PR</a>
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setCursorItem(Player player, ItemStack cursorItem) {
        // Calls a method
        player.getInventory().setCursorItem(cursorItem);
    // End of a block/expression
    }

    /**
     * Sends a window property to all viewers.
     *
     * @param property the property to send
     * @param value    the value of the property
     * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Set_Container_Property">the Minecraft wiki</a>
     */
    // Start of a method/block
    protected void sendProperty(InventoryProperty property, short value) {
        // Calls a method
        sendPacketToViewers(new WindowPropertyPacket(getWindowId(), property.getProperty(), value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean leftClick(Player player, int slot) {
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final ItemStack cursor = playerInventory.getCursorItem();
        // Calls a method
        final boolean isInWindow = isClickInWindow(slot);
        // Assigns a value
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Assigns a value
        final AbstractInventory clickedInventory = isInWindow ? this : playerInventory;
        // Calls a method
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.leftClick(clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (isInWindow) {
            // Calls a method
            setItemStack(slot, clickResult.getClicked());
        // Alternative branch of the condition
        } else {
            // Calls a method
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // End of a block/expression
        }
        // Calls a method
        playerInventory.setCursorItem(clickResult.getCursor());
        // Calls a method
        callClickEvent(player, clickedInventory, slot, ClickType.LEFT_CLICK, clicked, cursor);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean rightClick(Player player, int slot) {
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final ItemStack cursor = playerInventory.getCursorItem();
        // Calls a method
        final boolean isInWindow = isClickInWindow(slot);
        // Assigns a value
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Calls a method
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Assigns a value
        final AbstractInventory clickedInventory = isInWindow ? this : playerInventory;
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.rightClick(clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (isInWindow) {
            // Calls a method
            setItemStack(slot, clickResult.getClicked());
        // Alternative branch of the condition
        } else {
            // Calls a method
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // End of a block/expression
        }
        // Calls a method
        playerInventory.setCursorItem(clickResult.getCursor());
        // Calls a method
        callClickEvent(player, clickedInventory, slot, ClickType.RIGHT_CLICK, clicked, cursor);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shiftClick(Player player, int slot, int button) {
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final boolean isInWindow = isClickInWindow(slot);
        // Assigns a value
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Calls a method
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Assigns a value
        final ItemStack cursor = playerInventory.getCursorItem(); // Isn't used in the algorithm

        // Code statement
        InventoryClickResult clickResult;
        // Branch: checks a condition
        if (isInWindow) {
            // The player shift-clicked an item in this GUI into their inventory.
            // Prioritize the hotbar (8->0), then their regular inventory (35->9).
            // Assigns a value
            clickResult = clickProcessor.shiftClick(
                    // Code statement
                    this, playerInventory,
                    // Code statement
                    8, 0, -1,
                    // Code statement
                    player, clickSlot, clicked, cursor);

            // Branch: checks a condition
            if (clickResult.isCancel()) {
                // Assigns a value
                clickResult = clickProcessor.shiftClick(
                        // Code statement
                        this, playerInventory,
                        // Code statement
                        playerInventory.getInnerSize() - 1, 0, -1,
                        // Code statement
                        player, clickSlot, clicked, cursor);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Assigns a value
            clickResult = clickProcessor.shiftClick(
                    // Code statement
                    playerInventory, this,
                    // Code statement
                    0, getInnerSize(), 1,
                    // Code statement
                    player, clickSlot, clicked, cursor);
        // End of a block/expression
        }

        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Branch: checks a condition
        if (isInWindow) {
            // Calls a method
            setItemStack(slot, clickResult.getClicked());
        // Alternative branch of the condition
        } else {
            // Calls a method
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // End of a block/expression
        }

        // Code statement
        updateAll(player); // FIXME: currently not properly client-predicted
        // Calls a method
        playerInventory.setCursorItem(clickResult.getCursor());
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean changeHeld(Player player, int slot, int key) {
        // Assigns a value
        final int convertedKey = key == 40 ? PlayerInventoryUtils.OFFHAND_SLOT : key;
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final boolean isInWindow = isClickInWindow(slot);
        // Assigns a value
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Calls a method
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Calls a method
        final ItemStack heldItem = playerInventory.getItemStack(convertedKey);
        // Assigns a value
        final AbstractInventory clickedInventory = isInWindow ? this : playerInventory;
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.changeHeld(clicked, heldItem);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (isInWindow) {
            // Calls a method
            setItemStack(slot, clickResult.getClicked());
        // Alternative branch of the condition
        } else {
            // Calls a method
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // End of a block/expression
        }
        // Calls a method
        playerInventory.setItemStack(convertedKey, clickResult.getCursor());
        // Calls a method
        callClickEvent(player, clickedInventory, slot, ClickType.CHANGE_HELD, clicked, playerInventory.getCursorItem());
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean middleClick(Player player, int slot) {
        // TODO
        // Calls a method
        update(player);
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean drop(Player player, boolean all, int slot) {
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final boolean isInWindow = isClickInWindow(slot);
        // Assigns a value
        final boolean outsideDrop = slot == -999;
        // Assigns a value
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Assigns a value
        final ItemStack clicked = outsideDrop ?
                // Calls a method
                ItemStack.AIR : (isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot));
        // Calls a method
        final ItemStack cursor = playerInventory.getCursorItem();
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.drop(player, all, clickSlot, clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        final ItemStack resultClicked = clickResult.getClicked();
        // Branch: checks a condition
        if (!outsideDrop && resultClicked != null) {
            // Branch: checks a condition
            if (isInWindow) {
                // Calls a method
                setItemStack(slot, resultClicked);
            // Alternative branch of the condition
            } else {
                // Calls a method
                playerInventory.setItemStack(clickSlot, resultClicked);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        playerInventory.setCursorItem(clickResult.getCursor());
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean dragging(Player player, List<Integer> slots, int button) {
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final ItemStack cursor = playerInventory.getCursorItem();

        // Calls a method
        final ItemStack clickResult = clickProcessor.dragging(player, this, slots, button, cursor);
        // Branch: checks a condition
        if (clickResult == null) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        playerInventory.setCursorItem(clickResult);
        // Code statement
        updateAll(player); // FIXME: currently not properly client-predicted
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean doubleClick(Player player, int slot) {
        // Calls a method
        final PlayerInventory playerInventory = player.getInventory();
        // Calls a method
        final boolean isInWindow = isClickInWindow(slot);
        // Assigns a value
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Assigns a value
        final ItemStack clicked = slot != -999 ?
                // Code statement
                (isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot)) :
                // Code statement
                ItemStack.AIR;
        // Calls a method
        final ItemStack cursor = playerInventory.getCursorItem();
        // Assigns a value
        final InventoryClickResult clickResult = clickProcessor.doubleClick(isInWindow ? this : playerInventory,
                // Code statement
                this, player, clickSlot, clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            updateAll(player);
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        playerInventory.setCursorItem(clickResult.getCursor());
        // Code statement
        updateAll(player); // FIXME: currently not properly client-predicted
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Start of a method/block
    private boolean isClickInWindow(int slot) {
        // Returns a value to the caller
        return slot < getSize();
    // End of a block/expression
    }

    // Start of a method/block
    private void updateAll(Player player) {
        // Calls a method
        player.getInventory().update();
        // Calls a method
        update(player);
    // End of a block/expression
    }
// End of a block/expression
}
