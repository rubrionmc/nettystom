// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import net.minestom.server.Viewable;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.EventFilter;
// Import of a required class
import net.minestom.server.event.EventHandler;
// Import of a required class
import net.minestom.server.event.EventNode;
// Import of a required class
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
// Import of a required class
import net.minestom.server.event.trait.InventoryEvent;
// Import of a required class
import net.minestom.server.inventory.click.InventoryClickProcessor;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.network.packet.server.play.CloseWindowPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.SetSlotPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.WindowItemsPacket;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import net.minestom.server.tag.Taggable;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.lang.invoke.MethodHandles;
// Import of a required class
import java.lang.invoke.VarHandle;
// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;
// Import of a required class
import java.util.function.UnaryOperator;

/**
 * Represents an inventory where items can be modified/retrieved.
 */
// Code statement
public sealed abstract class AbstractInventory implements InventoryClickHandler, Taggable, Viewable, EventHandler<InventoryEvent>
        // Start of a method/block
        permits Inventory, PlayerInventory {

    // Calls a method
    private static final VarHandle ITEM_UPDATER = MethodHandles.arrayElementVarHandle(ItemStack[].class);

    // Code statement
    private final int size;
    // Code statement
    protected final ItemStack[] itemStacks;

    // the click processor which process all the clicks in the inventory
    // Calls a method
    protected final InventoryClickProcessor clickProcessor = new InventoryClickProcessor();

    // Calls a method
    private final TagHandler tagHandler = TagHandler.newHandler();

    // the players currently viewing this inventory
    // Calls a method
    protected final Set<Player> viewers = new CopyOnWriteArraySet<>();
    // Calls a method
    protected final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);

    // the local event node filtered to this inventory
    // Code statement
    private final EventNode<InventoryEvent> eventNode;

    // Start of a method/block
    protected AbstractInventory(int size) {
        // Access to the current/parent object
        this.size = size;
        // Access to the current/parent object
        this.itemStacks = new ItemStack[getSize()];
        // Calls a method
        Arrays.fill(itemStacks, ItemStack.AIR);
        // Setup event node
        // Calls a method
        final ServerProcess process = MinecraftServer.process();
        // Branch: checks a condition
        if (process != null) {
            // Access to the current/parent object
            this.eventNode = process.eventHandler().map(this, EventFilter.INVENTORY);
        // Alternative branch of the condition
        } else {
            // Local nodes require a server process
            // Access to the current/parent object
            this.eventNode = null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets this window id.
     * <p>
     * This is the id that the client will send to identify the affected inventory, mostly used by packets.
     *
     * @return the window id
     */
    // Calls a method
    public abstract byte getWindowId();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<? extends Player> getViewers() {
        // Returns a value to the caller
        return unmodifiableViewers;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addViewer(Player player) {
        // Branch: checks a condition
        if (!this.viewers.add(player)) return false;

        // Calls a method
        update(player);
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeViewer(Player player) {
        // Branch: checks a condition
        if (!this.viewers.remove(player)) return false;

        // Drop cursor item when closing inventory
        // Calls a method
        ItemStack cursorItem = player.getInventory().getCursorItem();
        // Calls a method
        player.getInventory().setCursorItem(ItemStack.AIR);

        // Branch: checks a condition
        if (!cursorItem.isAir()) {
            // Branch: checks a condition
            if (!player.dropItem(cursorItem)) {
                // Calls a method
                player.getInventory().addItemStack(cursorItem);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (player.didCloseInventory()) {
            // Calls a method
            player.sendPacket(new CloseWindowPacket(getWindowId()));
        // End of a block/expression
        }

        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    /**
     * Sets an {@link ItemStack} at the specified slot and send relevant update to the viewer(s).
     *
     * @param slot      the slot to set the item
     * @param itemStack the item to set
     */
    // Start of a method/block
    public void setItemStack(int slot, ItemStack itemStack) {
        // Calls a method
        setItemStack(slot, itemStack, true);
    // End of a block/expression
    }

    /**
     * Sets an {@link ItemStack} at the specified slot and send relevant update to the viewer(s).
     *
     * @param slot       the slot to set the item
     * @param itemStack  the item to set
     * @param sendPacket whether to send packets
     */
    // Start of a method/block
    public void setItemStack(int slot, ItemStack itemStack, boolean sendPacket) {
        // Code statement
        Check.argCondition(!MathUtils.isBetween(slot, 0, getSize() - 1), // Subtract 1 because MathUtils is <= max, instead of strictly less than
                // Code statement
                "Inventory does not have the slot " + slot);

        // Code statement
        ItemStack previous;
        // Start of a method/block
        synchronized (this) {
            // Assigns a value
            previous = itemStacks[slot];
            // Branch: checks a condition
            if (itemStack.equals(previous)) return; // Avoid sending updates if the item has not changed
            // Calls a method
            UNSAFE_itemInsert(slot, itemStack, previous, sendPacket);
        // End of a block/expression
        }
        // Calls a method
        EventDispatcher.call(new InventoryItemChangeEvent(this, slot, previous, itemStack));
    // End of a block/expression
    }

    // Start of a method/block
    protected void UNSAFE_itemInsert(int slot, ItemStack item, ItemStack previous, boolean sendPacket) {
        // Assigns a value
        itemStacks[slot] = item;
        // Branch: checks a condition
        if (sendPacket) sendSlotRefresh(slot, item);
    // End of a block/expression
    }

    // Start of a method/block
    public void sendSlotRefresh(int slot, ItemStack item) {
        // Calls a method
        sendPacketToViewers(new SetSlotPacket(getWindowId(), 0, (short) slot, item));
    // End of a block/expression
    }

    // Code statement
    public synchronized <T> T processItemStack(ItemStack itemStack,
                                               // Code statement
                                               TransactionType type,
                                               // Start of a method/block
                                               TransactionOption<T> option) {
        // Returns a value to the caller
        return option.fill(type, this, itemStack);
    // End of a block/expression
    }

    // Code statement
    public synchronized <T> List<T> processItemStacks(List<ItemStack> itemStacks,
                                                      // Code statement
                                                      TransactionType type,
                                                      // Start of a method/block
                                                      TransactionOption<T> option) {
        // Calls a method
        List<T> result = new ArrayList<>(itemStacks.size());
        // Start of a method/block
        itemStacks.forEach(itemStack -> {
            // Calls a method
            T transactionResult = processItemStack(itemStack, type, option);
            // Calls a method
            result.add(transactionResult);
        // End of a block/expression
        });
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    /**
     * Adds an {@link ItemStack} to the inventory and sends relevant update to the viewer(s).
     *
     * @param itemStack the item to add
     * @param option    the transaction option
     * @return true if the item has been successfully added, false otherwise
     */
    // Start of a method/block
    public <T> T addItemStack(ItemStack itemStack, TransactionOption<T> option) {
        // Returns a value to the caller
        return processItemStack(itemStack, TransactionType.ADD, option);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean addItemStack(ItemStack itemStack) {
        // Returns a value to the caller
        return addItemStack(itemStack, TransactionOption.ALL_OR_NOTHING);
    // End of a block/expression
    }

    /**
     * Adds {@link ItemStack}s to the inventory and sends relevant updates to the viewer(s).
     *
     * @param itemStacks items to add
     * @param option     the transaction option
     * @return the operation results
     */
    // Code statement
    public <T> List<T> addItemStacks(List<ItemStack> itemStacks,
                                     // Start of a method/block
                                     TransactionOption<T> option) {
        // Returns a value to the caller
        return processItemStacks(itemStacks, TransactionType.ADD, option);
    // End of a block/expression
    }

    /**
     * Takes an {@link ItemStack} from the inventory and sends relevant update to the viewer(s).
     *
     * @param itemStack the item to take
     * @return true if the item has been successfully fully taken, false otherwise
     */
    // Start of a method/block
    public <T> T takeItemStack(ItemStack itemStack, TransactionOption<T> option) {
        // Returns a value to the caller
        return processItemStack(itemStack, TransactionType.TAKE, option);
    // End of a block/expression
    }

    /**
     * Takes {@link ItemStack}s from the inventory and sends relevant updates to the viewer(s).
     *
     * @param itemStacks items to take
     * @return the operation results
     */
    // Code statement
    public <T> List<T> takeItemStacks(List<ItemStack> itemStacks,
                                      // Start of a method/block
                                      TransactionOption<T> option) {
        // Returns a value to the caller
        return processItemStacks(itemStacks, TransactionType.TAKE, option);
    // End of a block/expression
    }

    // Start of a method/block
    public synchronized void replaceItemStack(int slot, UnaryOperator<ItemStack> operator) {
        // Calls a method
        var currentItem = getItemStack(slot);
        // Calls a method
        setItemStack(slot, operator.apply(currentItem));
    // End of a block/expression
    }

    /**
     * Clears the inventory and send relevant update to the viewer(s).
     */
    // Start of a method/block
    public synchronized void clear() {
        // Clear the item array
        // Loop: repeats a block
        for (int i = 0; i < size; i++) {
            // Calls a method
            setItemStack(i, ItemStack.AIR, false);
        // End of a block/expression
        }
        // Send the cleared inventory to viewers
        // Calls a method
        update();
    // End of a block/expression
    }

    /**
     * Refreshes the inventory for all viewers.
     */
    // Start of a method/block
    public void update() {
        // Access to the current/parent object
        this.viewers.forEach(this::update);
    // End of a block/expression
    }

    /**
     * Refreshes the inventory for a specific viewer.
     *
     * @param player the player to update the inventory for
     */
    // Start of a method/block
    public void update(Player player) {
        // Calls a method
        player.sendPacket(new WindowItemsPacket(getWindowId(), 0, List.of(itemStacks), player.getInventory().getCursorItem()));
    // End of a block/expression
    }

    /**
     * Gets the {@link ItemStack} at the specified slot.
     *
     * @param slot the slot to check
     * @return the item in the slot {@code slot}
     */
    // Start of a method/block
    public ItemStack getItemStack(int slot) {
        // Returns a value to the caller
        return (ItemStack) ITEM_UPDATER.getVolatile(itemStacks, slot);
    // End of a block/expression
    }

    /**
     * Gets all the {@link ItemStack} in the inventory.
     * <p>
     * Be aware that the returned array does not need to be the original one,
     * meaning that modifying it directly may not work.
     *
     * @return an array containing all the inventory's items
     */
    // Start of a method/block
    public ItemStack[] getItemStacks() {
        // Returns a value to the caller
        return itemStacks.clone();
    // End of a block/expression
    }

    /**
     * Gets the size of the inventory.
     *
     * @return the inventory's size
     */
    // Start of a method/block
    public int getSize() {
        // Returns a value to the caller
        return size;
    // End of a block/expression
    }

    /**
     * Gets the size of the "inner inventory" (which includes only "usable" slots).
     *
     * @return inner inventory's size
     */
    // Start of a method/block
    public int getInnerSize() {
        // Returns a value to the caller
        return getSize();
    // End of a block/expression
    }

    /**
     * Places all the items of {@code itemStacks} into the internal array.
     *
     * @param itemStacks the array to copy the content from
     * @throws IllegalArgumentException if the size of the array is not equal to {@link #getSize()}
     * @throws NullPointerException     if {@code itemStacks} contains one null element or more
     */
    // Start of a method/block
    public void copyContents(ItemStack[] itemStacks) {
        // Code statement
        Check.argCondition(itemStacks.length != getSize(),
                // Calls a method
                "The size of the array has to be of the same size as the inventory: " + getSize());

        // Loop: repeats a block
        for (int i = 0; i < itemStacks.length; i++) {
            // Assigns a value
            final ItemStack itemStack = itemStacks[i];
            // Calls a method
            Objects.requireNonNull(itemStack, "The item array cannot contain any null element!");
            // Calls a method
            setItemStack(i, itemStack);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public TagHandler tagHandler() {
        // Returns a value to the caller
        return tagHandler;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EventNode<InventoryEvent> eventNode() {
        // Returns a value to the caller
        return eventNode;
    // End of a block/expression
    }
// End of a block/expression
}
