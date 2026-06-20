// Package declaration for this file
package net.minestom.server.inventory.click;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.IntFunction;

/**
 * A tagged union representing possible clicks from the client.
 */
// Type declaration (class/interface/enum/record)
public sealed interface Click {

    /**
     * Gets the slot of this click. -999 indicates the click either drops the cursor item in some way (implements
     * {@link DropCursor}) or is a drag click, which support multiple slots (implements {@link Drag}). Otherwise, this
     * represents a slot inside the relevant inventory, so {@code inventory.getItemStack(click.slot())}) will return the
     * "clicked" item.
     */
    // Start of a method/block
    default int slot() {
        // Returns a value to the caller
        return -999;
    // End of a block/expression
    }

    /**
     * Represents the player dropping an item, whether from clicking outside the inventory or from pressing the drop
     * key.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface DropCursor extends Click {
    // End of a block/expression
    }

    /**
     * Represents a drag click in an inventory.
     */
    // Type declaration (class/interface/enum/record)
    sealed interface Drag extends Click {

        /**
         * Returns the list of slots. When the event inventory is the opened inventory, slots greater than its size
         * indicate slots in the player inventory; subtract the size of the event inventory to get the player inventory
         * slot.
         */
        // Calls a method
        List<Integer> slots();

    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Left(int slot) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Right(int slot) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Middle(int slot) implements Click {
        // Creative only
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LeftShift(int slot) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RightShift(int slot) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Double(int slot) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LeftDrag(List<Integer> slots) implements Drag {
        // Start of a method/block
        public LeftDrag {
            // Calls a method
            slots = List.copyOf(slots);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RightDrag(List<Integer> slots) implements Drag {
        // Start of a method/block
        public RightDrag {
            // Calls a method
            slots = List.copyOf(slots);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record MiddleDrag(List<Integer> slots) implements Drag {
        // Creative only
        // Start of a method/block
        public MiddleDrag {
            // Calls a method
            slots = List.copyOf(slots);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record LeftDropCursor() implements DropCursor {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RightDropCursor() implements DropCursor {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record MiddleDropCursor() implements DropCursor {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record DropSlot(int slot, boolean all) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record HotbarSwap(int hotbarSlot, int slot) implements Click {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record OffhandSwap(int slot) implements Click {
    // End of a block/expression
    }

    /**
     * Converts any clicks that are fully within the player inventory into clicks that are considered as being inside
     * the player inventory. This is useful for making click event APIs much less obfuscated due to how the protocol is
     * structured.
     * <br>
     * Essentially, if the player has an inventory open but clicks inside their own inventory, the packet sent will be
     * inside the opened inventory but have a slot ID greater than the size of the opened inventory. For cases where
     * this happens, this function will convert it into a click that's considered inside the player inventory instead,
     * adjusting the slot ID as necessary. On the returned {@link Window} instance, the boolean field indicates which
     * inventory the click is in (since it was unambiguous previously, but is not now).
     *
     * @param click         the click to convert
     * @param containerSize the size of the opened container, or null if the player inventory is open
     * @return the (possibly) converted click
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static Click.Window toWindow(Click click, @Nullable Integer containerSize) {
        // Returns a value to the caller
        return switch (click) {
            // Everything with one dynamic slot
            // Multiple branching (switch/case)
            case Left(int slot) -> toWindowSingle(slot, containerSize, Left::new);
            // Multiple branching (switch/case)
            case Right(int slot) -> toWindowSingle(slot, containerSize, Right::new);
            // Multiple branching (switch/case)
            case Middle(int slot) -> toWindowSingle(slot, containerSize, Middle::new);
            // Multiple branching (switch/case)
            case LeftShift(int slot) -> toWindowSingle(slot, containerSize, LeftShift::new);
            // Multiple branching (switch/case)
            case RightShift(int slot) -> toWindowSingle(slot, containerSize, RightShift::new);
            // Multiple branching (switch/case)
            case Double(int slot) -> toWindowSingle(slot, containerSize, Double::new);
            // Multiple branching (switch/case)
            case OffhandSwap(int slot) -> toWindowSingle(slot, containerSize, OffhandSwap::new);
            // Multiple branching (switch/case)
            case DropSlot(int slot, boolean all) -> toWindowSingle(slot, containerSize, s -> new DropSlot(s, all));
            // Multiple branching (switch/case)
            case HotbarSwap(int hotbarSlot, int slot) ->
                    // Calls a method
                    toWindowSingle(slot, containerSize, s -> new HotbarSwap(hotbarSlot, s));

            // Everything with zero slots
            // Multiple branching (switch/case)
            case LeftDropCursor() -> new Window(false, click);
            // Multiple branching (switch/case)
            case MiddleDropCursor() -> new Window(false, click);
            // Multiple branching (switch/case)
            case RightDropCursor() -> new Window(false, click);

            // Everything with multiple slots
            // Multiple branching (switch/case)
            case LeftDrag(List<Integer> slots) -> toWindowMultiple(slots, containerSize, LeftDrag::new);
            // Multiple branching (switch/case)
            case RightDrag(List<Integer> slots) -> toWindowMultiple(slots, containerSize, RightDrag::new);
            // Multiple branching (switch/case)
            case MiddleDrag(List<Integer> slots) -> toWindowMultiple(slots, containerSize, MiddleDrag::new);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static Click.Window toWindowSingle(int slot, @Nullable Integer containerSize, IntFunction<Click> constructor) {
        // Branch: checks a condition
        if (containerSize == null) { // No opened inventory, so always in the player inventory
            // Returns a value to the caller
            return new Window(false, constructor.apply(slot));
        // Branch: checks a condition
        } else if (slot < containerSize) { // In the opened inventory, so do nothing
            // Returns a value to the caller
            return new Window(true, constructor.apply(slot));
        // Alternative branch of the condition
        } else { // In the opened inventory, so shift it over and place inside player inventory
            // Returns a value to the caller
            return new Window(false, constructor.apply(slot - containerSize));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static Click.Window toWindowMultiple(List<Integer> slots, @Nullable Integer containerSize, Function<List<Integer>, Click> constructor) {
        // Branch: checks a condition
        if (containerSize == null) { // No opened inventory, so always in the player inventory
            // Returns a value to the caller
            return new Window(false, constructor.apply(slots));
        // End of a block/expression
        }

        // If there's at least one slot in the opened inventory, the entire click is considered inside it
        // Loop: repeats a block
        for (int slot : slots) {
            // Branch: checks a condition
            if (slot < containerSize) {
                // Returns a value to the caller
                return new Window(true, constructor.apply(slots));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Otherwise, everything is in the player inventory, and map it over
        // Returns a value to the caller
        return new Window(false, constructor.apply(slots.stream().map(slot -> slot - containerSize).toList()));
    // End of a block/expression
    }

    /**
     * Converts a click from window-specific context back to "normal" click information.
     * <br>
     * This is the inverse of {@link #toWindow(Click, Integer)}; read that for more information
     *
     * @param window        the click, along with whether it was inside the window
     * @param containerSize the size of the opened container, or null if the player inventory is open
     * @return the (potentially) converted click information
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static Click fromWindow(Click.Window window, @Nullable Integer containerSize) {
        // Returns a value to the caller
        return switch (window.click()) {
            // Everything with one dynamic slot
            // Multiple branching (switch/case)
            case Left(_) -> fromWindowSingle(window, containerSize, Left::new);
            // Multiple branching (switch/case)
            case Right(_) -> fromWindowSingle(window, containerSize, Right::new);
            // Multiple branching (switch/case)
            case Middle(_) -> fromWindowSingle(window, containerSize, Middle::new);
            // Multiple branching (switch/case)
            case LeftShift(_) -> fromWindowSingle(window, containerSize, LeftShift::new);
            // Multiple branching (switch/case)
            case RightShift(_) -> fromWindowSingle(window, containerSize, RightShift::new);
            // Multiple branching (switch/case)
            case Double(_) -> fromWindowSingle(window, containerSize, Double::new);
            // Multiple branching (switch/case)
            case OffhandSwap(_) -> fromWindowSingle(window, containerSize, OffhandSwap::new);
            // Multiple branching (switch/case)
            case DropSlot(_, boolean all) -> fromWindowSingle(window, containerSize, s -> new DropSlot(s, all));
            // Multiple branching (switch/case)
            case HotbarSwap(int hotbarSlot, _) ->
                    // Calls a method
                    fromWindowSingle(window, containerSize, s -> new HotbarSwap(hotbarSlot, s));

            // Everything with zero slots
            // Multiple branching (switch/case)
            case LeftDropCursor() -> window.click();
            // Multiple branching (switch/case)
            case RightDropCursor() -> window.click();
            // Multiple branching (switch/case)
            case MiddleDropCursor() -> window.click();

            // Everything with multiple slots
            // Multiple branching (switch/case)
            case LeftDrag(List<Integer> slots) -> fromWindowMultiple(window, slots, containerSize, LeftDrag::new);
            // Multiple branching (switch/case)
            case RightDrag(List<Integer> slots) -> fromWindowMultiple(window, slots, containerSize, RightDrag::new);
            // Multiple branching (switch/case)
            case MiddleDrag(List<Integer> slots) -> fromWindowMultiple(window, slots, containerSize, MiddleDrag::new);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private static Click fromWindowSingle(Click.Window window, @Nullable Integer containerSize, IntFunction<Click> constructor) {
        // The inverse of toWindowSingle; more details there
        // Returns a value to the caller
        return containerSize == null || window.inOpened() ? window.click()
                // Calls a method
                : constructor.apply(window.click().slot() + containerSize);
    // End of a block/expression
    }

    // Start of a method/block
    private static Click fromWindowMultiple(Window window, List<Integer> slots, @Nullable Integer containerSize, Function<List<Integer>, Click> constructor) {
        // The inverse of toWindowMultiple; more details there
        // Returns a value to the caller
        return containerSize == null || window.inOpened() ? window.click()
                // Calls a method
                : constructor.apply(slots.stream().map(slot -> slot + containerSize).toList());
    // End of a block/expression
    }

    /**
     * Represents a click inside a window.
     *
     * @param inOpened whether the window is the player inventory (false) or the opened inventory (true).
     * @param click    the (contextualized) click
     */
    // Type declaration (class/interface/enum/record)
    record Window(boolean inOpened, Click click) {
    // End of a block/expression
    }

// End of a block/expression
}
