// Package declaration for this file
package net.minestom.server.inventory.click;

// Import of a required class
import net.minestom.server.inventory.PlayerInventory;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import of a required class
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.LinkedHashSet;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

/**
 * Preprocesses click packets, turning them into {@link Click} instances for further processing.
 */
// Type declaration (class/interface/enum/record)
public final class ClickPreprocessor {
    // Calls a method
    private final Set<Integer> leftDrag = new LinkedHashSet<>();
    // Calls a method
    private final Set<Integer> rightDrag = new LinkedHashSet<>();
    // Calls a method
    private final Set<Integer> middleDrag = new LinkedHashSet<>();

    // Start of a method/block
    public void clearCache() {
        // Access to the current/parent object
        this.leftDrag.clear();
        // Access to the current/parent object
        this.rightDrag.clear();
        // Access to the current/parent object
        this.middleDrag.clear();
    // End of a block/expression
    }

    /**
     * Determines whether a click is creative only. This should match client behavior, including edge cases like
     * middle clicks (item clones) being sent in survival when there is an item in the cursor (which would make it a
     * no-op), hence the parameter. This function can be overridden if modifying the creative check logic is desired,
     * since {@link net.minestom.server.listener.WindowListener} directly depends on this.
     *
     * @param click         the click to check
     * @param hasCursorItem if the client has an item in the cursor (for checking {@code Click.Middle})
     * @return if the click is creative only
     */
    // Start of a method/block
    public boolean isCreativeClick(Click click, boolean hasCursorItem) {
        // Returns a value to the caller
        return switch (click) {
            // Multiple branching (switch/case)
            case Click.Middle ignored -> !hasCursorItem; // Block clones (except the edge case)
            // Multiple branching (switch/case)
            case Click.MiddleDrag ignored -> true; // Block clone drags
            // Multiple branching (switch/case)
            default -> false;
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Processes the provided click packet, turning it into a {@link Click}.
     *
     * @param packet        the raw click packet
     * @param containerSize the size of the open container, or null if the player inventory is open
     * @return the processed click, or nothing if the click takes place over multiple packets and this is not the final
     * one (e.g. a drag)
     */
    // Start of a method/block
    public @Nullable Click processClick(ClientClickWindowPacket packet, @Nullable Integer containerSize) {
        // Code statement
        final int slot;
        // Branch: checks a condition
        if (containerSize == null) {
            // Calls a method
            slot = PlayerInventoryUtils.convertWindow0SlotToMinestomSlot(packet.slot());
        // Branch: checks a condition
        } else if (packet.slot() >= containerSize) {
            // Calls a method
            slot = containerSize + PlayerInventoryUtils.convertWindowSlotToMinestomSlot(packet.slot(), containerSize);
        // Alternative branch of the condition
        } else {
            // Calls a method
            slot = packet.slot();
        // End of a block/expression
        }

        // Assigns a value
        final int maxSize = containerSize == null ? PlayerInventory.INVENTORY_SIZE : containerSize + PlayerInventory.INNER_INVENTORY_SIZE;
        // Assigns a value
        final boolean valid = slot >= 0 && slot < maxSize;

        // Branch: checks a condition
        if (valid) {
            // Returns a value to the caller
            return process(packet.clickType(), slot, packet.button());
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return slot == -999 ? processInvalidSlot(packet.clickType(), packet.button()) : null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Processes a click in an invalid slot (i.e. the slot is irrelevant, like in a drop)
     */
    // Start of a method/block
    private @Nullable Click processInvalidSlot(ClientClickWindowPacket.ClickType type, byte button) {
        // Returns a value to the caller
        return switch (type) {
            // Multiple branching (switch/case)
            case PICKUP, THROW -> {
                // Branch: checks a condition
                if (button == 0) yield new Click.LeftDropCursor();
                // Branch: checks a condition
                if (button == 1) yield new Click.RightDropCursor();
                // Code statement
                yield null;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case CLONE -> {
                // Branch: checks a condition
                if (button == 2) yield new Click.MiddleDropCursor(); // Why Mojang, why?
                // Code statement
                yield null;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case QUICK_CRAFT -> {
                // Trust me, a switch would not make this cleaner

                // Branch: checks a condition
                if (button == 2) {
                    // Calls a method
                    var list = List.copyOf(leftDrag);
                    // Calls a method
                    leftDrag.clear();
                    // Calls a method
                    yield new Click.LeftDrag(list);
                // Branch: checks a condition
                } else if (button == 6) {
                    // Calls a method
                    var list = List.copyOf(rightDrag);
                    // Calls a method
                    rightDrag.clear();
                    // Calls a method
                    yield new Click.RightDrag(list);
                // Branch: checks a condition
                } else if (button == 10) {
                    // Calls a method
                    var list = List.copyOf(middleDrag);
                    // Calls a method
                    middleDrag.clear();
                    // Calls a method
                    yield new Click.MiddleDrag(list);
                // End of a block/expression
                }

                // Branch: checks a condition
                if (button == 0) leftDrag.clear();
                // Branch: checks a condition
                if (button == 4) rightDrag.clear();
                // Branch: checks a condition
                if (button == 8) middleDrag.clear();

                // Code statement
                yield null;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            default -> null;
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Processes a click in a valid slot, possibly returning a result.
     */
    // Start of a method/block
    private @Nullable Click process(ClientClickWindowPacket.ClickType type, int slot, byte button) {
        // Returns a value to the caller
        return switch (type) {
            // Multiple branching (switch/case)
            case PICKUP -> switch (button) {
                // Multiple branching (switch/case)
                case 0 -> new Click.Left(slot);
                // Multiple branching (switch/case)
                case 1 -> new Click.Right(slot);
                // Multiple branching (switch/case)
                default -> null;
            // End of a block/expression
            };
            // Multiple branching (switch/case)
            case QUICK_MOVE -> button == 0 ? new Click.LeftShift(slot) : new Click.RightShift(slot);
            // Multiple branching (switch/case)
            case SWAP -> {
                // Branch: checks a condition
                if (button >= 0 && button < 9) {
                    // Calls a method
                    yield new Click.HotbarSwap(button, slot);
                // Branch: checks a condition
                } else if (button == 40) {
                    // Calls a method
                    yield new Click.OffhandSwap(slot);
                // Alternative branch of the condition
                } else {
                    // Code statement
                    yield null;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case CLONE -> new Click.Middle(slot);
            // Multiple branching (switch/case)
            case THROW -> new Click.DropSlot(slot, button == 1);
            // Multiple branching (switch/case)
            case QUICK_CRAFT -> {
                // Multiple branching (switch/case)
                switch (button) {
                    // Multiple branching (switch/case)
                    case 1 -> leftDrag.add(slot);
                    // Multiple branching (switch/case)
                    case 5 -> rightDrag.add(slot);
                    // Multiple branching (switch/case)
                    case 9 -> middleDrag.add(slot);
                // End of a block/expression
                }
                // Code statement
                yield null;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case PICKUP_ALL -> new Click.Double(slot);
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
