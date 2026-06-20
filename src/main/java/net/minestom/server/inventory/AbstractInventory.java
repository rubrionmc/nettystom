// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import net.minestom.server.Viewable;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.EventFilter;
// Import d'une classe nécessaire
import net.minestom.server.event.EventHandler;
// Import d'une classe nécessaire
import net.minestom.server.event.EventNode;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.InventoryEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.InventoryClickProcessor;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.CloseWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetSlotPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.WindowItemsPacket;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import net.minestom.server.tag.Taggable;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.lang.invoke.MethodHandles;
// Import d'une classe nécessaire
import java.lang.invoke.VarHandle;
// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;
// Import d'une classe nécessaire
import java.util.function.UnaryOperator;

/**
 * Represents an inventory where items can be modified/retrieved.
 */
// Instruction de code
public sealed abstract class AbstractInventory implements InventoryClickHandler, Taggable, Viewable, EventHandler<InventoryEvent>
        // Début d'une méthode/d'un bloc
        permits Inventory, PlayerInventory {

    // Appelle une méthode
    private static final VarHandle ITEM_UPDATER = MethodHandles.arrayElementVarHandle(ItemStack[].class);

    // Instruction de code
    private final int size;
    // Instruction de code
    protected final ItemStack[] itemStacks;

    // the click processor which process all the clicks in the inventory
    // Appelle une méthode
    protected final InventoryClickProcessor clickProcessor = new InventoryClickProcessor();

    // Appelle une méthode
    private final TagHandler tagHandler = TagHandler.newHandler();

    // the players currently viewing this inventory
    // Affecte une valeur
    protected final Set<Player> viewers = new CopyOnWriteArraySet<>();
    // Appelle une méthode
    protected final Set<Player> unmodifiableViewers = Collections.unmodifiableSet(viewers);

    // the local event node filtered to this inventory
    // Instruction de code
    private final EventNode<InventoryEvent> eventNode;

    // Début d'une méthode/d'un bloc
    protected AbstractInventory(int size) {
        // Accès à l'objet courant/parent
        this.size = size;
        // Accès à l'objet courant/parent
        this.itemStacks = new ItemStack[getSize()];
        // Appelle une méthode
        Arrays.fill(itemStacks, ItemStack.AIR);
        // Setup event node
        // Appelle une méthode
        final ServerProcess process = MinecraftServer.process();
        // Embranchement : vérifie une condition
        if (process != null) {
            // Accès à l'objet courant/parent
            this.eventNode = process.eventHandler().map(this, EventFilter.INVENTORY);
        // Branche alternative de la condition
        } else {
            // Local nodes require a server process
            // Accès à l'objet courant/parent
            this.eventNode = null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets this window id.
     * <p>
     * This is the id that the client will send to identify the affected inventory, mostly used by packets.
     *
     * @return the window id
     */
    // Appelle une méthode
    public abstract byte getWindowId();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Set<Player> getViewers() {
        // Renvoie une valeur à l'appelant
        return unmodifiableViewers;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Embranchement : vérifie une condition
        if (!this.viewers.add(player)) return false;

        // Appelle une méthode
        update(player);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeViewer(Player player) {
        // Embranchement : vérifie une condition
        if (!this.viewers.remove(player)) return false;

        // Drop cursor item when closing inventory
        // Appelle une méthode
        ItemStack cursorItem = player.getInventory().getCursorItem();
        // Appelle une méthode
        player.getInventory().setCursorItem(ItemStack.AIR);

        // Embranchement : vérifie une condition
        if (!cursorItem.isAir()) {
            // Embranchement : vérifie une condition
            if (!player.dropItem(cursorItem)) {
                // Appelle une méthode
                player.getInventory().addItemStack(cursorItem);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (player.didCloseInventory()) {
            // Appelle une méthode
            player.sendPacket(new CloseWindowPacket(getWindowId()));
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets an {@link ItemStack} at the specified slot and send relevant update to the viewer(s).
     *
     * @param slot      the slot to set the item
     * @param itemStack the item to set
     */
    // Début d'une méthode/d'un bloc
    public void setItemStack(int slot, ItemStack itemStack) {
        // Appelle une méthode
        setItemStack(slot, itemStack, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets an {@link ItemStack} at the specified slot and send relevant update to the viewer(s).
     *
     * @param slot      the slot to set the item
     * @param itemStack the item to set
     * @param sendPacket whether or not to send packets
     */
    // Début d'une méthode/d'un bloc
    public void setItemStack(int slot, ItemStack itemStack, boolean sendPacket) {
        // Instruction de code
        Check.argCondition(!MathUtils.isBetween(slot, 0, getSize() - 1), // Subtract 1 because MathUtils is <= max, instead of strictly less than
                // Instruction de code
                "Inventory does not have the slot " + slot);

        // Instruction de code
        ItemStack previous;
        // Début d'une méthode/d'un bloc
        synchronized (this) {
            // Affecte une valeur
            previous = itemStacks[slot];
            // Embranchement : vérifie une condition
            if (itemStack.equals(previous)) return; // Avoid sending updates if the item has not changed
            // Appelle une méthode
            UNSAFE_itemInsert(slot, itemStack, previous, sendPacket);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        EventDispatcher.call(new InventoryItemChangeEvent(this, slot, previous, itemStack));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void UNSAFE_itemInsert(int slot, ItemStack item, ItemStack previous, boolean sendPacket) {
        // Affecte une valeur
        itemStacks[slot] = item;
        // Embranchement : vérifie une condition
        if (sendPacket) sendSlotRefresh(slot, item);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void sendSlotRefresh(int slot, ItemStack item) {
        // Appelle une méthode
        sendPacketToViewers(new SetSlotPacket(getWindowId(), 0, (short) slot, item));
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public synchronized <T> T processItemStack(ItemStack itemStack,
                                                        // Instruction de code
                                                        TransactionType type,
                                                        // Début d'une méthode/d'un bloc
                                                        TransactionOption<T> option) {
        // Renvoie une valeur à l'appelant
        return option.fill(type, this, itemStack);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public synchronized <T> List<T> processItemStacks(List<ItemStack> itemStacks,
                                                                        // Instruction de code
                                                                        TransactionType type,
                                                                        // Début d'une méthode/d'un bloc
                                                                        TransactionOption<T> option) {
        // Appelle une méthode
        List<T> result = new ArrayList<>(itemStacks.size());
        // Début d'une méthode/d'un bloc
        itemStacks.forEach(itemStack -> {
            // Appelle une méthode
            T transactionResult = processItemStack(itemStack, type, option);
            // Appelle une méthode
            result.add(transactionResult);
        // Fin d'un bloc/d'une expression
        });
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds an {@link ItemStack} to the inventory and sends relevant update to the viewer(s).
     *
     * @param itemStack the item to add
     * @param option    the transaction option
     * @return true if the item has been successfully added, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public <T> T addItemStack(ItemStack itemStack, TransactionOption<T> option) {
        // Renvoie une valeur à l'appelant
        return processItemStack(itemStack, TransactionType.ADD, option);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean addItemStack(ItemStack itemStack) {
        // Renvoie une valeur à l'appelant
        return addItemStack(itemStack, TransactionOption.ALL_OR_NOTHING);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds {@link ItemStack}s to the inventory and sends relevant updates to the viewer(s).
     *
     * @param itemStacks items to add
     * @param option     the transaction option
     * @return the operation results
     */
    // Instruction de code
    public <T> List<T> addItemStacks(List<ItemStack> itemStacks,
                                                       // Début d'une méthode/d'un bloc
                                                       TransactionOption<T> option) {
        // Renvoie une valeur à l'appelant
        return processItemStacks(itemStacks, TransactionType.ADD, option);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Takes an {@link ItemStack} from the inventory and sends relevant update to the viewer(s).
     *
     * @param itemStack the item to take
     * @return true if the item has been successfully fully taken, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public <T> T takeItemStack(ItemStack itemStack, TransactionOption<T> option) {
        // Renvoie une valeur à l'appelant
        return processItemStack(itemStack, TransactionType.TAKE, option);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Takes {@link ItemStack}s from the inventory and sends relevant updates to the viewer(s).
     *
     * @param itemStacks items to take
     * @return the operation results
     */
    // Instruction de code
    public <T> List<T> takeItemStacks(List<ItemStack> itemStacks,
                                                        // Début d'une méthode/d'un bloc
                                                        TransactionOption<T> option) {
        // Renvoie une valeur à l'appelant
        return processItemStacks(itemStacks, TransactionType.TAKE, option);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public synchronized void replaceItemStack(int slot, UnaryOperator<ItemStack> operator) {
        // Appelle une méthode
        var currentItem = getItemStack(slot);
        // Appelle une méthode
        setItemStack(slot, operator.apply(currentItem));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Clears the inventory and send relevant update to the viewer(s).
     */
    // Début d'une méthode/d'un bloc
    public synchronized void clear() {
        // Clear the item array
        // Boucle : répète un bloc
        for (int i = 0; i < size; i++) {
            // Appelle une méthode
            setItemStack(i, ItemStack.AIR, false);
        // Fin d'un bloc/d'une expression
        }
        // Send the cleared inventory to viewers
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Refreshes the inventory for all viewers.
     */
    // Début d'une méthode/d'un bloc
    public void update() {
        // Accès à l'objet courant/parent
        this.viewers.forEach(this::update);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Refreshes the inventory for a specific viewer.
     *
     * @param player the player to update the inventory for
     */
    // Début d'une méthode/d'un bloc
    public void update(Player player) {
        // Appelle une méthode
        player.sendPacket(new WindowItemsPacket(getWindowId(), 0, List.of(itemStacks), player.getInventory().getCursorItem()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ItemStack} at the specified slot.
     *
     * @param slot the slot to check
     * @return the item in the slot {@code slot}
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack(int slot) {
        // Renvoie une valeur à l'appelant
        return (ItemStack) ITEM_UPDATER.getVolatile(itemStacks, slot);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the {@link ItemStack} in the inventory.
     * <p>
     * Be aware that the returned array does not need to be the original one,
     * meaning that modifying it directly may not work.
     *
     * @return an array containing all the inventory's items
     */
    // Début d'une méthode/d'un bloc
    public ItemStack[] getItemStacks() {
        // Renvoie une valeur à l'appelant
        return itemStacks.clone();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the size of the inventory.
     *
     * @return the inventory's size
     */
    // Début d'une méthode/d'un bloc
    public int getSize() {
        // Renvoie une valeur à l'appelant
        return size;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the size of the "inner inventory" (which includes only "usable" slots).
     *
     * @return inner inventory's size
     */
    // Début d'une méthode/d'un bloc
    public int getInnerSize() {
        // Renvoie une valeur à l'appelant
        return getSize();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Places all the items of {@code itemStacks} into the internal array.
     *
     * @param itemStacks the array to copy the content from
     * @throws IllegalArgumentException if the size of the array is not equal to {@link #getSize()}
     * @throws NullPointerException     if {@code itemStacks} contains one null element or more
     */
    // Début d'une méthode/d'un bloc
    public void copyContents(ItemStack[] itemStacks) {
        // Instruction de code
        Check.argCondition(itemStacks.length != getSize(),
                // Appelle une méthode
                "The size of the array has to be of the same size as the inventory: " + getSize());

        // Boucle : répète un bloc
        for (int i = 0; i < itemStacks.length; i++) {
            // Affecte une valeur
            final ItemStack itemStack = itemStacks[i];
            // Appelle une méthode
            Check.notNull(itemStack, "The item array cannot contain any null element!");
            // Appelle une méthode
            setItemStack(i, itemStack);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TagHandler tagHandler() {
        // Renvoie une valeur à l'appelant
        return tagHandler;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EventNode<InventoryEvent> eventNode() {
        // Renvoie une valeur à l'appelant
        return eventNode;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
