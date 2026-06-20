// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.ClickType;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.InventoryClickResult;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.OpenWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.WindowPropertyPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an inventory which can be viewed by a collection of {@link Player}.
 * <p>
 * You can create one with {@link Inventory#Inventory(InventoryType, String)} or by making your own subclass.
 * It can then be opened using {@link Player#openInventory(Inventory)}.
 */
// Déclaration de type (classe/interface/enum/record)
public non-sealed class Inventory extends AbstractInventory {
    // Appelle une méthode
    private static final AtomicInteger ID_COUNTER = new AtomicInteger();

    // Instruction de code
    private final byte id;
    // Instruction de code
    private final InventoryType inventoryType;
    // Instruction de code
    private Component title;

    // Instruction de code
    private final int offset;

    // Début d'une méthode/d'un bloc
    public Inventory(InventoryType inventoryType, Component title) {
        // Accès à l'objet courant/parent
        super(inventoryType.getSize());
        // Accès à l'objet courant/parent
        this.id = generateId();
        // Accès à l'objet courant/parent
        this.inventoryType = inventoryType;
        // Accès à l'objet courant/parent
        this.title = title;

        // Accès à l'objet courant/parent
        this.offset = getSize();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Inventory(InventoryType inventoryType, String title) {
        // Appelle une méthode
        this(inventoryType, Component.text(title));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static byte generateId() {
        // Renvoie une valeur à l'appelant
        return (byte) ID_COUNTER.updateAndGet(i -> i + 1 >= 128 ? 1 : i + 1);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the inventory type.
     *
     * @return the inventory type
     */
    // Début d'une méthode/d'un bloc
    public InventoryType getInventoryType() {
        // Renvoie une valeur à l'appelant
        return inventoryType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the inventory title.
     *
     * @return the inventory title
     */
    // Début d'une méthode/d'un bloc
    public Component getTitle() {
        // Renvoie une valeur à l'appelant
        return title;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the inventory title.
     *
     * @param title the new inventory title
     */
    // Début d'une méthode/d'un bloc
    public void setTitle(Component title) {
        // Accès à l'objet courant/parent
        this.title = title;
        // Re-open the inventory
        // Appelle une méthode
        sendPacketToViewers(new OpenWindowPacket(getWindowId(), getInventoryType().getWindowType(), title));
        // Send inventory items
        // Appelle une méthode
        update();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte getWindowId() {
        // Renvoie une valeur à l'appelant
        return id;
    // Fin d'un bloc/d'une expression
    }

    /**
     * This will not open the inventory for {@code player}, use {@link Player#openInventory(Inventory)}.
     *
     * @param player the viewer to add
     * @return true if the player has successfully been added
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean addViewer(Player player) {
        // Embranchement : vérifie une condition
        if (!this.viewers.add(player)) return false;

        // Also send the open window packet
        // Appelle une méthode
        player.sendPacket(new OpenWindowPacket(id, getInventoryType().getWindowType(), title));
        // Appelle une méthode
        update(player);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * This will not close the inventory for {@code player}, use {@link Player#closeInventory()}.
     *
     * @param player the viewer to remove
     * @return true if the player has successfully been removed
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean removeViewer(Player player) {
        // Embranchement : vérifie une condition
        if (!super.removeViewer(player)) return false;

        // Appelle une méthode
        player.getClickPreprocessor().clearCache();
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the cursor item of a player.
     *
     * @deprecated normal inventories no longer store cursor items
     * @see <a href="https://github.com/Minestom/Minestom/pull/2294">the relevant PR</a>
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public ItemStack getCursorItem(Player player) {
        // Renvoie une valeur à l'appelant
        return player.getInventory().getCursorItem();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the cursor item of a player.
     *
     * @deprecated normal inventories no longer store cursor items
     * @see <a href="https://github.com/Minestom/Minestom/pull/2294">the relevant PR</a>
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setCursorItem(Player player, ItemStack cursorItem) {
        // Appelle une méthode
        player.getInventory().setCursorItem(cursorItem);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a window property to all viewers.
     *
     * @param property the property to send
     * @param value    the value of the property
     * @see <a href="https://minecraft.wiki/w/Minecraft_Wiki:Projects/wiki.vg_merge/Protocol#Set_Container_Property">the Minecraft wiki</a>
     */
    // Début d'une méthode/d'un bloc
    protected void sendProperty(InventoryProperty property, short value) {
        // Appelle une méthode
        sendPacketToViewers(new WindowPropertyPacket(getWindowId(), property.getProperty(), value));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean leftClick(Player player, int slot) {
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final ItemStack cursor = playerInventory.getCursorItem();
        // Appelle une méthode
        final boolean isInWindow = isClickInWindow(slot);
        // Affecte une valeur
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Affecte une valeur
        final AbstractInventory clickedInventory = isInWindow ? this : playerInventory;
        // Appelle une méthode
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.leftClick(clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (isInWindow) {
            // Appelle une méthode
            setItemStack(slot, clickResult.getClicked());
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        playerInventory.setCursorItem(clickResult.getCursor());
        // Appelle une méthode
        callClickEvent(player, clickedInventory, slot, ClickType.LEFT_CLICK, clicked, cursor);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean rightClick(Player player, int slot) {
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final ItemStack cursor = playerInventory.getCursorItem();
        // Appelle une méthode
        final boolean isInWindow = isClickInWindow(slot);
        // Affecte une valeur
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Appelle une méthode
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Affecte une valeur
        final AbstractInventory clickedInventory = isInWindow ? this : playerInventory;
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.rightClick(clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (isInWindow) {
            // Appelle une méthode
            setItemStack(slot, clickResult.getClicked());
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        playerInventory.setCursorItem(clickResult.getCursor());
        // Appelle une méthode
        callClickEvent(player, clickedInventory, slot, ClickType.RIGHT_CLICK, clicked, cursor);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shiftClick(Player player, int slot, int button) {
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final boolean isInWindow = isClickInWindow(slot);
        // Affecte une valeur
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Appelle une méthode
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Affecte une valeur
        final ItemStack cursor = playerInventory.getCursorItem(); // Isn't used in the algorithm

        // Instruction de code
        InventoryClickResult clickResult;
        // Embranchement : vérifie une condition
        if (isInWindow) {
            // The player shift-clicked an item in this GUI into their inventory.
            // Prioritize the hotbar (8->0), then their regular inventory (35->9).
            // Affecte une valeur
            clickResult = clickProcessor.shiftClick(
                    // Instruction de code
                    this, playerInventory,
                    // Instruction de code
                    8, 0, -1,
                    // Instruction de code
                    player, clickSlot, clicked, cursor);

            // Embranchement : vérifie une condition
            if (clickResult.isCancel()) {
                // Affecte une valeur
                clickResult = clickProcessor.shiftClick(
                        // Instruction de code
                        this, playerInventory,
                        // Instruction de code
                        playerInventory.getInnerSize() - 1, 0, -1,
                        // Instruction de code
                        player, clickSlot, clicked, cursor);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            clickResult = clickProcessor.shiftClick(
                    // Instruction de code
                    playerInventory, this,
                    // Instruction de code
                    0, getInnerSize(), 1,
                    // Instruction de code
                    player, clickSlot, clicked, cursor);
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (isInWindow) {
            // Appelle une méthode
            setItemStack(slot, clickResult.getClicked());
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        updateAll(player); // FIXME: currently not properly client-predicted
        // Appelle une méthode
        playerInventory.setCursorItem(clickResult.getCursor());
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean changeHeld(Player player, int slot, int key) {
        // Affecte une valeur
        final int convertedKey = key == 40 ? PlayerInventoryUtils.OFFHAND_SLOT : key;
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final boolean isInWindow = isClickInWindow(slot);
        // Affecte une valeur
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Appelle une méthode
        final ItemStack clicked = isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot);
        // Appelle une méthode
        final ItemStack heldItem = playerInventory.getItemStack(convertedKey);
        // Affecte une valeur
        final AbstractInventory clickedInventory = isInWindow ? this : playerInventory;
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.changeHeld(clicked, heldItem);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (isInWindow) {
            // Appelle une méthode
            setItemStack(slot, clickResult.getClicked());
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            playerInventory.setItemStack(clickSlot, clickResult.getClicked());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        playerInventory.setItemStack(convertedKey, clickResult.getCursor());
        // Appelle une méthode
        callClickEvent(player, clickedInventory, slot, ClickType.CHANGE_HELD, clicked, playerInventory.getCursorItem());
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean middleClick(Player player, int slot) {
        // TODO
        // Appelle une méthode
        update(player);
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean drop(Player player, boolean all, int slot) {
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final boolean isInWindow = isClickInWindow(slot);
        // Affecte une valeur
        final boolean outsideDrop = slot == -999;
        // Affecte une valeur
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Affecte une valeur
        final ItemStack clicked = outsideDrop ?
                // Appelle une méthode
                ItemStack.AIR : (isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot));
        // Appelle une méthode
        final ItemStack cursor = playerInventory.getCursorItem();
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.drop(player, all, clickSlot, clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final ItemStack resultClicked = clickResult.getClicked();
        // Embranchement : vérifie une condition
        if (!outsideDrop && resultClicked != null) {
            // Embranchement : vérifie une condition
            if (isInWindow) {
                // Appelle une méthode
                setItemStack(slot, resultClicked);
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                playerInventory.setItemStack(clickSlot, resultClicked);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        playerInventory.setCursorItem(clickResult.getCursor());
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean dragging(Player player, List<Integer> slots, int button) {
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final ItemStack cursor = playerInventory.getCursorItem();

        // Appelle une méthode
        final ItemStack clickResult = clickProcessor.dragging(player, this, slots, button, cursor);
        // Embranchement : vérifie une condition
        if (clickResult == null) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        playerInventory.setCursorItem(clickResult);
        // Instruction de code
        updateAll(player); // FIXME: currently not properly client-predicted
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean doubleClick(Player player, int slot) {
        // Appelle une méthode
        final PlayerInventory playerInventory = player.getInventory();
        // Appelle une méthode
        final boolean isInWindow = isClickInWindow(slot);
        // Affecte une valeur
        final int clickSlot = isInWindow ? slot : slot - offset;
        // Affecte une valeur
        final ItemStack clicked = slot != -999 ?
                // Instruction de code
                (isInWindow ? getItemStack(slot) : playerInventory.getItemStack(clickSlot)) :
                // Instruction de code
                ItemStack.AIR;
        // Appelle une méthode
        final ItemStack cursor = playerInventory.getCursorItem();
        // Affecte une valeur
        final InventoryClickResult clickResult = clickProcessor.doubleClick(isInWindow ? this : playerInventory,
                // Instruction de code
                this, player, clickSlot, clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            updateAll(player);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        playerInventory.setCursorItem(clickResult.getCursor());
        // Instruction de code
        updateAll(player); // FIXME: currently not properly client-predicted
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private boolean isClickInWindow(int slot) {
        // Renvoie une valeur à l'appelant
        return slot < getSize();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void updateAll(Player player) {
        // Appelle une méthode
        player.getInventory().update();
        // Appelle une méthode
        update(player);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
