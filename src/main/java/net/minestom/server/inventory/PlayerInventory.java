// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.item.EntityEquipEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.ClickType;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.InventoryClickResult;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetCursorItemPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetPlayerInventorySlotPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.SetSlotPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.WindowItemsPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.utils.inventory.PlayerInventoryUtils.*;

/**
 * Represents the inventory of a {@link Player}, retrieved with {@link Player#getInventory()}.
 */
// Déclaration de type (classe/interface/enum/record)
public non-sealed class PlayerInventory extends AbstractInventory {
    // Affecte une valeur
    public static final int INVENTORY_SIZE = 46;
    // Affecte une valeur
    public static final int INNER_INVENTORY_SIZE = 36;

    // Affecte une valeur
    private ItemStack cursorItem = ItemStack.AIR;

    // Début d'une méthode/d'un bloc
    public PlayerInventory() {
        // Accès à l'objet courant/parent
        super(INVENTORY_SIZE);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void clear() {
        // Affecte une valeur
        cursorItem = ItemStack.AIR;
        // Accès à l'objet courant/parent
        super.clear();

        // Update equipments
        // Appelle une méthode
        viewers.forEach(viewer -> viewer.sendPacketToViewersAndSelf(viewer.getEquipmentsPacket()));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int getInnerSize() {
        // Renvoie une valeur à l'appelant
        return INNER_INVENTORY_SIZE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte getWindowId() {
        // Renvoie une valeur à l'appelant
        return 0;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private int getSlotId(EquipmentSlot slot, byte heldSlot) {
        // Renvoie une valeur à l'appelant
        return switch (slot) {
            // Embranchement multiple (switch/case)
            case MAIN_HAND -> heldSlot;
            // Embranchement multiple (switch/case)
            case OFF_HAND -> OFFHAND_SLOT;
            // Embranchement multiple (switch/case)
            default -> slot.armorSlot();
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private @Nullable EquipmentSlot getEquipmentSlot(int slot, byte heldSlot) {
        // Renvoie une valeur à l'appelant
        return switch (slot) {
            // Embranchement multiple (switch/case)
            case OFFHAND_SLOT -> EquipmentSlot.OFF_HAND;
            // Embranchement multiple (switch/case)
            case HELMET_SLOT -> EquipmentSlot.HELMET;
            // Embranchement multiple (switch/case)
            case CHESTPLATE_SLOT -> EquipmentSlot.CHESTPLATE;
            // Embranchement multiple (switch/case)
            case LEGGINGS_SLOT -> EquipmentSlot.LEGGINGS;
            // Embranchement multiple (switch/case)
            case BOOTS_SLOT -> EquipmentSlot.BOOTS;
            // Embranchement multiple (switch/case)
            default -> slot == heldSlot ? EquipmentSlot.MAIN_HAND : null;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemStack getEquipment(EquipmentSlot slot, byte heldSlot) {
        // Appelle une méthode
        final int slotId = getSlotId(slot, heldSlot);
        // Embranchement : vérifie une condition
        if (slotId < 0) return ItemStack.AIR;
        // Renvoie une valeur à l'appelant
        return getItemStack(slotId);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setEquipment(EquipmentSlot slot, byte heldSlot, ItemStack itemStack) {
        // Appelle une méthode
        final int slotId = getSlotId(slot, heldSlot);
        // Embranchement : vérifie une condition
        if (slotId < 0) Check.fail("PlayerInventory does not support {0} equipment", slot);

        // Appelle une méthode
        setItemStack(slotId, itemStack);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void update(Player player) {
        // Appelle une méthode
        player.sendPacket(createWindowItemsPacket());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item in player cursor.
     *
     * @return the cursor item
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getCursorItem() {
        // Renvoie une valeur à l'appelant
        return cursorItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player cursor item.
     *
     * @param cursorItem the new cursor item
     */
    // Début d'une méthode/d'un bloc
    public void setCursorItem(ItemStack cursorItem) {
        // Appelle une méthode
        setCursorItem(cursorItem, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the player cursor item.
     *
     * @param cursorItem the new cursor item
     * @param sendPacket true to send the update packet to the client, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public void setCursorItem(ItemStack cursorItem, boolean sendPacket) {
        // Embranchement : vérifie une condition
        if (this.cursorItem.equals(cursorItem)) return;
        // Accès à l'objet courant/parent
        this.cursorItem = cursorItem;
        // Embranchement : vérifie une condition
        if (sendPacket) sendPacketToViewers(new SetCursorItemPacket(cursorItem));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected void UNSAFE_itemInsert(int slot, ItemStack item, ItemStack previous, boolean sendPacket) {
        // Boucle : répète un bloc
        for (Player player : getViewers()) {
            // Appelle une méthode
            final EquipmentSlot equipmentSlot = getEquipmentSlot(slot, player.getHeldSlot());
            // Embranchement : vérifie une condition
            if (equipmentSlot == null) continue;

            // Appelle une méthode
            EntityEquipEvent entityEquipEvent = new EntityEquipEvent(player, item, equipmentSlot);
            // Appelle une méthode
            EventDispatcher.call(entityEquipEvent);
            // Appelle une méthode
            item = entityEquipEvent.getEquippedItem();

            // Appelle une méthode
            player.updateEquipmentAttributes(previous, item, equipmentSlot);
            // Appelle une méthode
            player.syncEquipment(equipmentSlot, item);
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        super.UNSAFE_itemInsert(slot, item, previous, sendPacket);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendSlotRefresh(int slot, ItemStack item) {
        // Embranchement : vérifie une condition
        if (slot < 0 || slot > INVENTORY_SIZE)
            // Renvoie une valeur à l'appelant
            return; // Sanity check
        // See note in PlayerInventoryUtils about why we do this conversion
        // Appelle une méthode
        boolean isPlayerInventorySlot = isPlayerInventorySlot(slot);
        // Affecte une valeur
        int packetSlot = isPlayerInventorySlot
                // Instruction de code
                ? convertMinestomSlotToPlayerInventorySlot(slot)
                // Appelle une méthode
                : convertMinestomSlotToWindowSlot(slot);

        // Instruction de code
        sendPacketToViewers(isPlayerInventorySlot
                // Instruction de code
                ? new SetPlayerInventorySlotPacket(packetSlot, item)
                // Appelle une méthode
                : new SetSlotPacket(0, 0, (short) packetSlot, item));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link WindowItemsPacket} with all the items in the inventory.
     *
     * @return a {@link WindowItemsPacket} with inventory items
     */
    // Début d'une méthode/d'un bloc
    private WindowItemsPacket createWindowItemsPacket() {
        // Affecte une valeur
        ItemStack[] convertedSlots = new ItemStack[INVENTORY_SIZE];
        // Boucle : répète un bloc
        for (int i = 0; i < itemStacks.length; i++) {
            // Appelle une méthode
            final int slot = convertMinestomSlotToWindowSlot(i);
            // Affecte une valeur
            convertedSlots[slot] = itemStacks[i];
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new WindowItemsPacket(0, 0, List.of(convertedSlots), cursorItem);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean leftClick(Player player, int slot) {
        // Appelle une méthode
        final ItemStack cursor = getCursorItem();
        // Appelle une méthode
        final ItemStack clicked = getItemStack(slot);
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.leftClick(clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setItemStack(slot, clickResult.getClicked());
        // Appelle une méthode
        setCursorItem(clickResult.getCursor());
        // Appelle une méthode
        callClickEvent(player, this, slot, ClickType.LEFT_CLICK, clicked, cursor);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean rightClick(Player player, int slot) {
        // Appelle une méthode
        final ItemStack cursor = getCursorItem();
        // Appelle une méthode
        final ItemStack clicked = getItemStack(slot);
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.rightClick(clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setItemStack(slot, clickResult.getClicked());
        // Appelle une méthode
        setCursorItem(clickResult.getCursor());
        // Appelle une méthode
        callClickEvent(player, this, slot, ClickType.RIGHT_CLICK, clicked, cursor);
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
        update();
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean drop(Player player, boolean all, int slot) {
        // Appelle une méthode
        final ItemStack cursor = getCursorItem();
        // Affecte une valeur
        final boolean outsideDrop = slot == -999;
        // Appelle une méthode
        final ItemStack clicked = outsideDrop ? ItemStack.AIR : getItemStack(slot);
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.drop(player, all, slot, clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        final ItemStack resultClicked = clickResult.getClicked();
        // Embranchement : vérifie une condition
        if (resultClicked != null && !outsideDrop) {
            // Appelle une méthode
            setItemStack(slot, resultClicked);
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setCursorItem(clickResult.getCursor());
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean shiftClick(Player player, int slot, int button) {
        // Appelle une méthode
        final ItemStack cursor = getCursorItem();
        // Appelle une méthode
        final ItemStack clicked = getItemStack(slot);
        // Affecte une valeur
        final boolean craftingGridClick = slot > 36 && slot <= 40;
        // Affecte une valeur
        final boolean craftingResultClick = slot == 36;
        // Affecte une valeur
        final boolean hotBarClick = slot < 9;

        // the client has different behavior for clicking based on where the item is in the inventory
        // Instruction de code
        InventoryClickResult clickResult;
        // Appelle une méthode
        final EquipmentSlot equipmentSlot = getEquipmentSlot(slot, player.getHeldSlot());
        // Embranchement : vérifie une condition
        if (equipmentSlot != null && (equipmentSlot.isArmor() || equipmentSlot == EquipmentSlot.OFF_HAND)) {
            // CASE: shift-clicking equipped armor or your off-hand item
            // we want to go through the inventory slots first
            // and then through the hotbar going left to right
            // Affecte une valeur
            clickResult = clickProcessor.shiftClick(
                    // Instruction de code
                    this, this,
                    // Instruction de code
                    9, INNER_INVENTORY_SIZE, 1,
                    // Instruction de code
                    player, slot, clicked, cursor
            // Fin d'un bloc/d'une expression
            );

            // Embranchement : vérifie une condition
            if (clickResult.isCancel()) {
                // Affecte une valeur
                clickResult = clickProcessor.shiftClick(
                        // Instruction de code
                        this, this,
                        // Instruction de code
                        0, 9, 1,
                        // Instruction de code
                        player, slot, clicked, cursor
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Embranchement : vérifie une condition
        } else if (craftingGridClick) {
            // CASE: shift-clicking an item from the crafting grid into your inventory
            // we want to prioritize the inventory from left-to-right and then the hotbar from left-to-right
            // Affecte une valeur
            clickResult = clickProcessor.shiftClick(
                    // Instruction de code
                    this, this,
                    // Instruction de code
                    9, INNER_INVENTORY_SIZE, 1,
                    // Instruction de code
                    player, slot, clicked, cursor
            // Fin d'un bloc/d'une expression
            );

            // Embranchement : vérifie une condition
            if(clickResult.isCancel()) {
                // Affecte une valeur
                clickResult = clickProcessor.shiftClick(
                        // Instruction de code
                        this, this,
                        // Instruction de code
                        0, 9, 1,
                        // Instruction de code
                        player, slot, clicked, cursor
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Embranchement : vérifie une condition
        } else if (craftingResultClick) {
            // CASE: shift-clicking an item from the crafting grid result into your inventory
            // we want to prioritize the hotbar from right-to-left and then the inventory from right-to-left
            // Affecte une valeur
            clickResult = clickProcessor.shiftClick(
                    // Instruction de code
                    this, this,
                    // Instruction de code
                    9, 0, -1,
                    // Instruction de code
                    player, slot, clicked, cursor
            // Fin d'un bloc/d'une expression
            );

            // Embranchement : vérifie une condition
            if(clickResult.isCancel()) {
                // Affecte une valeur
                clickResult = clickProcessor.shiftClick(
                        // Instruction de code
                        this, this,
                        // Instruction de code
                        INNER_INVENTORY_SIZE, 9, -1,
                        // Instruction de code
                        player, slot, clicked, cursor
                // Fin d'un bloc/d'une expression
                );
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // CASE: shift-clicking an item in the hotbar or inventory
            // Affecte une valeur
            clickResult = clickProcessor.shiftClick(
                    // Instruction de code
                    this, this,
                    // Instruction de code
                    (hotBarClick ? 9 : 0), (hotBarClick ? INNER_INVENTORY_SIZE : 9), 1,
                    // Instruction de code
                    player, slot, clicked, cursor
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        setItemStack(slot, clickResult.getClicked());
        // Appelle une méthode
        setCursorItem(clickResult.getCursor());
        // Instruction de code
        update(); // FIXME: currently not properly client-predicted
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean changeHeld(Player player, int slot, int key) {
        // Appelle une méthode
        final ItemStack cursorItem = getCursorItem();
        // Embranchement : vérifie une condition
        if (!cursorItem.isAir()) return false;
        // Appelle une méthode
        final ItemStack heldItem = getItemStack(key);
        // Appelle une méthode
        final ItemStack clicked = getItemStack(slot);
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.changeHeld(clicked, heldItem);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setItemStack(slot, clickResult.getClicked());
        // Appelle une méthode
        setItemStack(key, clickResult.getCursor());
        // Appelle une méthode
        callClickEvent(player, this, slot, ClickType.CHANGE_HELD, clicked, cursorItem);
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean dragging(Player player, List<Integer> slots, int button) {
        // Appelle une méthode
        final ItemStack cursor = getCursorItem();

        // Appelle une méthode
        final ItemStack clickResult = clickProcessor.dragging(player, this, slots, button, cursor);
        // Embranchement : vérifie une condition
        if (clickResult == null) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setCursorItem(clickResult);
        // Instruction de code
        update(); // FIXME: currently not properly client-predicted
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean doubleClick(Player player, int slot) {
        // Appelle une méthode
        final ItemStack cursor = getCursorItem();
        // Appelle une méthode
        final ItemStack clicked = getItemStack(slot);
        // Appelle une méthode
        final InventoryClickResult clickResult = clickProcessor.doubleClick(this, this, player, slot, clicked, cursor);
        // Embranchement : vérifie une condition
        if (clickResult.isCancel()) {
            // Appelle une méthode
            update();
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        setCursorItem(clickResult.getCursor());
        // Instruction de code
        update(); // FIXME: currently not properly client-predicted
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
