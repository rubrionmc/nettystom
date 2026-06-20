// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click;

// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.EquipmentSlot;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.inventory.InventoryClickEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.TransactionType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.item.component.Equippable;
// Import d'une classe nécessaire
import net.minestom.server.registry.RegistryTag;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.BiFunction;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class InventoryClickProcessor {

    // Début d'une méthode/d'un bloc
    public InventoryClickResult leftClick(ItemStack clicked, ItemStack cursor) {
        // Embranchement : vérifie une condition
        if (cursor.isSimilar(clicked)) {
            // Try to stack items
            // Appelle une méthode
            final int totalAmount = cursor.amount() + clicked.amount();
            // Appelle une méthode
            final int maxSize = cursor.maxStackSize();
            // Embranchement : vérifie une condition
            if (!MathUtils.isBetween(totalAmount, 0, clicked.maxStackSize())) {
                // Size is too big, stack as much as possible into clicked
                // Appelle une méthode
                cursor = cursor.withAmount(totalAmount - maxSize);
                // Appelle une méthode
                clicked = clicked.withAmount(maxSize);
            // Branche alternative de la condition
            } else {
                // Merge cursor item clicked
                // Affecte une valeur
                cursor = ItemStack.AIR;
                // Appelle une méthode
                clicked = clicked.withAmount(totalAmount);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Items are not compatible, swap them
            // Affecte une valeur
            var temp = clicked;

            // Affecte une valeur
            clicked = cursor;
            // Affecte une valeur
            cursor = temp;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new InventoryClickResult(clicked, cursor);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InventoryClickResult rightClick(ItemStack clicked, ItemStack cursor) {
        // Appelle une méthode
        final var result = new InventoryClickResult(clicked, cursor);

        // Embranchement : vérifie une condition
        if (clicked.isSimilar(cursor)) {
            // Items can be stacked
            // Appelle une méthode
            final int amount = clicked.amount() + 1;
            // Embranchement : vérifie une condition
            if (!MathUtils.isBetween(amount, 0, clicked.maxStackSize())) {
                // Size too large, stop here
                // Renvoie une valeur à l'appelant
                return result;
            // Branche alternative de la condition
            } else {
                // Add 1 to clicked
                // Appelle une méthode
                result.setCursor(cursor.withAmount(operand -> operand - 1));
                // Appelle une méthode
                result.setClicked(clicked.withAmount(amount));
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Items cannot be stacked
            // Embranchement : vérifie une condition
            if (cursor.isAir()) {
                // Take half of clicked
                // Appelle une méthode
                final int amount = (int) Math.ceil((double) clicked.amount() / 2d);
                // Appelle une méthode
                result.setCursor(clicked.withAmount(amount));
                // Appelle une méthode
                result.setClicked(clicked.withAmount(operand -> operand / 2));
            // Branche alternative de la condition
            } else {
                // Embranchement : vérifie une condition
                if (clicked.isAir()) {
                    // Put 1 to clicked
                    // Appelle une méthode
                    result.setCursor(cursor.withAmount(operand -> operand - 1));
                    // Appelle une méthode
                    result.setClicked(cursor.withAmount(1));
                // Branche alternative de la condition
                } else {
                    // Swap items
                    // Appelle une méthode
                    result.setCursor(clicked);
                    // Appelle une méthode
                    result.setClicked(cursor);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public InventoryClickResult changeHeld(ItemStack clicked, ItemStack cursor) {
        // Renvoie une valeur à l'appelant
        return new InventoryClickResult(cursor, clicked); // Swap items
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public InventoryClickResult shiftClick(AbstractInventory inventory, AbstractInventory targetInventory,
                                                    // Instruction de code
                                                    int start, int end, int step,
                                                    // Instruction de code
                                                    Player player, int slot,
                                                    // Début d'une méthode/d'un bloc
                                                    ItemStack clicked, ItemStack cursor) {
        // Appelle une méthode
        final InventoryClickResult clickResult = new InventoryClickResult(clicked, cursor);
        // Embranchement : vérifie une condition
        if (clicked.isAir()) return clickResult.cancelled();

        // Affecte une valeur
        final boolean craftingGridClick = slot >= 36 && slot <= 40;

        // Handle armor and off-hand equippables
        // Embranchement : vérifie une condition
        if (inventory instanceof PlayerInventory && targetInventory instanceof PlayerInventory) {
            // Appelle une méthode
            Equippable equippableComponent = clicked.get(DataComponents.EQUIPPABLE);
            // Embranchement : vérifie une condition
            if (equippableComponent != null) {
                // Appelle une méthode
                final EquipmentSlot equipmentSlot = equippableComponent.slot();
                // Appelle une méthode
                RegistryTag<EntityType> allowed = equippableComponent.allowedEntities();
                // Embranchement : vérifie une condition
                if ((allowed == null || allowed.contains(EntityType.PLAYER))
                        // Instruction de code
                        && (equipmentSlot.isArmor() || equipmentSlot == EquipmentSlot.OFF_HAND)
                        // Début d'une méthode/d'un bloc
                        && !craftingGridClick) {
                    // Shift-click equip
                    // Appelle une méthode
                    final ItemStack currentItem = player.getEquipment(equipmentSlot);
                    // Embranchement : vérifie une condition
                    if (currentItem.isAir()) {
                        // Appelle une méthode
                        player.setEquipment(equipmentSlot, clicked);
                        // Renvoie une valeur à l'appelant
                        return new InventoryClickResult(ItemStack.AIR, cursor);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        clickResult.setCancel(true);
        // Affecte une valeur
        final var pair = TransactionType.ADD.process(targetInventory, clicked, (index, itemStack) -> {
            // Embranchement : vérifie une condition
            if (inventory == targetInventory && index == slot) {
                // Renvoie une valeur à l'appelant
                return false; // Prevent item lose/duplication
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            clickResult.setCancel(false);
            // Renvoie une valeur à l'appelant
            return true;
        // Instruction de code
        }, start, end, step);

        // Appelle une méthode
        final ItemStack itemResult = pair.left();
        // Appelle une méthode
        final Map<Integer, ItemStack> itemChangesMap = pair.right();
        // Début d'une méthode/d'un bloc
        itemChangesMap.forEach((Integer s, ItemStack itemStack) -> {
            // Appelle une méthode
            targetInventory.setItemStack(s, itemStack);
            // Appelle une méthode
            callClickEvent(player, targetInventory, s, ClickType.SHIFT_CLICK, itemStack, cursor);
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        clickResult.setClicked(itemResult);
        // Renvoie une valeur à l'appelant
        return clickResult;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public @Nullable ItemStack dragging(Player player, AbstractInventory inventory,
                                        // Début d'une méthode/d'un bloc
                                        List<Integer> slots, int button, ItemStack cursor) {
        // Drag instruction
        // Embranchement : vérifie une condition
        if (button == 2) {
            // End left
            // Appelle une méthode
            final int slotCount = slots.size();
            // Appelle une méthode
            final int cursorAmount = cursor.amount();
            // Embranchement : vérifie une condition
            if (slotCount > cursorAmount) return null;

            // Should be size of each defined slot (if not full)
            // Appelle une méthode
            final int slotSize = (int) ((float) cursorAmount / (float) slotCount);
            // Place all waiting drag action
            // Affecte une valeur
            int finalCursorAmount = cursorAmount;
            // Boucle : répète un bloc
            for (int slot : slots) {
                // Appelle une méthode
                final boolean isInWindow = slot < inventory.getSize();
                // Appelle une méthode
                final var inv = isInWindow ? inventory : player.getInventory();
                // Appelle une méthode
                final int s = isInWindow ? slot : slot - inventory.getSize();

                // Appelle une méthode
                ItemStack slotItem = inv.getItemStack(s);
                // Appelle une méthode
                final int amount = slotItem.amount();
                // Embranchement : vérifie une condition
                if (cursor.isSimilar(slotItem)) {
                    // Embranchement : vérifie une condition
                    if (MathUtils.isBetween(amount + slotSize, 0, slotItem.maxStackSize())) {
                        // Append divided amount to slot
                        // Appelle une méthode
                        slotItem = slotItem.withAmount(a -> a + slotSize);
                        // Instruction de code
                        finalCursorAmount -= slotSize;
                    // Branche alternative de la condition
                    } else {
                        // Amount too big, fill as much as possible
                        // Appelle une méthode
                        final int maxSize = cursor.maxStackSize();
                        // Affecte une valeur
                        final int removedAmount = maxSize - amount;
                        // Appelle une méthode
                        slotItem = slotItem.withAmount(maxSize);
                        // Instruction de code
                        finalCursorAmount -= removedAmount;
                    // Fin d'un bloc/d'une expression
                    }
                // Embranchement : vérifie une condition
                } else if (slotItem.isAir()) {
                    // Slot is empty, add divided amount
                    // Appelle une méthode
                    slotItem = cursor.withAmount(slotSize);
                    // Instruction de code
                    finalCursorAmount -= slotSize;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                inv.setItemStack(s, slotItem);
                // Appelle une méthode
                callClickEvent(player, inv, s, ClickType.LEFT_DRAGGING, slotItem, cursor);
            // Fin d'un bloc/d'une expression
            }
            // Update the cursor
            // Appelle une méthode
            cursor = cursor.withAmount(finalCursorAmount);
        // Embranchement : vérifie une condition
        } else if (button == 6) {
            // End right
            // Appelle une méthode
            int cursorAmount = cursor.amount();
            // Embranchement : vérifie une condition
            if (slots.size() > cursorAmount) return null;
            // Place all waiting drag action
            // Affecte une valeur
            int finalCursorAmount = cursorAmount;
            // Boucle : répète un bloc
            for (int slot : slots) {
                // Appelle une méthode
                final boolean isInWindow = slot < inventory.getSize();
                // Appelle une méthode
                final var inv = isInWindow ? inventory : player.getInventory();
                // Appelle une méthode
                final int s = isInWindow ? slot : slot - inventory.getSize();

                // Appelle une méthode
                ItemStack slotItem = inv.getItemStack(s);
                // Embranchement : vérifie une condition
                if (cursor.isSimilar(slotItem)) {
                    // Compatible item in the slot, increment by 1
                    // Appelle une méthode
                    final int amount = slotItem.amount() + 1;
                    // Embranchement : vérifie une condition
                    if (MathUtils.isBetween(amount, 0, slotItem.maxStackSize())) {
                        // Appelle une méthode
                        slotItem = slotItem.withAmount(amount);
                        // Instruction de code
                        finalCursorAmount -= 1;
                    // Fin d'un bloc/d'une expression
                    }
                // Embranchement : vérifie une condition
                } else if (slotItem.isAir()) {
                    // No item at the slot, place one
                    // Appelle une méthode
                    slotItem = cursor.withAmount(1);
                    // Instruction de code
                    finalCursorAmount -= 1;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                inv.setItemStack(s, slotItem);
                // Appelle une méthode
                callClickEvent(player, inv, s, ClickType.RIGHT_DRAGGING, slotItem, cursor);
            // Fin d'un bloc/d'une expression
            }
            // Update the cursor
            // Appelle une méthode
            cursor = cursor.withAmount(finalCursorAmount);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return cursor;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public InventoryClickResult doubleClick(AbstractInventory clickedInventory, AbstractInventory inventory, Player player, int slot,
                                                     // Début d'une méthode/d'un bloc
                                                     ItemStack clicked, ItemStack cursor) {
        // Appelle une méthode
        InventoryClickResult clickResult = new InventoryClickResult(clicked, cursor);
        // Embranchement : vérifie une condition
        if (cursor.isAir()) return clickResult.cancelled();

        // Appelle une méthode
        final int amount = cursor.amount();
        // Appelle une méthode
        final int maxSize = cursor.maxStackSize();
        // Affecte une valeur
        final int remainingAmount = maxSize - amount;
        // Embranchement : vérifie une condition
        if (remainingAmount == 0) {
            // Item is already full
            // Renvoie une valeur à l'appelant
            return clickResult;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        final BiFunction<AbstractInventory, ItemStack, ItemStack> func = (inv, rest) -> {
            // Affecte une valeur
            var pair = TransactionType.TAKE.process(inv, rest, (index, itemStack) -> {
                // Prevent item loss/duplication
                // Renvoie une valeur à l'appelant
                return index != slot || clickedInventory != inv;
            // Fin d'un bloc/d'une expression
            });
            // Appelle une méthode
            final ItemStack itemResult = pair.left();
            // Appelle une méthode
            var itemChangesMap = pair.right();
            // Début d'une méthode/d'un bloc
            itemChangesMap.forEach((Integer s, ItemStack itemStack) -> {
                // Appelle une méthode
                inv.setItemStack(s, itemStack);
                // Appelle une méthode
                callClickEvent(player, inv, s, ClickType.DOUBLE_CLICK, itemStack, cursor);
            // Fin d'un bloc/d'une expression
            });
            // Renvoie une valeur à l'appelant
            return itemResult;
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        ItemStack remain = cursor.withAmount(remainingAmount);
        // Appelle une méthode
        final var playerInventory = player.getInventory();
        // Retrieve remain
        // Embranchement : vérifie une condition
        if (Objects.equals(clickedInventory, inventory)) {
            // Clicked inside inventory
            // Appelle une méthode
            remain = func.apply(inventory, remain);
            // Embranchement : vérifie une condition
            if (!remain.isAir()) {
                // Appelle une méthode
                remain = func.apply(playerInventory, remain);
            // Fin d'un bloc/d'une expression
            }
        // Embranchement : vérifie une condition
        } else if (clickedInventory == playerInventory) {
            // Clicked inside player inventory, but with another inventory open
            // Appelle une méthode
            remain = func.apply(playerInventory, remain);
            // Embranchement : vérifie une condition
            if (!remain.isAir()) {
                // Appelle une méthode
                remain = func.apply(inventory, remain);
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Clicked inside player inventory
            // Appelle une méthode
            remain = func.apply(playerInventory, remain);
        // Fin d'un bloc/d'une expression
        }

        // Update cursor based on the remaining
        // Embranchement : vérifie une condition
        if (remain.isAir()) {
            // Item has been filled
            // Appelle une méthode
            clickResult.setCursor(cursor.withAmount(maxSize));
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            final int tookAmount = remainingAmount - remain.amount();
            // Appelle une méthode
            clickResult.setCursor(cursor.withAmount(amount + tookAmount));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return clickResult;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    public InventoryClickResult drop(Player player,
                                              // Début d'une méthode/d'un bloc
                                              boolean all, int slot, ItemStack clicked, ItemStack cursor) {
        // Appelle une méthode
        final InventoryClickResult clickResult = new InventoryClickResult(clicked, cursor);

        // Embranchement : vérifie une condition
        if (slot == -999) {
            // Click outside
            // Embranchement : vérifie une condition
            if (all) {
                // Left (drop all)
                // Appelle une méthode
                final int amount = cursor.amount();
                // Appelle une méthode
                final ItemStack dropItem = cursor.withAmount(amount);
                // Appelle une méthode
                final boolean dropResult = player.dropItem(dropItem);
                // Appelle une méthode
                clickResult.setCancel(!dropResult);
                // Embranchement : vérifie une condition
                if (dropResult) {
                    // Affecte une valeur
                    cursor = ItemStack.AIR;
                // Fin d'un bloc/d'une expression
                }
            // Branche alternative de la condition
            } else {
                // Right (drop 1)
                // Appelle une méthode
                final ItemStack dropItem = cursor.withAmount(1);
                // Appelle une méthode
                final boolean dropResult = player.dropItem(dropItem);
                // Appelle une méthode
                clickResult.setCancel(!dropResult);
                // Embranchement : vérifie une condition
                if (dropResult) {
                    // Appelle une méthode
                    final int amount = cursor.amount();
                    // Affecte une valeur
                    final int newAmount = amount - 1;
                    // Appelle une méthode
                    cursor = cursor.withAmount(newAmount);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (all) {
                // Ctrl + Drop key Q (drop all)
                // Appelle une méthode
                final int amount = clicked.amount();
                // Appelle une méthode
                final ItemStack dropItem = clicked.withAmount(amount);
                // Appelle une méthode
                final boolean dropResult = player.dropItem(dropItem);
                // Appelle une méthode
                clickResult.setCancel(!dropResult);
                // Embranchement : vérifie une condition
                if (dropResult) {
                    // Affecte une valeur
                    clicked = ItemStack.AIR;
                // Fin d'un bloc/d'une expression
                }
            // Branche alternative de la condition
            } else {
                // Drop key Q (drop 1)
                // Appelle une méthode
                final ItemStack dropItem = clicked.withAmount(1);
                // Appelle une méthode
                final boolean dropResult = player.dropItem(dropItem);
                // Appelle une méthode
                clickResult.setCancel(!dropResult);
                // Embranchement : vérifie une condition
                if (dropResult) {
                    // Appelle une méthode
                    final int amount = clicked.amount();
                    // Affecte une valeur
                    final int newAmount = amount - 1;
                    // Appelle une méthode
                    clicked = clicked.withAmount(newAmount);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        clickResult.setClicked(clicked);
        // Appelle une méthode
        clickResult.setCursor(cursor);

        // Renvoie une valeur à l'appelant
        return clickResult;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private void callClickEvent(Player player, AbstractInventory inventory, int slot,
                                // Début d'une méthode/d'un bloc
                                ClickType clickType, ItemStack clicked, ItemStack cursor) {
        // Appelle une méthode
        EventDispatcher.call(new InventoryClickEvent(inventory, player, slot, clickType, clicked, cursor));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}