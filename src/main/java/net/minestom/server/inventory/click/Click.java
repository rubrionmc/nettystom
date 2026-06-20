// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.IntFunction;

/**
 * A tagged union representing possible clicks from the client.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface Click {

    /**
     * Gets the slot of this click. -999 indicates the click either drops the cursor item in some way (implements
     * {@link DropCursor}) or is a drag click, which support multiple slots (implements {@link Drag}). Otherwise, this
     * represents a slot inside the relevant inventory, so {@code inventory.getItemStack(click.slot())}) will return the
     * "clicked" item.
     */
    // Début d'une méthode/d'un bloc
    default int slot() {
        // Renvoie une valeur à l'appelant
        return -999;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents the player dropping an item, whether from clicking outside the inventory or from pressing the drop
     * key.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface DropCursor extends Click {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents a drag click in an inventory.
     */
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Drag extends Click {

        /**
         * Returns the list of slots. When the event inventory is the opened inventory, slots greater than its size
         * indicate slots in the player inventory; subtract the size of the event inventory to get the player inventory
         * slot.
         */
        // Appelle une méthode
        List<Integer> slots();

    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Left(int slot) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Right(int slot) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Middle(int slot) implements Click {
        // Creative only
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LeftShift(int slot) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RightShift(int slot) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Double(int slot) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LeftDrag(List<Integer> slots) implements Drag {
        // Début d'une méthode/d'un bloc
        public LeftDrag {
            // Appelle une méthode
            slots = List.copyOf(slots);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RightDrag(List<Integer> slots) implements Drag {
        // Début d'une méthode/d'un bloc
        public RightDrag {
            // Appelle une méthode
            slots = List.copyOf(slots);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record MiddleDrag(List<Integer> slots) implements Drag {
        // Creative only
        // Début d'une méthode/d'un bloc
        public MiddleDrag {
            // Appelle une méthode
            slots = List.copyOf(slots);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record LeftDropCursor() implements DropCursor {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RightDropCursor() implements DropCursor {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record MiddleDropCursor() implements DropCursor {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record DropSlot(int slot, boolean all) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record HotbarSwap(int hotbarSlot, int slot) implements Click {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record OffhandSwap(int slot) implements Click {
    // Fin d'un bloc/d'une expression
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
     * @param click the click to convert
     * @param containerSize the size of the opened container, or null if the player inventory is open
     * @return the (possibly) converted click
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static Click.Window toWindow(Click click, @Nullable Integer containerSize) {
        // Renvoie une valeur à l'appelant
        return switch (click) {
            // Everything with one dynamic slot
            // Embranchement multiple (switch/case)
            case Left(int slot) -> toWindowSingle(slot, containerSize, Left::new);
            // Embranchement multiple (switch/case)
            case Right(int slot) -> toWindowSingle(slot, containerSize, Right::new);
            // Embranchement multiple (switch/case)
            case Middle(int slot) -> toWindowSingle(slot, containerSize, Middle::new);
            // Embranchement multiple (switch/case)
            case LeftShift(int slot) -> toWindowSingle(slot, containerSize, LeftShift::new);
            // Embranchement multiple (switch/case)
            case RightShift(int slot) -> toWindowSingle(slot, containerSize, RightShift::new);
            // Embranchement multiple (switch/case)
            case Double(int slot) -> toWindowSingle(slot, containerSize, Double::new);
            // Embranchement multiple (switch/case)
            case OffhandSwap(int slot) -> toWindowSingle(slot, containerSize, OffhandSwap::new);
            // Embranchement multiple (switch/case)
            case DropSlot(int slot, boolean all) -> toWindowSingle(slot, containerSize, s -> new DropSlot(s, all));
            // Embranchement multiple (switch/case)
            case HotbarSwap(int hotbarSlot, int slot) -> toWindowSingle(slot, containerSize, s -> new HotbarSwap(hotbarSlot, s));

            // Everything with zero slots
            // Embranchement multiple (switch/case)
            case LeftDropCursor() -> new Window(false, click);
            // Embranchement multiple (switch/case)
            case MiddleDropCursor() -> new Window(false, click);
            // Embranchement multiple (switch/case)
            case RightDropCursor() -> new Window(false, click);

            // Everything with multiple slots
            // Embranchement multiple (switch/case)
            case LeftDrag(List<Integer> slots) -> toWindowMultiple(slots, containerSize, LeftDrag::new);
            // Embranchement multiple (switch/case)
            case RightDrag(List<Integer> slots) -> toWindowMultiple(slots, containerSize, RightDrag::new);
            // Embranchement multiple (switch/case)
            case MiddleDrag(List<Integer> slots) -> toWindowMultiple(slots, containerSize, MiddleDrag::new);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Click.Window toWindowSingle(int slot, @Nullable Integer containerSize, IntFunction<Click> constructor) {
        // Embranchement : vérifie une condition
        if (containerSize == null) { // No opened inventory, so always in the player inventory
            // Renvoie une valeur à l'appelant
            return new Window(false, constructor.apply(slot));
        // Embranchement : vérifie une condition
        } else if (slot < containerSize) { // In the opened inventory, so do nothing
            // Renvoie une valeur à l'appelant
            return new Window(true, constructor.apply(slot));
        // Branche alternative de la condition
        } else { // In the opened inventory, so shift it over and place inside player inventory
            // Renvoie une valeur à l'appelant
            return new Window(false, constructor.apply(slot - containerSize));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Click.Window toWindowMultiple(List<Integer> slots, @Nullable Integer containerSize, Function<List<Integer>, Click> constructor) {
        // Embranchement : vérifie une condition
        if (containerSize == null) { // No opened inventory, so always in the player inventory
            // Renvoie une valeur à l'appelant
            return new Window(false, constructor.apply(slots));
        // Fin d'un bloc/d'une expression
        }

        // If there's at least one slot in the opened inventory, the entire click is considered inside it
        // Boucle : répète un bloc
        for (int slot : slots) {
            // Embranchement : vérifie une condition
            if (slot < containerSize) {
                // Renvoie une valeur à l'appelant
                return new Window(true, constructor.apply(slots));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Otherwise, everything is in the player inventory, and map it over
        // Renvoie une valeur à l'appelant
        return new Window(false, constructor.apply(slots.stream().map(slot -> slot - containerSize).toList()));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts a click from window-specific context back to "normal" click information.
     * <br>
     * This is the inverse of {@link #toWindow(Click, Integer)}; read that for more information
     *
     * @param window the click, along with whether or not it was inside the window
     * @param containerSize the size of the opened container, or null if the player inventory is open
     * @return the (potentially) converted click information
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static Click fromWindow(Click.Window window, @Nullable Integer containerSize) {
        // Renvoie une valeur à l'appelant
        return switch (window.click()) {
            // Everything with one dynamic slot
            // Embranchement multiple (switch/case)
            case Left(int slot) -> fromWindowSingle(window, containerSize, Left::new);
            // Embranchement multiple (switch/case)
            case Right(int slot) -> fromWindowSingle(window, containerSize, Right::new);
            // Embranchement multiple (switch/case)
            case Middle(int slot) -> fromWindowSingle(window, containerSize, Middle::new);
            // Embranchement multiple (switch/case)
            case LeftShift(int slot) -> fromWindowSingle(window, containerSize, LeftShift::new);
            // Embranchement multiple (switch/case)
            case RightShift(int slot) -> fromWindowSingle(window, containerSize, RightShift::new);
            // Embranchement multiple (switch/case)
            case Double(int slot) -> fromWindowSingle(window, containerSize, Double::new);
            // Embranchement multiple (switch/case)
            case OffhandSwap(int slot) -> fromWindowSingle(window, containerSize, OffhandSwap::new);
            // Embranchement multiple (switch/case)
            case DropSlot(int slot, boolean all) -> fromWindowSingle(window, containerSize, s -> new DropSlot(s, all));
            // Embranchement multiple (switch/case)
            case HotbarSwap(int hotbarSlot, int slot) -> fromWindowSingle(window, containerSize, s -> new HotbarSwap(hotbarSlot, s));

            // Everything with zero slots
            // Embranchement multiple (switch/case)
            case LeftDropCursor() -> window.click();
            // Embranchement multiple (switch/case)
            case RightDropCursor() -> window.click();
            // Embranchement multiple (switch/case)
            case MiddleDropCursor() -> window.click();

            // Everything with multiple slots
            // Embranchement multiple (switch/case)
            case LeftDrag(List<Integer> slots) -> fromWindowMultiple(window, slots, containerSize, LeftDrag::new);
            // Embranchement multiple (switch/case)
            case RightDrag(List<Integer> slots) -> fromWindowMultiple(window, slots, containerSize, RightDrag::new);
            // Embranchement multiple (switch/case)
            case MiddleDrag(List<Integer> slots) -> fromWindowMultiple(window, slots, containerSize, MiddleDrag::new);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Click fromWindowSingle(Click.Window window, @Nullable Integer containerSize, IntFunction<Click> constructor) {
        // The inverse of toWindowSingle; more details there
        // Renvoie une valeur à l'appelant
        return containerSize == null || window.inOpened() ? window.click()
                // Appelle une méthode
                : constructor.apply(window.click().slot() + containerSize);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Click fromWindowMultiple(Window window, List<Integer> slots, @Nullable Integer containerSize, Function<List<Integer>, Click> constructor) {
        // The inverse of toWindowMultiple; more details there
        // Renvoie une valeur à l'appelant
        return containerSize == null || window.inOpened() ? window.click()
                // Appelle une méthode
                : constructor.apply(slots.stream().map(slot -> slot + containerSize).toList());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Represents a click inside a window.
     *
     * @param inOpened whether the window is the player inventory (false) or the opened inventory (true).
     * @param click the (contextualized) click
     */
    // Déclaration de type (classe/interface/enum/record)
    record Window(boolean inOpened, Click click) {
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
