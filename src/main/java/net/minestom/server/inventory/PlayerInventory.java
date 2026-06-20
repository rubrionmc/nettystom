// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.item.EntityEquipEvent;
// Import of a required class
import net.minestom.server.inventory.click.ClickType;
// Import of a required class
import net.minestom.server.inventory.click.InventoryClickResult;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetCursorItemPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetPlayerInventorySlotPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetSlotPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.WindowItemsPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;

/**
 * Represents the inventory of a {@link Player}, retrieved with {@link Player#getInventory()}.
 */
// Type declaration (class/interface/enum/record)
public non-sealed class PlayerInventory extends AbstractInventory {
    // Assigns a value
    public static final int INVENTORY_SIZE = 46;
    // Assigns a value
    public static final int INNER_INVENTORY_SIZE = 36;

    // Assigns a value
    private ItemStack cursorItem = ItemStack.AIR;

    // Start of a method/block
    public PlayerInventory() {
        // Access to the current/parent object
        super(INVENTORY_SIZE);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void clear() {
        // Assigns a value
        cursorItem = ItemStack.AIR;
        // Access to the current/parent object
        super.clear();

        // Update equipments
        // Calls a method
        viewers.forEach(viewer -> viewer.sendPacketToViewersAndSelf(viewer.getEquipmentsPacket()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getInnerSize() {
        // Returns a value to the caller
        return INNER_INVENTORY_SIZE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte getWindowId() {
        // Returns a value to the caller
        return 0;
    // End of a block/expression
    }

    // Start of a method/block
    private int getSlotId(EquipmentSlot slot, byte heldSlot) {
        // Returns a value to the caller
        return switch (slot) {
            // Multiple branching (switch/case)
            case MAIN_HAND -> heldSlot;
            // Multiple branching (switch/case)
            case OFF_HAND -> OFFHAND_SLOT;
            // Multiple branching (switch/case)
            default -> slot.armorSlot();
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private @Nullable EquipmentSlot getEquipmentSlot(int slot, byte heldSlot) {
        // Returns a value to the caller
        return switch (slot) {
            // Multiple branching (switch/case)
            case OFFHAND_SLOT -> EquipmentSlot.OFF_HAND;
            // Multiple branching (switch/case)
            case HELMET_SLOT -> EquipmentSlot.HELMET;
            // Multiple branching (switch/case)
            case CHESTPLATE_SLOT -> EquipmentSlot.CHESTPLATE;
            // Multiple branching (switch/case)
            case LEGGINGS_SLOT -> EquipmentSlot.LEGGINGS;
            // Multiple branching (switch/case)
            case BOOTS_SLOT -> EquipmentSlot.BOOTS;
            // Multiple branching (switch/case)
            default -> slot == heldSlot ? EquipmentSlot.MAIN_HAND : null;
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getEquipment(EquipmentSlot slot, byte heldSlot) {
        // Calls a method
        final int slotId = getSlotId(slot, heldSlot);
        // Branch: checks a condition
        if (slotId < 0) return ItemStack.AIR;
        // Returns a value to the caller
        return getItemStack(slotId);
    // End of a block/expression
    }

    // Start of a method/block
    public void setEquipment(EquipmentSlot slot, byte heldSlot, ItemStack itemStack) {
        // Calls a method
        final int slotId = getSlotId(slot, heldSlot);
        // Branch: checks a condition
        if (slotId < 0) Check.fail("PlayerInventory does not support {0} equipment", slot);

        // Calls a method
        setItemStack(slotId, itemStack);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void update(Player player) {
        // Calls a method
        player.sendPacket(createWindowItemsPacket());
    // End of a block/expression
    }

    /**
     * Gets the item in player cursor.
     *
     * @return the cursor item
     */
    // Start of a method/block
    public ItemStack getCursorItem() {
        // Returns a value to the caller
        return cursorItem;
    // End of a block/expression
    }

    /**
     * Changes the player cursor item.
     *
     * @param cursorItem the new cursor item
     */
    // Start of a method/block
    public void setCursorItem(ItemStack cursorItem) {
        // Calls a method
        setCursorItem(cursorItem, true);
    // End of a block/expression
    }

    /**
     * Changes the player cursor item.
     *
     * @param cursorItem the new cursor item
     * @param sendPacket true to send the update packet to the client, false otherwise
     */
    // Start of a method/block
    public void setCursorItem(ItemStack cursorItem, boolean sendPacket) {
        // Branch: checks a condition
        if (this.cursorItem.equals(cursorItem)) return;
        // Access to the current/parent object
        this.cursorItem = cursorItem;
        // Branch: checks a condition
        if (sendPacket) sendPacketToViewers(new SetCursorItemPacket(cursorItem));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected void UNSAFE_itemInsert(int slot, ItemStack item, ItemStack previous, boolean sendPacket) {
        // Loop: repeats a block
        for (Player player : getViewers()) {
            // Calls a method
            final EquipmentSlot equipmentSlot = getEquipmentSlot(slot, player.getHeldSlot());
            // Branch: checks a condition
            if (equipmentSlot == null) continue;

            // Calls a method
            EntityEquipEvent entityEquipEvent = new EntityEquipEvent(player, item, equipmentSlot);
            // Calls a method
            EventDispatcher.call(entityEquipEvent);
            // Calls a method
            item = entityEquipEvent.getEquippedItem();

            // Calls a method
            player.updateEquipmentAttributes(previous, item, equipmentSlot);
            // Calls a method
            player.syncEquipment(equipmentSlot, item);
        // End of a block/expression
        }

        // Access to the current/parent object
        super.UNSAFE_itemInsert(slot, item, previous, sendPacket);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sendSlotRefresh(int slot, ItemStack item) {
        // Branch: checks a condition
        if (slot < 0 || slot > INVENTORY_SIZE)
            // Returns a value to the caller
            return; // Sanity check
        // See note in PlayerInventoryUtils about why we do this conversion
        // Calls a method
        boolean isPlayerInventorySlot = isPlayerInventorySlot(slot);
        // Assigns a value
        int packetSlot = isPlayerInventorySlot
                // Code statement
                ? convertMinestomSlotToPlayerInventorySlot(slot)
                // Calls a method
                : convertMinestomSlotToWindowSlot(slot);

        // Code statement
        sendPacketToViewers(isPlayerInventorySlot
                // Code statement
                ? new SetPlayerInventorySlotPacket(packetSlot, item)
                // Calls a method
                : new SetSlotPacket(0, 0, (short) packetSlot, item));
    // End of a block/expression
    }

    /**
     * Gets a {@link WindowItemsPacket} with all the items in the inventory.
     *
     * @return a {@link WindowItemsPacket} with inventory items
     */
    // Start of a method/block
    private WindowItemsPacket createWindowItemsPacket() {
        // Assigns a value
        ItemStack[] convertedSlots = new ItemStack[INVENTORY_SIZE];
        // Loop: repeats a block
        for (int i = 0; i < itemStacks.length; i++) {
            // Calls a method
            final int slot = convertMinestomSlotToWindowSlot(i);
            // Assigns a value
            convertedSlots[slot] = itemStacks[i];
        // End of a block/expression
        }
        // Returns a value to the caller
        return new WindowItemsPacket(0, 0, List.of(convertedSlots), cursorItem);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean leftClick(Player player, int slot) {
        // Calls a method
        final ItemStack cursor = getCursorItem();
        // Calls a method
        final ItemStack clicked = getItemStack(slot);
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.leftClick(clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        setItemStack(slot, clickResult.getClicked());
        // Calls a method
        setCursorItem(clickResult.getCursor());
        // Calls a method
        callClickEvent(player, this, slot, ClickType.LEFT_CLICK, clicked, cursor);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean rightClick(Player player, int slot) {
        // Calls a method
        final ItemStack cursor = getCursorItem();
        // Calls a method
        final ItemStack clicked = getItemStack(slot);
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.rightClick(clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        setItemStack(slot, clickResult.getClicked());
        // Calls a method
        setCursorItem(clickResult.getCursor());
        // Calls a method
        callClickEvent(player, this, slot, ClickType.RIGHT_CLICK, clicked, cursor);
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
        update();
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean drop(Player player, boolean all, int slot) {
        // Calls a method
        final ItemStack cursor = getCursorItem();
        // Assigns a value
        final boolean outsideDrop = slot == -999;
        // Calls a method
        final ItemStack clicked = outsideDrop ? ItemStack.AIR : getItemStack(slot);
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.drop(player, all, slot, clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        final ItemStack resultClicked = clickResult.getClicked();
        // Branch: checks a condition
        if (resultClicked != null && !outsideDrop) {
            // Calls a method
            setItemStack(slot, resultClicked);
        // End of a block/expression
        }
        // Calls a method
        setCursorItem(clickResult.getCursor());
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean shiftClick(Player player, int slot, int button) {
        // Calls a method
        final ItemStack cursor = getCursorItem();
        // Calls a method
        final ItemStack clicked = getItemStack(slot);
        // Assigns a value
        final boolean craftingGridClick = slot > 36 && slot <= 40;
        // Assigns a value
        final boolean craftingResultClick = slot == 36;
        // Assigns a value
        final boolean hotBarClick = slot < 9;

        // the client has different behavior for clicking based on where the item is in the inventory
        // Code statement
        InventoryClickResult clickResult;
        // Calls a method
        final EquipmentSlot equipmentSlot = getEquipmentSlot(slot, player.getHeldSlot());
        // Branch: checks a condition
        if (equipmentSlot != null && (equipmentSlot.isArmor() || equipmentSlot == EquipmentSlot.OFF_HAND)) {
            // CASE: shift-clicking equipped armor or your off-hand item
            // we want to go through the inventory slots first
            // and then through the hotbar going left to right
            // Assigns a value
            clickResult = clickProcessor.shiftClick(
                    // Code statement
                    this, this,
                    // Code statement
                    9, INNER_INVENTORY_SIZE, 1,
                    // Code statement
                    player, slot, clicked, cursor
            // End of a block/expression
            );

            // Branch: checks a condition
            if (clickResult.isCancel()) {
                // Assigns a value
                clickResult = clickProcessor.shiftClick(
                        // Code statement
                        this, this,
                        // Code statement
                        0, 9, 1,
                        // Code statement
                        player, slot, clicked, cursor
                // End of a block/expression
                );
            // End of a block/expression
            }
        // Branch: checks a condition
        } else if (craftingGridClick) {
            // CASE: shift-clicking an item from the crafting grid into your inventory
            // we want to prioritize the inventory from left-to-right and then the hotbar from left-to-right
            // Assigns a value
            clickResult = clickProcessor.shiftClick(
                    // Code statement
                    this, this,
                    // Code statement
                    9, INNER_INVENTORY_SIZE, 1,
                    // Code statement
                    player, slot, clicked, cursor
            // End of a block/expression
            );

            // Branch: checks a condition
            if(clickResult.isCancel()) {
                // Assigns a value
                clickResult = clickProcessor.shiftClick(
                        // Code statement
                        this, this,
                        // Code statement
                        0, 9, 1,
                        // Code statement
                        player, slot, clicked, cursor
                // End of a block/expression
                );
            // End of a block/expression
            }
        // Branch: checks a condition
        } else if (craftingResultClick) {
            // CASE: shift-clicking an item from the crafting grid result into your inventory
            // we want to prioritize the hotbar from right-to-left and then the inventory from right-to-left
            // Assigns a value
            clickResult = clickProcessor.shiftClick(
                    // Code statement
                    this, this,
                    // Code statement
                    9, 0, -1,
                    // Code statement
                    player, slot, clicked, cursor
            // End of a block/expression
            );

            // Branch: checks a condition
            if(clickResult.isCancel()) {
                // Assigns a value
                clickResult = clickProcessor.shiftClick(
                        // Code statement
                        this, this,
                        // Code statement
                        INNER_INVENTORY_SIZE, 9, -1,
                        // Code statement
                        player, slot, clicked, cursor
                // End of a block/expression
                );
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // CASE: shift-clicking an item in the hotbar or inventory
            // Assigns a value
            clickResult = clickProcessor.shiftClick(
                    // Code statement
                    this, this,
                    // Code statement
                    (hotBarClick ? 9 : 0), (hotBarClick ? INNER_INVENTORY_SIZE : 9), 1,
                    // Code statement
                    player, slot, clicked, cursor
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Calls a method
        setItemStack(slot, clickResult.getClicked());
        // Calls a method
        setCursorItem(clickResult.getCursor());
        // Code statement
        update(); // FIXME: currently not properly client-predicted
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean changeHeld(Player player, int slot, int key) {
        // Calls a method
        final ItemStack cursorItem = getCursorItem();
        // Branch: checks a condition
        if (!cursorItem.isAir()) return false;
        // Calls a method
        final ItemStack heldItem = getItemStack(key);
        // Calls a method
        final ItemStack clicked = getItemStack(slot);
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.changeHeld(clicked, heldItem);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        setItemStack(slot, clickResult.getClicked());
        // Calls a method
        setItemStack(key, clickResult.getCursor());
        // Calls a method
        callClickEvent(player, this, slot, ClickType.CHANGE_HELD, clicked, cursorItem);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean dragging(Player player, List<Integer> slots, int button) {
        // Calls a method
        final ItemStack cursor = getCursorItem();

        // Calls a method
        final ItemStack clickResult = clickProcessor.dragging(player, this, slots, button, cursor);
        // Branch: checks a condition
        if (clickResult == null) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        setCursorItem(clickResult);
        // Code statement
        update(); // FIXME: currently not properly client-predicted
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean doubleClick(Player player, int slot) {
        // Calls a method
        final ItemStack cursor = getCursorItem();
        // Calls a method
        final ItemStack clicked = getItemStack(slot);
        // Calls a method
        final InventoryClickResult clickResult = clickProcessor.doubleClick(this, this, player, slot, clicked, cursor);
        // Branch: checks a condition
        if (clickResult.isCancel()) {
            // Calls a method
            update();
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
        // Calls a method
        setCursorItem(clickResult.getCursor());
        // Code statement
        update(); // FIXME: currently not properly client-predicted
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

// End of a block/expression
}
