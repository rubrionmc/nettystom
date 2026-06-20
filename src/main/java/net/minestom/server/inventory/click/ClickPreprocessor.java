// Déclaration du paquet de ce fichier
package net.minestom.server.inventory.click;

// Import d'une classe nécessaire
import net.minestom.server.inventory.PlayerInventory;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientClickWindowPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.inventory.PlayerInventoryUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.LinkedHashSet;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

/**
 * Preprocesses click packets, turning them into {@link Click} instances for further processing.
 */
// Déclaration de type (classe/interface/enum/record)
public final class ClickPreprocessor {
    // Appelle une méthode
    private final Set<Integer> leftDrag = new LinkedHashSet<>();
    // Appelle une méthode
    private final Set<Integer> rightDrag = new LinkedHashSet<>();
    // Appelle une méthode
    private final Set<Integer> middleDrag = new LinkedHashSet<>();

    // Début d'une méthode/d'un bloc
    public void clearCache() {
        // Accès à l'objet courant/parent
        this.leftDrag.clear();
        // Accès à l'objet courant/parent
        this.rightDrag.clear();
        // Accès à l'objet courant/parent
        this.middleDrag.clear();
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public boolean isCreativeClick(Click click, boolean hasCursorItem) {
        // Renvoie une valeur à l'appelant
        return switch (click) {
            // Embranchement multiple (switch/case)
            case Click.Middle ignored -> !hasCursorItem; // Block clones (except the edge case)
            // Embranchement multiple (switch/case)
            case Click.MiddleDrag ignored -> true; // Block clone drags
            // Embranchement multiple (switch/case)
            default -> false;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Processes the provided click packet, turning it into a {@link Click}.
     *
     * @param packet        the raw click packet
     * @param containerSize the size of the open container, or null if the player inventory is open
     * @return the processed click, or nothing if the click takes place over multiple packets and this is not the final
     * one (e.g. a drag)
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Click processClick(ClientClickWindowPacket packet, @Nullable Integer containerSize) {
        // Instruction de code
        final int slot;
        // Embranchement : vérifie une condition
        if (containerSize == null) {
            // Appelle une méthode
            slot = PlayerInventoryUtils.convertWindow0SlotToMinestomSlot(packet.slot());
        // Embranchement : vérifie une condition
        } else if (packet.slot() >= containerSize) {
            // Appelle une méthode
            slot = containerSize + PlayerInventoryUtils.convertWindowSlotToMinestomSlot(packet.slot(), containerSize);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            slot = packet.slot();
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final int maxSize = containerSize == null ? PlayerInventory.INVENTORY_SIZE : containerSize + PlayerInventory.INNER_INVENTORY_SIZE;
        // Affecte une valeur
        final boolean valid = slot >= 0 && slot < maxSize;

        // Embranchement : vérifie une condition
        if (valid) {
            // Renvoie une valeur à l'appelant
            return process(packet.clickType(), slot, packet.button());
        // Branche alternative de la condition
        } else {
            // Renvoie une valeur à l'appelant
            return slot == -999 ? processInvalidSlot(packet.clickType(), packet.button()) : null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Processes a click in an invalid slot (i.e. the slot is irrelevant, like in a drop)
     */
    // Début d'une méthode/d'un bloc
    private @Nullable Click processInvalidSlot(ClientClickWindowPacket.ClickType type, byte button) {
        // Renvoie une valeur à l'appelant
        return switch (type) {
            // Embranchement multiple (switch/case)
            case PICKUP, THROW -> {
                // Embranchement : vérifie une condition
                if (button == 0) yield new Click.LeftDropCursor();
                // Embranchement : vérifie une condition
                if (button == 1) yield new Click.RightDropCursor();
                // Instruction de code
                yield null;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case CLONE -> {
                // Embranchement : vérifie une condition
                if (button == 2) yield new Click.MiddleDropCursor(); // Why Mojang, why?
                // Instruction de code
                yield null;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case QUICK_CRAFT -> {
                // Trust me, a switch would not make this cleaner

                // Embranchement : vérifie une condition
                if (button == 2) {
                    // Appelle une méthode
                    var list = List.copyOf(leftDrag);
                    // Appelle une méthode
                    leftDrag.clear();
                    // Appelle une méthode
                    yield new Click.LeftDrag(list);
                // Embranchement : vérifie une condition
                } else if (button == 6) {
                    // Appelle une méthode
                    var list = List.copyOf(rightDrag);
                    // Appelle une méthode
                    rightDrag.clear();
                    // Appelle une méthode
                    yield new Click.RightDrag(list);
                // Embranchement : vérifie une condition
                } else if (button == 10) {
                    // Appelle une méthode
                    var list = List.copyOf(middleDrag);
                    // Appelle une méthode
                    middleDrag.clear();
                    // Appelle une méthode
                    yield new Click.MiddleDrag(list);
                // Fin d'un bloc/d'une expression
                }

                // Embranchement : vérifie une condition
                if (button == 0) leftDrag.clear();
                // Embranchement : vérifie une condition
                if (button == 4) rightDrag.clear();
                // Embranchement : vérifie une condition
                if (button == 8) middleDrag.clear();

                // Instruction de code
                yield null;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            default -> null;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    /**
     * Processes a click in a valid slot, possibly returning a result.
     */
    // Début d'une méthode/d'un bloc
    private @Nullable Click process(ClientClickWindowPacket.ClickType type, int slot, byte button) {
        // Renvoie une valeur à l'appelant
        return switch (type) {
            // Embranchement multiple (switch/case)
            case PICKUP -> switch (button) {
                // Embranchement multiple (switch/case)
                case 0 -> new Click.Left(slot);
                // Embranchement multiple (switch/case)
                case 1 -> new Click.Right(slot);
                // Embranchement multiple (switch/case)
                default -> null;
            // Fin d'un bloc/d'une expression
            };
            // Embranchement multiple (switch/case)
            case QUICK_MOVE -> button == 0 ? new Click.LeftShift(slot) : new Click.RightShift(slot);
            // Embranchement multiple (switch/case)
            case SWAP -> {
                // Embranchement : vérifie une condition
                if (button >= 0 && button < 9) {
                    // Appelle une méthode
                    yield new Click.HotbarSwap(button, slot);
                // Embranchement : vérifie une condition
                } else if (button == 40) {
                    // Appelle une méthode
                    yield new Click.OffhandSwap(slot);
                // Branche alternative de la condition
                } else {
                    // Instruction de code
                    yield null;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case CLONE -> new Click.Middle(slot);
            // Embranchement multiple (switch/case)
            case THROW -> new Click.DropSlot(slot, button == 1);
            // Embranchement multiple (switch/case)
            case QUICK_CRAFT -> {
                // Embranchement multiple (switch/case)
                switch (button) {
                    // Embranchement multiple (switch/case)
                    case 1 -> leftDrag.add(slot);
                    // Embranchement multiple (switch/case)
                    case 5 -> rightDrag.add(slot);
                    // Embranchement multiple (switch/case)
                    case 9 -> middleDrag.add(slot);
                // Fin d'un bloc/d'une expression
                }
                // Instruction de code
                yield null;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case PICKUP_ALL -> new Click.Double(slot);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
