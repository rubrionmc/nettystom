// Package declaration for this file
package net.minestom.server.inventory.click;

// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.EquipmentSlot;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.inventory.InventoryClickEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.inventory.TransactionType;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.component.Equippable;
// Import of a required class
import net.minestom.server.registry.RegistryTag;
// Import of a required class
import net.minestom.server.utils.MathUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.BiFunction;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class InventoryClickProcessor {

    // Start of a method/block
    public InventoryClickResult leftClick(ItemStack clicked, ItemStack cursor) {
        // Branch: checks a condition
        if (cursor.isSimilar(clicked)) {
            // Try to stack items
            // Calls a method
            final int totalAmount = cursor.amount() + clicked.amount();
            // Calls a method
            final int maxSize = cursor.maxStackSize();
            // Branch: checks a condition
            if (!MathUtils.isBetween(totalAmount, 0, clicked.maxStackSize())) {
                // Size is too big, stack as much as possible into clicked
                // Calls a method
                cursor = cursor.withAmount(totalAmount - maxSize);
                // Calls a method
                clicked = clicked.withAmount(maxSize);
            // Alternative branch of the condition
            } else {
                // Merge cursor item clicked
                // Assigns a value
                cursor = ItemStack.AIR;
                // Calls a method
                clicked = clicked.withAmount(totalAmount);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Items are not compatible, swap them
            // Assigns a value
            var temp = clicked;

            // Assigns a value
            clicked = cursor;
            // Assigns a value
            cursor = temp;
        // End of a block/expression
        }
        // Returns a value to the caller
        return new InventoryClickResult(clicked, cursor);
    // End of a block/expression
    }

    // Start of a method/block
    public InventoryClickResult rightClick(ItemStack clicked, ItemStack cursor) {
        // Calls a method
        final var result = new InventoryClickResult(clicked, cursor);

        // Branch: checks a condition
        if (clicked.isSimilar(cursor)) {
            // Items can be stacked
            // Calls a method
            final int amount = clicked.amount() + 1;
            // Branch: checks a condition
            if (!MathUtils.isBetween(amount, 0, clicked.maxStackSize())) {
                // Size too large, stop here
                // Returns a value to the caller
                return result;
            // Alternative branch of the condition
            } else {
                // Add 1 to clicked
                // Calls a method
                result.setCursor(cursor.withAmount(operand -> operand - 1));
                // Calls a method
                result.setClicked(clicked.withAmount(amount));
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Items cannot be stacked
            // Branch: checks a condition
            if (cursor.isAir()) {
                // Take half of clicked
                // Calls a method
                final int amount = (int) Math.ceil((double) clicked.amount() / 2d);
                // Calls a method
                result.setCursor(clicked.withAmount(amount));
                // Calls a method
                result.setClicked(clicked.withAmount(operand -> operand / 2));
            // Alternative branch of the condition
            } else {
                // Branch: checks a condition
                if (clicked.isAir()) {
                    // Put 1 to clicked
                    // Calls a method
                    result.setCursor(cursor.withAmount(operand -> operand - 1));
                    // Calls a method
                    result.setClicked(cursor.withAmount(1));
                // Alternative branch of the condition
                } else {
                    // Swap items
                    // Calls a method
                    result.setCursor(clicked);
                    // Calls a method
                    result.setClicked(cursor);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    public InventoryClickResult changeHeld(ItemStack clicked, ItemStack cursor) {
        // Returns a value to the caller
        return new InventoryClickResult(cursor, clicked); // Swap items
    // End of a block/expression
    }

    // Code statement
    public InventoryClickResult shiftClick(AbstractInventory inventory, AbstractInventory targetInventory,
                                                    // Code statement
                                                    int start, int end, int step,
                                                    // Code statement
                                                    Player player, int slot,
                                                    // Start of a method/block
                                                    ItemStack clicked, ItemStack cursor) {
        // Calls a method
        final InventoryClickResult clickResult = new InventoryClickResult(clicked, cursor);
        // Branch: checks a condition
        if (clicked.isAir()) return clickResult.cancelled();

        // Assigns a value
        final boolean craftingGridClick = slot >= 36 && slot <= 40;

        // Handle armor and off-hand equippables
        // Branch: checks a condition
        if (inventory instanceof PlayerInventory && targetInventory instanceof PlayerInventory) {
            // Calls a method
            Equippable equippableComponent = clicked.get(DataComponents.EQUIPPABLE);
            // Branch: checks a condition
            if (equippableComponent != null) {
                // Calls a method
                final EquipmentSlot equipmentSlot = equippableComponent.slot();
                // Calls a method
                RegistryTag<EntityType> allowed = equippableComponent.allowedEntities();
                // Branch: checks a condition
                if ((allowed == null || allowed.contains(EntityType.PLAYER))
                        // Code statement
                        && (equipmentSlot.isArmor() || equipmentSlot == EquipmentSlot.OFF_HAND)
                        // Start of a method/block
                        && !craftingGridClick) {
                    // Shift-click equip
                    // Calls a method
                    final ItemStack currentItem = player.getEquipment(equipmentSlot);
                    // Branch: checks a condition
                    if (currentItem.isAir()) {
                        // Calls a method
                        player.setEquipment(equipmentSlot, clicked);
                        // Returns a value to the caller
                        return new InventoryClickResult(ItemStack.AIR, cursor);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        clickResult.setCancel(true);
        // Assigns a value
        final var pair = TransactionType.ADD.process(targetInventory, clicked, (index, itemStack) -> {
            // Branch: checks a condition
            if (inventory == targetInventory && index == slot) {
                // Returns a value to the caller
                return false; // Prevent item lose/duplication
            // End of a block/expression
            }

            // Calls a method
            clickResult.setCancel(false);
            // Returns a value to the caller
            return true;
        // Code statement
        }, start, end, step);

        // Calls a method
        final ItemStack itemResult = pair.left();
        // Calls a method
        final Map<Integer, ItemStack> itemChangesMap = pair.right();
        // Start of a method/block
        itemChangesMap.forEach((Integer s, ItemStack itemStack) -> {
            // Calls a method
            targetInventory.setItemStack(s, itemStack);
            // Calls a method
            callClickEvent(player, targetInventory, s, ClickType.SHIFT_CLICK, itemStack, cursor);
        // End of a block/expression
        });

        // Calls a method
        clickResult.setClicked(itemResult);
        // Returns a value to the caller
        return clickResult;
    // End of a block/expression
    }

    // Code statement
    public @Nullable ItemStack dragging(Player player, AbstractInventory inventory,
                                        // Start of a method/block
                                        List<Integer> slots, int button, ItemStack cursor) {
        // Drag instruction
        // Branch: checks a condition
        if (button == 2) {
            // End left
            // Calls a method
            final int slotCount = slots.size();
            // Calls a method
            final int cursorAmount = cursor.amount();
            // Branch: checks a condition
            if (slotCount > cursorAmount) return null;

            // Should be size of each defined slot (if not full)
            // Calls a method
            final int slotSize = (int) ((float) cursorAmount / (float) slotCount);
            // Place all waiting drag action
            // Assigns a value
            int finalCursorAmount = cursorAmount;
            // Loop: repeats a block
            for (int slot : slots) {
                // Calls a method
                final boolean isInWindow = slot < inventory.getSize();
                // Calls a method
                final var inv = isInWindow ? inventory : player.getInventory();
                // Calls a method
                final int s = isInWindow ? slot : slot - inventory.getSize();

                // Calls a method
                ItemStack slotItem = inv.getItemStack(s);
                // Calls a method
                final int amount = slotItem.amount();
                // Branch: checks a condition
                if (cursor.isSimilar(slotItem)) {
                    // Branch: checks a condition
                    if (MathUtils.isBetween(amount + slotSize, 0, slotItem.maxStackSize())) {
                        // Append divided amount to slot
                        // Calls a method
                        slotItem = slotItem.withAmount(a -> a + slotSize);
                        // Code statement
                        finalCursorAmount -= slotSize;
                    // Alternative branch of the condition
                    } else {
                        // Amount too big, fill as much as possible
                        // Calls a method
                        final int maxSize = cursor.maxStackSize();
                        // Assigns a value
                        final int removedAmount = maxSize - amount;
                        // Calls a method
                        slotItem = slotItem.withAmount(maxSize);
                        // Code statement
                        finalCursorAmount -= removedAmount;
                    // End of a block/expression
                    }
                // Branch: checks a condition
                } else if (slotItem.isAir()) {
                    // Slot is empty, add divided amount
                    // Calls a method
                    slotItem = cursor.withAmount(slotSize);
                    // Code statement
                    finalCursorAmount -= slotSize;
                // End of a block/expression
                }
                // Calls a method
                inv.setItemStack(s, slotItem);
                // Calls a method
                callClickEvent(player, inv, s, ClickType.LEFT_DRAGGING, slotItem, cursor);
            // End of a block/expression
            }
            // Update the cursor
            // Calls a method
            cursor = cursor.withAmount(finalCursorAmount);
        // Branch: checks a condition
        } else if (button == 6) {
            // End right
            // Calls a method
            int cursorAmount = cursor.amount();
            // Branch: checks a condition
            if (slots.size() > cursorAmount) return null;
            // Place all waiting drag action
            // Assigns a value
            int finalCursorAmount = cursorAmount;
            // Loop: repeats a block
            for (int slot : slots) {
                // Calls a method
                final boolean isInWindow = slot < inventory.getSize();
                // Calls a method
                final var inv = isInWindow ? inventory : player.getInventory();
                // Calls a method
                final int s = isInWindow ? slot : slot - inventory.getSize();

                // Calls a method
                ItemStack slotItem = inv.getItemStack(s);
                // Branch: checks a condition
                if (cursor.isSimilar(slotItem)) {
                    // Compatible item in the slot, increment by 1
                    // Calls a method
                    final int amount = slotItem.amount() + 1;
                    // Branch: checks a condition
                    if (MathUtils.isBetween(amount, 0, slotItem.maxStackSize())) {
                        // Calls a method
                        slotItem = slotItem.withAmount(amount);
                        // Code statement
                        finalCursorAmount -= 1;
                    // End of a block/expression
                    }
                // Branch: checks a condition
                } else if (slotItem.isAir()) {
                    // No item at the slot, place one
                    // Calls a method
                    slotItem = cursor.withAmount(1);
                    // Code statement
                    finalCursorAmount -= 1;
                // End of a block/expression
                }
                // Calls a method
                inv.setItemStack(s, slotItem);
                // Calls a method
                callClickEvent(player, inv, s, ClickType.RIGHT_DRAGGING, slotItem, cursor);
            // End of a block/expression
            }
            // Update the cursor
            // Calls a method
            cursor = cursor.withAmount(finalCursorAmount);
        // End of a block/expression
        }

        // Returns a value to the caller
        return cursor;
    // End of a block/expression
    }

    // Code statement
    public InventoryClickResult doubleClick(AbstractInventory clickedInventory, AbstractInventory inventory, Player player, int slot,
                                                     // Start of a method/block
                                                     ItemStack clicked, ItemStack cursor) {
        // Calls a method
        InventoryClickResult clickResult = new InventoryClickResult(clicked, cursor);
        // Branch: checks a condition
        if (cursor.isAir()) return clickResult.cancelled();

        // Calls a method
        final int amount = cursor.amount();
        // Calls a method
        final int maxSize = cursor.maxStackSize();
        // Assigns a value
        final int remainingAmount = maxSize - amount;
        // Branch: checks a condition
        if (remainingAmount == 0) {
            // Item is already full
            // Returns a value to the caller
            return clickResult;
        // End of a block/expression
        }
        // Assigns a value
        final BiFunction<AbstractInventory, ItemStack, ItemStack> func = (inv, rest) -> {
            // Assigns a value
            var pair = TransactionType.TAKE.process(inv, rest, (index, itemStack) -> {
                // Prevent item loss/duplication
                // Returns a value to the caller
                return index != slot || clickedInventory != inv;
            // End of a block/expression
            });
            // Calls a method
            final ItemStack itemResult = pair.left();
            // Calls a method
            var itemChangesMap = pair.right();
            // Start of a method/block
            itemChangesMap.forEach((Integer s, ItemStack itemStack) -> {
                // Calls a method
                inv.setItemStack(s, itemStack);
                // Calls a method
                callClickEvent(player, inv, s, ClickType.DOUBLE_CLICK, itemStack, cursor);
            // End of a block/expression
            });
            // Returns a value to the caller
            return itemResult;
        // End of a block/expression
        };

        // Calls a method
        ItemStack remain = cursor.withAmount(remainingAmount);
        // Calls a method
        final var playerInventory = player.getInventory();
        // Retrieve remain
        // Branch: checks a condition
        if (Objects.equals(clickedInventory, inventory)) {
            // Clicked inside inventory
            // Calls a method
            remain = func.apply(inventory, remain);
            // Branch: checks a condition
            if (!remain.isAir()) {
                // Calls a method
                remain = func.apply(playerInventory, remain);
            // End of a block/expression
            }
        // Branch: checks a condition
        } else if (clickedInventory == playerInventory) {
            // Clicked inside player inventory, but with another inventory open
            // Calls a method
            remain = func.apply(playerInventory, remain);
            // Branch: checks a condition
            if (!remain.isAir()) {
                // Calls a method
                remain = func.apply(inventory, remain);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Clicked inside player inventory
            // Calls a method
            remain = func.apply(playerInventory, remain);
        // End of a block/expression
        }

        // Update cursor based on the remaining
        // Branch: checks a condition
        if (remain.isAir()) {
            // Item has been filled
            // Calls a method
            clickResult.setCursor(cursor.withAmount(maxSize));
        // Alternative branch of the condition
        } else {
            // Calls a method
            final int tookAmount = remainingAmount - remain.amount();
            // Calls a method
            clickResult.setCursor(cursor.withAmount(amount + tookAmount));
        // End of a block/expression
        }
        // Returns a value to the caller
        return clickResult;
    // End of a block/expression
    }

    // Code statement
    public InventoryClickResult drop(Player player,
                                              // Start of a method/block
                                              boolean all, int slot, ItemStack clicked, ItemStack cursor) {
        // Calls a method
        final InventoryClickResult clickResult = new InventoryClickResult(clicked, cursor);

        // Branch: checks a condition
        if (slot == -999) {
            // Click outside
            // Branch: checks a condition
            if (all) {
                // Left (drop all)
                // Calls a method
                final int amount = cursor.amount();
                // Calls a method
                final ItemStack dropItem = cursor.withAmount(amount);
                // Calls a method
                final boolean dropResult = player.dropItem(dropItem);
                // Calls a method
                clickResult.setCancel(!dropResult);
                // Branch: checks a condition
                if (dropResult) {
                    // Assigns a value
                    cursor = ItemStack.AIR;
                // End of a block/expression
                }
            // Alternative branch of the condition
            } else {
                // Right (drop 1)
                // Calls a method
                final ItemStack dropItem = cursor.withAmount(1);
                // Calls a method
                final boolean dropResult = player.dropItem(dropItem);
                // Calls a method
                clickResult.setCancel(!dropResult);
                // Branch: checks a condition
                if (dropResult) {
                    // Calls a method
                    final int amount = cursor.amount();
                    // Assigns a value
                    final int newAmount = amount - 1;
                    // Calls a method
                    cursor = cursor.withAmount(newAmount);
                // End of a block/expression
                }
            // End of a block/expression
            }

        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (all) {
                // Ctrl + Drop key Q (drop all)
                // Calls a method
                final int amount = clicked.amount();
                // Calls a method
                final ItemStack dropItem = clicked.withAmount(amount);
                // Calls a method
                final boolean dropResult = player.dropItem(dropItem);
                // Calls a method
                clickResult.setCancel(!dropResult);
                // Branch: checks a condition
                if (dropResult) {
                    // Assigns a value
                    clicked = ItemStack.AIR;
                // End of a block/expression
                }
            // Alternative branch of the condition
            } else {
                // Drop key Q (drop 1)
                // Calls a method
                final ItemStack dropItem = clicked.withAmount(1);
                // Calls a method
                final boolean dropResult = player.dropItem(dropItem);
                // Calls a method
                clickResult.setCancel(!dropResult);
                // Branch: checks a condition
                if (dropResult) {
                    // Calls a method
                    final int amount = clicked.amount();
                    // Assigns a value
                    final int newAmount = amount - 1;
                    // Calls a method
                    clicked = clicked.withAmount(newAmount);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        clickResult.setClicked(clicked);
        // Calls a method
        clickResult.setCursor(cursor);

        // Returns a value to the caller
        return clickResult;
    // End of a block/expression
    }

    // Code statement
    private void callClickEvent(Player player, AbstractInventory inventory, int slot,
                                // Start of a method/block
                                ClickType clickType, ItemStack clicked, ItemStack cursor) {
        // Calls a method
        EventDispatcher.call(new InventoryClickEvent(inventory, player, slot, clickType, clicked, cursor));
    // End of a block/expression
    }
// End of a block/expression
}